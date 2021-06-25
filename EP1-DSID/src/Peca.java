import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.UUID;

public class Peca implements Part {	

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private String name;
	private String description;
	private ArrayList<Subcomponent> subcomponentes;
	private String serverRespositoryName;

	public Peca(String name, String description, String serverRespositoryName) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.serverRespositoryName = serverRespositoryName;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Subcomponent> getSubcomponentes() {
		return this.subcomponentes;
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getServerRepositoryName() {
		return this.serverRespositoryName;
	}

	public boolean isPrimitivePart() {
		if (subcomponentes.isEmpty())
			return true;
		return false;
	}

	public PartRepository getPartRepository() throws MalformedURLException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(5099);
		return (PartRepository) registry.lookup(serverRespositoryName);
	}

	public ArrayList<Subcomponent> getSubcomponents() {
		return this.subcomponentes;
	}

	public String getUid() {
		return this.uuid.toString();
	}

	public void setSubcomponentes(ArrayList<Subcomponent> subcomponentes) {
		this.subcomponentes = subcomponentes;
	}
  
}
