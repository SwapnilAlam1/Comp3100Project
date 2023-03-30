import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class server {
    public static void main(String[] args) throws IOException{
      
      
        int aPort= Integer.parseInt(args[0]);
        System.out.println("Port Number: " + aPort);
        ServerSocket socket = new ServerSocket(aPort);
       while(true){
        try {
        
            
            Socket s= socket.accept();
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
DataInputStream din= new DataInputStream(s.getInputStream());
            // 3 way handshake
            System.out.println("Target IP: " + s.getInetAddress() + "target Port: " + s.getPort());
            System.out.println("Local IP: " + s.getLocalAddress() + "local Port: " + s.getLocalPort());
            try{TimeUnit.SECONDS.sleep(10);} catch (InterruptedException e){System.out.println(e);}

            String str = (String) din.readUTF();
            System.out.println("RCVD: " +str);
            dout.writeUTF("G'DAY");
            System.out.println("SENT: G'DAY");
            str = (String) din.readUTF();
            System.out.println("RCVD: " +str);
            dout.writeUTF("BYE");
            System.out.println("SENT: BYE");
            din.close();
            dout.close();
            socket.close();
            }
            catch (Exception e) {System.out.println(e);}
           
            }
            }
            }
            
