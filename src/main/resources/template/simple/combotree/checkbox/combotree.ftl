${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}

${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyui.min.js")?string}


${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}   
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/easyui.css")?string}
  

多选树形下拉框：
<script>
	$(function(){
			$("#${parameters.id?html}").combotree({	

			});
		});
		

</script>

 
<select class="rainbow-ui-combotree"

<#if parameters.id?exists>
 id="${parameters.id?html}"  
</#if>  

 
<#if parameters.skin?exists>
 skin="${parameters.skin?html}"    
</#if>


 url="${base}/rainbowui/combotree_url.shtml?servlet_name=combotree<#if parameters.initmethod?exists>&method=${parameters.initmethod?html}<#else>&method=${parameters.method?html}</#if><#if parameters.initparams?exists>&params=${parameters.initparams?html}<#else>&params=${parameters.params?html}</#if>&treeid=${parameters.id?html}&treetype=checkbox<#if parameters.parentnode?exists> &parentnode=${parameters.parentnode?html}</#if>"    


<#if parameters.params?exists>
 params="${parameters.params?html}"      
</#if>

<#if parameters.width?exists>
 width="${parameters.width?html}"    
</#if>


 multiple="true"    


<#if parameters.mode?exists>
 mode="${parameters.mode?html}"    
</#if>



></select>





