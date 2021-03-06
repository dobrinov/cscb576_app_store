package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AppStoreServer implements IAppStoreServer {
	
	private ServerState state;

	public AppStoreServer() {
		super();

		// Put the server in default state;
		this.state = ServerState.STOPPED;
	}

	public String[][] getApplicationList() {
		ArrayList<String[]> applications = new ArrayList<String[]>();
		
		String csvFile = "src/applications.db";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	 
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] application = line.split(cvsSplitBy);
				applications.add(application);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return applications.toArray(new String[applications.size()][3]);
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