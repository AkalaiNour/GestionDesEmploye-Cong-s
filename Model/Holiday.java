package Model;

import java.io.File;
import java.util.Date;

public class Holiday {
    public enum typeHoliday {
        congePaye,
        congeNonPaye,
        congeMaladie
    }

    private  int holiday_id ;
    public int  getEmployeeId(){return  employeeId;}
    private Date date_de ;
    private Date date_fin ;
    private typeHoliday type_holiday ;
    private int solde_reste = 20  ;
    private int employeeId ;
    private String employeeName;
    public String getNom(){return employeeName;}
    @Override
    public String toString() {
        return "Holiday{" +
                "holiday_id=" + holiday_id +
                ", date_de=" + date_de +
                ", date_fin=" + date_fin +
                ", type_holiday=" + type_holiday +
                ", solde_reste=" + solde_reste +
                ", employeeId=" + employeeId +
                '}';
    }

    public Holiday(int id , Date date_de, Date date_fin, typeHoliday type , int solde_reste , int employee ,String employeeName){
        this.holiday_id = id ;
        this.date_de = date_de ;
        this.date_fin = date_fin;
        this.type_holiday= type ;
        this.employeeId = employee ;
        this.employeeName = employeeName ;
        if(solde_reste!=0)this.solde_reste = solde_reste ;

    };
    public int suivreSolde(){return solde_reste ;};
    public boolean estSoldeValide(){if(suivreSolde() >0)return true ;
        return false;
    }

    public int getHoliday_id() {
        return holiday_id;
    }

    public void setHoliday_id(int holiday_id) {
        this.holiday_id = holiday_id;
    }

    public Date getDate_de() {
        return date_de;
    }

    public void setDate_de(Date date_de) {
        this.date_de = date_de;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public typeHoliday getType_holiday() {
        return type_holiday;
    }

    public void setType_holiday(typeHoliday type_holiday) {
        this.type_holiday = type_holiday;
    }

    public int getSolde_reste() {
        return solde_reste;
    }

    public void ajouterSolde(int solde) {
        this.solde_reste = getSolde_reste()+solde;
    }
    public void diminuerSolde(int solde) {
        this.solde_reste = getSolde_reste() - solde;
    }
    
}
