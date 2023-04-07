package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BoardDAO;
import entity.PageTO;


public class BoardPageCommand implements BoardCommand {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		// 맨 처음 요청시 보여줄 페이지 값으로 기본 페이지 1을 저장
		int curPage = 1; 
		
		// curPage 파라미터를 지정하지 않으면, 기본값 1로 설정
		// curPage 파라미터가 존재하면, 파라미터값으로 현재 페이지를 설정하여 PageTO에 저장 후 리턴 받는다.
		if(req.getParameter("curPage") != null) {
			curPage = Integer.parseInt(req.getParameter("curPage"));
		} // if
		
		BoardDAO dao = new BoardDAO();
		PageTO list = dao.page(curPage);
		
		// listPage.jsp에서 목록 리스트 데이터 저장
		req.setAttribute("list", list.getList());
		
		// page.jsp에서 페이징 처리 데이터 저장
		req.setAttribute("page", list);
		
	} // execute

} // end class
