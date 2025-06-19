package src.main.java.components.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import src.main.java.App;
import src.main.java.auth.session.java.FetchUser;
import src.server.java.ServerURL;
import src.utils.CustomBorder;
import src.utils.CustomHeaderRenderer;
import src.utils.RowRendererInventoryTransaction;

public class GenerateReports extends ServerURL implements CustomBorder {

    private String lastTime = "0000-00-00 00:00:00";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private JTable jTable4 = new JTable();
    private JScrollPane jScrollPane4 = new JScrollPane();
    private int previousKilo = 0;
    private Set<String> previousValidSelections = new HashSet<>();
    int kiloBlockSize = 1;
    boolean isUpdatingAmount = false;

    Inventory inventory = new Inventory();
    JSONObject user = FetchUser.getUserData();
    Set<String> previousSelections;
    private App app;
    private boolean isPaidValid = false;

    public GenerateReports() {
    }

    public GenerateReports(App app) {
        this.app = app;
    }

    public void loadReportsRequests(DefaultTableModel model, boolean limitToLatest8) {
        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;"
                        + "&since=" + URLEncoder.encode(lastTime, StandardCharsets.UTF_8);
                HttpURLConnection conn = createPostConnection("get_reports.php", params);
                JSONArray arr = parseJsonArrayResponse(conn);

                List<Object[]> allRows = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String rawTime = obj.getString("time");
                    String formattedTime = rawTime.length() >= 16 ? rawTime.substring(0, 16) : rawTime;
                    String transaction = obj.getString("transaction_id");
                    String customer = obj.getString("customer_name");
                    String email = obj.getString("contact");
                    String serviceType = obj.getString("servicetype");
                    String c_pay = obj.getString("c_pay");
                    String c_change = obj.getString("c_change");
                    String amount = obj.getString("total_amount");
                    String branch = obj.getString("branch");
                    String status = obj.getString("status");

                    if (!branch.equals(user.getString("branch"))
                            && !"all".equals(user.getString("branch"))) {
                        continue;
                    }

                    allRows.add(new Object[]{
                        transaction, formattedTime, email,
                        customer, serviceType, c_pay, c_change, amount, branch, status
                    });

                    if (rawTime.compareTo(lastTime) > 0) {
                        lastTime = rawTime;
                    }
                }

                allRows.sort((a, b) -> ((String) b[1]).compareTo((String) a[1]));
                if (limitToLatest8 && allRows.size() > 8) {
                    return new ArrayList<>(allRows.subList(0, 8));
                }
                return allRows;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> rows = get();

                    if (model.getRowCount() == 0) {
                        for (Object[] row : rows) {
                            model.addRow(row);
                        }
                    } else {
                        Set<String> seenTx = new HashSet<>();
                        for (Object[] row : rows) {
                            String txId = (String) row[0];
                            seenTx.add(txId);
                            boolean found = false;
                            for (int r = 0; r < model.getRowCount(); r++) {
                                if (model.getValueAt(r, 0).equals(txId)) {
                                    for (int c = 0; c < row.length; c++) {
                                        model.setValueAt(row[c], r, c);
                                    }
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                model.insertRow(0, row);
                            }
                        }
                        if (limitToLatest8) {
                            while (model.getRowCount() > 8) {
                                model.removeRow(model.getRowCount() - 1);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public void newReport(DefaultTableModel tableModel, JTable table) {

        JDialog dialog = new JDialog((Frame) null, "Add new Transaction", true);
        dialog.setSize(1150, 850);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JTextField customerNameField = new JTextField();
        JTextField contactField = new JTextField();
        JSpinner kiloSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        kiloSpinner.setEditor(new JSpinner.NumberEditor(kiloSpinner, "#"));
        JFormattedTextField tf = ((JSpinner.NumberEditor) kiloSpinner.getEditor()).getTextField();
        tf.setEditable(false);
        JComponent kiloEditor = kiloSpinner.getEditor();
        if (kiloEditor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) kiloEditor).getTextField();
            textField.setOpaque(false);
            textField.setBackground(new Color(0, 0, 0, 0));
            textField.setBorder(null);
        }

        JCheckBox premiumWash = new JCheckBox("Premium Wash");
        JCheckBox classicWash = new JCheckBox("Classic Wash");

        premiumWash.addItemListener(e -> {
            if (premiumWash.isSelected()) {
                classicWash.setSelected(false);
            }
        });

        classicWash.addItemListener(e -> {
            if (classicWash.isSelected()) {
                premiumWash.setSelected(false);
            }
        });

        JCheckBox clothesOnly = new JCheckBox("Clothes Only");
        JCheckBox withJeans = new JCheckBox("With Jeans");
        JCheckBox withTowels = new JCheckBox("With Towels");

        clothesOnly.setToolTipText("Requires at least 8 kilos to process.");
        withJeans.setToolTipText("Requires at least 7 kilos to process.");
        withTowels.setToolTipText("Requires at least 6 kilos to process.");

        clothesOnly.addItemListener(e -> {
            if (clothesOnly.isSelected()) {
                withJeans.setSelected(false);
                withTowels.setSelected(false);
            }
        });

        withJeans.addItemListener(e -> {
            if (withJeans.isSelected()) {
                clothesOnly.setSelected(false);
                withTowels.setSelected(false);
            }
        });

        withTowels.addItemListener(e -> {
            if (withTowels.isSelected()) {
                clothesOnly.setSelected(false);
                withJeans.setSelected(false);
            }
        });

        JCheckBox wash = new JCheckBox("WASH");
        JCheckBox dry = new JCheckBox("DRY");
        JCheckBox fold = new JCheckBox("FOLD");

        wash.addItemListener(e -> {
            if (wash.isSelected()) {
            }
        });

        dry.addItemListener(e -> {
            if (dry.isSelected()) {
            }
        });

        fold.addItemListener(e -> {
            if (fold.isSelected()) {
            }
        });

        JCheckBox dropOff = new JCheckBox("DROP-OFF");
        JCheckBox pickupDeliver = new JCheckBox("PICK-UP and DELIVER");

        dropOff.addItemListener(e -> {
            if (dropOff.isSelected()) {
                pickupDeliver.setSelected(false);
            }
        });

        pickupDeliver.addItemListener(e -> {
            if (pickupDeliver.isSelected()) {
                dropOff.setSelected(false);
            }
        });

        JCheckBox diy = new JCheckBox("DIY");
        JCheckBox dryClean = new JCheckBox("DRY CLEAN");

        diy.addItemListener(e -> {
            if (diy.isSelected()) {
                dryClean.setSelected(false);
            }
        });

        dryClean.addItemListener(e -> {
            if (dryClean.isSelected()) {
                diy.setSelected(false);
            }
        });

        JCheckBox customerFULLPaid = new JCheckBox("Paid?");
        customerFULLPaid.setEnabled(false);

        JTextField toPay = new JTextField();
        toPay.setEditable(false);

        JTextField customerPaymentAdd = new JTextField();
        JTextField customerChangeAdd = new JTextField();
        customerChangeAdd.setEditable(false);

        customerPaymentAdd.setEnabled(false);
        customerChangeAdd.setEnabled(false);

        classicWash.setEnabled(false);
        premiumWash.setEnabled(false);
        wash.setEnabled(false);
        dry.setEnabled(false);
        fold.setEnabled(false);
        dropOff.setEnabled(false);
        pickupDeliver.setEnabled(false);
        diy.setEnabled(false);
        dryClean.setEnabled(false);

        if (!clothesOnly.isSelected() && !withJeans.isSelected() && !withTowels.isSelected()) {
            classicWash.setEnabled(false);
            premiumWash.setEnabled(false);
            wash.setEnabled(false);
            dry.setEnabled(false);
            fold.setEnabled(false);
            dropOff.setEnabled(false);
            pickupDeliver.setEnabled(false);
            diy.setEnabled(false);
            dryClean.setEnabled(false);
        }

        Timer statusChecker = new Timer(1000, e -> {
            new SwingWorker<Void, Void>() {
                JSONArray premiumResult, classicResult, washResult, dryResult, foldResult, dropOffResult, pickupResult,
                        diyResult, dryCleanResult;

                @Override
                protected Void doInBackground() {
                    try {
                        Pricing pricing = new Pricing();
                        premiumResult = pricing.loadPrice("Premium Wash");
                        classicResult = pricing.loadPrice("Classic Wash");
                        washResult = pricing.loadPrice("WASH");
                        dryResult = pricing.loadPrice("DRY");
                        foldResult = pricing.loadPrice("FOLD");
                        dropOffResult = pricing.loadPrice("DROP-OFF");
                        pickupResult = pricing.loadPrice("PICK-UP and DELIVER");
                        diyResult = pricing.loadPrice("DIY");
                        dryCleanResult = pricing.loadPrice("DRY CLEAN");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {

                        setServiceStatus(classicWash, classicResult, "Every " + kiloBlockSize + " kilo ");
                        setServiceStatus(premiumWash, premiumResult, "Every " + kiloBlockSize + " kilo ");

                        setServiceStatus(wash, washResult, "Every " + kiloBlockSize + " kilo ");
                        setServiceStatus(wash, washResult, "Every " + kiloBlockSize + " kilo ");
                        setServiceStatus(dry, dryResult, "Every " + kiloBlockSize + " kilo ");
                        setServiceStatus(fold, foldResult, "Every " + kiloBlockSize + " kilo ");
                        setServiceStatus(dropOff, dropOffResult, "Flat Rate");
                        setServiceStatus(pickupDeliver, pickupResult, "Flat rate");
                        setServiceStatus(diy, diyResult, "Price per kilo");
                        setServiceStatus(dryClean, dryCleanResult, "Price per kilo");

                        if (classicWash.isSelected() || premiumWash.isSelected()) {
                            wash.setSelected(false);
                            dry.setSelected(false);
                            fold.setSelected(false);
                            wash.setEnabled(false);
                            dry.setEnabled(false);
                            fold.setEnabled(false);

                        }
                        if (dropOff.isSelected()) {
                            diy.setEnabled(false);
                        }
                        if (diy.isSelected()) {
                            dropOff.setEnabled(false);
                        }

                        if (wash.isSelected() || dry.isSelected() || fold.isSelected()) {
                            classicWash.setEnabled(false);
                            premiumWash.setEnabled(false);
                        }

                        if (!clothesOnly.isSelected() && !withJeans.isSelected() && !withTowels.isSelected()) {
                            classicWash.setEnabled(false);
                            premiumWash.setEnabled(false);
                            wash.setEnabled(false);
                            dry.setEnabled(false);
                            fold.setEnabled(false);
                            dropOff.setEnabled(false);
                            pickupDeliver.setEnabled(false);
                            diy.setEnabled(false);
                            dryClean.setEnabled(false);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                void setServiceStatus(JComponent button, JSONArray result, String label) {
                    if (result != null && "1".equalsIgnoreCase(result.optString(1))) {
                        button.setEnabled(true);
                        button.setToolTipText(label + ": ₱" + result.optDouble(0));
                    } else {
                        button.setEnabled(false);
                        button.setToolTipText("Service not available");
                    }
                }
            }.execute();
        });
        statusChecker.start();

        JTextField amountField = new JTextField();
        amountField.setEditable(false);
        amountField.setBackground(Color.LIGHT_GRAY);

        JComboBox<String> statusTransactionAdd = new JComboBox<>(
                new String[]{"Pending", "Pickup"});
        statusTransactionAdd.setBackground(Color.WHITE);
        statusTransactionAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusTransactionAdd.setForeground(Color.DARK_GRAY);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.CYAN);
        saveButton.setFocusPainted(false);
        saveButton.setEnabled(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel washClassicPremium = new JPanel(new GridLayout(1, 3));
        washClassicPremium.add(classicWash);
        washClassicPremium.add(premiumWash);

        JPanel washDryFoldPanel = new JPanel(new GridLayout(1, 3));
        washDryFoldPanel.add(wash);
        washDryFoldPanel.add(dry);
        washDryFoldPanel.add(fold);

        JPanel deliveryPanel = new JPanel(new GridLayout(1, 2));
        deliveryPanel.add(dropOff);
        deliveryPanel.add(pickupDeliver);

        JPanel specialServicePanel = new JPanel(new GridLayout(1, 2));
        specialServicePanel.add(diy);
        specialServicePanel.add(dryClean);

        JPanel category = new JPanel(new GridLayout(1, 3));
        category.add(clothesOnly);
        category.add(withJeans);
        category.add(withTowels);

        setInputBorderColor(customerNameField, "Customer Name", Color.BLACK);
        setInputBorderColor(contactField, "Customer Email or (Phone Number)", Color.BLACK);
        setInputBorderColor(category, "With?", Color.BLACK);
        setInputBorderColor(washClassicPremium, "Wash Type", Color.BLACK);
        setInputBorderColor(washDryFoldPanel, "Main Service", Color.BLACK);
        setInputBorderColor(deliveryPanel, "Delivery & Handling Services", Color.BLACK);
        setInputBorderColor(specialServicePanel, "Special Services / Facility Type", Color.BLACK);
        setInputBorderColor(kiloSpinner, "Kilograms", Color.BLACK);

        setInputBorderColor(toPay, "To Pay (₱)", Color.BLACK);
        setInputBorderColor(customerPaymentAdd, "Customer Payment (₱)", Color.BLACK);
        setInputBorderColor(customerChangeAdd, "Customer Change (₱)", Color.BLACK);

        setInputBorderColor(amountField, "Total Amount (₱)", Color.BLACK);

        dialog.setLayout(new GridLayout(1, 2));

        JPanel panel = new JPanel(new GridLayout(16, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(customerNameField);
        panel.add(contactField);
        panel.add(category);
        panel.add(washClassicPremium);
        panel.add(washDryFoldPanel);
        panel.add(deliveryPanel);
        panel.add(specialServicePanel);
        panel.add(kiloSpinner);

        panel.add(toPay);

        panel.add(customerFULLPaid);
        panel.add(customerPaymentAdd);
        panel.add(customerChangeAdd);

        panel.add(amountField);
        panel.add(statusTransactionAdd);
        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);

        JPanel inventoryPanel = new JPanel(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTable4.setBackground(new java.awt.Color(255, 255, 255));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Item Name", "Quantity", "Threshold", "Status"
                }) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
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

            jTable4.getColumnModel().getColumn(0).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/box.png"));
            jTable4.getColumnModel().getColumn(1).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/bulk.png"));
            jTable4.getColumnModel().getColumn(2).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/type.png"));
            jTable4.getColumnModel().getColumn(3).setHeaderRenderer(new CustomHeaderRenderer("./src/icons/status.png"));
        }
        jTable4.setDefaultRenderer(Object.class, new RowRendererInventoryTransaction());
        DefaultTableModel modelInventory = (DefaultTableModel) jTable4.getModel();
        inventory.loadInventoryTransaction(modelInventory);
        JLabel inventoryLabelTrans = new JLabel("Current Inventory");
        inventoryLabelTrans.setFont(new Font(null, Font.BOLD, 18));
        inventoryPanel.add(inventoryLabelTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 45));
        inventoryPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 560, 750));

        dialog.add(inventoryPanel);

        Runnable updateAmount = () -> {
            if (isUpdatingAmount) {
                return;

            }
            isUpdatingAmount = true;

            try {
                int kilo = (int) kiloSpinner.getValue();
                double amount = 0;
                JSONArray result;
                DefaultTableModel model = (DefaultTableModel) jTable4.getModel();

                if (!clothesOnly.isSelected() && !withJeans.isSelected() && !withTowels.isSelected()) {
                    classicWash.setEnabled(false);
                    premiumWash.setEnabled(false);
                    wash.setEnabled(false);
                    dry.setEnabled(false);
                    fold.setEnabled(false);
                    dropOff.setEnabled(false);
                    pickupDeliver.setEnabled(false);
                    diy.setEnabled(false);
                    dryClean.setEnabled(false);
                }

                SpinnerNumberModel modelKiloSpinner = (SpinnerNumberModel) kiloSpinner.getModel();

                if (clothesOnly.isSelected()) {
                    kiloBlockSize = 8;
                    modelKiloSpinner.setMinimum(8);
                    if ((int) modelKiloSpinner.getNumber() < 8) {
                        modelKiloSpinner.setValue(8);
                    }
                } else if (withJeans.isSelected()) {
                    kiloBlockSize = 7;
                    modelKiloSpinner.setMinimum(7);
                    if ((int) modelKiloSpinner.getNumber() < 7) {
                        modelKiloSpinner.setValue(7);
                    }
                } else if (withTowels.isSelected()) {
                    kiloBlockSize = 6;
                    modelKiloSpinner.setMinimum(6);
                    if ((int) modelKiloSpinner.getNumber() < 6) {
                        modelKiloSpinner.setValue(6);
                    }
                } else {
                    kiloBlockSize = 1;
                    modelKiloSpinner.setMinimum(1);
                    if ((int) modelKiloSpinner.getNumber() > 1) {
                        modelKiloSpinner.setValue(1);
                    }
                }

                int blocks = (int) Math.ceil(kilo / (double) kiloBlockSize);

                if (premiumWash.isSelected() || classicWash.isSelected() || wash.isSelected() || dry.isSelected() || fold.isSelected() || dropOff.isSelected() || diy.isSelected()) {
                    withJeans.setEnabled(false);
                    clothesOnly.setEnabled(false);
                    withTowels.setEnabled(false);
                } else {
                    withJeans.setEnabled(true);
                    clothesOnly.setEnabled(true);
                    withTowels.setEnabled(true);
                }

                Set<String> currentSelections = new HashSet<>();
                Set<String> validCurrentSelections = new HashSet<>();

                if (wash.isSelected()) {
                    currentSelections.add("WASH");
                    result = new Pricing().loadPrice("WASH");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += blocks * result.optDouble(0, 0);
                        validCurrentSelections.add("WASH");
                    }
                    classicWash.setEnabled(false);
                    premiumWash.setEnabled(false);
                }

                if (classicWash.isSelected()) {
                    currentSelections.add("Classic Wash");
                    result = new Pricing().loadPrice("Classic Wash");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += blocks * result.optDouble(0, 0);
                        validCurrentSelections.add("Classic Wash");
                    }

                    wash.setEnabled(false);
                    dry.setEnabled(false);
                    fold.setEnabled(false);
                }

                if (premiumWash.isSelected()) {
                    currentSelections.add("Premium Wash");
                    result = new Pricing().loadPrice("Premium Wash");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += blocks * result.optDouble(0, 0);
                        validCurrentSelections.add("Premium Wash");
                    }

                    wash.setSelected(false);
                    dry.setSelected(false);
                    fold.setSelected(false);
                    wash.setEnabled(false);
                    dry.setEnabled(false);
                    fold.setEnabled(false);
                }

                if (dry.isSelected()) {
                    currentSelections.add("DRY");
                    result = new Pricing().loadPrice("DRY");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += blocks * result.optDouble(0, 0);
                        validCurrentSelections.add("DRY");
                    }
                    classicWash.setEnabled(false);
                    premiumWash.setEnabled(false);
                }

                if (diy.isSelected()) {
                    currentSelections.add("DIY");
                    result = new Pricing().loadPrice("DIY");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += result.optDouble(0, 0);
                        validCurrentSelections.add("DIY");
                    }
                    dropOff.setEnabled(false);
                }

                if (fold.isSelected()) {
                    currentSelections.add("FOLD");
                    result = new Pricing().loadPrice("FOLD");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += blocks * result.optDouble(0, 0);
                        validCurrentSelections.add("FOLD");
                    }
                    classicWash.setEnabled(false);
                    premiumWash.setEnabled(false);
                }

                if (dropOff.isSelected()) {
                    currentSelections.add("DROP-OFF");
                    result = new Pricing().loadPrice("DROP-OFF");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += result.optDouble(0, 0);
                        validCurrentSelections.add("DROP-OFF");
                    }
                    diy.setEnabled(false);
                }

                if (dryClean.isSelected()) {
                    currentSelections.add("DRY CLEAN");
                    result = new Pricing().loadPrice("DRY CLEAN");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += result.optDouble(0, 0) * kilo;
                        validCurrentSelections.add("DRY CLEAN");
                    }
                }

                if (pickupDeliver.isSelected()) {
                    currentSelections.add("PICK-UP and DELIVER");
                    result = new Pricing().loadPrice("PICK-UP and DELIVER");
                    if (result != null && "1".equalsIgnoreCase(result.optString(1, ""))) {
                        amount += result.optDouble(0, 0);
                        validCurrentSelections.add("PICK-UP and DELIVER");
                    }
                }

                if (customerFULLPaid.isSelected()) {
                    if (!"Paid".equals(statusTransactionAdd.getSelectedItem())) {
                        statusTransactionAdd.setSelectedItem("Paid");
                    }

                    customerPaymentAdd.setEnabled(true);
                    customerChangeAdd.setEnabled(true);

                } else {

                    customerPaymentAdd.setEnabled(false);
                    customerChangeAdd.setEnabled(false);

                    customerPaymentAdd.setText("Not paid yet");
                    customerChangeAdd.setText("Not paid yet");
                }

                boolean washTypeSelected = (classicWash.isSelected() && classicWash.isEnabled())
                        || (premiumWash.isSelected() && premiumWash.isEnabled());

                boolean mainServiceSelected = (wash.isSelected() && wash.isEnabled())
                        || (dry.isSelected() && dry.isEnabled())
                        || (fold.isSelected() && fold.isEnabled());

                boolean handlingOrSpecSelected = (dropOff.isSelected() && dropOff.isEnabled())
                        || (pickupDeliver.isSelected() && pickupDeliver.isEnabled())
                        || (diy.isSelected() && diy.isEnabled())
                        || (dryClean.isSelected() && dryClean.isEnabled());

                boolean anyValidServiceSelected = (washTypeSelected || mainServiceSelected) && handlingOrSpecSelected;

                if (anyValidServiceSelected) {
                    customerFULLPaid.setEnabled(true);
                } else {
                    customerFULLPaid.setEnabled(false);
                    customerFULLPaid.setSelected(false);
                    customerPaymentAdd.setEnabled(false);
                    customerChangeAdd.setEnabled(false);
                }

                if (customerFULLPaid.isSelected()) {
                    if (statusTransactionAdd.getItemCount() != 1 || !"Paid".equals(statusTransactionAdd.getItemAt(0))) {
                        statusTransactionAdd.setModel(new DefaultComboBoxModel<>(new String[]{"Paid"}));
                        statusTransactionAdd.setSelectedItem("Paid");
                    }

                    statusTransactionAdd.setEnabled(false);
                    customerPaymentAdd.setEnabled(true);
                    customerChangeAdd.setEnabled(true);

                } else {
                    if (statusTransactionAdd.getItemCount() != 2) {
                        statusTransactionAdd.setModel(new DefaultComboBoxModel<>(new String[]{"Pending", "Pickup"}));
                        statusTransactionAdd.setSelectedItem("Pending");
                    }

                    statusTransactionAdd.setEnabled(true);
                    customerPaymentAdd.setEnabled(false);
                    customerChangeAdd.setEnabled(false);
                }

                Map<String, Integer> previousItems = computeRequiredItems(previousValidSelections, previousKilo);
                Map<String, Integer> currentItems = computeRequiredItems(validCurrentSelections, kilo);

                Set<String> allItems = new HashSet<>();
                allItems.addAll(previousItems.keySet());
                allItems.addAll(currentItems.keySet());

                for (String item : allItems) {
                    int prevQty = previousItems.getOrDefault(item, 0);
                    int currQty = currentItems.getOrDefault(item, 0);
                    int delta = currQty - prevQty;

                    if (delta > 0) {
                        inventory.simulateDeductInTable(item, delta, model);
                        System.out.println(currentItems);
                    } else if (delta < 0) {
                        inventory.restoreItem(item, -delta, model);
                    }
                }

                try {
                    double customerPayment = Double.parseDouble(customerPaymentAdd.getText());
                    double change = customerPayment - amount;

                    if (customerFULLPaid.isSelected()) {
                        if (customerPayment < amount) {
                            JOptionPane.showMessageDialog(dialog,
                                    "Customer payment is less than the amount to pay!\nPlease collect the correct amount.",
                                    "Payment Error",
                                    JOptionPane.WARNING_MESSAGE);
                            customerChangeAdd.setText("Not paid yet");
                        } else {
                            isPaidValid = true;
                            customerChangeAdd.setText(String.format("%.2f", change));
                        }
                    }
                } catch (NumberFormatException ex) {
                    customerChangeAdd.setText("Not paid yet");
                }

                toPay.setText(String.format("%.2f", amount));
                amountField.setText(String.format("%.2f", amount));
                previousSelections = currentSelections;
                previousValidSelections = validCurrentSelections;
                previousKilo = kilo;

            } finally {
                isUpdatingAmount = false;
            }
        };

        ActionListener recalcListener = e -> updateAmount.run();
        ChangeListener spinnerChange = e -> updateAmount.run();

        classicWash.addActionListener(recalcListener);
        premiumWash.addActionListener(recalcListener);
        customerFULLPaid.addActionListener(recalcListener);
        wash.addActionListener(recalcListener);
        dry.addActionListener(recalcListener);
        fold.addActionListener(recalcListener);
        dropOff.addActionListener(recalcListener);
        diy.addActionListener(recalcListener);
        dryClean.addActionListener(recalcListener);
        pickupDeliver.addActionListener(recalcListener);
        kiloSpinner.addChangeListener(spinnerChange);
        clothesOnly.addChangeListener(spinnerChange);
        withJeans.addChangeListener(spinnerChange);
        withTowels.addChangeListener(spinnerChange);

        Timer typingTimer = new Timer(500, null);
        typingTimer.setRepeats(false);

        customerPaymentAdd.getDocument().addDocumentListener(new DocumentListener() {
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

        typingTimer.addActionListener(e -> updateAmount.run());

        updateAmount.run();

        DocumentListener inputListener = new DocumentListener() {
            void checkChanges() {
                boolean changed = !customerNameField.getText().trim().isEmpty()
                        && !contactField.getText().trim().isEmpty();
                saveButton.setEnabled(changed);
            }

            public void insertUpdate(DocumentEvent e) {
                checkChanges();
            }

            public void removeUpdate(DocumentEvent e) {
                checkChanges();
            }

            public void changedUpdate(DocumentEvent e) {
                checkChanges();
            }
        };

        customerNameField.getDocument().addDocumentListener(inputListener);
        contactField.getDocument().addDocumentListener(inputListener);

        saveButton.addActionListener(e -> {
            String customer = customerNameField.getText().trim();
            String contact = contactField.getText().trim();
            Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(contact);
            int kilo = (int) kiloSpinner.getValue();
            String status = statusTransactionAdd.getSelectedItem().toString();
            String amountText = amountField.getText();

            setInputBorderColor(washClassicPremium, "Wash Type", Color.BLACK);
            setInputBorderColor(washDryFoldPanel, "Main Service", Color.BLACK);
            setInputBorderColor(deliveryPanel, "Delivery & Handling Services", Color.BLACK);
            setInputBorderColor(specialServicePanel, "Special Services / Facility Type", Color.BLACK);
            setInputBorderColor(kiloSpinner, "Kilograms", Color.BLACK);

            if (!customer.matches("^[A-Za-z ]+$")) {
                JOptionPane.showMessageDialog(dialog, "Customer name must only contain letters and spaces.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isEmail = emailMatcher.matches();
            boolean isPhone = contact.matches("^\\d{7,15}$");
            boolean isManual = contact.equalsIgnoreCase("n/a") || contact.equalsIgnoreCase("no contact");

            if (!isEmail && !isPhone && !isManual) {
                setInputBorderColor(contactField, "Customer Email or (Phone Number)", Color.RED);
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid email, phone number (digits only), or type 'N/A' if not available.",
                        "Invalid Contact Info", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder selectedServices = new StringBuilder();

            boolean washtypeSelected = false;
            boolean mainSelected = false;
            boolean handlingService = false;
            boolean SSF = false;

            if (!clothesOnly.isSelected() && !withJeans.isSelected() && !withTowels.isSelected()) {
                setInputBorderColor(category, "With?", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Please select at least one (With?).", "Service Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (classicWash.isSelected()) {
                selectedServices.append("Classic Wash (includes WASH, DRY, FOLD), ");
                washtypeSelected = true;
                mainSelected = true;
            }

            if (premiumWash.isSelected()) {
                selectedServices.append("Premium Wash (includes WASH, DRY, FOLD), ");
                washtypeSelected = true;
                mainSelected = true;
            }

            if (!washtypeSelected) {
                if (wash.isSelected()) {
                    selectedServices.append("WASH, ");
                    mainSelected = true;
                }
                if (dry.isSelected()) {
                    selectedServices.append("DRY, ");
                    mainSelected = true;
                }
                if (fold.isSelected()) {
                    selectedServices.append("FOLD, ");
                    mainSelected = true;
                }
            }
            if (dropOff.isSelected()) {
                selectedServices.append("DROP-OFF, ");
                handlingService = true;
            }
            if (diy.isSelected()) {
                selectedServices.append("DIY, ");
                SSF = true;
            }
            if (dryClean.isSelected()) {
                selectedServices.append("DRY CLEAN, ");
                SSF = true;
            }
            if (pickupDeliver.isSelected()) {
                selectedServices.append("PICK-UP and DELIVER, ");
                handlingService = true;
            }

            if (selectedServices.length() > 0) {
                selectedServices.setLength(selectedServices.length() - 2);
            }

            boolean anyServiceSelectedButDisabled = (classicWash.isSelected() && !classicWash.isEnabled())
                    || (premiumWash.isSelected() && !premiumWash.isEnabled())
                    || (wash.isSelected() && !wash.isEnabled())
                    || (dry.isSelected() && !dry.isEnabled())
                    || (fold.isSelected() && !fold.isEnabled())
                    || (dropOff.isSelected() && !dropOff.isEnabled())
                    || (pickupDeliver.isSelected() && !pickupDeliver.isEnabled())
                    || (diy.isSelected() && !diy.isEnabled())
                    || (dryClean.isSelected() && !dryClean.isEnabled());

            boolean allServicesDisabled = !classicWash.isEnabled()
                    && !premiumWash.isEnabled()
                    && !wash.isEnabled()
                    && !dry.isEnabled()
                    && !fold.isEnabled()
                    && !dropOff.isEnabled()
                    && !pickupDeliver.isEnabled()
                    && !diy.isEnabled()
                    && !dryClean.isEnabled();

            boolean anyValidServiceSelected = (classicWash.isSelected() && classicWash.isEnabled())
                    || (premiumWash.isSelected() && premiumWash.isEnabled())
                    || (wash.isSelected() && wash.isEnabled())
                    || (dry.isSelected() && dry.isEnabled())
                    || (fold.isSelected() && fold.isEnabled())
                    || (dropOff.isSelected() && dropOff.isEnabled())
                    || (pickupDeliver.isSelected() && pickupDeliver.isEnabled())
                    || (diy.isSelected() && diy.isEnabled())
                    || (dryClean.isSelected() && dryClean.isEnabled());

            if (anyValidServiceSelected) {
                customerFULLPaid.setEnabled(true);
            } else {
                customerFULLPaid.setEnabled(false);
                customerFULLPaid.setSelected(false);
                customerPaymentAdd.setEnabled(false);
                customerChangeAdd.setEnabled(false);
                if (!"Pending".equals(statusTransactionAdd.getSelectedItem())) {
                    statusTransactionAdd.setSelectedItem("Pending");
                }
            }

            if (anyServiceSelectedButDisabled && !allServicesDisabled) {
                JOptionPane.showMessageDialog(null,
                        "One or more selected services are no longer available. Please review your selection.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean washTypeEnabled = !allDisabled(classicWash, premiumWash);
            boolean mainServiceEnabled = !allDisabled(wash, dry, fold);

            if (!washtypeSelected && !mainSelected && (washTypeEnabled || mainServiceEnabled)) {
                if (washTypeEnabled) {
                    setInputBorderColor(washClassicPremium, "Wash Type Missing", Color.RED);
                }
                if (mainServiceEnabled) {
                    setInputBorderColor(washDryFoldPanel, "Main Service Missing", Color.RED);
                }

                JOptionPane.showMessageDialog(dialog,
                        "Please select at least one wash service: either Classic/Premium Wash, or WASH, DRY, FOLD individually.",
                        "Service Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean deliveryEnabled = !allDisabled(dropOff, pickupDeliver);
            boolean facilityEnabled = !allDisabled(diy, dryClean);

            if (!handlingService && !SSF && (deliveryEnabled || facilityEnabled)) {
                if (deliveryEnabled) {
                    setInputBorderColor(deliveryPanel, "Delivery / Handling Missing", Color.RED);
                }
                if (facilityEnabled) {
                    setInputBorderColor(specialServicePanel, "Facility Service Missing", Color.RED);
                }

                JOptionPane.showMessageDialog(dialog,
                        "Please select at least one: Delivery/Handling service or DIY / Dry Clean service.",
                        "Service Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (clothesOnly.isSelected() && kilo < 8) {
                setInputBorderColor(kiloSpinner, "Kilograms", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Minimum of kilo is 8kg", "Minimum Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (withJeans.isSelected() && kilo < 7) {
                setInputBorderColor(kiloSpinner, "Kilograms", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Minimum of kilo is 7kg", "Minimum Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (withTowels.isSelected() && kilo < 6) {
                setInputBorderColor(kiloSpinner, "Kilograms", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Minimum of kilo is 6kg", "Minimum Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (isManual) {
                JOptionPane.showMessageDialog(dialog,
                        "Customer has no email or phone number. A physical claim slip will be required.",
                        "Manual Notification Required", JOptionPane.INFORMATION_MESSAGE);
            }

            if (status.equals("Pickup")) {
                int choice = JOptionPane.showConfirmDialog(dialog,
                        "By setting the status to 'Pickup', the customer will be notified that their order is ready.\nAre you sure?",
                        "Confirm Pickup", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (status.equals("Paid")) {
                int choice = JOptionPane.showConfirmDialog(dialog,
                        "Marking as 'Paid' means the transaction is complete and locked.\nContinue?",
                        "Confirm Paid", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (!mainSelected && !allDisabled(wash, dry, fold)) {
                setInputBorderColor(washDryFoldPanel, "Main Service Missing", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Please select at least one main service (WASH, DRY, or FOLD).",
                        "Service Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isPaidValid && customerFULLPaid.isSelected()) {

                JOptionPane.showMessageDialog(dialog,
                        "Customer payment is less than the amount to pay!\nPlease collect the correct amount.",
                        "Payment Error",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            boolean success = inventory.deductInventoryFromSimulatedTable(modelInventory);
            if (!success) {
                return;
            }

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    SwingUtilities.invokeLater(() -> app.overlayLoading(true));
                    dialog.setVisible(false);
                    Thread.sleep(2000);

                    StringBuilder receipt = new StringBuilder();
                    receipt.append("------ How Your Bill Was Calculated ------\n");

                    int blocks = (int) Math.ceil(kilo / (double) kiloBlockSize);

                    if (wash.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("WASH");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * blocks;
                            receipt.append("WASH: ₱").append(rate)
                                    .append(" x ").append(blocks)
                                    .append(" block(s) (every ").append(kiloBlockSize)
                                    .append(" kilos) = ₱").append(total).append("\n");
                        }
                    }

                    if (classicWash.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("Classic Wash");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * blocks;
                            receipt.append("Classic Wash: ₱").append(rate)
                                    .append(" x ").append(blocks)
                                    .append(" block(s) (every ").append(kiloBlockSize)
                                    .append(" kilos) = ₱").append(total).append("\n");
                        }
                    }

                    if (premiumWash.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("Premium Wash");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * blocks;
                            receipt.append("Premium Wash: ₱").append(rate)
                                    .append(" x ").append(blocks)
                                    .append(" block(s) (every ").append(kiloBlockSize)
                                    .append(" kilos) = ₱").append(total).append("\n");
                        }
                    }

                    if (dry.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("DRY");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * blocks;
                            receipt.append("DRY: ₱").append(rate)
                                    .append(" x ").append(blocks)
                                    .append(" block(s) (every ").append(kiloBlockSize)
                                    .append(" kilos) = ₱").append(total).append("\n");
                        }
                    }

                    if (fold.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("FOLD");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * blocks;
                            receipt.append("FOLD: ₱").append(rate)
                                    .append(" x ").append(blocks)
                                    .append(" block(s) (every ").append(kiloBlockSize)
                                    .append(" kilos) = ₱").append(total).append("\n");
                        }
                    }

                    if (dropOff.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("DROP-OFF");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate;
                            receipt.append("DROP-OFF: ₱").append(total).append(" (flat)\n");
                        }
                    }

                    if (pickupDeliver.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("PICK-UP and DELIVER");
                        if (price != null) {
                            double flatRate = price.optDouble(0, 0);
                            receipt.append("PICK-UP and DELIVER: ₱").append(flatRate).append(" (fixed)\n");
                        }
                    }

                    if (diy.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("DIY");
                        if (price != null) {
                            double flatRate = price.optDouble(0, 0);
                            receipt.append("DIY: ₱").append(flatRate).append(" (fixed)\n");
                        }
                    }

                    if (dryClean.isSelected()) {
                        JSONArray price = new Pricing().loadPrice("DRY CLEAN");
                        if (price != null) {
                            double rate = price.optDouble(0, 0);
                            double total = rate * kilo;
                            receipt.append("DRY CLEAN: ₱").append(rate)
                                    .append(" x ").append(kilo).append("kg = ₱").append(total).append("\n");
                        }
                    }

                    receipt.append("-------------------------------------\n");
                    receipt.append("TOTAL AMOUNT: ₱").append(amountField.getText()).append("\n");
                    receipt.append("=====================================\n");

                    String customerPaymentText = customerPaymentAdd.getText().trim();
                    String customerChangeText = customerChangeAdd.getText().trim();

                    receipt.append("Customer Paid: ₱").append(customerPaymentText).append("\n");
                    receipt.append("Change Given: ₱").append(customerChangeText).append("\n");

                    String finalReceipt = receipt.toString();
                    System.out.println(finalReceipt);

                    HttpURLConnection conn = createPostConnection(
                            "get_reports.php",
                            "action=add&client_fetch_token_request=;;mslaundryshop2025;;"
                            + "&customer=" + URLEncoder.encode(customer, StandardCharsets.UTF_8)
                            + "&contact=" + URLEncoder.encode(contact, StandardCharsets.UTF_8)
                            + "&servicetype=" + URLEncoder.encode(selectedServices.toString(), StandardCharsets.UTF_8)
                            + "&c_pay=" + customerPaymentText
                            + "&c_change=" + customerChangeText
                            + "&amount=" + URLEncoder.encode(amountText, StandardCharsets.UTF_8)
                            + "&status=" + URLEncoder.encode(status, StandardCharsets.UTF_8)
                            + "&receipt=" + URLEncoder.encode(finalReceipt, StandardCharsets.UTF_8)
                            + "&branch=" + URLEncoder.encode(user.getString("branch"), StandardCharsets.UTF_8)
                    );

                    JSONObject responseArray = parseJsonResponse(conn);

                    if (responseArray.getBoolean("response")) {
                        String transactionId = responseArray.getString("ti");
                        String receiptContent = responseArray.getString("receipt");

                        if (status.equalsIgnoreCase("Pickup") || status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Paid")) {

                            if (isPhone) {
                                String smsMessage = "Hello " + customer + ", ";

                                if (status.equalsIgnoreCase("Pickup")) {
                                    smsMessage += "your laundry is ready for pickup.\n";
                                } else {
                                    smsMessage += "thank you for your order. We've received your laundry.\n";
                                }

                                smsMessage += "Transaction ID: " + transactionId + "\n"
                                        + "Service: " + selectedServices + "\n"
                                        + "Amount: ₱" + amountText + "\n"
                                        + "From branch: " + user.getString("branch") + "\n\n"
                                        + receiptContent;

                                JOptionPane.showMessageDialog(dialog,
                                        "Please manually SMS the customer:\n\n"
                                        + smsMessage + "\n\n"
                                        + "Contact: " + contact,
                                        "Manual SMS Required",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else if (isManual) {
                                String slipIntro = "Dear " + customer + ",\n\n";

                                String slipMessage = "";
                                if (status.equalsIgnoreCase("Pickup")) {
                                    slipMessage = "Your laundry is READY for pickup.\n\n";
                                } else {
                                    slipMessage = "Thank you for trusting MRS CLEAN Laundry.\n"
                                            + "Your laundry is now being processed.\n\n";
                                }

                                String manualSlip = "CLAIM SLIP (MANUAL)\n"
                                        + "----------------------------------\n"
                                        + slipIntro
                                        + slipMessage
                                        + "Transaction ID: " + transactionId + "\n"
                                        + "Service Type: " + selectedServices + "\n"
                                        + "Branch: " + user.getString("branch") + "\n\n"
                                        + receiptContent + "\n"
                                        + "Please bring this slip when claiming your laundry.\n"
                                        + "----------------------------------";

                                JOptionPane.showMessageDialog(dialog,
                                        "Customer has no contact information.\nA physical CLAIM SLIP will be given:\n\n"
                                        + manualSlip,
                                        "Manual Claim Slip Required",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        loadReportsRequests(tableModel, false);

                        JOptionPane.showMessageDialog(null, "Transaction added successfully");
                        dialog.dispose();
                        table.clearSelection();
                    }

                    return null;
                }

                protected void done() {
                    SwingUtilities.invokeLater(() -> app.overlayLoading(false));
                }
            }.execute();
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void updateReport(JTable userTable, DefaultTableModel tableModel) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No report selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int modelRow = userTable.convertRowIndexToModel(selectedRow);

        String ti = (String) tableModel.getValueAt(modelRow, 0);
        String customerName = (String) tableModel.getValueAt(modelRow, 2);
        String contact = (String) tableModel.getValueAt(modelRow, 3);
        String service = (String) tableModel.getValueAt(modelRow, 4);
        String c_pay = (String) tableModel.getValueAt(modelRow, 5);
        String c_change = (String) tableModel.getValueAt(modelRow, 6);
        String amount = (String) tableModel.getValueAt(modelRow, 7);
        String branch = (String) tableModel.getValueAt(modelRow, 8);
        String oldStatus = (String) tableModel.getValueAt(modelRow, 9);

        JDialog dialog = new JDialog((Frame) null, "Update report", true);
        dialog.setSize(450, 380);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JComboBox jComboBox2 = new JComboBox();
        jComboBox2.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jComboBox2.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Pending", "Canceled", "Pickup", "Delivered", "Paid"}));

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.CYAN);
        saveButton.setFocusPainted(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(jComboBox2);
        panel.add(saveButton);
        panel.add(cancelButton);
        dialog.add(panel);

        saveButton.addActionListener(e -> {

            boolean isPhone = contact.matches("^\\d{7,15}$");

            String status = jComboBox2.getSelectedItem().toString();
            if (oldStatus.equals("Pickup") && status.equals("Pickup")) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Customer has already been notified that their laundry is ready for pickup!",
                        "Already Notified",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    SwingUtilities.invokeLater(() -> app.overlayLoading(true));
                    dialog.setVisible(false);
                    Thread.sleep(2000);
                    if (status.equals("Pickup") && !oldStatus.equals("Pickup")) {
                        int choice = JOptionPane.showConfirmDialog(
                                dialog,
                                "By setting the status to “Ready for Pickup”, the customer will be notified that their order is available for collection.\n"
                                + "They’ll expect to come in and pick up their laundry—are you sure you want to proceed?",
                                "Confirm Pickup Status",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (choice != JOptionPane.YES_OPTION) {
                            return null;
                        }
                    }

                    JTextField txtCustomerPaid = new JTextField();
                    JTextField txtChange = new JTextField();

                    if (status.equals("Paid")) {
                        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

                        JLabel lblAmountDue = new JLabel("Amount to Pay (₱):");
                        JTextField txtAmountDue = new JTextField(amount);
                        txtAmountDue.setEditable(false);

                        JLabel lblCustomerPaid = new JLabel("Customer Paid (₱):");

                        JLabel lblChange = new JLabel("Change to Customer (₱):");
                        txtChange.setEditable(false);

                        panel.add(lblAmountDue);
                        panel.add(txtAmountDue);
                        panel.add(lblCustomerPaid);
                        panel.add(txtCustomerPaid);
                        panel.add(lblChange);
                        panel.add(txtChange);

                        txtCustomerPaid.getDocument().addDocumentListener(new DocumentListener() {
                            private void updateChange() {
                                try {
                                    double paid = Double.parseDouble(txtCustomerPaid.getText().trim());
                                    double due = Double.parseDouble(amount);
                                    double change = paid - due;

                                    if (change < 0) {
                                        txtChange.setText("Not paid yet");
                                    } else {
                                        txtChange.setText(String.format("%.2f", change));
                                    }
                                } catch (NumberFormatException ex) {
                                    txtChange.setText("Not paid yet");
                                }
                            }

                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                updateChange();
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                updateChange();
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                updateChange();
                            }
                        });

                        int result = JOptionPane.showConfirmDialog(
                                dialog,
                                panel,
                                "Enter Payment Details",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                        if (result != JOptionPane.OK_OPTION) {
                            return null;
                        }

                        try {
                            double paid = Double.parseDouble(txtCustomerPaid.getText().trim());
                            double due = Double.parseDouble(amount);

                            if (paid < due) {
                                JOptionPane.showMessageDialog(dialog,
                                        "Customer payment is less than the amount to pay!\nPlease collect the correct amount.",
                                        "Payment Error",
                                        JOptionPane.WARNING_MESSAGE);
                                return null;
                            }

                            lblCustomerPaid.setText(String.format("%.2f", paid));
                            lblChange.setText(txtChange.getText());

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog,
                                    "Invalid payment input! Please enter a valid number.",
                                    "Input Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }

                    String customerPaidText = txtCustomerPaid.getText().trim().isEmpty() ? c_pay : String.format("%.2f", Double.parseDouble(txtCustomerPaid.getText().trim()));
                    String changeText = txtChange.getText().trim().isEmpty() ? c_change : txtChange.getText().trim();

                    HttpURLConnection conn = createPostConnection(
                            "get_reports.php",
                            "action=update&client_fetch_token_request=;;mslaundryshop2025;;"
                            + "&newstatus=" + URLEncoder.encode(status, StandardCharsets.UTF_8)
                            + "&ti=" + URLEncoder.encode(ti, StandardCharsets.UTF_8)
                            + "&contact=" + URLEncoder.encode(contact, StandardCharsets.UTF_8)
                            + "&c_pay=" + URLEncoder.encode(customerPaidText, StandardCharsets.UTF_8)
                            + "&c_change=" + URLEncoder.encode(changeText, StandardCharsets.UTF_8)
                            + "&amount=" + URLEncoder.encode(amount, StandardCharsets.UTF_8)
                            + "&branch=" + URLEncoder.encode(branch, StandardCharsets.UTF_8)
                            + "&customer=" + URLEncoder.encode(customerName, StandardCharsets.UTF_8)
                    );

                    JSONObject responseArray = parseJsonResponse(conn);

                    if (responseArray.getBoolean("response")) {
                        String transactionId = responseArray.getString("ti");

                        if (isPhone && status.equalsIgnoreCase("Pickup")) {
                            String manualSms = "Hello " + customerName + ", your laundry is ready for pickup.\n"
                                    + "Transaction ID: " + transactionId + "\n"
                                    + "Service: " + service + "\n"
                                    + responseArray.getString("receipt")
                                    + "From branch: " + branch;

                            JOptionPane.showMessageDialog(dialog,
                                    "Please manually SMS the customer:\n\n" + manualSms + "\n\nContact: " + contact,
                                    "Manual SMS Required",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        loadReportsRequests(tableModel, false);
                        JOptionPane.showMessageDialog(null, "Report updated successfully");
                        dialog.dispose();
                        userTable.clearSelection();
                    }

                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> app.overlayLoading(false));
                }
            }.execute();

        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private Map<String, Integer> computeRequiredItems(Set<String> services, int kilo) {
        Map<String, Integer> items = new HashMap<>();

        boolean isPremiumWash = services.contains("Premium Wash");
        boolean isClassicWash = services.contains("Classic Wash");

        int blocks = (int) Math.ceil(kilo / (double) kiloBlockSize);

        if (isClassicWash) {
            items.put("Surf", 2 * blocks);
            items.put("Fabric", 1 * blocks);
            items.put("Sketch Tape", kilo);
        }

        if (isPremiumWash) {
            items.put("Surf", 3 * blocks);
            items.put("Fabric", 2 * blocks);
            items.put("Zonrox (ml)", 60 * blocks);
            items.put("Sketch Tape", kilo);
        }

        boolean includeWash = services.contains("WASH");
        boolean includeDry = services.contains("DRY") || isClassicWash || isPremiumWash;
        boolean includeFold = services.contains("FOLD") || isClassicWash || isPremiumWash;

        if (includeFold) {
            items.put("Sketch Tape", blocks);
            items.put("Plastic Bag", blocks);
        }

        if (services.contains("DROP-OFF")) {
            items.put("Tag", 1);
        }

        if (services.contains("DRY CLEAN")) {
            items.put("Dry Cleaning Solvent", kilo);
        }

        if (services.contains("PICK-UP and DELIVER")) {
            items.put("Delivery Receipt", 1);
        }

        return items;
    }

    private boolean allDisabled(JComponent... buttons) {
        for (JComponent btn : buttons) {
            if (btn.isEnabled()) {
                return false;
            }
        }
        return true;
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

    private JSONArray parseJsonArrayResponse(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }

            return new JSONArray(responseBuilder.toString());

        } catch (Exception e) {
            return new JSONArray();
        }
    }

}
