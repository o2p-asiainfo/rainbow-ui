${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/easyui.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}   
${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyui.min.js")?string}
<script>
	$(function(){
			$("#${parameters.id?html}").combotree({	
			url:"${base}/rainbowui/combotree_url.shtml?servlet_name=combotree<#if parameters.initmethod?exists>&method=${parameters.initmethod?html}<#else>&method=${parameters.method?html}</#if><#if parameters.initparams?exists>&params=${parameters.initparams?html}<#else>&params=${parameters.params?html}</#if>&treeid=${parameters.id?html}&treetype=checkbox<#if parameters.parentnode?exists> &parentnode=${parameters.parentnode?html}</#if>"    
			<#if parameters.multiple?exists>
			, multiple:${parameters.multiple?string}    
			</#if>
			<#if parameters.cascadeCheck?exists>
			, cascadeCheck:${parameters.cascadeCheck?html}     
			</#if>
			<#if parameters.onlyLeafCheck?exists>
			, onlyLeafCheck:${parameters.onlyLeafCheck?html}      
			</#if>
			<#if parameters.nameValue?exists>
			  ,onLoadSuccess: function(){$('#${parameters.id?html}').combotree('setValues',[<@s.property value="parameters.nameValue"/>]);}
			</#if>		
			<#if parameters.width?exists>
			 ,width:"${parameters.width?html}"    
			</#if>
			<#if parameters.panelWidth?exists>
			 ,panelWidth:"${parameters.panelWidth?html}"
			</#if>	
			<#if parameters.panelHeight?exists>
			 ,panelHeight:"${parameters.panelHeight?html}"
			</#if>	
			});
		});
</script>
<select
<#if parameters.id?exists>
 id="${parameters.id?html}"   
</#if>  
<#if parameters.name?exists>
 name="${parameters.name?html}"  
</#if>
></select>





