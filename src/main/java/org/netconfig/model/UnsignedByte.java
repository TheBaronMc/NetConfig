package org.netconfig.model;

import org.netconfig.model.util.Convertor;

public class UnsignedByte {
    String binVal;
    String decVal;
    String hexVal;

    public UnsignedByte(String value, int base) {
        checkByte(value, base);

        Convertor cvt = new Convertor();
        switch (base) {
            case IPv4Format.BASE2:
                binVal = fillBlank(value, IPv4Format.BASE2);
                decVal = String.valueOf(cvt.binToDec(value));
                hexVal = fillBlank(cvt.binToHex(value), IPv4Format.BASE16);
                break;
            case IPv4Format.BASE10:
                binVal = fillBlank(cvt.decToBin(Integer.parseInt(value)), IPv4Format.BASE2);
                decVal = value;
                hexVal = fillBlank(cvt.decToHex(Integer.parseInt(value)), IPv4Format.BASE16);
                break;
            case IPv4Format.BASE16:
                binVal = fillBlank(cvt.hexToBin(value), IPv4Format.BASE2);
                decVal = String.valueOf(cvt.hexToDec(value));
                hexVal = fillBlank(value, IPv4Format.BASE16);
                break;
        }
    }

    private void checkByte(String value, int base) {
        Convertor cvt = new Convertor();
        switch (base) {
            case IPv4Format.BASE2:
                this.checkValue(cvt.binToDec(value));
            break;
            case IPv4Format.BASE10:
                this.checkValue(Integer.parseInt(value));
            break;
            case IPv4Format.BASE16:
                this.checkValue(cvt.hexToDec(value));
            break;
        }
    }

    private void checkValue(int val) {
        if ((val > 255) || (val < 0)) {
            throw new IllegalArgumentException(String.valueOf(val));
        }
    }

    private String fillBlank(String val, int base) {
        StringBuilder valBuilder = new StringBuilder(val);
        if (base == IPv4Format.BASE2) {
            while (valBuilder.length() < 8) {
                valBuilder.insert(0, "0");
            }
            val = valBuilder.toString();
        } else if (base == IPv4Format.BASE16) {
            while (valBuilder.length() < 2) {
                valBuilder.insert(0, "0");
            }
            val = valBuilder.toString();
        }
        return val;
    }

    public String toString(int base) {
        String val = null;

        switch (base) {
            case IPv4Format.BASE2:
                val = binVal;
                break;
            case IPv4Format.BASE10:
                val = decVal;
                break;
            case IPv4Format.BASE16:
                val = hexVal;
                break;
        }

        return val;
    }
}
