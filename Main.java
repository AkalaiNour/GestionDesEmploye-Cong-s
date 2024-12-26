import Controller.EmployeeController;
import Controller.HolidayController;
import Controller.LoginController;
import DAO.EmployeeDAOImpl;
import DAO.GenericDAOImpl;
import DAO.SignUpViewDAOImpl;
import Model.EmployeeModel;
import Model.HolidayModel;
import view.LoginView;
import view.Vue;

public class Main {
    public static void main(String[] args) {
        // Initialize the DAO and model for login
        SignUpViewDAOImpl signUpDao = new SignUpViewDAOImpl();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, signUpDao);

        // Wait for the login to be successful
        loginController.setLoginSuccessListener(() -> {
            // Initialize the main application components
            EmployeeDAOImpl employeeDao = new EmployeeDAOImpl();
            EmployeeModel employeeModel = new EmployeeModel(employeeDao);
            Vue mainView = new Vue();
            HolidayModel holidayModel = new HolidayModel(new GenericDAOImpl());

            new EmployeeController(employeeModel, mainView);
            new HolidayController(holidayModel, mainView, employeeModel);
        });
    }
}