package com.models;

/* Author: Jim
 * May be used in the future; 
 * for now, just a reference
 * for checking types in Parcel
 */

public enum Type {
	user {
		public String toString() {
			return "user";
		}
	},
	quiz {
		public String toString() {
			return "quiz";
		}
	},
	friend {
		public String toString() {
			return "friend";
		}
	},
	announcement {
		public String toString() {
			return "announcement";
		}
	},
	activity {
		public String toString() {
			return "activity";
		}
	},
	message {
		public String toString() {
			return "message";
		}
	},
}
