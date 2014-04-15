package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAppStoreServer extends Remote {
	ServerState           status()  throws RemoteException, Exception;
	ServerOperationResult start()   throws RemoteException, Exception;
	ServerOperationResult stop()    throws RemoteException, Exception;
	ServerOperationResult restart() throws RemoteException, Exception;
	ServerOperationResult pause()   throws RemoteException, Exception;
}