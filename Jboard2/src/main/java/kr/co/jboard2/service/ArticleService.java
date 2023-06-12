package kr.co.jboard2.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.jboard2.dao.ArticleDAO;
import kr.co.jboard2.vo.ArticleVO;

public enum ArticleService {

	INSTANCE;
	private ArticleDAO dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ArticleService() {
		dao = new ArticleDAO();
	}
	
	public int insertArticle(ArticleVO article) {
		 return dao.insertArticle(article);
	}
	
	public ArticleVO insertComment(ArticleVO comment) {
		return dao.insertComment(comment);
	}
	
	public void insertFile(int parent, String newName, String fname) {
		dao.insertFile(parent, newName, fname);
	}
	
	public ArticleVO selectArticle(String no) {
		return dao.selectArticle(no);
	}
	
	public int selectCountTotal(String search) {
		return dao.selectCountTotal(search);
	}
	
	public List<ArticleVO> selectArticles(int limitStart) {
		return dao.selectArticles(limitStart);
	}
	
	public List<ArticleVO> selectArticlesByKeyword(String keyword, int limitStart) {
		return dao.selectArticlesByKeyword(keyword, limitStart);
	}
	
	public void updateArticle(String title, String content, String no) {
		dao.updateArticle(title, content, no);
	}
	
	public void updateHit(String no) {
		dao.updateHit(no);
	}
	
	public void deleteArticle(String no, String parent) {
		dao.deleteArticle(no, parent);
	}
	
	// 추가적인 서비스 로직
	public MultipartRequest uploadFile(HttpServletRequest req, String path) throws IOException {
		
		int maxSize = 1024 * 1024 * 10;
		return new MultipartRequest(req, path, maxSize, "UTF-8", new DefaultFileRenamePolicy());
	}
	
	public String renameFile(String fname, String uid, String path) {
		// 파일명 수정
		int i = fname.lastIndexOf(".");
		String ext = fname.substring(i);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_");
		String now = sdf.format(new Date());
		String newName = now+uid+ext; // 20221026160517_letary.txt
		
		File f1 = new File(path+"/"+fname);
		File f2 = new File(path+"/"+newName);
		
		f1.renameTo(f2);
		
		return newName;
	}
	
	public int[] getPageGroupNum(int currentPage, int lastPageNum) {
		int pageGroupCurrent = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (pageGroupCurrent - 1) * 10 + 1;
		int pageGroupEnd = pageGroupCurrent * 10;
		
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		int[] result = {pageGroupStart, pageGroupEnd, pageGroupCurrent};
		return result;
	}
}
