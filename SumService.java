/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Kalle
 */
public class SumService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[256];
        ArrayList<SumThread> threads = new ArrayList();
        ArrayList<Integer> ports = new ArrayList();
        String message = "33334";
        sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
        clientSocket.connect(IPAddress, 3126);
        clientSocket.send(sendPacket);
        
        ServerSocket server = new ServerSocket(33334);
        server.setSoTimeout(5000);
        Socket client = server.accept();
        
        InputStream iS = client.getInputStream();
        OutputStream oS = client.getOutputStream();
        
        ObjectOutputStream oOut = new ObjectOutputStream(oS);
	ObjectInputStream oIn = new ObjectInputStream(iS);
        
        int foo = oIn.readInt();
        
        SumRepository repository = new SumRepository();
        
        for (int port = 33335;port<33335+foo;++port){
            oOut.writeInt(port);
            ports.add(port);
            oOut.flush();
        }
        
        for (int port : ports){
            System.out.println(port);
            SumSlot slot = repository.AddSlot();
            SumThread summer = new SumThread(port, slot);
            summer.start();
            threads.add(summer);
        }
        
        while  ((foo = oIn.readInt()) != 0)
        {
            //repository.lock.lock = true;
            System.out.println("Kysely: "+foo);
            int output = -1;
            switch (foo)
            {
                case 1:
                    output = repository.GetTotalSum();
                    break;
                case 2:
                    output = repository.GetMaxSum();
                    break;
                case 3:
                    output = repository.GetTotalN();
                            break;
            }
            oOut.writeInt(output);
            oOut.flush();
            System.out.println("Vastaus: "+output);
            //repository.lock.lock = false;
        }
        
        System.out.println("Loppu");
        client.close();
        /*
        */
     }
}
