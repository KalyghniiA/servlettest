package mapper;

import model.car.Car;
import model.car.CarDto;

public class CarMapper {
    public static Car toCar(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setPrice(carDto.getPrice());
        return car;
    }

    public static CarDto toCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setYear(car.getYear());
        carDto.setPrice(car.getPrice());
        return carDto;
    }
}
