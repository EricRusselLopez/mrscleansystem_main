package src.main.java.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONObject;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.Timer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import src.main.java.App;
import src.main.java.auth.Authenticator;
import src.main.java.auth.session.java.FetchUser;
import src.main.java.components.helper.Accounts;
import src.main.java.components.helper.Branches;
import src.main.java.components.helper.GenerateReports;
import src.main.java.components.helper.Inventory;
import src.main.java.components.helper.Notifications;
import src.main.java.components.helper.Sales;
import src.server.java.ServerURL;
import src.main.java.components.helper.Pricing;
import src.utils.CustomBorder;
import src.utils.CustomHeaderRenderer;
import src.utils.CustomImageResizer;
import src.utils.RowRenderer;
import src.utils.RowRendererInventory;
import src.utils.RowRendererPricing;
import src.utils.RowRendererReports;

public class Owner extends JPanel implements CustomBorder {

    JSONObject user = FetchUser.getUserData();
    ServerURL server = new ServerURL();
    Branches branches = new Branches();
    private App app;

    public Owner(App app) {
        this.app = app;

        if (new Authenticator().isAuthenticated() == null) {
            new Authenticator().logout();
        }

        initComponents();
    }

    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        narbarLogo = new javax.swing.JLabel();
        logoutIconNav = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dashboardButton = new javax.swing.JLabel();
        inventoryButton = new javax.swing.JLabel();
        reportButton = new javax.swing.JLabel();
        pricingButton = new javax.swing.JLabel();
        accountButton = new javax.swing.JLabel();
        branchButton = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        dashboardGenReportTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dashboardNotifTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTable7 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jTable5 = new javax.swing.JTable();
        removeBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        accountApprovalBtn = new javax.swing.JButton();
        rejectBtn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jTable6 = new javax.swing.JTable();
        addBranchBtn = new javax.swing.JButton();
        updateBranchBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        removeBranchBtn = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1110, 650));
        setPreferredSize(new java.awt.Dimension(1100, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
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

        Border padding = BorderFactory.createEmptyBorder(0, 0, 10, 0);
        Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel2.setMinimumSize(new java.awt.Dimension(1100, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1100, 50));

        narbarLogo.setForeground(new java.awt.Color(0, 0, 0));
        ImageIcon labelLogo = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/assets/Laundry-removebg-preview.png"), 45, 50);
        narbarLogo.setIcon(labelLogo);

        logoutIconNav.setForeground(new java.awt.Color(0, 0, 0));
        logoutIconNav.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelIconLogout = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/arrow-down.png"), 25,
                25);
        logoutIconNav.setIcon(labelIconLogout);
        logoutIconNav.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutIconNav.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(new Font(null, Font.BOLD, 14));
        ImageIcon labelIconLogoutMain = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/logout.png"), 15,
                15);
        logoutItem.setIcon(labelIconLogoutMain);
        logoutItem.addActionListener(e -> {
            int confirmLogout = JOptionPane.showConfirmDialog(this, "Are you sure you want to Logout now?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirmLogout == JOptionPane.YES_OPTION) {
                new Authenticator(app).logout();
            }
        });
        popupMenu.add(logoutItem);

        logoutIconNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger() || evt.getButton() == MouseEvent.BUTTON1) {
                    popupMenu.show(logoutIconNav, evt.getX() - 75, evt.getY() + 15);
                }
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popupMenu.show(logoutIconNav, evt.getX(), evt.getY());
                }
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(narbarLogo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1015,
                                        Short.MAX_VALUE)
                                .addComponent(logoutIconNav)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(narbarLogo)
                                        .addComponent(logoutIconNav))));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 650));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(153, 153, 153)));

        dashboardButton.setForeground(new java.awt.Color(0, 0, 0));
        dashboardButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelIconDashboard = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/dashboard.png"),
                30, 30);
        dashboardButton.setIcon(labelIconDashboard);
        dashboardButton.setToolTipText("<html><b>Dashboard</b><br>Shows summary, charts, and notifications.</html>");
        dashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        dashboardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Dashboard", jPanel7);
            }
        });

        inventoryButton.setForeground(new java.awt.Color(0, 0, 0));
        inventoryButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelInventory = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/icons/material-management.png"), 30, 30);
        inventoryButton.setIcon(labelInventory);
        inventoryButton.setToolTipText("<html><b>Inventory</b></html>");
        inventoryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        inventoryButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        inventoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Inventory", jPanel8);
            }
        });

        reportButton.setForeground(new java.awt.Color(0, 0, 0));
        reportButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelReports = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/document.png"), 30, 30);
        reportButton.setIcon(labelReports);
        reportButton.setToolTipText("<html><b>Reports</b></html>");
        reportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        reportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Reports", jPanel9);
            }
        });

        accountButton.setForeground(new java.awt.Color(0, 0, 0));
        accountButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelAccounts = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/add-friend.png"), 30,
                30);
        accountButton.setIcon(labelAccounts);
        accountButton.setToolTipText("<html><b>Accounts</b></html>");
        accountButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accountButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        accountButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Accounts", jPanel10);
            }
        });

        pricingButton.setForeground(new java.awt.Color(0, 0, 0));
        pricingButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelPricing = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/stability.png"), 30,
                30);
        pricingButton.setIcon(labelPricing);
        pricingButton.setToolTipText("<html><b>Pricing Settings</b></html>");
        pricingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pricingButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        pricingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Pricing", jPanel15);
            }
        });

        branchButton.setForeground(new java.awt.Color(0, 0, 0));
        branchButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelBranch = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/git.png"), 30, 30);
        branchButton.setIcon(labelBranch);
        branchButton.setToolTipText("<html><b>Manage Branches</b></html>");
        branchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        branchButton.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));
        branchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Branches", jPanel13);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dashboardButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(inventoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(reportButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(accountButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(pricingButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addComponent(branchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(dashboardButton)
                                .addGap(20, 20, 20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inventoryButton)
                                .addGap(20, 20, 20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reportButton)
                                .addGap(20, 20, 20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accountButton)
                                .addGap(20, 20, 20)
                                .addComponent(pricingButton)
                                .addGap(20, 20, 20)
                                .addComponent(branchButton)
                                .addGap(20, 20, 20)
                                .addContainerGap(496, Short.MAX_VALUE)));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 70, 600));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setIcon(labelIconDashboard);
        jLabel1.setText("Dashboard");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(153, 153, 153)));

        DefaultTableModel modelReportsDashboard = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "T.I", "Date & Time", "Customer", "Contact", "Service Type", "Customer Payment (₱)", "Change to Customer (₱)", "₱ Total Amount", "Branch",
                    "Status"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        dashboardGenReportTable = new JTable(modelReportsDashboard) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                if (row >= 0) {
                    return "<html><div style='padding: 5px; font-size: 11px'>"
                            + "<strong>Transaction ID:</strong> " + getValueAt(row, 0)
                            + "<br><strong>Date & Time:</strong> " + getValueAt(row, 1)
                            + "<br><strong>Customer:</strong> " + getValueAt(row, 2)
                            + "<br><strong>Contact:</strong> " + getValueAt(row, 3)
                            + "<br><strong>Service Type:</strong> " + getValueAt(row, 4)
                            + "<br><strong>Customer Payment:</strong> " + getValueAt(row, 5)
                            + "<br><strong>Change to Customer:</strong> " + getValueAt(row, 6)
                            + "<br><strong>Total Amount:</strong> " + getValueAt(row, 7)
                            + "<br><strong>Branch:</strong> " + getValueAt(row, 8)
                            + "<br><strong>Status:</strong> " + getValueAt(row, 9)
                            + "</div></html>";
                }
                return null;
            }
        };

        dashboardGenReportTable.setToolTipText(null);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(dashboardGenReportTable);

        dashboardGenReportTable.setBackground(new java.awt.Color(255, 255, 255));
        dashboardGenReportTable.setRowHeight(30);
        dashboardGenReportTable.getTableHeader().setResizingAllowed(false);
        dashboardGenReportTable.getTableHeader().setReorderingAllowed(false);

        jScrollPane2.setViewportView(dashboardGenReportTable);
        dashboardGenReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dashboardGenReportTable.setDefaultRenderer(Object.class, new RowRendererReports());
        if (dashboardGenReportTable.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i <= 5; i++) {
                dashboardGenReportTable.getColumnModel().getColumn(i).setResizable(false);
            }
        }

        GenerateReports generateReportsDashboard = new GenerateReports();
        generateReportsDashboard.loadReportsRequests(modelReportsDashboard, true);

        new Timer(2000, e -> generateReportsDashboard.loadReportsRequests(modelReportsDashboard, true)).start();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        jPanel7.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 580, 280));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(153, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15));
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Notifications");
        inventoryButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelNotifications = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/icons/notification-bell.png"), 25, 25);
        jLabel4.setIcon(labelNotifications);

        dashboardNotifTable.setBackground(new java.awt.Color(255, 255, 255));
        dashboardNotifTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Time", "Message"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        DefaultTableModel model = (DefaultTableModel) dashboardNotifTable.getModel();
        dashboardNotifTable = new JTable(model) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                if (row >= 0) {
                    return "<html><div style='padding: 5px; font-size: 11px'>"
                            + "<strong>Time</strong> " + getValueAt(row, 1)
                            + "<br><strong>Message</strong> " + getValueAt(row, 2)
                            + "</div></html>";
                }
                return null;
            }
        };
        dashboardNotifTable.setRowHeight(20);
        dashboardNotifTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dashboardNotifTable.getTableHeader().setResizingAllowed(false);
        dashboardNotifTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(dashboardNotifTable);
        if (dashboardNotifTable.getColumnModel().getColumnCount() > 0) {
            dashboardNotifTable.getColumnModel().getColumn(0).setResizable(false);
            dashboardNotifTable.getColumnModel().getColumn(1).setResizable(false);
            dashboardNotifTable.getColumnModel().getColumn(2).setResizable(false);
        }
        Notifications notifications = new Notifications();

        notifications.loadNotificationsRequests(model, dashboardNotifTable);

        new Timer(2000, e -> notifications.loadNotificationsRequests(model, dashboardNotifTable))
                .start();

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12));
        ImageIcon labelDeleteNotif = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/trash.png"), 25,
                25);
        jButton3.setIcon(labelDeleteNotif);
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setToolTipText("<html><b>Delete notification</b></html>");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(e -> notifications.deleteNotification(dashboardNotifTable, model));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                        Short.MAX_VALUE)));
        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 445, 265));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LocalDate today = LocalDate.now();
        String currentMonth = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int currentYear = today.getYear();

        String[] months = new DateFormatSymbols().getMonths();
        JComboBox<String> monthSelector = new JComboBox<>();
        monthSelector.addItem("All");
        for (int i = 0; i < 12; i++) {
            monthSelector.addItem(months[i]);
        }
        monthSelector.setSelectedItem(currentMonth);

        JComboBox<Integer> yearSelector = new JComboBox<>();
        for (int y = 2025; y <= 2050; y++) {
            yearSelector.addItem(y);
        }
        yearSelector.setSelectedItem(currentYear);

        jPanel7.add(new JLabel("Month:"), new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 14, 50, 25));
        jPanel7.add(monthSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 14, 100, 25));

        jPanel7.add(new JLabel("Year:"), new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 14, 40, 25));
        jPanel7.add(yearSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 14, 70, 25));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Sales", // Chart title
                "Month", // X-axis Label
                "Sales (₱)", // Y-axis Label
                dataset, // Dataset
                PlotOrientation.HORIZONTAL, // Orientation
                true, // Legend
                true, // Tooltips
                true // URLs
        );
        new Sales().loadSalesChartData(dataset, null, null);
        new Timer(500, e -> {
            String selectedMonth = (String) monthSelector.getSelectedItem();
            Object selectedYearObj = yearSelector.getSelectedItem();
            Integer selectedYear = null;

            if (selectedMonth != null && "All".equalsIgnoreCase(selectedMonth)) {
                selectedMonth = null;
            }

            if (selectedYearObj instanceof Integer) {
                selectedYear = (Integer) selectedYearObj;
            }

            new Sales().loadSalesChartData(dataset, selectedMonth, selectedYear);
        }).start();

        barChart.setBackgroundPaint(new Color(230, 240, 255));

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(0, 0, 0));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        Color[] colors = {
            new Color(38, 166, 180),
            new Color(255, 183, 100),
            new Color(103, 58, 183),
            new Color(255, 112, 80)
        };

        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, colors[i]);
        }

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);

        ChartPanel chartPanel = new ChartPanel(barChart);

        jPanel3.add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1025, 235));
        chartPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(10, 10, 10)));

        jPanel7.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 240));

        jTabbedPane1.addTab("Dashboard", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DefaultTableModel modelInventory = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Item Name", "Quantity", "Threshold", "Branch", "Status", "Last Restocked"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable4 = new JTable(modelInventory) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                if (row >= 0) {
                    return "<html><div style='padding: 5px; font-size: 11px'>"
                            + "<strong>Item Name:</strong> " + getValueAt(row, 0)
                            + "<br><strong>Quantity:</strong> " + getValueAt(row, 1)
                            + "<br><strong>Threshold:</strong> " + getValueAt(row, 2)
                            + "<br><strong>Branch:</strong> " + getValueAt(row, 3)
                            + "<br><strong>Status:</strong> " + getValueAt(row, 4)
                            + "<br><strong>Last Restocked:</strong> " + getValueAt(row, 5)
                            + "</div></html>";
                }
                return null;
            }
        };

        jTable4.setToolTipText(null);
        jTable4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(jTable4);

        jTable4.setBackground(new java.awt.Color(255, 255, 255));
        jTable4.setRowHeight(30);
        jTable4.getTableHeader().setResizingAllowed(false);
        jTable4.getTableHeader().setReorderingAllowed(false);

        jScrollPane4.setViewportView(jTable4);

        if (jTable4.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i <= 5; i++) {
                jTable4.getColumnModel().getColumn(i).setResizable(false);
            }

            jTable4.getColumnModel().getColumn(0).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/box.png"));
            jTable4.getColumnModel().getColumn(1).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/bulk.png"));
            jTable4.getColumnModel().getColumn(2).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/type.png"));
            jTable4.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/git.png"));
            jTable4.getColumnModel().getColumn(4).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
            jTable4.getColumnModel().getColumn(5)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/calendar.png"));
        }

        jTable4.setDefaultRenderer(Object.class, new RowRendererInventory());

        Inventory inventory = new Inventory();
        jButton4.addActionListener(e -> inventory.newInventory(modelInventory));
        jButton5.addActionListener(e -> inventory.updateInventory(jTable4, modelInventory));
        jButton6.addActionListener(e -> inventory.deleteInventory(jTable4, modelInventory));
        inventory.loadInventoryRequests(modelInventory);

        new Timer(2000, e -> inventory.loadInventoryRequests(modelInventory)).start();

        jPanel8.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1025, 445));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Inventory");
        jLabel5.setIcon(labelInventory);
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 10));

        jButton4.setText("Add Item");
        jButton4.setFocusPainted(false);
        ImageIcon labelAddItem = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/plus.png"), 15, 15);
        jButton4.setIcon(labelAddItem);
        jPanel8.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton5.setText("Update Item");
        jButton5.setFocusPainted(false);
        ImageIcon labelUpdateItem = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/browser.png"), 15,
                15);
        jButton5.setIcon(labelUpdateItem);
        jPanel8.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, 30));
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBox4.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox4.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jComboBox4.setForeground(new java.awt.Color(51, 51, 51));
        jPanel8.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 60, 190, 30));

        jComboBox3.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox3.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jComboBox3.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox3.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[]{"Status", "Normal", "Low", "Out of Stock"}));
        jComboBox3.addActionListener(e -> {
            String selected = jComboBox3.getSelectedItem().toString().toLowerCase();

            switch (selected) {
                case "low" -> {
                    jComboBox3.setBackground(new Color(247, 147, 59));
                    jComboBox3.setForeground(Color.BLACK);
                }
                case "normal" -> {
                    jComboBox3.setBackground(new Color(185, 217, 30));
                    jComboBox3.setForeground(Color.BLACK);
                }
                case "out of stock" -> {
                    jComboBox3.setBackground(new Color(242, 104, 104));
                    jComboBox3.setForeground(Color.BLACK);
                }
                default -> {
                    jComboBox3.setBackground(Color.WHITE);
                    jComboBox3.setForeground(Color.BLACK);
                }
            }
        });

        jPanel8.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 60, -1, 30));

        TableRowSorter<DefaultTableModel> inventorySorter = new TableRowSorter<>(
                (DefaultTableModel) jTable4.getModel());
        jTable4.setRowSorter(inventorySorter);

        final String[] selectedBranch = {"select branch"};
        final String[] selectedStatus = {"status"};

        jComboBox4.addActionListener(e -> {
            selectedBranch[0] = jComboBox4.getSelectedItem().toString().trim().toLowerCase();
            applyCombinedFilter(inventorySorter, selectedBranch[0], selectedStatus[0]);
        });

        jComboBox3.addActionListener(e -> {
            selectedStatus[0] = jComboBox3.getSelectedItem().toString().trim().toLowerCase();
            applyCombinedFilter(inventorySorter, selectedBranch[0], selectedStatus[0]);
        });

        jButton6.setText("Remove Item");
        jButton6.setFocusPainted(false);
        ImageIcon labelDeleteItem = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/trash.png"), 15, 15);
        jButton6.setIcon(labelDeleteItem);
        jPanel8.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 60, -1, 30));
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DefaultTableModel modelReports = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Transaction ID", "Date & Time", "Customer", "Contact", "Service Type", "Customer Payment (₱)", "Change to Customer (₱)", "₱ Total Amount",
                    "Branch", "Status"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable5 = new JTable(modelReports) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                if (row >= 0) {
                    return "<html><div style='padding: 5px; font-size: 11px'>"
                            + "<strong>Transaction ID:</strong> " + getValueAt(row, 0)
                            + "<br><strong>Date & Time:</strong> " + getValueAt(row, 1)
                            + "<br><strong>Customer:</strong> " + getValueAt(row, 2)
                            + "<br><strong>Contact:</strong> " + getValueAt(row, 3)
                            + "<br><strong>Service Type:</strong> " + getValueAt(row, 4)
                            + "<br><strong>Customer Payment:</strong> " + getValueAt(row, 5)
                            + "<br><strong>Change to Customer:</strong> " + getValueAt(row, 6)
                            + "<br><strong>Total Amount:</strong> " + getValueAt(row, 7)
                            + "<br><strong>Branch:</strong> " + getValueAt(row, 8)
                            + "<br><strong>Status:</strong> " + getValueAt(row, 9)
                            + "</div></html>";
                }
                return null;
            }
        };

        jTable5.setToolTipText(null);
        jTable5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(jTable5);

        jTable5.setBackground(new java.awt.Color(255, 255, 255));
        jTable5.setRowHeight(30);
        jTable5.getTableHeader().setResizingAllowed(false);
        jTable5.getTableHeader().setReorderingAllowed(false);

        jScrollPane5.setViewportView(jTable5);

        if (jTable5.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i <= 6; i++) {
                jTable5.getColumnModel().getColumn(i).setResizable(false);
            }

            jTable5.getColumnModel().getColumn(0).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/issue.png"));
            jTable5.getColumnModel().getColumn(1)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/calendar.png"));
            jTable5.getColumnModel().getColumn(2).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/user.png"));
            jTable5.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/mail.png"));
            jTable5.getColumnModel().getColumn(4)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/customer-service.png"));
            jTable5.getColumnModel().getColumn(5).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/money.png"));
            jTable5.getColumnModel().getColumn(6).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/payment-method.png"));
            jTable5.getColumnModel().getColumn(7).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/gross.png"));
            jTable5.getColumnModel().getColumn(8).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/git.png"));
            jTable5.getColumnModel().getColumn(9).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
        }

        jTable5.setDefaultRenderer(Object.class, new RowRendererReports());

        GenerateReports generateReports = new GenerateReports();
        generateReports.loadReportsRequests(modelReports, false);

        new Timer(2000, e -> generateReports.loadReportsRequests(modelReports, false)).start();

        jPanel9.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1025, 445));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Reports");
        jLabel7.setIcon(labelReports);
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 10));

        jComboBox5.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox5.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jComboBox5.setForeground(new java.awt.Color(51, 51, 51));
        jPanel9.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, 190, 35));

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jComboBox1.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Status", "Pending", "Canceled", "Pickup", "Delivered", "Paid"}));
        jComboBox1.addActionListener(e -> {
            String selected = jComboBox1.getSelectedItem().toString().toLowerCase();

            switch (selected) {
                case "pending" -> {
                    jComboBox1.setBackground(new Color(255, 243, 205));
                    jComboBox1.setForeground(new Color(133, 100, 4));
                }
                case "canceled" -> {
                    jComboBox1.setBackground(new Color(248, 215, 218));
                    jComboBox1.setForeground(new Color(114, 28, 36));
                }
                case "pickup" -> {
                    jComboBox1.setBackground(new Color(209, 236, 241));
                    jComboBox1.setForeground(new Color(12, 84, 96));
                }
                case "delivered" -> {
                    jComboBox1.setBackground(new Color(212, 237, 218));
                    jComboBox1.setForeground(new Color(21, 87, 36));
                }
                case "paid" -> {
                    jComboBox1.setBackground(new Color(209, 242, 235));
                    jComboBox1.setForeground(new Color(17, 120, 100));
                }
                default -> {
                    jComboBox1.setBackground(Color.WHITE);
                    jComboBox1.setForeground(Color.BLACK);
                }
            }
        });

        jPanel9.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 60, -1, 35));

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        setInputBorderColor(jTextField1, "🔎 Search by ID", Color.BLACK);

        JToggleButton toggleReportCancelled = new JToggleButton("Hide cancelled reports");
        ImageIcon labelShowToggleBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/check-mark.png"),
                20, 20);
        ImageIcon labelHideToggleBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/remove.png"), 20,
                20);
        toggleReportCancelled.setIcon(labelHideToggleBtn);
        toggleReportCancelled.setContentAreaFilled(false);
        toggleReportCancelled.setFocusPainted(false);

        jPanel9.add(toggleReportCancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 190, 35));

        TableRowSorter<DefaultTableModel> sorterReports = new TableRowSorter<>((DefaultTableModel) jTable5.getModel());
        jTable5.setRowSorter(sorterReports);

        final String[] selectedStatusReport = {"status"};
        final String[] selectBranchReport = {"Select branch"};
        final String[] enteredID = {""};
        final boolean[] hideCanceledReports = {false};

        toggleReportCancelled.addActionListener(e -> {
            hideCanceledReports[0] = toggleReportCancelled.isSelected();

            toggleReportCancelled.setIcon(hideCanceledReports[0] ? labelShowToggleBtn : labelHideToggleBtn);
            toggleReportCancelled.setText(hideCanceledReports[0] ? "Hide cancelled reports" : "Show cancelled reports");

            applyReportsFilter(
                    sorterReports,
                    selectedStatusReport[0],
                    enteredID[0],
                    selectBranchReport[0],
                    hideCanceledReports[0]);
        });

        jComboBox1.addActionListener(e -> {
            selectedStatusReport[0] = jComboBox1.getSelectedItem().toString().trim().toLowerCase();
            applyReportsFilter(sorterReports, selectedStatusReport[0], enteredID[0], selectBranchReport[0],
                    hideCanceledReports[0]);
        });

        jComboBox5.addActionListener(e -> {
            selectBranchReport[0] = jComboBox5.getSelectedItem().toString().trim().toLowerCase();
            applyReportsFilter(sorterReports, selectedStatusReport[0], enteredID[0], selectBranchReport[0],
                    hideCanceledReports[0]);
        });

        Timer typingTimer = new Timer(500, evt -> {
            enteredID[0] = jTextField1.getText().trim().toLowerCase();
            applyReportsFilter(sorterReports, selectedStatusReport[0], enteredID[0], selectBranchReport[0],
                    hideCanceledReports[0]);

            if (jTable5.getRowCount() > 0) {
                setInputBorderColor(jTextField1, "🔎 Search by ID", Color.BLACK);
            } else {
                setInputBorderColor(jTextField1, "❌ No report found", Color.RED);
            }
        });
        typingTimer.setRepeats(false);

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                typingTimer.restart();
            }

            public void removeUpdate(DocumentEvent e) {
                typingTimer.restart();
            }

            public void changedUpdate(DocumentEvent e) {
                typingTimer.restart();
            }
        });

        jPanel9.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 40));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountApprovalBtn.setBackground(new java.awt.Color(51, 255, 255));
        accountApprovalBtn.setFont(new java.awt.Font("Segoe UI", 1, 12));
        accountApprovalBtn.setForeground(new java.awt.Color(51, 51, 51));
        accountApprovalBtn.setFocusPainted(false);
        accountApprovalBtn.setText("Approve");

        jPanel10.add(accountApprovalBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 29));

        rejectBtn.setBackground(new java.awt.Color(204, 204, 204));
        rejectBtn.setFont(new java.awt.Font("Segoe UI", 1, 12));
        rejectBtn.setForeground(new java.awt.Color(51, 51, 51));
        rejectBtn.setText("Reject");
        rejectBtn.setFocusPainted(false);
        jPanel10.add(rejectBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 120, 29));

        DefaultTableModel modelAccountTABLE = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Firstname", "Lastname", "Email address", "Branch", "Gender", "Status"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable1 = new JTable(modelAccountTABLE) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                if (row >= 0) {
                    return "<html><div style='padding: 8px;'>"
                            + "<strong>Firstname:</strong> " + getValueAt(row, 0)
                            + "<br><strong>Lastname:</strong> " + getValueAt(row, 1)
                            + "<br><strong>Email:</strong> " + getValueAt(row, 2)
                            + "<br><strong>Branch:</strong> " + getValueAt(row, 3)
                            + "<br><strong>Gender:</strong> " + getValueAt(row, 4)
                            + "<br><strong>Status:</strong> " + getValueAt(row, 5)
                            + "</div></html>";
                }
                return null;
            }
        };

        jTable1.setToolTipText(null);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(jTable1);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setRowHeight(30);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jScrollPane1.setViewportView(jTable1);

        if (jTable1.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i <= 5; i++) {
                jTable1.getColumnModel().getColumn(i).setResizable(false);
            }

            jTable1.getColumnModel().getColumn(0).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/user.png"));
            jTable1.getColumnModel().getColumn(1).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/user.png"));
            jTable1.getColumnModel().getColumn(2).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/mail.png"));
            jTable1.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/git.png"));
            jTable1.getColumnModel().getColumn(4)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/equality.png"));
            jTable1.getColumnModel().getColumn(5).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
        }

        jTable1.setDefaultRenderer(Object.class, new RowRenderer());

        Accounts accountsHelper = new Accounts();
        accountsHelper.loadAccountsRequests(modelAccountTABLE);

        new Timer(2000, e -> accountsHelper.loadAccountsRequests(modelAccountTABLE)).start();

        ImageIcon labelApproveBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/check-mark.png"), 25,
                25);
        ImageIcon labelRejectBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/remove.png"), 20, 20);
        accountApprovalBtn.setIcon(labelApproveBtn);
        rejectBtn.setIcon(labelRejectBtn);

        ImageIcon labelRemoveBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/delete.png"), 20, 20);
        removeBtn.setIcon(labelRemoveBtn);

        accountApprovalBtn.addActionListener(
                e -> new Authenticator(app).approveUser(jTable1, (DefaultTableModel) jTable1.getModel()));
        rejectBtn.addActionListener(
                e -> new Authenticator(app).rejectUser(jTable1, (DefaultTableModel) jTable1.getModel()));
        removeBtn.addActionListener(
                e -> new Authenticator(app).removeUser(jTable1, (DefaultTableModel) jTable1.getModel()));

        jPanel10.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1025, 455));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Account/Request");
        jLabel6.setIcon(labelAccounts);
        jPanel10.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 10));

        jComboBox2.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox2.setForeground(new java.awt.Color(0, 0, 0));

        branches.updateBranchesLive(jComboBox5);
        branches.updateBranchesLive(jComboBox4);
        branches.updateBranchesLive(jComboBox2);

        jComboBox2.addActionListener(e -> {

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTable1.getModel());
            jTable1.setRowSorter(sorter);

            String searchText = jComboBox2.getSelectedItem().toString().trim().toLowerCase();
            if (searchText.equals("select branch")) {
                sorter.setRowFilter(null);
            } else {
                RowFilter<TableModel, Integer> filter = new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                        for (int col : new int[]{0, 1, 3, 4, 5}) {
                            String value = entry.getStringValue(col).trim().toLowerCase();
                            if (value.contains(searchText)) {
                                return true;
                            }
                        }
                        return false;
                    }
                };
                sorter.setRowFilter(filter);
                if (jTable1.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "No user found", "No data", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jPanel10.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(825, 60, 190, 30));

        removeBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeBtn.setFont(new java.awt.Font("Segoe UI", 1, 12));
        removeBtn.setFocusPainted(false);
        removeBtn.setForeground(new java.awt.Color(51, 51, 51));
        removeBtn.setText("Remove");
        jPanel10.add(removeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 120, 29));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setIcon(labelBranch);
        jLabel13.setText("Branches");
        jPanel13.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 10));

        jTable6.setBackground(new java.awt.Color(255, 255, 255));
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Branch ID", "Name"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable6.getTableHeader().setResizingAllowed(false);
        jTable6.getTableHeader().setReorderingAllowed(false);
        jTable6.setRowHeight(30);
        jTable6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setResizable(false);
        }
        new Branches().loadBranchesRequests((DefaultTableModel) jTable6.getModel());

        jPanel13.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1025, 455));

        addBranchBtn.setBackground(new java.awt.Color(0, 255, 255));
        addBranchBtn.setForeground(new java.awt.Color(51, 51, 51));
        addBranchBtn.setText("New branch");
        jPanel13.add(addBranchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 30));
        addBranchBtn.addActionListener(e -> new Branches().addBranch(jTable6, (DefaultTableModel) jTable6.getModel()));
        addBranchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color dangerBg = new Color(255, 235, 238);
        Color dangerFg = new Color(183, 28, 28);
        Color dangerBorder = new Color(211, 47, 47);

        updateBranchBtn.setBackground(dangerBg);
        updateBranchBtn.setForeground(dangerFg);
        updateBranchBtn.setText("Update Branch");
        updateBranchBtn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, dangerBorder));
        updateBranchBtn.setToolTipText(
                "<html><b>Warning:</b> Changing a branch<br>may cause issues with employee logins.<br>They must request a new account if affected.</html>");
        jPanel13.add(updateBranchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 150, 30));
        updateBranchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBranchBtn
                .addActionListener(e -> new Branches().editBranch(jTable6, (DefaultTableModel) jTable6.getModel()));

        removeBranchBtn.setBackground(new Color(220, 53, 69));
        removeBranchBtn.setForeground(Color.WHITE);
        removeBranchBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 35, 51), 2));
        removeBranchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeBranchBtn.setFocusPainted(false);
        removeBranchBtn.setText("Remove Branch");
        removeBranchBtn.setToolTipText(
                "<html><b>Danger:</b> Removing a branch<br>will disconnect any linked employees.<br>They must register again to log in.</html>");
        jPanel13.add(removeBranchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 150, 30));
        removeBranchBtn
                .addActionListener(e -> new Branches().removeBranch(jTable6, (DefaultTableModel) jTable6.getModel()));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        ImageIcon labelIconPricingSet = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/stability.png"), 35,
                35);
        jLabel14.setIcon(labelIconPricingSet);
        jLabel14.setText("Pricing Settings");
        jPanel15.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel16Layout.setVerticalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 10));

        jTable7.setBackground(new java.awt.Color(255, 255, 255));
        jTable7.setToolTipText(null);
        jTable7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable7.setRowHeight(30);
        javax.swing.ToolTipManager.sharedInstance().registerComponent(jTable7);
        jTable7.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Service Name", "Price per Kilo (₱)", "Branch", "Status"
                }) {
            Class[] types = new Class[]{
                String.class, String.class, String.class, Boolean.class
            };

            boolean[] canEdit = new boolean[]{
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jTable7.getTableHeader().setResizingAllowed(false);
        jTable7.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable7);
        RowRendererPricing rendererPricing = new RowRendererPricing();
        jTable7.setDefaultRenderer(Object.class, rendererPricing);
        jTable7.setDefaultRenderer(Boolean.class, rendererPricing);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(0).setResizable(false);
            jTable7.getColumnModel().getColumn(1).setResizable(false);
            jTable7.getColumnModel().getColumn(2).setResizable(false);
            jTable7.getColumnModel().getColumn(3).setResizable(false);

            jTable7.getColumnModel().getColumn(0)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/customer-service.png"));
            jTable7.getColumnModel().getColumn(1)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/git.png"));
            jTable7.getColumnModel().getColumn(2)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/cash-flow.png"));
            jTable7.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
        }
        jTable7.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 3) {
                String serviceName = (String) jTable7.getModel().getValueAt(row, 0);
                String pricing = (String) jTable7.getModel().getValueAt(row, 1);
                String branch = (String) jTable7.getModel().getValueAt(row, 2);
                boolean isAvailable = (Boolean) jTable7.getModel().getValueAt(row, 3);
                String status = isAvailable ? "1" : "0";

                new Thread(() -> {
                    String params = "action=update_status&client_fetch_token_request=;;mslaundryshop2025;;"
                            + "&service_name=" + serviceName + "&price_per_kilo=" + pricing + "&status=" + status + "&branch=" + branch;
                    HttpURLConnection conn = server.createPostConnection("get_prices.php", params);
                    parseJsonResponse(conn);
                    jTable7.clearSelection();
                }).start();
            }
        });
        new Timer(1000, e -> new Pricing().loadServicePricesLive((DefaultTableModel) jTable7.getModel(), jTable7))
                .start();

        jPanel15.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1027, 455));

        jButton12.setBackground(new java.awt.Color(0, 255, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jButton12.setForeground(new java.awt.Color(51, 51, 51));
        jButton12.setText("New Service");
        jButton12.setToolTipText("Add a new service (currently unavailable).");
        jButton12.setEnabled(false);
        jPanel15.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, 30));

        jButton13.setBackground(new java.awt.Color(225, 225, 225));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jButton13.setForeground(new java.awt.Color(51, 51, 51));
        jButton13.setText("Update Pricing");
        jButton13.addActionListener(e -> new Pricing().editPricing((DefaultTableModel) jTable7.getModel(), jTable7));
        jPanel15.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 130, 30));

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jTextField2.setForeground(new java.awt.Color(51, 51, 51));
        setInputBorderColor(jTextField2, "🔎 Search Item", Color.BLACK);
        jPanel15.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 56, 155, 38));

        branches.updateBranchesLive(jComboBox6);

        TableRowSorter<DefaultTableModel> servicesSorter = new TableRowSorter<>((DefaultTableModel) jTable7.getModel());
        jTable7.setRowSorter(servicesSorter);

        final String[] selectedBranchService = {"select branch"};
        final String[] enteredServiceName = {""};

        jComboBox6.addActionListener(e -> {
            selectedBranchService[0] = jComboBox6.getSelectedItem().toString().trim();
            applyServiceBranchAndNameFilter(servicesSorter, selectedBranchService[0], enteredServiceName[0]);
        });

        Timer typingTimerServices = new Timer(500, evt -> {
            enteredServiceName[0] = jTextField2.getText().trim();
            applyServiceBranchAndNameFilter(servicesSorter, selectedBranchService[0], enteredServiceName[0]);

            if (jTable7.getRowCount() > 0) {
                setInputBorderColor(jTextField2, "🔎 Search Item", Color.BLACK);
            } else {
                setInputBorderColor(jTextField2, "❌ No item found", Color.RED);
            }
        });
        typingTimerServices.setRepeats(false);

        jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                typingTimerServices.restart();
            }

            public void removeUpdate(DocumentEvent e) {
                typingTimerServices.restart();
            }

            public void changedUpdate(DocumentEvent e) {
                typingTimerServices.restart();
            }
        });

        jPanel15.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 60, 160, 30));

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 45, 1030, -1));

    }

    private void applyServiceBranchAndNameFilter(TableRowSorter<DefaultTableModel> sorter, String branchFilter, String serviceNameFilter) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        boolean hasBranchFilter = branchFilter != null && !branchFilter.equalsIgnoreCase("select branch");
        boolean hasServiceNameFilter = serviceNameFilter != null && !serviceNameFilter.isEmpty();

        if (hasBranchFilter) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(branchFilter) + "$", 2));
        }

        if (hasServiceNameFilter) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(serviceNameFilter), 0));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
            if (jTable7.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "No item found", "No data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyReportsFilter(TableRowSorter<DefaultTableModel> sorter, String statusFilter, String idFilter,
            String branchFilter, boolean hideCanceled) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        boolean hasStatus = statusFilter != null && !statusFilter.equalsIgnoreCase("status");
        boolean hasID = idFilter != null && !idFilter.isEmpty();
        boolean hasBranch = branchFilter != null && !branchFilter.equalsIgnoreCase("select branch");

        if (hasStatus) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(statusFilter), 7));
        }
        if (hasID) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(idFilter), 0));
        }
        if (hasBranch) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(branchFilter), 6));
        }

        if (hideCanceled) {
            filters.add(RowFilter.notFilter(RowFilter.regexFilter("(?i)Canceled", 7)));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
            if (jTable5.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "No report found", "No data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyCombinedFilter(TableRowSorter<DefaultTableModel> sorter, String branchFilter,
            String statusFilter) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        boolean hasBranchFilter = branchFilter != null && !branchFilter.equalsIgnoreCase("select branch");
        boolean hasStatusFilter = statusFilter != null && !statusFilter.equalsIgnoreCase("status");

        if (hasBranchFilter) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(branchFilter) + "$", 3));
        }

        if (hasStatusFilter) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(statusFilter) + "$", 4));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
            if (jTable4.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "No item found", "No data", JOptionPane.ERROR_MESSAGE);
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

    private javax.swing.JButton accountApprovalBtn;
    private javax.swing.JButton rejectBtn;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton addBranchBtn;
    private javax.swing.JButton updateBranchBtn;
    private javax.swing.JLabel pricingButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel logoutIconNav;
    private javax.swing.JLabel narbarLogo;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel dashboardButton;
    private javax.swing.JLabel inventoryButton;
    private javax.swing.JLabel reportButton;
    private javax.swing.JLabel accountButton;
    private javax.swing.JLabel branchButton;
    private javax.swing.JButton removeBranchBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable dashboardGenReportTable;
    private javax.swing.JTable dashboardNotifTable;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
