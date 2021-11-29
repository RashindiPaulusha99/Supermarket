package DAO.Custom.Impl;

import DAO.CrudUtil;
import DAO.Custom.QueryDAO;
import DTO.TableDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    public ArrayList<TableDTO> getDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(" SELECT o.orderId,o.cId,o.orderDate,o.ordertime,c.itemCode,c.qty,c.price,c.amount,o.cost FROM `Order` o LEFT JOIN `order detail` c ON o.orderId=c.orderId");
        ArrayList<TableDTO> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new TableDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(9)
            ));
        }
        //return data
        return items;
    }
}
