package sumservice.HarjoitusDTEK1047;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SumSockets {
	
	private Socket client;
	
	public SumSockets(int startPort) throws Exception {
        ServerSocket server = new ServerSocket(startPort);
        //Timeout 5s
        server.setSoTimeout(5000);
        client = server.accept();
	}
	
	public ObjectInputStream getObjectInputStream() throws Exception{
        InputStream iS = client.getInputStream();
        ObjectInputStream oIn = new ObjectInputStream(iS);
		
		return  oIn;
	}
	
	public ObjectOutputStream getObjectOutputStream() throws Exception{
        OutputStream oS = client.getOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(oS);
		
		return  oOut;
	}
	
	public void closeSocket() throws Exception{
		
		client.close();
	}
	
}
