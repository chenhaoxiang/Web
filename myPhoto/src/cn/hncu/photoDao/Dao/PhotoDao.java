package cn.hncu.photoDao.Dao;

import java.util.List;

import cn.hncu.domain.PhotoModel;

public interface PhotoDao {
	/**
	 * @param photo
	 * @return 数据的保存
	 */
	public boolean save(PhotoModel photo);
	
	/**
	 * @return 返回所所有的图片信息
	 */
	public List<PhotoModel> getAll();
	
	/**
	 * @param uuid
	 * @return 通过uuid 查找那个被封装的值对象
	 */
	public PhotoModel getSingleByUuid(String uuid);
	
	
	/**
	 * @param uuid
	 * @return 通过uuid删除photos.xml中该图片的信息
	 */
	public boolean deleteXml(String uuid);
	
	/**
	 * @param dir
	 * @return 通过路径删除服务器磁盘中该图片的信息
	 */
	public boolean deleteFile(String pathFileName);
}
