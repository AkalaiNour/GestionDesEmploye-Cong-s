package DAO;

import Model.Employee;
import java.io.IOException;
import java.util.List;

public interface DataImportExport<T>{
    void importData(String fileName) throws IOException;
    void exportData(String fileName, List<Employee> employees) throws IOException; 
    
}
