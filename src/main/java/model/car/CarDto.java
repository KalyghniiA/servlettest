package model.car;

import java.util.Objects;

public class CarDto {
    private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDto)) return false;
        CarDto carDto = (CarDto) o;
        return id == carDto.id && year == carDto.year && price == carDto.price && Objects.equals(brand, carDto.brand) && Objects.equals(model, carDto.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, year, price);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
