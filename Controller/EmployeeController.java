package Controller;

import Model.Employee;
import Model.EmployeeModel;
import Model.HolidayModel;
import view.Vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EmployeeController {
    private final EmployeeModel model;
    private final Vue view;

    public EmployeeController(EmployeeModel model, Vue view) {
        this.model = model;
        this.view = view;

        view.importButton.addActionListener(l -> {
            try {
                handleImport();
            } catch (IOException e) {
                view.showErrorMessage("Erreur lors de l'importation");
            }
        });

        view.exportButton.addActionListener(l -> {
            try {
                handleExport();
            } catch (IOException e) {
                view.showErrorMessage("Erreur lors de l'exportation");
            }
        });

        initializeListeners();
    }

    private void initializeListeners() {
        view.getAjouter().addActionListener(e -> {
            try {
                Employee emp = new Employee(
                        0,
                        view.getNom().getText(),
                        view.getPrenom().getText(),
                        view.getTel().getText(),
                        view.getEmail().getText(),
                        Double.parseDouble(view.getSal().getText()),
                        Employee.Role.valueOf((String) view.getRoleComboBox().getSelectedItem()),
                        Employee.Poste.valueOf((String) view.getPostesComboBox().getSelectedItem())
                );
                model.addEmployee(emp);
                JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getAfficher().doClick();
                
                // Clear input fields
                view.getNom().setText("");
                view.getPrenom().setText("");
                view.getTel().setText("");
                view.getEmail().setText("");
                view.getSal().setText("");
                view.getRoleComboBox().setSelectedIndex(0);
                view.getPostesComboBox().setSelectedIndex(0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid salary value!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getAfficher().addActionListener(e -> {
            // Get all employees from the model
            List<Employee> allEmployees = model.getAllEmployees();
            
            // Prepare the data for the JTable
            List<Object[]> employeeData = new ArrayList<>();
            for (Employee emp : allEmployees) {
                employeeData.add(new Object[] {
                    emp.getId(), 
                    emp.getNom(), 
                    emp.getPrenom(), 
                    emp.getTel(), 
                    emp.getEmail(), 
                    emp.getSalaire(), 
                    emp.getPoste().name(), 
                    emp.getRole().name()
                });
            }
            
            // Convert List to 2D array for JTable
            Object[][] employeeArray = employeeData.toArray(new Object[0][]);
            
            // Define column names
            String[] columnNames = {"ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"};
            
            // Create a new JTable with the updated data
            JTable updatedEmployeeTable = new JTable(employeeArray, columnNames);
            
            // Add ListSelectionListener to the JTable
            updatedEmployeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()) {
                        int selectedRow = updatedEmployeeTable.getSelectedRow();
                        if (selectedRow != -1) {
                            System.out.println("Row selected: " + selectedRow);
                            System.out.println("Nom: " + updatedEmployeeTable.getValueAt(selectedRow, 1));
                            System.out.println("Prenom: " + updatedEmployeeTable.getValueAt(selectedRow, 2));
                            System.out.println("Tel: " + updatedEmployeeTable.getValueAt(selectedRow, 3));
                            System.out.println("Email: " + updatedEmployeeTable.getValueAt(selectedRow, 4));
                            System.out.println("Salaire: " + updatedEmployeeTable.getValueAt(selectedRow, 5));
                            System.out.println("Role: " + updatedEmployeeTable.getValueAt(selectedRow, 7));
                            System.out.println("Poste: " + updatedEmployeeTable.getValueAt(selectedRow, 6));
                            
                            view.getNom().setText((String) updatedEmployeeTable.getValueAt(selectedRow, 1));
                            view.getPrenom().setText((String) updatedEmployeeTable.getValueAt(selectedRow, 2));
                            view.getTel().setText((String) updatedEmployeeTable.getValueAt(selectedRow, 3));
                            view.getEmail().setText((String) updatedEmployeeTable.getValueAt(selectedRow, 4));
                            view.getSal().setText(String.valueOf(updatedEmployeeTable.getValueAt(selectedRow, 5)));
                            view.getRoleComboBox().setSelectedItem(updatedEmployeeTable.getValueAt(selectedRow, 7));
                            view.getPostesComboBox().setSelectedItem(updatedEmployeeTable.getValueAt(selectedRow, 6));
                        }
                    }
                }
            });
            
            // Set the updated JTable to the view
            view.setEmployeeTable(updatedEmployeeTable);
            
            // Refresh the p3 panel to display the updated table
            JPanel p3 = view.getP3();
            p3.removeAll();  // Remove old table
            JScrollPane scrollPane = new JScrollPane(updatedEmployeeTable);
            p3.add(scrollPane);  // Add the new table in a JScrollPane
            p3.revalidate();    // Revalidate the panel
            p3.repaint();       // Repaint the panel
        });
        

        view.getSupprimer().addActionListener(e -> {
            // Get the selected row index in the JTable
            int selectedRow = view.getEmployeeTable().getSelectedRow();
            
            if (selectedRow == -1) {
                // No row is selected
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Get the employee ID from the selected row (assuming the ID is in the first column)
            int employeeId = (int) view.getEmployeeTable().getValueAt(selectedRow, 0); 
        
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Call the model's delete method to delete the employee
                model.deleteEmployee(employeeId);  // Assuming model is an instance of your model class
                
                JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the table or update the list after deletion
                view.getAfficher().doClick();
            }
        });
        

        view.getModifier().addActionListener(e -> {
            // Get the selected row index from the JTable
            int selectedRow = view.getEmployeeTable().getSelectedRow();
            
            // Check if a row is selected
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                // Extract the employee data from the selected row
                int id = (int) view.getEmployeeTable().getValueAt(selectedRow, 0); // Assuming the ID is in the first column
                String nom = (String) view.getEmployeeTable().getValueAt(selectedRow, 1);
                String prenom = (String) view.getEmployeeTable().getValueAt(selectedRow, 2);
                String tel = (String) view.getEmployeeTable().getValueAt(selectedRow, 3);
                String email = (String) view.getEmployeeTable().getValueAt(selectedRow, 4);
                double salaire = (double) view.getEmployeeTable().getValueAt(selectedRow, 5);
                Employee.Poste poste = Employee.Poste.valueOf((String) view.getEmployeeTable().getValueAt(selectedRow, 6));
                Employee.Role role = Employee.Role.valueOf((String) view.getEmployeeTable().getValueAt(selectedRow, 7));
        
                // Create the Employee object with updated data from the text fields and combo boxes
                Employee emp = new Employee(
                        id,
                        view.getNom().getText(),
                        view.getPrenom().getText(),
                        view.getTel().getText(),
                        view.getEmail().getText(),
                        Double.parseDouble(view.getSal().getText()),
                        role,
                        poste
                );
        
                // Update the employee in the model
                model.updateEmployee(emp, id);
                JOptionPane.showMessageDialog(view, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the table view after the update
                view.getAfficher().doClick(); // Trigger the "Afficher" action to reload the employee list
        
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid salary value!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
    private void handleImport() throws IOException{
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier CSV", "txt"));

        if(fileChooser.showOpenDialog(view)== JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            model.importdata(filePath);
            view.showSuccessMessage("Importation réussie");
        }
    }

    public void handleExport() throws IOException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier CSV", "csv"));

        if(fileChooser.showSaveDialog(view)== JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if(!filePath.toLowerCase().endsWith(".txt")){
                filePath += ".csv";
            }
            List<Employee> employees = model.getAllEmployees();
            HolidayModel holidayModel = new HolidayModel(null);
            holidayModel.exportData(filePath, employees);
            view.showSuccessMessage("Exportation réussie");
        }
    }
}