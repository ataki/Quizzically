package com.backend;
import java.util.HashMap;
import java.util.Map;

import com.models.*;
/** 
 * @author jimzheng
 * A Competition is a live, timed synchronous event
 * between several users on the site at a given time.
 * 
 * This class manages the logistics of that. At a certain
 * point, when a competition begins, it is kept track of
 * and modified by the Servlet context. Threads that modify
 * it must call atomic operations, hence all functions
 * that write to a competitionManager object must
 * be atomic.
 * 
 * This class keeps track of pointers to users and their
 * corresponding quizzes; the quiz object is shared by 
 * both the servlet context and this class, and is
 * sychronously read and written to.
 *  
 * @author jimzheng
 *
 */
public class CompetitionManager {

	Map<User, Quiz> tracker;
	
	public CompetitionManager() {
		tracker = new HashMap<User, Quiz>();
	}
	
	public synchronized void addParticipant(User user, Quiz quiz) {
		tracker.put(user, quiz);
	}
	
	public synchronized void removeParticipant(User user) {
		tracker.remove(user);
	}
	
	/**
	 * A statistic is calculated at some point and simply
	 * reports a high level summary of the whole Competition,
	 * including if any users have completed, what the highest
	 * current score is, etc...
	 * @author jimzheng
	 *
	 */
	public class Statistic {
		int numParticipants;
		int numQuestions;
		
		
		public Statistic() {
			
		}
	}
	
	public synchronized Statistic calculateStatistics() {
		return null;
	}

	/**
	 * Progress class keeps track of a user's
	 * progress through a Quiz
	 * @author jimzheng
	 */
	public class Progress {
		Quiz quiz;
		int curQuestion;
		int score;
		double timeTaken;
		
		public Progress() {
			
		}
	}
	
	
	
}
