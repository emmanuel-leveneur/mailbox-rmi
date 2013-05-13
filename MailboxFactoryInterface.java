import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailboxFactoryInterface extends Remote {
	public boolean createMailbox(String name) throws RemoteException;
}
