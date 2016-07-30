package cn.hncu.photoDao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.hncu.domain.PhotoModel;
import cn.hncu.photoDao.Dao.PhotoDao;
import cn.hncu.utils.Dom4jFactory;

public class PhotoSerImpl implements PhotoDao{

	@Override
	public boolean save(PhotoModel photo) {
		Document dom = Dom4jFactory.getDocument();
		
		Element root = dom.getRootElement();
		//添加属性
		Element p = root.addElement("photo");
		p.addAttribute("uuid", photo.getUuid());
		p.addAttribute("realName", photo.getRealName());
		p.addAttribute("dateTime", photo.getDateTime());
		p.addAttribute("ip", photo.getIp());
		p.addAttribute("ext", photo.getExt());
		p.addAttribute("dir", photo.getDir());
		p.addAttribute("desc", photo.getDesc());
		return Dom4jFactory.save();
	}

	@Override
	public List<PhotoModel> getAll() {
		List<PhotoModel> list = new ArrayList<PhotoModel>();
		Document dom = Dom4jFactory.getDocument();
		Element root = dom.getRootElement();
		Iterator<Element> it = root.elementIterator("photo");
		//通过DOM4J获得photo元素的迭代器,也可以通过xPath来找到所有的 photo
		//List<Node> lists = dom.selectNodes("//photo[@uuid]");
		//Iterator<Node> it = lists.iterator();
		while(it.hasNext()){
			Element e = it.next();
			PhotoModel photo = new PhotoModel();
			photo.setUuid(e.attributeValue("uuid"));
			photo.setRealName(e.attributeValue("realName"));
			photo.setDateTime(e.attributeValue("dateTime"));
			photo.setExt(e.attributeValue("ext"));
			photo.setIp(e.attributeValue("ip"));
			photo.setDir(e.attributeValue("dir"));
			photo.setDesc(e.attributeValue("desc"));
			list.add(photo);
		}
		return list;
	}

	@Override
	public PhotoModel getSingleByUuid(String uuid) {
		List<PhotoModel> photos=getAll();
		for(PhotoModel photo:photos){
			if(photo.getUuid().equals(uuid)){
				return photo;
			}
		}
		return null;
	}

	@Override
	public boolean deleteXml(String uuid) {
		Document dom = Dom4jFactory.getDocument();
		Element e = (Element) dom.selectSingleNode("//photo[@uuid='"+uuid.trim()+"']");
		return e.getParent().remove(e);
	}

	@Override
	public boolean deleteFile(String pathFileName) {
		try {
			File file = new File(pathFileName);
			if(file.exists()){
				file.delete();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
