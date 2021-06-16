import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	private static Registry registry;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry(5099);
		listRepositories();

	}

	private static String[] listRepositories() throws AccessException, RemoteException {
		String[] names = registry.list();
		int i = 1;
		for (String name : names)
			System.out.println(i++ + "- " + name);
		return names;
	}

}
