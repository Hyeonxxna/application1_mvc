<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="ko">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>목록보기</title>
        </head>

        <body>
            <h1>게시판 목록 보기</h1>
            <%-- 검색기능구현 --%>
            <table border="1">
                <tr>
                    <td colspan="5">
                        <form action="search.do">
                            <select name="searchName" size="1">
                                <option value="author">작성자</option>
                                <option value="title">글제목</option>
                            </select>
                            <input type="text" name="searchValue">
                            <input type="submit" value="찾기">
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>번호</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>날짜</td>
                    <td>조회수</td>
                </tr>
                <%-- BoardListCommand에서 바인딩한 key값인 "list"를 이용하여 JSTL 반복 태그를 사용해 출력 --%>
                <c:forEach items="${list}" var="dto">
                    <tr>
                        <td>${dto.num}</td>
                        <td>    
                        	<%-- <c:forEach begin="" end="" step=""> --%>
                            <%-- begin : 반복에 사용될 첫 번째 항목의 index --%>  
                            <%-- end : 반복에 사용될 마지막 항목의 index --%> 
                            <%-- step : 각 반복마다 증가값을 지정함 --%> 
                            <c:forEach begin="1" end="${dto.repIndent}">
                                <%= "&nbsp;&nbsp;" %>
                            </c:forEach>
                            <a href="retrieve.do?num=${dto.num}">${dto.title}</a>
                            </td>
                        <td>${dto.author}</td>
                        <td>${dto.writeday}</td>
                        <td>${dto.readcnt}</td>
                    </tr>
                </c:forEach>

            </table>
            <a href="writeui.do">글쓰기</a>
        </body>

        </html>