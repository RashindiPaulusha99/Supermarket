package DAO.Custom;

import DAO.CrudDAO;
import Entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String > {

    //create unique methods
     List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

     boolean ifCustomerExists(String id) throws SQLException, ClassNotFoundException;
}
