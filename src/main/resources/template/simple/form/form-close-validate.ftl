<#--
/*
 * $Id: Action.java 502296 2007-02-01 17:33:39Z niallp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#--
START SNIPPET: supported-validators
Only the following validators are supported:
* required validator
* requiredstring validator
* stringlength validator
* regex validator
* email validator
* url validator
* int validator
* double validator
END SNIPPET: supported-validators
-->
<#if ((parameters.validate?default(false) == true) )>
<script type="text/javascript"> 

<#assign validators=tag.getValidators() />
<#if validators?size gt 0>
	$(document).ready(function(){ 
	<#-- $.formValidator.initConfig({formid:"${parameters.id?html}",autotip:true,tidymode:true,alertmessage:true,onerror:function(msg){alert(msg)},onsuccess:function(){return true;}}); 
	 $.formValidator.setTipState(obj,"onError",msg);
	 $.formValidator.setFailState(obj.id+"Tip",msg);
	-->
	$.formValidator.initConfig({formid:"${parameters.id?html}",<#rt/>
	<#if parameters.validateAlert?default("div")?lower_case == "div">
	onerror:function(msg,obj){commShowAlert(obj,msg)},<#rt/>
	<#elseif parameters.validateAlert?default("div")?lower_case == "alert">
	onerror:function(msg){alert(msg)},<#rt/>
	<#elseif parameters.validateAlert?default("div")?lower_case == "word">
	autotip:true,onerror:function(msg,obj){},<#rt/>
	<#elseif parameters.validateAlert?default("div")?lower_case == "tidy">
	autotip:true,tidymode:true,onerror:function(msg,obj){},<#rt/>
	</#if>
	onsuccess:function(){return true;}});
</#if>

<#assign p_fieldName='' />
<#list validators as validator>
	<#--map['requiredValidatorField'] 的方式转为 map_requiredValidatorField 
	    map.requiredValidatorField 的方式转为map_requiredValidatorField
	-->
	<#assign fieldName=validator.fieldName?replace('.','_')?replace('[','_')?replace('\'','')?replace(']','')?js_string />
	<#assign errorMsg=validator.getMessage(action)?js_string />
	<#if fieldName != p_fieldName>
		<#if validator_index !=0>
		;
		}
		</#if>
		<#assign isCanEmpty=!(validator.validatorType == "required" ||validator.validatorType == "requiredstring")/>
		field = document.getElementById("${fieldName}"); 
		if (field) {
			$(field).formValidator({empty:${isCanEmpty?string},onshow:" ",oncorrect:" "})
		<#assign p_fieldName=fieldName />
	<#else >
	
	</#if>
        <#--不能为空验证 -->
        <#if validator.validatorType == "required">
        	.inputValidator({min:1,trim:true,onfocus:"${errorMsg}",onerror:"${errorMsg}"})
        <#--字符串不能为空验证 参数:trim-->
        <#elseif validator.validatorType == "requiredstring">
            .inputValidator({min:1 <#if validator.trim?default(true)>,trim:true</#if>,onfocus:"${errorMsg}",onerror:"${errorMsg}"})
        <#--字符串长度验证 参数:trim、minLength、maxLength-->
        <#elseif validator.validatorType == "stringlength">
        	<#if (validator.minLength?exists && validator.minLength?int > -1) || (validator.maxLength?exists && validator.maxLength?int > -1) >
        		.inputValidator({<#rt/>
        		<#if (validator.minLength?exists && validator.minLength?int > -1) >min:${validator.minLength?string},<#rt/></#if> 
        		<#if (validator.maxLength?exists && validator.maxLength?int > -1) >max:${validator.maxLength?string},<#rt/></#if> 
        		<#if validator.trim?default(true)>trim:true,<#rt/></#if>
        		<#rt/>onfocus:"${errorMsg}",onerror:"${errorMsg}"})
        	</#if>
        <#--正则表达式验证 参数:expression-->
        <#elseif validator.validatorType == "regex">
        	.inputValidator({onerror:"${errorMsg}"}).regexValidator({regexp:"${validator.expression?js_string}",onerror:"${errorMsg}"})
        <#--email验证-->
        <#elseif validator.validatorType == "email">
        	.inputValidator({onerror:"${errorMsg}"}).regexValidator({regexp:"^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\.[A-Za-z0-9-]+)*((\.[A-Za-z0-9]{2,})|(\.[A-Za-z0-9]{2,}\.[A-Za-z0-9]{2,}))$",onerror:"${errorMsg}"})
        <#--url验证-->
        <#elseif validator.validatorType == "url">
        	.inputValidator({onerror:"${errorMsg}"}).regexValidator({regexp:"^(ftp|http|https):\/\/(\.[_A-Za-z0-9-]+)*(@?([A-Za-z0-9-])+)?(\.[A-Za-z0-9-]+)*((\.[A-Za-z0-9]{2,})|(\.[A-Za-z0-9]{2,}\.[A-Za-z0-9]{2,}))(:[0-9]+)?([/A-Za-z0-9?#_-]*)?$",onerror:"${errorMsg}"})
        <#--整数验证 参数:min,max-->
        <#elseif validator.validatorType == "int">
        		.regexValidator({regexp:"intege",datatype:"enum",onerror:"${errorMsg}"})
        	<#if validator.min?exists||validator.max?exists>
        		.inputValidator({<#rt/>
        		<#if validator.min?exists ><#rt/>min:${validator.min?string},<#rt/></#if> 
        		<#if validator.max?exists ><#rt/>max:${validator.max?string},<#rt/></#if>
        		type:"number",<#rt/>
        		<#rt/>onerror:"${errorMsg}"})
        	</#if>
        <#--浮点数验证 参数:minInclusive,maxInclusive,minExclusive,maxExclusive-->
        <#elseif validator.validatorType == "double">
        		.regexValidator({regexp:"double",datatype:"enum",onerror:"${errorMsg}"})
        	<#if validator.minInclusive?exists||validator.minExclusive?exists ||validator.maxInclusive?exists||validator.maxExclusive?exists>
        		.inputValidator({<#rt/>
        		<#if validator.minInclusive?exists ><#rt/>min:${validator.minInclusive?string},<#rt/></#if> 
        		<#if validator.maxInclusive?exists ><#rt/>max:${validator.maxInclusive?string},<#rt/></#if>
        		<#if validator.minExclusive?exists ><#rt/>minExclusive:${validator.minExclusive?string},<#rt/></#if>
        		<#if validator.maxExclusive?exists ><#rt/>maxExclusive:${validator.maxExclusive?string},<#rt/></#if>
        		type:"number",<#rt/>
        		<#rt/>onerror:"${errorMsg}"})
        	</#if>    
        <#--日期验证-->
        <#elseif validator.validatorType == "date">
        	.inputValidator({<#if validator.min?exists ><#rt/>min:"${validator.min?date}",</#if><#if validator.min?exists ><#rt/>max:"${validator.max?date}",</#if>type:"date",onerror:"${errorMsg}"})
        <#--字段表示式验证-->
        <#elseif validator.validatorType == "fieldexpression">
        	.expressionValidator({exp:"${tag.createExpression(validator.expression)?js_string}",onerror:"${errorMsg}"})
       
        
        
        
        <#--
        <#elseif validator.validatorType == "expression">
        	$(field).formValidator({onshow:" ",onfocus:error,oncorrect:" "}).inputValidator({min:,empty:{leftempty:false,rightempty:false},onerror:error});

        <#elseif validator.validatorType == "visitor">
        	$(field).formValidator({onshow:" ",onfocus:error,oncorrect:" "}).inputValidator({min:1,empty:{leftempty:false,rightempty:false},onerror:error});

        <#elseif validator.validatorType == "conversion">
        	$(field).formValidator({onshow:" ",onfocus:error,oncorrect:" "}).inputValidator({min:1,empty:{leftempty:false,rightempty:false},onerror:error});
       	-->
        </#if>
        <#if !validator_has_next>
		;
		}
		</#if>
 </#list>
<#if validators?size gt 0>
});
</#if>

</script>
</#if>

