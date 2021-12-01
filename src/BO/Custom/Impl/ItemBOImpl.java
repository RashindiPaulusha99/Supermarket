package BO.Custom.Impl;

import BO.Custom.ItemBO;
import DAO.Custom.ItemDAO;
import DAO.DAOFactory;
import DTO.ItemDTO;
import Entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(item.getCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getDiscount(),item.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getDiscount(),item.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item search = itemDAO.search(code);
        if (search == null) {
            return null;
        } else {
            return new ItemDTO(search.getCode(), search.getDescription(), search.getQtyOnHand(), search.getPackSize(), search.getUnitPrice(), search.getDiscount());
        }
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItem = new ArrayList<>();
        for (Item item : all) {
            allItem.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getDiscount()
            ));
        }
        return allItem;
    }

    @Override
    public boolean searchExistsItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.searchExistsItem(id);
    }

    @Override
    public boolean updateItemQty(int sellQty, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.updateItemQty(sellQty,code);
    }

}
