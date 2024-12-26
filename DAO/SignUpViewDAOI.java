package DAO;

import Model.SignUpModel;
import java.util.List;

public interface SignUpViewDAOI {
    // Add a new user (sign up)
    void addUser(SignUpModel user);

    // Retrieve a user by username
    SignUpModel getUserById(int id);

    // Retrieve all users
    List<SignUpModel> getAllUsers();  // Change the return type to List<SignUpModel>

    SignUpModel getUserByUsernameAndPassword(String username, String password);

}