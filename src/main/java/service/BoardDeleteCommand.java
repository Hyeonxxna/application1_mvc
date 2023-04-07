package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;

public class BoardDeleteCommand implements BoardCommand{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		String num = req.getParameter("num");
		
		BoardDAO dao = new BoardDAO();
		dao.delete(num);
	} // execute
	
} // end class
