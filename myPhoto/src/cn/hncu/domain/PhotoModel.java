package cn.hncu.domain;
/**
 * 值对象封装
 * @author 陈浩翔
 * 2016-7-24
 */
public class PhotoModel {
	
	private String uuid;//uuid
	private String realName="";//图片真实文件名(上传时的文件名)
	private String ext;//后缀名
	private String dir;//打散后的路径
	private String dateTime;//上传文件的时间
	private String ip;//上传者的IP
	private String desc;//文件的说明
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "PhotoModel [uuid=" + uuid + ", realName=" + realName + ", ext="
				+ ext + ", dir=" + dir + ", dateTime=" + dateTime + ", ip="
				+ ip + ", desc=" + desc + "]";
	}
	
}
