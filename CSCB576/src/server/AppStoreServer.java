package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppStoreServer implements IAppStoreServer {
	
	private ServerState state;

	public AppStoreServer() {
		super();

		// Put the server in default state;
		this.state = ServerState.STOPPED;
	}

	public String[][] getApplicationList() {
		String[][] applications = new String[1][3];

		applications[0][0] = "a";
		applications[0][1] = "b";
		applications[0][2] = "c";

		return applications;
	}

	public ServerState status() throws Exception {
		return this.state;
	}
	
	public ServerOperationResult start() throws Exception {
		if(this.state.possibleFollowUps().contains(ServerState.STARTED)){
			this.state = ServerState.STARTED;
			return ServerOperationResult.SERVER_STARTED;
		} else {
			return ServerOperationResult.SERVER_OPERATION_NOT_POSSIBLE;
		}
	}
	
	public ServerOperationResult stop() throws Exception {
		if(this.state.possibleFollowUps().contains(ServerState.STOPPED)){
			this.state = ServerState.STOPPED;
			return ServerOperationResult.SERVER_STOPPED;
		} else {
			return ServerOperationResult.SERVER_OPERATION_NOT_POSSIBLE;
		}
	}
	
	public ServerOperationResult restart() throws Exception {
		stop();
		start();

		return ServerOperationResult.SERVER_RESTARTED;
	}
	
	public ServerOperationResult pause() throws Exception {
		if(this.state.possibleFollowUps().contains(ServerState.PAUSED)){
			this.state = ServerState.PAUSED;
			return ServerOperationResult.SERVER_PAUSED;
		} else {
			return ServerOperationResult.SERVER_OPERATION_NOT_POSSIBLE;
		}
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