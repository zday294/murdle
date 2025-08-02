package org.zday.murdle.util;

public class AssortedUtils {


    public static String stringMultiply(String string, int multiplier) {
        return new String(new char[multiplier]).replace("\0", string);
    }

//    public static String stringMultiply(String string, int multiplier) {
//        String comboString = "";
//        for (int i = 0; i < multiplier; i++) {
//            comboString += string;
//        }
//        return comboString;
//    }
}
