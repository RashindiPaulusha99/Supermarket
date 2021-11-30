package DAO.Custom.Impl;

import DAO.Custom.CustomerDAO;
import Entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        //save new customer in database
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(c);

        transaction.commit();
        session.close();

        if(save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        //update customer's details
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(c);
        Customer customer = session.get(Customer.class, c.getId());

        transaction.commit();
        session.close();

        if(c.equals(customer)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, id);
        session.delete(customer);
        Customer c = session.get(Customer.class, id);

        transaction.commit();
        session.close();

        if(c == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        //search relevant customer
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, id);

        transaction.commit();
        session.close();

        if (customer != null){
            return customer;
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        //take data that should load to table
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Customer";
        Query query = session.createQuery(hql);

        List<Customer> list = query.list();
        ArrayList<Customer> customers = new ArrayList<>();

        for (Customer customer : list) {
            customers.add(customer);
        }

        transaction.commit();
        session.close();

        return customers;
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        //take customer ids from database
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Customer";
        Query query = session.createQuery(hql);

        List<Customer> list = query.list();
        List<String> ids = new ArrayList<>();

        for (Customer customer : list) {
            ids.add(customer.getId());
        }

        transaction.commit();
        session.close();

        return ids;
    }

    @Override
    public boolean ifCustomerExists(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class,id);

        transaction.commit();
        session.close();

        if (customer != null){
            return true;
        }else {
            return false;
        }
    }
}
