package cn.hncu.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.hncu.domain.PhotoModel;
import cn.hncu.photoDao.Dao.PhotoDao;
import cn.hncu.photoDao.factory.PhotoDaoFactory;
import cn.hncu.utils.Dom4jFactory;
import cn.hncu.utils.MyUtils;

public class UploadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print("<h1>很抱歉，本页面不支持GET方式访问！！！</h1>");
		response.getWriter().print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		final PrintWriter out = response.getWriter();
		
		//防黑-非multipart表单提交
		//使用上传工具的方式
		boolean boo = ServletFileUpload.isMultipartContent(request);
		if(!boo){
			out.print("<h1>不支持普通表单的提交方式！</h1>");
			return;
		}
		
		File file = new File("d:/photoCache");
		if(!file.exists()){
			file.mkdir();
		}
		
		DiskFileItemFactory fiFactory = new DiskFileItemFactory(1024*10,file);
		
		ServletFileUpload upload = new ServletFileUpload(fiFactory);
		upload.setHeaderEncoding("utf-8");//设置文件名编码
		String path = getServletContext().getRealPath("/photos");
		
		
		FileItem fi = null;
		try {
			List<FileItem> list = upload.parseRequest(request);
			PhotoModel photo = new PhotoModel();//数据封装---需要7个属性
			photo.setRealName("");
			int cont=0;
			for(FileItem f:list){
				if(cont!=0 && cont%2==0 && !photo.getRealName().equals("")){
					photo = new PhotoModel();//重新数据封装
				}
				fi=f;
				if(fi.isFormField()){//普通表单组件
					//得到文件说明
					String desc = fi.getString("utf-8");
					photo.setDesc(desc);//#1
				}else{
					
					//防黑3-如果在file组件中不选择文件
					if(fi.getSize()==0){
						photo.setRealName("");
						cont++;
						continue;
					}
					String fileName = fi.getName();
					fileName = fileName.substring( fileName.lastIndexOf("\\")+1);//真实文件名
					photo.setRealName(fileName);//#2
					String ext = fileName.substring(fileName.lastIndexOf("."));//扩展名
					photo.setExt(ext);//#3
					photo.setDateTime(MyUtils.getCurrentDateTime());//#4
					photo.setIp( request.getRemoteAddr() );//#5
					String uuid = MyUtils.getUUID();
					photo.setUuid(uuid);//#6
					photo.setDir( MyUtils.getDir(uuid) );//#7
					
					//打散目录
					File dFile = new File(path+photo.getDir());
					if(!dFile.exists()){
						dFile.mkdirs();
					}
					
					fi.write(new File(path+photo.getDir()+"/"+photo.getUuid()+photo.getExt()));
					
					
				}
				
				cont++;
				
				if(cont%2==0 && !photo.getRealName().equals("")){
					PhotoDao dao = PhotoDaoFactory.getPhotoDao();
					boo = dao.save(photo);
					//存入磁盘-法二：FileUtils.copyInputStreamToFile(in, new File(fileName2));//※2※ 把图片文件存储到服务器硬盘
					photo = new PhotoModel();//重新数据封装
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fi!=null){
				fi.delete();
			}
			if(Dom4jFactory.save()){
				out.print("<h1>上传成功！</h1>");
				out.print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
			}else{
				out.print("<h1>上传失败！</h1>");
				out.print("<a href='javascript:history.go(-1)'>返回上一页</a> ");
			}
		}
	}
}

