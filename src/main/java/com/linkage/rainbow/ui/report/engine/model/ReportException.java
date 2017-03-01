package com.linkage.rainbow.ui.report.engine.model;

/**
 * 报表系统抛出的异常<br>
 * <p>
 * @version 1.0
 * @author 陈亮 2009-6-1
 * <hr>
 * 修改记录
 * <hr>
 * 1、修改人员: 陈亮   修改时间:2009-6-1<br>       
 *    修改内容:新建
 * <hr>
 */
public class ReportException extends RuntimeException {
	/**
	 * 空构造
	 *
	 */
  public ReportException() {
    super();
  }

  /**
   * 根据错误信息构造
   * @param sMess_ 错误信息
   */
  public ReportException(String sMess_) {
	 
    super(sMess_);
  }
  
  /**
   * 根据错误信息与异常类构造
   * @param sMess_ 错误信息
   * @param t 异常类
   */
  public ReportException(String sMess_,Throwable t) {
		 
	    super(sMess_,t);
 }
  
  /**
   * 根据异常类构造
   * @param cause 异常类
   */
  public ReportException(Throwable cause) {
    super(cause);
  }

}
