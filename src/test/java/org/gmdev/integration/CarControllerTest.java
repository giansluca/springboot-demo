package org.gmdev.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gmdev.api.car.model.GetCarApiRes;
import org.gmdev.dao.car.entity.Car;
import org.gmdev.service.car.CarTestHelper;
import org.gmdev.setup.MongoDBTestContainerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest extends MongoDBTestContainerSetup {

    @Autowired
    CarTestHelper carTestHelper;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        carTestHelper.cleanDb();
    }

    @Test
    void itShouldSelectOneCar() throws Exception {
        // Given
        List<Car> cars = carTestHelper.getFakeCars();
        carTestHelper.saveCarList(cars);

        // When
        ResultActions getCarAction = mockMvc.perform(
                get("/api/v1/car/search-by-name", "savedCarId").param("name", "Golf"));

        // Then
        MvcResult result = getCarAction
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        GetCarApiRes[] carList = mapper.readValue(response, GetCarApiRes[].class);

        assertThat(carList).hasSize(2);
        assertThat(carList).allMatch(car -> car.getName().toLowerCase().contains("golf"));
    }


}