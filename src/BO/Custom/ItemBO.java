package BO.Custom;

import BO.SuperBO;
import DTO.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    boolean saveItem(ItemDTO i) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO i) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean searchExistsItem(String id) throws SQLException, ClassNotFoundException;

    boolean updateItemQty(int sellQty, String code) throws SQLException, ClassNotFoundException;

}
