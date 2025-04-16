package filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CarSerializationResponseFilter extends HttpFilter {
    private ObjectMapper objectMapper;
    private final Logger log = LogManager.getLogger(CarSerializationResponseFilter.class);

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);

        var resBody = req.getAttribute("responseBody");
        if (resBody == null) {
            return;
        }

        try {
            res.getOutputStream().write(objectMapper.writeValueAsBytes(resBody));
            res.setContentType("application/json");
        } catch (IOException e) {
            log.error("Ошибка серилизации", e);
            throw e;
        }
    }
}
