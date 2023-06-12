package kr.co.jboard2.controller;

import java.io.Console;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.vo.ArticleVO;

@WebServlet("/list.do")
public class ListController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pg = req.getParameter("pg");
		String search = req.getParameter("search");
		
		int currentPage = 1;
		int limitStart = 0;
		int total = 0;
		int lastPageNum = 0;
		int pageStartNum = 0;
		
		List<ArticleVO> articles = null;
		
		if(search == null) {
			articles = service.selectArticles(limitStart);
		}else {
			articles = service.selectArticlesByKeyword(search, limitStart);
		}	
		// 전체 게시물 갯수 구하기
		total = service.selectCountTotal(search);
		
		// 페이지 마지막번호 계산
		if(total % 10 == 0){
			lastPageNum = (total / 10);
		}else{
			lastPageNum = (total / 10) + 1;
		}
		
		if(pg != null){
			currentPage = Integer.parseInt(pg);
		}
		limitStart = (currentPage - 1) * 10;
		
		// 페이지 그룹 계산
		int[] result = service.getPageGroupNum(currentPage, lastPageNum);
		// 페이지 시작 번호
		pageStartNum = total - limitStart;
		
		req.setAttribute("articles", articles);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("lastPageNum", lastPageNum);
		req.setAttribute("pageGroupStart", result[0]);
		req.setAttribute("pageGroupEnd", result[1]);
		req.setAttribute("pageStartNum", pageStartNum + 4);
		req.setAttribute("search", search);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/list.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
