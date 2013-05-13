import java.rmi.RMISecurityManager;
import java.rmi.Naming;
import java.security.Permission;

public class MailboxFactoryServer {
	public static void main (String [ ] argv) {
		/* lancer SecurityManager */
		System.setSecurityManager (new RMISecurityManager() {
            public void checkConnect(String host, int port) {}
            public void checkConnect(String host, int port, Object context) {}
            public void checkAccept(String host, int port) {}
        });

		
		try {
			Naming.rebind("Factory", new MailboxFactory());
			System.out.println ("Serveur pret.") ;
		} catch (Exception e) { 
			System.out.println("Erreur serveur : " + e) ;
		}
	}
}
