${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery-ui.min.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.multiselect.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/jquery-ui.css")?string}
<script type="text/javascript">
     var url = "${base}/rainbowui/getMulSelectBoxData.shtml?t=<#if parameters.method?exists>&method=${parameters.method?html}</#if><#if parameters.params?exists>&params=${parameters.params?html}</#if><#if parameters.listKey?exists>&listKey=${parameters.listKey?html}</#if><#if parameters.listValue?exists>&listValue=${parameters.listValue?html}</#if>";
    var  checkbox_id = "";
	$(function(){
		${parameters.id?html} ();
	});
	
	function ${parameters.id?html} (){
		 checkbox_id = "${parameters.id?html}";
		$("#${parameters.id?html}").multiselect({
				  checkbox_id:checkbox_id,
				  <#if parameters.header?exists>header:${parameters.header}, </#if>
				  <#if parameters.checkAllText?exists>checkAllText:"${parameters.checkAllText}", </#if>
				  <#if parameters.uncheckAllText?exists>uncheckAllText:"${parameters.uncheckAllText}", </#if>
				  <#if parameters.noneSelectedText?exists>noneSelectedText:"${parameters.noneSelectedText}", </#if>
				  <#if parameters.selectedList?exists>selectedList:"${parameters.selectedList}"</#if>
				   <#if parameters.loadMode?exists>
				   		<#if parameters.loadMode == true>
				   			,url:url
				 		</#if>
				   </#if>
	        });
	   	 <#if parameters.style?exists>
	   	 	$("button[id='${parameters.id?html}']").attr("style","${parameters.style?html};font-size:12px;background:#FFFFFF");
	   	 </#if>
	   	 <#if parameters.width?exists>
	   	 	$("button[id='${parameters.id?html}']").attr("style","width:${parameters.width?html}px;font-size:12px;background:#FFFFFF");
	   	 </#if>
	     <#if parameters.layerWidth?exists>
	     	  $("div[id='${parameters.id?html}']").css("width","${parameters.layerWidth?html}");
	     </#if>
	     <#if parameters.layerHeight?exists>
	     	    $("div[id='${parameters.id?html}']").css("height","${parameters.layerHeight?html}");
	     </#if>
	}
</script>
<select multiple="multiple" 
	<#if parameters.id?exists> id="${parameters.id?html}"</#if>  
<#if parameters.name?exists> name="${parameters.name?html}"</#if> 
  <#if parameters.style?exists> style="${parameters.style?html}"</#if> >
   <#if parameters.list?exists>
   		 <@s.iterator value="parameters.list">
   		 		<#if parameters.listKey?exists>
   		 			 <#if stack.findValue(parameters.listKey)?exists>
   		 			 		 <#assign itemKey = stack.findValue(parameters.listKey)/>
             		 <#assign itemKeyStr = itemKey.toString()/>
             <#else>
             		<#assign itemKey = ''/>
             		<#assign itemKeyStr = ''/>
   		 			 </#if>
   		 	  <#else>
             <#assign itemKey = stack.findValue('top')/>
             <#assign itemKeyStr = itemKey.toString()/>
   		 		</#if>
   		 		
   		 		 <#if parameters.listValue?exists>
             <#if stack.findString(parameters.listValue)?exists>
             		<#assign itemValue = stack.findString(parameters.listValue)/>
             <#else>
             		<#assign itemValue = ''/>
             </#if>
           <#else>
             <#assign itemValue = stack.findString('top')/>
           </#if>
           <#rt/>
           <option value="${itemKeyStr?html}">${itemValue?html}</option>
   		 </@s.iterator>
   </#if>
</select>


