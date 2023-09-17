package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll(connection);
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(connection,new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getSalary()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(connection,new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection,customerId);
    }
}
