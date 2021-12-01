package DAO.Custom.Impl;

import DAO.CrudUtil;
import DAO.Custom.OrderDetailDAO;
import Entity.Customer;
import Entity.Login;
import Entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetail temp) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?,?)", temp.getItemCode(), temp.getOrderId(), temp.getQty(), temp.getPrice(), temp.getAmount());
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(temp);

        transaction.commit();
        session.close();

        if(save != null){
            return true;
        }else {
            return false;
        }*/
    }

    @Override
    public boolean update(OrderDetail temp) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order Detail` SET qty=?, price=?, amount=? WHERE orderId=? AND itemCode=?", temp.getQty(), temp.getPrice(), temp.getAmount(), temp.getOrderId(), temp.getItemCode());
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(temp);
        OrderDetail orderDetail = session.get(OrderDetail.class, temp.getOrderId());

        transaction.commit();
        session.close();

        if(temp.equals(orderDetail)){
            return true;
        }else {
            return false;
        }*/
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public String mostMovableItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode ,COUNT(qty) FROM `order detail` GROUP BY (itemCode) ORDER BY qty DESC LIMIT 1");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;

    }

    @Override
    public String leastMovableItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode ,COUNT(qty) FROM `order detail` GROUP BY (itemCode) ORDER BY qty ASC LIMIT 1");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;

    }
}
