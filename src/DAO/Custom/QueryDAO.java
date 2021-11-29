package DAO.Custom;

import DAO.SuperDAO;
import DTO.TableDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<TableDTO> getDetails() throws SQLException, ClassNotFoundException;
}
