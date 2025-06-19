package src.main.java.auth;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONArray;
import org.json.JSONObject;

import src.main.java.components.helper.Branches;
import src.server.java.ServerURL;
import src.utils.CustomBorder;
import src.utils.CustomImageResizer;

public class ForgotPassword extends ServerURL implements CustomBorder {
    Branches branches = new Branches();
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void requestForgotPassword() {
        JDialog dialog = new JDialog((Frame) null, "Forgot Password", true);
        dialog.setSize(460, 530);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JTextField emailField = new JTextField();
        setInputBorderColor(emailField, "Enter your email", Color.BLACK);
        JComboBox<String> branchCombo = new JComboBox<>();
        branches.updateBranchesLive(branchCombo);

        JTextField vcodeField = new JTextField();
        setInputBorderColor(vcodeField, "Enter verification code", Color.GRAY);
        vcodeField.setEnabled(false);
        final boolean[] codeVerified = { false };

        JPasswordField newPassField = new JPasswordField();
        setInputBorderColor(newPassField, "New password", Color.GRAY);
        newPassField.setEnabled(false);
        JPasswordField confirmPassField = new JPasswordField();
        setInputBorderColor(confirmPassField, "Confirm password", Color.GRAY);
        confirmPassField.setEnabled(false);

        JButton findButton = new JButton("Find your account");
        ImageIcon labelFindIcon = new CustomImageResizer().resizeIcon(new ImageIcon("./src/icons/user-avatar.png"), 25,
                25);
        findButton.setIcon(labelFindIcon);
        findButton.setFocusPainted(false);
        JButton resetButton = new JButton("Reset Password");
        resetButton.setFocusPainted(false);
        resetButton.setEnabled(false);
        resetButton.setBackground(Color.CYAN);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(emailField);
        panel.add(branchCombo);
        panel.add(findButton);
        panel.add(vcodeField);
        panel.add(newPassField);
        panel.add(confirmPassField);
        panel.add(resetButton);
        panel.add(cancelButton);
        dialog.add(panel);

        findButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String branch = branchCombo.getSelectedItem().toString();
            Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (email.isEmpty()) {
                setInputBorderColor(emailField, "Enter your email", Color.RED);
                return;
            }

            if (!emailMatcher.matches()) {
                setInputBorderColor(emailField, "Enter your email", Color.RED);
                JOptionPane.showMessageDialog(dialog, "Please enter your email address correctly", "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (branch.equals("Select branch")) {
                JOptionPane.showMessageDialog(dialog, "Please select your branch.", "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            findButton.setEnabled(false);

            new SwingWorker<JSONObject, Void>() {
                @Override
                protected JSONObject doInBackground() throws Exception {
                    try {
                        String postData = "action=request" +
                                "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                                "&branch=" + URLEncoder.encode(branch, StandardCharsets.UTF_8) +
                                "&client_fetch_token_request=;;mslaundryshop2025;;";

                        return parseJsonResponse(createPostConnection("forgot_password.php", postData));
                    } catch (Exception ex) {
                        return new JSONObject().put("response", false)
                                .put("error", "Connection error: " + ex.getMessage());
                    }
                }

                @Override
                protected void done() {
                    try {
                        JSONObject resp = get();

                        if (resp.optBoolean("response", false)) {
                            // Update UI on EDT
                            SwingUtilities.invokeLater(() -> {
                                findButton.setEnabled(false);
                                emailField.setEnabled(false);
                                branchCombo.setEnabled(false);
                                vcodeField.setEnabled(true);
                                setInputBorderColor(vcodeField, "Enter verification code", Color.BLACK);

                                JOptionPane.showMessageDialog(dialog,
                                        "A 6 digit verification code has been sent to your email address. " +
                                                "Please check your inbox to proceed with resetting your password.",
                                        "Password Reset Code Sent",
                                        JOptionPane.INFORMATION_MESSAGE);
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                findButton.setEnabled(true); // Re-enable button on failure
                                JOptionPane.showMessageDialog(dialog,
                                        resp.optString("error", "Unknown error occurred"),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            findButton.setEnabled(true); // Re-enable button on error
                            JOptionPane.showMessageDialog(dialog,
                                    "Failed to process request: " + ex.getMessage(),
                                    "Connection Error",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                }
            }.execute();
        });

        // --- CODE verification ---
        vcodeField.getDocument().addDocumentListener(new DocumentListener() {
            void verifyCode() {
                setInputBorderColor(vcodeField, "Enter verification code", Color.BLACK);
                String code = vcodeField.getText().trim();
                if (code.length() >= 6) {
                    try {
                        String postData = "action=verify_code" +
                                "&email=" + URLEncoder.encode(emailField.getText(), StandardCharsets.UTF_8) +
                                "&branch="
                                + URLEncoder.encode(branchCombo.getSelectedItem().toString(), StandardCharsets.UTF_8) +
                                "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                                "&client_fetch_token_request=;;mslaundryshop2025;;";
                        JSONObject resp = parseJsonResponse(createPostConnection("forgot_password.php", postData));
                        codeVerified[0] = resp.getBoolean("response");
                        if (codeVerified[0]) {
                            newPassField.setEnabled(true);
                            confirmPassField.setEnabled(true);
                            setInputBorderColor(newPassField, "New password", Color.BLACK);
                            setInputBorderColor(confirmPassField, "Confirm password", Color.BLACK);
                        } else {
                            setInputBorderColor(vcodeField, "Incorrect code", Color.RED);
                            newPassField.setEnabled(false);
                            confirmPassField.setEnabled(false);
                        }
                    } catch (Exception ex) {
                        codeVerified[0] = false;
                    }
                }
                combinedCheck();
            }

            public void insertUpdate(DocumentEvent e) {
                verifyCode();
            }

            public void removeUpdate(DocumentEvent e) {
                verifyCode();
            }

            public void changedUpdate(DocumentEvent e) {
                verifyCode();
            }

            void combinedCheck() {
                String p1 = new String(newPassField.getPassword()).trim();
                String p2 = new String(confirmPassField.getPassword()).trim();
                resetButton.setEnabled(codeVerified[0] && !p1.isEmpty() && p1.equals(p2));
            }
        });

        DocumentListener passListener = new DocumentListener() {
            void checkMatch() {
                String p1 = new String(newPassField.getPassword()).trim();
                String p2 = new String(confirmPassField.getPassword()).trim();

                boolean validLength = p1.length() >= 8;
                boolean match = p1.equals(p2);

                if (!validLength) {
                    setInputBorderColor(newPassField, "🔒 At least 8 characters", Color.RED);
                } else {
                    setInputBorderColor(newPassField, "New password", Color.BLACK);
                }

                if (!match && !p2.isEmpty()) {
                    setInputBorderColor(confirmPassField, "❌ Passwords do not match", Color.RED);
                } else if (!p2.isEmpty()) {
                    setInputBorderColor(confirmPassField, "Confirm password", Color.BLACK);
                }

                resetButton.setEnabled(codeVerified[0] && validLength && match);
            }

            public void insertUpdate(DocumentEvent e) {
                checkMatch();
            }

            public void removeUpdate(DocumentEvent e) {
                checkMatch();
            }

            public void changedUpdate(DocumentEvent e) {
                checkMatch();
            }
        };

        newPassField.getDocument().addDocumentListener(passListener);
        confirmPassField.getDocument().addDocumentListener(passListener);

        resetButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String branch = branchCombo.getSelectedItem().toString();
            String newPass = new String(newPassField.getPassword()).trim();
            try {
                String postData = "action=reset" +
                        "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                        "&branch=" + URLEncoder.encode(branch, StandardCharsets.UTF_8) +
                        "&new_password=" + URLEncoder.encode(newPass, StandardCharsets.UTF_8) +
                        "&confirm_password=" + URLEncoder.encode(newPass, StandardCharsets.UTF_8) +
                        "&client_fetch_token_request=;;mslaundryshop2025;;";
                JSONObject resp = parseJsonResponse(createPostConnection("forgot_password.php", postData));
                if (resp.getBoolean("response")) {
                    JOptionPane.showMessageDialog(null, "Password reset successfully.");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, resp.optString("error", "Error resetting"), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
            }
        });

        cancelButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String branch = branchCombo.getSelectedItem().toString();
            String code = vcodeField.getText().trim();
            if (!email.isEmpty() || !code.isEmpty() || !branch.equals("Select branch")) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to cancel this?",
                        "Cancel reset password",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        createPostConnection("forgot_password.php",
                                "action=cancel&client_fetch_token_request=;;mslaundryshop2025;;");
                    } catch (Exception ignored) {
                    }
                    dialog.dispose();
                }
            } else {
                dialog.dispose();
            }
        });

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
