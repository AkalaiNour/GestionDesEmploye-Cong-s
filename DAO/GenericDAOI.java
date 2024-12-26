package DAO;

import Model.Employee;
import java.util.List;

public interface GenericDAOI<T> {
    void create(T entity);      // T is the type of the entity
    T findById(int id);          // Returns an entity of type T
    List<T> findAll();          // Returns a list of entities of type T
    void update(T entity , int id);      // Updates an entity of type T
    void delete(int id);         // Deletes an entity using its ID
    List<Employee.Role> findAllRoles();
    List<Employee.Poste>  findAllPostes();
}