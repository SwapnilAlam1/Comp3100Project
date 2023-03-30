import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class client {
    public static void main(String[] args){
       
        try {
        
            Socket socket = new Socket("127.0.0.1", 50000);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream din= new DataInputStream(socket.getInputStream());
            ServerInfo largestServer= new ServerInfo("N/A",0,"N/A",0,0,0);
            String serverMsg="";
            // 3 way handshake
            System.out.println("Target IP: " + socket.getInetAddress() + "target Port: " + socket.getPort());
            System.out.println("Local IP: " + socket.getLocalAddress() + "local Port: " + socket.getLocalPort());
            
            
            output.write("HELO\n".getBytes());
            output.flush();
            System.out.println("SENT: HELO");
            String str = (String) input.readLine();
           
            System.out.println("RCVD:" +str);
            
            output.write("AUTH Swapnil\n".getBytes());
            output.flush();
            System.out.println("SENT: AUTH");
            str = (String) input.readLine();
            System.out.println(str);

            
            
            serverMsg= input.readLine();
            if (serverMsg.equals("OK")){
            sendMessageToServer("REDY",output);
            sendMessageToServer("GETS ALL",output);
            
            serverMsg = input.readLine();
        while (!serverMsg.split(" ")[0].equals("DATA")) {
          serverMsg = input.readLine();
        }

        // loop through data to get largest server type
        sendMessageToServer("OK", output);
        String[] dataVals = serverMsg.split(" ");
        if (dataVals[0].equals("DATA")) {
          int numberOfServers = Integer.parseInt(dataVals[1]);

          for (int i = 0; i < numberOfServers; i++) {
            String serverInfo = input.readLine();
            String[] serverInfoVals = serverInfo.split(" ");

            int serverCores = Integer.parseInt(serverInfoVals[4]);
            if (largestServer.cores < serverCores) {
              String serverType = serverInfoVals[0];
              int serverId = Integer.parseInt(serverInfoVals[1]);
              String serverState = serverInfoVals[2];
              int serverMemory = Integer.parseInt(serverInfoVals[5]);
              int serverDisk = Integer.parseInt(serverInfoVals[6]);

              largestServer = new ServerInfo(serverType, serverId, serverState, serverCores, serverMemory, serverDisk);
            }

          }

        }
        sendMessageToServer("OK", output);
      
        }
        
        // schedule first job
      serverMsg = input.readLine();
      if (serverMsg.equals(".")) {
        sendMessageToServer("SCHD 0 " + largestServer.type + " " + largestServer.id, output);
        System.out.println("SCHD 0 " + largestServer.type + " " + largestServer.id);
      }
            output.write("QUIT\n".getBytes());
            output.flush();
            System.out.println("SENT: QUIT");
            str = (String) input.readLine();
           
            System.out.println("RCVD:" +str);
            
            output.close();
            input.close();
            socket.close();
          }  
            catch (Exception e) {System.out.println(e);}
            //try{TimeUnit. SECONDS.sleep(1);}catch (InterruptedException e){System.out.println(e);}
            }
            
           public static void sendMessageToServer(String message, DataOutputStream outputStream) {
    try {
      outputStream.write((message+"\n").getBytes());
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


class ServerInfo {

  String type;
  int id;
  String state;
  int cores;
  int memory;
  int disk;

  public ServerInfo(String type, int id, String state, int cores, int memory, int disk) {
    this.type = type;
    this.id = id;
    this.state = state;
    this.cores = cores;
    this.memory = memory;
    this.disk = disk;
  }

  public void printServerInfo() {
    System.out.println(
        "Server name: " + type + " " +
        "Server id: " + id + " " +
        "Server cores: " + cores + " " +
        "Server memory: " + memory + " " +
        "Server disk: " + disk
    );
  }

} 

           
