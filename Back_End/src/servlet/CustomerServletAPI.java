package servlet;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServletAPI extends HttpServlet {

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()){

            PrintWriter writer = resp.getWriter();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();

            ArrayList<CustomerDTO> all = customerBO.getAllCustomers(connection);

            for (CustomerDTO customerDTO:all){
                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id",customerDTO.getId());
                customer.add("name",customerDTO.getName());
                customer.add("address",customerDTO.getAddress());
                customer.add("salary",customerDTO.getSalary());

                allCustomers.add(customer.build());
            }
            writer.print(allCustomers.build());


        } catch (ClassNotFoundException e) {
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.getWriter().println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()){
            CustomerDTO customerDTO = new CustomerDTO(cusID,cusName,cusAddress,cusSalary);

            if (customerBO.saveCustomer(connection,customerDTO)) {
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Updated !"));
            }

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("cusID");
        String name = jsonObject.getString("cusName");
        String address = jsonObject.getString("cusAddress");
        String salary = jsonObject.getString("cusSalary");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()){

            CustomerDTO customerDTO =new CustomerDTO(id,name,address,salary);
            if (customerBO.updateCustomer(connection,customerDTO)) {
                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Updated !"));
            }
        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("cusID");

        ServletContext servletContext =getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()){
            if (customerBO.deleteCustomer(connection,id)) {

                resp.getWriter().print(ResponseUtil.getJson("OK","Successfully Deleted !"));
            }

        } catch (ClassNotFoundException e) {

            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.getJson("Error",e.getMessage()));
        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin","*");
//        resp.addHeader("Access-Control-Allow-Methods","PUT,DELETE");
//        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
