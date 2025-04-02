package dao;

import model.car.Car;

import java.util.List;

public interface CarRepository {
    Car save(Car car);

    Car findById(int id);

    List<Car> findAll();

    void delete(int id);

    Car update(Car car);

    List<Car> findByBrand(String brand);

    List<Car> findByYear(int year);

    List<Car> findByBrandAndYear(String brand, int year);
}
