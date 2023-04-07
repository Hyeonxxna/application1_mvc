package entity;

import java.util.ArrayList;

import dao.BoardDTO;

public class PageTO {
	
	// 페이징 처리에 필요한 데이터를 저장하기 위한 변수 선언
	ArrayList<BoardDTO> list;	// 목록 리스트 저장
	int curPage;				// 현재페이지 번호
	int perPage = 5;			// 페이지당 보여줄 레코드 갯수
	int totalCount;				// 전체 레코드 갯수
	
	public ArrayList<BoardDTO> getList() {
		return list;
	} // getList
	
	public void setList(ArrayList<BoardDTO> list) {
		this.list = list;
	} // setList
	
	public int getCurPage() {
		return curPage;
	} // getCurPage
	
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	} // setCurPage
	
	public int getPerPage() {
		return perPage;
	} // getperPage
	
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	} // setPerPage
	
	public int getTotalCount() {
		return totalCount;
	} // getTotalCount
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	} // setTatalCount
	
} // end class