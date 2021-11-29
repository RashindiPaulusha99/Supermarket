package DAO;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static PreparedStatement repeatStatement(String sqlQuery, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sqlQuery);
        for (int i = 0; i < args.length; i++) {
            stm.setObject(i+1 , args[i]);
        }
        return stm;
    }

    public static boolean executeUpdate(String sqlQuery, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = repeatStatement(sqlQuery, args);
        return pstm.executeUpdate()>0;
    }

    public static ResultSet executeQuery(String sqlQuery, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = repeatStatement(sqlQuery, args);
        return pstm.executeQuery();
    }
}
