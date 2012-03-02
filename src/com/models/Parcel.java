package com.models;
/** Author: Jim
 * 
 * Parcels Class:
 * 
 * Collection of Statc Inner Class Models that 
 * our Servlets need to understand to communicate with
 * the front-end.
 * Use these in coordination with GSON to easily
 * produce JSON Strings from Objects and vice versa.
 * 
 * Most of the communication will be push, i.e.
 * from server to client, but these are still helpful
 * in case the client translates from server to client.
 *  
 * For more info see DESIGNDOCS (Google Docs).
 * 
 * 
 * @author jimzheng
 *
 */

public class Parcel {

	public static class User {
		public Type type;			/* see Type.java for enum */
		public Type_Type type_type;	/* see Type_Type.java for enum */
		public String text;			/* body of text related to type */
		public int id;				/* userid/announcementID/... */
		public Aux aux;			/* any auxiliary data */
		
		public User(Type type, Type_Type type_type, String text, int id, Aux aux) {
			this.type = type;
			this.type_type = type_type;
			this.text = text;
			this.id = id;
			this.aux = aux;
		}
	}
	
	/**
	 * Ask is a way for the front end to request 
	 * data from the backend by doing an "ask".
	 * Based on the type, the server would
	 * respond with a JSON String representing
	 * an array of objects, each representing
	 * a suggestion
	 * 
	 * @author jimzheng
	 *
	 */
	public static class Ask { 
		public String type;
		public int id;
		public String aux;
		
		public Ask(int id, String type, String aux) {
			this.id = id;
			this.type = type;
			this.aux = aux;
		}
	}
	
	public static class Recommendation {
		public String type;			/* see Type.java for enum */
		public String name;			/* recommended names */
		public int id;				/* userid/announcementID/... */
		public String aux;			/* any more aux data */
		
		public Recommendation(String type, String name, int id, String aux) {
			this.type = type;
			this.name = name;
			this.id =id;
			this.aux =aux;
		}
	}
	
	public static class Admin {
		public String type;			/* see Type.java for enum */		
		public Aux aux;				/* Extensible aux field. */
		
		public Admin(String type, Object aux) {
			this.type = type;
			if(aux != null && aux instanceof Aux) {
				this.aux = (Aux) aux;
			} else this.aux = null;
		}
	}
	
	public static class Aux {
		/* a userID/announcementID/...
		 * any ID in the database. Depends on type
		 */
		public int id;
		
		/* Option means something different
		 * depending on the type and id
		 */
		public String option;
		
		public Aux(int id, String option) {
			this.id = id;
			this.option = option;
		}
	}
	
}
