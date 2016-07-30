package cn.hncu.photoDao.factory;

import cn.hncu.photoDao.impl.PhotoSerImpl;

/**
 * 工厂方法
 * @author 陈浩翔
 * 2016-7-24
 */
public class PhotoDaoFactory {
	public static PhotoSerImpl getPhotoDao(){
		return new PhotoSerImpl();
	}
}
