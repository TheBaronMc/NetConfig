import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netconfig.model.IPv4Format;
import org.netconfig.model.UnsignedByte;

public class TestUnsignedByte {
    int decNum = 55;
    String binNum = "00110111";
    String hexNum = "37";
    int tooHighDecNum = 354;
    String tooHighBinNum = "1111111111";
    String tooHighHexNum = "AFAF";
    String NaN = "DZQ@436q_";

    int hexaBase = IPv4Format.BASE16;
    int decBase = IPv4Format.BASE10;
    int binBase = IPv4Format.BASE2;

    @Before
    public void setUp() {}

    @Test
    public void testDecimalValue() {
        UnsignedByte b = new UnsignedByte(String.valueOf(decNum), decBase);

        assertEquals(binNum, b.toString(binBase));
        assertEquals(hexNum, b.toString(hexaBase));
        assertEquals(String.valueOf(decNum), b.toString(decBase));
    }

    @Test
    public void testBinaryValue() {
        UnsignedByte b = new UnsignedByte(binNum, binBase);

        assertEquals(binNum, b.toString(binBase));
        assertEquals(hexNum, b.toString(hexaBase));
        assertEquals(String.valueOf(decNum), b.toString(decBase));
    }

    @Test
    public void testHexadecimalValue() {
        UnsignedByte b = new UnsignedByte(hexNum, hexaBase);

        assertEquals(binNum, b.toString(binBase));
        assertEquals(hexNum, b.toString(hexaBase));
        assertEquals(String.valueOf(decNum), b.toString(decBase));
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongDecNum() {
        new UnsignedByte(NaN, decBase);
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongBinNum() {
        new UnsignedByte(NaN, binBase);
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongHexNum() {
        new UnsignedByte(NaN, hexaBase);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooHighDecNum() {
        new UnsignedByte(String.valueOf(tooHighDecNum), decBase);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooHighBinNum() {
        new UnsignedByte(tooHighBinNum, binBase);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooHighHexNum() {
        new UnsignedByte(tooHighHexNum, hexaBase);
    }

}
