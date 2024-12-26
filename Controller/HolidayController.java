package Controller;

import DAO.*;
import Model.Employee;
import Model.EmployeeModel;
import Model.Holiday;
import Model.HolidayModel;
import view.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HolidayController {
    private final HolidayModel model;
    private final Vue view;
    private  EmployeeModel employeeModel ;
    public HolidayController(HolidayModel model, Vue view , EmployeeModel employeeModel) {

        this.model = model;
        this.view = view;
        this.employeeModel = employeeModel  ;
        refreshTable();
        view.ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHoliday();
                refreshTable();
            }
        });

        view.modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyHoliday();
                refreshTable();
            }
        });

        view.supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteHoliday();
                refreshTable();
            }
        });

        refreshTable();
    }

    private void addHoliday() {
        try {
            String employeeName = (String) view.employeeComboBox.getSelectedItem();
            String holidayType = (String) view.typeCongeComboBox.getSelectedItem();
            Date startDate = (Date) view.startDateSpinner.getValue();
            Date endDate = (Date) view.endDateSpinner.getValue();
            long diffInMillis = endDate.getTime() - startDate.getTime();
            long days = diffInMillis / (1000 * 60 * 60 * 24);
            System.out.println("Days between startDate and endDate: " + days);
            if(days > 20){
                JOptionPane.showMessageDialog(view, "Solde de jours insuffisant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            if (employeeName == null || holidayType == null || startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(view, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(view, "La date de début doit être avant la date de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Employee employee = findEmployeeByName(employeeName) ;
            System.out.println(employee);
            System.out.println(employee.getId());
            if (employee == null) {
                JOptionPane.showMessageDialog(view, "Employé introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int soldeReste = 0;
            Holiday holiday = new Holiday(0, startDate, endDate, Holiday.typeHoliday.valueOf(holidayType), soldeReste, employee.getId() ,"" );
            System.out.println(holiday);
            model.addHoliday(holiday);
            JOptionPane.showMessageDialog(view, "Congé ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void modifyHoliday() {
        try {
            int selectedRow = view.congeTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un congé à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int holidayId = Integer.parseInt(view.congeTable.getValueAt(selectedRow, 0).toString());

            String employeeName = (String) view.employeeComboBox.getSelectedItem();
            Holiday.typeHoliday type = Holiday.typeHoliday.valueOf((String) view.typeCongeComboBox.getSelectedItem());
            Date startDate = (Date) view.startDateSpinner.getValue();
            Date endDate = (Date) view.endDateSpinner.getValue();

            Employee selectedEmployee = findEmployeeByName(employeeName);

            if (selectedEmployee == null) {
                JOptionPane.showMessageDialog(view, "Employé non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = formatter.format(startDate);
            String formattedEndDate = formatter.format(endDate);

            java.sql.Date sqlStartDate = new java.sql.Date(formatter.parse(formattedStartDate).getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(formatter.parse(formattedEndDate).getTime());

            Holiday updatedHoliday = new Holiday(holidayId, sqlStartDate, sqlEndDate, type, 0, selectedEmployee.getId() , "");

            System.out.println("Updating holiday with ID: " + holidayId);
            System.out.println("New values - Date_de: " + sqlStartDate + ", Date_fin: " + sqlEndDate + ", Type: " + type + ", Employee ID: " + selectedEmployee.getId());

            long diffInMillis = endDate.getTime() - startDate.getTime();
            long days = diffInMillis / (1000 * 60 * 60 * 24);
            System.out.println("Days between startDate and endDate: " + days);
            if(days > updatedHoliday.getSolde_reste()){
                JOptionPane.showMessageDialog(view, "Solde de jours insuffisant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            model.updateHoliday(updatedHoliday, holidayId);
            JOptionPane.showMessageDialog(view, "Congé modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void deleteHoliday() {
        try {
            int selectedRow = view.congeTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un congé à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Êtes-vous sûr de vouloir supprimer ce congé ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            int holidayId = Integer.parseInt(view.congeTable.getValueAt(selectedRow, 0).toString());

            model.deleteHolidayEmployee(holidayId);
            JOptionPane.showMessageDialog(view, "Congé supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        List<Holiday> holidays = model.findAllHolidays();
        Object[][] data = new Object[holidays.size()][5];
        for (int i = 0; i < holidays.size(); i++) {
            Holiday holiday = holidays.get(i);


            data[i][0] = holiday.getHoliday_id();
            data[i][1] = holiday.getNom() ;
            data[i][2] = holiday.getDate_de().toString();
            data[i][3] = holiday.getDate_fin().toString();
            data[i][4] = holiday.getType_holiday().name();
        }

        String[] columnNames = {"ID", "Employé", "Date de Début", "Date de Fin", "Type"};
        view.congeTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private Employee findEmployeeByName(String name) {
        for (Employee e : employeeModel.getAllEmployees()){
            if((e.getNom() + ' ' +e.getPrenom()).equals(name)){

                return e ; }

        }

        return null;
    }
}
