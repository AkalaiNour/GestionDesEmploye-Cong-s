package DAO;

import Model.SignUpModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignUpViewDAOImpl implements SignUpViewDAOI {

    private Connection connection = DBConnection.getConnection();
    private String sql;

    public SignUpViewDAOImpl() {
    }

    @Override
    public void addUser(SignUpModel user) {
        sql = "INSERT INTO signup (username, email, password, nom, prenom, ConfirmPass, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getConfirmPassword());
            stmt.setString(7, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SignUpModel getUserById(int id) {
        sql = "SELECT * FROM signup WHERE id = ?";
        SignUpModel user = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new SignUpModel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ConfirmPass"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<SignUpModel> getAllUsers() {
        sql = "SELECT * FROM signup";
        List<SignUpModel> usersList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SignUpModel user = new SignUpModel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ConfirmPass"),
                        rs.getString("role")
                );
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }
    @Override
public SignUpModel getUserByUsernameAndPassword(String username, String password) {
    sql = "SELECT * FROM signup WHERE username = ? AND password = ?";
    SignUpModel user = null;
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                user = new SignUpModel(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("ConfirmPass"),
                    rs.getString("role")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}

}