package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(connection,"select * from Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "insert into Customer values(?,?,?,?)", entity.getId(), entity.getName(), entity.getAddress(), entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "update Customer set name=?,address=?,salary=? where id=?",entity.getId(), entity.getName(), entity.getAddress(), entity.getSalary());
    }

    @Override
    public boolean delete(Connection connection, String Id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(connection, "delete from Customer where id=?", Id);
    }
}
