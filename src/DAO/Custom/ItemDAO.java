package DAO.Custom;

import DAO.CrudDAO;
import Entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String > {

    //create unique methods
    List<String> getItemIds() throws SQLException, ClassNotFoundException ;

    Item getItem(String code) throws SQLException, ClassNotFoundException;

    int findqty(String id) throws SQLException, ClassNotFoundException ;

    String findDescription(String id) throws SQLException, ClassNotFoundException;

    boolean searchExistsItem(String id) throws SQLException, ClassNotFoundException ;

    boolean updateItemQty(int sellQty,String  code) throws SQLException, ClassNotFoundException;

}
