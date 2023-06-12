package kr.co.jboard2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jboard2.service.ArticleService;

@WebServlet("/delete.do")
public class DeleteController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no		 = req.getParameter("no");
		String parent	 = req.getParameter("parent");
		String pg		 = req.getParameter("pg");
		service.deleteArticle(no, parent);
		
		resp.sendRedirect("/Jboard2/list.do?pg="+pg+"");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
}
