package com.linkage.rainbow.ui.views.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linkage.rainbow.ui.components.comboTree.ComboTreeNode;
import com.linkage.rainbow.ui.components.comboTree.IComboTreeNode;
import com.linkage.rainbow.ui.components.tree.ITreeNode;
import com.linkage.rainbow.ui.struts.BaseAction;
import com.linkage.rainbow.util.StringUtil;

import net.sf.json.JSONArray;

public class ComboTreeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ComboTreeAction.class);
	// JSONArray jsonArray;
	private String results;

	public String execute() throws UnsupportedEncodingException {

//		System.out.println("我已经进入下拉树类");
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sysencoding = org.apache.struts2.config.DefaultSettings
				.get("struts.i18n.encoding");
		request.setCharacterEncoding(sysencoding);
		String method = StringUtil.valueOf(request.getParameter("method"));// 调用的方法
		String params = StringUtil.valueOf(request.getParameter("params"));// 参数
		String treetype = StringUtil.valueOf(request.getParameter("treetype"));// 树的类型
		String id = StringUtil.valueOf(request.getParameter("id"));// 节点id
		String parentnode = StringUtil.valueOf(request
				.getParameter("parentnode"));// 父节点字段名
		String treeid = StringUtil.valueOf(request.getParameter("treeid"));// 树的id
		String mode = StringUtil.valueOf(request.getParameter("mode"));// 选择checkbox的模式
		String sec = StringUtil.valueOf(request.getParameter("sec"));// 是否显示第2个复选框
		String sectx = java.net.URLDecoder.decode(StringUtil.valueOf(request
				.getParameter("sectx")), "utf-8");
		// String
		// sectx=StringUtil.valueOf(request.getParameter("sectx"));//第2个复选框文本

		String index = StringUtil.valueOf(request.getParameter("index"));// 是否是第1次查询
		String openRoad = StringUtil.valueOf(request.getParameter("openRoad"));// 默认展开路径
		String defaultValue = StringUtil.valueOf(request
				.getParameter("defaultValue"));// 第1个选择框的默认值
		String secondValue = StringUtil.valueOf(request
				.getParameter("secondValue"));// 第1个选择框的默认值
		String tocancelcheckbox = StringUtil.valueOf(request
				.getParameter("tocancelcheckbox"));

		// 第2次查询参数替换
		if (!id.equals("")) {

			params = this.replacaParam(params, parentnode, id);
		}

		// 参数分解完毕
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		List list = null;
		try {
			list = this.doHashMapList(params, method, ctx);
		} catch (Exception ex) {
			try {
				list = this.doMapList(params, method, ctx);
			} catch (Exception e) {
				log.error(e.getStackTrace());
			}
		}

		try {

			response.setCharacterEncoding(sysencoding);
			response
					.setContentType("text/xml; charset=\"" + sysencoding + "\"");
			response.setHeader("Cache-Control", "no-cache");

			response.getWriter().println(transToJson(list));
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

		return null;
	}

	/**
	 * 动态调用spring的bean获取List,参数为HashMap
	 * 
	 * @param params
	 * @param methods
	 * @param ctx
	 * @return List
	 * @throws Exception
	 */
	public List doHashMapList(String params, String methods,
			WebApplicationContext ctx) throws Exception {
		Object retobj = null;
		try {
			HashMap map = new HashMap();
			if (params.length() != 0) {
				if (params.indexOf(";") == -1) {
					map
							.put(params.split(":")[0],
									params.split(":").length > 1 ? (params
											.split(":")[1]) : "");
				} else {
					String[] allparams = params.split(";");
					for (int i = 0; i < allparams.length; i++) {
						if (allparams[i].length() != 0) {
							map.put(allparams[i].split(":")[0], allparams[i]
									.split(":").length > 1 ? (allparams[i]
									.split(":")[1]) : "");
						}
					}
				}
			}

			Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();// Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
			Class partypes[] = new Class[1];
			partypes[0] = map.getClass();
			Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
			Object arglist[] = new Object[1];
			arglist[0] = map;
			retobj = meth.invoke(ctx.getBean(methods.split("\\.")[0]), arglist);
		} catch (Exception ex) {
			log.error(ex.getStackTrace());
		}
		return (List) retobj;
	}

	/**
	 * 动态调用spring的bean获取List,参数为HashMap
	 * 
	 * @param params
	 * @param methods
	 * @param ctx
	 * @return List
	 * @throws Exception
	 */
	public List doMapList(String params, String methods,
			WebApplicationContext ctx) throws Exception {
		Object retobj = null;
		try {
			Map map = new HashMap();
			if (params.length() != 0) {
				if (params.indexOf(";") == -1) {
					map
							.put(params.split(":")[0],
									params.split(":").length > 1 ? (params
											.split(":")[1]) : "");
				} else {
					String[] allparams = params.split(";");
					for (int i = 0; i < allparams.length; i++) {
						if (allparams[i].length() != 0) {
							map.put(allparams[i].split(":")[0], allparams[i]
									.split(":").length > 1 ? (allparams[i]
									.split(":")[1]) : "");
						}
					}
				}
			}
			Class cls = ctx.getBean(methods.split("\\.")[0]).getClass();// Class.forName(ctx.getBean(methods.split("\\.")[0]).getClass().getName());
			Class partypes[] = new Class[1];
			partypes[0] = map.getClass();
			Method meth = cls.getMethod(methods.split("\\.")[1], partypes);
			Object arglist[] = new Object[1];
			arglist[0] = map;
			retobj = meth.invoke(ctx.getBean(methods.split("\\.")[0]), arglist);
		} catch (Exception ex) {
			log.error(ex.getStackTrace());
		}
		return (List) retobj;
	}

	/**
	 * 调用方法的参数替换
	 * 
	 * @param param
	 * @param key
	 * @param value
	 * @return String
	 */
	public String replacaParam(String param, String key, String value) {
		String str = "";
		String[] temp = param.split(";");
		for (int i = 0; i < temp.length; i++) {

			if (temp[i].split(":")[0].equals(key) == true) {
				str = str + temp[i].split(":")[0] + ":" + value + ";";

			} else {
				str = str + temp[i] + ";";
			}
		}
		return str;
	}

	/**
	 * 判断节点是否在默认展开的节点里面
	 * 
	 * @param openRoad
	 * @param node
	 * @return boolean
	 */
	public boolean toOpen(String openRoad, String node) {
		boolean flag = false;
		if (openRoad.equals("")) {
		} else {
			if (openRoad.lastIndexOf(",") != openRoad.length() - 1) {
				openRoad += ",";
			}
			openRoad = "," + openRoad;
			node = "," + node + ",";
			if (openRoad.indexOf(node) > -1) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 将查询的list转换为json格式的字符串
	 * 
	 */

	public String transToJson(List list) {
		if (list != null && list.size() > 0) {
			List newList = new ArrayList();
			JSONArray jsonArray = null;
			if (list.get(0) instanceof Map) {
				for (int i = 0; i < list.size(); i++) {
					Object obj = list.get(i);
					Map map = (Map) list.get(i);
					String hava_sub = map.get("HAVE_SUB")!=null?map.get("HAVE_SUB").toString():null;
					Map m = new HashMap();
					if (hava_sub == null || hava_sub.equals("0") || hava_sub.equals("")) {
						m.put("state", "open");
					} else {
						m.put("state", "closed");
					}
					m.put("id", map.get("NODE"));
					m.put("f_node", map.get("F_NODE"));
					m.put("text", map.get("TEXT"));
					m.put("have_sub", map.get("HAVE_SUB"));
					newList.add(m);
				}
				jsonArray = JSONArray.fromObject(newList);
			} else {
				jsonArray = JSONArray.fromObject(list);
			}
//			System.out.println(jsonArray.toString());
			return jsonArray.toString();
		}

		return null;

	}

	/**
	 * 将查询的list转换为xml格式的字符串
	 * 
	 * @param list
	 *            结果集
	 * @param treetype
	 *            树的类型
	 * @param treeid
	 *            树的id
	 * @param mode
	 *            树的模式
	 * @param showSecond
	 *            是否显示第2个checkbox
	 * @param secondText
	 *            第2个checkbox的文本
	 * @param index
	 *            是否是第一次查询
	 * @param openRoad
	 *            展开路径
	 * @param defaultValue
	 *            第2个节点默认值
	 * @param secondValue
	 *            第2个节点默认值
	 * @param tocancelcheckbox
	 *            是否取消第2个checkbox
	 * @return String
	 */
	// public static String transtoXMLString(List list,String treetype,String
	// treeid,String mode,String showSecond,String secondText,String
	// index,String openRoad,String defaultValue,String secondValue,String
	// tocancelcheckbox){
	// if(list==null||list.size()==0){
	// return "<tree id=\"rootdynsids\">";
	// }else{
	// if(list.get(0) instanceof HashMap){
	// //记录上级结点的栈
	// List parentList = new ArrayList();
	// String parent=null,preNode=null;
	// String xml ="";
	// if(index.equals("0")){
	// xml="<tree id=\"rootdynsids\">";
	// }else{
	// xml="<tree id=\""+StringUtil.valueOf(((HashMap)list.get(0)).get("F_NODE"))+"\">";
	// }
	// for(int i=0;i<list.size();i++){
	// HashMap map = (HashMap)list.get(i);
	// parent=null;
	//					
	// if(parentList.size() >0){ //从栈取得最后一个上级结点
	// parent=StringUtil.valueOf(parentList.get(parentList.size() -1));
	// }
	// if(parent!=null &&parent.equals(StringUtil.valueOf(map.get("F_NODE")))){
	// //如果与前一条同级,则输入上条结尾</tree>
	// xml+="</item>";
	// } else {
	// int iIndex = parentList.indexOf(StringUtil.valueOf(map.get("F_NODE")));
	// //是否已在栈中
	// if(iIndex>-1){ //如果在栈中,如果输入,则输入(iParentListSize-iIndex)条结尾</tree>
	// int iParentListSize=parentList.size();
	// for(int j=iParentListSize-1;j>=iIndex;j-- ){
	// xml+="</item>";
	// if(j<iParentListSize-1)
	// parentList.remove(parentList.size()-1); //删除栈中最后一个
	// }
	// }else {//如果不在栈中,则说明继续输入下一级结点,则增加些上级结点值到 栈中
	// if(preNode == null ||
	// StringUtil.valueOf(map.get("F_NODE")).equals(preNode))
	// parentList.add(StringUtil.valueOf(map.get("F_NODE")));
	// else {
	// for(int x=0;x<parentList.size();x++){
	// xml+="</item>";
	// }
	// parentList.clear();
	// parentList.add(StringUtil.valueOf(map.get("F_NODE")));
	// }
	//							
	// }
	// }
	// String child="";
	// if(StringUtil.valueOf(map.get("HAVE_SUB")).equals("0")||StringUtil.valueOf(map.get("HAVE_SUB")).equals("")){
	// child="child=\"0\"";
	// }else{
	// child="child=\"1\"";
	// }
	// String strIcon="";
	// //节点关闭图标
	// if(!StringUtil.valueOf(map.get("ICON")).equals("")){
	// strIcon=" im2=\""+map.get("ICON")+"\" im0=\""+map.get("ICON")+"\"";
	// }
	// //节点展开图标
	// if(!StringUtil.valueOf(map.get("OPENICON")).equals("")){
	// strIcon+=" im1=\""+map.get("OPENICON")+"\"";
	// }
	//					
	// //默认展开的设置
	// String isOpen="";
	// if(SelectTree.toOpen(openRoad, StringUtil.valueOf(map.get("NODE")))){
	// isOpen=" open=\"1\" ";
	// }
	//					
	// //默认是否选择
	// String ischecked="";
	// if(SelectTree.toOpen(defaultValue, StringUtil.valueOf(map.get("NODE")))){
	// ischecked=" checked ";
	// }
	//					
	// //第2个checkbox默认是否选择
	// String secondChecked="";
	// if(SelectTree.toOpen(secondValue, StringUtil.valueOf(map.get("NODE")))){
	// secondChecked=" checked ";
	// }
	//					
	// if(treetype.equals("simple")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\""+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }else if(treetype.equals("radio")){
	// if(StringUtil.valueOf(map.get("NODE_TYPE")).equals("L")){
	// xml+="<item child=\"0\" id=\""+StringUtil.valueOf(map.get("NODE"))+"LeaderNodeTree\" text=\"&lt;input type='radio' myurl='"+StringUtil.valueOf(map.get("URL"))+"' name='"+treeid+"radio' value='"+StringUtil.valueOf(map.get("NODE"))+"'"+ischecked+" text='"+StringUtil.valueOf(map.get("TEXT"))+"' onclick='"+treeid+"radioClick(this,event)' &gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }else if(StringUtil.valueOf(map.get("NODE_TYPE")).equals("T")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\""+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+
	// "> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='radio' myurl='"+StringUtil.valueOf(map.get("URL"))+"' name='"+treeid+"radio' value='"+StringUtil.valueOf(map.get("NODE"))+"'"+ischecked+" text='"+StringUtil.valueOf(map.get("TEXT"))+"' onclick='"+treeid+"radioClick(this,event)' &gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }
	// }else if(treetype.equals("checkbox")){
	// if(mode.equals("simple")){
	// if(StringUtil.valueOf(map.get("NODE_TYPE")).equals("L")){
	// if(showSecond.equals("true")){
	// xml+="<item child=\"0\" id=\""+StringUtil.valueOf(map.get("NODE"))+"LeaderNodeTree\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"simple"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(map.get("NODE"))+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(map.get("NODE"))+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item child=\"0\" id=\""+StringUtil.valueOf(map.get("NODE"))+"LeaderNodeTree\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"simple"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }
	// }else if(StringUtil.valueOf(map.get("NODE_TYPE")).equals("T")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\""+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }else{
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"simple"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"&lt;input type='checkbox'"+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(map.get("NODE"))+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(map.get("NODE"))+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"simple"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }
	// }
	// }else if(mode.equals("cascade")){
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"cascade"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"cascade"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"operateConnect(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(map.get("NODE"))+"' onclick='"+treeid+"setCascadeSecondHidden(this)' id='"+treeid+"secondCascade"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"secondCascade"+StringUtil.valueOf(map.get("F_NODE"))+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"cascade"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"cascade"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"operateConnect(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }
	//							
	// }else if(mode.equals("contain")){
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' "+(tocancelcheckbox.equals("true")?"disabled='true'":"")+" id='"+treeid+"contain"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"contain"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"operateContain(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(map.get("NODE"))+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(map.get("NODE"))+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(map.get("NODE"))+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' "+(tocancelcheckbox.equals("true")?"disabled='true'":"")+" id='"+treeid+"contain"+StringUtil.valueOf(map.get("NODE"))+"' parentid='"+treeid+"contain"+StringUtil.valueOf(map.get("F_NODE"))+"' value='"+StringUtil.valueOf(map.get("NODE"))+"' text='"+StringUtil.valueOf(map.get("TEXT"))+"'"+" onclick='"+treeid+"operateContain(this)'"+"&gt;"+StringUtil.valueOf(map.get("TEXT"))+"\""+strIcon+"> ";
	// }
	//							
	// }
	// }
	//					
	// if(!StringUtil.valueOf(StringUtil.valueOf(map.get("URL"))).equals("")){
	// xml+="<userdata name=\"myurl\">"+StringUtil.valueOf(map.get("URL")).replaceAll("&amp;",
	// "tempsurl;").replaceAll("&", "tempsurl;")+"</userdata>";
	// }
	// preNode = StringUtil.valueOf(map.get("NODE"));
	// }
	// for(int i=0;i<parentList.size();i++){
	// xml+="</item>";
	// }
	// return xml;
	// }else{
	// ITreeNode node =null;
	// //记录上级结点的栈
	// List parentList = new ArrayList();
	// String parent=null,preNode=null;
	// String xml ="";
	// if(index.equals("0")){
	// xml="<tree id=\"rootdynsids\">";
	// }else{
	// xml="<tree id=\""+((ITreeNode)list.get(0)).getF_Node()+"\">";
	// }
	// for(int i=0;i<list.size();i++){
	// node = (ITreeNode)list.get(i);
	// parent=null;
	// if(parentList.size() >0){ //从栈取得最后一个上级结点
	// parent=(String)parentList.get(parentList.size() -1);
	// }
	// if(parent!=null &&parent.equals( node.getF_Node())){
	// //如果与前一条同级,则输入上条结尾</tree>
	// xml+="</item>";
	// } else {
	// int iIndex = parentList.indexOf(node.getF_Node()); //是否已在栈中
	// if(iIndex>-1){ //如果在栈中,如果输入,则输入(iParentListSize-iIndex)条结尾</tree>
	// int iParentListSize=parentList.size();
	// for(int j=iParentListSize-1;j>=iIndex;j-- ){
	// xml+="</item>";
	// if(j<iParentListSize-1)
	// parentList.remove(parentList.size()-1); //删除栈中最后一个
	// }
	// }else {//如果不在栈中,则说明继续输入下一级结点,则增加些上级结点值到 栈中
	// if(preNode == null || node.getF_Node().equals(preNode))
	// parentList.add(node.getF_Node());
	// else {
	// for(int x=0;x<parentList.size();x++){
	// xml+="</item>";
	// }
	// parentList.clear();
	// parentList.add(node.getF_Node());
	// }
	// }
	// }
	// String child="";
	// if(StringUtil.valueOf(node.getHave_Sub()).equals("0")||StringUtil.valueOf(node.getHave_Sub()).equals("")){
	// child="child=\"0\"";
	// }else{
	// child="child=\"1\"";
	// }
	// String strIcon="";
	// //节点关闭图标
	// if(!StringUtil.valueOf(node.getIcon()).equals("")){
	// strIcon=" im2=\""+node.getIcon()+"\" im0=\""+node.getIcon()+"\"";
	// }
	//					
	// //节点展开图标
	// if(!StringUtil.valueOf(node.getOpenIcon()).equals("")){
	// strIcon+=" im1=\""+node.getOpenIcon()+"\"";
	// }
	//					
	// //默认展开的设置
	// String isOpen="";
	// if(SelectTree.toOpen(openRoad, StringUtil.valueOf(node.getNode()))){
	// isOpen=" open=\"1\" ";
	// }
	// //默认是否选择
	// String ischecked="";
	// if(SelectTree.toOpen(defaultValue, StringUtil.valueOf(node.getNode()))){
	// ischecked=" checked ";
	// }
	// //第2个checkbox默认是否选择
	// String secondChecked="";
	// if(SelectTree.toOpen(secondValue, StringUtil.valueOf(node.getNode()))){
	// secondChecked=" checked ";
	// }
	//					
	// if(treetype.equals("simple")){
	// xml+="<item "+child+isOpen+" id=\""+node.getNode()+"\" text=\""+node.getText()+"\""+strIcon+
	// "> ";
	// }else if(treetype.equals("radio")){
	// if(StringUtil.valueOf(node.getNode_Type()).equals("L")){
	// xml+="<item child=\"0\" id=\""+node.getNode()+"LeaderNodeTree\" text=\"&lt;input type='radio' myurl='"+StringUtil.valueOf(node.getUrl())+"' name='"+treeid+"radio' value='"+StringUtil.valueOf(node.getNode())+"'"+ischecked+" text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"radioClick(this,event);' &gt;"+node.getText()+"\""+strIcon+
	// "> ";
	// }else if(StringUtil.valueOf(node.getNode_Type()).equals("T")){
	// xml+="<item "+child+isOpen+" id=\""+node.getNode()+"\" text=\""+node.getText()+"\""+strIcon+
	// "> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+node.getNode()+"\" text=\"&lt;input type='radio' myurl='"+StringUtil.valueOf(node.getUrl())+"' name='"+treeid+"radio' value='"+StringUtil.valueOf(node.getNode())+"'"+ischecked+" text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"radioClick(this,event);' &gt;"+StringUtil.valueOf(node.getText())+"\""+strIcon+
	// "> ";
	// }
	// }else if(treetype.equals("checkbox")){
	// if(mode.equals("simple")){
	// if(StringUtil.valueOf(node.getNode_Type()).equals("L")){
	// if(showSecond.equals("true")){
	// xml+="<item child=\"0\" id=\""+StringUtil.valueOf(node.getNode())+"LeaderNodeTree\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"simple"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"&lt;input type='checkbox'"+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(node.getNode())+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(node.getNode())+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item child=\"0\" id=\""+StringUtil.valueOf(node.getNode())+"LeaderNodeTree\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"simple"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"\""+strIcon+"> ";
	// }
	// }else if(StringUtil.valueOf(node.getNode_Type()).equals("T")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\""+StringUtil.valueOf(node.getText())+"\""+strIcon+"> ";
	// }else{
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"simple"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(node.getNode())+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(node.getNode())+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"simple"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"simple"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"getAllChkValue(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"\""+strIcon+"> ";
	// }
	//								
	// }
	// }else if(mode.equals("cascade")){
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"cascade"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"cascade"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"operateConnect(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(node.getNode())+"' onclick='"+treeid+"setCascadeSecondHidden(this)' id='"+treeid+"secondCascade"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"secondCascade"+StringUtil.valueOf(node.getF_Node())+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' id='"+treeid+"cascade"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"cascade"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"operateConnect(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"\""+strIcon+"> ";
	// }
	//							
	// }else if(mode.equals("contain")){
	// if(showSecond.equals("true")){
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' "+(tocancelcheckbox.equals("true")?"disabled='true'":"")+" id='"+treeid+"contain"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"contain"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"operateContain(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"&lt;input type='checkbox' "+(ischecked.equals("")?"disabled='true'":"")+(secondChecked.equals("")?"":secondChecked)+" value='"+StringUtil.valueOf(node.getNode())+"' onclick='"+treeid+"setSecondHidden()' id='"+treeid+"|second"+StringUtil.valueOf(node.getNode())+"' name='"+treeid+"secondbox'&gt;"+secondText+"\""+strIcon+"> ";
	// }else{
	// xml+="<item "+child+isOpen+" id=\""+StringUtil.valueOf(node.getNode())+"\" text=\"&lt;input type='checkbox'"+ischecked+" name='"+treeid+"checkbox' "+(tocancelcheckbox.equals("true")?"disabled='true'":"")+" id='"+treeid+"contain"+StringUtil.valueOf(node.getNode())+"' parentid='"+treeid+"contain"+StringUtil.valueOf(node.getF_Node())+"' value='"+StringUtil.valueOf(node.getNode())+"' text='"+StringUtil.valueOf(node.getText())+"' onclick='"+treeid+"operateContain(this)'"+"&gt;"+StringUtil.valueOf(node.getText())+"\""+strIcon+"> ";
	// }
	//							
	// }
	// }
	// if(!StringUtil.valueOf(node.getUrl()).equals("")){
	// xml+="<userdata name=\"myurl\">"+StringUtil.valueOf(node.getUrl()).replaceAll("&amp;",
	// "tempsurl;").replaceAll("&", "tempsurl;")+"</userdata>";
	// }
	// preNode = StringUtil.valueOf(node.getNode());
	// }
	// for(int i=0;i<parentList.size();i++){
	// xml+="</item>";
	// }
	// return xml;
	// }
	// }
	// }
	public static void main(String[] args) {

		// List results =new ArrayList();
		// Map map = new HashMap();
		// map.put("id0", 1);
		// map.put("id1", 2);
		// map.put("id2", 3);
		//		
		//
		// Map map1 = new HashMap();
		// map1.put("id10", 1);
		// map1.put("id11", 2);
		// map1.put("id21", 3);
		//		
		// Map map3 = new HashMap();
		// map3.put("id10", 1);
		// map3.put("id11", 2);
		// map3.put("id21", 3);
		//		
		// results.add(map3);
		// results.add(map1);
		// map.put("children", results);
		ComboTreeAction ss = new ComboTreeAction();

		List list = new ArrayList();
		ComboTreeNode ll = new ComboTreeNode();
		ll.setId("12");
		ll.setText("平台事业部");
		ll.setHave_Sub("0");
		IComboTreeNode ww = ll;
		ComboTreeNode hh = new ComboTreeNode();
		hh.setId("3");
		hh.setText("fdsfds");
		hh.setHave_Sub("3");
		IComboTreeNode rr = hh;
		list.add(ww);
		list.add(rr);

		ss.transToJson(list);
		// Map map_1 = new HashMap();
		// map_1.put("f_node", "0");
		// map_1.put("id", "1");
		// map_1.put("text", "平台事业部");
		// Map map_2 = new HashMap();
		// map_2.put("f_node", "0");
		// map_2.put("node", "2");
		// map_2.put("text", "平台事业部2");
		// Map map_3 = new HashMap();
		// map_3.put("f_node", "0");
		// map_3.put("node", "3");
		// map_3.put("text", "平台事业部3");
		// list.add(map_1);
		// list.add(map_2);
		// list.add(map_3);
		//		
		// ss.transToJson(list);
		// Map mapTree= new HashMap();
		//
		// mapTree.put("id", "0");
		// mapTree.put("text", "部门根节点");
		// mapTree.put("iconCls", "icon-ok");
		// JSONArray jsonArray = JSONArray.fromObject(list);
		//
		//
		// System.out.println(jsonArray.toString());

		// System.out.println(this.transtoString());
	}

	// public JSONArray getJsonArray() {
	// return jsonArray;
	// }
	//
	//
	// public void setJsonArray(JSONArray jsonArray) {
	// this.jsonArray = jsonArray;
	// }

	// 将查询的list转换为xml
	// public static String transtoString(){
	// List<ITreeNode> list = new ArrayList<ITreeNode>();
	//		
	// ITreeNode node =null;
	// // node = new TreeNodeImpl("3","根结点","2");list.add(node);
	// // node = new TreeNodeImpl("1","系统管理","0");list.add(node);
	// node = new TreeNodeImpl("1","根结点","0");list.add(node);
	// node = new TreeNodeImpl("2","系统管理","1");list.add(node);
	// node = new TreeNodeImpl("3","角色管理","2");list.add(node);
	// node = new TreeNodeImpl("64","角色管理添加","3");list.add(node);
	// node = new TreeNodeImpl("65","角色管理删除","3");list.add(node);
	// node = new TreeNodeImpl("66","角色管理查询","3");list.add(node);
	// node = new TreeNodeImpl("67","角色管理修改","3");list.add(node);
	// node = new TreeNodeImpl("98","角色菜单权限设置","3");list.add(node);
	// node = new TreeNodeImpl("99","角色 数据权限设置","3");list.add(node);
	// //
	// // node = new TreeNodeImpl("888","根结点","777");list.add(node);
	// // node = new TreeNodeImpl("999","系统管理","888");list.add(node);
	//		
	// node = new TreeNodeImpl("5","组织管理","2");list.add(node);
	// node = new TreeNodeImpl("88","组织管理添加","5");list.add(node);
	// node = new TreeNodeImpl("90","组织管理修改","5");list.add(node);
	// node = new TreeNodeImpl("89","组织管理删除","5");list.add(node);
	// node = new TreeNodeImpl("91","人员添加","5");list.add(node);
	// node = new TreeNodeImpl("92","人员删除","5");list.add(node);
	// node = new TreeNodeImpl("93","人员编辑","5");list.add(node);
	// node = new TreeNodeImpl("94","添加人员角色","5");list.add(node);
	// node = new TreeNodeImpl("95","添加人员用户级数据权限","5");list.add(node);
	// node = new TreeNodeImpl("96","添加人员临时权限","5");list.add(node);
	// node = new TreeNodeImpl("6","菜单管理","2");list.add(node);
	// node = new TreeNodeImpl("39","菜单管理添加","6");list.add(node);
	// node = new TreeNodeImpl("40","菜单管理删除","6");list.add(node);
	// node = new TreeNodeImpl("41","菜单管理查询","6");list.add(node);
	// node = new TreeNodeImpl("86","菜单管理修改","6");list.add(node);
	// node = new TreeNodeImpl("97","批量添加操作权限","6");list.add(node);
	// node = new TreeNodeImpl("43","修改密码","2");list.add(node);
	// node = new TreeNodeImpl("68","员工密码修改","43");list.add(node);
	// node = new TreeNodeImpl("76","密码重置","2");list.add(node);
	// node = new TreeNodeImpl("77","员工密码重置","76");list.add(node);
	// node = new TreeNodeImpl("85","自定义菜单配置","2");list.add(node);
	// node = new TreeNodeImpl("4","报表","1");list.add(node);
	// node = new TreeNodeImpl("42","cltest","1");list.add(node);
	// node = new TreeNodeImpl("44","系统配置","1");list.add(node);
	// node = new TreeNodeImpl("45","状态配置","44");list.add(node);
	// node = new TreeNodeImpl("72","状态配置添加","45");list.add(node);
	// node = new TreeNodeImpl("73","状态配置删除","45");list.add(node);
	// node = new TreeNodeImpl("74","状态配置修改","45");list.add(node);
	// node = new TreeNodeImpl("46","数据权限类型配置","44");list.add(node);
	// node = new TreeNodeImpl("81","数据权限类型配置添加","46");list.add(node);
	// node = new TreeNodeImpl("82","数据权限类型配置删除","46");list.add(node);
	// node = new TreeNodeImpl("83","数据权限类型配置修改","46");list.add(node);
	// node = new TreeNodeImpl("47","数据权限对像配置","44");list.add(node);
	// node = new TreeNodeImpl("78","数据权限对像配置添加","47");list.add(node);
	// node = new TreeNodeImpl("79","数据权限对像配置删除","47");list.add(node);
	// node = new TreeNodeImpl("80","数据权限对像配置修改","47");list.add(node);
	// node = new TreeNodeImpl("49","主界面视图配置","44");list.add(node);
	// node = new TreeNodeImpl("69","主界面视图配置添加","49");list.add(node);
	// node = new TreeNodeImpl("70","主界面视图配置删除","49");list.add(node);
	// node = new TreeNodeImpl("71","主界面视图配置修改","49");list.add(node);
	// node = new TreeNodeImpl("100","添加部门扩展属性","44");list.add(node);
	// node = new TreeNodeImpl("101","添加人员扩展属性","44");list.add(node);
	//		
	// //记录上级结点的栈
	// List parentList = new ArrayList();
	// String parent=null,preNode=null;
	// String xml ="";
	// for(int i=0;i<list.size();i++){
	// node = list.get(i);
	// parent=null;
	// if(parentList.size() >0){ //从栈取得最后一个上级结点
	// parent=(String)parentList.get(parentList.size() -1);
	// }
	// if(parent!=null &&parent.equals( node.getF_Node())){
	// //如果与前一条同级,则输入上条结尾</tree>
	// xml+="</item>";
	// } else {
	// int iIndex = parentList.indexOf(node.getF_Node()); //是否已在栈中
	// if(iIndex>-1){ //如果在栈中,如果输入,则输入(iParentListSize-iIndex)条结尾</tree>
	// int iParentListSize=parentList.size();
	// for(int j=iParentListSize-1;j>=iIndex;j-- ){
	// xml+="</item>";
	// if(j<iParentListSize-1)
	// parentList.remove(parentList.size()-1); //删除栈中最后一个
	// }
	// }else {//如果不在栈中,则说明继续输入下一级结点,则增加些上级结点值到 栈中
	//					
	// if(preNode == null || node.getF_Node().equals(preNode))
	// parentList.add(node.getF_Node());
	// else {
	// for(int x=0;x<parentList.size();x++){
	// xml+="</item>";
	// }
	// parentList.clear();
	// parentList.add(node.getF_Node());
	// }
	//					
	// }
	// }
	// xml+="<item id=\""+node.getNode()+"\" text=\""+node.getText()+"\" > ";
	// preNode = node.getNode();
	// }
	// for(int i=0;i<parentList.size();i++){
	// xml+="</item>";
	// }
	// return xml;
	// }

}
