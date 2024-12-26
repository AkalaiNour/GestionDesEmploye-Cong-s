package view;

import Model.Employee;
import java.awt.*;
import javax.swing.*;

public class SignUpView extends JFrame {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField usernameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JComboBox<Employee.Role> roleComboBox;
    private final JButton signUpButton;

    public SignUpView() {
        // Frame setup
        setTitle("Sign Up");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false); // Prevent resizing

        // Set a nice background color
        getContentPane().setBackground(new Color(235, 235, 235));

        // Use GroupLayout for a modern layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Create components
        JLabel firstNameLabel = new JLabel("First Name (Nom):");
        JLabel lastNameLabel = new JLabel("Last Name (Prenom):");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JLabel roleLabel = new JLabel("Role:");

        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Role options from Employee.Role enum
        roleComboBox = new JComboBox<>(Employee.Role.values());

        signUpButton = new JButton("Sign Up");

        // Customize text fields and button size
        firstNameField.setPreferredSize(new Dimension(250, 25));
        lastNameField.setPreferredSize(new Dimension(250, 25));
        usernameField.setPreferredSize(new Dimension(250, 25));
        emailField.setPreferredSize(new Dimension(250, 25));
        passwordField.setPreferredSize(new Dimension(250, 25));
        confirmPasswordField.setPreferredSize(new Dimension(250, 25));
        roleComboBox.setPreferredSize(new Dimension(250, 25));
        signUpButton.setPreferredSize(new Dimension(250, 35));

        // Customize sign-up button
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Set up layout
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(firstNameLabel)
                .addComponent(firstNameField)
                .addComponent(lastNameLabel)
                .addComponent(lastNameField)
                .addComponent(usernameLabel)
                .addComponent(usernameField)
                .addComponent(emailLabel)
                .addComponent(emailField)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addComponent(confirmPasswordLabel)
                .addComponent(confirmPasswordField)
                .addComponent(roleLabel)
                .addComponent(roleComboBox)
                .addComponent(signUpButton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(firstNameLabel)
                .addComponent(firstNameField)
                .addGap(10)
                .addComponent(lastNameLabel)
                .addComponent(lastNameField)
                .addGap(10)
                .addComponent(usernameLabel)
                .addComponent(usernameField)
                .addGap(10)
                .addComponent(emailLabel)
                .addComponent(emailField)
                .addGap(10)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addGap(10)
                .addComponent(confirmPasswordLabel)
                .addComponent(confirmPasswordField)
                .addGap(10)
                .addComponent(roleLabel)
                .addComponent(roleComboBox)
                .addGap(20)
                .addComponent(signUpButton)
        );

        setVisible(true);
    }

    // Getter methods for the fields and sign-up button
    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JComboBox<Employee.Role> getRoleComboBox() {
        return roleComboBox;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpView());
    }
}