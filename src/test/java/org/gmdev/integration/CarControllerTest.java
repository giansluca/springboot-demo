package org.gmdev.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

//    @Autowired
//    UtilsForTest utils;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @AfterEach
//    void tearDown() {
//        utils.deleteAllCars();
//    }

//    //@Test
//    void itShouldThrowIfCarNotFound() throws Exception {
//        // Given
//        String carId = ObjectId.get().toString();
//
//        // When
//        ResultActions getCarAction = mockMvc.perform(
//                get("/api/v1/car/{id}", carId));
//
//        // Then
//        getCarAction.andExpect(status().isNotFound());
//    }
//
//    //@Test
//    void itShouldSelectOneCar() throws Exception {
//        // Given
//        String savedCarId = utils.insertCar();
//
//        // When
//        ResultActions getCarAction = mockMvc.perform(
//                get("/api/v1/car/{id}", savedCarId));
//
//        // Then
//        getCarAction.andExpect(status().isOk());
//    }
}