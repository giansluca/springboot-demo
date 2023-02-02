package org.gmdev.service;

import org.gmdev.model.entity.car.Car;
import org.gmdev.dao.car.CarRepository;
import org.gmdev.service.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    CarService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CarService(carRepository, mongoTemplate);
    }

    //@Test
    void itShouldSelectAllCars() {
        // Given an empty list
        List<Car> cars = new ArrayList<>();

        given(carRepository.findAll()).willReturn(cars);

        // When
        List<Car> carsInDb = underTest.getAll();

        // Then
        assertThat(carsInDb).isEmpty();
    }

}