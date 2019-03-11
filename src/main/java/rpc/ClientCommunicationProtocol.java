package rpc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class clientCommunicationProtocol {
    static final int FRAGMENT_SIZE = 15000;                      // Packet size
    private static final int TIMEOUT_DURATION = 10000;
    byte[] packetSize = new byte[FRAGMENT_SIZE];

    private DatagramSocket socket;
    private InetAddress ip;
    private int portNumber;

    void connect(int portNum) {
        try {
            this.ip = InetAddress.getByName("localhost");    // Get localhost IP address
            this.socket = new DatagramSocket();            // Initialize Socket
            socket.setSoTimeout(TIMEOUT_DURATION);
            this.portNumber = portNum;                       // Initialize port number
            //socket.connect(ip,portNum);//Not sure if this is needed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String sendRequest(String request, String callSemantic) {
        String response = "";
        try {

            byte[] requestPayload = new byte[FRAGMENT_SIZE];                                                            // Initialize payload
            requestPayload = request.getBytes();                                                                        // Fill payload
            DatagramPacket requestPacket = new DatagramPacket(requestPayload, requestPayload.length, this.ip, this.portNumber);     // Initialize request packet
            System.out.println("Client sending request packet.");

            switch(callSemantic) {
                case "MAYBE":
                    socket.send(requestPacket);
                    break;
                case "AT_LEAST_ONCE":
                    
            }

            socket.send(requestPacket);                                                                               // Send request packet
            System.out.print("Client request packet sent.");

            byte[] responseData = new byte[FRAGMENT_SIZE];                                                              // Prepare response packet
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);                      // Initialize reponse packet
            System.out.println("Client attempting to receive response packet.");
            socket.receive(responsePacket);                                                                           // Retrieve reponse packet
            System.out.println("Client has received response packet from server.");
            response = new String(responsePacket.getData());                                                            // Get reponse packet's payload

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;                                                                                                // Return request response
    }

}

