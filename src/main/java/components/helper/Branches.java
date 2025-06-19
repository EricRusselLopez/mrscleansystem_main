package src.main.java.components.helper;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import src.server.java.ServerURL;
import src.utils.CustomBorder;

public class Branches extends ServerURL implements CustomBorder {

    public void loadBranchesRequests(DefaultTableModel model) {
        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                HttpURLConnection conn = createPostConnection(
                        "get_branches.php",
                        "action=get&client_fetch_token_request=;;mslaundryshop2025;;");
                JSONArray arr = parseJsonArrayResponse(conn);

                List<Object[]> rows = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject b = arr.getJSONObject(i);
                    rows.add(new Object[] {
                            b.getString("branchid"),
                            b.getString("name")
                    });
                }
                return rows;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> rows = get();
                    SwingUtilities.invokeLater(() -> {
                        model.setRowCount(0);
                        for (Object[] row : rows) {
                            model.addRow(row);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    public void updateBranchesLive(JComboBox<String> branchField) {
        new Thread(() -> {
            String[] previousComboItems = new String[0];
            while (true) {
                try {
                    String postData = "action=get&client_fetch_token_request=;;mslaundryshop2025;;";
                    HttpURLConnection conn = new ServerURL().createPostConnection("get_branches.php", postData);
                    JSONArray response = parseJsonArrayResponse(conn);

                    String[] comboItems;
                    if (response != null) {
                        comboItems = new String[response.length() + 1];
                        comboItems[0] = "Select branch";

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            comboItems[i + 1] = obj.getString("branchid") + " (" + obj.getString("name") + ")";
                        }
                    } else {
                        comboItems = new String[] { "Select branch" };
                    }

                    final String[] finalComboItems = comboItems;

                    boolean isDifferent = comboItems.length != previousComboItems.length;
                    if (!isDifferent) {
                        for (int i = 0; i < comboItems.length && !isDifferent; i++) {
                            if (!comboItems[i].equals(previousComboItems[i])) {
                                isDifferent = true;
                            }
                        }
                    }

                    if (isDifferent) {
                        Object selectedItem = branchField.getSelectedItem();

                        SwingUtilities.invokeLater(() -> {
                            branchField.setModel(new DefaultComboBoxModel<>(finalComboItems));
                            if (selectedItem != null
                                    && java.util.Arrays.asList(finalComboItems).contains(selectedItem)) {
                                branchField.setSelectedItem(selectedItem);
                            }
                        });

                        previousComboItems = finalComboItems;
                    }

                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        branchField.setModel(new DefaultComboBoxModel<>(new String[] { "Select branch" }));
                    });
                    previousComboItems = new String[] { "Select branch" };
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public void addBranch(JTable userTable, DefaultTableModel tableModel) {

        JDialog dialog = new JDialog((Frame) null, "Add new Branch", true);
        dialog.setSize(450, 380);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JTextField branchidField = new JTextField();
        JTextField nameField = new JTextField();

        setInputBorderColor(branchidField, "Branch ID (e.g Branch 1)", Color.BLACK);
        setInputBorderColor(nameField, "Branch Name", Color.BLACK);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.CYAN);
        saveButton.setFocusPainted(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(branchidField);
        panel.add(nameField);
        panel.add(saveButton);
        panel.add(cancelButton);
        dialog.add(panel);

        saveButton.setEnabled(false);

        DocumentListener inputListener = new DocumentListener() {
            void checkChanges() {
                boolean changed = !branchidField.getText().equals("")
                        || !nameField.getText().equals("");
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

        branchidField.getDocument().addDocumentListener(inputListener);
        nameField.getDocument().addDocumentListener(inputListener);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HttpURLConnection conn = createPostConnection(
                        "get_branches.php",
                        "action=add&client_fetch_token_request=;;mslaundryshop2025;;&newbranchid="
                                + branchidField.getText() + "&newname=" + nameField.getText());

                JSONObject responseArray = parseJsonResponse(conn);
                if (responseArray.getBoolean("response") == true) {
                    loadBranchesRequests(tableModel);
                    JOptionPane.showMessageDialog(null, "Branch added successfully");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "This branch is already exist.", "Branch error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void editBranch(JTable userTable, DefaultTableModel tableModel) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No branch selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                "<html><b>Warning:</b> Updating this branch<br>may affect employees linked to it.<br>" +
                        "Their accounts might no longer work,<br>and they will need to request a new one.</html>",
                "Branch Update Warning",
                JOptionPane.WARNING_MESSAGE);

        int modelRow = userTable.convertRowIndexToModel(selectedRow);

        String branchid = (String) tableModel.getValueAt(modelRow, 0);
        String name = (String) tableModel.getValueAt(modelRow, 1);

        JDialog dialog = new JDialog((Frame) null, "Change Branch", true);
        dialog.setSize(450, 380);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JTextField branchidField = new JTextField(branchid);
        JTextField nameField = new JTextField(name);
        setInputBorderColor(branchidField, "Branch ID (e.g Branch 1)", Color.BLACK);
        setInputBorderColor(nameField, "Branch Name", Color.BLACK);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.CYAN);
        saveButton.setFocusPainted(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(branchidField);
        panel.add(nameField);
        panel.add(saveButton);
        panel.add(cancelButton);
        dialog.add(panel);

        saveButton.setEnabled(false);

        DocumentListener inputListener = new DocumentListener() {
            void checkChanges() {
                boolean changed = !branchidField.getText().equals(branchid)
                        || !nameField.getText().equals(name);
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

        branchidField.getDocument().addDocumentListener(inputListener);
        nameField.getDocument().addDocumentListener(inputListener);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HttpURLConnection conn = createPostConnection(
                        "get_branches.php",
                        "action=update&client_fetch_token_request=;;mslaundryshop2025;;&newbranchid="
                                + branchidField.getText() + "&newname=" + nameField.getText() + "&oldbranchid="
                                + branchid + "&oldbranchname=" + name);
                JSONObject responseArray = parseJsonResponse(conn);
                if (responseArray.getBoolean("response") == true) {
                    loadBranchesRequests(tableModel);
                    JOptionPane.showMessageDialog(null, "Branch updated successfully");
                    dialog.dispose();
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void removeBranch(JTable userTable, DefaultTableModel tableModel) {
        if (userTable.getRowCount() <= 1) {
            JOptionPane.showMessageDialog(null,
                    "Cannot delete the only remaining branch. At least one branch must always exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No branch selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(
                    null,
                    "<html><b>Danger:</b> Removing this branch<br>will prevent any employees assigned to it<br>" +
                            "from logging in. They must register again.</html>",
                    "Branch Removal Warning",
                    JOptionPane.ERROR_MESSAGE);

            int modelRow = userTable.convertRowIndexToModel(selectedRow);

            String branchid = (String) tableModel.getValueAt(modelRow, 0);
            String name = (String) tableModel.getValueAt(modelRow, 1);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you you want to remove this branch?",
                    "Remove Branch", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                HttpURLConnection conn = createPostConnection(
                        "get_branches.php",
                        "action=remove&client_fetch_token_request=;;mslaundryshop2025;;&branchid=" + branchid + "&name="
                                + name);

                JSONObject responseArray = parseJsonResponse(conn);
                if (responseArray.getBoolean("response") == true) {
                    loadBranchesRequests(tableModel);
                    JOptionPane.showMessageDialog(null, "Branch removed successfully");
                }
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
