${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.layout.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.panel.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.resizable.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/layout.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/panel.css")?string}
<script>
	$(function(){
<#if parameters.renderTo?exists>
	<#if parameters.renderTo=="body"||parameters.renderTo=="BODY">
	$("${parameters.renderTo?html}").layout({});
	<#else>
	$("#${parameters.renderTo?html}").layout({});
	</#if><#rt/>
<#else>
	$("#${parameters.id?html}").layout({});
</#if>
	});
</script>
<#if !parameters.renderTo?exists>
	<div id="${parameters.id?html}" <#rt/>
	<#if parameters.fit?exists>
		fit="${parameters.fit?html}"<#rt/>
	</#if>
	 style="<#rt/>
	<#if parameters.style?exists>
	 ${parameters.style?html};<#rt/>
	</#if>
	<#if parameters.width?exists>
	 width:${parameters.width?html};<#rt/>
	</#if>
	<#if parameters.height?exists>
	 height:${parameters.height?html};<#rt/>
	</#if>
	"<#rt/>
	<#if parameters.styleClass?exists>
	class="${parameters.styleClass?html}"<#rt/>
	</#if>
	>
</#if>

