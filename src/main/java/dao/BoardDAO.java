package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import entity.PageTO;
import lombok.Cleanup;

public class BoardDAO {

	DataSource dataFactory;
	
	/* JNDI(Java Naming and Directory Interface) : 디렉터리 서비스에서 제공하는 데이터 및 객체를
	                                               발견(Discover)하고 참고(Lookup)하기 위한 자바 API
	  JNDI 용도 : (1) 자바 애플리케이션을 외부 디렉터리 서비스에 연결
	              (2) 자바 애플릿이 호스팅 웹 컨테이너가 제공하는 구성 정보를 참고
	*/
	// JNDI를 사용해서 DataSource 객체 얻기
	  public BoardDAO() {
	      try {
	         Context ctx = new InitialContext();
//	         dataFactory = (DataSource)ctx.lookup("java:comp/env/" + "jdbc/oracle");
	         dataFactory = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
	      } catch (NamingException e) {
	         e.printStackTrace();
	      } // try-catch
	      
	   } // BoardDAO

	// list 메소드내에서 board테이블의 레코드를 DTO클래스와 ArrayList를 사용하여 저장하고 리턴
	// 목록보기
	public ArrayList<BoardDTO> list() {
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			@Cleanup Connection con = null;
			
			String query = "SELECT num, author, title, content, to_char(writeday, 'YYYY/MM/DD') writeday,"
							+ "readcnt, repRoot, repStep, repIndent "
							+ "FROM board "
							+ "order by repRoot desc, repStep asc";
			con = dataFactory.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int readRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");
				
				BoardDTO data = new BoardDTO();
				data.setNum(num);
				data.setAuthor(author);
				data.setTitle(title);
				data.setContent(content);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
				data.setRepRoot(readRoot);
				data.setRepStep(repStep);
				data.setRepIndent(repIndent);
				list.add(data);
			} // while
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try-catch
		} // end finally
		return list;
		
	} // end list
	
	// 글쓰기
	public void write(String _title, String _author, String _content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO board(num, title, author, content, repRoot, repStep, repIndent) "
							+ "values(board_seq.nextval, ?, ?, ?, board_seq.currval, 0, 0)";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, _title); // 첫번째 물음표의 값 지정 **setString(int, String) : 인덱스를 String값으로 지정
			pstmt.setString(2, _author);
			pstmt.setString(3, _content);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // write
	
	// 조회수 1 증가 num값에 해당하는 readcnt 컬럼값을 1 증가
	public void readCount(String _num) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "UPDATE board SET readcnt = readcnt + 1 "
							+ "WHERE num =" + _num;
			pstmt = con.prepareStatement(query);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // readCount
	
	// 글 자세히 보기
	public BoardDTO retrieve(String _num) {  // retrieve 실행하고, 그 결과를 BoardDTO를 반환
		
		// 조회수 증가
		readCount(_num);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO data = new BoardDTO();
		
		try {
			con = dataFactory.getConnection();
			String query = "SELECT * FROM board WHERE num =?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				
				data.setNum(num);
				data.setTitle(title);
				data.setAuthor(author);
				data.setContent(content);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
			} // if
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // finally
		
		return data;
		
	} // retrieve
	
	// 글 수정하기
	public void update(String _num, String _title, String _author, String _content) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "UPDATE board SET title = ?, author = ?, content = ? WHERE num = ?";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, _title);
			pstmt.setString(2, _author);
			pstmt.setString(3, _content);
			pstmt.setInt(4, Integer.parseInt(_num));
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // finally
		
	} // update
	
	// 글 삭제하기
	public void delete(String _num) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM board WHERE num = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num));
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // delete
	
	public ArrayList<BoardDTO> search(String _searchName, String _searchValue) {
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "SELECT num, author, title, content, "
							+ "to_char(writeday, 'YYYY/MM/DD') writeday, readcnt "
							+ "FROM board";
			
			// _searchName 값에 따라서, title과 author 컬럼 중에 사용할 지 결정
			if(_searchName.equals("title")) {
				query += " WHERE title LIKE ?"; // LIKE 연산자를 사용, 문자 하나만 일치해도 모두 검색되도록 구현
			} else {
				query += " WHERE author LIKE ?";
			} // if-else
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+_searchValue+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				
				BoardDTO data = new BoardDTO();
				data.setNum(num);
				data.setAuthor(author);
				data.setTitle(title);
				data.setContent(content);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
				
				list.add(data);
			} // while
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
		return list;
		
	} // search
	
	// 답변글 입력폼 보기
	public BoardDTO replyui(String _num) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO data = new BoardDTO();
		
		try {
			// ** Bind Variable **
			con = dataFactory.getConnection();
			String query = "SELECT * FROM board WHERE num = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(_num)); // String타입의 숫자를 int타입으로 변환
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				data.setNum(rs.getInt("num"));
				data.setTitle(rs.getString("title"));
				data.setAuthor(rs.getString("author"));
				data.setContent(rs.getString("content"));
				data.setWriteday(rs.getString("writeday"));
				data.setReadcnt(rs.getInt("readcnt"));
				data.setRepRoot(rs.getInt("repRoot"));
				data.setRepStep(rs.getInt("repStep"));
				data.setRepIndent(rs.getInt("repIndent"));
			} // if
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
		return data;
	} // replyui
	
	// 답변글의 기존 repStep +1 증가
	public void makeReply(String _root, String _step) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "UPDATE board SET repStep = repStep + 1 "
							+ "WHERE repRoot = ? AND repStep > ?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, Integer.parseInt(_root));
			pstmt.setInt(2, Integer.parseInt(_step));
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // makeReply
	
	// 답변달기
	public void reply(String _num, String _title, String _author, String _content, 
						String _repRoot, String _repStep, String _repIndent) {
	
		// repStep +1 증가
		makeReply(_repRoot, _repStep);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO board(num, title, author, content, repRoot, repStep, repIndent) "
							+ "values(board_seq.nextVal, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, _title);
			pstmt.setString(2, _author);
			pstmt.setString(3, _content);
			pstmt.setInt(4, Integer.parseInt(_repRoot));
			pstmt.setInt(5, Integer.parseInt(_repStep) +1);
			pstmt.setInt(6, Integer.parseInt(_repIndent) +1);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
	} // reply
	
	// 페이징 처리: 전체 레코드 개수 구하기
	public int totalCount() {
		
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "SELECT count(*) FROM board";
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			} // if
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} 
		} // finally
		
		return count;
		
	} // totalCount
	
	// 페이지 구현
	public PageTO page(int curPage) {
		
		PageTO to = new PageTO();
		int totalCount = totalCount();
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			String query = "SELECT num, author, title, content, "
							+ "to_char(writeday, 'YYYY/MM/DD') writeday, readcnt, repRoot, repStep, repIndent "
							+ "FROM board "
							+ "order by repRoot desc, repStep asc";
			// Result.set에서 rs.next()를 사용하게 되면, 
			// 다음 결과의 row를 가져오고, 이전값을 사용 못하게 된다.
			// ResultSet.TYPE_SCROLL_INSENSITIVE : 위치 이동을 자유롭게 하고, 업데이트 내용 미반영
			// ResultSet.CONCUR_READ_ONLY : 데이터 변경 불가능
			pstmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = pstmt.executeQuery();
			
			int perPage = to.getPerPage();
			
			int skip = (curPage -1) * perPage;
			if(skip > 0) {
				rs.absolute(skip);
			} // if
			for(int i=0; i<perPage && rs.next(); i++) {
				int num = rs.getInt("num");
				String author = rs.getString("author");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writeday = rs.getString("writeday");
				int readcnt = rs.getInt("readcnt");
				int repRoot = rs.getInt("repRoot");
				int repStep = rs.getInt("repStep");
				int repIndent = rs.getInt("repIndent");
				
				BoardDTO data = new BoardDTO();
				data.setNum(num);
				data.setAuthor(author);
				data.setTitle(title);
				data.setContent(content);
				data.setWriteday(writeday);
				data.setReadcnt(readcnt);
				data.setRepRoot(repRoot);
				data.setRepStep(repStep);
				data.setRepIndent(repIndent);
				list.add(data);
			} // for
			to.setList(list); // ArrayList 저장
			to.setTotalCount(totalCount); // 전체레코드개수
			to.setCurPage(curPage); // 현재페이지
		} catch(Exception e) { 
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} 
		} // finally
		return to;
		
	} // page
	
} // end class	