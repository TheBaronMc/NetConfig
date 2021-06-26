package org.netconfig.model;

import java.util.HashMap;

public abstract class IPv4Format {
    public static final int BASE2 = 2; // BINARY
    public static final int BASE10 = 10; // DECIMAL
    public static final int BASE16 = 16; // HEXADECIMAL

    protected UnsignedByte[] addr;

    /**
     * Get the regex according to the base
     * @param base
     * @return
     */
    protected String getRegEx(int base) {
        String regex = null;
        switch (base) {
            case BASE2:
                regex = "^[0-1]{1,8}.[0-1]{1,8}.[0-1]{1,8}.[0-1]{1,8}$";
                break;
            case BASE10:
                regex = "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$";
                break;
            case BASE16:
                regex = "^[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}$";
                break;
            default:
                throw new BaseError(String.valueOf(base));
        }
        return regex;
    }

    /**
     * Get the separator according to the base
     * @param base
     * @return
     */
    protected char getSeparator(int base) {
        if ((base == BASE2) || (base == BASE10)) {
            return '.';
        } else if (base == BASE16) {
            return ':';
        }
        throw new BaseError(String.valueOf(base));
    }

    /**
     * Check if the base is supported
     * @param base
     */
    protected void checkBase(int base) {
        if (!((base == BASE2) || (base == BASE10) || (base == BASE16))) {
            throw new BaseError(String.valueOf(base));
        }
    }

    /**
     * Get the array corresponding to the address in the given base
     * @param base
     * @return
     */
    public String[] toArray(int base) {
        checkBase(base);

        UnsignedByte[] byteArray = this.addr;
        String[] array = new String[byteArray.length];
        for (int i=0; i<byteArray.length; i++) {
            array[i] = byteArray[i].toString(base);
        }

        return array;
    }

    /**
     * Get the string corresponding to the address in the given base
     * @param base
     * @return
     */
    public String toString(int base) {
        return String.join(String.valueOf(getSeparator(base)), toArray(base));
    }
}
