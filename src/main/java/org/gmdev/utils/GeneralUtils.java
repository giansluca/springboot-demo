package org.gmdev.utils;

import java.util.regex.Pattern;

public class GeneralUtils {

    public static boolean isIntegerNumber(String text) {
        if (text == null) return false;
        return Pattern.matches("[0-9]+", text);
    }

}
