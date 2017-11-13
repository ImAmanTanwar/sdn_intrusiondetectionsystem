
import org.pcap4j.packet.namednumber.TcpPort;

import java.io.*;

public class PacketFeaturesTCP extends PacketFeatures {
    private TcpPort srcPort,destPort;
    private Boolean synFlag,rstFlag,ackFlag,finFlag,urgFlag,pushFlag;

    public PacketFeaturesTCP(TcpPort srcPort, TcpPort destPort, Boolean synFlag, Boolean rstFlag, Boolean ackFlag, Boolean finFlag, Boolean urgFlag, Boolean pushFlag) {
        this.srcPort = srcPort;
        this.destPort = destPort;
        this.synFlag = synFlag;
        this.rstFlag = rstFlag;
        this.ackFlag = ackFlag;
        this.finFlag = finFlag;
        this.urgFlag = urgFlag;
        this.pushFlag = pushFlag;
    }

    public void setSrcPort(TcpPort srcPort) {
        this.srcPort = srcPort;
    }

    public void setDestPort(TcpPort destPort) {
        this.destPort = destPort;
    }

    public void setSynFlag(Boolean synFlag) {
        this.synFlag = synFlag;
    }

    public void setRstFlag(Boolean rstFlag) {
        this.rstFlag = rstFlag;
    }


    public void setAckFlag(Boolean ackFlag) {
        this.ackFlag = ackFlag;
    }

    public void setFinFlag(Boolean finFlag) {
        this.finFlag = finFlag;
    }

    public void setUrgFlag(Boolean urgFlag) {
        this.urgFlag = urgFlag;
    }

    public void setPushFlag(Boolean pushFlag) {
        this.pushFlag = pushFlag;
    }

    public TcpPort getSrcPort() {
        return srcPort;
    }

    public TcpPort getDestPort() {
        return destPort;
    }

    public Boolean getSynFlag() {
        return synFlag;
    }

    public Boolean getRstFlag() {
        return rstFlag;
    }

    public Boolean getAckFlag() {
        return ackFlag;
    }

    public Boolean getFinFlag() {
        return finFlag;
    }

    public Boolean getUrgFlag() {
        return urgFlag;
    }

    public Boolean getPushFlag() {
        return pushFlag;
    }

    @Override
    public String toString() {
        return "PacketFeaturesTCP{" +
                "srcPort=" + srcPort +
                ", destPort=" + destPort +
                ", synFlag=" + synFlag +
                ", rstFlag=" + rstFlag +
                ", ackFlag=" + ackFlag +
                ", finFlag=" + finFlag +
                ", urgFlag=" + urgFlag +
                ", pushFlag=" + pushFlag +
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
                synFlag.toString()+","+
                rstFlag.toString()+","+
                ackFlag.toString()+","+
                finFlag.toString()+","+
                urgFlag.toString()+","+
                pushFlag.toString()+","+
                packetLength;
        bufferedWriter.write(data+"\n");
        bufferedWriter.close();
        fileWriter.close();
    }
}
