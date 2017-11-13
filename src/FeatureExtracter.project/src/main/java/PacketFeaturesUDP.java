import org.pcap4j.packet.namednumber.UdpPort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PacketFeaturesUDP extends PacketFeatures {
    private UdpPort srcPort;
    private UdpPort destPort;

    public PacketFeaturesUDP(UdpPort srcPort, UdpPort destPort) {
        this.srcPort = srcPort;
        this.destPort = destPort;
    }

    public void setSrcPort(UdpPort srcPort) {
        this.srcPort = srcPort;
    }

    public void setDestPort(UdpPort destPort) {
        this.destPort = destPort;
    }

    public UdpPort getSrcPort() {
        return srcPort;
    }

    public UdpPort getDestPort() {
        return destPort;
    }

    @Override
    public String toString() {
        return "PacketFeaturesUDP{" +
                "srcPort=" + srcPort +
                ", destPort=" + destPort +
                ", srcAddress=" + srcAddress +
                ", destAddress=" + destAddress +
                ", packetLength=" + packetLength +
                ", srcMAC=" + srcMAC +
                ", destMAC=" + destMAC +
                '}';
    }
    @Override
    public void writeFile(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String data = srcAddress.toString()+","+
                destAddress.toString()+","+
                srcMAC.toString()+","+
                destMAC.toString()+","+
                srcPort.toString()+","+
                destPort.toString()+","+
                packetLength;
        bufferedWriter.write(data+"\n");
        bufferedWriter.close();
        fileWriter.close();
    }
}
