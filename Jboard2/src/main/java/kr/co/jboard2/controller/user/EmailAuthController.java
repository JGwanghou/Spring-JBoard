package kr.co.jboard2.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import kr.co.jboard2.service.UserService;

@WebServlet("/user/emailAuth.do")
public class EmailAuthController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private UserService service = UserService.INSTANCE;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		
		int[] result = service.sendEmailCode(email);
		
		// JSON 출력
		JsonObject json = new JsonObject();
		json.addProperty("status", result[0]);
		json.addProperty("code", result[1]);
		
		PrintWriter writer = resp.getWriter();
		writer.print(json.toString());
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
