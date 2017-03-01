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
<#if parameters.validate?default(false) == true>
	${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
	${parameters.tagUtil.writeScript("/struts/simple/form/resource/jsVariables${localeName}.js")?string}
	${parameters.tagUtil.writeScript("/struts/simple/form/resource/formValidator.js")?string}
	${parameters.tagUtil.writeScript("/struts/simple/form/resource/formValidatorRegex.js")?string}
	${parameters.tagUtil.writeScript("/struts/simple/form/resource/"+parameters.validateAlert?default("div")+"/validator.js")?string}
	${parameters.tagUtil.writeStyle("/struts/simple/form/resource/"+parameters.validateAlert?default("div")+"/validator.css")?string}
	
	<#if parameters.validateAlert?default("div")?lower_case == "div">
	<#if parameters.tagUtil.checkByKey("comm_alert_box") >
	<div id="comm_alert_box" style="position:absolute; top:100px; left:2px;width:15em;top:0px;height:66;z-index:999999;visibility:hidden;" >
	<table border="0" width="160" height="66" cellspacing="0" cellpadding="2">
		<tr>
			<td valign="top" ><div style="filter:'progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=0,finishOpacity=100';"><img src="${base}/struts/simple/form/resource/div/th_ju.gif" width="18" height="24" style="border=0; position:relative; top=10px"/></div>
			</td>
			<td>
			<iframe id="comm_alert_box_iframe"  src="javascript:false" style="border=0; position:absolute; visibility:inherit; top:0px; width:242; height:119; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';opacity:0; "></iframe>
				<div id="comm_alert_box_div" class="comm-alert-box" style="width:15em; filter:'progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=100,finishOpacity=100'">
					<div id="comm_alert_box_div_outer" class="comm-alert-box-outer">
						<div id="comm_alert_box_div_inner" class="comm-alert-box-inner">
							<p id="comm_alert_text">
							</p>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	</div>
	</#if>
	</#if>
</#if>
