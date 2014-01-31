package client.customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IAppStoreServer;
import server.ServerOperationResult;

public class Main {

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String name = "AppStore";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            IAppStoreServer comp = (IAppStoreServer) registry.lookup(name);
            ServerOperationResult status = comp.status();
            System.out.println(status.statusMessage());
            status = comp.start();
            System.out.println(status.statusMessage());
            status = comp.pause();
            System.out.println(status.statusMessage());
            status = comp.start();
            System.out.println(status.statusMessage());
            status = comp.restart();
            System.out.println(status.statusMessage());
            status = comp.start();
            System.out.println(status.statusMessage());
            status = comp.stop();
            System.out.println(status.statusMessage());
        } catch (Exception e) {
            System.err.println("AppServer exception:");
            e.printStackTrace();
        }
	}

	
}
