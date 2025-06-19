package src.main.java.components.helper;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.CYAN;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import src.main.java.auth.session.java.FetchUser;
import src.server.java.ServerURL;
import src.utils.CustomBorder;

public class Pricing extends ServerURL implements CustomBorder {

    private String lastTime = "1970-01-01 00:00:00";

    JSONObject user = FetchUser.getUserData();

    public void loadServicePricesLive(DefaultTableModel model, JTable table) {
        new Thread(() -> {
            try {
                String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;&since="
                        + URLEncoder.encode(lastTime, StandardCharsets.UTF_8);
                HttpURLConnection conn = createPostConnection("get_prices.php", params);
                JSONArray arr = parseJsonArrayResponse(conn);

                List<Object[]> newRows = new ArrayList<>();
                boolean[] newDataAdded = {false};

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String time = obj.getString("time");
                    String serviceName = obj.getString("service_name");
                    String price = obj.getString("price_per_kilo");
                    String branch = obj.getString("branch");
                    String available = obj.getString("status");
                    boolean isAvailable = "1".equals(available);

                    if (!branch.equals(user.getString("branch"))
                            && !"all".equals(user.getString("branch"))) {
                        continue;
                    }

                    boolean alreadyExists = false;
                    for (int j = 0; j < model.getRowCount(); j++) {
                        if (serviceName.equals(model.getValueAt(j, 0))) {
                            alreadyExists = true;
                            break;
                        }
                    }

                    if (!alreadyExists) {
                        newRows.add(new Object[]{serviceName, price, branch, isAvailable});
                        newDataAdded[0] = true;
                    }

                    if (time.compareTo(lastTime) > 0) {
                        lastTime = time;
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    for (Object[] row : newRows) {
                        model.addRow(row);
                    }

                    if (newDataAdded[0] && model.getRowCount() > 0) {
                        table.scrollRectToVisible(table.getCellRect(0, 0, true));
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public JSONArray loadPrice(String serviceNameToFind) {
        try {
            String params = "action=get&client_fetch_token_request=;;mslaundryshop2025;;"
                    + "&since=" + URLEncoder.encode(lastTime, StandardCharsets.UTF_8);
            HttpURLConnection conn = createPostConnection("get_prices.php", params);
            JSONArray arr = parseJsonArrayResponse(conn);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String branch = obj.getString("branch");
                if (!branch.equals(user.getString("branch"))
                        && !"all".equals(user.getString("branch"))) {
                    continue;
                }

                if (obj.getString("service_name").equalsIgnoreCase(serviceNameToFind)) {
                    JSONArray found = new JSONArray();
                    found.put(obj.getDouble("price_per_kilo"));
                    found.put(obj.getString("status"));
                    return found;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editPricing(DefaultTableModel tableModel, JTable userTable) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No service selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int modelRow = userTable.convertRowIndexToModel(selectedRow);

        String service = (String) tableModel.getValueAt(modelRow, 0);
        String price = (String) tableModel.getValueAt(modelRow, 1);
        String branch = (String) tableModel.getValueAt(modelRow, 2);
        System.out.println(branch);

        JDialog dialog = new JDialog((Frame) null, "Update Pricing", true);
        dialog.setSize(450, 380);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JTextField serviceField = new JTextField(service);
        serviceField.setEnabled(false);
        JTextField priceField = new JTextField(price);
        setInputBorderColor(serviceField, "Service Name", Color.BLACK);
        setInputBorderColor(priceField, "Price per Kilo (₱)", Color.BLACK);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(CYAN);
        saveButton.setFocusPainted(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(serviceField);
        panel.add(priceField);
        panel.add(saveButton);
        panel.add(cancelButton);
        dialog.add(panel);

        saveButton.setEnabled(false);

        DocumentListener inputListener = new DocumentListener() {
            void checkChanges() {
                boolean changed = !serviceField.getText().equals(service)
                        || !priceField.getText().equals(price);
                saveButton.setEnabled(changed);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkChanges();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkChanges();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkChanges();
            }
        };

        serviceField.getDocument().addDocumentListener(inputListener);
        priceField.getDocument().addDocumentListener(inputListener);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                HttpURLConnection conn = createPostConnection(
                        "get_prices.php",
                        "action=update&client_fetch_token_request=;;mslaundryshop2025;;&newservice="
                        + serviceField.getText() + "&newprice=" + priceField.getText() + "&oldservice="
                        + service + "&oldprice=" + price + "&branch=" + branch);
                JSONObject responseArray = parseJsonResponse(conn);
                if (responseArray.getBoolean("response") == true) {
                    tableModel.setRowCount(0);
                    loadServicePricesLive(tableModel, userTable);
                    JOptionPane.showMessageDialog(null, "Price updated successfully");
                    dialog.dispose();
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
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
            e.printStackTrace();
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
