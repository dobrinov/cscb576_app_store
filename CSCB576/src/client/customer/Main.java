package client.customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IAppStoreServer;

public class Main {

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String name = "AppStore";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            IAppStoreServer comp = (IAppStoreServer) registry.lookup(name);
            int status = comp.start();
            System.out.println(status);
        } catch (Exception e) {
            System.err.println("AppServer exception:");
            e.printStackTrace();
        }
	}

	
}
