package org.gmdev.service.car;


import org.gmdev.dao.car.CarRepository;
import org.gmdev.dao.car.entity.Car;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CarTestHelper {

    private final CarRepository carRepository;

    public CarTestHelper(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void cleanDb() {
        carRepository.deleteAll();
    }

    public void saveCarList(List<Car> cars) {
        carRepository.saveAll(cars);
    }

    public Optional<Car> findCarById(String carId) {
        return carRepository.findById(carId);
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }


}
