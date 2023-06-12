<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="./_header.jsp"/>
<script>
	$(function(){
		$('.commentForm > form').submit(function(){
			
			let no		 = $(this).children('input[name=no]').val();
			let uid		 = $(this).children('input[name=uid]').val();
			let textarea = $(this).children('textarea[name=content]')
			let content  = textarea.val();
			
			if(content == ''){
				alert('댓글을 작성하세요.');
				return false;
			}
			
			let jsonData = {
					"no":no,
					"uid": uid,
					"content":content
			};
			
			$.ajax({
				url:'/Jboard2/view.do',
				method:'POST',
				data: jsonData,
				dataType:'json',
				success: function(data){
					console.log(data);
					
					if(data.result > 0 ){
						
						let article = "<article>";
							article += "<span class='nick'>"+data.nick+"</span>";
							article += "<span class='date'>"+data.date+"</span>";
							article += "<p class='content'>"+data.content+"</p>";
							article += "<div>";
							article += "<a href='#' class='remove' data-no='"+no+"' data-parent='"+data.parent+"'>삭제</a>";
							article += "<a href='#' class='modify' data-no='"+no+"'>수정</a>";
							article += "</div>";							
							article += "</article>";
						
							$('.commentList > .empty').hide();
							
							$('.commentList').append(article);
							textarea.val('');
					}
				}
			});
			return false;
		});
	})
</script>
        <main id="board">
            <section class="view">
                
                <table border="0">
                    <caption>글보기</caption>
                    <tr>
                        <th>제목</th>
                        <td><input type="text" name="title" readonly value="${article.title}"/></td>
                    </tr>
                    
                    <c:if test="${article.file > 0}">
                    <tr>
                        <th>파일</th>
                        <td><a href="#">${article.oriName}</a>&nbsp;<span>${article.download}</span>회 다운로드</td>
                    </tr>
                    </c:if>
                    <tr>
                        <th>내용</th>
                        <td>
                            <textarea name="content" readonly>${article.content}</textarea>
                        </td>
                    </tr>                    
                </table>
                
                <div>
                    <a href="/Jboard2/delete.do?no=${article.no}&pg=${pg}" class="btn btnRemove">삭제</a>
                    <a href="/Jboard2/modify.do?no=${article.no}&pg=${pg}" class="btn btnModify">수정</a>
                    <a href="/Jboard2/list.do?no=${article.no}&pg=${pg}" class="btn btnList">목록</a>
                </div>
	
                <!-- 댓글목록 -->
                <section class="commentList">
                    <h3>댓글목록</h3>                   

                    <article>
                        <span class="nick">길동이</span>
                        <span class="date">20-05-20</span>
                        <p class="content">댓글 샘플 입니다.</p>                        
                        <div>
                            <a href="#" class="remove">삭제</a>
                            <a href="#" class="modify">수정</a>
                        </div>
                    </article>

                    <p class="empty">등록된 댓글이 없습니다.</p>

                </section>

                <!-- 댓글쓰기 -->
                <section class="commentForm">
                    <h3>댓글쓰기</h3>
                    <form action="#">
                    	<input type="hidden" name="uid" value="${article.uid}">
            			<input type="hidden" name="no" value="${article.no}">	
                        <textarea name="content" placeholder="댓글을 입력하세요."></textarea>
                        <div>
                            <a href="#" class="btn btnCancel">취소</a>
                            <input type="submit" value="작성완료" class="btn btnComplete"/>
                        </div>
                    </form>
                </section>
            </section>
        </main>
<jsp:include page="./_footer.jsp"/>          