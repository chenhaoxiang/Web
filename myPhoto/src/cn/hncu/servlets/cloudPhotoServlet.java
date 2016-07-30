package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.PhotoModel;
import cn.hncu.photoDao.Dao.PhotoDao;
import cn.hncu.photoDao.factory.PhotoDaoFactory;

public class cloudPhotoServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pwd = (String) request.getParameter("pwd");
		if ("chx".equals(pwd)) {
			doPost(request, response);
		} else {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("<h1>很抱歉，你没有权限访问！！！</h1>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>CHX云相册</TITLE></HEAD>");
		out.println("  <BODY>");

		out.println("<table border='1px' bordercolor='red'>");
		out.println("<tr>");
		out.println("<td>文件名</td>");
		out.println("<td>上传时间");
		out.println("<td>文件</td>");
		out.println("<td>文件说明</td>");
		out.println("<td>操作</td> ");
		out.println("</tr>");

		String tdWH = "style='width:200px; height:200px;'";

		// 显示所有图片
		PhotoDao dao = PhotoDaoFactory.getPhotoDao();
		List<PhotoModel> photos = dao.getAll();
		for (PhotoModel photo : photos) {
			String fileName = photo.getRealName();
			String time = photo.getDateTime();

			// 输出图片
			String path = request.getContextPath() + "/photos/"
					+ photo.getDir() + "/" + photo.getUuid() + photo.getExt();
			// System.out.println(path);
			// /myPhoto/photos//7/c/a1237a48a6aa451cb22fa78b15bafcea.jpg
			String img = "<a href='" + path + "'><img src='" + path + "'/></a>";
			String desc = photo.getDesc();

			String delStr = "<a href='/myPhoto/servlet/DelServlet?uuid="
					+ photo.getUuid() + "'>删除</a>";
			String downStr = "<a href='/myPhoto/servlet/DownServlet?uuid="
					+ photo.getUuid() + "'>下载</a>";

			out.println("<tr>");
			out.println("<td " + tdWH + "> " + fileName + " </td>");
			out.println("<td " + tdWH + ">" + time + "</td>");
			out.println("<td " + tdWH + ">" + img + "</td>");
			out.println("<td " + tdWH + ">" + desc + "</td>");
			out.println("<td " + tdWH + ">" + delStr + "&nbsp;" + downStr
					+ "</td>");
			out.println("</tr>");
		}

		out.println("<tr>");
		out.println("</tr>");

		out.println("</table>");

		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
