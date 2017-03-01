			cols[0] = header;
		    $("#${parameters.id?html}").treegrid({
		    <#if parameters.title?exists>
	        title:'${parameters.title?html}',
	        </#if>
	        <#if parameters.animate?exists>
	        animate:${parameters.animate?html},
	        </#if>
		    <#if parameters.method?exists>
		    url:'${base}/rainbowui/getGridTreeData.shtml?method=${parameters.method?html}',
			<#else>
		    url:'${base}/rainbowui/getGridTreeData.shtml',
		    </#if>
	        <#if parameters.idField?exists>
	        idField:'${parameters.idField?html}',
	        </#if>
	        <#if parameters.treeField?exists>
	        treeField:'${parameters.treeField?html}',
	        </#if>
	        <#if parameters.rownumbers?exists>
	        rownumbers:${parameters.rownumbers?html},
	        </#if>
	        <#if parameters.pagination?exists>
	        pagination:${parameters.pagination?html},
	        </#if>
	        <#if parameters.fitColumns?exists>
	        fitColumns:${parameters.fitColumns?html},
	        </#if>
	        <#if parameters.columns?exists>
	        columns:${parameters.columns?html},
	        </#if>
	        <#if parameters.autoRowHeight?exists>
	        autoRowHeight:${parameters.autoRowHeight?html},
	        </#if>
	        onBeforeExpand:function(row){
            $(this).treegrid('options').url='${base}/rainbowui/getGridTreeData.shtml?method=${parameters.method?html}&${parameters.idField?html}='+row.${parameters.idField?html};
            },
            onLoadSuccess:function(row,data){
            $(this).treegrid('options').url='${base}/rainbowui/getGridTreeData.shtml?method=${parameters.method?html}';
            },
            <#if parameters.onAfterEdit?exists>
            onAfterEdit:function(row,changes){
            var rowId = row.nodeDescId;
            $.ajax({
            url:"${base}/rainbowui/getGridTreeData.shtml?method=${parameters.onAfterEdit?html}" ,
            dataType:"text",
            data: row,
            success: function(data,textStatus){
            $.messager.alert('提示信息',data,'info');  
            }
            });
            }
            </#if>
	});	
	
	
	});	
	</script>
	<table id="${parameters.id?html}">
  </table>