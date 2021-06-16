import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RepositoryServant extends UnicastRemoteObject implements PartRepository {

	private String name;
	private ArrayList<Part> repository;

	public RepositoryServant(String serverName) throws RemoteException {
		super();
		this.name = serverName + "Repository";
		this.repository = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	public String teste(String input) throws RemoteException {
		return ("From service: " + input);
	}

	public void addPart(Part peca) throws RemoteException {
		this.repository.add(peca);
	}

	public Part getPart(long code) throws RemoteException {
		return this.repository.stream().filter(p -> p.getUid() == code).findFirst().orElse(null);
	}
	
	public String getName() {
		return this.name;
	}

	public List<Part> listParts() throws RemoteException {
		return this.repository;
	}
}
