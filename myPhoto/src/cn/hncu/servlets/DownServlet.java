package cn.hncu.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.PhotoModel;
import cn.hncu.photoDao.Dao.PhotoDao;
import cn.hncu.photoDao.factory.PhotoDaoFactory;

public class DownServlet extends HttpServlet {
	private PhotoDao dao = PhotoDaoFactory.getPhotoDao();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String uuid = request.getParameter("uuid");
		System.out.println(uuid);
		PhotoModel photo = dao.getSingleByUuid(uuid);
		System.out.println(photo);
		if(photo!=null){
			//设置响应头--内容接收程序--浏览器看到这个响应头，就会把它认为的下载程序打开
	        //(如果识别迅雷那么就会自动打开迅雷，如果没有则会打开一个“文件另存为”的对话框)
			response.setContentType("application/force-download");
			
			String realName = photo.getRealName();
			
			String agent = request.getHeader("User-Agent");
	        System.out.println(agent);
	        if(agent.indexOf("Mozilla")!=-1){//火狐浏览器
	            response.setHeader("Content-Disposition", "attachment;filename="+ new String(realName.getBytes("GB2312"),"ISO-8859-1"));
	        }else{
	            //解决中文乱码问题（只要用下面一句对文件名进行编码就行了）
	        	realName = URLEncoder.encode(realName, "utf-8");//使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式。
	            response.setHeader("Content-Disposition", "attachment;filename=\""+realName+"\"");
	            //注意：“cttachment;”不能少，否则浏览器会直接在新窗口中打开
	        }
	        
	        String fileName = "photos/" + photo.getDir()+"/"+photo.getUuid()+photo.getExt();
	        String pathFileName = getServletContext().getRealPath(fileName);
	        
	        InputStream in = new FileInputStream(pathFileName);
	        OutputStream out = response.getOutputStream();
	        
	        byte buf[] = new byte[2048];
	        int len=0;
	        while( (len=in.read(buf))!=-1 ){
	        	out.write(buf, 0, len);
	        }
	        out.close();
	        in.close();
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("<h1>该文件已经被删除了</h1>");
		}
	}

}
