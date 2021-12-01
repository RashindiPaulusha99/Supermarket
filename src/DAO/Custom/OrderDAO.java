package DAO.Custom;

import DAO.CrudDAO;
import Entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order,String > {

    String getOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<Order> setTodayData(String date) throws SQLException, ClassNotFoundException;

    Double findCost(String date) throws SQLException, ClassNotFoundException;

}
