package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Connection connection, OrderDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection,"insert into orderdetail values(?,?,?,?)",entity.getCode(), entity.getOrderId(),entity.getQtyOnHand(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String Id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
