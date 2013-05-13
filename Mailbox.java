import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Mailbox extends UnicastRemoteObject implements MailboxInterface {
	String name;
	Boite sentbox;
	Boite inbox;
	
	public Mailbox(String n) throws RemoteException {
		name = n;
		sentbox = new Boite();
		inbox = new Boite();
	}
	
	
	public boolean send(String adresse, MailboxInterface destMailbox, String message) throws RemoteException{
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(date);
		
		// Création du mail à enregistrer sur le serveur du destinataire
		Mail newMail = new Mail(adresse, dateString, message, "Inbox");
		this.sentbox.mails.add(newMail);
		if(destMailbox.deliver(newMail))
			return true;
		else return false;
		
		
	}
	
	public boolean deliver(Mail newMail) throws RemoteException{
		if(this.inbox.mails.add(newMail)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public String checkAll() throws RemoteException {
		String allMails = "Aucun message";
		
		if( this.inbox.mails.size() < 1 && this.sentbox.mails.size() < 1){
			return allMails;
		}else{
			if(this.sentbox.mails.size() < 1){
				allMails = "\n\n Aucun Message dans Boite d'envoi\n\n============== BOITE INBOX ==============";
				for (int i = 0; i < this.inbox.mails.size(); i++){
					allMails = allMails + "ID:"+ i + "\n" + this.inbox.mails.get(i).date + " - " + this.inbox.mails.get(i).adresse
							+ "\n" + this.inbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
				}
				return allMails;
			}
			else if(this.inbox.mails.size() < 1){
				allMails = "\n\n Aucun Message dans Inbox \n\n============== BOITE D'ENVOI ==============";
				for (int i = 0; i < this.sentbox.mails.size(); i++){
					allMails = allMails + "ID:"+ i + "\n" + this.sentbox.mails.get(i).date + " - " + this.sentbox.mails.get(i).adresse
							+ "\n" + this.sentbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
				}
				return allMails;
			}else{
				allMails = "============== INBOX ==============\n\n\n\n";
				for (int i = 0; i < this.inbox.mails.size(); i++){
					allMails = allMails + "ID:"+ i + "\n" + this.inbox.mails.get(i).date + " - " + this.inbox.mails.get(i).adresse
							+ "\n" + this.inbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
				}
				allMails = "============== BOITE D'ENVOI ==============\n\n\n\n";
				for (int i = 0; i < this.sentbox.mails.size(); i++){
					allMails = allMails + "ID:"+ i + "\n" + this.sentbox.mails.get(i).date + " - " + this.sentbox.mails.get(i).adresse
							+ "\n" + this.sentbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
				}
				return allMails;
			}
		}
	}
	
	public String checkSent() throws RemoteException {
		String allMails = "Aucun message";
		if(this.sentbox.mails.size() > 0){
			allMails = "============== BOITE D'ENVOI ==============\n\n\n\n";
			for (int i = 0; i < this.sentbox.mails.size(); i++){
				allMails = allMails + "ID:"+ i + "\n" + this.sentbox.mails.get(i).date + " - " + this.sentbox.mails.get(i).adresse
						+ "\n" + this.sentbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
			}
		}
		return allMails;
	}
	
	public String checkInbox() throws RemoteException{
		String allMails = "Aucun message";
		if(this.inbox.mails.size() > 0){
			allMails = "============== INBOX ==============\n\n";
			for (int i = 0; i < this.inbox.mails.size(); i++){
				allMails = allMails + "ID:"+ i + "\n" + this.inbox.mails.get(i).date + " - " + this.inbox.mails.get(i).adresse
						+ "\n" + this.inbox.mails.get(i).message + "\n--------------------------------------------------------\n\n";
			}
		}
		return allMails;
		
		/* ============== INBOX ==============
		 * 
		 * 
		 * ID:0
		 * 13/05/2013 - emmanuel@localhost
		 * Hello World!
		 *
		 * ID:1
		 * 13/05/2013 - ludovic@localhost
		 * Hello World Manu!
		 */
	}
}









