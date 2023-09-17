package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems= new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll(connection);
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection,new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection,new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(connection,code);
    }
}
