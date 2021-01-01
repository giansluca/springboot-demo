package org.gmdev.service;

import org.bson.types.ObjectId;
import org.gmdev.utils.UtilsForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarIntegrationTest {

    @Autowired
    UtilsForTest utils;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        utils.deleteAllCars();
    }

    @Test
    void itShouldThrowIfCarNotFound() throws Exception {
        // Given
        String carId = ObjectId.get().toString();

        // When
        ResultActions getCarAction = mockMvc.perform(
                get("/api/v1/car/{id}", carId));

        // Then
        getCarAction.andExpect(status().isNotFound());
    }

    @Test
    void itShouldSelectOneCar() throws Exception {
        // Given
        String savedCarId = utils.insertCar();

        // When
        ResultActions getCarAction = mockMvc.perform(
                get("/api/v1/car/{id}", savedCarId));

        // Then
        getCarAction.andExpect(status().isOk());
    }
}