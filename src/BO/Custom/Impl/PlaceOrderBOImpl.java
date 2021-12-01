package BO.Custom.Impl;

import BO.Custom.PlaceOrderBO;
import DAO.Custom.CustomerDAO;
import DAO.Custom.ItemDAO;
import DAO.Custom.OrderDAO;
import DAO.Custom.OrderDetailDAO;
import DAO.DAOFactory;
import DTO.CustomerDTO;
import DTO.ItemDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import Entity.Customer;
import Entity.Item;
import Entity.Order;
import Entity.OrderDetail;
import db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    //data set to tables in database
    public boolean placeOrder(OrderDTO order,Customer customer) throws SQLException, ClassNotFoundException {
        //save order
            boolean ifInsertOrder = orderDAO.add(new Order(order.getOrderId(),customer.getId(),LocalDate.parse(order.getOrderDate()),order.getOrderTime(),order.getCost()));

            if(ifInsertOrder){//if data save

                try {
                    if(saveOrderDetails(order)) {
                        return true;
                    }else {
                        return false;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                return false;
            }
        return false;
    }

    public boolean saveOrderDetails(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        //data pass orderDetails table
        for (OrderDetailDTO temp : orderDTO.getItems()) {

            boolean ifOrderDetailSaved = orderDetailDAO.add(new OrderDetail(temp.getItemCode(),temp.getOrderId(),temp.getSellQty(),temp.getPrice(),temp.getAmount()));

            if(ifOrderDetailSaved){

                if (updateQty(temp.getItemCode(),temp.getSellQty())){

                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    //modify item qty
    public  boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateItemQty(qty, itemCode);
    }

    @Override
    public String setOrderIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerIds();
    }

    @Override
    public CustomerDTO searchCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(id);
        return new CustomerDTO(search.getId(),search.getTitle(),search.getName(),search.getAddress(),search.getCity(),search.getProvince(),search.getPostalCode());
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemIds();
    }

    @Override
    public ItemDTO searchItemData(String id) throws SQLException, ClassNotFoundException {
        Item search = itemDAO.getItem(id);
        return new ItemDTO(search.getCode(),search.getDescription(),search.getQtyOnHand(),search.getPackSize(),search.getUnitPrice(),search.getDiscount());
    }
}
