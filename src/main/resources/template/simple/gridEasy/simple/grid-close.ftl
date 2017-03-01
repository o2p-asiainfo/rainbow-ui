			cols[0] = header;
			$("#${parameters.id?html}").datagrid({
				<#if parameters.title?exists>
				title:'${parameters.title?html}'
			  </#if>
			  <#if parameters.iconCls?exists>
				,iconCls:'${parameters.iconCls?html}'
			  </#if>
				<#if parameters.width?exists>
				,width:${parameters.width?html}
			  </#if>
			  <#if parameters.height?exists>
				,height:${parameters.height?html}
			  </#if>
			  <#if parameters.collapsible?exists>
				,collapsible:${parameters.collapsible?html}			
			  </#if>
			  <#if parameters.queryParams?exists>
			   ,queryParams: {
		       queryParamsData: '${parameters.queryParamsData?html}'
	           }
	          </#if>
	           ,autoRowHeight:true
		      <#if parameters.method?exists>
				,url:'${base}/rainbowui/getGridEasyData.shtml?method=${parameters.method?html}'
			  <#else>
				,url:'${base}/rainbowui/getGridEasyData.shtml'
		      </#if>
			  <#if parameters.striped?exists>
				,striped:${parameters.striped?html}
			  </#if>
			  <#if parameters.sortName?exists>
				,sortName:'${parameters.sortName?html}'
			  </#if>
			  <#if parameters.fit?exists>
				,fit:${parameters.fit?html}
			  </#if>
			  <#if parameters.sortOrder?exists>
				,sortOrder:'${parameters.sortOrder?html}'
			  </#if>			
			  <#if parameters.resizable?exists>
				,resizable:${parameters.resizable?html}
			  </#if>
			  <#if parameters.remoteSort?exists>
				,remoteSort:${parameters.remoteSort?html}
			  </#if>
			  <#if parameters.singleSelect?exists>
				,singleSelect:${parameters.singleSelect?html}
			  </#if>
			  <#if parameters.rownumbers?exists>
				,rownumbers:${parameters.rownumbers?html}
			  </#if> 
			  <#if parameters.frozenColumns?exists>
				,frozenColumns:[[
                 {field:'ck',checkbox:true,width:100}
                 ]] 
			  </#if>
			 <#if parameters.columns?exists>
				,columns:${parameters.columns?html}
			  <#else>
				,columns:cols
			  </#if>
			  ,fitColumns:true
			  <#if parameters.pageList?exists>
				,pageList : [${parameters.pageList?html},20]
			  </#if> 
			  <#if parameters.toolbar?exists>
				,toolbar:[{
				   id:'btnadd',
				   iconCls:'icon-add',
		           text:'<@s.property value="%{getText('rainbow.ui.addBtn')}"/>',
		           handler:function(){ 
		           $('#btnsave').linkbutton('enable'); 
                   addMehtod();
                   }
				},{
					id:'btncut',
					iconCls:'icon-edit',
					text:'<@s.property value="%{getText('rainbow.ui.updateBtn')}"/>',
		            handler:function(){ 
		            $('#btnsave').linkbutton('enable'); 
                    updateMethod();
                   }
				},'-',{
					id:'btnsave',
					iconCls:'icon-remove',
				    text:'<@s.property value="%{getText('rainbow.ui.deleteBtn')}"/>',
		            handler:function(){ 
		            $('#btnsave').linkbutton('enable'); 
                    deleteMethod();
                   }
                   }
                   <#if parameters.exportMethod?exists>
                    ,{
					id:'btnsave',
					iconCls:'icon-save',
				    text:'<@s.property value="%{getText('rainbow.ui.exportBtn')}"/>',
		            handler:function(){ 
		            $('#btnsave').linkbutton('enable'); 
                    exportMethod();
                   }
                   }
                    </#if> 
				]
			  </#if>
			  <#if parameters.pageSize?exists>
				,pageSize:${parameters.pageSize?html}
			  </#if> 
			  <#if parameters.pagination?exists>
				,pagination:${parameters.pagination?html}
			  </#if>
			  <#if parameters.onClickCell?exists>
			    ,onClickCell: function(index,field,value){
		          clickMethod(index,field,value);
		        }
		      </#if> 
			  });			  
			  <#if parameters.pagination?exists>
			   var p = $('#${parameters.id?html}').datagrid('getPager');		   
  			   $(p).pagination({
       			// 显示分页信息的文字   	           
        		beforePageText: '<@s.property value="%{getText('rainbow.ui.from')}"/>',
        		afterPageText: '<@s.property value="%{getText('rainbow.ui.gridPage')}"/>    <@s.property value="%{getText('rainbow.ui.gridTotal')}"/>  {pages}  '
        		<#if parameters.pageInfo?exists> 
        		,displayMsg: '<@s.property value="%{getText('rainbow.ui.currentShow')}"/> {from} - {to}   <@s.property value="%{getText('rainbow.ui.gridTotal')}"/> {total} <@s.property value="%{getText('rainbow.ui.records')}"/>'
        		</#if>      		
        		});
        		
			  </#if>   
		  });
		  var sy = $.extend({}, sy);
          sy.serializeObject = function (form) { 
          var o = {};
          $.each(form.serializeArray(), function (index) {
          if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
          } else {
            o[this['name']] = this['value'];
          }
          });
          return o;
          };
          <#if parameters.fit?exists && parameters.fit== 'true'>
	          $(window).resize(function() {
	          	$("#${parameters.id?html}").datagrid("resize",{width:$(document.body).width()});
	          });
		  </#if> 	
	</script>
	<#if parameters.fitHeight?exists>
	 <div style="height:${parameters.fitHeight?html}px">
	<#else>
	 <div style="height:340px">
	 </#if> 	
	<table id="${parameters.id?html}">
	
    </table>
    </div> 