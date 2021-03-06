/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice.HarjoitusDTEK1047;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * P��luokka
 * Avaa yhteyden WorkDistributoriin ja l�hett�� sille portin numeron
 * Keskustelee WorkDistributorin kanssa, ilmoittaen sille threadien portit ja summaimien statukset
 * 
 * @author Kalle, Esa
 */
public class SumService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

    	SumMessenger newConnect = new SumMessenger();
    	
    	String addressName = "localhost";
    	
    	newConnect.setTargetIP(addressName);
    	
        //Aloitusportti 33334 l�hetet��n viestin�, 3126 on kohdeportti
        String message = "33334";
        int initPort = 3126;
        
        newConnect.sendData(message,initPort);
       
        //Uusi socketti ilmoitettuun porttiin
	    int socketStartPort = 33334;
        SumSockets server = new SumSockets(socketStartPort);
        
        //Streamin lukijat
        ObjectInputStream oIn = server.getObjectInputStream();
        ObjectOutputStream oOut = server.getObjectOutputStream();
        
        
	    ArrayList<SumThread> threads = new ArrayList();
	    ArrayList<Integer> ports = new ArrayList();
        
	    //Luetaan sockettiin tuleva data
        int foo = oIn.readInt();
        
        SumRepository repository = new SumRepository();
        
        //Summauspalvelinten k�ytt�m�t portit 33335->
        //V�litet��n porttien numerot palvelimelle
        for (int port = 33335;port<33335+foo;++port){
            oOut.writeInt(port);
            ports.add(port);
            oOut.flush();
        }
        //K�yd��n kaikki portit l�pi ja lis�t��n niille oma numerovarasto ja threadi
        for (int port : ports){
            System.out.println(port);
            SumSlot slot = repository.AddSlot();
            SumThread summer = new SumThread(port, slot);
            summer.start();
            threads.add(summer);
        }
        
        //Statuskyselyt, kunhan ei ole 0 arvona. jos luetaan 0, niin ohjelma loppuu
        while  ((foo = oIn.readInt()) != 0)
        {
            //repository.lock.lock = true;
            System.out.println("Kysely: "+foo);
            int output = -1;
            while (!status(threads)) {System.out.println("wait");}
            repository.lock.lock();
            
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
            repository.lock.unlock();
            
            //repository.lock.lock = false;
        }
        
        //Suljetaan treadi
        for (SumThread summer : threads)
        {
            if (summer.isAlive())
            {
                summer.interrupt();
            }
        }
        
        System.out.println("Loppu");
        server.closeSocket();
        /*
        */
     }
    
    //Tarkisetetaan onko threadi valmis ettei tule synkronointivirheit�
    public static boolean status(ArrayList<SumThread> summers)
    {
        for (SumThread summer : summers)
        {
            if (!summer.isReady())
            {
                System.out.println("Thread: "+summer.port);
                return false;
            }
        }
    
        return true;
        
    }
    
}
