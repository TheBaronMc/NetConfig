package org.netconfig.model;

import org.netconfig.model.util.Convertor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mask extends IPv4Format {
    public Mask(int base, String address) {
        // Check the format
        Pattern p = Pattern.compile(this.getRegEx(base));
        Matcher m = p.matcher(address);
        if (!m.find()) {
            throw new IllegalArgumentException(address);
        }

        String sep = String.valueOf(this.getSeparator(base));
        if (sep.equals(".")) {
            sep = "\\.";
        }
        String[] bytes = address.split(sep);


        assert bytes.length == 4;

        setMask(base, bytes);
    }

    public Mask(int base, String[] address) {
        if (address.length > 4) {
            throw new IllegalArgumentException("There is at most 4 bytes in an address. You gave " + address.length + ".");
        } else if (address.length < 4) {
            throw new IllegalArgumentException("There is at least 4 bytes in an address. You gave " + address.length + ".");
        } else {
            setMask(base, address);
        }
    }

    private void setMask(int base, String[] address) {
        checkBase(base);

        this.addr = new UnsignedByte[4];
        Convertor cvr = new Convertor();

        for (int i=0; i<this.addr.length; i++) {
            this.addr[i] = new UnsignedByte(address[i], base);
        }

        checkMask();
    }

    private void checkMask() {
        boolean pass = false;
        boolean first = true;
        for (UnsignedByte b : addr) {
            for (char bit : b.toString(IPv4Format.BASE2).toCharArray()) {
                if (first && (bit=='0')) {
                    throw new IllegalArgumentException();
                }

                if (bit=='0') {
                    pass = true;
                }

                if (pass && (bit=='1')) {
                    throw new IllegalArgumentException();
                }

                first = false;
            }
        }
    }
}
