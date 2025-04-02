import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dao.CarRepository;
import dao.CarRepositoryJDBC;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import service.CarService;
import service.CarServiceImpl;
import servlet.CarServlet;

public class CarContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        HikariConfig hikariConfig = new HikariConfig("./src/main/resources/hikari.properties");
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        CarRepository repository = new CarRepositoryJDBC(hikariDataSource);
        CarService service = new CarServiceImpl(repository);

        ServletContext sc = sce.getServletContext();
        sc.setAttribute("carService", service);
        sc.addServlet("carServlet", new CarServlet()).addMapping("/car");
    }
}
