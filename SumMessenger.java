package sumservice.HarjoitusDTEK1047;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
*
* @author Kalle, Esa
*/

public class SumMessenger {
    

	private InetAddress targetIP = null;
	private byte[] sendMsg = new byte[256];
	
	public void SumMessenger() throws Exception {
		
	}
	
	public void setTargetIP(String addressName) throws Exception {
		targetIP = InetAddress.getByName(addressName);
		//System.out.println(targetIP);
	}
	
	public void sendData(String message, int port) throws Exception{
		
		DatagramSocket clientSocket = new DatagramSocket();
		
        sendMsg = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length);
		
        System.out.println("Target IP: "+targetIP);
        System.out.println("Target Port: "+port);
        
        clientSocket.connect(targetIP, port);
        clientSocket.send(sendPacket);
        
        
	}


}