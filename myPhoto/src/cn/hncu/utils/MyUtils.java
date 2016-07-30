package cn.hncu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MyUtils {
	
	/**
	 * @return 获取UUID
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * @param uuid
	 * @return 通过uuid，获得打散后的路径
	 */
	public static String getDir(String uuid){
		String dir1 = Integer.toHexString( uuid.hashCode() & 0xf );
		String dir2 = Integer.toHexString( (uuid.hashCode() & 0xf0)>>4 );
		return "/"+dir1+"/"+dir2;
	}
	
	//日期时间格式
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	/**
	 * @return 返回的是上传的时候的日期时间
	 */
	public static String getCurrentDateTime(){
		return sdf.format(new Date());
	}
	
}
