package Model;
import DAO.* ;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public void exportData(String filePath, List<Employee> employees) throws IOException {

        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("ID,Nom,Prenom,Tel,Email,Salaire,Poste,Role\n");

            for (Employee emp : employees) {

                writer.write(emp.getId() + "," + emp.getNom() + "," + emp.getPrenom() + "," + emp.getTel() + "," + emp.getEmail() + "," + emp.getSalaire() + "," + emp.getPoste().name() + "," + emp.getRole().name() + "\n");

            }

        }

    }

}
