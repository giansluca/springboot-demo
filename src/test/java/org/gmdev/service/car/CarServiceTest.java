package org.gmdev.service.car;

import org.gmdev.api.car.model.CreateCarApiRes;
import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.api.car.model.UpdateCarApiReq;
import org.gmdev.dao.car.entity.Car;
import org.gmdev.setup.MongoDBTestContainerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        assertThat(allCars).hasSize(4);
    }

    @Test
    void itShouldShouldAddCar() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);

        CreateCarApiRes bodyReq = new CreateCarApiRes("Ferrari");

        // When
        String carId = underTest.addOne(bodyReq);
        Car savedCar = carTestHelper.findCarById(carId).orElseThrow();

        // Then
        assertThat(savedCar.getName()).isEqualTo("Ferrari");
    }

    @Test
    void itShouldUpdateCar() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);
        String carId = cars.get(0).getId();

        UpdateCarApiReq bodyReq = new UpdateCarApiReq("California 3");

        // When
        GetCarApiRes updatedCar = underTest.updateOne(carId, bodyReq);

        // Then
        assertThat(updatedCar).isNotNull();
        assertThat(updatedCar.getName()).isEqualTo("California 3");
    }

    @Test
    void itShouldDeleteCar() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);
        String carId = cars.get(0).getId();

        // When
        underTest.deleteOne(carId);
        Optional<Car> carMaybe = carTestHelper.findCarById(carId);
        List<Car> allCars = carTestHelper.findAllCars();

        // Then
        assertThat(carMaybe).isEmpty();
        assertThat(allCars).hasSize(3);
    }

    @Test
    void itShouldSearchByName() {
        // Given
        List<Car> cars = getFakeCars();
        carTestHelper.saveCarList(cars);

        // When
        List<GetCarApiRes> search1 = underTest.searchByName("Calif");
        List<GetCarApiRes> search2 = underTest.searchByName("Golf");

        // Then
        assertThat(search1).hasSize(1);
        assertThat(search2).hasSize(2);
    }

    List<Car> getFakeCars() {
        LocalDateTime now = LocalDateTime.now();

        Car car1 = new Car(UUID.randomUUID().toString(), "Golf", now, now);
        Car car2 = new Car(UUID.randomUUID().toString(), "Rifter", now, now);
        Car car3 = new Car(UUID.randomUUID().toString(), "California", now, now);
        Car car4 = new Car(UUID.randomUUID().toString(), "New golf", now, now);

        return List.of(car1, car2, car3, car4);
    }


}