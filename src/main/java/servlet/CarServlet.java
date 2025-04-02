package servlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.UserInputException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.car.CarDto;
import service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

public class CarServlet extends HttpServlet {
    private CarService carService;
    private final ObjectMapper objectMapper = new ObjectMapper();

   @Override
   public void init() {
       carService = (CarService) getServletContext().getAttribute("carService");
   }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         Map<String, String[]> parameterMap = req.getParameterMap();
         PrintWriter writer = resp.getWriter();
         if (!parameterMap.containsKey("type")) {
             throw new ServletException("Missing type parameter");
         }

         switch (parameterMap.get("type")[0]) {
             case "id":
                writer.print(getCarById(parameterMap.get("id")));
                writer.close();
                break;
             case "all":
                 writer.print(getAllCars());
                 writer.close();
                 break;
             case "all_to_brand":
                 writer.print(getCarsByBrand(parameterMap.get("brand")));
                 writer.close();
                 break;
             case "all_to_year":
                 writer.print(getCarsByYear(parameterMap.get("year")));
                 writer.close();
                 break;
             case "all_to_brand_and_year":
                 writer.print(getCarsByBrandAndYear(parameterMap));
                 writer.close();
                 break;
             default:
                 throw new ServletException("Invalid type parameter");
         }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String body = reader.lines().collect(Collectors.joining());
        CarDto carDto = objectMapper.readValue(body, CarDto.class);

        PrintWriter writer = resp.getWriter();
        writer.print(toJson(carService.save(carDto)));
        writer.close();
    }

    private String getCarById(String[] paramID) throws JsonProcessingException {
        if (paramID == null || paramID.length == 0) {
            throw new UserInputException("Missing id parameter");
        }

        return toJson(carService.findById(Integer.parseInt(paramID[0])));
    }

    private String getAllCars() throws JsonProcessingException {
        return toJson(carService.findAll());
    }

    private String getCarsByBrand(String[] paramBrand) throws JsonProcessingException {
        if (paramBrand == null || paramBrand.length == 0) {
            throw new UserInputException("Missing brands parameter");
        }
        return toJson(carService.findByBrand(paramBrand[0]));
    }

    private String getCarsByYear(String[] paramYear) throws JsonProcessingException {
        if (paramYear == null || paramYear.length == 0) {
            throw new UserInputException("Missing year parameter");
        }
        return toJson(carService.findByYear(Integer.parseInt(paramYear[0])));
    }

    private String getCarsByBrandAndYear(Map<String, String[]> params) throws JsonProcessingException {
        if (params.get("year") == null || params.get("year").length == 0) {
            throw new UserInputException("Missing year parameter");
        }
        if (params.get("brand") == null || params.get("brand").length == 0) {
            throw new UserInputException("Missing brands parameter");
        }
        return toJson(carService.findByBrandAndYear(params.get("brand")[0], Integer.parseInt(params.get("year")[0])));
    }

    private String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
