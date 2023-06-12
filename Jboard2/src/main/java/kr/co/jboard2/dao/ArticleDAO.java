package kr.co.jboard2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.jboard2.db.DBHelper;
import kr.co.jboard2.db.Sql;
import kr.co.jboard2.vo.ArticleVO;

public class ArticleDAO extends DBHelper{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int insertArticle(ArticleVO vo) {
		int parent = 0;
		try {
			logger.debug("insertArticle...");
			conn = getConnection();
			conn.setAutoCommit(false);
			
			psmt = conn.prepareStatement(Sql.INSERT_ARTICLE);
			Statement stmt = conn.createStatement();
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getContent());
			psmt.setInt(3, vo.getFname() == null ? 0 : 1);
			psmt.setString(4, vo.getUid());
			psmt.setString(5, vo.getRegip());
			psmt.executeUpdate();
			
			rs = stmt.executeQuery(Sql.SELECT_MAX_NO);
			conn.commit();
			
			if(rs.next()){
				parent = rs.getInt(1);
			}
			close();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return parent;
	}
	
	public void insertFile(int parent, String newName, String fname) {
		try{
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.INSERT_FILE);
			psmt.setInt(1, parent);
			psmt.setString(2, newName);
			psmt.setString(3, fname);
			
			psmt.executeUpdate();
			
			psmt.close();
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public ArticleVO insertComment(ArticleVO comment) {
		ArticleVO article = null;
		int result = 0;
		
		try{
			conn = getConnection();
			
			conn.setAutoCommit(false);
			PreparedStatement psmt1 = conn.prepareStatement(Sql.INSERT_COMMENT);
			PreparedStatement psmt2 = conn.prepareStatement(Sql.UPDATE_ARTICLE_COMMENT_PLUS);
			Statement stmt = conn.createStatement();
			psmt1.setInt(1, comment.getParent());
			psmt1.setString(2, comment.getContent());
			psmt1.setString(3, comment.getUid());
			psmt1.setString(4, comment.getRegip());
			
			psmt2.setInt(1, comment.getParent());
			
			result = psmt1.executeUpdate();
			psmt2.executeUpdate();
			ResultSet rs = stmt.executeQuery(Sql.SELECT_COMMENT_LATEST);
			
			
			conn.commit();
			
			if(rs.next()) {
				article = new ArticleVO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setContent(rs.getString(6));
				article.setRdate(rs.getString(11).substring(2, 10));
				article.setNick(rs.getString(12));
			}
			
			close();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return article;
	}
	
	public ArticleVO selectArticle(String no) {
		ArticleVO article = null;
				
		try{
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLE);
			psmt.setString(1, no);
			rs = psmt.executeQuery();
			if(rs.next()){
				article = new ArticleVO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setUid(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11));
				article.setFno(rs.getInt(12));
				article.setPno(rs.getInt(13));
				article.setNewName(rs.getString(14));
				article.setOriName(rs.getString(15));
				article.setDownload(rs.getInt(16));
			}
			close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return article;
	}
	
	public int selectCountTotal(String search) {
		int total = 0;
		try {
			logger.debug("selectCountTotal..");
			conn = getConnection();
			
			if(search == null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(Sql.SELECT_COUNT_TOTAL);
			}else {
				psmt = conn.prepareStatement(Sql.SELECT_COUNT_TOTAL_FOR_SEARCH);
				psmt.setString(1, "%"+search+"%");
				psmt.setString(2, "%"+search+"%");
				rs = psmt.executeQuery();
			}
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
			close();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}
	
	public List<ArticleVO> selectArticles(int limitStart) {
		List<ArticleVO> articles = new ArrayList<>();
		
		try {
			logger.debug("selectArticles...");
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLES);
			psmt.setInt(1, limitStart);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ArticleVO article = new ArticleVO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setUid(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11).substring(2, 10));
				article.setNick(rs.getString(12));
				
				articles.add(article);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return articles;
	}
	
	public List<ArticleVO> selectArticlesByKeyword(String keyword, int limitStart) {
		List<ArticleVO> articles = new ArrayList<>();
		try {
			logger.debug("selectArticleBykeyword...");
			
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.SELECT_ARTICLES_BY_KEYWORD);
			psmt.setString(1, "%"+keyword+"%");
			psmt.setString(2, "%"+keyword+"%");
			psmt.setInt(3, limitStart);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ArticleVO article = new ArticleVO();
				article.setNo(rs.getInt(1));
				article.setParent(rs.getInt(2));
				article.setComment(rs.getInt(3));
				article.setCate(rs.getString(4));
				article.setTitle(rs.getString(5));
				article.setContent(rs.getString(6));
				article.setFile(rs.getInt(7));
				article.setHit(rs.getInt(8));
				article.setUid(rs.getString(9));
				article.setRegip(rs.getString(10));
				article.setRdate(rs.getString(11).substring(2, 10));
				article.setNick(rs.getString(12));
				
				articles.add(article);
			}
			
			close();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return articles;
	}
	
	public void updateArticle(String title, String content, String no) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setString(3, no);
			psmt.executeUpdate();
			
			psmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public void updateHit(String no) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.UPDATE_ARTICLE_HIT);
			psmt.setString(1, no);
			psmt.executeUpdate();
			
			psmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public void deleteArticle(String no, String parent) {
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(Sql.DELETE_ARTICLE);
			psmt.setString(1, no);
			psmt.setString(2, parent);
			
			psmt.executeUpdate();
			
			close();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
