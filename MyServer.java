import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;
import java.time.*;
public class MyServer{
    public static void main(String args[]) throws Exception {
        //create server socket
        ServerSocket ss=new ServerSocket(1234);
        Socket s;
        while(true){
            try{
                //connect it to client socket
                s=ss.accept();
                System.out.println("connection with a client");
                //create MyServerThread object
                MyServerThread mt=new MyServerThread(s);
                //start MyServerThread
                mt.start();
            }
            catch(Exception e){
                System.out.println("Error:"+e);
            }
        }
    }
}
class MyServerThread extends Thread{
    //to read data from the keyboard
    Scanner sc=new Scanner(System.in);
    DataInputStream dis=null;
    DataOutputStream dos=null;
    PrintWriter pw=null;
    Socket s1=null;
    String name="";
    public MyServerThread(Socket s){
        s1=s;
    }
    public void run(){
        try{
            //to read data coming from the client
            dis=new DataInputStream(s1.getInputStream());
            //to send data to the client
            dos=new DataOutputStream(s1.getOutputStream());
            name=sc.nextLine();
            dos.writeUTF(name);
            dos.flush();
            name=dis.readUTF();
            System.out.println("Connected to a client with " +name);
            String msgToSend="";
            String msgToReceive="";
            //read from client
            while((msgToReceive=dis.readUTF())!=null){
                System.out.println(name +" response:" +msgToReceive);
                if(msgToReceive.equals("date")){
                    Date d=new Date();
                    SimpleDateFormat df=new SimpleDateFormat("MMM dd,yyyy");
                    msgToSend=df.format(d);
                    //send to client
                    dos.writeUTF(msgToSend);
                    dos.flush();
                    //System.out.println(msgToSend);
                }
                else if(msgToReceive.equals("time")){
                    Date d=new Date();
                    SimpleDateFormat df=new SimpleDateFormat("hh:mm a");
                    msgToSend=df.format(d);
                    //send to client
                    dos.writeUTF(msgToSend);
                    dos.flush();
                    //System.out.println(msgToSend);
                }
                else{
                    msgToSend=sc.nextLine();
                    dos.writeUTF(msgToSend);
                    dos.flush();

                }
            }
           // System.out.println("Disconnect with a client:" + "$" +name);
           dis.close();
           dos.close();
           s1.close();
            
        }
        catch(Exception e){
            System.out.println("Disconnect with a client:" + "$"+ name);
        }
    }
}