package BO.Custom;

import BO.SuperBO;
import DTO.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    boolean ifCustomerExists(String id) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

}
