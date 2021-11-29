package BO.Custom;

import BO.SuperBO;
import DTO.LoginDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    boolean saveLoginData(LoginDTO login) throws SQLException, ClassNotFoundException;

    boolean searchId(String id) throws SQLException, ClassNotFoundException;

    boolean checkUsername(String username) throws SQLException, ClassNotFoundException;

    boolean insertNewPassword(String username, String newPassword) throws SQLException, ClassNotFoundException;

    LoginDTO returnLoginData(String username, String password) throws SQLException, ClassNotFoundException;

    boolean checkCorrectUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException;
}
