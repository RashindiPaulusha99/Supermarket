package BO.Custom.Impl;

import BO.Custom.LoginBO;
import DAO.Custom.LoginDAO;
import DAO.DAOFactory;
import DTO.LoginDTO;
import Entity.Login;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    private LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean saveLoginData(LoginDTO login) throws SQLException, ClassNotFoundException {
        return loginDAO.add(new Login(login.getId(), login.getRole(), login.getUsername(), login.getPassword()));
    }

    @Override
    public boolean searchId(String id) throws SQLException, ClassNotFoundException {
        return loginDAO.searchId(id);
    }

    @Override
    public boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        return loginDAO.checkUsername(username);
    }

    @Override
    public boolean insertNewPassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        return loginDAO.insertNewPassword(username, newPassword);
    }

    @Override
    public LoginDTO returnLoginData(String username, String password) throws SQLException, ClassNotFoundException {
        Login login = loginDAO.returnLoginData(username, password);
        if (login == null) {
            return null;
        } else {
            return new LoginDTO(login.getId(), login.getRole(), login.getUserName(), login.getPassword());
        }
    }


    @Override
    public boolean checkCorrectUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException {
        return loginDAO.checkCorrectUsernameAndPassword(username,password);
    }
}
