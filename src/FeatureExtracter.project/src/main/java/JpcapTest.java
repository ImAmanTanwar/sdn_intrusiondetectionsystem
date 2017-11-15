import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class JpcapTest  {
    private static final int LENGTH = 65536;
    private static final int TIMEOUT = 10;
    private static final String TCP_FILE_HEAD = "srcIP,destIP,srcMAC,destMAC,srcPort,destPort,isSYN,isRST,isACK,isFIN,isURG,isPSH,packetLen";
    private static final String UDP_FILE_HEAD = "srcIP,destIP,srcMAC,destMAC,srcPort,destPort,packetLen";
    private static final String PATH = "Packet Feature Logs";
    private static final String LOG = "_log_";
    private static final String TCP = "tcp";
    private static final String UDP = "udp";
    private static final String STARTJSON = "{\"packets\":[";
    private static final String ENDJSON = "]}";
    static FileWriter fileWriter;
    static BufferedWriter bufferedWriter;
    static  String serverAddress="172.16.167.1";
    static String serverPort="8000";

    public static void main(String[] str) throws PcapNativeException, IOException, NotOpenException {
        new File(PATH).mkdir();
        Date date = new Date();

        if(str.length==2) {
            serverAddress = str[0];
            serverPort = str[1];
        }
        else if(str.length==1) {
            serverAddress = str[0];
        }

        String currTime = date.getHours()+""+date.getMinutes()+""+date.getSeconds()+"_"+date.getDate()+""+date.getMonth()+""+date.getYear();
        File tcpFile = setLogFiles(TCP,currTime,TCP_FILE_HEAD);
        File udpFile = setLogFiles(UDP,currTime,UDP_FILE_HEAD);
        List<PcapNetworkInterface> listInterfaces = Pcaps.findAllDevs();
        System.out.println("Interfaces List:");
        for(int i=0;i<listInterfaces.size();i++) {
            PcapNetworkInterface iface = listInterfaces.get(i);
            System.out.println((i+1)+". "+iface.getName());
        }

        Scanner scanner = new Scanner(System.in);
        int interfaceNum = scanner.nextInt();

        PcapNetworkInterface selectedInterface = listInterfaces.get(interfaceNum-1);
        PcapHandle handle = selectedInterface.openLive(LENGTH,PromiscuousMode.PROMISCUOUS,TIMEOUT);

        System.out.println("Packet feature log started generated.....\nCheck Files:");
        System.out.println(tcpFile.getName()+"\n"+udpFile.getName());
        System.out.println("Press CTRL+C to exit....");

        int packetCount = 0;
        StringBuilder apiData = new StringBuilder();
        apiData.append(STARTJSON);


        while(true) {
            if(packetCount == 100) {
                packetCount = 0;
                apiData.append(ENDJSON);
                System.out.println(apiData.toString());
                APIService.sendReq(serverAddress,serverPort,apiData.toString());
                apiData.setLength(0);
                apiData.append(STARTJSON);
            }


            Packet packet = handle.getNextPacket();
            File currFile;


            if(packet!=null) {
                EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                PacketFeatures packetFeatures = null;
                String data = "";
                if(ethernetPacket!=null) {
                    IpV4Packet ipV4Packet = ethernetPacket.getPayload().get(IpV4Packet.class);
                    if(ipV4Packet!=null) {
                        TcpPacket tcpPacket = ipV4Packet.getPayload().get(TcpPacket.class);
                        if (tcpPacket != null) {
                            TcpPacket.TcpHeader tcpHeader = tcpPacket.getHeader();
                            currFile = tcpFile;
                            packetFeatures = new PacketFeaturesTCP(tcpHeader.getSrcPort(),
                                    tcpHeader.getDstPort(),
                                    tcpHeader.getSyn(),
                                    tcpHeader.getRst(),
                                    tcpHeader.getAck(),
                                    tcpHeader.getFin(),
                                    tcpHeader.getUrg(),
                                    tcpHeader.getPsh());
                            data += "{\"src_port\":\""+tcpHeader.getSrcPort()+"\",\"dest_port\":\""+tcpHeader.getDstPort()+"\"";
                        }

                        else {
                            UdpPacket udpPacket = ipV4Packet.getPayload().get(UdpPacket.class);
                            currFile = udpFile;
                            if (udpPacket != null) {
                                UdpPacket.UdpHeader udpHeader = udpPacket.getHeader();
                                packetFeatures = new PacketFeaturesUDP(udpHeader.getSrcPort(),
                                        udpHeader.getDstPort());
                                data += "{\"src_port\":\""+udpHeader.getSrcPort()+"\",\"dest_port\":\""+udpHeader.getDstPort()+"\"";
                            }
                        }


                        if(packetFeatures!=null) {
                            packetFeatures.setPacketLength(ipV4Packet.getHeader().getTotalLength());
                            packetFeatures.setDestAddress(ipV4Packet.getHeader().getDstAddr());
                            packetFeatures.setSrcAddress(ipV4Packet.getHeader().getSrcAddr());
                            packetFeatures.setSrcMAC(ethernetPacket.getHeader().getSrcAddr());
                            packetFeatures.setDestMAC(ethernetPacket.getHeader().getDstAddr());
                            packetFeatures.writeFile(currFile);
                            data += ",\"src_ip\":\""+packetFeatures.getSrcAddress()+"\",\"dest_ip\":\""+packetFeatures.getDestAddress()+ "\"}";
                            if(packetCount == 0)
                                apiData.append(data);
                            else
                                apiData.append(","+data);
                            packetCount++;
                        }
                    }
                }
            }
        }
    }

    public static File setLogFiles(String type,String currTime,String header) throws IOException {
        File file = new File(PATH + "/" + type + LOG +currTime +".csv");
        fileWriter = new FileWriter(file.getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(header+"\n");
        bufferedWriter.close();
        return file;
    }
}
