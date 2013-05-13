import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;


public class MailboxFactory extends UnicastRemoteObject implements MailboxFactoryInterface {
	
	
	public MailboxFactory() throws RemoteException {
	}
	
	public boolean createMailbox(String name) throws RemoteException {
		try {
			Mailbox m = new Mailbox(name);
			Naming.rebind(name, m);
			return true;
		} catch(MalformedURLException e) {
			System.out.println(e);	
		}
		return false;
	}
	
}









