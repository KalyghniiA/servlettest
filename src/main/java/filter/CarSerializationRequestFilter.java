package filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;

import model.car.CarDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class CarSerializationRequestFilter extends HttpFilter {
    private ObjectMapper objectMapper;
    private final Logger log = LogManager.getLogger(CarSerializationRequestFilter.class);

    @Override
    public void init() throws ServletException {
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!req.getContentType().contains("application/json") || req.getContentLength() == 0) {
            chain.doFilter(req, res);
        }

        byte[] reqBody = req.getInputStream().readAllBytes();

        try {
            CarDto carDto = objectMapper.readValue(reqBody, CarDto.class);
            req.setAttribute("carDto", carDto);
        } catch (IOException e) {
            log.error("ошибка серилизации", e);
            throw e;
        }

        chain.doFilter(req, res);
    }
}
