package distributedcomputing;

import java.net.*;
import java.io.*;
import java.util.*;
/*
This thread spawned from UnitHub sets up a TCP server and wait for volunteers.
Volunteers will either sigh in by
  1) sending "sign in"
  2) sending port number where RMI is running
  in which case we use IRegister to call addToRegister

or volunteers will sigh out by
    1) sending "sign out"
    2) sending port number where RMI is running
    then use IRegister to call removeFromRegister
*/
public class RegisterServer implements Runnable{
  Thread t;
  int port;
  ServerSocket server;
  Socket client;
  InputStream is;
  IRegister register;

  RegisterServer(IRegister register, int port){
    this.port = port;
    this.register = register;
    try{
    	server=new ServerSocket(port);

        System.out.println("RegisterServer listening on \n\tIP address "+
        server.getInetAddress().getHostAddress()+"\n\tPort number "+
        server.getLocalPort());
  	}
  	catch(Exception e){
    }

    t=new Thread(this);
    t.start();

  }

  public void listen(Socket clinet, InputStream is){
    Scanner in = new Scanner(is);
    String msg = in.nextLine();
    System.out.println("reveived: "+msg);
    if(msg.equals("sign in")){
      System.out.println("signing in");
      register.addToRegister(new InetSocketAddress(clinet.getInetAddress(), in.nextInt()));
    }
    else if(msg.equals("sign out")){
      System.out.println("signing out");
      register.removeFromRegister(new InetSocketAddress(clinet.getInetAddress(), in.nextInt()));
    }
  }
  public void run(){

    try
		{
      System.out.println("waiting for client");
			while(true)
			{
        try{
          client = server.accept();
  				is = client.getInputStream();
          System.out.println("calling listen");
          listen(client, is);
			  }
        catch(SocketException se){
    			System.out.println("Disconnected with client\nIP :"
    			+ client.getInetAddress().toString()+" PORT : "+
    			client.getPort());
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		finally{
    			is.close();
    			client.close();
    		}
    }//while end

	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
    try{server.close();}
    catch(Exception e){
  		e.printStackTrace();
  	}
	}

  }

}
