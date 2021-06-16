import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		String[] names = { "servidor 1", "servidor 2", "servidor 3", "servidor 4" };
		int port = 5099;
		Registry registry = LocateRegistry.createRegistry(port);
		for (String name : names)
			registry.rebind(name, new RepositoryServant(name));

	}

}
