package org.gmdev.springbootdemo.dao;

import org.gmdev.springbootdemo.model.entity.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carRepository")
public interface CarRepository extends MongoRepository<CarModel, String> {

    @Query("{ name: { $regex: ?0, $options : i } }")
    List<CarModel> findByNameLike(String name);

}
