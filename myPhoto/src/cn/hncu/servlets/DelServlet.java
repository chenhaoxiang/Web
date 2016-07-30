package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.PhotoModel;
import cn.hncu.photoDao.Dao.PhotoDao;
import cn.hncu.photoDao.factory.PhotoDaoFactory;

public class DelServlet extends HttpServlet {

	//注入
	private PhotoDao dao = PhotoDaoFactory.getPhotoDao();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String ip = request.getRemoteAddr();
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		PhotoModel photo = dao.getSingleByUuid(uuid);
		
		if(photo!=null){
			if(!photo.getIp().equals(ip)){
				out.print("<h1>你没有权限删除这个文件！！！</h1>");
				out.print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
				return ;
			}
			
			//1.删除数据库中的信息
			boolean boo = dao.deleteXml(uuid);
			
			if(boo){
				String fileName = "photos/"+photo.getDir()+"/"+photo.getUuid()+photo.getExt();
				String pathFileName = getServletContext().getRealPath(fileName);
				if(dao.deleteFile(pathFileName)){
					//重定向到相册页面
					response.sendRedirect("/myPhoto/cloudPhoto?pwd=chx");
				}else{
					out.print("<h1>无法从服务器中删除，文件正在被占用！！！</h1>");
					out.print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
				}
			}else{
				out.print("<h1>该文件已经不存在！！！</h1>");
				out.print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
			}
		}
	}
}
