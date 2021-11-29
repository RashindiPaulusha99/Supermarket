package DAO.Custom;

import DAO.CrudDAO;
import Entity.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order,String > {

    String getOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<Order> searchOrder(String id) throws SQLException, ClassNotFoundException;

    ArrayList<Order> getOrderDetailsToSearch() throws SQLException, ClassNotFoundException;

    ArrayList<Order> setTodayData(String date) throws SQLException, ClassNotFoundException;

    ArrayList<Order> setMonthlyAnnuallyData(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException;

    Double findCost(String date) throws SQLException, ClassNotFoundException;

    Double findCostForCustomer(String id) throws SQLException, ClassNotFoundException;

    Double findCostForMoAn(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException;

    ArrayList<Order> searchData(String id) throws SQLException, ClassNotFoundException;
}
