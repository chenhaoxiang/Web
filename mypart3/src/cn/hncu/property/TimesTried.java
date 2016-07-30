package cn.hncu.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TimesTried {
	public static void main(String[] args) throws IOException {
		if(countDemo()){
			//进入软件的相应模块
			
		}else{
			
			System.out.println("试用次数已到，请进行注册！");
		}
		
	}

	private static boolean countDemo() throws IOException {
		Properties p = new Properties();
		
		int count =0;
		//配置文件
		
		File configFile = new File("config.chx");
		
		if(!configFile.exists()){
			configFile.createNewFile();
		}
		
		FileInputStream fin = new FileInputStream(configFile);
		//下面的一句为错误的演示，已注释
		//FileOutputStream fout = new FileOutputStream(configFile);
		//FileOutputStream对象一new出来就会创建一个新文件，自然就覆盖旧的文件数据了。
		//因此程序每次运行到这里都会产生新文件
		
		//把配置文件中的信息读入p对象当中
		p.load(fin);
		
		//从p对象当中读取数据---软件试用次数
		String value = p.getProperty("count");
		
		if(value!=null){
			count = Integer.parseInt(value);
			if(count>=5){
				return false;
			}
			
		}
		
		
		count++;
		System.out.println("运行"+count+"次");
		
		//把当前使用的次数存储到配置文件当中
		p.setProperty("count", ""+count);
		//也可以使用这句
		//p.setProperty("count",String.valueOf(count));
		
		FileOutputStream fout = new FileOutputStream(configFile);
		
		p.store(fout, null);
		
		fin.close();
		fout.close();
		
		return true;
		
		
	}

}
