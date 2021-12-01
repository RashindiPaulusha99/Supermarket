package DAO.Custom;

import DAO.CrudDAO;
import Entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String > {

    String mostMovableItems() throws SQLException, ClassNotFoundException;

    String leastMovableItems() throws SQLException, ClassNotFoundException;
}
