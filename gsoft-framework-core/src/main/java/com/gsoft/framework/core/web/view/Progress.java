/**
 * 
 */
package com.gsoft.framework.core.web.view;


/**
 * 进度条信息
 * -1 表示未开始
 * 100 表示完成
 * @author Administrator
 *
 */
public class Progress extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5908457723545041165L;

	/**
	 * @Fields PROGRESS_READY : 未开始
	 */
	public final static int PROGRESS_READY = -1;
	
	/**
	 * @Fields PROGRESS_COMPLETE : 完成
	 */
	public final static int PROGRESS_COMPLETE = 100;
	
	/**
	 * @Fields percent : 百分比 
	 */
	private double percent;
	
	public Progress(double percent) {
		super("000000", ""+percent);
		this.percent = percent;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
}
