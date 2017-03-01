
function ShowChildLayer(target_element,show_element) {
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