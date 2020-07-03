package sample;

public class NetConfig {
    // Base elements
    private String address;
    private String mask;

    // Calculated elements
    private String broadcast;
    private String network;
    private String lower;
    private String higher;
    private int nbAvailable;


    NetConfig(String address, String mask) {
        /**
         * Constructor with address and mask
         */
        this.address = address;
        this.mask = mask;

        this.network = calculNetwork(this.address, this.mask);
        this.broadcast = calculBroadcast(this.address, this.mask);

        this.lower = calculLower(this.network);
        this.higher = calculHigher(this.broadcast);
    }

    NetConfig(String address, int mask) {
        /**
         * Constructor with address and classless mask
         */

    }

    private String calculBroadcast(String address, String mask) {
        String[] binAddress = convertIntoBinary(address);
        String[] binMask = convertIntoBinary(mask);
        String[] binBroadcast = new String[4];
        String broadcast = "";

        for (int i = 0; i < binMask.length; i++) {
            String bteBroadcast = "";
            char[] bteMaskArray = binMask[i].toCharArray();
            char[] bteAddressArray = binAddress[i].toCharArray();
            for (int j=0; j < bteMaskArray.length; j++) {
                if (bteMaskArray[j] == '1') {
                    bteBroadcast = bteBroadcast + bteAddressArray[j];
                } else if (bteMaskArray[j] == '0') {
                    bteBroadcast = bteBroadcast + "1";
                }
            }
            binBroadcast[i] = bteBroadcast;
        }

        for (String bteBroadcast : binBroadcast) {
            broadcast = broadcast + Integer.toString(Integer.parseInt(bteBroadcast, 2)) + ".";
        }

        return broadcast.substring(0, (broadcast.length()-1));
    }

    private String calculNetwork(String address, String mask) {
        String[] binAddress = convertIntoBinary(address);
        String[] binMask = convertIntoBinary(mask);
        String[] binNetwork = new String[4];
        String network = "";

        for (int i = 0; i < binMask.length; i++) {
            String bteNetwork = "";
            char[] bteMaskArray = binMask[i].toCharArray();
            char[] bteAddressArray = binAddress[i].toCharArray();
            for (int j=0; j < bteMaskArray.length; j++) {
                if (bteMaskArray[j] == '1') {
                    bteNetwork = bteNetwork + bteAddressArray[j];
                } else if (bteMaskArray[j] == '0') {
                    bteNetwork = bteNetwork + "0";
                }
            }
            binNetwork[i] = bteNetwork;
        }

        for (String bteNetwork : binNetwork) {
            network = network + Integer.toString(Integer.parseInt(bteNetwork, 2)) + ".";
        }

        return network.substring(0, (network.length()-1));
    }

    private String calculHigher(String broadcast) {
        String[] splitBroadcast = broadcast.split("\\.");
        String higher = "";
        splitBroadcast[3] = Integer.toString(Integer.parseInt(splitBroadcast[3])-1);

        for (String bteHigher : splitBroadcast) {
            higher = higher + bteHigher + ".";
        }

        return higher.substring(0, (higher.length()-1));
    }

    private String calculLower(String network) {
        String[] splitNetwork = network.split("\\.");
        String lower = "";
        splitNetwork[3] = Integer.toString(Integer.parseInt(splitNetwork[3])+1);

        for (String bteLower : splitNetwork) {
            lower = lower + bteLower + ".";
        }

        return lower.substring(0, (lower.length()-1));
    }

    private String[] convertIntoBinary(String address) {
        /**
         * Convert a decimal into a binary address
         * 
         * example : 10.10.10.10 => 00001010.00001010.00001010.00001010
         */
        String[] addressArray = address.split("\\.");
        String[] binAddressArray = new String [4];

        for (int i = 0; i < addressArray.length; i++) {
            String bte = Integer.toBinaryString(Integer.parseInt(addressArray[i]));
            while (bte.length() < 8) {
                bte = "0" + bte;
            }
            binAddressArray[i] = bte;
        }

        return binAddressArray;
    }

    public String getBroadcast() {
        return this.broadcast;
    }

    public String getNetwork() {
        return this.network;
    }

    public String getLower() {
        return this.lower;
    }

    public String getHigher() {
        return this.higher;
    }

    public int getNbAvailable() {
        return this.nbAvailable;
    }
}