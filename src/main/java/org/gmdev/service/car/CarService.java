package org.gmdev.service.car;

import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.dao.car.CarRepository;
import org.gmdev.model.entity.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

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

    public GetCarApiRes getOne(String id) {
        Car car = carRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", id)));

        return car.toApiRes();
    }

//    public Car addOne(Car car) {
//        return carRepository.save(car);
//    }
//
//    public Car updateOne(String id, Car car) {
//        return carRepository.findById(id)
//                .map(carInDb -> {
//                    carInDb.setName(car.getName());
//                    return carRepository.save(carInDb);
//                })
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Car with id: %s not found", id)));
//    }
//
//    public void deleteOne(String id) {
//        if (!carRepository.existsById(id))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    String.format("Car with id: %s not found", id));
//
//        carRepository.deleteById(id);
//    }
//
//    public List<Car> getByNameLike(String name) {
//        return carRepository.findByNameLike(name);
//    }


}
