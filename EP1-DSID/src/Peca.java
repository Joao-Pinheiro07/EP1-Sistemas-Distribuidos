import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public class Peca implements Part {
	private UUID uuid;
	private String name;
	private String description;
	private ArrayList<Subcomponente> subcomponentes;
	private String repositoryName;

	public Peca(String name, String description, ArrayList<Subcomponente> subcomponentes, String repositoryName) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.subcomponentes = new ArrayList<Subcomponente>();
		this.repositoryName = repositoryName;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Subcomponente> getSubcomponentes() {
		return subcomponentes;
	}

	public String getDescription() {
		return description;
	}

	public boolean isPrimitivePart() {
		if (subcomponentes.isEmpty())
			return true;
		return false;
	}

	public PartRepository getPartRepository() throws MalformedURLException, RemoteException, NotBoundException {
		return (PartRepository) Naming.lookup("rmi://localhost:5099/" + repositoryName);
	}

	public ArrayList<Part> listSubParts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

}
