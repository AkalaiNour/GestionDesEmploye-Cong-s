package Model;
import DAO.* ;

import java.util.List;

public class HolidayModel {
    private GenericDAOImpl dao ;
    public HolidayModel(GenericDAOImpl dao){this.dao = dao ;}
    public void addHoliday(Holiday h) {
        long  time =  h.getDate_fin().getTime() - h.getDate_de().getTime();
        long days = time / (1000 * 60 * 60 * 24);
        System.out.println("Days:"+ days);
        if(h.getSolde_reste() > days ){
            h.diminuerSolde(h.getSolde_reste()  - (int)days);
            dao.create(h) ; };
    }
    public Holiday findHolidayById(int id){
        return dao.findById(id);
    }
    public List<Holiday> findAllHolidays(){
        return dao.findAll();
    }
    public void updateHoliday(Holiday h , int id){
        dao.update(h , id);
    }
    public void deleteHolidayEmployee(int id){
        dao.delete(id);
    }

}
