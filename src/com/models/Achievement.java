package com.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Achievement {
	private String description;
	private String award;
	private String url;
	private Date date;
	
	public Achievement(String description, String award, String url, String date) {
		this.setDescription(description);
		this.setAward(award);
		this.setUrl(url);
		Date sdf = null;
		try {
			sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setDate(sdf);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getAward() {
		return award;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
