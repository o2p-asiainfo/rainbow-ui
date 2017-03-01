var reportContextPath;
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


//????????????
function  fr_tropen_unite_paras(reportid,url){  
  var new_paras = url;
  if(url.indexOf('&')!=0)
  	url = "&"+url;

  var inputs =document.getElementById(reportid+"_form").elements;
  //alert(inputs);
  for(var i=0;i<inputs.length;i++){
	//alert(inputs[i].name + "="+inputs[i].value)
	if(url.indexOf('&'+inputs[i].name+'=')==-1 && inputs[i].name != 'sheets_name'){
		new_paras += "&"+inputs[i].name + "="+inputs[i].value;
	}
		
  }
  //alert(new_paras);
  return new_paras;
} 


var req;
function fr_execAjax(callback, url) {
	// branch for native XMLHttpRequest object
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		req.onreadystatechange = callback;
		req.open("GET", url, true);
		req.send(null);
	} // branch for IE/Windows ActiveX version
	else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	if (req) {
			req.onreadystatechange = callback;
			req.open("GET", url, true);
			req.send();
		}
	}
}
function fr_callback() {
	// only if req shows "loaded"
	if (req.readyState == 4) {
		// only if "OK"
		if (req.status == 200) {
			fr_tropen_callback(req.responseText);
		} else {
			alert("There was a problem retrieving the XML data:\n" +
			req.statusText);
		}
	}
} 

var openreportid;
var opentrid ;
//??????????????????????????????????.
function fr_tropen_callback(text){
  //????????????????XML????????
  //alert(text);
  if(text != ""){
	  var xml =new ActiveXObject("Microsoft.XMLDOM");
	  xml.async=false;
	  xml.loadXML(text);
	  if (xml.parseError.errorCode!=0) {
	         alert("Load XML:"+xml.parseError.reason);
	         return;
	  }
	
	  
	  //??????????
	  var paraRow = document.getElementById(opentrid);
	  var newRowLevel = new Number(paraRow.level)+1;
	
	  var trs = xml.getElementsByTagName("tr");
	  //alert(paraRow.cells[0].rowSpan);
	  var rowIndex = paraRow.rowIndex+paraRow.cells[0].rowSpan;
	  //alert("paraRow.rowIndex:"+rowIndex);
	  //alert("trs.length:"+trs.length + " "+openreportid);
	  for(var i=0;i<trs.length;i++){
		//alert(trs[i].getAttribute("id"));
		var tab = paraRow.parentElement;
		var newRow= tab.insertRow(rowIndex+i);
		//alert("newRow.id:"+newRow.id);
		newRow.level= newRowLevel;
	    //newRow.id=opentrid+'_'+i;
		newRow.id =trs[i].getAttribute("id");// paraRow.id + '_'+i;//
	    var tds = trs[i].childNodes;
	    for(var j=0;j<tds.length;j++){
	       var newCol=newRow.insertCell();
		   newCol.id=newRow.id+'_td'+j;
		   var imghtm = "";
		   if(j == 0){
			   //alert("newRowLevel:"+newRowLevel);
			   for(var k=1;k<newRowLevel;k++)
				 imghtm += "<img src=\""+reportContextPath+"/struts/simple/report/resource/images/tree_line.gif\" >";
		   }
		   
		   if(tds[j].getAttribute("colspan") )
		     newCol.colSpan = tds[j].getAttribute("colspan");
		   if(tds[j].getAttribute("rowspan"))
		     newCol.rowSpan = tds[j].getAttribute("rowspan");
		   if(tds[j].getAttribute("style") )
		     newCol.style.cssText =tds[j].getAttribute("style");
		     //newCol.sStyle = tds[j].getAttribute("style");
		   if(tds[j].getAttribute("class"))
		     newCol.className = tds[j].getAttribute("class");
		   if(tds[j].getAttribute("nowrap") )
		     newCol.noWrap = tds[j].getAttribute("nowrap");
	
	       //newCol.innerHTML = imghtm + tds[j].text;
			setInnerHTML(newCol,imghtm + tds[j].text);
	       //alert(tds[j].getAttribute("id") + " " +tds[j].text);
		}
	    //alert("newLevel:"+newRow.level);
	  }
	  paraRow.isload= 'true';
	  fr_close_load_cue();
	  toppage();
  }
}

/**
 * ??????????????
 */
function fr_tropen(path,reportid,url,trid,detailPath){
  //??????????
  var paraRow = document.getElementById(trid);
  //alert("trid:"+trid ); 
  //alert("paraRow.id"+paraRow.id);
  //????????????????
  if(paraRow.isload== 'true'){
    //var tab = document.all[openreportid+'_tab'];
    var tab = paraRow.parentElement;
	var trs = tab.rows;
    var length = trs.length;
	var newRowLevel = new Number(paraRow.level)+1;
	var rowIndex = paraRow.rowIndex+paraRow.cells[0].rowSpan;
	for(var i= rowIndex ;i<length;i++){
		if(trs[i].id.indexOf(trid+'_') == 0 ){
			if(trs[i].level==newRowLevel){
				trs[i].style.display="";
				var tds = trs[i].cells;
				for(var j= 0 ;j<tds.length;j++){
					tds[j].style.display="";			
				}
			}
		} else {
			break;
		}
	}
	fr_close_load_cue();
	toppage();
  } else {
	  //????AJAX????????????
	  openreportid = reportid;
	  opentrid = trid;
	  //var url = path+"/fixedreport/fixedreport.jsp?parent_tr_id="+trid+"&"+fr_tropen_unite_paras(reportid,url);
	  //var url = detailPath+"?parent_tr_id="+trid+"&"+fr_tropen_unite_paras(reportid,url);
	  var url = document.location.href+"?parent_tr_id="+trid+"&"+fr_tropen_unite_paras(reportid,url);
	  //url="/rp/demo/a.htm";
	  //alert("url:"+url);
	  
	  //var aElement=document.createElement("<input type=text name=\"mytesturl\" value=\""+url+"\">"); 
	  //document.all[reportid+'_span'].appendChild(aElement); 
	  var callback = fr_callback;
	  fr_execAjax(callback, url);
  }
}

/**
 * ??????????????
 */
function fr_trclose(path,reportid,url,trid){
	var paraRow = document.getElementById(trid);
	var tab = paraRow.parentElement;
    //var tab = document.all[openreportid+'_tab'];
	var trs = tab.rows;
   // var paraRow = document.all[trid];
    var length = trs.length;
    var rowIndex = paraRow.rowIndex+paraRow.cells[0].rowSpan;
	for(var i= rowIndex ;i<length;i++){
		if(trs[i].id.indexOf(trid+'_') == 0 ){
			trs[i].style.display="none";
			var img = document.getElementById(trs[i].id+'_img');
			//alert(trs[i].id+'_img ' + img);
			if(img && img.state && img.state != '') {
				img.state = "close";
    			img.src=path+"/struts/simple/report/resource/images/tree_close.gif";
			}
			var tds = trs[i].cells;
			for(var j= 0 ;j<tds.length;j++){
				tds[j].style.display="none";			
			}
		} else {
			break;
		}
	}
	 //document.body.style.zoom='1.1';  //document.body.style.zoom=1; 
	 //alert('abc');
}
//unite_paras('fixedreport_form','area_id=59200&layer_id=2');
//fr_tropen('tr2','fixedreport_form','area_id=59200&layer_id=2');


//????????????
function fr_show_load_cue(el){
	if( !document.getElementById('fr_show_load_span')) {
		var offsetPos = getAbsolutePos(el);
		
		var span ="<span id=fr_show_load_span style=\"POSITION: absolute ;visibility: visible;left: "+(offsetPos.x+20)+";top: "+(offsetPos.y+20)+"; background: FFFFE1; border:1px; border-style: solid; font-size: 9pt; \"></span>";
		//alert(span);
		var aElement=document.createElement(span);
	    document.body.appendChild(aElement);
	    document.getElementById('fr_show_load_span').innerHTML="请稍后,处理中...";
	} else {
		var span = document.getElementById('fr_show_load_span');
		span.style.visibility = "visible";
		var offsetPos = getAbsolutePos(el);
		span.style.left = offsetPos.x+20;
		span.style.top = offsetPos.y+20;
		
	}
}
//????????????
function fr_close_load_cue(){
   if(document.getElementById('fr_show_load_span')) {
		var span = document.getElementById('fr_show_load_span');
		span.style.visibility = "hidden";
		span.style.left = 0;
		span.style.top = 0;
   }
}

//????????1,????????????????????????????.
function fr_drill_1(path,reportid,url,trid,img,detailPath) {
  reportContextPath = path;  
  if (img.state=="close") {
  	fr_show_load_cue(img);
    fr_tropen(path,reportid,url,trid,detailPath);
    img.state = "open";
    img.src=path+"/struts/simple/report/resource/images/tree_open.gif";
    
  }
  else {
    fr_trclose(path,reportid,url,trid);
    img.state = "close";
    img.src=path+"/struts/simple/report/resource/images/tree_close.gif";
    toppage();
  }
  
}


function  rp_drill(path,reportid,url){ 
	fr_drill_2(path,reportid,url);
}

//????????2,????????????????????
function  fr_drill_2(path,reportid,url){ 
	reportContextPath = path;  
  var paras = url.split('&'); 
  var page_action ="";// path+"/struts/simple/report/resource/images/fixedreport.jsp";
  var page_target = "";
  for(var i=0;i<paras.length;i++){  
    var idx = paras[i].indexOf('='); 
    if(idx>0){ 
       var paraName = paras[i].substr(0,idx); 
       var paraValue = paras[i].substr(idx+1); 
       var inputObj = document.getElementById(reportid+"_"+paraName.replace(".","_")); //document.all[reportid+'_form'].all[paraName]; 
       if(inputObj){ 
         if(inputObj.length){ 
           var length = inputObj.length; 
           for(var j=0;j<length;j++){ 
             document.getElementById(reportid+'_span').removeChild(inputObj[0]);
           } 
         } 
	      else {
           document.getElementById(reportid+'_span').removeChild(inputObj);
         } 
       } 
       if(paraName.toLowerCase() == 'action'){
       	  page_action = paraValue;
       }else if(paraName.toLowerCase() == 'target'){
       	  page_target = paraValue;
       } else {
	       var aElement=document.createElement("<input type=hidden id=\""+reportid+"_"+paraName.replace(".","_")+"\" name=\""+paraName+"\" value=\""+paraValue+"\">"); 
	       document.getElementById(reportid+'_span').appendChild(aElement); 
       }
    }	 
  } 

  document.getElementById(reportid+'_form').action=page_action; 
  document.getElementById(reportid+'_form').target=page_target; 
  document.getElementById(reportid+'_form').submit();
} 
/**
动态报表清单钻取,get提交,暂不用,改为post提交
*/
function  rp_drill_detail_get(path,reportid,urlpara,page_action,page_target){ 

  reportContextPath = path;  
  var paras = urlpara.split('&'); 
  //var page_action ="";// path+"/struts/simple/report/resource/images/fixedreport.jsp";
  //var page_target = "";
  if(!page_action)
  	page_action="";
  if(!page_target)
  	page_target="_blank";

  var inputs = document.getElementById(reportid+'_span').children;
  for(var i=0;i<inputs.length;i++){ 
     if(inputs[i].name !="scrolling" && inputs[i].name.lastIndexOf(".scrolling") ==-1 &&inputs[i].name !="detailURL"){
	     if(urlpara.indexOf('&'+inputs[i].name+"=")==-1){
	     	urlpara =urlpara+'&'+inputs[i].name+"="+inputs[i].value;
	     }
     }
  }
  if(page_action.indexOf("?")>-1){
   	page_action = page_action+urlpara;
  }else{
  	page_action = page_action+"?"+urlpara;
  }

  if(page_target=="_self"){
  	self.location.href=page_action;
  }else{
  	 window.open(page_action,page_target);
  }
  try{parent.closeProgress();}catch(e){}
  //"width=800,height=650,toolbar=no,left=300,top=50,toolbar=no,left=300,top=50,toolbar=no,location=no,directories=no,status=not,menubar=no,scrollbars=yes,resizable=yes"
} 

/**
动态报表清单钻取,post提交
*/
function  rp_drill_detail(path,reportid,urlpara,page_action,page_target){ 
  if(!page_action)
  	page_action="";
  if(!page_target)
  	page_target="_blank";
  
  //生成钻取清单时临时表单
  var detailForm=document.createElement("<form method=POST name=\"olap_drill_detail_tmp_form\" id=\"olap_drill_detail_tmp_form\" action=\"\" target=\"_blank\" style=\"margin: 0px\"></form>");
  document.body.appendChild(detailForm);
  detailForm.innerHTML = document.getElementById(reportid+'_span').innerHTML;
  
  var paras = urlpara.split('&');//钻取参数
  for(var i=0;i<paras.length;i++){  
    var idx = paras[i].indexOf('='); 
    if(idx>0){ 
       var paraName = paras[i].substr(0,idx); 
       var paraValue = paras[i].substr(idx+1); 
       if(paraName.toLowerCase() == 'action'){
       	  page_action = paraValue;
       }else if(paraName.toLowerCase() == 'target'){
       	  page_target = paraValue;
       } else {
       	   //清除原有同名隐含域
       	   removeChildByName(detailForm,paraName);
       	   //建立隐含域
       	   var aHiddenElement=document.createElement("<input type=hidden id=\""+reportid+"_"+paraName.replace(".","_")+"\" name=\""+paraName+"\" value=\""+paraValue+"\">"); 
	       	 detailForm.appendChild(aHiddenElement); 
       }
    }	 
  } 
  detailForm.action=page_action; 
  detailForm.target=page_target; 
  detailForm.submit();
  //清除临时表单
  document.body.removeChild(detailForm);
  try{parent.closeProgress();}catch(e){}
} 

//olap操作时,按位置钻取
function olap_drill_open(reportid,member){

    var aElement = document.getElementById(reportid+"_olap_drill_members_"+member.replace(/\./g, "_").replace(/\[|\]/g,""));
    if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_drill_members_"+member.replace(/\./g, "_").replace(/\[|\]/g,"")+"\" name='olap.drill_members' value=\""+member+"\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}
	var page_action ="";
    var page_target = "";
    //alert(abc.abc.abc);
    document.getElementById(reportid+'_form').action=page_action; 
    document.getElementById(reportid+'_form').target=page_target; 
    document.getElementById(reportid+'_form').submit();
}
//olap操作时,按位置上卷
function olap_drill_close(reportid,member){

	//var aElement = document.getElementById(reportid+"_olap_drill_members_"+member.replace(/\./g, "_").replace(/\[|\]/g,""));
    //document.getElementById(reportid+'_span').removeChild(aElement);
    //取得所有隐含域
    var oColl =document.getElementById(reportid+'_span').children;
    for(x=0;x<oColl.length;x++){
    	var aElement =oColl.item(x);
    	if(aElement.name=="olap.drill_members"){
    		//alert(aElement+"\n"+aElement.name+":"+aElement.value+":"+aElement.value.indexOf(member));
    		if(aElement.value.indexOf(member)==0){//删除本级及下级钻取成员
    			document.getElementById(reportid+'_span').removeChild(aElement);
    			x--;
    		}
    	}
    	//alert(oColl.item(x).name);
    }
    
    
   	var page_action ="";
    var page_target = "";
    document.getElementById(reportid+'_form').action=page_action; 
    document.getElementById(reportid+'_form').target=page_target; 
    document.getElementById(reportid+'_form').submit();
}

/**
OLAP钻取至清单,get提交,暂不用,改为post提交方法
*/
function  olap_drill_detail_get(reportid,urlpara,page_action,page_target){ 
  if(!page_action)
  	page_action="";
  if(!page_target)
  	page_target="_blank";
  
  var inputs = document.getElementById(reportid+'_span').children;
  for(var i=0;i<inputs.length;i++){ 
     if(inputs[i].name !="scrolling" && inputs[i].name.lastIndexOf(".scrolling") ==-1 &&inputs[i].name !="detailURL"){
	     if(urlpara.indexOf('&'+inputs[i].name+"=")==-1){
	     	urlpara =urlpara+'&'+inputs[i].name+"="+inputs[i].value;
	     }
     }
  }
  
  if(page_action.indexOf("?")>-1){
   	page_action = page_action+urlpara;
  }else{
  	page_action = page_action+"?"+urlpara;
  }

  if(page_target=="_self"){
  	self.location.href=page_action;
  }else{
  	 window.open(page_action,page_target,"width=800,height=650,toolbar=no,left=300,top=50,toolbar=no,left=300,top=50,toolbar=no,location=no,directories=no,status=not,menubar=no,scrollbars=yes,resizable=yes");
  }
  try{parent.closeProgress();}catch(e){}
  //"width=800,height=650,toolbar=no,left=300,top=50,toolbar=no,left=300,top=50,toolbar=no,location=no,directories=no,status=not,menubar=no,scrollbars=yes,resizable=yes"
} 

/**
清除某一对象下的指字名字的对象
**/
function removeChildByName(aElement,inputName){
	var childElements = aElement.children;
	for(var j=0;j<childElements.length;j++){ 
     	if(childElements[j].name==inputName){
		     aElement.removeChild(childElements[j]);
		     j--;
    	}
	} 
}

/**
OLAP钻取至清单
*/
function  olap_drill_detail(reportid,urlpara,page_action,page_target){ 
  if(!page_action)
  	page_action="";
  if(!page_target)
  	page_target="_blank";
  
  //生成钻取清单时临时表单
  var detailForm=document.createElement("<form method=POST name=\"olap_drill_detail_tmp_form\" id=\"olap_drill_detail_tmp_form\" action=\"\" target=\"_blank\" style=\"margin: 0px\"></form>");
  document.body.appendChild(detailForm);
  detailForm.innerHTML = document.getElementById(reportid+'_span').innerHTML;
  
  var paras = urlpara.split('&');//钻取参数
  for(var i=0;i<paras.length;i++){  
    var idx = paras[i].indexOf('='); 
    if(idx>0){ 
       var paraName = paras[i].substr(0,idx); 
       var paraValue = paras[i].substr(idx+1); 
       if(paraName.toLowerCase() == 'action'){
       	  page_action = paraValue;
       }else if(paraName.toLowerCase() == 'target'){
       	  page_target = paraValue;
       } else {
       	   //清除原有同名隐含域
       	   removeChildByName(detailForm,paraName);
       	   //建立隐含域
       	   var aHiddenElement=document.createElement("<input type=hidden id=\""+reportid+"_"+paraName.replace(".","_")+"\" name=\""+paraName+"\" value=\""+paraValue+"\">"); 
	       	 detailForm.appendChild(aHiddenElement); 
       }
    }	 
  } 
  detailForm.action=page_action; 
  detailForm.target=page_target; 
  detailForm.submit();
  //清除临时表单
  document.body.removeChild(detailForm);
  try{parent.closeProgress();}catch(e){}
} 


/*
* ???????????????????? innerHTML ????
*       ?????????? HTML ?????????? script ?? style
* ??????kenxu <kenxu at ajaxwing dot com>
* ??????2006-09-01
* ??????
*    el: ?????? DOM ??????????
*    htmlCode: ?????? HTML ????
* ????????????????ie5+, firefox1.5+, opera8.5+
*/
 var  setInnerHTML = function (el, htmlCode) {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf('msie') >= 0 && ua.indexOf('opera') < 0) {
        htmlCode = '<div style="display:none">for IE</div>' + htmlCode;
        htmlCode = htmlCode.replace(/<script([^>]*)>/gi,
                                    '<script$1 defer>');
        el.innerHTML = '';
        el.innerHTML = htmlCode;
        el.removeChild(el.firstChild);
    } else {
        var el_next = el.nextSibling;
        var el_parent = el.parentNode;
        el_parent.removeChild(el);
        el.innerHTML = htmlCode;
        if (el_next) {
            el_parent.insertBefore(el, el_next)
        } else {
            el_parent.appendChild(el);
        }
    }
}


  /****************** ?????????????? *************************/
  function getAbsolutePos(el) {
   var r = { x: el.offsetLeft, y: el.offsetTop };
   if (el.offsetParent) {
    var tmp = getAbsolutePos(el.offsetParent);
    r.x += tmp.x;
    r.y += tmp.y;
   }
   return r;
  };
  
/**************************************scroll***************************************************/
  function _reportScroll( name ) {
		var contentDiv = document.getElementById( name + "_contentdiv" );
		var topDiv = document.getElementById( name + "_topdiv" );
		if( topDiv != null ) topDiv.scrollLeft = contentDiv.scrollLeft;
		var leftDiv = document.getElementById( name + "_leftdiv" );
		if( leftDiv != null ) leftDiv.scrollTop = contentDiv.scrollTop;
		try { _initInput.toString(); }catch( e ) { return; }
		
		var otherTable = document.getElementById( name + "_$_top" );
		if( otherTable != null ) _tableScrolling( otherTable );
		otherTable = document.getElementById( name + "_$_left" );
		if( otherTable != null ) _tableScrolling( otherTable );
		otherTable = document.getElementById( name );
		if( otherTable != null ) _tableScrolling( otherTable );
	}

	function _tableScrolling( table ) {
		var editor = table.currEditor;
		if( editor == null ) return;
		var cell = editor.editingCell;
		var x = cell.offsetLeft, y = cell.offsetTop;
		var obj = cell.offsetParent;
		var offsetP;
		if( editor.style ) offsetP = editor.offsetParent;
		else offsetP = editor.Table.offsetParent;
		while( obj != null && obj != offsetP ) {
			x += obj.offsetLeft + obj.clientLeft;
			y += obj.offsetTop + obj.clientTop;
			obj = obj.offsetParent;
		}
		var div = cell.parentElement;
		while( div.tagName != "DIV" || div.id == "div_" + cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) ) div = div.parentElement;
		x = x - div.scrollLeft;
		y = y - div.scrollTop;
		
		var dx = div.offsetLeft, dy = div.offsetTop;
		obj = div.offsetParent;
		while( obj != null && obj != offsetP ) {
			dx += obj.offsetLeft + obj.clientLeft;
			dy += obj.offsetTop + obj.clientTop;
			obj = obj.offsetParent;
		}
		switch( cell.editStyle ) {
			case "2":
			case "3":
				if( x < dx || y < dy || x + cell.offsetWidth > dx + div.offsetWidth || y + cell.offsetHeight > dy + div.offsetHeight ) {
					if( cell.offsetWidth < div.offsetWidth && cell.offsetHeight < div.offsetHeight ) {
						_submitEditor( table );
						return;
					}
				}
				editor.setLeft( x );
				editor.setTop( y );
				break;
			default:
				if( x < dx || y < dy || x + cell.offsetWidth > dx + div.offsetWidth || y + cell.offsetHeight > dy + div.offsetHeight ) {
					if( cell.offsetWidth < div.offsetWidth && cell.offsetHeight < div.offsetHeight ) {
						_submitEditor( table );
						return;
					}
				}
				editor.style.left = x;
				editor.style.top = y;
		}	
	}

	function _resizeScroll() {
		var name, div;
		var divs = _lookupDiv( document.body );
		
		for( var i = 0; i < divs.length; i++ ) {
			div = divs[i];
			var pos = div.id.indexOf( "_scrollArea" );
			name = div.id.substring( 0, pos );
			var W = div.clientWidth;
			var H = div.clientHeight;
			if(W>=document.body.clientWidth) {//firefox
				W=document.body.clientWidth-div.getBoundingClientRect().left*2-3;
				H=H-5;
			}else{//ie
				W=W-4;
				H=H-4;
			}
			
			var leftW = 0, topH = 0;
			var contentDiv = document.getElementById( name + "_contentdiv" );
			var contentTable = document.getElementById( name );
			var contentW = contentTable.offsetWidth;
			var contentH = contentTable.offsetHeight;
			var topDiv = document.getElementById( name + "_topdiv" );
			if( topDiv != null ) {
				var topH = document.getElementById( name + "_$_top" ).offsetHeight;
			}
			var leftDiv = document.getElementById( name + "_leftdiv" );
			if( leftDiv != null ) {
				var leftW = document.getElementById( name + "_$_left" ).offsetWidth;
			}
			var isWidthScroll=false;
			var isHeightScroll=false;
			var topW,leftH;
			
			if( topDiv != null ) {
				var topW = W - leftW;
				if( H - topH < contentH ) {
					topW -= 17;
					isHeightScroll = true;
				}
				if( topW < 0 ) topW = 0;
				//if( topW > contentW ) topW = contentW;
				//topDiv.style.width = topW;
			}
			if( leftDiv != null ) {
				var leftH = H - topH;
				if( W - leftW < contentW ) {
					leftH -= 17;
					isWidthScroll = true;
				}
				if( leftH < 0 ) leftH = 0;
				//leftDiv.style.height = leftH;
			}
			var cw = W - leftW < 0 ? 0 : W - leftW;
			//if( cw > contentW + 16 ) cw = contentW + 16;
			if(isHeightScroll ||isWidthScroll){
				topDiv.style.width = topW;
				contentDiv.style.width = cw;

				leftDiv.style.height = leftH;
				contentDiv.style.height = H - topH < 0 ? 0 : H - topH;
			}
		}
		if( document.body.oldOnload != null ) {
			var s = document.body.oldOnload.toString();
			document.body.oldOnload = null;
			s = s.substring( s.indexOf( "{" ) );
			eval( s );
		}
	}

	function _lookupDiv( obj ) {
		var child;
		var divs = new Array();
		for( var i = 0; i < obj.childNodes.length; i++ ) {
			child = obj.childNodes[i];
			if( child.tagName == "DIV" ) {
				if( child.id != null && child.id.indexOf( "_scrollArea" ) > 0 ) divs[ divs.length ] = child;
			}
			var subdivs = _lookupDiv( child );
			for( var k = 0; k < subdivs.length; k++ ) {
				divs[ divs.length ] = subdivs[k];
			}
		}
		return divs;
	}

	function getHeightX( obj ) {
		var child, h = 0;
		for( var i = 0; i < obj.childNodes.length; i++ ) {
			child = obj.childNodes[i];
			if( child.tagName == "DIV" ) {
				if( child.className ==  "report1" ) return child.offsetHeight;
			}
			h = getHeightX( child );
			if( h > 0 ) return h;
		}
		return h;
	}


/*************************************end scroll***************************************************/

/*************************************select***************************************************/
			//注意此页面不许加div标签
		
		//此方法是用来响应右移事件的 id 可用字段容器ID, str 目标容器现有数据(格式如下：显示值,值;显示值,值), tid 目标容器的ID, type 1->明细字段 2->排序字段或行显示字段 3->汇总字段 4->分组字段或列显示字段
		function rightSelected(id,str,tid,type,contextPath,hiddenName){
			
			//将str转换为二维数组
	  		var array = toArray(str) ;
	  		var startStr = array.length ;
	  		//得到选中的可用字段
	  		var sel = document.getElementById(id) ;
	  		var length = sel.length ;
	  		//临时数组用来存放新选中的并且在目标容器中没有的可用字段
	  		var tempArr = new Array() ;
	  		var tempIndex = 0 ;
	  		//遍历当前选中的可用字段，如果目标容器中没有此条数据，将其放到tempArr数组中
	  		for(var i=0;i<length;i++){
	  			var o = sel.options[i] ;
	  			if(o.selected == true){
	  				var flag = false ;
	  				var index = array.length ;
	  				for(var j=0;j<index;j++){
	  					if(array[j][0] == o.text){
	  						flag = true ;
	  					}
	  				}
	  				if(!flag){
		  				array[index] = new Array() ;
		  				array[index][0]=o.text;
		  				array[index][1]=o.value;
		  				tempArr[tempIndex] = new Array() ;
		  				tempArr[tempIndex][0] = o.text ;
		  				tempArr[tempIndex][1] = o.value ;
		  				tempArr[tempIndex][2] ="true";
		  				try{ tempArr[tempIndex][2] =document.getElementById("IS_SUM_"+o.value).value ;}catch(e){} //是否合计
		  				tempIndex ++ ;
	  				}
	  			}
	  		}	  	
	  		var sel1 = document.getElementById(tid) ;
	  		//获取目标容器div内的代码
	  		var s = sel1.innerHTML ;
	  		var oldStr = str ;
	  		//将数组在转换为字符串
	  		var str = toStr(array) ;
	  		//使目标容器中原有数据和新添加数据中的str参数保持一致
	  		if(oldStr != null && oldStr != ""){
	  			var re=new RegExp( "\'"+oldStr+"\'", "g"); 
	  			s = s.replace(re,"'"+str+"'") ;
	  		}
	  		//根据目标容器的类型不同，在其div间添加不同的内容
	  		if(type==1){
	  			selectedField = str ;
	  			for(var i=0;i<tempArr.length;i++){
		  			 s+="<div style='text-align:center;background-color:white' id='s_"+(i+startStr)+"' onclick=changeSelectedColor(this.id,'"+tempArr[i][0]+"','"+tempArr[i][1]+"')><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='selected"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"',selectedField,"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"',selectedField,"+type+")></td><td width='70%' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='10%' align='right'> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"',selectedField,"+type+",'"+id+"')></td></tr></table></div>" ;
		  		}
	  		}else if(type==2){
	  			orderField = str ;
	  			for(var i=0;i<tempArr.length;i++){
	  				 //s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"','"+str+"',"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"','"+str+"',"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='45%' align='right'> <span style='display:none;'> <select name='order"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;' disabled><option value='asc'>升序</option><option value='desc'>降序</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"','"+str+"',"+type+",'"+id+"')></td></tr></table></div>" ;
	  				 s+="<div style='text-align:center;'><table width='100%'  cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='order"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"',orderField,"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"',orderField,"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='45%' align='right' ><select name='"+hiddenName+"Sum' style='font-size:12px;'><option value='"+tempArr[i][1]+"'>合计</option><option value='' "+(tempArr[i][2]=="true"?"":"selected")+">不合计</option></select> <span style='display:none;'> <select name='order"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;' disabled><option value='asc'>升序</option><option value='desc'>降序</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"',orderField,"+type+",'"+id+"')></td></tr></table></div>" ;
		  		}
	  		}else if(type==3){
	  			countField = str ;
	  			for(var i=0;i<tempArr.length;i++){
		  			 s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='35'><input type='hidden' name="+hiddenName+" id='count"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"',countField,"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"',countField,"+type+")></td><td align='center' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='15' align='right'> <span style='display:none;'><select name='count"+(i+startStr)+"_"+(i+startStr)+"' style='font-size:12px;'><option value='sum'>求和</option><option value='max'>最大</option><option value='min'>最小</option><option value='avg'>平均</option><option value='count'>计数</option></select></span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"',countField,"+type+",'"+id+"')></td></tr></table></div>" ;
		  		}
	  		}else if(type==4){
	  			groupField = str ;
	  			for(var i=0;i<tempArr.length;i++){
		  			 s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='group"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"',groupField,"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"',groupField,"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='45%' align='right'><select name='"+hiddenName+"Sum' style='font-size:12px;'><option value='"+tempArr[i][1]+"'>合计</option><option value='' "+(tempArr[i][2]=="true"?"":"selected")+">不合计</option></select> <span style='display:none;'> <select name='group"+(i+startStr)+"_"+(i+startStr)+"' style='display:node;font-size:12px;'><option value='asc'>升序</option><option value='desc'>降序</option></select> </span><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"',groupField,"+type+",'"+id+"')></td></tr></table></div>" ;
		  		}
	  		}else if(type==5){
	  			sortField = str ;
	  			for(var i=0;i<tempArr.length;i++){
		  			 s+="<div style='text-align:center;'><table width='100%' cellpadding='0' border='0' cellspacing='0'><tr width='100%'><td width='25%'><input type='hidden' name="+hiddenName+" id='group"+(i+startStr)+"' value='"+tempArr[i][1]+"'><img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/up.gif' onclick=up("+(i+startStr)+",'"+tid+"',sortField,"+type+")> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/down.gif' onclick=dw("+(i+startStr)+",'"+tid+"',sortField,"+type+")></td><td width='30%' style='font:small-caps 600 9pts/18pts 宋体;'> "+tempArr[i][0]+"</td><td width='45%' align='right'><select name='"+hiddenName+"Desc' style='font-size:12px;'><option value=''>升序</option><option value='"+tempArr[i][1]+"' "+(tempArr[i][2]=="true"?"":"selected")+">倒序</option></select> <img style='cursor:pointer' src='"+contextPath+"/struts/simple/report/resource/images/del.gif' onclick=del("+(i+startStr)+",'"+tid+"',sortField,"+type+",'"+id+"')></td></tr></table></div>" ;
		  		}
	  		}
			//将新的字符串添加到div之间
	  		sel1.innerHTML=s;
	  		
	  		for(var i=0;i<sel.length;i++){
	  			var o = sel.options[i] ;
	  			if(o.selected == true){
	  				sel.remove(i);
	  				i--;
	  			}
	  		}	 
	  	}
	  	//上移操作
	  	function up(v,id,str,type){
	  		if(v == 0) return ;
	  		//alert("id:"+id+" str:"+str);
	  		var array = toArray(str) ;
	  		//新建临时变量，用作数组交换操作
	  		var temp = new Option(array[v][0], array[v][1]);
	  		array[v][0] = array[v-1][0] ;
	  		array[v][1] = array[v-1][1] ;
	  		array[v-1][0] = temp.text ;
	  		array[v-1][1] = temp.value ;	  		
	  		var sel1 = document.getElementById(id) ;
	  		var s = sel1.innerHTML ;
	  		s = s.substring(0,s.length-6) ;
	  		str = toStr(array) ;
	  		
	  		var strArray = s.split("</DIV>") ;
	  		//更改交换后的两行数据的代码串中的 v 参数，使其与二维数组中的数据顺序保持一致。
	  		var temp1 = strArray[v] ;
	  		strArray[v] = strArray[v-1] ;
	  		var lastUp = "up("+v+"," ;
	  		var preUp = "up("+(v-1)+"," ;
	  		var lastDel = "del("+v+"," ;
	  		var preDel = "del("+(v-1)+"," ;
	  		var lastDw = "dw("+v+"," ;
	  		var preDw = "dw("+(v-1)+"," ;
	  		strArray[v] = strArray[v].replace(preUp,lastUp) ;
	  		strArray[v] = strArray[v].replace(preDel,lastDel) ;
	  		strArray[v] = strArray[v].replace(preDw,lastDw) ;
	  		strArray[v-1] = temp1 ;
	  		strArray[v-1] = strArray[v-1].replace(lastUp,preUp) ;
	  		strArray[v-1] = strArray[v-1].replace(lastDel,preDel) ;
	  		strArray[v-1] = strArray[v-1].replace(lastDw,preDw) ;
	  		s = "" ;
	  		//alert(str);
	  		if(type==1){
	  			selectedField = str ;
	  		}else if(type==2){
	  			orderField = str ;
	  		}else if(type==3){
	  			countField = str ;
	  		}else if(type==4){
	  			groupField = str ;
	  		}else if(type==5){
	  			sortField = str;
	  		}
	  		for(var i=0;i<strArray.length;i++){
	  			s += strArray[i]+"</div>"
	  		}
	  		//document.getElementById("list_paraMap_BILLING_TYPE_ID").value=s;
	  		sel1.innerHTML=s;
	  	}
	  	//下移操作
	  	function dw(v,id,str,type){
	  		var array = toArray(str) ;
	  		if(v == array.length-1) return ;
	  		//新建一个临时变量，进行数组交换操作
	  		var temp = new Option(array[v][0], array[v][1]);
	  		array[v][0] = array[v+1][0] ;
	  		array[v][1] = array[v+1][1] ;
	  		array[v+1][0] = temp.text ;
	  		array[v+1][1] = temp.value ;
	  		var sel1 = document.getElementById(id) ;
	  		var s = sel1.innerHTML ;
	  		s = s.substring(0,s.length-6) ;
	  		str = toStr(array) ;
	  		
	  		var strArray = s.split("</DIV>") ;
	  		//更改交换后的两行数据中的v 参数，使其与二维数组中的数据顺序保持一致。
	  		var temp1 = strArray[v] ;
	  		strArray[v] = strArray[v+1] ;
	  		var lastUp = "up("+v+"," ;
	  		var preUp = "up("+(v+1)+"," ;
	  		var lastDel = "del("+v+"," ;
	  		var preDel = "del("+(v+1)+"," ;
	  		var lastDw = "dw("+v+"," ;
	  		var preDw = "dw("+(v+1)+"," ;
	  		strArray[v] = strArray[v].replace(preUp,lastUp) ;
	  		strArray[v] = strArray[v].replace(preDel,lastDel) ;
	  		strArray[v] = strArray[v].replace(preDw,lastDw) ;
	  		strArray[v+1] = temp1 ;
	  		strArray[v+1] = strArray[v+1].replace(lastUp,preUp) ;
	  		strArray[v+1] = strArray[v+1].replace(lastDel,preDel) ;
	  		strArray[v+1] = strArray[v+1].replace(lastDw,preDw) ;
	  		s = "" ;
	  		if(type==1){
	  			selectedField = str ;
	  		}else if(type==2){
	  			orderField = str ;
	  		}else if(type==3){
	  			countField = str ;
	  		}else if(type==4){
	  			groupField = str ;
	  		}else if(type==5){
	  			sortField = str;
	  		}
	  		//将更改后的代码嵌入到div之间
	  		for(var i=0;i<strArray.length;i++){
	  			s += strArray[i]+"</div>"
	  		}
	  		
	  		sel1.innerHTML=s;
	  	}
	  	//删除某条数据 v 第几条数据, id 容器的id, str div之间的代码, type 类型
	  	function del(v,id,str,type,srcId){
	  		//alert("v:"+v+" id:"+id+" str"+str+" type"+type+" srcId"+srcId);
	  		var array = toArray(str) ;
	  		var delId,delName;
	  		if(type==1){
		  		var arrayTemp = toArray(countStr) ;
		  		for(var i=0;i<arrayTemp.length;i++){
		  			if(arrayTemp[i][0]==array[v][0] && arrayTemp[i][1]==array[v][1]){
		  				arrayTemp.splice(i,1) ;
		  				countStr = toStr(arrayTemp) ;
		  				break ;
		  			}
		  		}
		  		var tempArr = toArray(countField) ;
		  		for(var j=0;j<tempArr.length;j++){
		  			if(tempArr[j][0]==array[v][0] && tempArr[j][1]==array[v][1]){
		  				delCount(j,'count') ;
		  				break ;
		  			}
		  		}
	  		}
	  		//alert(array.length+" "+v+" "+array[v]);
	  		delId = array[v][1];
	  		delName= array[v][0];
	  		//删除数组中某个位置的数据
	  		array.splice(v,1) ;
	  		var sel1 = document.getElementById(id) ;
	  		var s = sel1.innerHTML ;
	  		var oldStr = str ;
	  		//将数组在转换为字符串
	  		var str = toStr(array) ;
	  		//使目标容器中原有数据和新添加数据中的str参数保持一致
	  		if(oldStr != null && oldStr != ""){
	  			var re=new RegExp( "\'"+oldStr+"\'", "g"); 
	  			s = s.replace(re,"'"+str+"'") ;
	  		}
	  		s = s.substring(0,s.length-6) ;
	  		str = toStr(array) ;
	  		var strArray = s.split("</DIV>") ;
	  		//删除div之间代码中的某一条
	  		strArray.splice(v,1) ;
	  		//将div之间的代码中的参数v的值进行改变，使其和数组中的达到一致
	  		for(var i=v;i<strArray.length;i++){
	  			var lastUp = "up("+(i+1)+"," ;
		  		var preUp = "up("+i+"," ;
		  		var lastDel = "del("+(i+1)+"," ;
		  		var preDel = "del("+i+"," ;
		  		var lastDw = "dw("+(i+1)+"," ;
		  		var preDw = "dw("+i+"," ;
		  		strArray[i] = strArray[i].replace(lastUp,preUp) ;
		  		strArray[i] = strArray[i].replace(lastDel,preDel) ;
		  		strArray[i] = strArray[i].replace(lastDw,preDw) ;
	  		}
	  		s = "" ;
	  		//将字符串值赋给页面中的变量
	  		if(type==1){
	  			selectedField = str ;
	  		}else if(type==2){
	  			orderField = str ;
	  		}else if(type==3){
	  			countField = str ;
	  		}else if(type==4){
	  			groupField = str ;
	  		}else if(type==5){
	  			sortField = str;
	  		}
	  		//遍历代码分割开的数组，将其在拼成串，放在div之间。
	  		for(var i=0;i<strArray.length;i++){
	  			s += strArray[i]+"</div>"
	  		}	 		
	  		sel1.innerHTML=s;
	  		
	  		var oo=document.createElement('option');
  			oo.text=delName;
			oo.value=delId;
			document.getElementById(srcId).add(oo) ;
	  	}
	  	//将字符串转化为二维数组 [i][0]=显示值 [i][1]=值
	  	function toArray(str){	
	  		if(str.length==0) return new Array() ;
			var array = new Array() ;
			var array2 = new Array() ;
			array = str.split(";") ;
			for(var i=0;i<array.length;i++){
				array2[i] = new Array() ;
				var tempArr = array[i].split(",") ; 
				array2[i][0]=tempArr[0];
				array2[i][1]=tempArr[1];
			}
			return array2 ;
	  	}
	  	//将二维数组转化为 显示值,值;显示值,值 的形式
	  	function toStr(array){
	  		var str = "" ;
	  		for(var i=0;i<array.length;i++){
	  			str = str+array[i][0]+","+array[i][1]+";" ;
	  		}
	  		str = str.substring(0,str.length-1) ;
	  		return str ;
	  	}
	  	
	  	function changeSelectedColor(id,text,value){
	  		var arrayTemp = toArray(countStr) ;
	  		var len = arrayTemp.length ;
	  		var flag = false ;
	  		var d = document.getElementById(id) ;
	  		if(d.style.backgroundColor == 'white'){
	  			d.style.backgroundColor='#0066CC' ;
	  			for(var i=0;i<len;i++){
	  				if(arrayTemp[i][0] == text && arrayTemp[i][1] == value){
	  					flag = true ;
	  					break ;
	  				}
	  			}
	  			if(!flag){
	  				arrayTemp[len] = new Array() ;
	  				arrayTemp[len][0] = text ;
	  				arrayTemp[len][1] = value ;
	  			}
	  			flag = false ;
	  		}else{
	  			d.style.backgroundColor='white' ;
	  			var temp = 0 ;
	  			for(var i=0;i<len;i++){
	  				if(arrayTemp[i][0] == text && arrayTemp[i][1] == value){
	  					flag = true ;
	  					temp = i ;
	  					break ;
	  				}
	  			}
	  			if(flag){
	  				arrayTemp.splice(temp,1) ;
	  			}
	  			flag = false ;
	  		}
	  		countStr = toStr(arrayTemp) ;
	  	}
	 
	  
/*************************************select***************************************************/
//让斜线多表头高度,宽度自适应
function autoCrossImg(reportId){
	var cross_img = document.getElementById(reportId+"_cross_head_img");
	if(cross_img){
		cross_img.style.width= cross_img.parentElement.offsetWidth+'px';
		cross_img.style.height= cross_img.parentElement.offsetHeight+'px';
	}
}

//显示提示信息  
function olapMouseoverCell(cell){
	cell.showingTip = true;
	var tipCell = cell;
 	cell.timeOutId = setTimeout(function() {
 		if (tipCell.showingTip) {
 			var pos = $(tipCell).position();
       		pos.top += tipCell.offsetHeight + 3;
       		var valueTip = $("#olap_ToolTips");
            if (jQuery(valueTip).is(":visible")) {
                $(valueTip).stop(true, true);
            }
		              
            if (valueTip.runtimeStyle != null) {
                valueTip.runtimeStyle.left = pos.left + "px";
                valueTip.runtimeStyle.top = pos.top + "px";
            }
            else {
                valueTip[0].style.left = pos.left + "px";
                valueTip[0].style.top = pos.top + "px";
            }
            valueTip[0].innerHTML = cell.toolTips;
            //这里的 speed (String|Number): (可选) 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000)
           	valueTip.slideDown("normal");
   	 	 }
    },300);
    //toppage(30,0);
}
//关闭提示信息 
function olapMouseoutCell(cell) {
	cell.showingTip = false;
   	$("#olap_ToolTips").hide("normal");
   	//toppage(30,0);
}

/**********************OLAP工具栏*******************************************************************************************************************/
//olap旋转
function olapSwapAxes(reportid){
	var aElement = document.getElementById(reportid+"_olap_swapAxes");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_swapAxes\" name='olap.swapAxes' value=\"true\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		aElement.value="true";
	}
	
	var page_action ="";
	var page_target = "";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
}


//显示MDX
function olapShowMDX(reportid){
	var mdxDev = document.getElementById(reportid+"_showMDX_dev");
	olapOpenw("<textarea rows=25 cols=92>"+mdxDev.innerHTML+"</textarea>");
}
//显示SQL
function olapShowSQL(reportid){
	var sqlDev = document.getElementById(reportid+"_showSQL_dev");
	olapOpenw("<textarea rows=25 cols=92>"+sqlDev.innerHTML+"</textarea>");
}
function olapOpenw(values){
   open1=window.open('','','resizable=1,scrollbars=1,menubar=0,width=800,height=450,toolbar=0,directories=0');
   open1.document.open();
   open1.document.write(values);
   open1.document.close();
}

//EXCEL导出
function olapExportXls(reportid){
	var aElement = document.getElementById(reportid+"_olap_format");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_format\" name='olap.format' value=\"xls\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		aElement.value="xls";
	}
	
	var page_action ="";
	var page_target = "_blank";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
	aElement.value="html";
}

//DOC导出
function olapExportDoc(reportid){
	var aElement = document.getElementById(reportid+"_olap_format");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_format\" name='olap.format' value=\"doc\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		aElement.value="doc";
	}
	
	var page_action ="";
	var page_target = "_blank";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
	aElement.value="html";
}

//EXCEL导出
function olapExportPdf(reportid){
	var aElement = document.getElementById(reportid+"_olap_format");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_format\" name='olap.format' value=\"pdf\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		aElement.value="pdf";
	}
	
	var page_action ="";
	var page_target = "_blank";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
	aElement.value="html";
}

		
//是否显示斜表头
function olapShowBiasTab(reportid){
	var aElement = document.getElementById(reportid+"_olap_showBias");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_showBias\" name='olap.showBias' value=\"true\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		if(aElement.value =="true"){
			aElement.value="false";
		}else{
			aElement.value="true";
		}
	}
	
	var page_action ="";
	var page_target = "";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
}

//是否冻结表头
function olapShowScrolling(reportid){
	var aElement = document.getElementById(reportid+"_olap_scrolling");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_scrolling\" name='olap.scrolling' value=\"true\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		if(aElement.value =="true"){
			aElement.value="false";
		}else{
			aElement.value="true";
		}
	}
	
	var page_action ="";
	var page_target = "";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
}

//是否冻结表头
function olapChangeViewType(reportid){
	var aElement = document.getElementById(reportid+"_olap_showViewType");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_showViewType\" name='olap.showViewType' value=\"tabChart\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}else{
		if(aElement.value =="tabChart"){
			aElement.value="tab";
		}else{
			aElement.value="tabChart";
		}
	}
	
	var page_action ="";
	var page_target = "";
	document.getElementById(reportid+'_form').action=page_action; 
	document.getElementById(reportid+'_form').target=page_target; 
	document.getElementById(reportid+'_form').submit();
}

function olapChartConfig(reportid,img){
	olapOpenChartConfigLayer(reportid,img);
}

//保存图表设置
function olapSaveChartConfig(path,reportid){
	olapCloseChartConfigLayer(reportid);
	//重新刷新图表
	olapChartDynamicDraw(path,reportid);
}

//打印设置
function olapPrintConfig(){
	document.getElementById('WebBrowser').ExecWB(8,1);
}
//打印预览
function olapPrintPreview(){
	document.getElementById('WebBrowser').ExecWB(7,1)
}

//打印
function olapPrint(){
	document.getElementById('WebBrowser').ExecWB(6,1);
}

//保存变量到session
function olapSaveToSession(path,name,value){
	var savePath=path+'/report/view/saveToSession.shtml?olap.saveToSession='+name+'&olap.'+name+'='+value;
	//alert(savePath);
	$.ajax({
    url:savePath,
    type: 'GET',
    dataType: 'xml',
    timeout: 1000,
    error: function(){
    },
    success: function(xml){
        // do something with xml
    }
});
	
}
/**********************OLAP工具栏 end*******************************************************************************************************************/
	
/**********************OLAP图表*******************************************************************************************************************/
	var olapLastChartCellElement;
	var olapLastArrowType;
	var olapLastCells;
	
	/**
	 *	打开默认图表
	 */
	function olapShowDefaultChart(path,reportid){
		var rpTable = document.getElementById(reportid);
		var rpCell= rpTable.rows[rpTable.rows.length-1].cells[0];
		if(rpCell)
			olapChartDynamicDraw(path,reportid,rpCell,'row');
	}
	/**
	 *	选择行或列数据,动态生图表
	 */
	function olapChartDynamicDraw(path,reportid,cellElement,arrowType){
		var cells;
		if(cellElement&&arrowType){//cellElement&&arrowType 都不为空,则取得选中数据进行画图表.
			//恢复上次选择行或列的单元格样式
			olapResumeLastChartCellStyle(reportid);
			 var aElement=document.getElementById(reportid+"_olap_chartShowPosition");
			 if(aElement.value=="1"){//浮动层
			 	olapOpenChartLayer(reportid,cellElement);
			 }else{//固定位置显示
			 	olapOpenChartLayerRelative(reportid);
			 }
			
			cells = olapChartGetTableCell(reportid,cellElement,arrowType);
		}else{//按上次选择的数据进行画图表
			cellElement=olapLastChartCellElement;
			arrowType=olapLastArrowType;
			cells = olapLastCells;
		}
		//alert(cellElement.row+" "+cellElement.col+" arrowType:"+arrowType);
		//var rowColIndex =getRowColIndex(reportid,cellElement,arrowType);
		//var rowIndex =rowColIndex[0];// cellElement.row; //点击的行
		//var colIndex =rowColIndex[1];// cellElement.col; //点击的列
		var chartType= document.getElementById(reportid+"_olap_chartType").value;
		var dataXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		var width="100%";
		var height="100%";
		var divid=reportid+"_chart";
		
		//取得选中的单元格
		var iRowCount=0;
		var iMaxTitleLength=10;
		var iCellsCount=cells.length;
		/*******************生成饼图*******************/
		if(chartType=="PIE"){
			var maxValue=0;
			for(var i=0;i<iCellsCount;i++){
				var title = arrowType=="col"?cells[i].rowTitle:cells[i].colTitle;
				if(title!="合计"){
					//alert(cells[i].cellValue+":"+parseFloat(cells[i].cellValue));
					var currValue=parseFloat(cells[i].cellValue);
					if(currValue>maxValue ){
						maxValue = currValue;
					}
				}
			}
			
			dataXml+="<pie>";
			for(var i=0;i<iCellsCount;i++){
				var title = arrowType=="col"?cells[i].rowTitle:cells[i].colTitle;
				if(title!="合计"){
					
					dataXml+="<slice title=\""+title+"\" "+(parseFloat(cells[i].cellValue)==maxValue?"pull_out=\"true\"":"")+" >"+cells[i].cellValue+"</slice>";
					iRowCount++;
				}
			}
			if(iRowCount>15)
				height=20*iRowCount;
			dataXml+="</pie>";
			olapSetChartDivWidth(reportid,width,height);
			var chartSetting = olapGetPieChartSettings(reportid);
			
			olapShowPieChart(path,divid,dataXml,width,height,chartSetting);
		}
		/*******************生成饼图 end *******************/
			
		/*******************生成柱图与线图*******************/
		iRowCount = 0;
		if(chartType=="BAR"||chartType=="LINE"){
			//var charDataObj=new olapChartDataObj();	
			var seriesMap = new Map();
			var measureMap = new Map();
			var measureArray = new Array();
			var totalValue=0;
			var shapeType="";//折线结点类型
			var fillAlpha = ""; 
			if(chartType=="LINE"){
				if(document.getElementById(reportid+"_olap_lineChartShapeType")&&document.getElementById(reportid+"_olap_lineChartShapeType").value!="")
					shapeType=" bullet=\""+document.getElementById(reportid+"_olap_lineChartShapeType").value+"\" bullet_size=\"10\" ";
				if(document.getElementById(reportid+"_olap_lineChartType")&&document.getElementById(reportid+"_olap_lineChartType").value=="area")
					fillAlpha+=" fill_alpha=\"50\" ";
			}
			dataXml+="<chart>";
				dataXml+="<series>";
				for(var i=0;i<iCellsCount;i++){
					var title = arrowType=="col"?cells[i].rowTitle:cells[i].colTitle;
					if(seriesMap.get(title) == undefined && title!="合计"){
						var indexCurr = seriesMap.size();
						dataXml+="<value xid=\""+indexCurr+"\" >"+olapSubString(title,iMaxTitleLength)+"</value>";
						seriesMap.put(title,indexCurr);
						
					}
					
					if(cells[i].measureTitle != undefined && measureMap.get(cells[i].measureTitle) == undefined){
						measureMap.put(cells[i].measureTitle,measureArray.length);
						measureArray[measureArray.length]=cells[i].measureTitle;
					}
				}
				dataXml+="</series>";
				dataXml+="<graphs>";
				for(var j=0;j<measureArray.length;j++){
					dataXml+="<graph title=\""+measureArray[j]+"\" line_width=\"2\" "+fillAlpha+">";
					for(var i=0;i<iCellsCount;i++){
						if(cells[i].measureTitle==measureArray[j]){
							var title = arrowType=="col"?cells[i].rowTitle:cells[i].colTitle;
							if(title!="合计"){
								dataXml+="<value xid=\""+seriesMap.get(title)+"\" "+shapeType+">"+cells[i].cellValue+"</value>";
								totalValue=totalValue+parseFloat(cells[i].cellValue);
								iRowCount++;
							}
						}
					}
					dataXml+="</graph>";
				}
				dataXml+="</graphs>";
				
			if( (chartType=="BAR"&&document.getElementById(reportid+"_olap_barChartShowAverage")&&document.getElementById(reportid+"_olap_barChartShowAverage").value=="true")
				|| (chartType=="LINE"&&document.getElementById(reportid+"_olap_lineChartShowAverage")&&document.getElementById(reportid+"_olap_lineChartShowAverage").value=="true")){
				var avgValue=totalValue/iRowCount;
				avgValue = Math.round(avgValue * Math.pow(10,2) ) / Math.pow(10,2);
				dataXml+="<guides><guide><start_value>"+avgValue+"</start_value><title>平均值:"+avgValue+"</title><color>#0D8ECF</color><inside>true</inside><behind>true</behind></guide></guides>";
			}
			dataXml+="</chart>";
			if(iRowCount>15)
				width=30*iRowCount;
				
			olapSetChartDivWidth(reportid,width,height);
			if(chartType=="BAR"){
				var chartSetting = olapGetBarChartSettings(reportid);
				olapShowBarChart(path,divid,dataXml,width,height,chartSetting);
			}else{
				var chartSetting = olapGetLineChartSettings(reportid);
				olapShowLineChart(path,divid,dataXml,width,height,chartSetting);
			}
		}
		/*******************生成柱图与线图 end *******************/
		//alert(dataXml);
		
		//将箭头指向选择的行或列
		olapArrowToElement(reportid,cellElement,arrowType);
		//记录最后一选中的单元格,方向等信息.
		olapLastChartCellElement=cellElement;
		olapLastArrowType=arrowType;
		olapLastCells=cells;
		toppage(30,0);
	}
	
	function getRowColIndex(reportid,cellElement,arrowType){
		var rowIndex;
		var colIndex;
		if (arrowType=='col'){//点击列头
			var cornerTab = document.getElementById(reportid+"_$_corner");
			if(cornerTab){
				colIndex = cellElement.col-cornerTab.rows[0].cells.length;
			}else{
				var leftTab = document.getElementById(reportid+"_$_left");
				if(leftTab){
					colIndex = cellElement.col-leftTab.rows[0].cells.length;
				}else{
					colIndex = cellElement.col;
				}
			}
		}else{
			var topTab = document.getElementById(reportid+"_$_top");
			if(topTab){
				rowIndex = cellElement.row-topTab.rows.length;
			}else{
				rowIndex = cellElement.row;
			}
		}
		return new Array(rowIndex,colIndex);
	}
	
	/**
		*如果字符串超长,则按最大字符数截取
		*/
	function olapSubString(title,maxLength){
		if(title&&title.length>maxLength){
			return title.substring(0,maxLength)+"...";
		}else{
			return title;
		}
		
	}
	
	/**
		*选择某行或某列时,将箭头指向选择中的行或列
		*/
	function olapArrowToElement(reportid,eventObj,arrowType){
		/*
		//生成箭头存放的层
		var aElement = document.getElementById(reportid+"olapChartArrow");
    if(!aElement){
			aElement =document.createElement("<div id=\""+reportid+"olapChartArrow"+"\" style='position: absolute; top: 100;visibility:hidden;z-index:100;'></div>"); 
			document.body.appendChild(aElement); 
		}
		aElement.style.display = "";
		var top=0,left=0;
		current_obj=eventObj;
		if (current_obj.tagName){
			while (current_obj.tagName!="BODY") {
				top=top+current_obj.offsetTop;
				left=left+current_obj.offsetLeft;
				current_obj=current_obj.offsetParent;
			}	
		}
	   aElement.style.visibility="visible";
	   if (arrowType=='col'){
	   	 aElement.className="olap-arrow-col";
	     aElement.style.left=left+12;
	     aElement.style.top=top-12;
	   }else{
	     aElement.className="olap-arrow-row";
	     aElement.style.left=left-12;
	     aElement.style.top=top+6;
	   }
	   */
	}    
	/**
	 * 取得选中的要做图表分析的单元格
	 */
	function olapChartGetTableCell(reportid,cellElement,arrowType){
		var selectTableCell = new Array();   
		//selectTableCell[0]="123"; 
		var rowColIndex =getRowColIndex(reportid,cellElement,arrowType);
		var rowIndex =rowColIndex[0];// cellElement.row; //点击的行
		var colIndex =rowColIndex[1];// cellElement.col; //点击的列
		var tableElement = document.getElementById(reportid);//rowElement.parentElement; //表格对象
		
			//点击列头
			if(arrowType=="col"){
				for(var i=0;i<tableElement.rows.length;i++){//tableElement.rows.length
					var rowTmp = tableElement.rows[i];
					for(var j=colIndex;j>-1;j--){
						if(j<rowTmp.cells.length){
							var cellTmp = rowTmp.cells[j];
							//alert(cellTmp.cellType+" "+cellTmp.cellValue+" "+cellTmp.cellType.indexOf("cell")+" "+cellTmp.col+ " " +colIndex);
							if(cellTmp.cellType.indexOf("cell")!=0)
									break;
							if(cellTmp.col==cellElement.col){
								//alert(cellTmp.cellValue);
								if(cellTmp.cellValue != undefined){
									selectTableCell[selectTableCell.length]=cellTmp;
								}
								cellTmp.srcClassName = cellTmp.className;
								cellTmp.className="olap-cell-chart-select";
								break;
							}
						}
					}
				}
			}else {
				//点击行头
				var rowElement =  tableElement.rows[rowIndex]; //cellElement.parentElement;//当前所有行对象
				for(var j=0;j<rowElement.cells.length;j++){
					var cellTmp = rowElement.cells[j];
					//alert(cellTmp.cellType+" "+cellTmp.cellValue+" "+cellTmp.cellType.indexOf("cell-"));
					if(cellTmp.cellType.indexOf("cell")==0){
							//alert(cellTmp.cellValue);
							if(cellTmp.cellValue != undefined){
								selectTableCell[selectTableCell.length]=cellTmp;
							}
							cellTmp.srcClassName = cellTmp.className;
							cellTmp.className="olap-cell-chart-select";
					}
				}
			}
		
		return selectTableCell;
	}
	
	//恢复最后一次选中的用于图表显示的单元格样式
	function olapResumeLastChartCellStyle(reportid){
		if(olapLastChartCellElement&&olapLastArrowType){
			var rowColIndex =getRowColIndex(reportid,olapLastChartCellElement,olapLastArrowType);
			var rowIndex =rowColIndex[0];// cellElement.row; //点击的行
			var colIndex =rowColIndex[1];// cellElement.col; //点击的列
			//var rowIndex = olapLastChartCellElement.row; //点击的行
			//var colIndex = olapLastChartCellElement.col; //点击的列
			var tableElement = document.getElementById(reportid);//rowElement.parentElement; //表格对象
			
			//点击列头
			if(olapLastArrowType=="col"){
				//alert("col"+rowIndex+" "+tableElement.rows.length);
				for(var i=0;i<tableElement.rows.length;i++){//tableElement.rows.length
					var rowTmp = tableElement.rows[i];
					for(var j=colIndex;j>-1;j--){
						if(j<rowTmp.cells.length){
							var cellTmp = rowTmp.cells[j];
							//alert(cellTmp.cellType+" "+cellTmp.cellValue+" "+cellTmp.cellType.indexOf("cell-"));
							if(cellTmp.cellType.indexOf("cell")!=0)
									break;
							if(cellTmp.col==olapLastChartCellElement.col){
								//alert(cellTmp.cellValue);
								cellTmp.className = cellTmp.srcClassName;
								break;
							}
						}
					}
				}
			}else {
				//点击行头
				var rowElement =  tableElement.rows[rowIndex]; //cellElement.parentElement;//当前所有行对象
				for(var j=0;j<rowElement.cells.length;j++){
					var cellTmp = rowElement.cells[j];
					//alert(cellTmp.cellType+" "+cellTmp.cellValue+" "+cellTmp.cellType.indexOf("cell-"));
					if(cellTmp.cellType.indexOf("cell")==0){
							//alert(cellTmp.cellValue);
							cellTmp.className = cellTmp.srcClassName;
					}
				}
			}
		}
	}
	
	function olapSetChartDivWidth(reportid,width,height){
	  
		var olap_scrolling = document.getElementById(reportid+"_olap_scrolling");
		var scrollingWidth = document.getElementById(reportid+"_olap_scrollingWidth");
		var chartDiv = document.getElementById(reportid+"_chart");
		if(olap_scrolling != undefined && olap_scrolling.value=='true'&&scrollingWidth!= undefined ){
			
			if(scrollingWidth.value<width){
			 	//alert(width+" "+height);
				chartDiv.style.width=scrollingWidth.value-20;
				chartDiv.style.height=height=="100%"?"450":height+20;
				chartDiv.style.overflow = "auto";
				return;
			}
		}
		chartDiv.style.width="";
		chartDiv.style.height="";
		chartDiv.style.overflow = "";
		
	}

	//切换不同图类型设置
	function olapChangechartConfigType(reportid){
		var chartConfigType = document.getElementById(reportid+"_olap_chartConfigType");
		if(chartConfigType.value=="BAR"){
			document.getElementById(reportid+"_barChartConfig").style.display="";
			document.getElementById(reportid+"_peiChartConfig").style.display="none";
			document.getElementById(reportid+"_lineChartConfig").style.display="none";
		}else if(chartConfigType.value=="LINE"){
			document.getElementById(reportid+"_lineChartConfig").style.display="";
			document.getElementById(reportid+"_barChartConfig").style.display="none";
			document.getElementById(reportid+"_peiChartConfig").style.display="none";
		}else{
			document.getElementById(reportid+"_peiChartConfig").style.display="";
			document.getElementById(reportid+"_lineChartConfig").style.display="none";
			document.getElementById(reportid+"_barChartConfig").style.display="none";				
		}
	}
	
	//绘制饼图
 	function olapShowPieChart(path,divid,chartData,width,height,chartSettings){
 		//alert(chartData);
	 	var so = new SWFObject(path+"/struts/simple/report/resource/js/amcharts/ampie/ampie.swf", "ampie", ""+width, ""+height, "8", "#FFFFFF");
	 	so.addParam("wmode", "transparent"); 
	 	so.addVariable("path", path+"/struts/simple/report/resource/js/amcharts/ampie/");  
	 	so.addVariable("settings_file", encodeURIComponent(path+"/struts/simple/report/resource/js/amcharts/ampie/ampie_settings.xml"));
	 	//so.addVariable("additional_chart_settings", "&lt;settings&gt;&lt;legend&gt;&lt;enabled&gt;false&lt;/enabled&gt;&lt;/legend&gt;&lt;pie&gt;&lt;x&gt;50%25&lt;/x&gt;&lt;y&gt;50%25&lt;/y&gt;&lt;/pie&gt;&lt;data_labels&gt;&lt;show&gt;&lt;![CDATA[{title}:{percents}%25]]&gt;&lt;/show&gt;&lt;/data_labels&gt;&lt;export_as_image&gt;&lt;file&gt;/rp/comm/servlet.shtml?servlet_name=exportImage&lt;/file&gt;&lt;/export_as_image&gt;&lt;/settings&gt;");
		so.addVariable("additional_chart_settings",encodeURIComponent(chartSettings) );
		so.addVariable("chart_data",encodeURIComponent(chartData));
	 	so.addVariable("loading_settings", "加载数据中。。。");
	 	so.addVariable("preloader_color","#999999");
	 	so.write(divid);
	}
	//绘制柱图
	function olapShowBarChart(path,divid,chartData,width,height,chartSettings){ 
		
		//alert(path+"struts/simple/report/resource/js/amcharts/amcolumn/amcolumn.swf");
		var so = new SWFObject(path+"/struts/simple/report/resource/js/amcharts/amcolumn/amcolumn.swf", "amcolumn", ""+width, ""+height, "8", "#FFFFFF");
		so.addParam("wmode", "transparent"); 
		so.addVariable("path", path+"/struts/simple/report/resource/js/amcharts/amcolumn/");  
		so.addVariable("settings_file", encodeURIComponent(path+"/struts/simple/report/resource/js/amcharts/amcolumn/amcolumn_settings.xml"));
		//so.addVariable("additional_chart_settings", "&lt;settings&gt;&lt;depth&gt;10&lt;/depth&gt;&lt;column&gt;&lt;width&gt;20&lt;/width&gt;&lt;data_labels&gt;&lt;![CDATA[{value}]]&gt;&lt;/data_labels&gt;&lt;/column&gt;&lt;export_as_image&gt;&lt;file&gt;/rp/comm/servlet.shtml?servlet_name=exportImage&lt;/file&gt;&lt;/export_as_image&gt;&lt;/settings&gt;");
		so.addVariable("additional_chart_settings",encodeURIComponent(chartSettings) );
		so.addVariable("chart_data",encodeURIComponent(chartData));
		so.addVariable("loading_settings", "加载数据中。。。");
		so.addVariable("preloader_color","#999999");
		so.write(divid);
	}
	//绘制线图
	function olapShowLineChart(path,divid,chartData,width,height,chartSettings){
		var so = new SWFObject(path+"/struts/simple/report/resource/js/amcharts/amline/amline.swf", "amline", ""+width, ""+height, "8", "#FFFFFF");
		so.addParam("wmode", "transparent"); 
		so.addVariable("path", path+"/struts/simple/report/resource/js/amcharts/amline/");  
		so.addVariable("settings_file", encodeURIComponent(path+"/struts/simple/report/resource/js/amcharts/amline/amline_settings.xml"));
		//so.addVariable("additional_chart_settings", "&lt;settings&gt;&lt;graphs&gt;&lt;graph gid=&quot;1&quot;&gt;&lt;axis&gt;left&lt;/axis&gt;&lt;line_width&gt;2&lt;/line_width&gt;&lt;/graph&gt;&lt;/graphs&gt;&lt;graphs&gt;&lt;graph gid=&quot;2&quot;&gt;&lt;axis&gt;left&lt;/axis&gt;&lt;line_width&gt;2&lt;/line_width&gt;&lt;/graph&gt;&lt;/graphs&gt;&lt;export_as_image&gt;&lt;file&gt;/rp/comm/servlet.shtml?servlet_name=exportImage&lt;/file&gt;&lt;/export_as_image&gt;&lt;/settings&gt;");
		so.addVariable("additional_chart_settings",encodeURIComponent(chartSettings) );
		so.addVariable("chart_data",encodeURIComponent(chartData));
		so.addVariable("loading_settings", "加载数据中。。。");
		so.addVariable("preloader_color","#999999");
		so.write(divid);
	}

	/**
	 *取得饼图设置
	 */
	function olapGetPieChartSettings(reportid){
		var xml="";
		
		var legend=""; //子标题(图例)设置
		var data_labels=""; //数据引用提示设置
		var pie=""; //饼图设置
		var animation=""; //动画设置
		//是否显示子标题
		var aElement=document.getElementById(reportid+"_olap_peiChartShowSubTitle");
		if(aElement&&aElement.value=="true"){
			legend+="<enabled>true</enabled>";
		}else{
			legend+="<enabled>false</enabled>";
		}
		//子标题内容
		aElement=document.getElementById(reportid+"_olap_peiChartPlotTitle");
		if(aElement&&aElement.value!=""){
			legend+="<values><text><![CDATA[: "+aElement.value.replace("{title}","")+"]]></text></values>";
			data_labels+="<show><![CDATA["+aElement.value+"]]></show>";
		}else{
			legend+="<values><text><![CDATA[: {value}]]></text></values>";
			data_labels+="<show><![CDATA[{title}:{percents}%]]></show>";
		} 
		
		
		//饼图的厚度
		aElement=document.getElementById(reportid+"_olap_peiChartPlotType");
		if(aElement&&aElement.value=="2D"){
			pie+="<height>0</height>";
		}else{
			aElement=document.getElementById(reportid+"_olap_peiChartPieHeight");
			if(aElement&&aElement.value!=""){
				pie+="<height>"+aElement.value+"</height>";
			}
		}
		//饼图的视角
		aElement=document.getElementById(reportid+"_olap_peiChartPieAngle");
		if(aElement&&aElement.value!=""){
			pie+="<angle>"+aElement.value+"</angle>";
		}
		
		//动画效果
		aElement=document.getElementById(reportid+"_olap_peiChartAnimationStartEffect");
		if(aElement&&aElement.value!=""){
			animation+="<start_effect>"+aElement.value+"</start_effect>";
		}
		//动画效果时长
		aElement=document.getElementById(reportid+"_olap_peiChartAnimationStartTime");
		if(aElement&&aElement.value!=""){
			animation+="<start_time>"+aElement.value+"</start_time>";
		}
		
		legend="<legend>"+legend+"</legend>";
		data_labels="<data_labels>"+data_labels+"</data_labels>";
		if(pie !="")
			pie="<pie>"+pie+"</pie>";
		animation="<animation>"+animation+"</animation>";
		xml = legend+data_labels+pie+animation;
		//xml = xml.replace("%","%25").replace("&","%26");
		xml = "<settings>"+xml+"</settings>";
		return xml;
	}
	
	
	/**
	 *取得柱图设置
	 */
	function olapGetBarChartSettings(reportid){
		var xml="";
		
		var legend=""; //子标题(图例)设置
		var column=""; //柱体设置
		var indicator=""; //坐标提供器
		
		//柱体的厚度
		aElement=document.getElementById(reportid+"_olap_barChartPlotType");
		if(aElement&&aElement.value=="2D"){
			xml+="<depth>0</depth>";
		}
		//是否为条状
		aElement=document.getElementById(reportid+"_olap_barChartType");
		if(aElement&&aElement.value=="bar"){
			xml+="<type>bar</type>";
		}
		
		//柱图类型:簇状柱图,堆叠柱图,100%堆叠柱图,3D簇状柱图
		aElement=document.getElementById(reportid+"_olap_barChartColumnType");
		if(aElement&&aElement.value!=""){
			column+="<type>"+aElement.value+"</type>";
		}
		//显示数据柱子上数据
		aElement=document.getElementById(reportid+"_olap_barChartShowDataTitle");
		if(aElement&&aElement.value=="true"){
			column+="<data_labels><![CDATA[{value}]]></data_labels>";
		}
		//汽泡提示信息
		aElement=document.getElementById(reportid+"_olap_barChartToolTipTitle");
		if(aElement&&aElement.value!=""){
			column+="<balloon_text><![CDATA["+aElement.value+"]]></balloon_text>";
		}
		
		//动画效果
		aElement=document.getElementById(reportid+"_olap_barChartGrowEffect");
		if(aElement&&aElement.value!=""){
			column+="<grow_effect>"+aElement.value+"</grow_effect>";
		}
		//动画效果时长
		aElement=document.getElementById(reportid+"_olap_barChartGrowTime");
		if(aElement&&aElement.value!=""){
			column+="<grow_time>"+aElement.value+"</grow_time>";
		}
		//是否按顺序生长
		aElement=document.getElementById(reportid+"_olap_barChartSequencedGrow");
		if(aElement&&aElement.value=="true"){
			column+="<sequenced_grow>true</sequenced_grow>";
		}
		
		
		
		//是否显示子标题
		var aElement=document.getElementById(reportid+"_olap_barChartShowSubTitle");
		if(aElement&&aElement.value=="true"){
			legend+="<enabled>true</enabled>";
		}else{
			legend+="<enabled>false</enabled>";
		}
		
		//显示坐标提供器 
		var aElement=document.getElementById(reportid+"_olap_barChartIndicator");
		if(aElement&&aElement.value=="true"){
			indicator+="<plugins><plugin file=\"plugins/value_indicator.swf\" position=\"above\">";
			indicator+="<chart_type>line</chart_type>";
			indicator+="<text_color>#000000</text_color>";
			indicator+="<text_size>12</text_size>";
			indicator+="<precision>2</precision>";
			indicator+="<axis>left</axis>";
			indicator+="</plugin></plugins>";
		}
		legend="<legend>"+legend+"</legend>";
		if(column !="")
			column="<column>"+column+"</column>";
		
		xml +=legend+column+indicator;
		xml = "<settings>"+xml+"</settings>";
		return xml;
	}
	
	
	/**
	 *取得线图设置
	 */
	function olapGetLineChartSettings(reportid){
		var xml="";
		
		var legend=""; //子标题(图例)设置
		var indicator=""; //坐标提供器
		var axes=""; //轴设置
		var balloon="";//汽泡提示
	
		//线图类型:折线图,堆叠图,100%堆叠图
		aElement=document.getElementById(reportid+"_olap_lineChartType");
		if(aElement&&aElement.value!=""){
			var lineChartType=aElement.value;
			if(lineChartType=="area")
				lineChartType="line";
			axes+="<y_left><type>"+lineChartType+"</type></y_left>";
		}
		//是否显示子标题
		var aElement=document.getElementById(reportid+"_olap_lineChartShowSubTitle");
		if(aElement&&aElement.value=="true"){
			legend+="<enabled>true</enabled>";
		}else{
			legend+="<enabled>false</enabled>";
		}
		//汽泡提示信息
		aElement=document.getElementById(reportid+"_olap_lineChartToolTipTitle");
		if(aElement&&aElement.value!=""){
			balloon+="<show><![CDATA["+aElement.value+"]]></show>";
		}

		
		//显示坐标提供器 
		var aElement=document.getElementById(reportid+"_olap_lineChartIndicator");
		if(aElement&&aElement.value=="true"){
			indicator+="<plugins><plugin file=\"plugins/value_indicator.swf\" position=\"above\">";
			indicator+="<chart_type>line</chart_type>";
			indicator+="<text_color>#000000</text_color>";
			indicator+="<text_size>12</text_size>";
			indicator+="<precision>2</precision>";
			indicator+="<axis>left</axis>";
			indicator+="</plugin></plugins>";
		}
		
		
		
		
		legend="<legend>"+legend+"</legend>";
		if(axes !="")
			axes="<axes>"+axes+"</axes>";
		if(balloon !="")
			balloon="<balloon>"+balloon+"</balloon>";
		
		xml +=legend+balloon+axes+indicator;
		xml = "<settings>"+xml+"</settings>";
		return xml;
	}
/**********************OLAP图表 end*******************************************************************************************************************/

/*******************olap图表弹出层*******************************************************************************************************************/
//改变图表显示位置1浮动层显示,2表格后固定位置显示
function chartShowPositionChange(contextPath,reportid,skin){
	var aElement = document.getElementById(reportid+"_olap_chartShowPosition");
	if(!aElement){
		aElement =document.createElement("<input type=hidden id=\""+reportid+"_olap_chartShowPosition\" name='olap.chartShowPosition' value=\"2\">"); 
		document.getElementById(reportid+'_span').appendChild(aElement); 
	}
	if(aElement.value=="1"){
		aElement.value="2";
		document.getElementById(reportid+"_chartShowPosition_img").src=contextPath+"/struts/simple/report/resource/skin/"+skin+"/images/stick.gif";
	}else{
		aElement.value="1";
		document.getElementById(reportid+"_chartShowPosition_img").src=contextPath+"/struts/simple/report/resource/skin/"+skin+"/images/stuck.gif";
	}
	
	 if(aElement.value=="1"){//
	 	olapOpenChartLayer(reportid);
	 }else{
	 	//olapOpenChartLayerRelative(reportid);
	 	//olapChartDynamicDraw(contextPath,reportid); 
	 	StopDrag(document.getElementById(reportid+"_chart_td"),event,reportid);
	 	var chartDiv= document.getElementById(reportid+"_chart_div");
	 	chartDiv.style.display = "none";
	 	if(document.getElementById("bodybg"))
	 		document.getElementById("bodybg").style.display = "none";

		chartDiv.style.position = "relative";
		chartDiv.style.zIndex = 0;
	 	chartDiv.style.top = 10;
		chartDiv.style.left = 0;
		
		olapChartDynamicDraw(contextPath,reportid);
		chartDiv.style.display = "";
		
	 	
	 }
	 //保存参数到session
	 //olapSaveToSession(contextPath,'olap.chartShowPosition',aElement.value);
	 toppage(30,0);
}

/**
 *固定位置显示图表
 */
function olapOpenChartLayerRelative(reportid) {
	StopDrag(document.getElementById(reportid+"_chart_td"),event,reportid);
 	var chartDiv= document.getElementById(reportid+"_chart_div");
 	chartDiv.style.display = "none";
 	if(document.getElementById("bodybg"))
 		document.getElementById("bodybg").style.display = "none";

	chartDiv.style.position = "relative";
	chartDiv.style.zIndex = 0;
 	chartDiv.style.top = 10;
	chartDiv.style.left = 0;
	chartDiv.style.display = "";
}

/**
 *弹出图表显示层
 */
function olapOpenChartLayer(reportid,cellElement) {
	var conId = reportid+"_chart_div";
	/*
	var arrayPageSize = getPageSize();//invoke getPageSize()
	if (!document.getElementById("bodybg")) {
		//create backgroung div
		var bodyBack = document.createElement("div");
		bodyBack.setAttribute("id", "bodybg");
		bodyBack.style.position = "absolute";
		bodyBack.style.width = (arrayPageSize[0] + "px");
		bodyBack.style.height = (arrayPageSize[1] + "px");
		bodyBack.style.zIndex = 98;
		bodyBack.style.top = 0;
		bodyBack.style.left = 0;
		bodyBack.style.filter = "alpha(opacity=40)";
		bodyBack.style.opacity = 0.4;
		bodyBack.style.background = "#ddf";
		//realise div
		document.body.appendChild(bodyBack);
	}
	

	//display background div
	document.getElementById("bodybg").style.display = "";
	*/
	//display content div
	var popObj = document.getElementById(conId);
	popObj.style.zIndex = 99;
	if(cellElement != undefined){
		var offsetPos = getAbsolutePos(cellElement);//conObj.offsetWidth, conObj.offsetHeight
		var vTop = offsetPos.y+cellElement.offsetHeight+10;
		var vLeft = offsetPos.x+cellElement.offsetWidth;
	}else{
		var offsetPos = getAbsolutePos(popObj);//conObj.offsetWidth, conObj.offsetHeight
		var vTop = offsetPos.y;
		var vLeft = offsetPos.x;
	}
	popObj.style.position = "absolute";
	popObj.style.top = vTop+ "px";
	popObj.style.left = vLeft+ "px";
	popObj.style.display = "";
	//olapResumeLastChartCellStyle(reportid);
	
}

/**
 *弹出图表设置显示层
 */
function olapOpenChartConfigLayer(reportid,cellElement) {
	var conId = reportid+"_chart_config_div";

	var arrayPageSize = getPageSize();//invoke getPageSize()
	if (!document.getElementById("bodybg")) {
		//create backgroung div
		var bodyBack = document.createElement("div");
		bodyBack.setAttribute("id", "bodybg");
		bodyBack.style.position = "absolute";
		bodyBack.style.width = (arrayPageSize[0] + "px");
		bodyBack.style.height = (arrayPageSize[1] + "px");
		bodyBack.style.zIndex = 98;
		bodyBack.style.top = 0;
		bodyBack.style.left = 0;
		bodyBack.style.filter = "alpha(opacity=40)";
		bodyBack.style.opacity = 0.4;
		bodyBack.style.background = "#ddf";
		//realise div
		document.body.appendChild(bodyBack);
	}
	

	//display background div
	document.getElementById("bodybg").style.display = "";

	//display content div
	var popObj = document.getElementById(conId);
	popObj.style.zIndex = 100;
	var conSize =getConSize(conId);
	var offsetPos = getAbsolutePos(cellElement);//conObj.offsetWidth, conObj.offsetHeight
	var vTop = offsetPos.y+cellElement.offsetHeight+30;
	var vLeft = offsetPos.x-conSize[0]/2;

	popObj.style.position = "absolute";
	popObj.style.top = vTop+ "px";
	popObj.style.left = vLeft+ "px";
	popObj.style.display = "";
	//olapResumeLastChartCellStyle(reportid);
	
}
/**
 *关闭图表设置显示层
 */
function olapCloseChartConfigLayer(reportid) {
	document.getElementById(reportid+"_chart_config_div").style.display = "none";
	if(document.getElementById("bodybg"))
		document.getElementById("bodybg").style.display = "none";
	return false;
}


/**
 *弹出图表显示层
 */
function olapCloseChartLayer(reportid) {
	document.getElementById(reportid+"_chart_div").style.display = "none";
	if(document.getElementById("bodybg"))
		document.getElementById("bodybg").style.display = "none";
	if(document.getElementById(reportid+"olapChartArrow"))
		document.getElementById(reportid+"olapChartArrow").style.display = "none";
	olapResumeLastChartCellStyle(reportid);
	return false;
}
//get content div orginal size
function getConSize(conId) {
	var conObj = document.getElementById(conId);
	conObj.style.position = "absolute";
	conObj.style.left = -1000 + "px";
	conObj.style.display = "";
	var arrayConSize = [conObj.offsetWidth, conObj.offsetHeight];
	conObj.style.display = "none";
	return arrayConSize;
}

//get page fact size
function getPageSize() {
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else {
		if (document.body.scrollHeight > document.body.offsetHeight) {
			xScroll = document.body.scrollWidth;
			yScroll = document.body.scrollHeight;
		} else {
			xScroll = document.body.offsetWidth;
			yScroll = document.body.offsetHeight;
		}
	}
	var windowWidth, windowHeight;
//var pageHeight,pageWidth;
	if (self.innerHeight) {
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
	} else {
		if (document.documentElement && document.documentElement.clientHeight) {
			windowWidth = document.documentElement.clientWidth;
			windowHeight = document.documentElement.clientHeight;
		} else {
			if (document.body) {
				windowWidth = document.body.clientWidth;
				windowHeight = document.body.clientHeight;
			}
		}
	}
	var pageWidth, pageHeight;
	if (yScroll < windowHeight) {
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
	if (xScroll < windowWidth) {
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}
	arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
	return arrayPageSize;
}

//drap
//defined hotpoint\uff1aonMousedown="StartDrag(this)" onMouseup="StopDrag(this)" onMousemove="Drag(this)"
var move = false, oldcolor, _X, _Y;
var isIE=(navigator.appVersion.indexOf("MSIE")!=-1)?true:false;
function StartDrag(obj,ev,reportid,conId) { //defined drap function
	
	if(document.getElementById(reportid+"_olap_chartShowPosition") &&document.getElementById(reportid+"_olap_chartShowPosition").value==2)
		return ;
	else{
		//alert('StartDrag');
		ev=ev||window.event;
		isIE? obj.setCapture():null; //stack this mouse move
		oldcolor = obj.style.backgroundColor;
		obj.style.background ="#BED2D6";// "#999";
		move = true;
	//get mouse relative content position
		var parentwin = document.getElementById(conId);
		_X = parentwin.offsetLeft - ev.clientX;
		_Y = parentwin.offsetTop - ev.clientY;
		//toppage(30,0);
	}
}
function Drag(obj,ev,reportid,conId) {        //define drap function

	if(document.getElementById(reportid+"_olap_chartShowPosition") &&document.getElementById(reportid+"_olap_chartShowPosition").value==2)
		return ;
	else{
		ev=ev||window.event;
		if (move) {
			//alert('StartDrag');
			var parentwin = document.getElementById(conId);
			parentwin.style.left = ev.clientX + _X;
			parentwin.style.top = ev.clientY + _Y;
			toppage(30,0);
		}
	}
}
function StopDrag(obj,ev,reportid) {   //defined stop drag function
		//alert(oldcolor);
		if(oldcolor!= undefined)
			obj.style.background = oldcolor;
		isIE? obj.releaseCapture():null; // stop stack this mouse
		move = false;
		document.onmousemove = null;
}
/*******************olap图表弹出层 end*******************************************************************************************************************/

/*******************JS Map对象定义*******************************************************************************************************************/
	function comm_map_struct(key, value) {    
   this.key = key;    
   this.value = value;    
 }    
     
 function comm_map_put(key, value){    
   for (var i = 0; i < this.comm_map_arr.length; i++) {    
     if ( this.comm_map_arr[i].key === key ) {    
       this.comm_map_arr[i].value = value;    
       return;    
     }    
   }    
   this.comm_map_arr[this.comm_map_arr.length] = new comm_map_struct(key, value);    
 }    
     
 function comm_map_get(key) {    
   for (var i = 0; i < this.comm_map_arr.length; i++) {    
     if ( this.comm_map_arr[i].key === key ) {    
       return this.comm_map_arr[i].value;    
     }    
   }    
   return null;    
 }    
     
 function comm_map_remove(key) {    
   var v;    
   for (var i = 0; i < this.comm_map_arr.length; i++) {    
     v = this.comm_map_arr.pop();    
     if ( v.key === key ) {    
       continue;    
     }    
     this.comm_map_arr.unshift(v);    
   }    
 }    
     
 function comm_map_size() {    
   return this.comm_map_arr.length;    
 }    
     
 function comm_map_isEmpty() {    
   return this.comm_map_arr.length <= 0;    
 }    
  
 /**
  * JS Map对象,提供put(key,value),get(key),remove(),size(),isEmpty(),arr 等方法.
  */   
 function Map() {    
   this.comm_map_arr = new Array();    
   this.get = comm_map_get;    
   this.put = comm_map_put;    
   this.remove = comm_map_remove;    
   this.size = comm_map_size;    
   this.isEmpty = comm_map_isEmpty; 
   this.arr = this.comm_map_arr;     
 }    
/*******************JS Map对象定义 end*******************************************************************************************************************/

/*******************平滑跳转到锚点(Anchor)******************************************************************************************************************/
// 转换为数字 
function anchor_intval(v) 
{ 
    v = parseInt(v); 
    return isNaN(v) ? 0 : v; 
} 

// 获取元素信息 
function anchor_getPos(e) 
{ 
    var l = 0; 
    var t  = 0; 
    var w = anchor_intval(e.style.width); 
    var h = anchor_intval(e.style.height); 
    var wb = e.offsetWidth; 
    var hb = e.offsetHeight; 
    while (e.offsetParent){ 
        l += e.offsetLeft + (e.currentStyle?anchor_intval(e.currentStyle.borderLeftWidth):0); 
        t += e.offsetTop  + (e.currentStyle?anchor_intval(e.currentStyle.borderTopWidth):0); 
        e = e.offsetParent; 
    } 
    l += e.offsetLeft + (e.currentStyle?anchor_intval(e.currentStyle.borderLeftWidth):0); 
    t  += e.offsetTop  + (e.currentStyle?anchor_intval(e.currentStyle.borderTopWidth):0); 
    return {x:l, y:t, w:w, h:h, wb:wb, hb:hb}; 
} 

// 获取滚动条信息 
function anchor_getScroll()  
{ 
    var t, l, w, h; 
     
    if (document.documentElement && document.documentElement.scrollTop) { 
        t = document.documentElement.scrollTop; 
        l = document.documentElement.scrollLeft; 
        w = document.documentElement.scrollWidth; 
        h = document.documentElement.scrollHeight; 
    } else if (document.body) { 
        t = document.body.scrollTop; 
        l = document.body.scrollLeft; 
        w = document.body.scrollWidth; 
        h = document.body.scrollHeight; 
    } 
    return { t: t, l: l, w: w, h: h }; 
} 

// 锚点(Anchor)间平滑跳转 
function scrollerToAnchor(el, duration) 
{ 
    if(typeof el != 'object') { el = document.getElementById(el); } 

    if(!el) return; 

    var z = this; 
    z.el = el; 
    z.p = anchor_getPos(el); 
    z.s = anchor_getScroll(); 
    z.clear = function(){window.clearInterval(z.timer);z.timer=null}; 
    z.t=(new Date).getTime(); 

    z.step = function(){ 
        var t = (new Date).getTime(); 
        var p = (t - z.t) / duration; 
        if (t >= duration + z.t) { 
            z.clear(); 
            window.setTimeout(function(){z.scroll(z.p.y, z.p.x)},13); 
        } else { 
            st = ((-Math.cos(p*Math.PI)/2) + 0.5) * (z.p.y-z.s.t) + z.s.t; 
            sl = ((-Math.cos(p*Math.PI)/2) + 0.5) * (z.p.x-z.s.l) + z.s.l; 
            z.scroll(st, sl); 
        } 
    }; 
    z.scroll = function (t, l){window.scrollTo(l, t)}; 
    z.timer = window.setInterval(function(){z.step();},13); 
}
/*******************平滑跳转到锚点(Anchor) end*******************************************************************************************************************/

/*******************收藏夹功能*******************************************************************************************************************/
//打开收藏夹层
function rp_ShowChildLayer(target_element,show_element) {
	var top=0,left=0;
	current_obj=target_element;
	target_obj=target_element;
	while (current_obj.tagName!="BODY") {
		top=top+current_obj.offsetTop;
		left=left+current_obj.offsetLeft;
		current_obj=current_obj.offsetParent;
	}
	show_element.style.left=left+2;
	show_element.style.top=top+target_obj.offsetHeight+1;
	target_obj.focus();
	show_element.style.visibility='visible';
}

//处理收藏夹设置值
function rp_CheckFavoritesValue(sList){
	//alert(sList);
	if (sList!=''&&(sList!='$')&&(sList!=undefined)) {
		 var next_div=0;
		if(reportResult.document.getElementById(reportid+'_span') != undefined){
		   var reportid="olap_${topic_node}";
		  
		   var favorites_new_setup_name_value = sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		   var favorites_if_default = sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		   var favorites_include_area = sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		   var favorites_include_date = sList.substring(next_div);
		   //alert(sList+"\n"+favorites_new_setup_name_value+" "+favorites_if_default+" "+favorites_include_area+" "+favorites_include_date);
		    var aElement = reportResult.document.getElementById(reportid+"_favorites_new_setup_name");
		    if(!aElement){
				aElement =reportResult.document.createElement("<input type=hidden id=\""+reportid+"_favorites_new_setup_name\" name='favorites_new_setup_name_value' value=\""+favorites_new_setup_name_value+"\">"); 
				reportResult.document.getElementById(reportid+'_span').appendChild(aElement); 
			}else{
				aElement.value=favorites_new_setup_name_value; 
			}
			aElement = reportResult.document.getElementById(reportid+"_favorites_if_default");
		    if(!aElement){
				aElement =reportResult.document.createElement("<input type=hidden id=\""+reportid+"_favorites_if_default\" name='favorites_if_default' value=\""+favorites_if_default+"\">"); 
				reportResult.document.getElementById(reportid+'_span').appendChild(aElement); 
			}else{
				aElement.value=favorites_if_default; 
			}
			aElement = reportResult.document.getElementById(reportid+"_favorites_include_area");
		    if(!aElement){
				aElement =reportResult.document.createElement("<input type=hidden id=\""+reportid+"_favorites_include_area\" name='favorites_include_area' value=\""+favorites_include_area+"\">"); 
				reportResult.document.getElementById(reportid+'_span').appendChild(aElement); 
			}else{
				aElement.value=favorites_include_area; 
			}
			aElement = reportResult.document.getElementById(reportid+"_favorites_include_date");
		    if(!aElement){
				aElement =reportResult.document.createElement("<input type=hidden id=\""+reportid+"_favorites_include_date\" name='favorites_include_date' value=\""+favorites_include_date+"\">"); 
				reportResult.document.getElementById(reportid+'_span').appendChild(aElement); 
			}else{
				aElement.value=favorites_include_date; 
			}
			
			//alert(reportResult.document.getElementById(reportid+'_span').innerHTML);
			reportResult.document.getElementById(reportid+'_form').action='report/view/insertFavorites.shtml'; 
		   	reportResult.document.getElementById(reportid+'_form').target='frame_staff_setup'; 
		    reportResult.document.getElementById(reportid+'_form').submit();
	    } else {
		    document.getElementById("favorites_setup_name").value=sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		    document.getElementById("favorites_if_default").value=sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		    document.getElementById("favorites_include_area").value=sList.substring(next_div,sList.indexOf('$',next_div));
		    next_div=sList.indexOf('$',next_div)+1;
		    document.getElementById("favorites_include_date").value=sList.substring(next_div);
		    document.getElementById("listForm").action='report/view/insertFavorites.shtml';
		    document.getElementById("listForm").target='frame_staff_setup';
		    document.getElementById("listForm").submit();
	    }
  }
}
/*******************收藏夹功能 end*******************************************************************************************************************/
