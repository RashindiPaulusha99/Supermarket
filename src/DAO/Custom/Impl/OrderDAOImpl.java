package DAO.Custom.Impl;

import DAO.CrudUtil;
import DAO.Custom.OrderDAO;
import Entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        //return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES(?,?,?,?,?)", order.getOrderId(), order.getcId(), order.getOrderDate(), order.getOrdertime(), order.getCost());
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(order);

        transaction.commit();
        session.close();

        if(save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM `Order` ORDER BY orderId DESC LIMIT 1";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Order.class);
        List<Order> list = sqlQuery.list();
        String id = null;

        for (Order order : list) {
            id = order.getOrderId();
        }

        transaction.commit();
        session.close();

        if (id != null){
            //if data has in database ,split orderId
            int tempId = Integer.parseInt(id.split("-")[1]);
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
    public Double findCost(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT SUM(cost) FROM `Order` WHERE orderDate=?", date);
        if (rst.next()){
            return rst.getDouble(1);
        }else {
            return null;
        }
    }

}
