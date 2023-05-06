import java.io.*;
import java.net.*;

public class TCPClient {
	public static void main(String[] args) {
	       try {
	            //InetAddress aHost = InetAddress.getByName(args[0]);
	            //int aPort = Integer.parseInt(args[1]);
	            boolean flag;
	            String serverType = "";
                    int serverID = 0; 
                    String [] temp= null; 
	            //Socket s = new Socket(aHost, aPort);
	            Socket s = new Socket("localhost", 50000);//Hardcode
	            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		    BufferedReader bin = new BufferedReader(new InputStreamReader(s.getInputStream()));
	            //DataInputStream din = new DataInputStream(s.getInputStream());
	            System.out.println("Target IP: " + s.getInetAddress() + " Target Port: "+ s.getPort());
	            System.out.println("Local IP: " + s.getLocalAddress() + " Local Port: " + s.getLocalPort());
	            String username = System.getProperty("user.name");
	          
	            // Send Message to server
	            System.out.println("SENT: HELO");
	            dout.write(("HELO\n").getBytes());
	            dout.flush();
	            // receive message from server
	            String str = (String)bin.readLine();
	            System.out.println("RCVD: "+ str);
	            
	            dout.write(("AUTH "+ username +"\n").getBytes());
	            dout.flush();
	            System.out.println("SENT: AUTH Swapnil");
	            str = (String)bin.readLine();
	            System.out.println("RCVD: "+ str);
	            
	            // Send Message to server
	            while(!str.equals("NONE")){
	            dout.write(("REDY\n").getBytes());
	            dout.flush();
	            System.out.println("SENT: REDY");
	            // receive message from server
	            str = (String)bin.readLine();
	            System.out.println("RCVD: " + str);
	            int ncore = Integer.parseInt(str.split(" ")[4]);
	            int ram = Integer.parseInt(str.split(" ")[5]);
	            int disk = Integer.parseInt(str.split(" ")[6]);
                    dout.write(("GETS Capable " + ncore + " " + ram +" " + disk + "\n").getBytes());
                    dout.flush(); 
                    dout.write(("OK\n").getBytes());
                    dout.flush();
                    str = (String) bin.readLine();
                    System.out.println("RCVD: " + str);
                    int xline = Integer.parseInt(str.split(" ")[1]);
                    dout.write(("OK\n").getBytes());
                    dout.flush();
                    flag = true;                
                    for(int i=0; i < xline; i++){
                    	str = (String) bin.readLine();
                    	if(flag == true){
                    		serverType = (temp[0]);
                    		serverID = Integer.parseInt(temp[1]);
                    	}
                    flag = false;
                    }
            	    dout.write(("OK\n").getBytes());
            	    dout.flush();

            	    dout.write((String.format("SCHD %d %s %d\n", jobID, serverType, serverID)).getBytes());
            	    dout.flush();
            	    System.out.println("RCVD: SCHD");
            	   }
	            // Send Message to server
	            dout.write(("QUIT\n").getBytes());
	            dout.flush();
	            System.out.println("SENT: QUIT");
	            // receive message from server
	            str = (String)bin.readLine();
	            System.out.println("RCVD: " + str);

	            bin.close();
	            dout.close();
	            s.close();
	         }
	         catch(Exception e){System.out.println(e);}
	  }
}
