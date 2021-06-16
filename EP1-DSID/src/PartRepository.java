import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartRepository extends Remote {

	public String teste(String input) throws RemoteException;

	public void addPart(Part peca) throws RemoteException;

	public Part getPart(long code) throws RemoteException;

	public List<Part> listParts() throws RemoteException;

	public String getName() throws RemoteException;
}
