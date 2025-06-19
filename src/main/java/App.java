package src.main.java;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.json.JSONObject;

import src.main.java.auth.Authenticator;

import src.main.java.auth.Login;
import src.main.java.auth.Register;
import src.main.java.components.Employee;
import src.main.java.components.Owner;
import src.server.java.ServerURL;

public final class App extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel loadingOverlay;
    private JLabel loadingIcon;

    public App() {

        ServerURL server = new ServerURL();

        setTitle("Mrs. Clean LaundryEase");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("./src/assets/Laundry-removebg-preview.png");
        setIconImage(icon);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int userExitChoice = JOptionPane.showConfirmDialog(App.this,
                        "Are you sure you want to exit LaundryEase?", "Exit application", JOptionPane.YES_NO_OPTION);
                if (userExitChoice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabAreaInsets.left = 0;
            }

            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0;
            }

            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return 0;
            }

            @Override
            protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
                    Rectangle textRect) {
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }
        });

        getContentPane().add(tabbedPane);

        initTab();

        setVisible(true);

        server.pingServer(isUp -> {
            if (!isUp) {
                JOptionPane.showMessageDialog(
                        null,
                        "WARNING: The server is not running. Please check your PHP server.",
                        "Server error!",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        initOwnerAccount();

    }

    public void initOwnerAccount() {
        new Thread(() -> {
            String postData = "client_fetch_token_request=;;mslaundryshop2025;;";
            HttpURLConnection conn = new ServerURL().createPostConnection("init_owner.php", postData);
            parseJsonResponse(conn);
        }).start();
    }

    private void initTab() {
        String authResult = new Authenticator(this).isAuthenticated();
        if (authResult != null) {
            if (authResult.equals("employee")) {
                showEmployee();
            } else if (authResult.equals("owner")) {
                showOwner();
            }
        } else {
            new Authenticator(this).logout();
            showLogin();
        }
    }

    public void showLogin() {
        tabbedPane.removeAll();
        setSize(680, 670);
        setLocationRelativeTo(null);
        tabbedPane.addTab("Login Panel", wrapCentered(new Login(this)));
    }

    public void showRegister() {
        tabbedPane.removeAll();
        setSize(1100, 650);
        setLocationRelativeTo(null);
        tabbedPane.addTab("Register Panel", wrapCentered(new Register(this)));
    }

    public void showOwner() {
        tabbedPane.removeAll();
        setSize(1120, 650);
        setLocationRelativeTo(null);
        tabbedPane.addTab("Owner Panel", new Owner(this));
    }

    public void showEmployee() {
        tabbedPane.removeAll();
        setSize(1120, 650);
        setLocationRelativeTo(null);
        tabbedPane.addTab("Employee Panel", new Employee(this));
    }

    private JPanel wrapCentered(Component comp) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        wrapper.add(comp, gbc);
        return wrapper;
    }

    public void overlayLoading(boolean isLoading) {
        if (isLoading) {
            if (loadingOverlay == null) {
                loadingOverlay = new JPanel(new GridBagLayout()) {
                    @Override
                    public boolean isOpaque() {
                        return false;
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        g.setColor(new java.awt.Color(0, 0, 0, 25));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponent(g);
                    }
                };

                loadingOverlay.addMouseListener(new java.awt.event.MouseAdapter() {
                });
                loadingOverlay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                });
                loadingOverlay.addKeyListener(new java.awt.event.KeyAdapter() {
                });

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;

                loadingIcon = new JLabel(new javax.swing.ImageIcon(
                        getClass().getResource("/src/main/resources/load-time-unscreen-ezgif.com-resize.gif")));
                loadingOverlay.add(loadingIcon, gbc);
                loadingOverlay.setBounds(0, 0, getWidth(), getHeight());
            }

            getLayeredPane().add(loadingOverlay, JLayeredPane.POPUP_LAYER);
            loadingOverlay.setBounds(0, 0, getWidth(), getHeight());
            loadingOverlay.setVisible(true);
            loadingOverlay.requestFocusInWindow();
            getLayeredPane().revalidate();
            getLayeredPane().repaint();

        } else {
            if (loadingOverlay != null) {
                loadingOverlay.setVisible(false);
                getLayeredPane().remove(loadingOverlay);
                getLayeredPane().revalidate();
                getLayeredPane().repaint();
            }
        }
    }

    private JSONObject parseJsonResponse(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }

            return new JSONObject(responseBuilder.toString());

        } catch (Exception e) {
            return null;
        }
    }
}
