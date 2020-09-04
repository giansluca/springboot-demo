package org.gmdev.springbootdemo.service;

import org.gmdev.springbootdemo.dao.CarRepository;
import org.gmdev.springbootdemo.model.entity.CarModel;
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

    public List<CarModel> getAll() {
        return carRepository.findAll();
    }

    public CarModel getOne(String id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Car with id: %s not found", id)));
    }

    public CarModel addOne(CarModel car) {
        return carRepository.save(car);
    }

    public CarModel updateOne(String id, CarModel car) {
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

    public List<CarModel> getByNameLike(String name) {
        return carRepository.findByNameLike(name);
    }


}
