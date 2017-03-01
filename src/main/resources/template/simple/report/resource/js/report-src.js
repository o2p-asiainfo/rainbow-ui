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
 * �ı�ͳ�Ƽ�¼
 */
function changeArea(sql_id,topic_node,path,defaultDimesValues){
	$.ajax({ 
		url:path+'/report/view/getLatStatXml.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date()+defaultDimesValues,
		error:function(json){
			  alert("��ȡά����Ϣʧ��!");
		},
		success: function(xml){
			var dim = document.getElementById('dimenSelect'); 				//ά��������
			var stat = document.getElementById('statSelect'); 				//ͳ��ָ��������
			dim.options.length = 0;							  				//���ά�ȶ���������
			stat.options.length = 0;						  				//���ͳ��ָ��������
			var rowDefault = document.getElementById('rowDimenDiv');		//Ĭ����ά��
			var colDefault = document.getElementById('colDimenDiv');		//Ĭ����ά��
			rowDefault.innerHTML = '';										//����Ĭ��ά�����
			colDefault.innerHTML = '';										//����Ĭ��ά�����
			var rowOper = '';
			var colOper = '';
			var rowSum = '';
			var colSum = '';
			$(xml).find("lat").each(function(){
				var value=$(this).attr("key");   //��ȡ���ص�key
			  	var text=$(this).attr("text");   //��ȡ���ص�text
				
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#dimenSelect');
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==2){//����Ĭ����ά�ȵ�ֵ
					rowOper+= text+','+value+';';		//�ۼ�Ĭ����ά����Ϣ
					rowSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")==1){//����Ĭ����ά�ȵ�ֵ
					colOper+= text+','+value+';';		//�ۼ�Ĭ����ά����Ϣ
					colSum+= ($(this).attr("sum")==0?'selected,':',selected')+';';
				}
			}); 
			//��Ĭ����ά�Ƚ��н�ȡ����
			if(rowOper!=''){
				rowOper = rowOper.substring(0,rowOper.length-1) ;
				addLatDiv('dimenSelect','rowDimenDiv','olap.rowDimes',rowOper,path,2,rowSum);
				orderField = rowOper;
			}
			//��Ĭ����ά�Ƚ��н�ȡ����
			if(colOper!=''){
				colOper = colOper.substring(0,colOper.length-1) ;
				addLatDiv('dimenSelect','colDimenDiv','olap.colDimes',colOper,path,4,colSum);
				groupField = colOper;
			}			
			
			var statOper = '';
			var statDiv = document.getElementById('statDiv');		//Ĭ��ͳ��ָ��
			statDiv.innerHTML = '';									//���Ĭ��ͳ��ָ��
			
			$(xml).find("stat").each(function(){
				var value=$(this).attr("key");   //��ȡ���ص�key
			  	var text=$(this).attr("text");   //��ȡ���ص�text					
				if($(this).attr("type")!=undefined&&$(this).attr("type")==0){
					//$('<option value='+value+'>'+text+'</option>').appendTo('#statSelect');
			  		stat.options.add(new Option(text,value));	
				}else{
					statOper+= text+','+value+';';		//�ۼ�ͳ��ָ����Ϣ
				}
			}); 
			
			//��Ĭͳ��ָ����н�ȡ����
			if(statOper!=''){
				statOper = statOper.substring(0,statOper.length-1) ;
				addStatDiv('statSelect','statDiv','olap.statItems',statOper,path,3);
				countField = statOper;
			}	
		}
	}); 	
}

/*
 * �ı��嵥��Ŀ
 */
function changeList(sql_id,topic_node,path){
	$.ajax({ 
		url:path+'/report/view/getSortCol.shtml', 
		type:'post',
		dataType:'xml',
		data:'hashmap.topic_sql_id='+sql_id+'&hashmap.topic_node='+topic_node+'&date='+new Date(),
		error:function(json){
			  alert("��ȡ�����ֶ���Ϣʧ��!");
		},
		success: function(xml){
			var dim = document.getElementById('orderSelect'); 				//δ�����ֶ�������
		  	var sortOper = '';
		  	var sortSum = '';
				
			$(xml).find("sort").each(function(){
				var value=$(this).attr("key");   //��ȡ���ص�key
		  		var text=$(this).attr("text");   //��ȡ���ص�text
				if($(this).attr("type")==undefined){
			  		dim.options.add(new Option(text,value));	
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="asc"){
					sortOper+= text+','+value+';';		//�ۼ�Ĭ�������ֶ���Ϣ
					sortSum+= 'selected,'+';';
				}else if($(this).attr("type")!=undefined&&$(this).attr("type")=="desc"){
					sortOper+= text+','+value+';';		//�ۼ�Ĭ�Ͻ����ֶ���Ϣ
					sortSum+= ',selected'+';';
				}
			});
			
			//�������ֶν��д���
			if(sortOper!=''){
				sortOper = sortOper.substring(0,sortOper.length-1) ;
				addSortDiv('orderSelect','orderDiv','orderByCols',sortOper,path,5,sortSum);
				sortField = sortOper;
			}	
		}
	}); 	
}

/*
 * id �����ֶ�����ID
 * tid ����div��div��id �ַ���
 * hiddenNameΪ���ر����name
 * strĿ��������������(��ʽ���£���ʾֵ,ֵ;��ʾֵ,ֵ)
 * contextPath ϵͳ����·��
 * type 1->��ϸ�ֶ� 2->�����ֶλ�����ʾ�ֶ� 3->�����ֶ� 4->�����ֶλ�����ʾ�ֶ�
 * sum �Ƿ�ϼƸ�ʽ��: ,checked;checked,;
 */
function addLatDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//��strת��Ϊ��ά����
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts ����;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Sum' style='font-size:12px;'><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[0]+">�ϼ�</option><option value='' "+sum.split(';')[i].split(',')[1]+">���ϼ�</option></select> <span style='display:none;'> <select name='order"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;' disabled><option value='asc'>����</option><option value='desc'>����</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id �����ֶ�����ID
 * tid ����div��div��id �ַ���
 * hiddenNameΪ���ر����name
 * strĿ��������������(��ʽ���£���ʾֵ,ֵ;��ʾֵ,ֵ)
 * contextPath ϵͳ����·��
 * type 1->��ϸ�ֶ� 2->�����ֶλ�����ʾ�ֶ� 3->�����ֶ� 4->�����ֶλ�����ʾ�ֶ�
 */
function addStatDiv(id,tid,hiddenName,str,contextPath,type){
	//��strת��Ϊ��ά����
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='35'><input type='hidden' name="+hiddenName+" id='count"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td  align='center' style='font:small-caps 600 9pts/18pts ����;'> "+array[i][0]+"</td><td width='15' align='right'> <span style='display:none;'><select name='count"+(i+startStr)+"_"+(i+startStr)+"' style='font-size:12px;'><option value='sum'>���</option><option value='max'>���</option><option value='min'>��С</option><option value='avg'>ƽ��</option><option value='count'>����</option></select></span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}

/*
 * id �����ֶ�����ID
 * tid ����div��div��id �ַ���
 * hiddenNameΪ���ر����name
 * strĿ��������������(��ʽ���£���ʾֵ,ֵ;��ʾֵ,ֵ)
 * contextPath ϵͳ����·��
 * type 1->��ϸ�ֶ� 2->�����ֶλ�����ʾ�ֶ� 3->�����ֶ� 4->�����ֶλ�����ʾ�ֶ�
 * sum �������ʽ��: ,selected;selected,;
 */
function addSortDiv(id,tid,hiddenName,str,contextPath,type,sum){
	//��strת��Ϊ��ά����
	var array = toArray(str) ;
	var startStr = array.length ;
	var target = document.getElementById(tid);
	var s = '';
	for(var i=0;i<startStr;i++){
		s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+array[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+i+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+i+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts ����;'> "+array[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Desc' style='font-size:12px;'><option value='' "+sum.split(';')[i].split(',')[0]+">����</option><option value='"+array[i][1]+"' "+sum.split(';')[i].split(',')[1]+">����</option></select> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+i+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	}
	target.innerHTML = s;
}
/*
 * �ı��ѯ�ؼ���iframe�����ӵ�ַ
 * 1.�ڵ�id 2.sql��ѯ���� 3.��ѯsql��ʶ
 */
function changeInputIframe(path,topic_node,sql_type,topic_sql_id){
	inputIframe.location.href=path+"/report/view/toInputList.shtml?hashmap.topic_node="+topic_node+"&hashmap.sql_type="+sql_type+"&hashmap.topic_sql_id="+topic_sql_id;
}

/**
 * ajax�������������� 
 * Դ���� Ŀ����� Ŀ����� ·��
 */
function operateAjaxSelect(source,target,targetcode,path){
	$.ajax({ 
		url:path+'/rpcomm/rpAjaxSelect.shtml', 
		type:'post',
		dataType:'xml', 
		data:'hashmap.key='+encodeURI(encodeURI(source.value))+"&hashmap.code="+targetcode+'&date='+new Date(), 
		error:function(json){
		   	alert('����ʧ��!');
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
 * ����ajax���������Ľӿ�
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

//��ѯ����������
			
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
	img.title="�����ʾ��ѯ������";
	div.title="˫����ʾ��ѯ������";
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
	img.title="������ز�ѯ������";
	div.title="˫�����ز�ѯ������";
	var td = document.getElementById("rp_query_submit_area_td");
	td.style.height=old_rp_query_submit_area_td_height;
}