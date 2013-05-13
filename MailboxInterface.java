import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailboxInterface extends Remote {
	// M�thode d'envoi de mail � partir du serveur du client
	public boolean send(String adresse, MailboxInterface destMailbox, String message) throws RemoteException;
	
	// M�thode de r�ception d'un nouveau mail sur le serveur du destinataire
	public boolean deliver(Mail newMail) throws RemoteException;
	
	// R�cup�ration de tous les messages
	public String checkAll() throws RemoteException;
	
	// R�cup�ration des messages de la boite d'envoi
	public String checkSent() throws RemoteException;
	
	// R�cup�ration des messages dans la boite de r�ception
	public String checkInbox() throws RemoteException;
}
