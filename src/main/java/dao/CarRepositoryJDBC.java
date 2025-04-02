package dao;

import model.car.Car;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryJDBC implements CarRepository {
    DataSource dataSource;

    public CarRepositoryJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Car save(Car car) {
        String sql = "insert into cars (brand, model, year, price) values (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             statement.setString(1, car.getBrand());
             statement.setString(2, car.getModel());
             statement.setInt(3, car.getYear());
             statement.setInt(4, car.getPrice());

             statement.executeUpdate();
             car.setId(statement.getGeneratedKeys().getInt(1));
             return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public Car findById(int id) {
        String sql = "select * from cars where id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            return mapToCar(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findAll() {
        String sql = "select * from cars";
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet  = statement.executeQuery(sql);

            return mapToCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from cars where id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car update(Car car) {
        String sql = "update cars set brand = ?, model = ?, year = ?, price = ? where id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setInt(4, car.getPrice());
            statement.setInt(5, car.getId());

            ResultSet resultSet = statement.executeQuery();
            return mapToCar(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findByBrand(String brand) {
        String sql = "select * from cars where brand = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();

            return mapToCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findByYear(int year) {
        String sql = "select * from cars where year = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, year);
            ResultSet resultSet = statement.executeQuery();

            return mapToCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findByBrandAndYear(String brand, int year) {
        String sql = "select * from cars where brand = ? and year = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, brand);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();

            return mapToCarList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Car mapToCar(ResultSet resultSet) throws SQLException {
        Car car = null;
        if (resultSet.next()) {
            car = map(resultSet);
        }
        return car;
    }

    private List<Car> mapToCarList(ResultSet resultSet) throws SQLException {
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            cars.add(map(resultSet));
        }
        return cars;
    }

    private Car map(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setYear(resultSet.getInt("year"));
        car.setPrice(resultSet.getInt("price"));
        return car;
    }

//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(
//                "jdbc:postgresql://localhost:6432/cardb", "cardb", "12345"
//        );
//   }
}
