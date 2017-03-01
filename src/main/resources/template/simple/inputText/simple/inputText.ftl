${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/css/console.css")?string}
 
<input class="box1"  onmouseover="this.className='box2'"  onMouseOut="this.className='box1'"  type="${parameters.type?html}"    size="${parameters.textSize?html}" 
 

 
 
 <#if parameters.id?exists>
  id="${parameters.id?html}" 
 </#if> 
 
  <#if parameters.name?exists>
  name="${parameters.name?html}" 
 </#if>
 
 
   <#if parameters.onkeyup?exists>
  onkeyup="${parameters.onkeyup?html}" 
 </#if> 
 
    <#if parameters.onfocus?exists>
  onfocus="${parameters.onfocus?html}" 
 </#if>
 
  <#if parameters.onblur?exists>
  onblur="${parameters.onblur?html}" 
 </#if> 
 
  <#if parameters.onchange?exists>
  onchange="${parameters.onchange?html}" 
 </#if> 
 

    <#if parameters.style?exists>
  style="${parameters.style?html}" 
 </#if>

 
 <#if parameters.value?exists>
  value="${parameters.value?html}" 
 </#if>
 
 <#if parameters.readonly?html?lower_case == "true">
  readonly
</#if>

 <#if parameters.disabled?html?lower_case == "true">
  disabled
</#if>

 />