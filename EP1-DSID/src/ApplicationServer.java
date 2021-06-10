import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("hello", new HelloServant());
	}

}
