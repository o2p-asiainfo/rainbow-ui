//open div
function openLayer(objId, conId) {
	var arrayPageSize = getPageSize();//invoke getPageSize()
	var arrayPageScroll = getPageScroll();//invoke getPageScroll()
	if (!document.getElementById("popupAddr")) {
//create open div
		var popupDiv = document.createElement("div");
//defined this css
		popupDiv.setAttribute("id", "popupAddr");
		popupDiv.style.position = "absolute";
		popupDiv.style.border = "1px solid #ccc";
		popupDiv.style.background = "#fff";
		popupDiv.style.zIndex = 99;
//create backgroung div
		var bodyBack = document.createElement("div");
		bodyBack.setAttribute("id", "bodybg");
		bodyBack.style.position = "absolute";
		bodyBack.style.width = (arrayPageSize[0] + "px");
		bodyBack.style.height = (arrayPageSize[1] + "px");
		bodyBack.style.zIndex = 98;
		bodyBack.style.top = 0;
		bodyBack.style.left = 0;
		bodyBack.style.filter = "alpha(opacity=50)";
		bodyBack.style.opacity = 0.5;
		bodyBack.style.background = "#ddf";
//realise div
		var mybody = document.getElementById(objId);
		insertAfter(popupDiv, mybody);//indoke insertAfter()
		insertAfter(bodyBack, mybody);//indoke insertAfter()
	}
//display background div
	document.getElementById("bodybg").style.display = "";
//display content div
	var popObj = document.getElementById("popupAddr");
	popObj.innerHTML = document.getElementById(conId).innerHTML;
	popObj.style.display = "";
//let open div center in the page
// popObj.style.width = "600px";
// popObj.style.height = "400px";
// popObj.style.top = arrayPageScroll[1] + (arrayPageSize[3] - 35 - 400) / 2 + 'px';
// popObj.style.left = (arrayPageSize[0] - 20 - 600) / 2 + 'px';

	
	var arrayConSize = getConSize(conId);
	//popObj.style.top = arrayPageScroll[1] + (arrayPageSize[3] - arrayConSize[1]) / 2 - 50 + "px";
	//popObj.style.left = (arrayPageSize[0] - arrayConSize[0]) / 2 - 30 + "px";
	var vTop = document.body.scrollTop+event.clientY;
	if(vTop>arrayConSize[1]){
		vTop = document.body.scrollTop+event.clientY-arrayConSize[1];
	}else if(vTop>(arrayConSize[1]/2)){
		vTop = document.body.scrollTop+event.clientY-arrayConSize[1]/2;
	}
	popObj.style.top = vTop+ "px";
	popObj.style.left = (document.body.scrollLeft+event.clientX+20)+ "px";
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
function insertAfter(newElement, targetElement) {//insert
	var parent = targetElement.parentNode;
	if (parent.lastChild == targetElement) {
		parent.appendChild(newElement);
	} else {
		parent.insertBefore(newElement, targetElement.nextSibling);
	}
}
//get scroll height
function getPageScroll() {
	var yScroll;
	if (self.pageYOffset) {
		yScroll = self.pageYOffset;
	} else {
		if (document.documentElement && document.documentElement.scrollTop) {
			yScroll = document.documentElement.scrollTop;
		} else {
			if (document.body) {
				yScroll = document.body.scrollTop;
			}
		}
	}
	arrayPageScroll = new Array("", yScroll);
	return arrayPageScroll;
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
//close div
function closeLayer() {
	document.getElementById("popupAddr").style.display = "none";
	document.getElementById("bodybg").style.display = "none";
	return false;
}
//drap
//defined hotpoint：onMousedown="StartDrag(this)" onMouseup="StopDrag(this)" onMousemove="Drag(this)"
var move = false, oldcolor, _X, _Y;
var isIE=(navigator.appVersion.indexOf("MSIE")!=-1)?true:false;
function StartDrag(obj,ev) { //defined drap function
	ev=ev||window.event;
	isIE? obj.setCapture():null; //stack this mouse move
	oldcolor = obj.style.backgroundColor;
	obj.style.background = "#999";
	move = true;
//get mouse relative content position
	var parentwin = document.getElementById("popupAddr");
	_X = parentwin.offsetLeft - ev.clientX;
	_Y = parentwin.offsetTop - ev.clientY;
}
function Drag(obj,ev) {        //define drap function
	ev=ev||window.event;
	if (move) {
		var parentwin = document.getElementById("popupAddr");
		parentwin.style.left = ev.clientX + _X;
		parentwin.style.top = ev.clientY + _Y;
	}
}
function StopDrag(obj,ev) {   //defined stop drag function
	obj.style.background = oldcolor;
	isIE? obj.releaseCapture():null; // stop stack this mouse
	move = false;
	document.onmousemove = null;
}
//get form element
function operateMyFormObject(form) {
	var elements = form.elements;// Enumeration the form elements           
	var element;
	var i;
	var postContent = "";// Form contents need to submit          
	for (i = 0; i < elements.length; ++i) {
		var element = elements[i];
		if (element.type == "text" || element.type == "textarea" || element.type == "hidden") {
			postContent += element.name.split('.')[element.name.split('.').length-1] + "=" + element.value + "&";
		} else {
			if (element.type == "select-one" || element.type == "select-multiple") {
				var options = element.options, j, item;
				for (j = 0; j < options.length; ++j) {
					item = options[j];
					if (item.selected) {
						postContent += element.name.split('.')[element.name.split('.').length-1] + "=" + item.value + "&";
					}
				}
			} else {
				if (element.type == "checkbox" || element.type == "radio") {
					if (element.checked) {
						postContent += element.name.split('.')[element.name.split('.').length-1] + "=" + element.value + "&";
					}
				} else {
					if (element.type == "file") {
						if (element.value != "") {
							postContent += element.name.split('.')[element.name.split('.').length-1] + "=" + element.value + "&";
						}
					} else {
						postContent += element.name.split('.')[element.name.split('.').length-1] + "=" + element.value + "&";
					}
				}
			}
		}
	}
	return postContent;
}

//get form by element id
function getMyDefinedFormName(obj) {
	if(obj.nodeName.toLowerCase()=="form")
	{
	    return obj;
	}else{
	     if(obj.parentNode)
		 {
		    
		    return getMyDefinedFormName(obj.parentNode);
			 
		 }else{
		     return null;
		 }
	}	
	//if (obj.parentNode.nodeName.toLowerCase() == "form") {
		//alert(obj.parentNode.getAttribute("name"));
	//	return obj.parentNode;
	//} else {
	//	getMyDefinedFormName(obj.parentNode);
	//}
}

//set selectColumn checkbox
/*
 * hidden id
 * tid target div's id
 * pageid 
 * optype 0选择完列后下载 1选择完列后打开下载条数层
 * grid 的id
 */
function setTableCheckbox(hid,tid,pageid,optype){
	var title = document.getElementById(hid).getAttribute('title');
	var column = document.getElementById(hid).getAttribute('column');
	var allTitle = document.getElementById(hid).getAttribute('allTitle');
	var allColumn = document.getElementById(hid).getAttribute('allColumn');
	var target = document.getElementById(tid);
	var s = '';
	s="<table align='center' width='50%'>";
	var flag = '';
	for(var i=0;i<allTitle.split(';title;').length-1;i++){
		flag='';
		for(var j=0;j<title.split(';title;').length-1;j++){
			if(allTitle.split(';title;')[i]==title.split(';title;')[j]){
				flag = 'checked';
			}
		}
		s+="<tr><td width='10%' align='right'><input type='checkbox' onclick=\"operateHid('"+hid+"','"+allTitle.split(';title;')[i]+"','"+allColumn.split(';column;')[i]+"')\" "+flag+"/></td><td align='left'>"+allTitle.split(";title;")[i]+"</td></tr>";
	}
	if(optype==0){
		s+="<tr><td align='center' colspan='2'><span style='cursor:pointer;' onclick=\"qdownload"+pageid+"();\">确定</span></td></tr>";
	}else{
		s+="<tr><td align='center' colspan='2'><span style='cursor:pointer;' onclick=\"openLayer('"+pageid+"myhref','"+pageid+"_con');\">确定</span></td></tr>";
	}
	s+="</table>";
	target.innerHTML = s;
}
//operate hidden attribute
/*
 * hidden id
 * title checkedbox's title 
 * column checkedbox's column 
 */
function operateHid(hid,title,column){
	var tar = document.getElementById(hid);
	var st = '';
	var sc = '';
	if((';title;'+tar.getAttribute('title')).indexOf(';title;'+title+';title;')!=-1){
		for(var i=0;i<tar.getAttribute('title').split(";title;").length-1;i++){
			if(tar.getAttribute('title').split(";title;")[i]!=title){
				st+=tar.getAttribute('title').split(";title;")[i]+";title;";
			}
			if(tar.getAttribute('column').split(";column;")[i]!=column){
				sc+=tar.getAttribute('column').split(";column;")[i]+";column;";
			}
		}
		tar.setAttribute('title',st);
		tar.setAttribute('column',sc);
	}else{
		tar.setAttribute('title',tar.getAttribute('title')+title+';title;');
		tar.setAttribute('column',tar.getAttribute('column')+column+';column;');
	}
	
	
}

