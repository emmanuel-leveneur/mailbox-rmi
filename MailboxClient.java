import java.rmi.*;

public class MailboxClient {
	public static void usage() {
		System.out.println("Usage: java MailboxClientInnerSecurityManager");
		System.out.println("\t c id@mailboxserver ||");
		System.out.println("\t s id@mailboxserver myfriend@othermailboxserver \"message\" ||");
		System.out.println("\t v id@mailboxserver");
		System.out.println(" /* c: Creating a mailbox on mailboxserver for id user */");
		System.out.println(" /* s: Sending a message to user myfriend whose mailbox is on othermailboxserver */");
		System.out.println(" /* v: Viewing message on mailboxserver for id user */");
		System.exit(1);
	}
	
	public static void main (String [] argv) {
		String id = null;
		String server = null;
		String url = null;
		char op;
		
		if(argv.length < 2 || argv[0].length() != 1)
			usage();
		
		op = argv[0].charAt(0);
		
		System.setSecurityManager(new RMISecurityManager() {
			public void checkConnect(String host, int port) {}
			public void checkConnect(String host, int port, Object context) {}
			public void checkAccept(String host, int port) {}
		});
		
		try {
			// System.out.println (hello.sayHello()) ;
			switch(op) {
				case 'c':
					id = argv[1].substring(0, argv[1].indexOf("@"));
					server = argv[1].substring(argv[1].indexOf("@") + 1);
					MailboxFactoryInterface factory =
						(MailboxFactoryInterface) Naming.lookup("rmi://" + server + "/Factory");
					System.out.println(factory.createMailbox(id));
					break;
				case 's':
					// Si pas assez d'arguments
					if(argv.length < 4)
						usage();
					
					// rŽcupŽration des informations du client
					String expid = argv[1].substring(0, argv[1].indexOf("@"));
					String expserver = argv[1].substring(argv[1].indexOf("@") + 1);
					MailboxInterface expMailBox = 
							(MailboxInterface) Naming.lookup("rmi://" + expserver + "/" + expid);
					
					
					// Si plusieurs destinataires 
					if(argv.length > 4){
						boolean flag = false;
						for(int i = 2; i < argv.length-1;i++){
							String destid = argv[i].substring(0, argv[i].indexOf("@"));
							String destserver = argv[i].substring(argv[i].indexOf("@") + 1);
							MailboxInterface destMailbox =
								(MailboxInterface) Naming.lookup("rmi://" + destserver + "/" + destid);
							
							if(expMailBox.send(argv[1], destMailbox, argv[argv.length])){
								flag = true;
							}else{
								flag=false;
							}
						}
						if(flag){
							System.out.println("Message envoyŽ avec succs");
						}
					}else{
					
					String destid = argv[2].substring(0, argv[2].indexOf("@"));
					String destserver = argv[2].substring(argv[2].indexOf("@") + 1);
					
					MailboxInterface destMailbox =
						(MailboxInterface) Naming.lookup("rmi://" + destserver + "/" + destid);
					
					System.out.println(expMailBox.send(argv[1], destMailbox, argv[3]));
					}
					
					//System.out.println(destMailbox.deliver(argv[1], argv[3]));
					break;
				case 'v':
					id = argv[1].substring(0, argv[1].indexOf("@"));
					server = argv[1].substring(argv[1].indexOf("@") + 1);
					MailboxInterface mailbox =
						(MailboxInterface) Naming.lookup("rmi://" + server + "/" + id);
					System.out.println(mailbox.checkAll());
					break;
				case 'd':
					// rŽcupŽration des informations du client
					expid = argv[1].substring(0, argv[1].indexOf("@"));
					expserver = argv[1].substring(argv[1].indexOf("@") + 1);
					expMailBox = 
							(MailboxInterface) Naming.lookup("rmi://" + expserver + "/" + expid);
					
					
				default:
					usage();
			}
			
		} catch (Exception e) {
			System.out.println ("Erreur client : " + e) ;
		}
	}
}
