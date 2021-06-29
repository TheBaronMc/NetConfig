package org.netconfig.model;

import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NetConfig {
    private Address address;
    private Mask mask;

    private Address broadcast;
    private Address network;
    private Address lower;
    private Address higher;

    private double available;

    private PropertyChangeSupport pcs;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public NetConfig() {
        this.pcs = new PropertyChangeSupport(this);
        this.available = 0;
    }

    public void setAddress(String address, int base) {
        this.address = new Address(base, address);

        if (this.mask != null) {
            this.compute();
        }
    }

    public void setMask(String mask, int base) {
        this.mask = new Mask(base, mask);

        if (this.address != null) {
            this.compute();
        }
    }

    private void compute() {
        String[] addressArray = this.address.toArray(IPv4Format.BASE2);
        String[] maskArray = this.mask.toArray(IPv4Format.BASE2);

        String[] broadcastArray = new String[4];
        String[] networkArray = new String[4];
        String[] higherArray = new String[4];
        String[] lowerArray = new String[4];

        int n = 0;

        for (int i=0; i < addressArray.length; i++) {
            StringBuilder bBroadcast = new StringBuilder();
            StringBuilder bNetwork = new StringBuilder();

            char[] addressByte = addressArray[i].toCharArray();
            char[] maskByte = maskArray[i].toCharArray();

            for (int y=0; y<addressByte.length; y++) {
                if (maskByte[y] == '1') {
                    bBroadcast.append(addressByte[y]);
                    bNetwork.append(addressByte[y]);
                } else {
                    bBroadcast.append('1');
                    bNetwork.append('0');
                    n++;
                }
            }

            broadcastArray[i] = bBroadcast.toString();
            networkArray[i] = bNetwork.toString();
            if (i == (addressArray.length-1)) {
                bBroadcast.deleteCharAt(bBroadcast.length()-1);
                bNetwork.deleteCharAt(bNetwork.length()-1);
                bBroadcast.append('0');
                bNetwork.append('1');
            }
            higherArray[i] = bBroadcast.toString();
            lowerArray[i] = bNetwork.toString();
        }

        this.broadcast = new Address(IPv4Format.BASE2, broadcastArray);
        this.network = new Address(IPv4Format.BASE2, networkArray);
        this.higher = new Address(IPv4Format.BASE2, higherArray);
        this.lower = new Address(IPv4Format.BASE2, lowerArray);

        this.available = Math.pow(2, n) - 2;

        this.pcs.firePropertyChange("New Computation", null, this);
    }

    public String getAddress(int base) {
        return this.address.toString(base);
    }

    public String getMask(int base) {
        return this.mask.toString(base);
    }

    public String getBroadcast(int base) {
        return this.broadcast.toString(base);
    }

    public String getNetwork(int base) {
        return this.network.toString(base);
    }

    public String getHigher(int base) {
        return this.higher.toString(base);
    }

    public String getLower(int base) {
        return this.lower.toString(base);
    }

    public double getAvailable() {
        return this.available;
    }

    public String toJson() {
        JSONObject jaddr = new JSONObject();
        JSONObject jmask = new JSONObject();
        JSONObject jbroadcast = new JSONObject();
        JSONObject jnetwork = new JSONObject();
        JSONObject jlower = new JSONObject();
        JSONObject jhigher = new JSONObject();

        addIP(jaddr, this.address);
        addIP(jmask, this.mask);
        addIP(jbroadcast, this.broadcast);
        addIP(jnetwork, this.network);
        addIP(jhigher, this.higher);
        addIP(jlower, this.lower);

        JSONObject jobj = new JSONObject();
        jobj.put("ADDRESS", jaddr);
        jobj.put("MASK", jmask);
        jobj.put("BROADCAST", jbroadcast);
        jobj.put("NETWORK", jnetwork);
        jobj.put("HIGHER", jhigher);
        jobj.put("LOWER", jlower);
        jobj.put("AVAILABLE", String.valueOf((int) this.available));

        return jobj.toString();
    }

    private void addIP(JSONObject jObj, IPv4Format ip) {
        jObj.put("BIN", ip.toString(IPv4Format.BASE2));
        jObj.put("DEC", ip.toString(IPv4Format.BASE10));
        jObj.put("HEX", ip.toString(IPv4Format.BASE16));
    }
}
