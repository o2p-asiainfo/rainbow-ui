function fn_turnPage(vflag,target,totalrecord){
	//var hidd=document.getElementById(target);
	//alert(hidd.hiddenvalue);
	//alert(encodeURI(hidd.hiddenvalue));
	//window.location.href=hidd.hiddenurl+"?&"+encodeURI(hidd.hiddenvalue)+"&"+target+".currentPage="+vflag+"&"+target+".queryflag="+totalrecord;
	//var page_form=document.getElementById("page_form_"+target);
	var v_page_currentPage=document.getElementById("page_currentPage_"+target);
	var v_page_queryflag=document.getElementById("page_queryflag_"+target);
	v_page_currentPage.value=vflag.replace(/\,/g,'');
	v_page_queryflag.value=totalrecord.replace(/\,/g,'');
	v_page_currentPage.form.submit();
}
	    
function fn_goPage(iPage,target,totalrecord,ifexcel){
	if(event.keyCode == 13){
	    event.returnValue = false;
	    fn_goPage_submit(iPage,target,totalrecord,ifexcel);
	}
}

function fn_goPage_submit(iPage,target,totalrecord,ifexcel){
	    if(ifexcel!=""){
	    	eval(ifexcel);
	    }
	    fn_turnPage(iPage,target,totalrecord);
}

function fn_selectRecord(pageRecord,iPage,target,totalrecord){
	var v_page_selectPerPage=document.getElementById("page_selectPerPage_"+target);
	var v_page_currentPage=document.getElementById("page_currentPage_"+target);
	var v_page_queryflag=document.getElementById("page_queryflag_"+target);
	v_page_currentPage.value=iPage.replace(/\,/g,'');
	v_page_queryflag.value=totalrecord.replace(/\,/g,'');
	v_page_selectPerPage.value=pageRecord.replace(/\,/g,'');
	v_page_currentPage.form.submit();
}