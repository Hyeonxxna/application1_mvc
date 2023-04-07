package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;

public class BoardReplyCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		// reply.jsp에서 넘어온 파라미터값을 얻는다.
		String num = req.getParameter("num");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		String repRoot = req.getParameter("repRoot");
		String repStep = req.getParameter("repStep");
		String repIndent = req.getParameter("repIndent");
		
		// BoardDAO클래스의 reply메소드를 사용하여 전달한다.
		BoardDAO dao = new BoardDAO();
		dao.reply(num, title, author, content, repRoot, repStep, repIndent);
	} // execute

} // end class
