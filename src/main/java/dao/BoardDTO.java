package dao;

public class BoardDTO {
	
// 1. 변수 선언
	int num;
	String author;
	String title;
	String content;
	int readcnt;
	String writeday;
	int repRoot;
	int repStep;
	int repIndent;
	
// 2. 생성자 선언	
	public BoardDTO() {}
	public BoardDTO(int num, String author, String title, String content, int readcnt, String writeday,
			        int repRoot, int repStep, int repIndent) {
		this.num = num;
		this.author = author;
		this.title = title;
		this.content = content;
		this.readcnt = readcnt;
		this.writeday = writeday;
		this.repRoot = repRoot;
		this.repStep = repStep;
		this.repIndent = repIndent;
		
	} // BoardDTO
	
// 3. setter Method & getter Method 정의
	public int getNum() {
		return num;
	} // getNum
	
	public void setNum(int num) {
		this.num = num;
	} // setNum
	
	public String getAuthor() {
		return author;
	} // getAuthor
	
	public void setAuthor(String author) {
		this.author = author;
	} // setAuthor
	
	public String getTitle() {
		return title;
	} // getTitle
	
	public void setTitle(String title) {
		this.title = title;
	} // setTitle
	
	public String getContent() {
		return content;
	} // getContent
	
	public void setContent(String content) {
		this.content = content;
	} // setContent
	
	public int getReadcnt() {
		return readcnt;
	} // getReadcnt
	
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	} // setReadcnt
	
	public String getWriteday() {
		return writeday;
	} // getWriteday
	
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	} // setWriteday
	
	public int getRepRoot() {
		return repRoot;
	} // getRepRoot
	
	public void setRepRoot(int repRoot) {
		this.repRoot = repRoot;
	} // setRepRoot
	
	public int getRepStep() {
		return repStep;
	} // getRepStep
	
	public void setRepStep(int repStep) {
		this.repStep = repStep;
	} // setRepStep
	
	public int getRepIndent() {
		return repIndent;
	} // getRepIndent
	
	public void setRepIndent(int repIndent) {
		this.repIndent = repIndent;
	} // setRepIndent
	
} // end class
