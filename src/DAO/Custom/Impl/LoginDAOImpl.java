package DAO.Custom.Impl;

import DAO.CrudUtil;
import DAO.Custom.LoginDAO;
import Entity.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {

    @Override
    public boolean add(Login login) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Login VALUES(?,?,?,?)", login.getId(), login.getRole(), login.getUserName(), login.getPassword());
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
        ResultSet rst = CrudUtil.executeQuery("SELECT id FROM Login WHERE id=?", id);
        if (rst.next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT userName FROM Login WHERE userName=?", username);
        if (rst.next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertNewPassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        if (CrudUtil.executeUpdate("UPDATE Login SET password=? WHERE userName=?",newPassword,username)) {
            return true;
        }
        return false;
    }

    @Override
    public Login returnLoginData(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Login WHERE userName=? AND password=?", username, password);
        if (rst.next()){
            return new Login(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public boolean checkCorrectUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Login WHERE userName=? AND password=?", username, password);
        if (rst.next()){
            return true;
        }
        return false;
    }
}
