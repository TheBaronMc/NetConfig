import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netconfig.model.BaseError;
import org.netconfig.model.IPv4Format;

public class TestIPv4Format {

    int hexaBase = IPv4Format.BASE16;
    int decBase = IPv4Format.BASE10;
    int binBase = IPv4Format.BASE2;

    int[] bases = {hexaBase, decBase, binBase};

    int wrongBase = -1;

    TestIPv4FormatClass testObj;

    @Before
    public void setUp() {
        testObj = new TestIPv4FormatClass();
    }

    @Test(expected = BaseError.class)
    public void testGetRegExWrongBase() {
        testObj.getRegEx(wrongBase);
    }

    @Test
    public void testGetRegExGoodBase() {
        for (int base : bases) {
            testObj.getRegEx(base);
        }

        String binRegEx = testObj.getRegEx(binBase);
        String decRegEx = testObj.getRegEx(decBase);
        String hexaRegEx = testObj.getRegEx(hexaBase);

        assertNotEquals(binRegEx, decRegEx);
        assertNotEquals(binRegEx, hexaRegEx);
        assertNotEquals(hexaRegEx, decRegEx);
    }

    @Test(expected = BaseError.class)
    public void testCheckBaseWrongBase() {
        testObj.checkBase(wrongBase);
    }

    @Test
    public void testCheckBaseGoodBase() {
        for (int base : bases) {
            testObj.checkBase(base);
        }
    }

    @Test(expected = BaseError.class)
    public void testGetSeparatorWrongBase() {
        testObj.getSeparator(wrongBase);
    }

    @Test
    public void testGetSepartorGoodBase() {
        for (int base : bases) {
            testObj.getSeparator(base);
        }

        assertEquals(testObj.getSeparator(decBase), testObj.getSeparator(binBase));
        assertEquals(testObj.getSeparator(hexaBase), ':');
    }

    /**
     * test class to have access to protected methods
     */
    class TestIPv4FormatClass extends IPv4Format {

        @Override
        public String getRegEx(int base) {
            return super.getRegEx(base);
        }

        @Override
        public char getSeparator(int base) {
            return super.getSeparator(base);
        }

        @Override
        public void checkBase(int base) {
            super.checkBase(base);
        }
    }
}


