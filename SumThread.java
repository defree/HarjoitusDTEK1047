/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice;

import java.net.*;
import java.io.*;

/**
 *
 * @author Kalle
 */
public class SumThread extends Thread{
    
    private int port;
    private SumSlot slot;
    ServerSocket server;
    
    SumThread(int port, SumSlot slot) throws Exception{
        System.out.println("Ctor SumThread in port: "+port);
        this.port = port;
        this.slot = slot;
        server = new ServerSocket(this.port);
    }
    
    public void run() {
        System.out.println("Spawning SumThread in port: "+port);
        try
        {
            Socket client = server.accept();

            InputStream iS = client.getInputStream();
            OutputStream oS = client.getOutputStream();

            ObjectOutputStream oOut = new ObjectOutputStream(oS);
            ObjectInputStream oIn = new ObjectInputStream(iS);
            int input = 1;
            while ((input = oIn.readInt()) != 0)
            {
                System.out.println("port: "+port+" saving: "+input);
                slot.Save(input);
                //System.out.println("port: "+port+" input: "+input);
            }

           // slot.empty();
            System.out.println("Thread End in port: "+port);
            client.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
}
