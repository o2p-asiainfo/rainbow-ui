
header[${parameters.index?html}] = {field:'${parameters.field?html}',title:title[${parameters.index?html}],sortable:true,width:${parameters.width?html},hidden:${parameters.hidden?html},align:'${parameters.align?html}'
<#if parameters.formatter?exists>
	,formatter:function(value,row,index) {
     return ${parameters.formatterMethod?html}(value,row,index);
	}
</#if>


};
		