import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class Client {

	private static Registry registry;

	private static PartRepository currentRepository;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry(5099);
		bind();

	}

	private static String[] listRepositories() throws AccessException, RemoteException {
		String[] names = registry.list();
		int i = 1;
		for (String name : names)
			System.out.println(i++ + "- " + name);
		return names;
	}

	private static void bind() throws AccessException, RemoteException, NotBoundException {
		System.out.println("Enter the number of the following servers you want to bind to");
		System.out.println();

		String[] servers = listRepositories();

		Scanner sc = new Scanner(System.in);
		String number = sc.nextLine();
		sc.close();

		try {

			int index = (Integer.parseInt(number) - 1);
			currentRepository = (PartRepository) registry.lookup(servers[index]);
			System.out.println("Current repository updated: " + currentRepository.getName());

		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {

			if (e instanceof NumberFormatException) {
				System.out.println("The text entered could not be converted to an integer");
			} else if (e instanceof ArrayIndexOutOfBoundsException) {
				System.out.println("The number entered does not exist in the server list");
			}

		}

	}

}
