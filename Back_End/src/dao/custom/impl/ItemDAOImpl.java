package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(connection,"select * from Item");
        while (rst.next()) {
            Item item = new Item(rst.getString(1), rst.getString(2), rst.getInt(3),rst.getDouble(4));
            allItems.add(item);
        }
        return allItems;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "insert into Item values(?,?,?,?)", entity.getCode(), entity.getDescription(), entity.getQtyOnHand(), entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "update Item set description=?,qtyOnHand=?,unitPrice=? where code=?",entity.getDescription(),entity.getQtyOnHand(),entity.getUnitPrice(),entity.getCode());
    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "delete from Item where code=?", code);
    }
}
