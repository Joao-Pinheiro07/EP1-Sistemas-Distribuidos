import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Part {
	public String getName();

	public String getDescription();

	public PartRepository getPartRepository() throws MalformedURLException, RemoteException, NotBoundException;

	public boolean isPrimitivePart();

	public ArrayList<Subcomponent> getSubcomponents();

	public String getUid();

	public String getServerRepositoryName();
	
	public void setSubcomponentes(ArrayList<Subcomponent> subcomponentes);
	
	public void setServerRespositoryName(String serverRespositoryName);
}
