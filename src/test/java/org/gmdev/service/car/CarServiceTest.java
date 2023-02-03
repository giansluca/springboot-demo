package org.gmdev.service.car;

import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.model.entity.car.Car;
import org.gmdev.setup.MongoDBTestContainerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarServiceTest extends MongoDBTestContainerSetup {

    @Autowired
    CarTestHelper carTestHelper;

    @Autowired
    CarService underTest;

    @AfterEach
    void tearDown() {
        carTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneCar() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);

        String carId = cars.get(0).getId();

        // When
        GetCarApiRes foundCar = underTest.getOne(carId);

        // Then
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getName()).isEqualTo("Golf");
    }

    @Test
    void itShouldThrowIfCarNotFound() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);

        // When Then
        assertThatThrownBy(() -> underTest.getOne("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Car with id: %s not found", "999"));
    }

    @Test
    void itShouldFindAllCars() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);

        // When
        List<GetCarApiRes> allCars = underTest.getAll();

        // Then
        assertThat(allCars).hasSize(3);
    }

    List<Car> getFakeCars() {
        Car car1 = new Car(UUID.randomUUID().toString(), "Golf");
        Car car2 = new Car(UUID.randomUUID().toString(), "Rifter");
        Car car3 = new Car(UUID.randomUUID().toString(), "California");

        return List.of(car1, car2, car3);
    }




}