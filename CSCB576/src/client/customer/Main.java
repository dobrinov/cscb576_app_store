package client.customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IAppStoreServer;
import server.ServerOperationResult;
import server.ServerState;

public class Main {

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String name = "AppStore";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            IAppStoreServer comp = (IAppStoreServer) registry.lookup(name);
            ServerState status;
            ServerOperationResult result;

            status = comp.status();
            System.out.println(status);
            result = comp.start();
            System.out.println(result.statusMessage());
            result = comp.pause();
            System.out.println(result.statusMessage());
            result = comp.start();
            System.out.println(result.statusMessage());
            result = comp.restart();
            System.out.println(result.statusMessage());
            result = comp.start();
            System.out.println(result.statusMessage());
            result = comp.stop();
            System.out.println(result.statusMessage());
        } catch (Exception e) {
            System.err.println("AppServer exception:");
            e.printStackTrace();
        }
	}

	
}
