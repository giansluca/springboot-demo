package org.gmdev.api;

import org.gmdev.model.dto.CarDto;

import org.gmdev.model.entity.Car;
import org.gmdev.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("api/v1/car")
@Validated
@RestController
public class CarController {

//    private final CarService carService;
//
//    @Autowired
//    public CarController(CarService carService) {
//        this.carService = carService;
//    }
//
//    @GetMapping
//    public List<CarDto> getAll() {
//        return carService.getAll();
//    }
//
//    @GetMapping(path = "{id}")
//    public CarDto getOne(@PathVariable("id") String id) {
//        return carService.getOne(id);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    private CarDto addOne(@Valid @NotNull @RequestBody Car carDto) {
//        return carService.addOne(carDto);
//    }
//
//    @PutMapping(path = "{id}")
//    public CarDto updateOne(@PathVariable("id") String id, @Valid @NotNull @RequestBody CarDto cardto) {
//        return carService.updateOne(id, cardto);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping(path = "{id}")
//    public void deleteOne(@PathVariable("id") String id) {
//        carService.deleteOne(id);
//    }
//
//    @GetMapping(path = "/search")
//    public List<CarDto> getByNameLike(@NotNull @RequestParam(value = "name") String name) {
//        return carService.getByNameLike(name);
//    }
//

}
