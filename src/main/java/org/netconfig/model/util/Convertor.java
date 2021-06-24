package org.netconfig.model.util;

/**
 * Num Convertor
 */
public class Convertor {

    /**
     * Convert a binary number into a decimal number
     * @param binaryNumber
     * @return
     */
    public int binToDec(String binaryNumber) {
        return Integer.parseInt(binaryNumber, 2);
    }

    /**
     * Convert a binary number into an hexadecimal number
     * @param binaryNumber
     * @return
     */
    public String binToHex(String binaryNumber) {
        return decToHex(Integer.parseInt(binaryNumber, 2));
    }

    /**
     * Convert a decimal number into a binary number
     * @param decimalNumber
     * @return
     */
    public String decToBin(int decimalNumber) {
        return Integer.toBinaryString(decimalNumber);
    }

    /**
     * Convert a decimal number into an hexadecimal number
     * @param decimalNumber
     * @return
     */
    public String decToHex(int decimalNumber) {
        return Integer.toHexString(decimalNumber);
    }

    /**
     * Convert an hexadecimal number into a binary number
     * @param hexadecimalNumber
     * @return
     */
    public String hexToBin(String hexadecimalNumber) {
        return decToBin(Integer.parseInt(hexadecimalNumber, 16));
    }

    /**
     * Convert an hexadecimal number into a decimal number
     * @param hexadecimalNumber
     * @return
     */
    public int hexToDec(String hexadecimalNumber) {
        return Integer.parseInt(hexadecimalNumber, 16);
    }

}
