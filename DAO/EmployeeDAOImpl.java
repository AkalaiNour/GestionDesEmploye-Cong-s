package DAO;

import DAO.GenericDAOI;
import Model.Employee;


import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;


public class EmployeeDAOImpl implements GenericDAOI<Employee>, DataImportExport<Employee> {
    private Connection connection = DBConnection.getConnection() ;
    private String sql;
    public EmployeeDAOImpl(){

    }

    @Override
    public Employee findById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        Employee employee = null;
        for (Employee e : findAll()){
            if(e.getId() == id){
                return e;
            }
        }
        return null;
    }
    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employee";
        Employee employee = null;
        ArrayList<Employee> employeesList = new ArrayList<Employee>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Employee e = new Employee(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("tel"),
                            rs.getString("email"),
                            rs.getDouble("salaire"),
                            Employee.Role.valueOf(rs.getString("role")),
                            Employee.Poste.valueOf(rs.getString("poste"))
                    );
                    employeesList.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesList ;
    }

    @Override
    public void create(Employee e) {
        sql = "insert into employee(nom , prenom , tel , email , salaire , role , poste) values (?,?,?,?,?,?,?);";
        try (PreparedStatement st = connection.prepareStatement(sql);){
            st.setString(1 , e.getNom());
            st.setString(2 , e.getPrenom());
            st.setString(3, e.getTel());
            st.setString(4 , e.getEmail());
            st.setDouble(5 , e.getSalaire());
            st.setString(6, e.getRole().name());
            st.setString(7, e.getPoste().name());
            st.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Employee e, int id) {
        String sql = "UPDATE employee SET nom = ?, prenom = ?, tel = ?, email = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, e.getNom());
            st.setString(2, e.getPrenom());
            st.setString(3, e.getTel());
            st.setString(4, e.getEmail());
            st.setDouble(5, e.getSalaire());
            st.setString(6, e.getRole().name());
            st.setString(7, e.getPoste().name());
            st.setInt(8, id);

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);

            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public   List<Employee.Role> findAllRoles() {
        return List.of(Employee.Role.values());
    }


    @Override
    public   List<Employee.Poste> findAllPostes() {
        return List.of(Employee.Poste.values());
    }

    @Override
    public void importData(String fileName) {
        String sql = "INSERT INTO employee (nom, prenom, tel, email, salaire, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\hp\\Desktop\\AKALAI\\AKALAI Copier\\src\\Employee.txt"));
             PreparedStatement stm = connection.prepareStatement(sql)) {
            String line= reader.readLine();
            while((line= reader.readLine()) != null){
                String[] data= line.split(",");
                if(data.length == 7){
                    stm.setString(1, data[0].trim());
                    stm.setString(2, data[1].trim());
                    stm.setString(3, data[2].trim());
                    stm.setString(4, data[3].trim());
                    stm.setDouble(5, Double.parseDouble(data[4].trim()));
                    stm.setString(6, data[5].trim());
                    stm.setString(7, data[6].trim());
                    stm.addBatch();
                }
            }
            stm.executeBatch();
            System.out.println("Data imported successfully");
        }catch(IOException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
public void exportData(String fileName, List<Employee> data) throws IOException {
    String query = "SELECT e.Nom, e.Prenom, e.Tel, e.Email, e.Salaire, e.Role, e.Poste " +
                   "FROM Employee e " +
                   "JOIN Holidays h ON e.id = h.employee_id";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

        writer.write("Nom, Prenom, Tel, Email, Salaire, Role, Poste");
        writer.newLine();

        while (rs.next()) {
            String line = String.format("%s, %s, %s, %s, %.2f, %s, %s",
                    rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Tel"),
                    rs.getString("Email"), rs.getDouble("Salaire"),
                    rs.getString("Role"), rs.getString("Poste"));
            writer.write(line);
            writer.newLine();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    

}
