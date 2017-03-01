header[${parameters.index?html}] = {title:"${parameters.title?html}",field:'${parameters.field?html}',width:${parameters.width?html},align:'${parameters.align?html}',editor:'${parameters.editor?html}'
<#if parameters.formatter?exists>
	,formatter:function(value,row,index) {
     return fomat(value,row,index);
	}
</#if>


};
		