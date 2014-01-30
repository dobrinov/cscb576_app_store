package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppStoreServer implements IAppStoreServer {
	
	private ServerState status;

	public AppStoreServer() {
		super();
	}

	public int status() throws Exception {
		throw new Exception("Not implemented");
	}
	
	public int start() throws Exception {
		throw new Exception("Not implemented");
	}
	
	public int stop() throws Exception {
		throw new Exception("Not implemented");
	}
	
	public int restart() throws Exception {
		throw new Exception("Not implemented");
	}
	
	public int pause() throws Exception {
		throw new Exception("Not implemented");
	}
	
	public int resume() throws Exception {
		throw new Exception("Not implemented");
	}

	public static void main(String[] args) {

		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
        	String appStoreServerName = "AppStore";

	        IAppStoreServer appStoreServer = new AppStoreServer();
	        IAppStoreServer stub = (IAppStoreServer) UnicastRemoteObject.exportObject(appStoreServer, 0);
	        
	        Registry registry = LocateRegistry.getRegistry();
	        registry.rebind(appStoreServerName, stub);

	        System.out.println("Server bound");
        } catch (Exception e) {
        	System.err.println("Server exception:");
        	e.printStackTrace();
        }

	}
	
}