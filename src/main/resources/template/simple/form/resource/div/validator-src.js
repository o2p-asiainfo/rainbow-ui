var comm_RayTimes=1;
var comm_input_curr;
var comm_input_old_class_name;

function commShowAlert(target_element,alert_content) {
 var top=0,left=0;
 var comm_alert_box_obj = document.getElementById("comm_alert_box");
 current_obj=target_element;
 target_obj=target_element;
 comm_RayTimes=1;
 /**
 获取对象相对于窗口顶部和左边的距离。
 **/
 while (current_obj.tagName!="BODY") {
  /**
  //文本框相对顶部距离
  **/
  top=top+current_obj.offsetTop; 
  /**
  //文本框相对左边距离
  **/
  left=left+current_obj.offsetLeft; 
  current_obj=current_obj.offsetParent;
 }
 var offsetRight =document.body.offsetWidth-left-target_obj.offsetWidth;
 //alert(offsetRight+"="+document.body.offsetWidth+"-"+left+"-"+target_obj.offsetWidth);

 if(offsetRight>=comm_alert_box_obj.offsetWidth){
 		comm_alert_box_obj.style.left=left+target_obj.offsetWidth;
 		comm_alert_box_obj.style.top=top-13;
 		document.getElementById("comm_alert_box_div").className = "comm-alert-box";
 		document.getElementById("comm_alert_box_div_outer").className = "comm-alert-box-outer";
 		document.getElementById("comm_alert_box_div_inner").className = "comm-alert-box-inner";
 } else {
 		comm_alert_box_obj.style.left=left;
 		comm_alert_box_obj.style.top=top+target_obj.offsetHeight;
 		document.getElementById("comm_alert_box_div").className = "comm-alert-box-b";
 		document.getElementById("comm_alert_box_div_outer").className = "comm-alert-box-outer-b";
 		document.getElementById("comm_alert_box_div_inner").className = "comm-alert-box-inner-b";
 }
 document.getElementById("comm_alert_text").innerHTML=alert_content;

 //alert("comm_alert_box.offsetWidth:"+comm_alert_box.offsetWidth+"comm_alert_box.offsetHeight:"+comm_alert_box.offsetHeight);
 var oldheight = target_obj.offsetHeight;
 //alert(oldheight);
 comm_input_curr = target_obj;
 comm_input_old_class_name = target_obj.className;
 target_obj.className = "comm-alert-input";
 //alert(target_obj.offsetHeight);
 target_obj.style.height=oldheight;
 target_obj.focus();
 comm_alert_box_obj.style.visibility='visible';
 //alert(comm_alert_box_obj.offsetWidth+" "+comm_alert_box_obj.offsetHeight)
 document.getElementById("comm_alert_box_iframe").style.width=comm_alert_box_obj.offsetWidth;
 document.getElementById("comm_alert_box_iframe").style.height=comm_alert_box_obj.offsetHeight;
 commRayAlert();
 
}

function commRayAlert() {
	var comm_alert_box_obj = document.getElementById("comm_alert_box");
 if (comm_RayTimes<5){ 
  if (comm_alert_box_obj.style.visibility=='visible') {
      comm_alert_box_obj.style.visibility='hidden';
      setTimeout("commRayAlert()",200);     
  }else {
        comm_alert_box_obj.style.visibility='visible';
        setTimeout("commRayAlert()",200);
       
        }
  comm_RayTimes=comm_RayTimes+1;
  
  }else{
    comm_RayTimes=1;
  }
}


document.onmousedown=new Function("document.getElementById('comm_alert_box').style.visibility='hidden';if(comm_input_curr)comm_input_curr.className=comm_input_old_class_name");