package com.models;

import java.sql.SQLException;

import org.junit.Test;

public class QuizTest {
	@Test
	public void quizUploadTest(){
		Quiz quiz = new Quiz();
	
		try {
			int id = quiz.quizUpload("Totoro", "dustSprites", "My Neighbor", "Science", "bla", false);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
}
