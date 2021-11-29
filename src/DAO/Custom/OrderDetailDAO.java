package DAO.Custom;

import DAO.CrudDAO;
import Entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String > {

    ArrayList<OrderDetail> searchOrderDetails(String id) throws SQLException, ClassNotFoundException;

    boolean deleteOrderDetail(String oid,String code) throws SQLException, ClassNotFoundException;

    String mostMovableItems() throws SQLException, ClassNotFoundException;

    String leastMovableItems() throws SQLException, ClassNotFoundException;
}
