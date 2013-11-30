/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice.HarjoitusDTEK1047;

import java.net.*;
import java.io.*;

/**
 *
 * @author Kalle, Esa
 */
public class SumThread extends Thread{
    
    public  int port;
    private SumSlot slot;
    private ServerSocket server;
    private InputStream iS;
    
    SumThread(int port, SumSlot slot) throws Exception{
        System.out.println("Ctor SumThread in port: "+port);
        this.port = port;
        this.slot = slot;
        
        //T‰m‰nkin voisi siirt‰‰ Sumsocketsiin
        server = new ServerSocket(this.port);
    }
    
    public void run() {
        System.out.println("Spawning SumThread in port: "+port);
        try
        {
            Socket client = server.accept();

            iS = client.getInputStream();
            
            ObjectInputStream oIn = new ObjectInputStream(iS);
            int input = 1;

            while ((input = oIn.readInt()) != 0)
            {
                
                slot.Save(input);
                //System.out.println("port: "+port+" saving: "+input);
                System.out.println("Input : "+input+" Sum: "+slot.GetSum()+ " N: "+ slot.GetN()+ " port " + (port-33335));
                //System.out.println("port: "+port+" input: "+input);
            }

            //slot.empty();
            System.out.println("Thread End in port: "+port);
            
            oIn.close();
            iS.close();
            client.close();
            server.close();
        }
        catch (Exception e)
        {
            System.out.println("port:"+port+" error");
            e.printStackTrace();
            
        }
    }
    //Onko threadi valmis
    public boolean isReady()
    {
        try
        {
            
        
            if (iS != null && !server.isClosed()){
                
                return iS.available() == 0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
    
    
    
}
