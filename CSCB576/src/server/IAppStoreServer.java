package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAppStoreServer extends Remote {
	int status()  throws RemoteException, Exception;
	int start()   throws RemoteException, Exception;
	int stop()    throws RemoteException, Exception;
	int restart() throws RemoteException, Exception;
	int pause()   throws RemoteException, Exception;
	int resume()  throws RemoteException, Exception;
}