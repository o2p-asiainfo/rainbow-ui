${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.autocomplete.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/default/autocomplete.css")?string}
<script>
	$(function(){
			$("#${parameters.id?html}").autocomplete(	
		  '${base}/rainbowui/autocomplete_url.shtml?servlet_name=autoComplete<#if parameters.method?exists>&method=${parameters.method?html}</#if><#if parameters.params?exists>&params=${parameters.params?html}</#if>',   
        {   
   multiple: true,     
   width:150,     
   max:50,     
   mustMatch: true, 
   multipleSeparator: ' ',
   matchContains: true,    
   dataType: 'json',     
       
   parse: function(data) {   
     var rows = [];   
     for(var i=0; i<data.length; i++){   
      rows[rows.length] = {    
        data:data[i],   
        value:data[i],   
        result:data[i]
        };    
      }   
   return rows;   
     },  
      
   formatItem: function(row, i, n) {   
      return row;         
  }   
}
);
		});
</script>
<input type="text"
<#if parameters.id?exists>
 id="${parameters.id?html}"   
<#elseif parameters.name?exists>
 id="${parameters.name?html}"   
</#if>  
<#if parameters.name?exists>
 name="${parameters.name?html}"   
</#if>
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.style?exists>
 style="${parameters.style?html}"    
</#if>
<#if parameters.styleClass?exists>
 class="${parameters.styleClass?html}"    
</#if>
>



