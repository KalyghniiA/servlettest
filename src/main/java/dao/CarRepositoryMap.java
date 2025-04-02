package dao;

import model.car.Car;

import java.util.HashMap;
import java.util.List;

public class CarRepositoryMap implements CarRepository {
    private final HashMap<Integer, Car> cars = new HashMap<>();
    private int index = 0;
    @Override
    public Car save(Car car) {
        car.setId(index);
        cars.put(index++, car);
        return car;
    }

    @Override
    public Car findById(int id) {
        //return cars.get(id);
        Car car = new Car();
        car.setId(1);
        car.setBrand("brand");
        car.setModel("model");
        car.setYear(1000);
        car.setPrice(100);

        return car;
    }

    @Override
    public List<Car> findAll() {
        return cars.values().stream().toList();
    }

    @Override
    public void delete(int id) {
        cars.remove(id);
    }

    @Override
    public Car update(Car car) {
        cars.put(car.getId(), car);
        return car;
    }

    @Override
    public List<Car> findByBrand(String brand) {
        return cars.values().stream().filter(car -> car.getBrand().equals(brand)).toList();
    }

    @Override
    public List<Car> findByYear(int year) {
        return cars.values().stream().filter(car -> car.getYear() == year).toList();
    }

    @Override
    public List<Car> findByBrandAndYear(String brand, int year) {
        return cars.values().stream().filter(car -> car.getYear() == year && car.getBrand().equals(brand)).toList();
    }
}
