package Controller;

import DAO.SignUpViewDAOImpl;
import Model.SignUpModel;
import javax.swing.*;
import view.LoginView;
import view.SignUpView;

public class LoginController {
    private final LoginView view;
    private final SignUpViewDAOImpl dao;
    private LoginSuccessListener loginSuccessListener;

    public LoginController(LoginView view, SignUpViewDAOImpl dao) {
        this.view = view;
        this.dao = dao;

        // Attach listeners directly in the controller
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getCreateAccountButton().addActionListener(e -> handleCreateAccount());
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText(); // Get username from the LoginView
        String password = new String(view.getPasswordField().getPassword()); // Get password from the LoginView

        // Validate credentials using DAO (checking against stored data)
        SignUpModel user = dao.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(view, "Login Successful!");
            view.dispose(); // Close the login window

            // Notify the listener that login was successful
            if (loginSuccessListener != null) {
                loginSuccessListener.onLoginSuccess();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCreateAccount() {
        // Logic to handle account creation, typically redirecting to the sign-up view
        SignUpView signUpView = new SignUpView();
        new SignUpController(signUpView);
        view.dispose(); // Close the login window
    }

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    public interface LoginSuccessListener {
        void onLoginSuccess();
    }

    public static void main(String[] args) {
        // Create the LoginView instance and initialize the controller
        LoginView loginView = new LoginView();
        SignUpViewDAOImpl dao = new SignUpViewDAOImpl();
        new LoginController(loginView, dao);
    }
}