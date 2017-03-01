		cols[0] = header;
		$("#${parameters.id?html}").datagrid({
			<#if parameters.method?exists>
				url:'${base}/rainbowui/getGridData.shtml?method=${parameters.method?html}',
			<#else>
				url:'${base}/rainbowui/getGridData.shtml',
			</#if>
			<#if parameters.title?exists>
				title:'${parameters.title?html}',
			</#if>
			<#if parameters.fit?exists>
				fit: ${parameters.fit?html},
			</#if>
			<#if parameters.width?exists>
				width:${parameters.width?html},
			</#if>
			<#if parameters.singleSelect?exists>
				singleSelect:${parameters.singleSelect?html},
			</#if>
			<#if parameters.height?exists>
				height:${parameters.height?html},
			</#if>
			<#if parameters.nowrap?exists>
				nowrap:${parameters.nowrap?html},
			</#if>
			<#if parameters.striped?exists>
				striped:${parameters.striped?html},
			</#if>
			<#if parameters.nowrap?exists>
				nowrap:${parameters.nowrap?html},
			</#if>
			<#if parameters.idField?exists>
				idField:${parameters.idField?html},
			</#if>
			<#if parameters.loadMsg?exists>
				loadMsg:'${parameters.loadMsg?html}',
			</#if>
			<#if parameters.rownumbers?exists>
				rownumbers:${parameters.rownumbers?html},
			</#if>
			<#if parameters.sortName?exists>
				sortName:${parameters.sortName?html},
			</#if>
			<#if parameters.sortOrder?exists>
				sortOrder:${parameters.sortOrder?html},
			</#if>
			<#if parameters.pagination?exists>
				pagination:${parameters.pagination?html},
			</#if>
			<#if parameters.fitColumns?exists>
				fitColumns:${parameters.fitColumns?html},
			</#if>
			<#if parameters.columns?exists>
				columns:${parameters.columns?html}
			<#else>
				columns:cols
			</#if>
		});
		<#if parameters.pagination?exists>
			var pager = $("#${parameters.id?html}").datagrid("getPager");
			if (pager) {
				$(pager).pagination({
					beforePageText:'第',
					afterPageText:'页    共 {pages} 页',
					displayMsg:'当前显示从 {from} 到 {to} 共 {total} 记录'
				});
			}
		</#if>
	})
</script>
<table id="${parameters.id?html}">
</table>