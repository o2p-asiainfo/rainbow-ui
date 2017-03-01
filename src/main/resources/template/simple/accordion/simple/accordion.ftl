${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/accordion.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/panel.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}

${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.accordion.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.panel.js")?string}

<script>
	$(function(){

$("#${parameters.id?html}").accordion({});

	})
</script>
<div id="${parameters.id?html}" <#rt/>
	<#if parameters.fit?exists>
		fit="${parameters.fit?string}"<#rt/>
	</#if>
	 style="<#rt/>
	<#if parameters.width?exists>
	 width:${parameters.width?html};<#rt/>
	</#if>
	<#if parameters.height?exists>
	 height:${parameters.height?html};<#rt/>
	</#if>
	"<#rt/>
	>

	

	
	
