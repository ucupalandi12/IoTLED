package com.pi.iot;

import java.io.Serializable;
import java.util.Random;

public class TCP implements Serializable {
    protected String sourcePort;
    protected String destinationPort;
    protected String sequence;
    protected String acknowledge;
    protected String dataOffset;
    protected String flags;
    protected String message1;
    protected String windowSize;
    protected String checksum;
    protected String urgentPointer;
    //protected byte[] options;
    protected String message;

    private static final long serialVersionUID = 752672295647324897L;

//    public TCP() {
//        System.out.println("New TCP Created");
//    }

    public TCP(String sourcePort, String destinationPort, String sequence, String dataOffset, String flags, String message1, String message, String acknowledge, String windowSize, String checksum, String urgentPointer) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.sequence = sequence;
        this.dataOffset = dataOffset;
        this.flags = flags;
        this.message1 = message1;
        this.message = message;
        this.acknowledge = acknowledge;
        this.windowSize = windowSize;
        this.checksum = checksum;
        this.urgentPointer = urgentPointer;


    }

 /** Getter Setter **/
    //get Source Port
    public String getSourcePort() {
     return sourcePort;
    }
    //set Source Port
    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    //get TCP Destination Port
    public String getDestinationPort() {
        return destinationPort;
    }
    //set TCP Destination Port
    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    //get Sequence number
    public String getSequence() {
        return sequence;
    }
    //set Sequence number
    public void setSequence(String seq) {
        this.sequence = seq;
    }

    //get Acknowledge
    //public boolean getAcknowledge() {
        //return this.acknowledge;
    //}
    //set Acknowledge
    //public void setAcknowledge(final boolean ack) {
    //    this.acknowledge = ack;
    //}

    //get TCP Flags
    public String getFlags() {
        return flags;
    }
    //set TCP Flags
    public void setFlags(String flags) {
        this.flags = flags;
    }

    //get OffSet/length
    public String getDataOffset() {
        return dataOffset;
    }
    //set OffSet/length
    public void setDataOffset(String offset) {
        this.dataOffset = offset;
    }

    //get message1
    public String getMessage1() {
        return message1;
    }
    //set message1
    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    //get message
    public String getMessage() {
        return message;
    }
    //set message
    public void setMessage(String message) {
        this.message = message;
    }

    //get ack
    public String getAcknowledge() {
        return acknowledge;
    }
    //set ack
    public void setAcknowledge(String acknowledge) {
        this.acknowledge = acknowledge;
    }

    //get windowsize
    public String getWindowSize() {
        return windowSize;
    }
    //set windowsize
    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    //get checksum
    public String getChecksum() {
        return checksum;
    }
    //set checksum
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    //get pointer
    public String getUrgentPointer() {
        return urgentPointer;
    }
    //set pointer
    public void setUrgentPointer(String urgentPointer) {
        this.urgentPointer = urgentPointer;
    }

    @Override
    public String toString() {
      return "TCP{" +
              "sourcePort='" + sourcePort + '\'' +
              ", destinationPort='" + destinationPort + '\'' +
              ", sequence='" + sequence + '\'' +
              ", dataOffset='" + dataOffset + '\'' +
              ", flags='" + flags + '\'' +
              ", message1='" + message1 +
              ", message='" + message +
              ", acknowledge='" + acknowledge +
              ", windowSize='" + windowSize +
              ", checksum='" + checksum +
              ", urgentPointer='" + urgentPointer +
              '}';
    }

    public String getRandomSourcePort(){
        Random iseng = new Random();
        int intRandom = iseng.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }

    public String getRandomDestinationPort(){
        Random iseng1 = new Random();
        int intRandom = iseng1.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }

    public String getRandomSequence(){
        Random iseng3 = new Random();
        int intRandom = iseng3.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }
    public String getRandomDataOffset(){
        Random iseng4 = new Random();
        int intRandom = iseng4.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }
    public String getRandomFlags(){
        Random iseng5 = new Random();
        int intRandom = iseng5.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }
    public String getRandomWindowSize(){
        Random iseng6 = new Random();
        int intRandom = iseng6.nextInt();
        String strRandom = String.valueOf(intRandom);
        return strRandom;
    }
//    public void writeObject(ObjectOutputStream o) throws IOException {
//        o.writeObject(this);
//    }
//    public void readObject(ObjectInputStream i) throws IOException, ClassNotFoundException {
//        TCP tcp = (TCP) i.readObject();
//        setSourcePort(tcp.getSourcePort());
//        setDestinationPort(tcp.getDestinationPort());
//        setSequence(tcp.getSequence());
//        //setAcknowledge(tcp.getAcknowledge());
//        setDataOffset(tcp.getDataOffset());
//        setMessage(tcp.getMessage());
//    }

}


        //tcp = new TCP("8080", "8080", "8", "4", "1", "1", "0", "true", "16", "3020984DYEN29DMTIRELVO", "90");

