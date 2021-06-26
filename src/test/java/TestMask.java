import org.junit.Before;
import org.junit.Test;
import org.netconfig.model.IPv4Format;
import org.netconfig.model.Mask;

public class TestMask {

    public String goodDecMask1 = "255.255.255.0";
    public String goodDecMask2 = "255.255.224.0";

    public String goodHexMask1 = "FF:FF:FF:00";
    public String goodHexMask2 = "FF:FF:E0:00";

    public String goodBinMask1 = "11111111.11111111.11111111.0";
    public String goodBinMask2 = "11111111.11111111.11100000.0";

    public String wrongBinMask = "11111111.11111111.11100001.0";
    public String wrongHexMask = "FF:FF:E1:00";
    public String wrongDecMask = "255.255.225.0";

    @Before
    public void setUp() {}

    @Test(expected = IllegalArgumentException.class)
    public void testWrongBinMask() {
        new Mask(IPv4Format.BASE2, wrongBinMask);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDecMask() {
        new Mask(IPv4Format.BASE10, wrongDecMask);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongHexMask() {
        new Mask(IPv4Format.BASE16, wrongHexMask);
    }

    @Test
    public void testGoodDecMask1() {
        new Mask(IPv4Format.BASE10, goodDecMask1);
    }

    @Test
    public void testGoodDecMask2() {
        new Mask(IPv4Format.BASE10, goodDecMask2);
    }

    @Test
    public void testGoodHexMask1() {
        new Mask(IPv4Format.BASE16, goodHexMask1);
    }

    @Test
    public void testGoodHexMask2() {
        new Mask(IPv4Format.BASE16, goodHexMask2);
    }

    @Test
    public void testGoodBinMask1() {
        new Mask(IPv4Format.BASE2, goodBinMask1);
    }

    @Test
    public void testGoodBinMask2() {
        new Mask(IPv4Format.BASE2, goodBinMask2);
    }
}
