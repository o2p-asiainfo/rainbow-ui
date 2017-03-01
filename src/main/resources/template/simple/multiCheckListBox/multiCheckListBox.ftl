<#-- 
<p>多选下拉框标签组件</p>
* 多选下拉框标签组件模版
	 *
	 * @version 	0.0.1
	 * @author 	huangcp Feb 11, 2009
	 */	
 -->
 ${parameters.tagUtil.writeScript("/struts/simple/multiCheckListBox/resource/publicTool.js")?string}
 <DIV id=${"Layer_"+parameters.name?replace('.','_')?html}     
      style="HEIGHT:${parameters.layerHeight?html}; POSITION: absolute ;visibility: hidden; left: 0;top: 0;WIDTH:${parameters.layerWidth?html} ; Z-INDEX: 1">
  <DIV id=${parameters.name?replace('.','_')?html+"_selectarea_shadow"}
      style="filter: alpha(Opacity=30, style=1, finishOpacity=30);
       background-color: #000000;  top:0; left: 0; position: absolute;  
       width: 255;height: 150; z-index: 2; layer-background-color: #FEDFB6;
        overflow: hidden; border: 3px none #00f000">
     <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; width:100%; height:100%; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
   </DIV>
  <DIV id=${parameters.name?replace('.','_')?html+"_selectarea"} 
      style="background-color: #ffffff;   position: absolute; top: 0;left: 0; width: 250;height: 150; z-index: 2; layer-background-color: #FEDFB6; border: 3px none #000000;overflow: auto;">
     <table border="0" style="border: 1px solid #000000"　　 width="100%"  height="100%" cellspacing="0" cellpadding="0" >
      <script language="javascript">
function ${parameters.name?replace('.','_')?html+"SelectAllBox"}(ControlBox)
  {<#-- 全部选中按钮的Onclick事件 -->
    if (ControlBox.checked)
       {
          var s = document.getElementsByName('${"mcheck_"+parameters.name?replace('.','_')?html}');
	 var y = new Array();
	 for(var i=0;i<s.length;i++){
		if(s[i].type=="checkbox"){
			y.push(s[i]);
		}
	 }
          for(var j=0;j<y.length;j++)
          {y[j].checked=true;
          }
       }
    <#-- 未选 -->
    if (ControlBox.checked==false)
       {
	 var s = document.getElementsByName('${"mcheck_"+parameters.name?replace('.','_')?html}');
	 var y = new Array();
	 for(var i=0;i<s.length;i++){
		if(s[i].type=="checkbox"){
			y.push(s[i]);
		}
	 }
         for(var j=0;j<y.length;j++)
         {
		y[j].checked=false;
         }
       }
    ${parameters.name?replace('.','_')?html+"ReList"}();
  }  
function ${parameters.name?replace('.','_')?html+"ReList"}()
  {<#-- 将选中按钮的值和标题组合成字符串 -->
          var s = document.getElementsByName('${"mcheck_"+parameters.name?replace('.','_')?html}');
	  var y = new Array();
	  for(var i=0;i<s.length;i++){
		if(s[i].type=="checkbox"){
			y.push(s[i]);
		}
	  }
          var ${parameters.name?replace('.','_')?html+"TitleList"}=''; 
          var ${parameters.name?replace('.','_')?html+"ValueList"}='';
          var ${parameters.name?replace('.','_')?html+"CharTypeValue"}="";
	      var ${parameters.name?replace('.','_')?html+"ValueSetBlank"}="";
          var ${parameters.name?replace('.','_')?html+"CheckedCount"}=0;
          <#if parameters.charTypeValue?exists>
             ${parameters.name?replace('.','_')?html+"CharTypeValue"}='${parameters.charTypeValue?html}';
          </#if>
	      <#if parameters.checkAllValueSetBlank?exists>
	       ${parameters.name?replace('.','_')?html+"ValueSetBlank"}='${parameters.checkAllValueSetBlank?html}';
	      </#if>
          for(var j=0;j<y.length;j++)
          {
            if (y[j].checked){
              ${parameters.name?replace('.','_')?html+"TitleList"}+=document.getElementById('t_'+y[j].id.substring(7)).innerHTML+',';
          
	      if(${parameters.name?replace('.','_')?html+"CharTypeValue"}.toLowerCase()=='true'){
                  ${parameters.name?replace('.','_')?html+"ValueList"}+="'"+y[j].value+"',";
                }else{
                  ${parameters.name?replace('.','_')?html+"ValueList"}+=y[j].value+',';
                }
              ${parameters.name?replace('.','_')?html+"CheckedCount"}++;
            }
          }
          if (${parameters.name?replace('.','_')?html+"CheckedCount"}==y.length){ 
            ${parameters.name?replace('.','_')?html+"TitleList"}='${parameters.allLabel?html}';
            document.getElementById('${"mcheck_all_"+parameters.name?replace('.','_')?html}').checked=true;
            if(${parameters.name?replace('.','_')?html+"ValueSetBlank"}.toLowerCase()=='true'){
	     ${parameters.name?replace('.','_')?html+"ValueList"}='ALL';
	    }
            document.all['${"list_"+parameters.name?replace('.','_')?html}'].rows="1";
          }else { 
          if (${parameters.name?replace('.','_')?html+"TitleList"}.substring(${parameters.name?replace('.','_')?html+"TitleList"}.length-1)==',') 	
                    ${parameters.name?replace('.','_')?html+"TitleList"}=${parameters.name?replace('.','_')?html+"TitleList"}.substring(0,${parameters.name?replace('.','_')?html+"TitleList"}.length-1); 
                   document.getElementById('${"mcheck_all_"+parameters.name?replace('.','_')?html}').checked=false;
                   document.all['${"list_"+parameters.name?replace('.','_')?html}'].rows="${parameters.rows?default('1')?html}";
          }
          if (${parameters.name?replace('.','_')?html+"ValueList"}.substring(${parameters.name?replace('.','_')?html+"ValueList"}.length-1)==',') 	
             ${parameters.name?replace('.','_')?html+"ValueList"}=${parameters.name?replace('.','_')?html+"ValueList"}.substring(0,${parameters.name?replace('.','_')?html+"ValueList"}.length-1); 
          document.getElementById('${"list_"+parameters.name?replace('.','_')?html}').value=${parameters.name?replace('.','_')?html+"TitleList"};
          document.all['${"img_"+parameters.name?replace('.','_')?html}'].alt=${parameters.name?replace('.','_')?html+"ValueList"};
          document.getElementById('<#if parameters.id?exists>${parameters.id?html}<#else>${parameters.name?html}</#if>').value=${parameters.name?replace('.','_')?html+"ValueList"};
         
  } 
function  ${parameters.name?replace('.','_')?html+"showBoxChecked"}(){
   var values="";
   var dList;
  <#if parameters.nameValue?exists>
     values="<@s.property value="parameters.nameValue"/>"<#rt/>;
  </#if>
 var valuesArray=values.split(",");
 dList= document.getElementsByName('${"mcheck_"+parameters.name?replace('.','_')?html}');
 if(valuesArray!=null&&valuesArray!=""){ 
   for(var i=0;i<dList.length;i++){
     for(var j=0;j<valuesArray.length;j++){
       if(valuesArray[j]==dList[i].value){     
         dList[i].checked=true;
         ${parameters.name?replace('.','_')?html+"ReList"}();   
        }
     } 
   }
  }else{
    if('${parameters.isCheckAll?html}'=='true'){<#-- 是否默认全选 -->
      for(var i=0;i<dList.length;i++){
       dList[i].checked=true;      
      }
       ${parameters.name?replace('.','_')?html+"ReList"}();  
     }
  }

}
</script>
      <tr>
         <td height=1><input type="checkbox" id="${"mcheck_all_"+parameters.name?replace('.','_')?html}"  name="${"mcheck_all_"+parameters.name?replace('.','_')?html}"  onclick="${parameters.name?replace('.','_')?html+"SelectAllBox"}(${"mcheck_all_"+parameters.name?replace('.','_')?html});ShowChildLayer(${"list_"+parameters.name?replace('.','_')?html},${"Layer_"+parameters.name?replace('.','_')?html});" value="1"  >
          <font color="#A23100">${parameters.allSelectLabel?html}</font>
          </input>
          <br>
          <#if parameters.list?exists>
          <#assign itemCount = 0/>
          <@s.iterator value="parameters.list"> <#if parameters.listKey?exists>
             <#if stack.findValue(parameters.listKey)?exists>
             <#assign itemKey = stack.findValue(parameters.listKey)/>
             <#assign itemKeyStr = itemKey.toString()/>
             <#else>
             <#assign itemKey = ''/>
             <#assign itemKeyStr = ''/>
             </#if>
             <#else>
             <#assign itemKey = stack.findValue('top')/>
             <#assign itemKeyStr = itemKey.toString()/>
             </#if>
             <#if parameters.listValue?exists>
             <#if stack.findString(parameters.listValue)?exists>
             <#assign itemValue = stack.findString(parameters.listValue)/>
             <#else>
             <#assign itemValue = ''/>
             </#if>
             <#else>
             <#assign itemValue = stack.findString('top')/>
             </#if><#rt/>
             <input type="checkbox"   name="${"mcheck_"+parameters.name?replace('.','_')?html}" id="${"mcheck_"+parameters.name?replace('.','_')?html+itemCount}" 
    onclick="ShowChildLayer(${"list_"+parameters.name?replace('.','_')?html},${"Layer_"+parameters.name?replace('.','_')?html});${parameters.name?replace('.','_')?html+"ReList"}();" 
             <#if parameters.javascript?exists>${parameters.javascript?html}</#if> value="${itemKeyStr?html}"<#rt/>       
             ></input><span id="${"t_"+parameters.name?replace('.','_')?html+itemCount}">${itemValue?html}</span><br>
             <#lt/>
             <#assign itemCount = itemCount + 1/> </@s.iterator>
          </#if> </td>
       </tr>
      <tr>
         <td ></td>
       </tr>
    </table>
   </DIV>
</DIV>
 <input type="hidden" name="${parameters.name?html}" id="<#if parameters.id?exists>${parameters.id?html}<#else>${parameters.name?html}</#if>"  value="ALL" />
 <textarea rows="${parameters.rows?default('1')?html}"   cols="${parameters.cols?default('15')?html}" name="${"list_"+parameters.name?replace('.','_')?html}"  id="${"list_"+parameters.name?replace('.','_')?html}" 
    onclick="if (${"Layer_"+parameters.name?replace('.','_')?html}.style.visibility=='hidden') {
    ShowChildLayer(${"list_"+parameters.name?replace('.','_')?html},${"Layer_"+parameters.name?replace('.','_')?html});
    } else {${"Layer_"+parameters.name?replace('.','_')?html}.style.visibility='hidden';}" 
    readonly style="cursor:hand"  class="text_area" >
 ${parameters.allLabel?html}
 </textarea>
 <img src="${base}/struts/simple/multiCheckListBox/resource/open_folder.gif" align='absbottom' id='${"img_"+parameters.name?replace('.','_')?html}' alt="ALL" 
    onclick="if (${"Layer_"+parameters.name?replace('.','_')?html}.style.visibility=='hidden') {
    ShowChildLayer(${"list_"+parameters.name?replace('.','_')?html},${"Layer_"+parameters.name?replace('.','_')?html});
    } else {${"Layer_"+parameters.name?replace('.','_')?html}.style.visibility='hidden';}" style="cursor:hand">
 <script>
  ${parameters.name?replace('.','_')?html+"ReList"}(); 
${parameters.name?replace('.','_')?html+"showBoxChecked"}();
</script>
