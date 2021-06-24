import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netconfig.model.util.Convertor;

public class TestConvertor {
    Convertor cvt;

    int decNum = 55;
    String binNum = "110111";
    String hexNum = "37";

    @Before
    public void setUp() {
        cvt = new Convertor();
    }

    @Test
    public void testHexToBin() {
        String res = cvt.hexToBin(hexNum);
        assertEquals(res, binNum);
    }

    @Test
    public void testHexToDec() {
        int res = cvt.hexToDec(hexNum);
        assertEquals(res, decNum);
    }

    @Test
    public void testDecToBin() {
        String res = cvt.decToBin(decNum);
        assertEquals(res, binNum);
    }

    @Test
    public void testDecToHex() {
        String res = cvt.decToHex(decNum);
        assertEquals(res, hexNum);
    }

    @Test
    public void testBinToDec() {
        int res = cvt.binToDec(binNum);
        assertEquals(res, decNum);
    }

    @Test
    public void testBinToHex() {
        String res = cvt.binToHex(binNum);
        assertEquals(res, hexNum);
    }
}
