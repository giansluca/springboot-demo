package org.gmdev.service.car;


import org.gmdev.dao.car.CarRepository;
import org.gmdev.dao.car.entity.Car;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Car> getFakeCars() {
        LocalDateTime now = LocalDateTime.now();

        Car car1 = new Car(UUID.randomUUID().toString(), "Golf", now, now);
        Car car2 = new Car(UUID.randomUUID().toString(), "Rifter", now, now);
        Car car3 = new Car(UUID.randomUUID().toString(), "California", now, now);
        Car car4 = new Car(UUID.randomUUID().toString(), "New golf", now, now);

        return List.of(car1, car2, car3, car4);
    }

}
