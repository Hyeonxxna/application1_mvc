package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.BoardDTO;

public class BoardReplyUICommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		// num 파라미터값에 해당되는 BoardDTO을 리턴 받아서, request scope에 "replyui" 키 값으로 바인딩
		String num = req.getParameter("num");
		BoardDAO dao = new BoardDAO();
		
		BoardDTO data = dao.replyui(num);
		req.setAttribute("replyui", data);
		
	} // execute
	
} // end class
