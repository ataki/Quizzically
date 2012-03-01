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
import com.models.Parcel.Aux;
import com.models.Parcel.User;

/**
 * Sydney
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String COMPOSETO = "composeTo";
    private DBObject db; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        db = new DBObject();//should be left at managers  will fix later
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
			db.setQuery("SELECT id,name FROM Quiz_user WHERE name LIKE \"%" + toUser+"%\"" );
			ResultSet rs = db.getResultSet();
			try {
				ArrayList<Aux> usersList = new ArrayList<Aux>();
				while(rs.next()){
					int id = rs.getInt("id");
					String name = rs.getString("name");
					usersList.add(new Aux(id, name));					
				}
				Gson converter = new Gson();
				String result = converter.toJson(usersList);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			int toUser_id = (Integer)request.getAttribute("Id");
			String message = (String)request.getAttribute("Text");
			String messageType = (String)request.getAttribute("MessageType"); 			
			MessageManager mm = new MessageManager();
			mm.addMessage(fromUser.id,toUser_id,message,messageType);
			RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.HTML");
			dispatch.forward(request, response);
		}
			
	}

}
