package Model;

import DAO.EmployeeDAOImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EmployeeModel {
    private final EmployeeDAOImpl dao;

    public EmployeeModel(EmployeeDAOImpl dao) {
        this.dao = dao;
    }

    public void addEmployee(Employee emp) {
        dao.create(emp);
    }

    public List<Employee> getAllEmployees() {
        return dao.findAll();
    }

    public void deleteEmployee(int id) {
        dao.delete(id);
    }

    public void updateEmployee(Employee emp, int id) {
        dao.update(emp, id);
    }

    public static int parseEmployeeId(String selectedEmployeeString) {
        try {
            return Integer.parseInt(selectedEmployeeString.split("\\|")[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid employee format.");
        }
    }
    private boolean chackFileExists(File file){
        if(!file.exists()){
            throw new IllegalArgumentException("Le fichier n'existe pas: " + file.getPath());
        }
        return true;
    }

    private boolean checkIsFile(File file){
        if(!file.isFile()){
            throw new IllegalArgumentException("Le chemin spécifié n'est pas un fichier : " + file.getPath());
        }
        return true;
    }
    private boolean checkIsReadable(File file){
        if(!file.canRead()){
            throw new IllegalArgumentException("Le fichier n'est pas lisible " + file.getPath());

        }
        return true;
    }
    public void importdata(String file) {
        dao.importData(file);
    }
     public void exportData(String filePath, List<Employee> employees) throws IOException {
        dao.exportData(filePath, employees);
    }

}
    

