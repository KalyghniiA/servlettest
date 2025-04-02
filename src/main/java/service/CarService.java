package service;

import model.car.CarDto;

import java.util.List;

public interface CarService {
    CarDto save(CarDto carDto);

    CarDto update(CarDto carDto);

    void delete(int idCar);

    CarDto findById(int idCar);

    List<CarDto> findAll();

    List<CarDto> findByBrand(String brand);

    List<CarDto> findByYear(int year);

    List<CarDto> findByBrandAndYear(String brand, int year);
}
