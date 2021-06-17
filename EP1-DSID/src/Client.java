import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class Client {

	private static Registry registry;

	private static PartRepository currentRepository;

	private static Part currentPart;

	private static ArrayList<Subcomponent> currentSubcomponents;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry(5099);
		bind();

	}

	private static String[] listRepositories() throws AccessException, RemoteException {
		String[] names = registry.list();
		int i = 0;
		for (String name : names)
			System.out.println(i++ + "- " + name);
		return names;
	}

	private static void bind() throws AccessException, RemoteException, NotBoundException {
		System.out.println("Enter the number of the following servers you want to bind to");
		System.out.println();

		String[] servers = listRepositories();
		System.out.println();

		Scanner sc = new Scanner(System.in);
		int index = sc.nextInt();
		sc.close();

		try {

			currentRepository = (PartRepository) registry.lookup(servers[index]);
			System.out.println("Current repository updated: " + currentRepository.getName());

		} catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {

			if (e instanceof InputMismatchException) {
				System.out.println("The text entered could not be converted to an integer");
			} else if (e instanceof ArrayIndexOutOfBoundsException) {
				System.out.println("The number entered does not exist in the server list");
			}

		}

	}

	private static void listp() throws RemoteException {
		ArrayList<Part> parts = currentRepository.getParts();
		System.out.println();
		int i = 0;
		for (Part p : parts) {
			System.out.println(i++ + "- " + p.getName());
		}
		System.out.println();
	}

	private static void getp() throws RemoteException {
		System.out.println("Enter the code of the part you want to search:");
		System.out.println();

		Scanner sc = new Scanner(System.in);
		String code = sc.nextLine();
		sc.close();

		Part part = currentRepository.findPart(code);
		if (part != null) {
			System.out.println("Part found: " + part.getName());
			currentPart = part;
			System.out.println(part.getName() + " defined as current part.");
		} else {
			System.out.println("Part not found.");
		}

		return;
	}

	private static void showp() {
		System.out.println();
		System.out.println("Code: " + currentPart.getUid());
		System.out.println("Name: " + currentPart.getName());
		System.out.println("Description: " + currentPart.getDescription());
		System.out.println("Server: " + currentPart.getServerRepositoryName());
		System.out.println("Subparts:");

		for (Subcomponent sub : currentPart.getSubcomponents()) {
			Part subPart = sub.getSubPart();
			System.out.println("	-" + subPart.getName() + " (" + subPart.getUid() + ")	Quant: " + sub.getQuant());
		}
		System.out.println();
	}

	private static void clearlist() {
		currentSubcomponents.clear();
		System.out.println("List of current subcomponents deleted.");
		return;
	}

	private static void addsubpart() {
		System.out.println("Enter the number of current parts to be added to the current subcomponent list.");
		System.out.println();

		Scanner sc = new Scanner(System.in);

		try {
			int number = sc.nextInt();
			currentSubcomponents.add(new Subcomponent(currentPart, number));
			System.out.println(number + " " + currentPart.getName() + " added to the current subcomponent list.");
		} catch (InputMismatchException e) {
			System.out.println("The text entered could not be converted to an integer");
		} finally {
			sc.close();
		}

	}
	
	private static void addp() throws RemoteException {
		currentPart.setSubcomponentes(currentSubcomponents);
		currentPart.setServerRespositoryName(currentRepository.getName());
		currentRepository.addPart(currentPart);
		System.out.println(currentPart.getName() + " added to " + currentRepository.getName());
	}

	private static void quit() {
		System.out.println("Exiting...");
		System.exit(0);
	}
	
	private static void createp() throws RemoteException {
		System.out.println("Enter the name and description separated by a \" - \" (Example: Aerocool - PC fan ).");
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		sc.close();		
		
		String[] arguments = input.split(" - ");		
		if(arguments[1] == "" || arguments[1] == null) {
			System.out.println("Invalid arguments");
			return;
		}
		currentPart = new Peca(arguments[0], arguments[1]);
		System.out.println(currentPart.getName() + " created and defined as current part.");
		return;		
		
	}
	
	private static void repoInfo() throws RemoteException {
		System.out.println("Name: " + currentRepository.getName() + "Number of parts: " + currentRepository.getParts().size());
	}
	
	private static void partType() {
		if(currentPart.isPrimitivePart()) System.out.println("Primitive Part");
		else System.out.println("Aggregate part");
	}
	
	private static void countSubP() {
		int count = 0;
		for (Subcomponent sub : currentPart.getSubcomponents()) {
			if(sub.getSubPart().isPrimitivePart()) count++;
		}
		System.out.println();
		System.out.println("The current part have " + count + " primitives subparts.");
		System.out.println();
		
	}
	
	private static void listSubP() {
		for (Subcomponent sub : currentPart.getSubcomponents()) {
			Part subPart = sub.getSubPart();
			System.out.println(subPart.getName() + " (" + subPart.getUid() + ")	Quant: " + sub.getQuant());
		}
	}
	
//	private static fillCurrentPart() {
//		System.out.println();
//	}
	
	
}
