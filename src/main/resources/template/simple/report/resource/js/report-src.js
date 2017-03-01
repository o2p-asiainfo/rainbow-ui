try{
	document.getElementById("progress").style.display="none";
}catch(e){}
function closeIt(){
    try{
	    $("form input:button").each(function(i,o){o.disabled=true;});
	    document.getElementById("progress").style.display="";
    }catch(e){
        try{
	    	Ext.each( Ext.query('input[type="button"],button'),function(o){o.disabled=1;},this);
	    	document.getElementById("progress").style.display="";
	    }catch(e){}
    }
}
function closeProgress(){
	try{
		document.getElementById("progress").style.display="none";
	}catch(e){}
	try{
		$("form input:button").each(function(i,o){o.disabled=false;});
	}catch(e){
		try{
	    	Ext.each( Ext.query('input[type="button"],button'),function(o){o.disabled=0;},this);
	    }catch(e){}
	}
	 
}

var lastPageMinWidth;
var lastPageMinHeight;
function  toppage(extHeight,extWidth,minHeight,minWidth){ 
	if(extWidth != undefined && minWidth !=undefined){
	    try{
	    	if(parent.document.all(self.name).width>minWidth)
	    		parent.document.all(self.name).width=minWidth; 
	    }catch(e){
	    	try{if(parent.document.all(self.name).width>minWidth)
	    		parent.document.getElementById(self.name).width=minWidth; }catch(e){}   
	    }  
	 }
  var extH = 0;
  var extW = 0;
  if(extHeight != undefined)
  	extH = extHeight;
  
  if(extWidth != undefined)
  	extW = extWidth;
  	
  var height=document.body.scrollHeight +extH ;
  if(minHeight!= undefined){
  	lastPageMinHeight = minHeight;
  }else{
  	minHeight = lastPageMinHeight;
  }
  if(minHeight!= undefined&&height<minHeight)
	height = minHeight;
	
  var width=document.body.scrollWidth +extW;
  if(minWidth!= undefined){
  	lastPageMinWidth = minWidth;
  }else{
  	minWidth = lastPageMinWidth;
  }
  if(minWidth!= undefined && width<minWidth)
		width = minWidth;

//alert(width);
//alert("height:"+height+"width:"+width);
  if  (self.location!=top.location ){
    try{  
      if( parent.document.all(self.name) != null) {
        try{parent.document.all(self.name).height=height ; }catch(e){}  
        if(extWidth != undefined){
        	try{parent.document.all(self.name).width=width; }catch(e){}  
        }
      }
    }catch(e){
       if(parent.document.getElementById(self.name) != null) { 
    	 try{parent.document.getElementById(self.name).height=height ; }catch(e){}
    	 if(extWidth != undefined){
    	 	try{parent.document.getElementById(self.name).width=width; }catch(e){}   
    	 }
    	}
    }  
  }   
 // alert(parent.document.body.scrollWidth+" "+parent.document.getElementById(self.name).width);
} 

function showStatList(obj){
	document.getElementById('layer_stat').style.display='none';
	document.getElementById('layer_list').style.display='none';
	document.getElementById(obj).style.display='block';
}

/*
 * 改变统计记录
 */
function changeArea(sql_id,topic_node,path,defaultDimesValues){
	$.ajax({ 
		url:path+'/report/view/getLatStatXml.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date()+defaultDimesValues,
		error:function(json){
			  alert("获取维度信息失败!");
		},
		success: function(xml){
			var dim = document.getElementById('dimenSelect'); 				//维度下拉框
			var stat = document.getElementById('statSelect'); 				//统计指标下拉框
			dim.options.length = 0;							  				//清空维度定义下拉框
			stat.options.length = 0;						  				//清空统计指标下拉框
			var rowDefault = document.getElementById('rowDimenDiv');		//默认行维度
			var colDefault = document.getElementById('colDimenDiv');		//默认列维度
			rowDefault.innerHTML = '';										//将行默认维度清空
			colDefault.innerHTML = '';										//将列默认维度清空
			var rowOper = '';
			var colOper = '';
			var rowSum = '';
			var colSum = '';
			$(xml).find("lat").each(function(){
				var value=$(this).attr("key");   //获取返回的key
			  	var text=$(this).attr("text");   //获取返回的text
				
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#dimenSelect');
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==2){//操作默认行维度的值
					rowOper+= text+','+value+';';		//累加默认行维度信息
					rowSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==1){//操作默认列维度的值
					colOper+= text+','+value+';';		//累加默认列维度信息
					colSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}
			}); 
			//对默认行维度进行截取处理
			if(rowOper!=''){
				rowOper = rowOper.substring(0,rowOper.length-1) ;
				addLatDiv('dimenSelect','rowDimenDiv','olap.rowDimes',rowOper,path,2,rowSum);
				orderField = rowOper;
			}
			//对默认列维度进行截取处理
			if(colOper!=''){
				colOper = colOper.substring(0,colOper.length-1) ;
				addLatDiv('dimenSelect','colDimenDiv','olap.colDimes',colOper,path,4,colSum);
				groupField = colOper;
			}			
			
			var statOper = '';
			var statDiv = document.getElementById('statDiv');		//默认统计指标
			statDiv.innerHTML = '';									//清空默认统计指标
			
			$(xml).find("stat").each(function(){
				var value=$(this).attr("key");   //获取返回的key
			  	var text=$(this).attr("text");   //获取返回的text					
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#statSelect');
			  		stat.options.add(new Option(text,value));	
				}else{
					statOper+= text+','+value+';';		//累加统计指标信息
				}
			}); 
			
			//对默统计指标进行截取处理
			if(statOper!=''){
				statOper = statOper.substring(0,statOper.length-1) ;
				addStatDiv('statSelect','statDiv','olap.statItems',statOper,path,3);
				countField = statOper;
			}	
		}
	}); 	
}

/*
 * 改变清单项目
 */
function changeList(sql_id,topic_node,path){
	$.ajax({ 
		url:path+'/report/view/getSortCol.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date(),
		error:function(json){
			  alert("获取排序字段信息失败!");
		},
		success: function(xml){
			var dim = document.getElementById('orderSelect'); 				//未排序字段下拉框
		  	var sortOper = '';
		  	var sortSum = '';
				
			$(xml).find("sort").each(function(){
				var value=$(this).attr("key");   //获取返回的key
		  		var text=$(this).attr("text");   //获取返回的text
				if($(this).attr("type")==undefined){
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="asc"){
					sortOper+= text+','+value+';';		//累加默认升序字段信息
					sortSum+= 'selected,'+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="desc"){
					sortOper+= text+','+value+';';		//累加默认降序字段信息
					sortSum+= ',selected'+';';
				}
			});
			
			//对排序字段进行处理
			if(sortOper!=''){
				sortOper = sortOper.substring(0,sortOper.length-1) ;
				addSortDiv('orderSelect','orderDiv','orderByCols',sortOper,path,5,sortSum);
				sortField = sortOper;
			}	
		}
	}); 	
}

/*
 * id 可用字段容器ID
 * tid 放置div的div的id 字符串
 * hiddenName为隐藏表单域的name
 * str目标容器现有数据(格式如下：显示值,值;显示值,值)
 * contextPath 系统工程路径
 * type 1->明细字段 2->排序字段或行显示字段 3->汇总字段 4->分组字段或列显示字段
 * sum 是否合计格式如: ,checked;checked,;
 */
function addLatDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//将str转换为二维数组
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Sum' style='font-size:12px;'><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[0]+">合计</option><option value='' "+sum.split(';')[i].split(',')[1]+">不合计</option></select> <span style='display:none;'> <select name='order"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;' disabled><option value='asc'>升序</option><option value='desc'>降序</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id 可用字段容器ID
 * tid 放置div的div的id 字符串
 * hiddenName为隐藏表单域的name
 * str目标容器现有数据(格式如下：显示值,值;显示值,值)
 * contextPath 系统工程路径
 * type 1->明细字段 2->排序字段或行显示字段 3->汇总字段 4->分组字段或列显示字段
 */
function addStatDiv(id,tid,hiddenName,str,contextPath,type){
	//将str转换为二维数组
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='35'><input type='hidden' name="+hiddenName+" id='count"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td  align='center' style='font:small-caps 600 9pts/18pts 宋体;'> "+array[i][0]+"</td><td width='15' align='right'> <span style='display:none;'><select name='count"+(i+startStr)+"_"+(i+startStr)+"' style='font-size:12px;'><option value='sum'>求和</option><option value='max'>最大</option><option value='min'>最小</option><option value='avg'>平均</option><option value='count'>计数</option></select></span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id 可用字段容器ID
 * tid 放置div的div的id 字符串
 * hiddenName为隐藏表单域的name
 * str目标容器现有数据(格式如下：显示值,值;显示值,值)
 * contextPath 系统工程路径
 * type 1->明细字段 2->排序字段或行显示字段 3->汇总字段 4->分组字段或列显示字段
 * sum 升序倒序格式如: ,selected;selected,;
 */
function addSortDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//将str转换为二维数组
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Desc' style='font-size:12px;'><option value='' "+sum.split(';')[i].split(',')[0]+">升序</option><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[1]+">降序</option></select> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}
/*
 * 改变查询控件的iframe的连接地址
 * 1.节点id 2.sql查询类型 3.查询sql标识
 */
function changeInputIframe(path,topic_node,sql_type,topic_sql_id){
	inputIframe.location.href=path+"/report/view/toInputList.shtml?hashmap.topic_node="+topic_node+"&hashmap.sql_type="+sql_type+"&hashmap.topic_sql_id="+topic_sql_id;
}

/**
 * ajax级联操作对象组 
 * 源对象 目标对象 目标代码 路径
 */
function operateAjaxSelect(source,target,targetcode,path){
	$.ajax({ 
		url:path+'/rpcomm/rpAjaxSelect.shtml', 
		type:'post',
		dataType:'xml', 
		data:'hashmap.key='+encodeURI(encodeURI(source.value))+"&hashmap.code="+targetcode+'&date='+new Date(), 
		error:function(json){
		   	alert('级联失败!');
		},
		success: function(xml){
			target.options.length = 0;
			$(xml).find("result").each(function(){ 
				var key=$(this).attr("id"); 
				var text=$(this).text();
				target.options.add(new Option(text,key));
			});
		}
	}); 
}

/**
 * 调用ajax级联方法的接口
 */
function invokeAjaxSelect(source,target,targetcode,path){
	if(document.addEventListener){  // firefox  , w3c   
		document.getElementsByName(source)[0].addEventListener("change",function(){operateAjaxSelect(document.getElementsByName(source)[0],document.getElementsByName(target)[0],targetcode,path)},false);   
    } else {   // ie   
		document.getElementsByName(source)[0].attachEvent("onchange",function(){operateAjaxSelect(document.getElementsByName(source)[0],document.getElementsByName(target)[0],targetcode,path)});    
    }  
}


function css_sys_btn_setbg(btnObj){ 
	var sys_fn = "this.className='sys-btn-down';";
	btnObj.setAttribute('onmousedown',document.all ? function(){eval(sys_fn);} :sys_fn) ;
	var sys_fn1 = "this.className='sys-btn-over';";
	btnObj.setAttribute('onmouseover',document.all ? function(){eval(sys_fn1);} :sys_fn1) ;
	var sys_fn2 = "this.className='sys-btn';";
	btnObj.setAttribute('onmouseout',document.all ? function(){eval(sys_fn2);} :sys_fn2) ;
}

//查询区收缩功能
			
function clickShrinkButton(){
	img = document.getElementById("rp_shrink_button_img");
	if(img.shrinkState=="open"){
		closeQueryArea();
	}else{
		openQueryArea();
	}
}
var old_rp_query_submit_area_td_height=0;
function closeQueryArea(){
	var img = document.getElementById("rp_shrink_button_img");
	var div = document.getElementById("rp_index_title_div");
	document.getElementById("rp_query_area").style.display="none";
	document.getElementById("rp_query_submit_area").style.display="none";
	document.getElementById("rp_query_end_area").style.display="none";
	img.shrinkState="close";
	img.src="struts/simple/report/resource/images/shrink_bottom.gif";
	img.title="点击显示查询条件区";
	div.title="双击显示查询条件区";
	var td = document.getElementById("rp_query_submit_area_td");
	if(old_rp_query_submit_area_td_height==0)
		old_rp_query_submit_area_td_height = td.style.height;
	td.style.height=0;
}
function openQueryArea(){
	var img = document.getElementById("rp_shrink_button_img");
	var div = document.getElementById("rp_index_title_div");
	document.getElementById("rp_query_area").style.display="";
	document.getElementById("rp_query_submit_area").style.display="";
	document.getElementById("rp_query_end_area").style.display="";
	img.shrinkState="open";
	img.src="struts/simple/report/resource/images/shrink_top.gif";
	img.title="点击隐藏查询条件区";
	div.title="双击隐藏查询条件区";
	var td = document.getElementById("rp_query_submit_area_td");
	td.style.height=old_rp_query_submit_area_td_height;
}