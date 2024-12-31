package DAO;

import Model.Employee;
import Model.Employee.Poste;
import Model.Employee.Role;
import Model.Holiday;
import Model.Holiday.typeHoliday;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.* ;
import java.util.ArrayList;
import java.util.List;

public class GenericDAOImpl implements GenericDAOI<Holiday> {
    private Connection connection = DBConnection.getConnection() ;
    private String sql ;

    public GenericDAOImpl(){}

    @Override
    public void create(Holiday e) {

        sql = "INSERT INTO Holiday (date_de, date_fin, type_holiday, solde_reste , employee_id) VALUES (?,?,?,?,?);";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, new Date(e.getDate_de().getTime()));
            st.setDate(2, new Date(e.getDate_fin().getTime()));
            st.setString(3, e.getType_holiday().name());
            st.setInt(4, e.getSolde_reste());
            st.setInt(5, e.getEmployeeId());
            st.executeUpdate();
            System.out.println(e.getEmployeeId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Holiday findById(int id) {
        sql = "SELECT holiday_id,date_de,date_fin,type_holiday,solde_reste,concat(nom,\" \",prenom) as nom, employee_id   FROM holiday JOIN employee ON holiday.employee_id = employee.id WHERE holiday_id = ?;";
        Holiday holiday = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    holiday = new Holiday(
                            rs.getInt("holiday_id") ,
                            rs.getDate("date_de"),
                            rs.getDate("date_fin"),
                            typeHoliday.valueOf(rs.getString("type_holiday")) ,
                            rs.getInt("solde_reste") ,rs.getInt("employee_id") ,
                            rs.getString("nom")

                    );
                    holiday.setHoliday_id(rs.getInt("holiday_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holiday;
    }

    @Override
    public List<Holiday> findAll() {
        sql = "SELECT holiday_id,date_de,date_fin,type_holiday,solde_reste,concat(nom,\" \",prenom) as nom, employee_id  FROM holiday JOIN employee ON holiday.employee_id = employee.id;";
        List<Holiday> holidays = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Holiday holiday = new Holiday(
                        rs.getInt("holiday_id") ,
                        rs.getDate("date_de"),
                        rs.getDate("date_fin"),
                        typeHoliday.valueOf(rs.getString("type_holiday")) ,
                        rs.getInt("solde_reste") ,
                        rs.getInt("employee_id"),
                        rs.getString("nom")

                );
                holiday.setHoliday_id(rs.getInt("holiday_id"));
                holidays.add(holiday);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holidays;
    }


    public  static List<Holiday.typeHoliday> findAllTypes() {
        return List.of(Holiday.typeHoliday.values());
    }
    @Override
    public void update(Holiday entity, int id) {
        sql = "UPDATE Holiday SET date_de = ?, date_fin = ?, type_holiday = ?, solde_reste = ? , employee_id=? WHERE holiday_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("Executing SQL: " + sql);
            System.out.println("With values: "
                    + "Date_de: " + entity.getDate_de()
                    + ", Date_fin: " + entity.getDate_fin()
                    + ", Type_holiday: " + entity.getType_holiday()
                    + ", Solde_reste: " + entity.getSolde_reste()
                    + ", Holiday_id: " + id);

            stmt.setDate(1, new java.sql.Date(entity.getDate_de().getTime()));
            stmt.setDate(2, new java.sql.Date(entity.getDate_fin().getTime()));
            stmt.setString(3, entity.getType_holiday().name());
            stmt.setInt(4, entity.getSolde_reste());
            stmt.setInt(5, entity.getEmployeeId());
            stmt.setInt(6, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Holiday updated successfully.");
            } else {
                System.out.println("No holiday found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        sql = "DELETE FROM Holiday WHERE holiday_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Holiday deleted successfully.");
            } else {
                System.out.println("No holiday found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Role> findAllRoles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllRoles'");
    }

    @Override
    public List<Poste> findAllPostes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllPostes'");
    }

    

}
