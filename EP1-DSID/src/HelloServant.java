import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServant extends UnicastRemoteObject implements HelloService {
	
	public HelloServant() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String teste(String input) throws RemoteException {
		return ("From service: " + input);
	}
}
