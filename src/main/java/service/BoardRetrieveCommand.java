package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.BoardDTO;

public class BoardRetrieveCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		// num값에 해당하는 파라미터값을 얻어서, BoardDTO에 저장하여 리턴 받음
		String num = req.getParameter("num");
		BoardDAO dao = new BoardDAO();
		BoardDTO data = dao.retrieve(num);
		
		// request scope에 BoardDTO 데이터를 "retrieve"의 키 값으로 바인딩
		req.setAttribute("retrieve", data);
		
	} // execute
	
} // end class
