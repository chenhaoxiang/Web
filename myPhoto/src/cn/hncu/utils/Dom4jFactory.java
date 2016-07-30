package cn.hncu.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jFactory {
	private static Document dom=null;
	private static String path;
	//静态块！只会运行一次！特点是在类加载的时候就执行
	static{
		try {
			SAXReader sax = new SAXReader();
			//因为我们的资源已经从myelipse中发布到tomcat服务器中了，所以跟原来的纯Java项目不一样了。
			//利用当前类找到它的类加载器，然后通过该类加载器再去获得资源路径
			path = Dom4jFactory.class.getClassLoader().getResource("photo.xml").getPath();
			//getClassLoader()返回：加载此对象所表示的类或接口的类加载器
			//public URL getResource(String name)返回：读取资源的 URL 对象；如果找不到该资源，或者调用者没有足够的权限获取该资源，则返回 null。
			//此方法首先搜索资源的父类加载器；如果父类加载器为 null，则搜索的路径就是虚拟机的内置类加载器的路径。
			//public String getPath()获取此 URL 的路径部分。 
			System.out.println(path);
			dom = sax.read(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return 获取相册的Document
	 */
	public static Document getDocument(){
		return dom;
	}
	
	/**
	 * 进行photo.xml的保存，保存到本地
	 */
	public static boolean save(){
		try {
			XMLWriter w = new XMLWriter(new FileOutputStream(path));
			w.write(dom);
			w.close();
			return true;
		} catch (UnsupportedEncodingException e) {
			return false;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static boolean del(String uuid){
		Node node = dom.selectSingleNode("[@uuid='"+uuid+"']");
		if(node==null){
			return false;
		}
		node.getParent().remove(node);
		return true;
	}
	
	
	/**
	 * 测试用
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println( getDocument() );
	}
}
