import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RepositoryServant extends UnicastRemoteObject implements HelloService {
	
	public RepositoryServant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String teste(String input) throws RemoteException {
		return ("From service: " + input);
	}
}
