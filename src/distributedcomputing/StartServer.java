package distributedcomputing;

import java.net.*;
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/*
this Binds rmi to UnitCompute
and then volunteers to (registerIp,registerPort) by sending "sign in" and
rmiRegistryPort which is port in volunteer's machine where rmi is running
*/
public final class StartServer
{
	String registerIp;
	int registerPort;
	int rmiRegistryPort;
        UnitCompute uc;
        StartServer(int rmiRegistryPort) throws RemoteException, MalformedURLException, Exception{
            this.setRmiRegistryPort(rmiRegistryPort);
            uc = new UnitCompute();
            LocateRegistry.createRegistry(rmiRegistryPort);
            Naming.rebind("rmi://localhost:"+rmiRegistryPort+"/UnitCompute",uc);
        }
        
        public String getRegisterIp(){
            return registerIp;
        }
        public int getRegisterPort(){
            return registerPort;
        }
        public int getRmiRegistryPort(){
            return rmiRegistryPort;
        }
        public void setRegisterIp(String registerIp){
            this.registerIp = registerIp;
        }
        public void setRegisterPort(int registryPort){
            this.registerPort = registryPort;
        }
        public void setRmiRegistryPort(int rmiRegistryPort) throws Exception{
            if(rmiRegistryPort <= 1024)
                throw new Exception();    
            else{
                this.rmiRegistryPort = rmiRegistryPort;
            }
        }
	public void signIn() throws IOException, RemoteException, MalformedURLException{
            
            OutputStream os;
            PrintStream ps;
            Socket client;

            client = new Socket(registerIp, registerPort);
            os = client.getOutputStream();
            ps = new PrintStream(os);
            System.out.println("sign in sent to register server at "+registerIp+" "+registerPort);
            ps.println("sign in");
            ps.println(rmiRegistryPort);
            
            ps.close();
            os.close();
            client.close();
            
                       
	}
	public void signOut() throws IOException, RemoteException{
            OutputStream os;
            PrintStream ps;
            Socket client;

            client = new Socket(registerIp, registerPort);
            os = client.getOutputStream();
            ps = new PrintStream(os);
            System.out.println("sign out sent to register server at "+registerIp+" "+registerPort);
            ps.println("sign out");
            ps.println(rmiRegistryPort);


            ps.close();
            os.close();
            client.close();
            
    	}
        /*
	public static void main (String[] args) throws IOException
	{
		rmiRegistryPort = Integer.parseInt(args[0]);
		registerIp = args[1];
		registerPort = Integer.parseInt(args[2]);
		try{
			System.out.println("\tcreating UnitCompute object");
			UnitCompute uc = new UnitCompute();

			System.out.println("\tcalling rebind");
			LocateRegistry.createRegistry(rmiRegistryPort);
			Naming.rebind("rmi://localhost:"+rmiRegistryPort+"/UnitCompute",uc);
			System.out.println("server ready");
			signIn();
			System.out.println(">>Enter when you want to quit volunteering");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
		}
		catch(RemoteException re){
			System.out.println("Remote Exception :"+re.getMessage());
	    re.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			signOut();
		}

	}*/
}
