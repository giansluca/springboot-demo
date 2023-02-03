package org.gmdev.service.car;


import org.gmdev.dao.car.CarRepository;
import org.gmdev.model.entity.car.Car;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

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


}
