package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.models.Announcement;

public class AnnouncementManager extends DBObject {
	AnnouncementManager(){
		super();
	}
	public ArrayList<Announcement> getAllAnnouncement() throws SQLException{
		String query = "SELECT * FROM "+DBObject.announcementTable;
		ResultSet rs = getResults(query);			
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		while(rs.next()){
			Announcement an = new Announcement(rs.getInt("id"), rs.getString("text"),rs.getTime("timestamp"));
			announcementList.add(an);
		}
			return announcementList;
		
		


	}
}