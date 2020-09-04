package org.gmdev.springbootdemo.dao;

import org.gmdev.springbootdemo.model.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carRepository")
public interface CarRepository extends MongoRepository<Car, String> {

    @Query("{ name: { $regex: ?0, $options : i } }")
    List<Car> findByNameLike(String name);

}
