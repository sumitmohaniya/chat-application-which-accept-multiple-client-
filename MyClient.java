import java.util.*;
import java.io.*;
import java.net.*;
public  class MyClient{
    public static void main(String args[]) throws IOException{
        // to read data from the keyboard
        Scanner sc=new Scanner(System.in);
        Socket s1=null;
        String msgToSend=null;
        DataInputStream dis=null;
        DataOutputStream dos=null;
        try{
            // create client socket
            s1=new Socket("localhost",1234);
            // to send data coming from the server
            dis=new DataInputStream(s1.getInputStream());
            // to send data to the server
            dos=new DataOutputStream(s1.getOutputStream());
            System.out.println("Connected to the server");
            msgToSend=dis.readUTF();
            System.out.println(msgToSend);
            msgToSend=sc.next();
            dos.writeUTF(msgToSend);
            dos.flush();
        }
        catch(IOException e){
            System.out.println(e);
        }
        System.out.println("Enter data:");
        
        String msgToReceive=null;
        try{
            System.out.println("user can enter only three things");
            System.out.println("1.Date");
            System.out.println("2.Time");
            System.out.println("3.Exit");
            while(!(msgToSend=sc.next().toLowerCase()).equals("exit")){
                //send to the server
                dos.writeUTF(msgToSend);
                dos.flush();
                //receive from the server
                msgToReceive=dis.readUTF();
                System.out.println("Server response:" +msgToReceive);
            }
            System.out.println("disconnect with the server");
            //close connection
            dis.close();
            dos.close();
            s1.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}