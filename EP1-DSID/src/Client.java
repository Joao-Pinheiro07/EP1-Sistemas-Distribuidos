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

	private static Scanner sc;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry(5099);
		currentSubcomponents = new ArrayList<Subcomponent>();
		help();

		sc = new Scanner(System.in);
		String command;

		do {
			command = sc.nextLine();
			System.out.println();

			clientCommand(command.trim());
		} while (true);

	}

	private static String[] listRepositories() throws AccessException, RemoteException {
		String[] names = registry.list();
		int i = 0;
		for (String name : names)
			System.out.println(i++ + " - " + name);
		return names;
	}

	private static void bind() throws AccessException, RemoteException, NotBoundException {
		System.out.println("Enter the number of the following servers you want to bind to");
		System.out.println();

		String[] servers = listRepositories();
		System.out.println();

		int index = sc.nextInt();
		sc.nextLine();

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
		System.out.println("Number of parts: " + parts.size());


		if (parts.isEmpty()) {
			System.out.println("This repository is empty!");
			return;
		}

		System.out.println();
		int i = 1;
		for (Part p : parts) {
			System.out.println(i++ + " - " + p.getName() + " (" + p.getUid() + ")");
		}
		System.out.println();
	}

	private static void getp() throws RemoteException {
		if (isCurrentRepositoryEmpty())
			return;
		System.out.println("Enter the code of the part you want to search:");
		System.out.println();

		String code = sc.nextLine();

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
		if (!isValidCurrentPart())
			return;
		System.out.println();
		System.out.println("Code: " + currentPart.getUid());
		System.out.println("Name: " + currentPart.getName());
		System.out.println("Description: " + currentPart.getDescription());
		System.out.println("Server: " + currentPart.getServerRepositoryName());
		System.out.println("Subparts:");

		for (Subcomponent sub : currentPart.getSubcomponents()) {
			Part subPart = sub.getSubPart();
			System.out.println("  -" + subPart.getName() + " (" + subPart.getUid() + ") Quant: " + sub.getQuant());
		}
		System.out.println();
	}

	private static void clearlist() {
		currentSubcomponents.clear();
		System.out.println("List of current subcomponents deleted.");
		return;
	}

	private static void addsubpart() {
		if (!isValidCurrentPart())
			return;
		System.out.println("Enter the number of current parts to be added to the current subcomponent list.");
		System.out.println();

		try {
			int number = sc.nextInt();
			sc.nextLine();
			currentSubcomponents.add(new Subcomponent(currentPart, number));
			System.out.println(number + " " + currentPart.getName() + " added to the current subcomponent list.");
		} catch (InputMismatchException e) {
			System.out.println("The text entered could not be converted to an integer");
		}

	}

	private static void addp() throws RemoteException {
		System.out.println("Enter the name and description separated by a \" - \": (Example: Aerocool - PC fan).");

		String input = sc.nextLine();

		String[] arguments = input.split(" - ");
		if (arguments[1] == "" || arguments[1] == null) {
			System.out.println("Invalid arguments");
			return;
		}

		try {
			Part newPart = new Peca(arguments[0], arguments[1], currentRepository.getName());
			newPart.setSubcomponentes(currentSubcomponents);
			currentRepository.addPart(newPart);
			System.out.println(newPart.getName() + " added to " + currentRepository.getName());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid text format");
		}

	}

	private static void quit() {
		System.out.println("Exiting...");
		System.exit(0);
	}

	private static void partType() {
		if (!isValidCurrentPart())
			return;
		if (currentPart.isPrimitivePart())
			System.out.println("Primitive Part");
		else
			System.out.println("Aggregate part");
	}

	private static void countSubP() {
		if (!isValidCurrentPart())
			return;
		int count = 0;
		for (Subcomponent sub : currentPart.getSubcomponents()) {
			if (sub.getSubPart().isPrimitivePart())
				count++;
		}
		System.out.println();
		System.out.println("The current part have " + count + " primitives subparts.");
		System.out.println();

	}

	private static void listSubP() {
		if (!isValidCurrentPart())
			return;
		for (Subcomponent sub : currentPart.getSubcomponents()) {
			Part subPart = sub.getSubPart();
			System.out.println(subPart.getName() + " (" + subPart.getUid() + ")	Quant: " + sub.getQuant());
		}
	}

	private static void showCurrents() throws RemoteException {
		System.out.println("Current Part: " + currentPart.getName());
		System.out.print("Current subcomponent list: | ");
		for (Subcomponent sub : currentSubcomponents) {
			System.out.print(sub.getSubPart().getName() + " | ");
		}
		System.out.println();
		System.out.println("Current repository: " + currentRepository.getName());
	}

	private static boolean isValidCurrentPart() {
		if (currentPart == null) {
			System.out.println("The current part is not defined, get a part from the current repository");
			return false;
		}
		return true;
	}

	private static boolean isCurrentRepositoryEmpty() throws RemoteException {
		if (currentRepository.getParts().isEmpty()) {
			System.out.println(
					"The current repository is empty, take a part from another repository or add a part to it.");
			return true;
		}
		return false;
	}

	private static void help() {
		System.out.println();
		System.out.println("Type:");
		System.out.println();
		System.out.println("\"bind\": to bind to one repository;");
		System.out.println("\"listp\": to list the parts from the current repository");
		System.out.println(
				"\"getp\": to search the repository for a part by its code and, if found, set it as the current part;");
		System.out.println("\"showp\": to show current part attributes;");
		System.out.println("\"clearlist\": to clear the current list of subcomponents;");
		System.out.println("\"addsubpart\": to add n units of the current part to the current list of subcomponents;");
		System.out.println(
				"\"addp\": to add a part to the current repository. The current subcomponent list is used as the direct subcomponent list of the new part;");
		System.out.println("\"quit\": to terminate client execution;");
		System.out.println("\"partType\": to show the current part type (primitive or aggregated);");
		System.out.println("\"countSubP\": to show how many direct and primitive subcomponents the current part has;");
		System.out.println("\"listSubP\": to show the direct subcomponents of the current part;");
		System.out.println("\"showCurrents\": to show all currents variables;");
		System.out.println("\"help\": to show these instructions again;");
	}

	private static void clientCommand(String command) throws AccessException, RemoteException, NotBoundException {
		switch (command) {
		case "bind":
			bind();
			break;

		case "listp":
			listp();
			break;

		case "getp":
			getp();
			break;

		case "showp":
			showp();
			break;

		case "clearlist":
			clearlist();
			break;

		case "addsubpart":
			addsubpart();
			break;

		case "addp":
			addp();
			break;

		case "quit":
			quit();
			break;

		case "partType":
			partType();
			break;

		case "countSubP":
			countSubP();
			break;

		case "listSubP":
			listSubP();
			break;

		case "help":
			help();
			break;

		case "showCurrents":
			showCurrents();
			break;

		default:
			System.out.println("Invalid command!: " + command);
			break;
		}
	}

}
