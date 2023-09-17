package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(connection,"select * from Orders");
        while (rst.next()) {
            Order orders = new Order(rst.getString(1), rst.getDate(2), rst.getString(3));
            allOrders.add(orders);
        }
        return allOrders;
    }

    @Override
    public boolean save(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection,"insert into orders values(?,?,?)",entity.getOrderId(), entity.getDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String Id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
