package cs335_package;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//https://www.baeldung.com/java-authentication-authorization-service

public class AuthDialog extends JDialog implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private String enteredUsername = null;
    private String enteredPassword = null;
    private boolean isLogin = true; // To track if the user is trying to login or register

    public AuthDialog(Frame owner) {
        super(owner, "Login or Register", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enteredUsername = usernameField.getText();
        enteredPassword = new String(passwordField.getPassword());

        if (e.getSource() == loginButton) {
            isLogin = true;
        } else if (e.getSource() == registerButton) {
            isLogin = false;
        }
        dispose(); // Close the dialog after button click
    }

    public String getEnteredUsername() {
        return enteredUsername;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public boolean isLoginAttempt() {
        return isLogin;
    }
}
