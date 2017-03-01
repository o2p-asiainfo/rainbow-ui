${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/easyui.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyui.min.js")?string}

<div  <#if parameters.divId?exists>id="${parameters.divId?html}" </#if> class="easyui-window" data-options="modal:true,closed:${parameters.closed?html},collapsible:false,minimizable:false,maximizable:false"  <#if parameters.divTitle?exists>title="${parameters.divTitle?html}" </#if>
 style="WIDTH: ${parameters.divWidth?html}px; HEIGHT:${parameters.divHeight?html}px;padding:0px;" >
	<iframe <#if parameters.iframeId?exists>  id="${parameters.iframeId?html}" </#if> src="${parameters.iframeSrc?default('')}"  
	 <#if parameters.iframeScrolling?exists> scrolling="${parameters.iframeScrolling?html}" </#if>
	 <#if parameters.frameborder?exists> frameborder="${parameters.frameborder?html}" </#if>
	  width="${parameters.iframeWidth?html}"  height="${parameters.iframeHeight?html}"  style="VERFLOW: hidden;border: 0px;"></iframe>
</div>