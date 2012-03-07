package com.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.backend.DBObject;


public class Question extends DBObject{
	
	
	public enum Type{
		QuestionResponse {
			public String toString() {
				return "question-response";
			}
		},
		FillInTheBlank {
			public String toString() {
				return "fill-in-the-blank";
			}
		},
		MultiAnswer {
			public String toString(){
				return "multi-answer";
			}
		},
		PictureResponse{
			public String toString(){
				return "picture-response";
			}
		},
		MultiChoice{
			public String toString(){
				return "multi-choice";
			}
		},
		MultiChoiceMultiAnswer{
			public String toString(){
				return "multi-choice-multi-answer";
			}
		},
		Matching{
			public String toString(){
				return "matching";
			}
		},
		AutoGenerated {
			public String toString(){
				return "auto-generated";
			}
		},
		Graded {
			public String toString(){
				return "graded";
			}
		}
		

	}
	private int id;
	private List<String> texts;
	private List<String> answers;
	private String url;
	private Type type;
	private int quizId;
	private boolean done = false;
	
	private static String DELIMITER = "#";
	private static String insertString = "INSERT INTO " + DBObject.questionTable + " VALUE (null, ?, ?, ?, ?, ?)";
	private static String updateString = "UPDATE " + DBObject.questionTable + " SET questionType = ?, question = ?, answers = ?, quiz_id = ?, url = ?";
	private static String deleteString = "DELETE FROM " + DBObject.questionTable;
	
	// Constructors
	public Question(int id)
	{
		StringBuilder query = new StringBuilder();
		try {
		query.append("SELECT * FROM " + DBObject.questionTable + " ");
		query.append("WHERE id = "+ id);
		ResultSet rs = getResults(query.toString());
		if (rs.next())
			this.id = id;
			this.texts = convertStringToTexts(rs.getString("question"));
			this.answers = convertStringToTexts(rs.getString("answers"));
			this.url = rs.getString("url");
			this.quizId = rs.getInt("quiz_id");
			
			String type = rs.getString("questionType");
			for (Type item: Type.values()){
				if (type.equals(item.toString()))
					this.type = item;
			}
			
			this.done = true;
		} catch (SQLException e) {
			// Ignore
		}
	}
	
	public Question(int id, List<String>texts, List<String>answers,String url,String type, int quizId) 
	{
		this.id = id;
		this.texts = texts;
		this.answers = answers;
		this.url = url;
		this.quizId = quizId;

		for(Type item: Type.values()){
			if(type.equals(item.toString()))
				this.type = item;
		}
	}
	
	public Question(int id, String texts, String answers, String url, String type, int quizId) 
	{
		this.id = id;
		this.texts = convertStringToTexts(texts);
		this.answers = convertStringToTexts(answers);
		this.url = url;
		this.quizId = quizId;

		for(Type item: Type.values()){
			if(type.equals(item.toString()))
				this.type = item;
		}
	}
	
	// Utility functions
	public static String convertTextsToString(List<String> Texts){
		StringBuilder strBuilder = new StringBuilder();
		for(String item:Texts){
			strBuilder.append(item + DELIMITER);	
		}
		return strBuilder.toString();
	}
	
	public static List<String> convertStringToTexts(String str){
		StringTokenizer tokens = new StringTokenizer(str,DELIMITER);
		List<String> list = new ArrayList<String>();
		while(tokens.hasMoreTokens()){
			list.add(tokens.nextToken());
		}
			
		return list;
	}
	
	/**
	 * Create a question and insert it to the database.
	 */
	public static Question insert(String type, List<String> texts, List<String> answers, int quizId, String url) {
		Connection con = getConnection();
		try {
			System.out.println(con);
			prepStatement = con.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
			prepStatement.setString(1, type);
			prepStatement.setString(2, convertTextsToString(texts));
			prepStatement.setString(3, convertTextsToString(answers));
			prepStatement.setInt(4, quizId);
			prepStatement.setString(5, url);
			prepStatement.executeUpdate();		 	
			ResultSet rs = prepStatement.getGeneratedKeys();
			if (rs.next())
				return new Question(rs.getInt(1));
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/** fetches quiz information from database based on given id
	 * @param id
	 * @return
	 */
	public static Question fetch(int id) {
		Question q = new Question(id);
		if (q.done) return q;
		return null;
	}
	
	/** Updates the row that represents the object from the database.
	 * @return A boolean to indicates if the request is successful.
	 */
	public boolean sync() {
		if (!conPrepare(updateString + id_filter)) return false;
		try {
			prepStatement.setString(1, type.toString());
			prepStatement.setString(2, convertTextsToString(texts));
			prepStatement.setString(3, convertTextsToString(answers));
			prepStatement.setInt(4, quizId);
			prepStatement.setString(5, url);
			prepStatement.setInt(6, id);
			return (prepStatement.executeUpdate() != 0);
		} catch (SQLException e) {
			return false;
		}
	};
	
	/** Deletes the row that represents the object from the database.
	 * @return A boolean to indicates if the request is successful.
	 */
	public boolean delete() {
		if (!conPrepare(deleteString + id_filter)) return false;
		try {
			prepStatement.setInt(1, id);
			return (prepStatement.executeUpdate() != 0);
		} catch (SQLException e) {
			return false;
		}
	};
	
	
	/*
	 * depending on the question type, Answer's
	 * index can mean either:
	 * - the index of hte question with which to associate
	 * - 
	 */
	/*
	public class Answer {
		String url;
		String text;
		int index;
		public Answer(String url, String text, int index) {
			this.index =index;
			this.url = url;
			this.text = text;
		}
		
		@Override
		public boolean equals(Object other) {
		    // Not strictly necessary, but often a good optimization
		    if (this == other)
		      return true;
		    if (!(other instanceof Answer))
		      return false;
		    Answer otherA = (Answer) other;
		    return 
		      (url.equals(otherA.url) &&
		    	text.equals(otherA.text) &&
		    	index == otherA.index);
		}
		@Override
		public int hashCode() { 
		   int hash = 1;
		   hash = hash * 31 + text.hashCode();
		   return hash;
		}
	}
	*/
	
	/**
	 * check our answer with given input; does so 
	 * by sorting answers by index and then checking
	 * for equality among all answers.
	 * Raises question of how we are going to represent
	 * answers on the front-end
	 * 
	 * @param answers
	 * @return
	 */
	/*
	public boolean checkAnswer(List<Answer>answers) {
		// sort the answer by a certain heuristic 
		Collections.sort(answers, new Comparator<Answer>() {
			@Override
			public int compare(Answer arg0, Answer arg1) {
				if(arg0.index == arg1.index) return 0;
				if(arg0.index < arg1.index) return -1;
				return 1;
			}
		});
		if(answers.size() != Ans.size()) return false;
		for(int i =0; i < answers.size(); i++) {
			if(!answers.get(i).equals(Ans.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	*/

	/**
	 * @return the texts
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the texts
	 */
	public List<String> getTexts() {
		return texts;
	}
	/**
	 * @param texts the texts to set
	 */
	public void setTexts(List<String> texts) {
		this.texts = texts;
	}
	/**
	 * @return the answers
	 */
	public  List<String> getAnswers() {
		return answers;
	}
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
			this.type = type;
	}
}
