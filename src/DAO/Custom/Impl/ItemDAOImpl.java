package DAO.Custom.Impl;

import DAO.Custom.ItemDAO;
import Entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item i) throws SQLException, ClassNotFoundException {
        //save new item in database
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(i);

        transaction.commit();
        session.close();

        if(save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Item i) throws SQLException, ClassNotFoundException {
        //update items's details
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(i);
        Item item = session.get(Item.class, i.getCode());

        transaction.commit();
        session.close();

        if(i.equals(item)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, code);
        session.delete(item);
        Item i = session.get(Item.class, code);

        transaction.commit();
        session.close();

        if(i == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        //search relevant item
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, code);

        transaction.commit();
        session.close();

        if (item != null){
            return item;
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        //take data that should load to table
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Item";
        Query query = session.createQuery(hql);

        List<Item> list = query.list();
        ArrayList<Item> items = new ArrayList<>();

        for (Item item : list) {
            items.add(item);
        }

        transaction.commit();
        session.close();

        return items;
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        //take item ids from database
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Item";
        Query query = session.createQuery(hql);

        List<Item> list = query.list();
        List<String> ids = new ArrayList<>();

        for (Item item : list) {
            ids.add(item.getCode());
        }

        transaction.commit();
        session.close();

        return ids;
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        //take item details from database
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, code);

        transaction.commit();
        session.close();

        if (item != null){
            return item;
        }else {
            return null;
        }
    }

    @Override
    public int findqty(String id) throws SQLException, ClassNotFoundException {
        //search relevant item
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, id);

        transaction.commit();
        session.close();

        if (item.getQtyOnHand() != 0){
            return item.getQtyOnHand();
        }else {
            return 0;
        }
    }

    @Override
    public String findDescription(String id) throws SQLException, ClassNotFoundException {
        //search relevant item
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, id);

        transaction.commit();
        session.close();

        if (item.getDescription() != null){
            return item.getDescription();
        }else {
            return null;
        }
    }

    @Override
    public boolean searchExistsItem(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class,id);

        transaction.commit();
        session.close();

        if (item != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateItemQty(int sellQty, String Code) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        boolean b;

        String hql = "UPDATE Item SET qtyOnHand = qtyOnHand - :qtyonhand WHERE code = :ItemCode";
        Query query = session.createQuery(hql);
        query.setParameter("qtyonhand", sellQty);
        query.setParameter("ItemCode", Code);

        Item item = session.get(Item.class,Code);

        if (item.getCode().equals(Code)){
            b = true;
        }else {
            b = false;
        }

        transaction.commit();
        session.close();

        return b;
    }

}
