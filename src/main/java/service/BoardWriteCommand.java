package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;

public class BoardWriteCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		
		BoardDAO dao = new BoardDAO();
		dao.write(title, author, content);
		
	} // execute
	
	
} // end class
