package Model;

public class SignUpModel {
    private  int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;

    public SignUpModel(int id, String firstName, String lastName, String username, String email, String password, String confirmPassword, String role) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    // Validate the user input
    public boolean validateSignUp() {
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty()) {
            return false;
        }

        // Password and confirm password must match
        if (!password.equals(confirmPassword)) {
            return false;
        }

        // Simple username validation (could be extended)
        if (username.length() < 3) {
            return false;
        }

        // Simple email validation (could be extended)
        if (!email.contains("@")) {
            return false;
        }

        // Simple password validation (could be extended)
        if (password.length() < 6) {
            return false;
        }

        // Simulating a successful sign-up
        return true;
    }

    // Getter methods
    public int getId(){
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getRole() {
        return role;
    }
}
