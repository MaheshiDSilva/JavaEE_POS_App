package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Order;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean purchaseOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException {
        connection.setAutoCommit(false);
        if (orderDAO.save(connection,new Order(dto.getOrderId(),dto.getDate(),dto.getCustomerId()))){
            for (OrderDetailsDTO orderDetailsDTO: dto.getOrderDetailsDTOList()) {
                if (orderDetailsDAO.save(connection,new OrderDetails(orderDetailsDTO.getCode(),orderDetailsDTO.getOrderId(),orderDetailsDTO.getQtyOnHand(),orderDetailsDTO.getUnitPrice()))){
                    connection.commit();
                    connection.setAutoCommit(true);
                    return true;
                }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
        }
        connection.rollback();
        connection.setAutoCommit(true);
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allOrders= new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll(connection);
        for (Order order : all) {
            allOrders.add(new OrderDTO(order.getOrderId(),order.getDate(),order.getCustomerId()));
        }
        return allOrders;
    }
    }

