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
 * \u6539\u53d8\u7edf\u8ba1\u8bb0\u5f55
 */
function changeArea(sql_id,topic_node,path,defaultDimesValues){
	$.ajax({ 
		url:path+'/report/view/getLatStatXml.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date()+defaultDimesValues,
		error:function(json){
			  alert("\u83b7\u53d6\u7ef4\u5ea6\u4fe1\u606f\u5931\u8d25!");
		},
		success: function(xml){
			var dim = document.getElementById('dimenSelect'); 				//\u7ef4\u5ea6\u4e0b\u62c9\u6846
			var stat = document.getElementById('statSelect'); 				//\u7edf\u8ba1\u6307\u6807\u4e0b\u62c9\u6846
			dim.options.length = 0;							  				//\u6e05\u7a7a\u7ef4\u5ea6\u5b9a\u4e49\u4e0b\u62c9\u6846
			stat.options.length = 0;						  				//\u6e05\u7a7a\u7edf\u8ba1\u6307\u6807\u4e0b\u62c9\u6846
			var rowDefault = document.getElementById('rowDimenDiv');		//\u9ed8\u8ba4\u884c\u7ef4\u5ea6
			var colDefault = document.getElementById('colDimenDiv');		//\u9ed8\u8ba4\u5217\u7ef4\u5ea6
			rowDefault.innerHTML = '';										//\u5c06\u884c\u9ed8\u8ba4\u7ef4\u5ea6\u6e05\u7a7a
			colDefault.innerHTML = '';										//\u5c06\u5217\u9ed8\u8ba4\u7ef4\u5ea6\u6e05\u7a7a
			var rowOper = '';
			var colOper = '';
			var rowSum = '';
			var colSum = '';
			$(xml).find("lat").each(function(){
				var value=$(this).attr("key");   //\u83b7\u53d6\u8fd4\u56de\u7684key
			  	var text=$(this).attr("text");   //\u83b7\u53d6\u8fd4\u56de\u7684text
				
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#dimenSelect');
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==2){//\u64cd\u4f5c\u9ed8\u8ba4\u884c\u7ef4\u5ea6\u7684\u503c
					rowOper+= text+','+value+';';		//\u7d2f\u52a0\u9ed8\u8ba4\u884c\u7ef4\u5ea6\u4fe1\u606f
					rowSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==1){//\u64cd\u4f5c\u9ed8\u8ba4\u5217\u7ef4\u5ea6\u7684\u503c
					colOper+= text+','+value+';';		//\u7d2f\u52a0\u9ed8\u8ba4\u5217\u7ef4\u5ea6\u4fe1\u606f
					colSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}
			}); 
			//\u5bf9\u9ed8\u8ba4\u884c\u7ef4\u5ea6\u8fdb\u884c\u622a\u53d6\u5904\u7406
			if(rowOper!=''){
				rowOper = rowOper.substring(0,rowOper.length-1) ;
				addLatDiv('dimenSelect','rowDimenDiv','olap.rowDimes',rowOper,path,2,rowSum);
				orderField = rowOper;
			}
			//\u5bf9\u9ed8\u8ba4\u5217\u7ef4\u5ea6\u8fdb\u884c\u622a\u53d6\u5904\u7406
			if(colOper!=''){
				colOper = colOper.substring(0,colOper.length-1) ;
				addLatDiv('dimenSelect','colDimenDiv','olap.colDimes',colOper,path,4,colSum);
				groupField = colOper;
			}			
			
			var statOper = '';
			var statDiv = document.getElementById('statDiv');		//\u9ed8\u8ba4\u7edf\u8ba1\u6307\u6807
			statDiv.innerHTML = '';									//\u6e05\u7a7a\u9ed8\u8ba4\u7edf\u8ba1\u6307\u6807
			
			$(xml).find("stat").each(function(){
				var value=$(this).attr("key");   //\u83b7\u53d6\u8fd4\u56de\u7684key
			  	var text=$(this).attr("text");   //\u83b7\u53d6\u8fd4\u56de\u7684text					
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#statSelect');
			  		stat.options.add(new Option(text,value));	
				}else{
					statOper+= text+','+value+';';		//\u7d2f\u52a0\u7edf\u8ba1\u6307\u6807\u4fe1\u606f
				}
			}); 
			
			//\u5bf9\u9ed8\u7edf\u8ba1\u6307\u6807\u8fdb\u884c\u622a\u53d6\u5904\u7406
			if(statOper!=''){
				statOper = statOper.substring(0,statOper.length-1) ;
				addStatDiv('statSelect','statDiv','olap.statItems',statOper,path,3);
				countField = statOper;
			}	
		}
	}); 	
}

/*
 * \u6539\u53d8\u6e05\u5355\u9879\u76ee
 */
function changeList(sql_id,topic_node,path){
	$.ajax({ 
		url:path+'/report/view/getSortCol.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date(),
		error:function(json){
			  alert("\u83b7\u53d6\u6392\u5e8f\u5b57\u6bb5\u4fe1\u606f\u5931\u8d25!");
		},
		success: function(xml){
			var dim = document.getElementById('orderSelect'); 				//\u672a\u6392\u5e8f\u5b57\u6bb5\u4e0b\u62c9\u6846
		  	var sortOper = '';
		  	var sortSum = '';
				
			$(xml).find("sort").each(function(){
				var value=$(this).attr("key");   //\u83b7\u53d6\u8fd4\u56de\u7684key
		  		var text=$(this).attr("text");   //\u83b7\u53d6\u8fd4\u56de\u7684text
				if($(this).attr("type")==undefined){
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="asc"){
					sortOper+= text+','+value+';';		//\u7d2f\u52a0\u9ed8\u8ba4\u5347\u5e8f\u5b57\u6bb5\u4fe1\u606f
					sortSum+= 'selected,'+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="desc"){
					sortOper+= text+','+value+';';		//\u7d2f\u52a0\u9ed8\u8ba4\u964d\u5e8f\u5b57\u6bb5\u4fe1\u606f
					sortSum+= ',selected'+';';
				}
			});
			
			//\u5bf9\u6392\u5e8f\u5b57\u6bb5\u8fdb\u884c\u5904\u7406
			if(sortOper!=''){
				sortOper = sortOper.substring(0,sortOper.length-1) ;
				addSortDiv('orderSelect','orderDiv','orderByCols',sortOper,path,5,sortSum);
				sortField = sortOper;
			}	
		}
	}); 	
}

/*
 * id \u53ef\u7528\u5b57\u6bb5\u5bb9\u5668ID
 * tid \u653e\u7f6ediv\u7684div\u7684id \u5b57\u7b26\u4e32
 * hiddenName\u4e3a\u9690\u85cf\u8868\u5355\u57df\u7684name
 * str\u76ee\u6807\u5bb9\u5668\u73b0\u6709\u6570\u636e(\u683c\u5f0f\u5982\u4e0b\uff1a\u663e\u793a\u503c,\u503c;\u663e\u793a\u503c,\u503c)
 * contextPath \u7cfb\u7edf\u5de5\u7a0b\u8def\u5f84
 * type 1->\u660e\u7ec6\u5b57\u6bb5 2->\u6392\u5e8f\u5b57\u6bb5\u6216\u884c\u663e\u793a\u5b57\u6bb5 3->\u6c47\u603b\u5b57\u6bb5 4->\u5206\u7ec4\u5b57\u6bb5\u6216\u5217\u663e\u793a\u5b57\u6bb5
 * sum \u662f\u5426\u5408\u8ba1\u683c\u5f0f\u5982: ,checked;checked,;
 */
function addLatDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//\u5c06str\u8f6c\u6362\u4e3a\u4e8c\u7ef4\u6570\u7ec4
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts \u5b8b\u4f53;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Sum' style='font-size:12px;'><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[0]+">Total</option><option value='' "+sum.split(';')[i].split(',')[1]+">Not Total</option></select> <span style='display:none;'> <select name='order"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;' disabled><option value='asc'>ASC</option><option value='desc'>DESC</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id \u53ef\u7528\u5b57\u6bb5\u5bb9\u5668ID
 * tid \u653e\u7f6ediv\u7684div\u7684id \u5b57\u7b26\u4e32
 * hiddenName\u4e3a\u9690\u85cf\u8868\u5355\u57df\u7684name
 * str\u76ee\u6807\u5bb9\u5668\u73b0\u6709\u6570\u636e(\u683c\u5f0f\u5982\u4e0b\uff1a\u663e\u793a\u503c,\u503c;\u663e\u793a\u503c,\u503c)
 * contextPath \u7cfb\u7edf\u5de5\u7a0b\u8def\u5f84
 * type 1->\u660e\u7ec6\u5b57\u6bb5 2->\u6392\u5e8f\u5b57\u6bb5\u6216\u884c\u663e\u793a\u5b57\u6bb5 3->\u6c47\u603b\u5b57\u6bb5 4->\u5206\u7ec4\u5b57\u6bb5\u6216\u5217\u663e\u793a\u5b57\u6bb5
 */
function addStatDiv(id,tid,hiddenName,str,contextPath,type){
	//\u5c06str\u8f6c\u6362\u4e3a\u4e8c\u7ef4\u6570\u7ec4
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='35'><input type='hidden' name="+hiddenName+" id='count"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td  align='center' style='font:small-caps 600 9pts/18pts \u5b8b\u4f53;'> "+array[i][0]+"</td><td width='15' align='right'> <span style='display:none;'><select name='count"+(i+startStr)+"_"+(i+startStr)+"' style='font-size:12px;'><option value='sum'>\u6c42\u548c</option><option value='max'>\u6700\u5927</option><option value='min'>\u6700\u5c0f</option><option value='avg'>\u5e73\u5747</option><option value='count'>\u8ba1\u6570</option></select></span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id \u53ef\u7528\u5b57\u6bb5\u5bb9\u5668ID
 * tid \u653e\u7f6ediv\u7684div\u7684id \u5b57\u7b26\u4e32
 * hiddenName\u4e3a\u9690\u85cf\u8868\u5355\u57df\u7684name
 * str\u76ee\u6807\u5bb9\u5668\u73b0\u6709\u6570\u636e(\u683c\u5f0f\u5982\u4e0b\uff1a\u663e\u793a\u503c,\u503c;\u663e\u793a\u503c,\u503c)
 * contextPath \u7cfb\u7edf\u5de5\u7a0b\u8def\u5f84
 * type 1->\u660e\u7ec6\u5b57\u6bb5 2->\u6392\u5e8f\u5b57\u6bb5\u6216\u884c\u663e\u793a\u5b57\u6bb5 3->\u6c47\u603b\u5b57\u6bb5 4->\u5206\u7ec4\u5b57\u6bb5\u6216\u5217\u663e\u793a\u5b57\u6bb5
 * sum \u5347\u5e8f\u5012\u5e8f\u683c\u5f0f\u5982: ,selected;selected,;
 */
function addSortDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//\u5c06str\u8f6c\u6362\u4e3a\u4e8c\u7ef4\u6570\u7ec4
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts \u5b8b\u4f53;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Desc' style='font-size:12px;'><option value='' "+sum.split(';')[i].split(',')[0]+">ASC</option><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[1]+">DESC</option></select> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}
/*
 * \u6539\u53d8\u67e5\u8be2\u63a7\u4ef6\u7684iframe\u7684\u8fde\u63a5\u5730\u5740
 * 1.\u8282\u70b9id 2.sql\u67e5\u8be2\u7c7b\u578b 3.\u67e5\u8be2sql\u6807\u8bc6
 */
function changeInputIframe(path,topic_node,sql_type,topic_sql_id){
	inputIframe.location.href=path+"/report/view/toInputList.shtml?hashmap.topic_node="+topic_node+"&hashmap.sql_type="+sql_type+"&hashmap.topic_sql_id="+topic_sql_id;
}

/**
 * ajax\u7ea7\u8054\u64cd\u4f5c\u5bf9\u8c61\u7ec4 
 * \u6e90\u5bf9\u8c61 \u76ee\u6807\u5bf9\u8c61 \u76ee\u6807\u4ee3\u7801 \u8def\u5f84
 */
function operateAjaxSelect(source,target,targetcode,path){
	$.ajax({ 
		url:path+'/rpcomm/rpAjaxSelect.shtml', 
		type:'post',
		dataType:'xml', 
		data:'hashmap.key='+encodeURI(encodeURI(source.value))+"&hashmap.code="+targetcode+'&date='+new Date(), 
		error:function(json){
		   	alert('\u7ea7\u8054\u5931\u8d25!');
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
 * \u8c03\u7528ajax\u7ea7\u8054\u65b9\u6cd5\u7684\u63a5\u53e3
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

//\u67e5\u8be2\u533a\u6536\u7f29\u529f\u80fd
			
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
	img.title="\u70b9\u51fb\u663e\u793a\u67e5\u8be2\u6761\u4ef6\u533a";
	div.title="\u53cc\u51fb\u663e\u793a\u67e5\u8be2\u6761\u4ef6\u533a";
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
	img.title="\u70b9\u51fb\u9690\u85cf\u67e5\u8be2\u6761\u4ef6\u533a";
	div.title="\u53cc\u51fb\u9690\u85cf\u67e5\u8be2\u6761\u4ef6\u533a";
	var td = document.getElementById("rp_query_submit_area_td");
	td.style.height=old_rp_query_submit_area_td_height;
}
