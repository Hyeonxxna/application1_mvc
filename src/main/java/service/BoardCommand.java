package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Command Pattern
// : 실행될 기능을 캡슐화함으로써, 주어진 여러 기능을 실행할 수 있는 재사용성이 높은 클래스를 설계하는 패턴
// : 이벤트가 발생했을 때 실행될 기능의 변경이 필요한 경우, 이벤트를 발생시키는 클래스를 변경하지 않고 재사용 할 때 유용 
// - 실행될 기능에 대한 인터페이스
// - 실행될 기능을 execute 메서드로 선언함
public interface BoardCommand {
		
	public void execute(HttpServletRequest request, HttpServletResponse respinse);
	
} // end interface
