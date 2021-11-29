package DAO.Custom;

import DAO.CrudDAO;
import Entity.Login;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO<Login,String > {

    boolean searchId(String id) throws SQLException, ClassNotFoundException;

    boolean checkUsername(String username) throws SQLException, ClassNotFoundException ;

    boolean insertNewPassword(String username, String newPassword) throws SQLException, ClassNotFoundException;

    Login returnLoginData(String username, String password) throws SQLException, ClassNotFoundException ;

    boolean checkCorrectUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException ;
}
