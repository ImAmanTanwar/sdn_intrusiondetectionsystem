
import org.pcap4j.util.MacAddress;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;

public class PacketFeatures {
    protected Inet4Address srcAddress;
    protected Inet4Address destAddress;
    protected int packetLength;
    protected MacAddress srcMAC;
    protected MacAddress destMAC;

    public void setSrcAddress(Inet4Address srcAddress) {
        this.srcAddress = srcAddress;
    }

    public void writeFile(File file) throws IOException {

    }

    public void setDestAddress(Inet4Address destAddress) {
        this.destAddress = destAddress;
    }

    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }

    public void setSrcMAC(MacAddress srcMAC) {
        this.srcMAC = srcMAC;
    }

    public void setDestMAC(MacAddress destMAC) {
        this.destMAC = destMAC;
    }

    public Inet4Address getSrcAddress() {
        return srcAddress;
    }

    public Inet4Address getDestAddress() {
        return destAddress;
    }

    public int getPacketLength() {
        return packetLength;
    }

    public MacAddress getSrcMAC() {
        return srcMAC;
    }

    public MacAddress getDestMAC() {
        return destMAC;
    }
}
