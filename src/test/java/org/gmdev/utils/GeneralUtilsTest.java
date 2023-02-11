package org.gmdev.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralUtilsTest {

    @ParameterizedTest
    @CsvSource(value = {
            "111, true",
            "11G, false",
            "-#@, false",
            "AAA, false",
            "null, false",
    }, nullValues = "null")
    void itShouldCheckIfStringIsANumber(String text, boolean expected) {
        // Given - When
        boolean result = GeneralUtils.isIntegerNumber(text);

        // Then
        assertThat(result).isEqualTo(expected);
    }


}