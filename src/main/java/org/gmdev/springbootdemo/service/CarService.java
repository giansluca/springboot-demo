package org.gmdev.springbootdemo.service;

import org.gmdev.springbootdemo.dao.CarRepository;
import org.gmdev.springbootdemo.model.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CarService(
            @Qualifier("carRepository") CarRepository carRepository,
            MongoTemplate mongoTemplate) {

        this.carRepository = carRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Car> getAll() {
        //List<Car> list = carRepository.findAll();

        return mongoTemplate.findAll(Car.class);
        //carRepository.findAll();

        //return list;
    }

    public Car getOne(String id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", id)));
    }

    public Car addOne(Car car) {
        return carRepository.save(car);
    }

    public Car updateOne(String id, Car car) {
        return carRepository.findById(id)
                .map(carInDb -> {
                    carInDb.setName(car.getName());
                    return carRepository.save(carInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", id)));
    }

    public void deleteOne(String id) {
        if (!carRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Car with id: %s not found", id));

        carRepository.deleteById(id);
    }

    public List<Car> getByNameLike(String name) {
        return carRepository.findByNameLike(name);
    }


}
