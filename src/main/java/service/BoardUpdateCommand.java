package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;

public class BoardUpdateCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		String num = req.getParameter("num");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		
		// BoardDAO 클래스의 update 메소드로 넘김
		BoardDAO dao = new BoardDAO();
		dao.update(num, title, author, content);
		
	} // execute
	
} // end class
