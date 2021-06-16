import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ApplicationServer {

	private static Registry registry;
	
	private static PartRepository repository;

	public static void main(String[] args) throws RemoteException {
		if (args.length == 0) {
			System.out.println("No server name passed on the command line arguments");
			System.out.println("Exiting...");
			System.exit(1);
		}

		createServer(args[0]);
		

	}

	private static void createServer(String serverName) throws RemoteException {
		try {
			registry = LocateRegistry.createRegistry(5099);
			System.out.println("New registry created");
		} catch (RemoteException e) {
			System.out.println("Registry already exists");
			registry = LocateRegistry.getRegistry(5099);
		} finally {
			createRepository(serverName);
		}
	}

	private static void createRepository(String serverName) throws AccessException, RemoteException {
		try {
			repository = new RepositoryServant(serverName);			
			registry.bind(serverName, repository);
			System.out.println("New server created: " + serverName);
			System.out.println("New repository created: " + repository.getName());
		} catch (AlreadyBoundException e) {
			System.out.println("This server name already exists.");
			System.out.println("Exiting...");
			System.exit(1);
		}
	}
}
