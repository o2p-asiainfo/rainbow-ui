${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.validatebox.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.panel.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.combo.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.combobox.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyloader.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.parser.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/combobox.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/combo.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/panel.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}
<script>
	$(function(){
$("#${parameters.id?html}").combobox({
a:1<#rt/>
<#if parameters.valueField?exists>
 ,valueField:"${parameters.valueField?html}"<#rt/>
</#if>  
<#if parameters.textField?exists>
 ,textField:"${parameters.textField?html}"<#rt/>
</#if> 
 ,url:"${base}/rainbowui/combobox_url.shtml?servlet_name=combobox<#if parameters.method?exists>&method=${parameters.method?html}</#if><#if parameters.params?exists>&params=${parameters.params?html}</#if>"<#rt/>   
<#if parameters.skin?exists>
 ,skin:"${parameters.skin?html}"<#rt/>
</#if>
<#if parameters.multiple?exists>
 ,multiple:${parameters.multiple?html}<#rt/>
</#if>
<#if parameters.height?exists>
 ,height:"${parameters.height?html}"<#rt/>
</#if>
<#if parameters.width?exists>
 ,width:"${parameters.width?html}"<#rt/>
</#if>
<#if parameters.panelWidth?exists>
 ,panelWidth:"${parameters.panelWidth?html}"
</#if>	
<#if parameters.panelHeight?exists>
 ,panelHeight:"${parameters.panelHeight?html}"
</#if>	
<#if parameters.nameValue?exists>
  ,onLoadSuccess: function(){$('#${parameters.id?html}').combobox('setValues',[<@s.property value="parameters.nameValue"/>]);}
</#if>		
});		
		});	
</script>
<input 
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>  
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>  
>



