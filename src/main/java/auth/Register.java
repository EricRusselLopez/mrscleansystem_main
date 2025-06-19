package src.main.java.auth;

import java.awt.Component;
import javax.swing.ImageIcon;
import src.main.java.App;
import src.main.java.components.helper.Branches;
import src.utils.CustomImageResizer;

public class Register extends javax.swing.JPanel {

    private App app;
    Branches branches = new Branches();

    public Register(App app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {

        fnameField = new javax.swing.JTextField();
        lnameField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        cPasswordField = new javax.swing.JPasswordField();
        requestBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();
        branchField = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        genderField = new javax.swing.JComboBox<>();

        fnameField.setBorder(javax.swing.BorderFactory.createTitledBorder("Firstname"));
        fnameField.setOpaque(false);

        lnameField.setBorder(javax.swing.BorderFactory.createTitledBorder("Lastname"));
        lnameField.setOpaque(false);

        emailField.setBorder(javax.swing.BorderFactory.createTitledBorder("Email address (Contact)"));
        emailField.setOpaque(false);

        passwordField.setBorder(javax.swing.BorderFactory.createTitledBorder("Password"));
        passwordField.setOpaque(false);

        cPasswordField.setBorder(javax.swing.BorderFactory.createTitledBorder("Confirm password"));
        cPasswordField.setOpaque(false);

        requestBtn.setBackground(new java.awt.Color(153, 255, 255));
        requestBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        requestBtn.setForeground(new java.awt.Color(0, 0, 0));
        requestBtn.setText("Request approval");
        requestBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        requestBtn.setFocusPainted(false);
        requestBtn.addActionListener(e -> new Authenticator(app).requestApproval(fnameField.getText(),
                lnameField.getText(), emailField.getText(), new String(passwordField.getPassword()),
                new String(cPasswordField.getPassword()), branchField.getSelectedItem().toString(),
                genderField.getSelectedItem().toString(), new Component[] { fnameField, lnameField, emailField,
                        passwordField, cPasswordField, branchField, genderField, requestBtn, loginBtn }));

        loginBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        loginBtn.setText("Login");
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(e -> app.showLogin());

        branchField.setFont(new java.awt.Font("Segoe UI", 1, 12));
        branches.updateBranchesLive(branchField);
        branchField.setBorder(null);
        branchField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        branchField.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 16));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(
                "<html><p style='width:300px;'>Welcome to <b>Mrs. Clean Laundry Shop</b>! We’re glad you’re here. With today’s busy lifestyle, we aim to make laundry convenient and stress-free for students, professionals, and families. Serving <b>Baliwag</b> and <b>San Rafael, Bulacan</b>, we offer reliable and affordable services to meet your needs. Thank you for trusting <b>Mrs. Clean</b> — your convenience is our priority.</p></html>");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageIcon labelIcon = new CustomImageResizer()
                .resizeIcon(new ImageIcon("./src/assets/Laundry-removebg-preview.png"), 180, 170);
        jLabel2.setIcon(labelIcon);

        genderField.setFont(new java.awt.Font("Segoe UI", 1, 12));
        genderField.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Select your gender", "Male", "Female" }));
        genderField.setBorder(null);
        genderField.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        genderField.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 554,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 530,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(emailField)
                                        .addComponent(passwordField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cPasswordField, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(loginBtn, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(requestBtn, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(branchField, javax.swing.GroupLayout.Alignment.TRAILING, 0,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(fnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 181,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lnameField, javax.swing.GroupLayout.DEFAULT_SIZE, 181,
                                                        Short.MAX_VALUE))
                                        .addComponent(genderField, javax.swing.GroupLayout.Alignment.TRAILING, 0,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(53, 53, 53)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(54, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(branchField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(genderField, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(requestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)));
    }

    private javax.swing.JButton requestBtn;
    private javax.swing.JButton loginBtn;
    private javax.swing.JComboBox<String> branchField;
    private javax.swing.JComboBox<String> genderField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField cPasswordField;
    private javax.swing.JTextField fnameField;
    private javax.swing.JTextField lnameField;
    private javax.swing.JTextField emailField;

}
