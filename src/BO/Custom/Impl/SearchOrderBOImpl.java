package BO.Custom.Impl;

import BO.Custom.SearchOrderBO;
import DAO.Custom.ItemDAO;
import DAO.Custom.OrderDAO;
import DAO.Custom.OrderDetailDAO;
import DAO.DAOFactory;
import DTO.DetailsDTO;
import DTO.ItemDTO;
import DTO.OrderDetailDTO;
import Entity.Item;
import Entity.Order;
import Entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchOrderBOImpl implements SearchOrderBO {

    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public double findCost(String date) throws SQLException, ClassNotFoundException {
        return orderDAO.findCost(date);
    }

    @Override
    public ArrayList<DetailsDTO> setTodayData(String date) throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = orderDAO.setTodayData(date);
        ArrayList<DetailsDTO> details = new ArrayList<>();
        for (Order order : orders) {
            details.add(new DetailsDTO(
                    order.getOrderId(),
                    order.getcId(),
                    String.valueOf(order.getOrderDate()),
                    order.getOrdertime(),
                    order.getCost()
            ));
        }
        return details;
    }

    @Override
    public int findQty(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.findqty(code);
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
    public String findDescription(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.findDescription(code);
    }

    @Override
    public String mostMovableItems() throws SQLException, ClassNotFoundException {
        return orderDetailDAO.mostMovableItems();
    }

    @Override
    public String leastMovableItems() throws SQLException, ClassNotFoundException {
        return orderDetailDAO.leastMovableItems();
    }

    @Override
    public ItemDTO getItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(code);
        return new ItemDTO(
                item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getDiscount()
        );
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemIds();
    }

}
