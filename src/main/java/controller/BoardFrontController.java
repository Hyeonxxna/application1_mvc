package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardCommand;
import service.BoardDeleteCommand;
import service.BoardListCommand;
import service.BoardPageCommand;
import service.BoardReplyCommand;
import service.BoardReplyUICommand;
import service.BoardRetrieveCommand;
import service.BoardSearchCommand;
import service.BoardUpdateCommand;
import service.BoardWriteCommand;

@WebServlet("*.do")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
						throws ServletException, IOException {
		doPost(req, res);
	} // doGet

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
						throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// 클라이언트에서 요청한 값을 얻기 위한 작업 구현
		String reqURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String com = reqURI.substring(contextPath.length());
		
		BoardCommand command = null;
		String nextPage = null;
		
		// <목록보기> 요청을 구현한 BoardListCommand 객체를 생성하고 execute 메소드를 호출
		if(com.equals("/list.do")) {
			command = new BoardListCommand();
			command.execute(req, res);
			nextPage = "list.jsp";  // 웹 브라우저에서 보여줄 페이지인 list.jsp를 지정
		} // if
		
		// <글쓰기 폼> writeui.do 요청시에는 글쓰기 폼인 write.jsp로 포워드 시킴
		if(com.equals("/writeui.do")) {
			nextPage = "write.jsp";
		} // if
		
		// write.do 요청인 경우 BoardWriteCommand로 넘겨 글쓰기 작업을 처리
		// 현재 작성한 글을 포함하여 보여주기 위해서 "/list.do" 형식으로 요청하기 때문에, 
		// nextPage변수에 "list.do 값을 설정한다."
		if(com.equals("/write.do")) {
			command = new BoardWriteCommand();
			command.execute(req, res);
			nextPage = "list.do";
		} // if
		
		// 글 자세히보기
		// retrieve.do로 요청하면, BoardRetrieveCommand로 요청하고 retrieve.jsp로 포워드 
		if(com.equals("/retrieve.do")) {
			command = new BoardRetrieveCommand();
			command.execute(req, res);
			nextPage = "retrieve.jsp";
		} // if
		
		// 글 수정하기
		if(com.equals("/update.do")) {
			command = new BoardUpdateCommand();
			command.execute(req, res);
			nextPage = "list.do";
		} // if
		
		// 글 삭제하기
		if(com.equals("/delete.do")) {
			command = new BoardDeleteCommand();
			command.execute(req, res);
			nextPage = "list.do";
		} // if
		
		// 글 검색하기
		if(com.equals("/search.do")) {
			command = new BoardSearchCommand();
			command.execute(req, res);
			nextPage = "list.jsp"; // 삭제 => 게시판 목록 보기로 나가야함
//			nextPage = "list.do";
		} // if
		
		// 답변글 입력폼 보기
		if(com.equals("/replyui.do")) {
			command = new BoardReplyUICommand();
			command.execute(req, res);
			nextPage = "reply.jsp";
		} // if
		
		// 답변 글쓰기
		if(com.equals("/reply.do")) {
			command = new BoardReplyCommand();
			command.execute(req, res);
			nextPage = "list.do";
		} // if
		
		// 페이징처리
		if(com.equals("/list.do")) {
			command = new BoardPageCommand();
			command.execute(req, res);
			nextPage = "listPage.jsp";
		} // if
		
		// 요청 포워드로 JSP 호출
		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, res);
	} // doPost
		
} // end class
