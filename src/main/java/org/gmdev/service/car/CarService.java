package org.gmdev.service.car;

import org.gmdev.api.car.model.CreateCarApiRes;
import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.api.car.model.UpdateCarApiReq;
import org.gmdev.dao.car.CarRepository;
import org.gmdev.model.entity.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<GetCarApiRes> getAll() {
        return carRepository.findAll().stream()
                .map(Car::toApiRes).toList();
    }

    public GetCarApiRes getOne(String carId) {
        Car car = getCarOrThrow(carId);

        return car.toApiRes();
    }

    public String addOne(CreateCarApiRes bodyReq) {
        LocalDateTime now = LocalDateTime.now();

        Car car = new Car(UUID.randomUUID().toString(), bodyReq.getName(), now, now);
        return carRepository.save(car).getId();
    }

    public GetCarApiRes updateOne(String carId, UpdateCarApiReq bodyReq) {
        Car updatedCar = carRepository.findById(carId)
                .map(carInDb -> {
                    carInDb.setUpdatedAt(LocalDateTime.now());

                    if (bodyReq.getName() != null)
                        carInDb.setName(bodyReq.getName());

                    return carRepository.save(carInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", carId)));

        return updatedCar.toApiRes();
    }

    public void deleteOne(String carId) {
        getCarOrThrow(carId);
        carRepository.deleteById(carId);
    }

    public List<GetCarApiRes> searchByName(String name) {
        return carRepository.searchByName(name)
                .stream()
                .map(Car::toApiRes)
                .toList();
    }

    private Car getCarOrThrow(String id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", id)));
    }

}
