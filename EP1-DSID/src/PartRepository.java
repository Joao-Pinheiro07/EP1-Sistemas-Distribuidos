import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PartRepository extends Remote {

	public String teste(String input) throws RemoteException;

	public void addPart(Part peca) throws RemoteException;

	public Part findPart(String code) throws RemoteException;

	public ArrayList<Part> getParts() throws RemoteException;

	public String getName() throws RemoteException;
}
