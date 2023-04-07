package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.BoardDTO;

public class BoardListCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		BoardDAO dao = new BoardDAO();         // BoardDAO를 접근하기 위해 객체를 생성
		ArrayList<BoardDTO> list = dao.list(); // 목록보기를 구현한 list()메소드를 호출하여 리턴
		
		req.setAttribute("list", list);    // 리턴받은 데이터는 list.jsp에서 보여주기 위해 request scope에 바인딩
		
	} // execute

} // end class
