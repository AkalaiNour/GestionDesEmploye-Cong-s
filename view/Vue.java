package view;

import DAO.*;
import Model.Employee;
import Model.Holiday;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Vue extends JFrame {
    JTabbedPane tabbedPane = new JTabbedPane();
    public JPanel p0;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    public JPanel getP3(){
        return p3;
    }
    public Object[][] data;
    public JTable congeTable;
    private JPanel p4;
    private JComboBox<String> postesComboBox;
    private JComboBox<String> roleComboBox;
    private JButton ajouter;
    private JButton modifier;
    private JButton supprimer;
    private JButton afficher;
    public JButton importButton;
    public JButton exportButton;
    public JButton ajouterButton = new JButton("Ajouter");
    public JButton modifierButton = new JButton("Modifier");
    public JButton supprimerButton = new JButton("Supprimer");
    public JSpinner startDateSpinner;
    public JSpinner endDateSpinner;
    public JComboBox<String> employeeComboBox;
    private JTextField tel;
    private JTextField sal;
    private JTextField nom;
    private JTextField prenom;
    private JTextField email;
    private JTable employeeTable;
    public JTable getEmployeeTable() {
        return employeeTable;
    }
    public void setEmployeeTable(JTable employeeTable) {
        this.employeeTable = employeeTable;
    }
    public JComboBox<String> typeCongeComboBox;

    public Vue() {
        setTitle("App");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmployeeDAOImpl eImp = new EmployeeDAOImpl();
        p0 = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Nom de l'Employé:"));
        List<String> employeeNames = new ArrayList<>();
        for (Employee employee : eImp.findAll()) {
            employeeNames.add(employee.getNom() + ' ' + employee.getPrenom());
        }
        employeeComboBox = new JComboBox<>(employeeNames.toArray(new String[0]));
        formPanel.add(employeeComboBox);
        formPanel.add(new JLabel("Type de Congé:"));
        List<Holiday.typeHoliday> conge = GenericDAOImpl.findAllTypes();
        String[] typesConge = conge.stream()
                .map(Enum::name)
                .toArray(String[]::new);
        typeCongeComboBox = new JComboBox<>(typesConge);
        formPanel.add(typeCongeComboBox);
        formPanel.add(new JLabel("Date de Début:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);
        formPanel.add(startDateSpinner);
        formPanel.add(new JLabel("Date de Fin:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        endDateSpinner.setEditor(endDateEditor);
        formPanel.add(endDateSpinner);
        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Employé", "Date de Début", "Date de Fin", "Type"};
        Object[][] data = {
        };
        congeTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(congeTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        panel2.add(formPanel, BorderLayout.NORTH);
        panel2.add(tablePanel, BorderLayout.CENTER);
        panel2.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Congés", panel2);

        // Add tabs to the JTabbedPane
        tabbedPane.addTab("Gestion des employés", p1);
        tabbedPane.addTab("Gestion des congés", panel2);

        p1.setLayout(new BorderLayout());
        p2.setLayout(new GridLayout(7, 2, 10, 10));

        p3.setLayout(new BorderLayout());
        p4.setLayout(new FlowLayout());
        add(tabbedPane, BorderLayout.CENTER);
        p1.add(p2, BorderLayout.NORTH);
        p1.add(p3, BorderLayout.CENTER);
        p1.add(p4, BorderLayout.SOUTH);

        p2.add(new JLabel("Nom:"));
        nom = new JTextField();
        p2.add(nom);
        p2.add(new JLabel("Prenom:"));
        prenom = new JTextField();
        p2.add(prenom);
        p2.add(new JLabel("Email:"));
        email = new JTextField();
        p2.add(email);
        p2.add(new JLabel("Telephone:"));
        tel = new JTextField();
        p2.add(tel);
        p2.add(new JLabel("Salaire:"));
        sal = new JTextField();
        p2.add(sal);

        p2.add(new JLabel("Role:"));
        List<Employee.Role> roles = eImp.findAllRoles();
        String[] roleStrings = roles.stream()
                .map(Enum::name)
                .toArray(String[]::new);
        roleComboBox = new JComboBox<>(roleStrings);
        p2.add(roleComboBox);

        p2.add(new JLabel("Poste:"));
        List<Employee.Poste> postes = eImp.findAllPostes();
        String[] postesStrings = postes.stream()
                .map(Enum::name)
                .toArray(String[]::new);
        postesComboBox = new JComboBox<>(postesStrings);
        p2.add(postesComboBox);

        // P3 Container
        List<Employee> all_e = eImp.findAll();
        List<Object[]> allData = new ArrayList<>();
        
        for (Employee e : all_e) {
            allData.add(new Object[]{
                    e.getId(), e.getNom(), e.getPrenom(), e.getTel(), e.getEmail(), e.getSalaire(), e.getPoste().name(), e.getRole().name()
            });
        }
        String[] columnNamesEmployee = {"ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"};
        Object[][] employeeData = allData.toArray(new Object[0][]);

        employeeTable = new JTable(employeeData, columnNamesEmployee);
        JScrollPane employeeScrollPane = new JScrollPane(employeeTable);
        p3.add(employeeScrollPane);

        // P4 Container
        p4.setLayout(new FlowLayout());
        this.ajouter = new JButton("Ajouter");
        this.modifier = new JButton("Modifier");
        this.supprimer = new JButton("Supprimer");
        this.afficher = new JButton("Afficher");
        this.importButton = new JButton("Import");
        this.exportButton = new JButton("Export");
        p4.add(this.ajouter);
        p4.add(this.modifier);
        p4.add(this.supprimer);
        p4.add(this.afficher);
        p4.add(this.importButton);
        p4.add(this.exportButton);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public JComboBox<String> getPostesComboBox() {
        return postesComboBox;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JButton getAjouter() {
        return ajouter;
    }

    public JButton getModifier() {
        return modifier;
    }

    public JButton getSupprimer() {
        return supprimer;
    }

    public JButton getAfficher() {
        return afficher;
    }
    public JButton getImport() {
        return importButton;
    }
    public JButton getExport() {
        return exportButton;
    }

    public JTextField getTel() {
        return tel;
    }

    public JTextField getSal() {
        return sal;
    }

    public JTextField getNom() {
        return nom;
    }

    public JTextField getPrenom() {
        return prenom;
    }

    public JTextField getEmail() {
        return email;
    }

    public JPanel getP1() {
        return p1;
    }

    JToggleButton btnEmployes = new JToggleButton("Employés");
    JToggleButton btnConges = new JToggleButton("Congés");
    ButtonGroup group = new ButtonGroup();
    
    public void showErrorMessage(String message) {

        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);

    }
    public void showSuccessMessage(String message) {

        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);

    }
}