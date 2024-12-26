package Controller;

import Model.SignUpModel;
import DAO.SignUpViewDAOImpl;
import view.LoginView;
import view.SignUpView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpController {

    private final SignUpView signUpView;
    private final SignUpViewDAOImpl signUpDAO;

    public SignUpController(SignUpView signUpView) {
        this.signUpView = signUpView;
        this.signUpDAO = new SignUpViewDAOImpl();

        // Add an action listener to the sign-up button
        signUpView.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
    }

    private void handleSignUp() {
    // Retrieve user input from the view
    String firstName = signUpView.getFirstNameField().getText().trim();
    String lastName = signUpView.getLastNameField().getText().trim();
    String username = signUpView.getUsernameField().getText().trim();
    String email = signUpView.getEmailField().getText().trim();
    String password = new String(signUpView.getPasswordField().getPassword());
    String confirmPassword = new String(signUpView.getConfirmPasswordField().getPassword());
    String role = signUpView.getRoleComboBox().getSelectedItem().toString();

    // Create a SignUpModel instance with the provided data
    SignUpModel newUser = new SignUpModel(0, firstName, lastName, username, email, password, confirmPassword, role);

    // Validate the user input
    if (!newUser.validateSignUp()) {
        JOptionPane.showMessageDialog(signUpView, "Invalid input! Please check your details.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Save the new user to the database
    try {
        signUpDAO.addUser(newUser);
        JOptionPane.showMessageDialog(signUpView, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Redirect to LoginView
        signUpView.dispose(); // Close the current SignUpView
        LoginView loginView = new LoginView();
        new LoginController(loginView, signUpDAO); // Pass DAO to the LoginController
        loginView.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(signUpView, "Error while creating account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void clearFields() {
        signUpView.getFirstNameField().setText("");
        signUpView.getLastNameField().setText("");
        signUpView.getUsernameField().setText("");
        signUpView.getEmailField().setText("");
        signUpView.getPasswordField().setText("");
        signUpView.getConfirmPasswordField().setText("");
    }

    public static void main(String[] args) {
        // Create the SignUpView instance and initialize the controller
        SignUpView signUpView = new SignUpView();
        new SignUpController(signUpView);
    }
}