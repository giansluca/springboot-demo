package org.gmdev.api;

import org.gmdev.model.dto.CarDto;

import org.gmdev.model.entity.Car;
import org.gmdev.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/car")
@RestController
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CarDto> getAll() {
        List<Car> cars = carService.getAll();
        return cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    public CarDto getOne(@PathVariable("id") String id) {
        Car car = carService.getOne(id);
        return toDto(car);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private CarDto addOne(@Valid @NotNull @RequestBody CarDto carDto) {
        Car newCar = carService.addOne(toEntity(carDto));
        return toDto(newCar);
    }

    @PutMapping(path = "{id}")
    public CarDto updateOne(@PathVariable("id") String id, @Valid @NotNull @RequestBody CarDto carDto) {
        Car updatedCar = carService.updateOne(id, toEntity(carDto));
        return toDto(updatedCar);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteOne(@PathVariable("id") String id) {
        carService.deleteOne(id);
    }

    @GetMapping(path = "/search")
    public List<CarDto> getByNameLike(@NotNull @RequestParam(value = "name") String name) {
        List<Car> cars = carService.getByNameLike(name);
        return cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Car toEntity(CarDto carDto) {
        return modelMapper.map(carDto, Car.class);
    }

    private CarDto toDto(Car car) {
        return modelMapper.map(car, CarDto.class);
    }

}
