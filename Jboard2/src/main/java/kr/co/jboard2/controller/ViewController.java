package kr.co.jboard2.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import kr.co.jboard2.service.ArticleService;
import kr.co.jboard2.vo.ArticleVO;

@WebServlet("/view.do")
public class ViewController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ArticleService service = ArticleService.INSTANCE;
	
	@Override
	public void init() throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no = req.getParameter("no");
		String pg = req.getParameter("pg");
		
		ArticleVO article = service.selectArticle(no);
		req.setAttribute("article", article);
		
		req.setAttribute("pg", pg);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/view.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no	 = req.getParameter("no");
		String uid	 = req.getParameter("uid");
		String content = req.getParameter("content");
		String regip = req.getRemoteAddr();
		
		ArticleVO comment = new ArticleVO();
		comment.setParent(no);
		comment.setContent(content);
		comment.setUid(uid);
		comment.setRegip(regip);
		
		ArticleVO comment1 = service.insertComment(comment);
		req.setAttribute("comment", comment1);
		
		JsonObject json = new JsonObject();
		json.addProperty("result", 1);
		json.addProperty("no", comment.getNo());
		json.addProperty("parent", comment.getParent());
		json.addProperty("nick", comment.getNick());
		json.addProperty("date", comment.getRdate());
		json.addProperty("content", comment.getContent());
		
		String jsonData = json.toString();
	}
}
