package com.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.backend.*;
import com.models.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Test for:
 * - Activities
 * - Achievements
 * - Category
 * - Tags
 * - User
 * 
 * @author jimzheng
 *
 */
public class MiscObjectTest {

	List<Integer> user_ids = new ArrayList<Integer>();
	ActivityManager activityManager = new ActivityManager();
	AchievementManager achievementManager = new AchievementManager();
	TagManager tagManager = new TagManager();
	UserManager userManager = new UserManager();
	AnnouncementManager announcementManager = new AnnouncementManager();
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		DBObject db = new DBObject();
		for(int i = 0; i < 100; i++) {
			User user = User.createUser("newUser" + i, "newUser" + i + "@gmail.com", "newUser" + i);
			user_ids.add(user.getId());
		}
	}
	
	@Test
	public void testActivity() {
		Activity act = Activity.TOOK_QUIZ(2, 3, 343, 234.3);
		assertEquals(true, act.upload());
		act.delete();
		
		List<Activity> activities = new ArrayList<Activity>();
		for(int i = 0; i < user_ids.size(); i++) {
			activities.add(Activity.TOOK_QUIZ(user_ids.get(i), 1+i, 34 + i, 234.42 + (double)i));
		}
		
		
	}

	public void testAchievements() {
		
	}
	
	public void testCategory() {
		
	}
	
	public void testTags() {
		
	}
}
