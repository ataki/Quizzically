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
	
	// urls that point o images of prizes for a certain level of award
	public static String DEFAULT_URL= "http://a.dryicons.com/images/icon_sets/luna_blue_icons/png/128x128/prize_winner.png";
	public static String LVL1_URL = "";
	public static String LVL2_URL = "";
	public static String LVL3_URL = "";
	public static String LVL4_URL = "";
	
	/* the required awards */
	public static Achievement AMATEUR_AUTHOR() {
		return new Achievement("You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PROLIFIC_AUTHOR() {
		return new Achievement("You've created 5 quizzes!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PRODIGIOUS_AUTHOR() {
		return new Achievement("You've created 10 quizzes!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement QUIZ_MACHINE() {
		return new Achievement("You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement I_AM_THE_GREATEST() {
		return new Achievement("You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PRACTICE_MAKES_PERFECT() {
		return new Achievement("You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	
	public Achievement(String description, String award, String url, Date date) {
		this.setDescription(description);
		this.setAward(award);
		this.setUrl(url);
		this.setDate(date);
	}
	
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
