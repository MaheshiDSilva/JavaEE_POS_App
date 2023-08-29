package servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServletAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin","*");

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                customerObject.add("salary", salary);
                allCustomers.add(customerObject.build());
            }


            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "OK");
            response.add("message", "Successfully Loaded....!");
            response.add("data", allCustomers.build());
            resp.getWriter().print(response.build());
//
//            JsonReader reader = Json.createReader(req.getReader());
//            JsonObject jsonObject = reader.readObject();
//            String c_id = jsonObject.getString("id");
//            String c_name = jsonObject.getString("name");
//            String c_address = jsonObject.getString("address");
//            String c_salary = jsonObject.getString("salary");
//
//            System.out.println("DO GET " + c_id + " " + c_name + " " + c_address + " " + c_salary);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonArray jsonValues = reader.readArray();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");


//            for (JsonValue jsonValue : jsonValues) {
//                JsonObject jsonObject = jsonValue.asJsonObject();
//
//                String c_id = jsonObject.getString("id");
//                String c_name = jsonObject.getString("name");
//                String c_address = jsonObject.getString("address");
//                String c_salary = jsonObject.getString("salary");

                PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
                pstm.setObject(1,cusID);
                pstm.setObject(2, cusName);
                pstm.setObject(3, cusAddress);
                pstm.setObject(4, cusSalary);


//                resp.addHeader("Content-Type", "application/json");
                resp.addHeader("Access-Control-Allow-Origin","*");
                if (pstm.executeUpdate() > 0) {

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("state:", "OK");
                    objectBuilder.add("message:", "Successfully Added...");
                    objectBuilder.add("data", "");
                    resp.getWriter().print(objectBuilder.build());
                }

//                System.out.println("DO POST " + c_id + " " + c_name + " " + c_address + " " + c_salary);
//            }


            //how to read json object

//            JsonReader reader = Json.createReader(req.getReader());
//            JsonObject jsonObject = reader.readObject();
//            String c_id = jsonObject.getString("id");
//            String c_name = jsonObject.getString("name");
//            String c_address = jsonObject.getString("address");
//            String c_salary = jsonObject.getString("salary");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state:", "Error");
            objectBuilder.add("message:", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            String cusID = req.getParameter("cusID");
            String cusName = req.getParameter("cusName");
            String cusAddress = req.getParameter("cusAddress");
            String cusSalary = req.getParameter("cusSalary");

            PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm3.setObject(4, cusID);
            pstm3.setObject(1, cusName);
            pstm3.setObject(2, cusAddress);
            pstm3.setObject(3, cusSalary);
            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin","*");
            resp.addHeader("Access-Control-Allow-Headers","content-type");

            if (pstm3.executeUpdate() > 0) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state:", "OK");
                objectBuilder.add("message:", "Successfully Updated...");
                objectBuilder.add("data", "");
                resp.getWriter().print(objectBuilder.build());
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state:", "Error");
            objectBuilder.add("message:", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            String cusID = req.getParameter("cusID");
            PreparedStatement pstm2 = connection.prepareStatement("delete from Customer where id=?");
            pstm2.setObject(1, cusID);
            resp.addHeader("Access-Control-Allow-Origin","*");
            resp.addHeader("Content-Type", "application/json");
            if (pstm2.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Updated..!");
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("state:", "OK");
                objectBuilder.add("message:", "Successfully Deleted...");
                objectBuilder.add("data", "");
                resp.getWriter().print(objectBuilder.build());
            }

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            String c_id = jsonObject.getString("id");
            String c_name = jsonObject.getString("name");
            String c_address = jsonObject.getString("address");
            String c_salary = jsonObject.getString("salary");

            System.out.println("DO DELETE " + c_id + " " + c_name + " " + c_address + " " + c_salary);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Methods","PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
