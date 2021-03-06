package cn.crap.utils;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private int allRow = 0; // allRow will always big than -1
	private int currentPage; // currentPage will always big than 0
	private int size; // size will always big than 0
	private int totalPage = 0; // totalPage will always big than -1
	private List<T> list;

	public Page(Integer currentPage){
		this(15, currentPage == null ? 1 : currentPage);
	}
	public Page(Integer size, Integer currentPage){
		Assert.notNull(size);
		Assert.notNull(currentPage);
		Assert.isTrue(size > 0 && size <= 1000);
		Assert.notNull(currentPage > 0);

		this.currentPage = currentPage;
		this.size = size;
	}

	/**
	 * the total number of result
	 * @return
	 */
	public int getAllRow() {
		return allRow;
	}

	/**
	 * allRow must big or equal 0
	 * @param allRow
	 */
	public void setAllRow(Integer allRow) {
		Assert.notNull(allRow);
		Assert.notNull(allRow >= 0);

		this.totalPage = (allRow+size-1)/size;
		this.allRow = allRow;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * according to size and currentPage calculate the start row num for databases
	 * @return
	 */
	public int getStart(){
		return  this.getSize() * (this.getCurrentPage() - 1);
	}

	public int getSize() {
		return size;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
