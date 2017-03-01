${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyui.min.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/panel.css")?string}
<div class="easyui-panel" id="${parameters.id?html}"
	 style="<#rt/>
	   <#if parameters.width?exists>width:${parameters.width?html};</#if>
	   <#if parameters.height?exists>height:${parameters.height?html};</#if>
	 "<#rt/>
	 <#if parameters.fit?exists>fit="${parameters.fit?string}"</#if>
	 <#if parameters.title?exists>title="${parameters.title?html}"</#if>
	 data-options="<#rt/> iconCls:'icon-save'
	 	<#if parameters.collapsible?exists>,collapsible:${parameters.collapsible?string}</#if>
	 	<#if parameters.minimizable?exists>,minimizable:${parameters.minimizable?string}</#if>
	 	<#if parameters.maximizable?exists>,maximizable:${parameters.maximizable?string}</#if>
	 	<#if parameters.closable?exists>,closable:${parameters.closable?string}</#if>
	 	<#if parameters.tools?exists>
	 		<#if parameters.tools == true>
	 		,tools:[{
	 			<#if parameters.iconCls?exists>iconCls:'${parameters.iconCls?html}'</#if>
	 			<#if parameters.iconCls?exists && parameters.handlerFunc?exists>,</#if>
	 			<#if parameters.handlerFunc?exists>handler:function(){${parameters.handlerFunc?html}}</#if>
	 		}]
	 		</#if>
	 	</#if>
	 "<#rt/>
>
