<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="./_header.jsp"/>

<main id="board">
    <section class="list">                
        <form action="#">
            <input type="text" name="search" placeholder="제목 키워드, 글쓴이 검색">
            <input type="submit" value="검색">
        </form>
        
        <table border="0">
            <caption>글목록</caption>
            
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>날짜</th>
                <th>조회</th>
            </tr>
            <c:forEach var="article" items="${articles}">
            <tr>
                <td>${article.no}</td>
                <td>
                	<a href="./view.do?no=${article.no}&pg=${currentPage}">${article.title}[${article.comment}]</a>
                </td>
                <td>${article.uid}</td>
                <td>${article.rdate}</td>
                <td>${article.hit}</td>
            </tr>
            </c:forEach>
        </table>

        <div class="page">
	      <a href="#" class="prev">이전</a>
	      <a href="#" class="num current">1</a>
	      <a href="#" class="num">2</a>
	      <a href="#" class="num">3</a>
	      <a href="#" class="next">다음</a>
		</div>

		<a href="./write.do?group=${group}&cate=${cate}" class="btn btnWrite">글쓰기</a>
    </section>
</main>
<jsp:include page="./_footer.jsp"/>        
        