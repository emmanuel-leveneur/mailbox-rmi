public class Mail implements java.io.Serializable{
		
		public String adresse;
		public String date;
		public String message;
		public String etiquette;
		
		// Contructeur par dfaut
		public Mail() {
			// TODO Auto-generated constructor stub
		}

		public Mail(String from, String date, String message, String etiquette){
			this.adresse = from;
			this.date = date;
			this.message = message;
			this.etiquette = etiquette;
		}


		public String getAdresse() {
			return adresse;
		}

		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
}
