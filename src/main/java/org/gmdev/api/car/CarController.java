package org.gmdev.api.car;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.car.model.CreateCarApiRes;
import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private String addOne(@Valid @NotNull @RequestBody CreateCarApiRes bodyReq) {
        log.info("Incoming call to [CarController - addOne]");
        //return carService.addOne(bodyReq);

        System.out.println(carService);

        return "OK";
    }

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
