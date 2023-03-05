package org.gmdev.api.car;

import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.service.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class CarControllerTest {

    @Mock
    CarService carService;

    CarController underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CarController(carService);
    }

    @Test
    void itShouldGetAllCars() {
        // Given
        given(carService.getAll()).willReturn(getFakeCarList());

        // When
        List<GetCarApiRes> allCars = underTest.getAll();

        // Then
        assertThat(allCars).hasSize(3);
    }

    @Test
    void itShouldGetOneCar() {
        // Given
        List<GetCarApiRes> fakeCarList = getFakeCarList();
        GetCarApiRes car1 = fakeCarList.get(0);
        when(carService.getOne(anyString())).thenReturn(car1);

        // When
        GetCarApiRes foundCar = underTest.getOne(car1.getId());

        // Then
        assertThat(foundCar.getId()).isEqualTo(car1.getId());
    }

    @Test
    void itShouldThrowIfCarNotFound() {
        // Given
        List<GetCarApiRes> fakeCarList = getFakeCarList();
        GetCarApiRes car1 = fakeCarList.get(0);
        when(carService.getOne(any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));

        // When - Then
        assertThatThrownBy(() -> underTest.getOne(car1.getId()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Car not found");
    }

    List<GetCarApiRes> getFakeCarList() {
        return List.of(
                new GetCarApiRes(UUID.randomUUID().toString(), "Fiat punto", LocalDateTime.now(), LocalDateTime.now()),
                new GetCarApiRes(UUID.randomUUID().toString(), "Citroen C4", LocalDateTime.now(), LocalDateTime.now()),
                new GetCarApiRes(UUID.randomUUID().toString(), "Bmw X5", LocalDateTime.now(), LocalDateTime.now())
        );
    }


}