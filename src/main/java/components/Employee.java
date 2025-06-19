package src.main.java.components;

import src.main.java.App;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import src.main.java.auth.Authenticator;
import src.main.java.auth.session.java.FetchUser;
import src.main.java.components.helper.GenerateReports;
import src.main.java.components.helper.Inventory;
import src.main.java.components.helper.Sales;
import src.utils.CustomBorder;
import src.utils.CustomHeaderRenderer;
import src.utils.CustomImageResizer;
import src.utils.RowRendererInventory;
import src.utils.RowRendererReports;

public class Employee extends JPanel implements CustomBorder {

    JSONObject user = FetchUser.getUserData();
    static TableRowSorter<DefaultTableModel> sorter;

    private App app;

    public Employee(App app) {

        this.app = app;

        if (new Authenticator(app).isAuthenticated() == null) {
            new Authenticator(app).logout();
        }

        initComponents();
    }

    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        logoutIconNav = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        jPanel2.setMinimumSize(new java.awt.Dimension(1100, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1100, 50));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        ImageIcon labelLogo = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/assets/Laundry-removebg-preview.png"), 45, 50);
        jLabel3.setIcon(labelLogo);

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

        logoutIconNav.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(logoutIconNav, e.getX() - 75, e.getY() + 15);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1015,
                                        Short.MAX_VALUE)
                                .addComponent(logoutIconNav)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(logoutIconNav))
                                .addContainerGap(17, Short.MAX_VALUE)));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 50));

        jPanel1.setBackground(new java.awt.Color(234, 234, 234));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(153, 153, 153)));
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 650));

        String fullName = user.getString("firstname") + " " + user.getString("lastname");

        String branch = user.getString("branch");
        ImageIcon labelBranch = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/git.png"), 25, 25);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 19));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText(truncateText(fullName, 17));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12));
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText(truncateText(branch, 15));
        jLabel4.setIcon(labelBranch);

        jPanel3.setBackground(new java.awt.Color(234, 234, 234));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4))
                                .addContainerGap(19, Short.MAX_VALUE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(496, Short.MAX_VALUE)));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 180, 600));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20));
        jLabel8.setForeground(new java.awt.Color(30, 30, 30));
        ImageIcon labelIconSummary = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/analysis.png"), 35,
                35);
        jLabel8.setIcon(labelIconSummary);
        jLabel8.setText("Total Sales Overview");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, -1, -1));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 9, Short.MAX_VALUE));

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

        jPanel7.add(new JLabel("Month:"), new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 14, 50, 25));
        jPanel7.add(monthSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 14, 100, 25));

        jPanel7.add(new JLabel("Year:"), new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 14, 40, 25));
        jPanel7.add(yearSelector, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 14, 70, 25));

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
        new Sales().loadSalesChartDataSpecBranch(dataset, user.getString("branch"), null, null);

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

            new Sales().loadSalesChartDataSpecBranch(dataset, user.getString("branch"), selectedMonth, selectedYear);
        }).start();

        ActionListener filterListener = e -> {
            String selectedMonth = (String) monthSelector.getSelectedItem();
            Object selectedYearObj = yearSelector.getSelectedItem();
            Integer selectedYear = null;

            if (selectedMonth != null && "All".equalsIgnoreCase(selectedMonth)) {
                selectedMonth = null;
            }

            if (selectedYearObj instanceof Integer) {
                selectedYear = (Integer) selectedYearObj;
            }

            new Sales().loadSalesChartDataSpecBranch(dataset, user.getString("branch"), selectedMonth, selectedYear);
        };

        monthSelector.addActionListener(filterListener);
        yearSelector.addActionListener(filterListener);

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

        jPanel7.add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 910, 460));

        jPanel7.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 920, 10));

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 560));

        jTabbedPane1.addTab("Summary", jPanel4);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable5.setBackground(new java.awt.Color(255, 255, 255));
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Transaction ID", "Date & Time", "Customer", "Contact", "Servive Type", "Customer Payment (₱)", "Change to Customer (₱)", "₱ Total Amount",
                    "Branch", "Status"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        String role = user.getString("role");

        jTable5 = new JTable(jTable5.getModel()) {
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

        jTable5.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                handleSelection(index0, index1, this);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                handleSelection(index0, index1, this);
            }

            private void handleSelection(int index0, int index1, DefaultListSelectionModel model) {
                int modelRow = jTable5.convertRowIndexToModel(index0);
                Object statusObj = jTable5.getModel().getValueAt(modelRow, 9);
                String status = statusObj != null
                        ? statusObj.toString().trim().toLowerCase()
                        : "";

                if ("paid".equals(status) && "employee".equals(role)) {
                    jTable5.clearSelection();
                    return;
                }
                super.setSelectionInterval(index0, index1);
            }
        });

        sorter = new TableRowSorter<>((DefaultTableModel) jTable5.getModel());
        jTable5.setRowSorter(sorter);
        jTable5.setRowHeight(30);
        jTable5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable5.getTableHeader().setResizingAllowed(false);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setResizable(false);
            jTable5.getColumnModel().getColumn(1).setResizable(false);
            jTable5.getColumnModel().getColumn(2).setResizable(false);
            jTable5.getColumnModel().getColumn(3).setResizable(false);
            jTable5.getColumnModel().getColumn(4).setResizable(false);
            jTable5.getColumnModel().getColumn(5).setResizable(false);
            jTable5.getColumnModel().getColumn(6).setResizable(false);
            jTable5.getColumnModel().getColumn(7).setResizable(false);
            jTable5.getColumnModel().getColumn(8).setResizable(false);
            jTable5.getColumnModel().getColumn(9).setResizable(false);

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
        jButton1.addActionListener(
                e -> new GenerateReports(app).newReport((DefaultTableModel) jTable5.getModel(), jTable5));
        jButton2.addActionListener(
                e -> new GenerateReports(app).updateReport(jTable5, (DefaultTableModel) jTable5.getModel()));

        DefaultTableModel modelReports = (DefaultTableModel) jTable5.getModel();
        GenerateReports generateReports = new GenerateReports();

        generateReports.loadReportsRequests(modelReports, false);

        int delayMs = 2000;

        new Timer(delayMs, e -> generateReports.loadReportsRequests(modelReports, false))
                .start();

        jTable5.setDefaultRenderer(Object.class, new RowRendererReports());

        jPanel9.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 915, 420));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Transactions");
        ImageIcon labelIconTransaction = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/cash-flow.png"),
                30, 30);
        jLabel7.setIcon(labelIconTransaction);
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

        JToggleButton toggleReportCancelled = new JToggleButton("Hide cancelled reports");
        ImageIcon labelShowToggleBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/check-mark.png"),
                20, 20);
        ImageIcon labelHideToggleBtn = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/remove.png"), 20,
                20);
        toggleReportCancelled.setIcon(labelHideToggleBtn);
        toggleReportCancelled.setContentAreaFilled(false);
        toggleReportCancelled.setFocusPainted(false);

        jPanel9.add(toggleReportCancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 190, 30));

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

        TableRowSorter<DefaultTableModel> sorterEmployee = new TableRowSorter<>((DefaultTableModel) jTable5.getModel());
        jTable5.setRowSorter(sorterEmployee);

        final String[] selectedStatus = {"status"};
        final String[] enteredID = {""};
        final boolean[] hideCanceledReports = {false};

        jComboBox1.addActionListener(e -> {
            selectedStatus[0] = jComboBox1.getSelectedItem().toString().trim().toLowerCase();
            applyReportsFilter(sorterEmployee, selectedStatus[0], enteredID[0], hideCanceledReports[0]);
        });

        Timer typingTimer = new Timer(500, null);
        typingTimer.setRepeats(false);

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                typingTimer.restart();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        typingTimer.addActionListener(e -> {
            enteredID[0] = jTextField1.getText().trim().toLowerCase();
            applyReportsFilter(sorterEmployee, selectedStatus[0], enteredID[0], hideCanceledReports[0]);

            if (enteredID[0].isEmpty()) {
                setInputBorderColor(jTextField1, "🔎 Search by ID", Color.BLACK);
                jComboBox1.setSelectedIndex(0);
                applyReportsFilter(sorterEmployee, "status", "", hideCanceledReports[0]);
                return;
            }

            if (jTable5.getRowCount() > 0) {
                setInputBorderColor(jTextField1, "🔎 Search by ID", Color.BLACK);
            } else {
                setInputBorderColor(jTextField1, "❌ No transaction found", Color.RED);
            }
        });
        toggleReportCancelled.addActionListener(e -> {
            hideCanceledReports[0] = toggleReportCancelled.isSelected();

            toggleReportCancelled.setIcon(hideCanceledReports[0] ? labelShowToggleBtn : labelHideToggleBtn);
            toggleReportCancelled.setText(hideCanceledReports[0] ? "Hide cancelled reports" : "Show cancelled reports");

            applyReportsFilter(sorterEmployee, selectedStatus[0], enteredID[0], hideCanceledReports[0]);
        });

        jPanel9.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(545, 70, -1, 30));

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        setInputBorderColor(jTextField1, "🔎 Search by ID", Color.BLACK);

        jPanel9.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 40));

        jButton1.setText("New Transanction");
        jButton1.setBackground(Color.CYAN);
        jPanel9.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, -1, 30));
        jButton2.setText("Update Status");
        jPanel9.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, -1, 30));

        jTabbedPane1.addTab("Transactions", jPanel9);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable4.setBackground(new java.awt.Color(255, 255, 255));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        });
        jTable4.getTableHeader().setResizingAllowed(false);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.setRowHeight(30);
        jTable4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setResizable(false);
            jTable4.getColumnModel().getColumn(4).setResizable(false);
            jTable4.getColumnModel().getColumn(5).setResizable(false);

            jTable4.getColumnModel().getColumn(0).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/box.png"));
            jTable4.getColumnModel().getColumn(1).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/bulk.png"));
            jTable4.getColumnModel().getColumn(2).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/type.png"));
            jTable4.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/git.png"));
            jTable4.getColumnModel().getColumn(4).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
            jTable4.getColumnModel().getColumn(5)
                    .setHeaderRenderer(new CustomHeaderRenderer("./src/icons/calendar.png"));
        }
        jTable4.setDefaultRenderer(Object.class, new RowRendererInventory());
        DefaultTableModel modelInventory = (DefaultTableModel) jTable4.getModel();
        Inventory inventory = new Inventory();
        inventory.loadInventoryRequests(modelInventory);

        new Timer(2000, e -> inventory.loadInventoryRequests(modelInventory))
                .start();

        jPanel8.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 915, 430));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 24));
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        ImageIcon labelInventory = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/icons/material-management.png"), 30, 30);
        jLabel5.setIcon(labelInventory);
        jLabel5.setText("Inventory");
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

        jButton4.setText("Use Item");
        jButton4.addActionListener(e -> inventory.useInventory(jTable4, modelInventory));
        jPanel8.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 60, 100, 30));

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jTextField2.setForeground(new java.awt.Color(51, 51, 51));
        setInputBorderColor(jTextField2, "🔎 Search Item", Color.BLACK);

        TableRowSorter<DefaultTableModel> inventorySorter = new TableRowSorter<>(modelInventory);
        jTable4.setRowSorter(inventorySorter);

        Timer typingTimerServices = new Timer(500, evt -> {
            String serviceNameFilter = jTextField2.getText().trim();

            if (serviceNameFilter.isEmpty()) {
                inventorySorter.setRowFilter(null);
            } else {
                inventorySorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(serviceNameFilter), 0));
            }

            if (jTable4.getRowCount() > 0) {
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

        jPanel8.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 57, 155, 38));

        jTabbedPane1.addTab("Inventory", jPanel8);

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 920, 610));
    }

    private void applyReportsFilter(TableRowSorter<DefaultTableModel> sorter, String statusFilter, String idFilter,
            boolean hideCancelled) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        boolean hasStatus = statusFilter != null && !statusFilter.equals("status");
        boolean hasID = idFilter != null && !idFilter.isEmpty();

        if (hasStatus) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(statusFilter), 7));
        }
        if (hasID) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(idFilter), 0));
        }
        if (hideCancelled) {
            filters.add(RowFilter.notFilter(RowFilter.regexFilter("(?i)canceled", 7)));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
            if (jTable5.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "No transaction found", "No data", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        } else {
            return text.substring(0, maxLength - 3) + "...";
        }
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel logoutIconNav;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
