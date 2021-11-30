package DAO.Custom.Impl;

import DAO.Custom.QueryDAO;
import DTO.TableDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    public ArrayList<TableDTO> getDetails() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = " SELECT o.orderId,o.cId,o.orderDate,o.ordertime,c.itemCode,c.qty,c.price,c.amount,o.cost FROM `Order` o LEFT JOIN `order detail` c ON o.orderId=c.orderId";
        List<TableDTO> list = session.createSQLQuery(hql).list();

        ArrayList<TableDTO> items = new ArrayList<>();

        for (TableDTO tableDTO : list) {
            items.add(tableDTO);
        }

        transaction.commit();
        session.close();

        return items;
    }
}
