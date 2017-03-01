${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/css/console.css")?string}



<a 
 <#if parameters.shape?html?lower_case == "s">
  class="button-base button-small"
</#if> 
  
 <#if parameters.shape?html?lower_case == "m">
  class="button-base button-middle"
</#if>

 <#if parameters.shape?html?lower_case == "l">
  class="button-base button-large"
</#if>

 <#if parameters.title?exists>
  title="${parameters.title?html}" 
 </#if>  
 
  <#if parameters.id?exists>
  id="${parameters.id?html}" 
 </#if>  
 
  <#if parameters.name?exists>
  name="${parameters.name?html}" 
 </#if>  
 
  <#if parameters.disabled?html?lower_case != "true">
   <#if parameters.onclick?exists>
    onclick="${parameters.onclick?html}" 
   </#if>
</#if>


 

 

 
>
  <#if parameters.text?exists>
  <span class="button-text">
   ${parameters.text?html}
   </span>
 </#if>  
</a>