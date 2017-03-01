package com.linkage.rainbow.ui.views.jsp.ui.loadmask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import com.linkage.rainbow.ui.components.loadmask.LoadMask;
import com.linkage.rainbow.ui.views.jsp.ui.UITagBase;
import com.linkage.rainbow.util.config.DefaultSettings;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * DateTag<br>
 * 进度条标签类提供对进度条标签中属性的操作
 * <p>
 * @version 1.0
 * <hr>
 */

public class LoadMaskTag extends UITagBase {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String skin ;
	
	/**
	 * 标签的构造函数中,增加根据配置文件初始化默认值
	 * 默认配置文件路径为:com/linkage/rainbow/uidufault.properties
	 * 项目组使用时,修改的配置文件路径为:comm.properties
	 * 1.配置文件中UI组件默认值设置的参数名设置规则为rainbow.ui.为前缀,
	 *   接下来是标签文件中定义的标签名,
	 *   最后是标签文件中定义的此标签的属性名
	 *   例：
	 *   rainbow.ui.date.template=WdatePicker
	 * 2.在标签类构造函数中通过调用：
	 * DefaultSettings.setDefaultValue(this,"rainbow.ui.标签名");
	 * 实现此标签的默认值设置
	 * @author 陈亮 2009-3-18 
	 */
	public LoadMaskTag(){
		DefaultSettings.setDefaultValue(this,"rainbow.ui.loadmask");
	}

	/**
	 * 实现接口类 继承父类的基本信息
	 * @param stack
	 * @param req
	 * @param res
	 * @return Component
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new LoadMask(stack, req, res);
	}

	/**
	 * 调用父类接口 将标签的扩展信息设置到组件类
	 */
	protected void populateParams() {
		super.populateParams();
		LoadMask loadMask = (LoadMask) component ;
		loadMask.setSkin(skin) ;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
	
}
