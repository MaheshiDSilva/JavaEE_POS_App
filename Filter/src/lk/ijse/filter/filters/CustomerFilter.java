package lk.ijse.filter.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
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

        req.setAttribute("test","set from filter");

        String name = servletRequest.getParameter("name");
        if (name!=null && name.equals("Iman")){
            //send request to servlet
            filterChain.doFilter(servletRequest,servletResponse);
            String header=resp.getHeader("testing header");
            System.out.println(header);

        }else {
            resp.setStatus(500);
            resp.getWriter().print("User not available");
            System.out.println("non authenticated user");
        }

        System.out.println("After:doFilter method Invoked");
    }

    @Override
    public void destroy() {
        System.out.println("Customer Filter destroy");
    }
}
