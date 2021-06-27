import org.junit.Before;
import org.junit.Test;
import org.netconfig.model.IPv4Format;
import org.netconfig.model.NetConfig;

import static org.junit.Assert.*;

public class TestNetConfig {
    String decAddress = "192.168.1.25";
    String hexAddress = "c0:a8:01:19";
    String binAddress = "11000000.10101000.00000001.00011001";

    String decMask = "255.255.224.0";
    String hexMask = "ff:ff:e0:00";
    String binMask = "11111111.11111111.11100000.00000000";

    String decNetwork = "192.168.0.0";
    String hexNetwork = "c0:a8:00:00";
    String binNetwork = "11000000.10101000.00000000.00000000";

    String decBroadcast = "192.168.31.255";
    String hexBroadcast = "c0:a8:1f:ff";
    String binBroadcast = "11000000.10101000.00011111.11111111";

    String decHigher = "192.168.31.254";
    String hexHigher = "c0:a8:1f:fe";
    String binHigher = "11000000.10101000.00011111.11111110";

    String decLower = "192.168.0.1";
    String hexLower = "c0:a8:00:01";
    String binLower = "11000000.10101000.00000000.00000001";

    double available = 8190.0;

    int hexBase = IPv4Format.BASE16;
    int decBase = IPv4Format.BASE10;
    int binBase = IPv4Format.BASE2;

    @Test
    public void testDecEntries() {
        NetConfig nc = new NetConfig();
        nc.setAddress(decAddress, decBase);
        nc.setMask(decMask, decBase);

        check(nc);
    }

    @Test
    public void testBinEntries() {
        NetConfig nc = new NetConfig();
        nc.setAddress(binAddress, binBase);
        nc.setMask(binMask, binBase);

        check(nc);
    }

    @Test
    public void testHexEntries() {
        NetConfig nc = new NetConfig();
        nc.setAddress(hexAddress, hexBase);
        nc.setMask(hexMask, hexBase);

        check(nc);
    }

    private void check(NetConfig nc) {
        assertEquals(decAddress, nc.getAddress(decBase));
        assertEquals(hexAddress, nc.getAddress(hexBase));
        assertEquals(binAddress, nc.getAddress(binBase));

        assertEquals(decMask, nc.getMask(decBase));
        assertEquals(hexMask, nc.getMask(hexBase));
        assertEquals(binMask, nc.getMask(binBase));

        assertEquals(decBroadcast, nc.getBroadcast(decBase));
        assertEquals(hexBroadcast, nc.getBroadcast(hexBase));
        assertEquals(binBroadcast, nc.getBroadcast(binBase));

        assertEquals(decNetwork, nc.getNetwork(decBase));
        assertEquals(hexNetwork, nc.getNetwork(hexBase));
        assertEquals(binNetwork, nc.getNetwork(binBase));

        assertEquals(decHigher, nc.getHigher(decBase));
        assertEquals(hexHigher, nc.getHigher(hexBase));
        assertEquals(binHigher, nc.getHigher(binBase));

        assertEquals(decLower, nc.getLower(decBase));
        assertEquals(hexLower, nc.getLower(hexBase));
        assertEquals(binLower, nc.getLower(binBase));

        assertEquals(available, nc.getAvailable(), 0);
    }
}

