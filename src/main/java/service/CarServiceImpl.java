package service;

import dao.CarRepository;
import exceptions.CarEmptyException;
import exceptions.UserInputException;
import mapper.CarMapper;
import model.car.Car;
import model.car.CarDto;

import java.util.List;

public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDto save(CarDto carDto) {
        return CarMapper.toCarDto(carRepository.save(CarMapper.toCar(carDto)));
    }

    @Override
    public CarDto update(CarDto carDto) {
        if (carDto.getId() == null) throw new UserInputException("Поле id пусто");
        Car car = carRepository.findById(carDto.getId());
        if (car == null) throw new CarEmptyException(String.format("Автомобиля c id %s нет в базе", carDto.getId()));
        return CarMapper.toCarDto(carRepository.update(CarMapper.toCar(carDto)));
    }

    @Override
    public void delete(int id) {
        if (carRepository.findById(id) == null) throw new CarEmptyException(String.format("Автомобиля c id %s нет в базе", id));
        carRepository.delete(id);
    }

    @Override
    public CarDto findById(int idCar) {
        Car car = carRepository.findById(idCar);
        if (car == null) throw new CarEmptyException(String.format("Автомобиль с id %s отсутствует", idCar));
        return CarMapper.toCarDto(car);
    }

    @Override
    public List<CarDto> findAll() {
        List<Car> carList = carRepository.findAll();
        if (carList.isEmpty()) throw new CarEmptyException("Информация по данным параметрам отсутствует");
        return carList.stream().map(CarMapper::toCarDto).toList();
    }

    @Override
    public List<CarDto> findByBrand(String brand) {
        List<Car> carList = carRepository.findByBrand(brand);
        if (carList.isEmpty()) throw new CarEmptyException("Информация по данным параметрам отсутствует");
        return carList.stream().map(CarMapper::toCarDto).toList();
    }

    @Override
    public List<CarDto> findByYear(int year) {
        List<Car> carList = carRepository.findByYear(year);
        if (carList.isEmpty()) throw new CarEmptyException("Информация по данным параметрам отсутствует");
        return carList.stream().map(CarMapper::toCarDto).toList();
    }

    @Override
    public List<CarDto> findByBrandAndYear(String brand, int year) {
        List<Car> carList = carRepository.findByBrandAndYear(brand, year);
        if (carList.isEmpty()) throw new CarEmptyException("Информация по данным параметрам отсутствует");
        return carList.stream().map(CarMapper::toCarDto).toList();
    }
}
