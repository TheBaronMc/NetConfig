import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netconfig.model.Address;
import org.netconfig.model.IPv4Format;

public class TestAddress {
    public String decAddr = "192.168.1.25";
    public String binAddr = "11000000.10101000.00000001.00011001";
    public String hexAddr = "c0:a8:01:19";

    public String[] decAddrArray = {"192", "168", "1", "25"};
    public String[] binAddrArray = {"11000000", "10101000", "00000001", "00011001"};
    public String[] hexAddrArray = {"c0", "a8", "01","19"};

    int hexaBase = IPv4Format.BASE16;
    int decBase = IPv4Format.BASE10;
    int binBase = IPv4Format.BASE2;

    @Before
    public void setUp() {}

    @Test
    public void testDecAddr() {
        Address a = new Address(decBase, decAddr);
        checkAddress(a);
    }

    @Test
    public void testBinAddr() {
        Address a = new Address(binBase, binAddr);
        checkAddress(a);
    }

    @Test
    public void testHexAddr() {
        Address a = new Address(hexaBase, hexAddr);
        checkAddress(a);
    }

    @Test
    public void testDecAddrArray() {
        Address a = new Address(decBase, decAddrArray);
        checkAddress(a);
    }

    @Test
    public void testBinAddrArray() {
        Address a = new Address(binBase, binAddrArray);
        checkAddress(a);
    }

    @Test
    public void testHexAddrArray() {
        Address a = new Address(hexaBase, hexAddrArray);
        checkAddress(a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeArray1() {
        new Address(hexaBase, new String[5]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeArray2() {
        new Address(hexaBase, new String[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWongAddress() {
        new Address(hexaBase, "DZDZQDZQ.DQ.DQ.DQ.");
    }

    public void checkAddress(Address a) {
        assertEquals(decAddr, a.toString(decBase));
        assertEquals(binAddr, a.toString(binBase));
        assertEquals(hexAddr, a.toString(hexaBase));

        assertArrayEquals(decAddrArray, a.toArray(decBase));
        assertArrayEquals(binAddrArray, a.toArray(binBase));
        assertArrayEquals(hexAddrArray, a.toArray(hexaBase));
    }
}
