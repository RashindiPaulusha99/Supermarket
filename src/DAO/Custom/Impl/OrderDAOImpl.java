package DAO.Custom.Impl;

import DAO.CrudUtil;
import DAO.Custom.OrderDAO;
import Entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES(?,?,?,?,?)", order.getOrderId(), order.getcId(), order.getOrderDate(), order.getOrdertime(), order.getCost());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order` SET cId=?, orderDate=?, ordertime=?, cost=? WHERE orderId=?", order.getcId(), order.getOrderDate(), order.getOrdertime(), order.getCost(), order.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE orderId=?", id);
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if (rst.next()){
            //if data has in database ,split orderId
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "O-00"+tempId;
            }else if (tempId <= 99){
                return "O-0"+tempId;
            }else {
                return "O-"+tempId;
            }
        }else {
            //if no data in database
            return "O-001";
        }
    }

    @Override
    public ArrayList<Order> searchOrder(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order` WHERE cId=?", id);
        ArrayList<Order> order = new ArrayList<>();
        while (rst.next()){
            order.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return order;
    }

    @Override
    public ArrayList<Order> getOrderDetailsToSearch() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order`");
        ArrayList<Order> order = new ArrayList<>();
        while (rst.next()){
            order.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return order;
    }

    @Override
    public ArrayList<Order> setTodayData(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderDate=?",date);
        ArrayList<Order> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    rst.getString(4),
                    rst.getDouble(5)

            ));
        }
        //return data
        return items;
    }

    @Override
    public ArrayList<Order> setMonthlyAnnuallyData(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
        ArrayList<Order> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    rst.getString(4),
                    rst.getDouble(5)

            ));
        }
        //return data
        return items;
    }

    @Override
    public Double findCost(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order` WHERE orderDate=?", date);
        if (rst.next()){
            return rst.getDouble(1);
        }else {
            return null;
        }
    }

    @Override
    public Double findCostForCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order` WHERE cId=?", id);
        if (rst.next()){
            return rst.getDouble(1);
        }else {
            return null;
        }
    }

    @Override
    public Double findCostForMoAn(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order` WHERE orderDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
        if (rst.next()){
            return rst.getDouble(1);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Order> searchData(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order` WHERE orderId LIKE '%"+id+"%'");
        ArrayList<Order> order = new ArrayList<>();
        while (rst.next()){
            order.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return order;
    }
}
