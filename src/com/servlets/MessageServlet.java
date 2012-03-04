package com.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.MessageManager;
import com.google.gson.Gson;
import com.models.Message;
import com.models.Parcel;
import com.models.Type;
import com.models.Type_Type;

/**
 * Sydney
 * Servlet implementation class MessageServlet 
 * It's task is to feed the front end the corresponding message content.
 * @@@not done yet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
		Parcel.User fromUser = (Parcel.User)request.getSession().getAttribute("User");
		String actionType = (String)request.getAttribute("ActionType");
		int id = (Integer)request.getAttribute("id");
		MessageManager mmg = new MessageManager();
		if(actionType.endsWith("Show")){
			
			try {
				Message msg	= mmg.getMessage(id);
				Gson converter = new Gson();
				String result = converter.toJson(msg);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
