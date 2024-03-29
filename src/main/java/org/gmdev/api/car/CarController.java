package org.gmdev.api.car;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.car.model.CreateCarApiRes;
import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.api.car.model.UpdateCarApiReq;
import org.gmdev.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/car")
@Validated
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetCarApiRes> getAll() {
        log.info("Incoming call to [CarController - getAll]");
        return carService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{carId}")
    public GetCarApiRes getOne(@PathVariable("carId") String carId) {
        log.info("Incoming call to [CarController - getOne]");
        return carService.getOne(carId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addOne(@Valid @NotNull @RequestBody CreateCarApiRes bodyReq) {
        log.info("Incoming call to [CarController - addOne]");
        return carService.addOne(bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{carId}")
    public GetCarApiRes updateOne(
            @PathVariable("carId") String carId, @Valid @NotNull @RequestBody UpdateCarApiReq bodyReq) {

        log.info("Incoming call to [CarController - updateOne]");
        return carService.updateOne(carId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{carId}")
    public void deleteOne(@PathVariable("carId") String carId) {
        log.info("Incoming call to [CarController - deleteOne]");
        carService.deleteOne(carId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search-by-name")
    public List<GetCarApiRes> searchByName(
            @NotNull @RequestParam(value = "name") String name) {

        log.info("Incoming call to [CarController - searchByName]");
        return carService.searchByName(name);
    }



}
