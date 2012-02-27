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
 * For more info see DESIGNDOCS (Google Docs).
 * 
 * 
 * @author jimzheng
 *
 */

public class Parcel {

	public static class User {
		public String type;			/* see Type.java for enum */
		public String type_type;	/* see Type_Type.java for enum */
		public String text;			/* body of text related to type */
		public int id;				/* userid/announcementID/... */
		public String aux;			/* any auxiliary data */
		
		public User(String type, String type_type, String text, int id, String aux) {
			this.type = type;
			this.type_type = type_type;
			this.text = text;
			this.id = id;
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
