package lk.ijse.filter.filters;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")//default filter
public class CustomerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {//filter ekak startup ekedi mokak har deyak karaganimata
        System.out.println("customer Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Before:doFilter method Invoked");

        HttpServletRequest req=(HttpServletRequest)servletRequest;//casting
        HttpServletResponse resp=(HttpServletResponse)servletResponse;//casting

//        req.setAttribute("test","set from filter");
        req.setAttribute("Auth","username=admin,password=admin");//create header

//        String name = servletRequest.getParameter("name");
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");
        if (username.equals("admin") && password.equals("admin")){
            //send request to servlet
            filterChain.doFilter(servletRequest,servletResponse);//servlet ekakata yawanawada nadda kiyala balanawa(filter karanawa)e line eka dala thibboth yanawa nathnm yanne na
//            String header=resp.getHeader("testing header");
//            System.out.println(header);

        }else {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("state","error");
            response.add("message","Invalid user");
            response.add("data","");
            /*resp.setStatus(500);
            resp.getWriter().print("User not available");
            System.out.println("non authenticated user");*/
        }

        System.out.println("After:doFilter method Invoked");
    }

    @Override
    public void destroy() {
        System.out.println("Customer Filter destroy");
    }
}
