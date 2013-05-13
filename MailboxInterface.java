import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailboxInterface extends Remote {
	// Méthode d'envoi de mail à partir du serveur du client
	public boolean send(String adresse, MailboxInterface destMailbox, String message) throws RemoteException;
	
	// Méthode de réception d'un nouveau mail sur le serveur du destinataire
	public boolean deliver(Mail newMail) throws RemoteException;
	
	// Récupération de tous les messages
	public String checkAll() throws RemoteException;
	
	// Récupération des messages de la boite d'envoi
	public String checkSent() throws RemoteException;
	
	// Récupération des messages dans la boite de réception
	public String checkInbox() throws RemoteException;
}
