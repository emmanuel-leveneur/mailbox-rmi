import java.rmi.*;

public class MailboxClient {
	public static void usage() {
		System.out.println("Usage: java MailboxClientInnerSecurityManager");
		
		System.out.println("\t c id@mailboxserver ||");
		System.out.println("\t s id@mailboxserver myfriend@othermailboxserver \"message\" ||");
		System.out.println("\t v id@mailboxserver [all|inbox|sentbox]");
		System.out.println("\t d id@mailboxserver [inbox|sentbox] id_message");
		System.out.println("\t t id@mailboxserver myfriend@othermailboxserver [inbox|sentbox] id_message ");
		
		System.out.println(" /* c: Creating a mailbox on mailboxserver for id user */");
		System.out.println(" /* s: Sending a message to user myfriend whose mailbox is on othermailboxserver */");
		System.out.println(" /* v: Viewing message on mailboxserver for id user */");
		System.out.println(" /* d: Deleting message on mailbox for id user and kind of mailbox */");
		
		System.exit(1);
	}
	
	public static void main (String [] argv) {
		String id = null;
		String server = null;
		String url = null;
		char op;
		MailboxInterface destMailbox ;
		
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
					MailboxInterface expMailBox = 
							(MailboxInterface) Naming.lookup("rmi://" + argv[1].substring(argv[1].indexOf("@") + 1) + "/" + argv[1].substring(0, argv[1].indexOf("@")));
					
					// Si plusieurs destinataires 
					if(argv.length > 4){
						boolean flag = false;
						
						for(int i = 2; i < argv.length-1;i++){
							destMailbox =
								(MailboxInterface) Naming.lookup("rmi://" + argv[i].substring(argv[i].indexOf("@") + 1) + "/" + argv[i].substring(0, argv[i].indexOf("@")));
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
						destMailbox =
							(MailboxInterface) Naming.lookup("rmi://" + argv[2].substring(argv[2].indexOf("@") + 1) + "/" + argv[2].substring(0, argv[2].indexOf("@")));
						System.out.println(expMailBox.send(argv[1], destMailbox, argv[3]));
					}
					break;
				case 'v':
					if(argv.length > 3)
						usage();
					
					id = argv[1].substring(0, argv[1].indexOf("@"));
					server = argv[1].substring(argv[1].indexOf("@") + 1);
					MailboxInterface mailbox =
							(MailboxInterface) Naming.lookup("rmi://" + server + "/" + id);
					
					if(argv[2].equals("all") || argv[2].equals(null)){
						System.out.println(mailbox.checkAll());
					}else if(argv[2].equals("inbox")){
						System.out.println(mailbox.checkInbox());
					}else if(argv[2].equals("sentbox")){
						System.out.println(mailbox.checkSent());
					}else{
						usage();
					}
					
					break;
				case 'd': // d id@mailboxserver [inbox|sentbox] id_message
					
					id = argv[1].substring(0, argv[1].indexOf("@"));
					server = argv[1].substring(argv[1].indexOf("@") + 1);
					
					MailboxInterface mailbox2 = 
							(MailboxInterface) Naming.lookup("rmi://" + server + "/" + id);
					System.out.println(mailbox2.deleteMail(Integer.parseInt(argv[3]),argv[2]));
					break;
				case 't': // t id@mailboxserver myfriend@othermailboxserver [inbox|sentbox] id_message
					if(argv.length != 5)
						usage();
					expMailBox =
					(MailboxInterface) Naming.lookup("rmi://" + argv[1].substring(argv[1].indexOf("@") + 1) + "/" + argv[1].substring(0, argv[1].indexOf("@")));
					
					destMailbox =
							(MailboxInterface) Naming.lookup("rmi://" + argv[2].substring(argv[2].indexOf("@") + 1) + "/" + argv[2].substring(0, argv[2].indexOf("@")));
					System.out.println(expMailBox.transfer(destMailbox, Integer.parseInt(argv[4]), argv[3]));
					break;
				default:
					usage();
			}
			
		} catch (Exception e) {
			System.out.println ("Erreur client : " + e) ;
		}
	}
}
