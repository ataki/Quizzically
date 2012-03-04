package com.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.DBObject;
import com.backend.MessageManager;
import com.backend.UserManager;
import com.google.gson.Gson;
import com.models.Parcel;
import com.models.Type;
import com.models.Type_Type;
import com.models.User;


/**
 * Sydney
 * Servlet implementation class MessageCreateServlet
 * @@@not done yet
 */
@WebServlet("/MessageCreateServlet")
public class MessageCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String COMPOSETO = "composeTo";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		User fromUser = (User)request.getSession().getAttribute("User");
		String actionType = (String)request.getAttribute("ActionType");
		if(actionType.equals(COMPOSETO)){
			String toUser = (String)request.getAttribute("ToUser");
			//go through user table to dynamically feedback to the front end
			UserManager userMg = new UserManager();
			ArrayList<User> toUsers;
			try {
				toUsers = userMg.getUsers(toUser);
				Gson converter = new Gson();
				//convert into Parcel form
					
				String result = converter.toJson(toUsers);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			
		}else{
			int toUser_id = (Integer)request.getAttribute("Id");
			String message = (String)request.getAttribute("Text");
			String messageType = (String)request.getAttribute("MessageType"); 			
			MessageManager mm = new MessageManager();
			try {
				mm.addMessage(fromUser.getUserId(),toUser_id,message,messageType);
				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.HTML");
				dispatch.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("MessageSentError.HTML");
			dispatch.forward(request, response);
		}
			
	}

}
