package DAO.Custom.Impl;

import DAO.Custom.LoginDAO;
import Entity.Login;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {

    @Override
    public boolean add(Login login) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(login);

        transaction.commit();
        session.close();

        if(save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Login search(String s) throws SQLException, ClassNotFoundException {
       throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public boolean update(Login login) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public ArrayList<Login> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Supported Yet.");
    }

    @Override
    public boolean searchId(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Login login = session.get(Login.class, id);

        transaction.commit();
        session.close();

        if (login != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM Login WHERE userName =username";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Login.class);
        List<Login> list = sqlQuery.list();
        String u = null;

        for (Login login : list) {
            if (username.equals(login.getUserName())){
                u = login.getUserName();
            }
        }

        transaction.commit();
        session.close();

        if (u.equals(username)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean insertNewPassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Login SET password=:new_Password WHERE userName= :user_name";
        Query query = session.createQuery(hql);
        query.setParameter("new_Password",newPassword);
        query.setParameter("user_name",username);

        boolean b = false;

        if (query.executeUpdate()>0){
            b = true;
        }else {
            b = false;
        }

        transaction.commit();
        session.close();

        return b;
    }

    @Override
    public Login returnLoginData(String username, String Password) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM Login WHERE userName=username AND password=Password";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Login.class);
        List<Login> list = sqlQuery.list();

        Login loginData =null;

        for (Login login : list) {
            if (login.getPassword().equals(Password)){
                loginData = login;
            }
        }

        transaction.commit();
        session.close();

        return loginData;
    }

    @Override
    public boolean checkCorrectUsernameAndPassword(String username, String Password) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT * FROM Login WHERE userName=username AND password=Password";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Login.class);
        List<Login> list = sqlQuery.list();

        Login loginData =null;

        for (Login login : list) {
            if (login.getPassword().equals(Password)){
                loginData = login;
            }
        }

        transaction.commit();
        session.close();

        if (loginData.getPassword().equals(Password) && loginData.getUserName().equals(username)){
            return true;
        }else {
            return false;
        }
    }
}
