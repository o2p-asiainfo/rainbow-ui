//====================================================================================================
// [\u63d2\u4ef6\u540d\u79f0] jQuery formValidator
//----------------------------------------------------------------------------------------------------
// [\u63cf    \u8ff0] jQuery formValidator\u8868\u5355\u9a8c\u8bc1\u63d2\u4ef6\uff0c\u5b83\u662f\u57fa\u4e8ejQuery\u7c7b\u5e93\uff0c\u5b9e\u73b0\u4e86js\u811a\u672c\u4e8e\u9875\u9762\u7684\u5206\u79bb\u3002\u5bf9\u4e00\u4e2a\u8868
//            \u5355\u5bf9\u8c61\uff0c\u4f60\u53ea\u9700\u8981\u5199\u4e00\u884c\u4ee3\u7801\u5c31\u53ef\u4ee5\u8f7b\u677e\u5b9e\u73b020\u79cd\u4ee5\u4e0a\u7684\u811a\u672c\u63a7\u5236\u3002\u73b0\u652f\u6301\u4e00\u4e2a\u8868\u5355\u5143\u7d20\u7d2f\u52a0\u5f88\u591a\u79cd
//            \u6821\u9a8c\u65b9\u5f0f,\u91c7\u7528\u914d\u7f6e\u4fe1\u606f\u7684\u601d\u60f3\uff0c\u800c\u4e0d\u662f\u628a\u4fe1\u606f\u5199\u5728\u8868\u5355\u5143\u7d20\u4e0a\uff0c\u80fd\u6bd4\u8f83\u5b8c\u7f8e\u7684\u5b9e\u73b0ajax\u8bf7\u6c42\u3002
//----------------------------------------------------------------------------------------------------
// [\u4f5c\u8005\u7f51\u540d] \u732b\u51ac	
// [\u90ae    \u7bb1] wzmaodong@126.com
// [\u4f5c\u8005\u535a\u5ba2] http://wzmaodong.cnblogs.com
// [\u66f4\u65b0\u65e5\u671f] 2008-01-24
// [\u7248 \u672c \u53f7] ver3.3
//====================================================================================================
var jQuery_formValidator_initConfig;
(function($) {

$.formValidator = 
{
	//\u5404\u79cd\u6821\u9a8c\u65b9\u5f0f\u652f\u6301\u7684\u63a7\u4ef6\u7c7b\u578b
	sustainType : function(id,setting)
	{
		var elem = $("#"+id).get(0);
		var srcTag = elem.tagName;
		var stype = elem.type;
		switch(setting.validatetype)
		{
			case "InitValidator":
				return true;
			case "InputValidator":
				if (srcTag == "INPUT" || srcTag == "TEXTAREA" || srcTag == "SELECT"){
					return true;
				}else{
					return false;
				}
			case "CompareValidator":
				if (srcTag == "INPUT" || srcTag == "TEXTAREA")
				{
					if (stype == "checkbox" || stype == "radio"){
						return false;
					}else{
						return true;
					}
				}
				return false;
			case "AjaxValidator":
				if (stype == "text" || stype == "textarea" || stype == "file" || stype == "password" || stype == "select-one"){
					return true;
				}else{
					return false;
				}
			case "RegexValidator":
				if (srcTag == "INPUT" || srcTag == "TEXTAREA")
				{
					if (stype == "checkbox" || stype == "radio"){
						return false;
					}else{
						return true;
					}
				}
				return false;
			case "FunctionValidator":
			    return true;
			case "ExpressionValidator":
			    return true;
		}
	},
    
	initConfig : function(controlOptions)
	{
		var settings = 
		{
			debug:false,
			validatorgroup : "1",
			alertmessage:false,
			validobjectids:"",
			forcevalid:false,
			onsuccess: function() {return true;},
			onerror:function() {},
			submitonce:false,
			formid:"",
			autotip: false,
			tidymode:false,
			errorfocus:true,
			wideword:true
		};
		controlOptions = controlOptions || {};
		$.extend(settings, controlOptions);
		//\u5982\u679c\u662f\u7cbe\u7b80\u6a21\u5f0f\uff0c\u53d1\u751f\u9519\u8bef\u7684\u65f6\u5019\uff0c\u7b2c\u4e00\u4e2a\u9519\u8bef\u7684\u63a7\u4ef6\u5c31\u4e0d\u83b7\u5f97\u7126\u70b9
		if(settings.tidymode){settings.errorfocus=false};
		onsubmit=""
		if(settings.formid!=""){$("#"+settings.formid).submit(function(){return $.formValidator.pageIsValid("1");})};
		
		if (jQuery_formValidator_initConfig == null ){jQuery_formValidator_initConfig = new Array();}
		jQuery_formValidator_initConfig.push( settings );
	},
	
	//\u5982\u679cvalidator\u5bf9\u8c61\u5bf9\u5e94\u7684element\u5bf9\u8c61\u7684validator\u5c5e\u6027\u8ffd\u52a0\u8981\u8fdb\u884c\u7684\u6821\u9a8c\u3002
	appendValid : function(id, setting )
	{
		//\u5982\u679c\u662f\u5404\u79cd\u6821\u9a8c\u4e0d\u652f\u6301\u7684\u7c7b\u578b\uff0c\u5c31\u4e0d\u8ffd\u52a0\u5230\u3002\u8fd4\u56de-1\u8868\u793a\u6ca1\u6709\u8ffd\u52a0\u6210\u529f
		if(!$.formValidator.sustainType(id,setting)) return -1;
		var srcjo = $("#"+id).get(0);   
		//\u91cd\u65b0\u521d\u59cb\u5316
		if (setting.validatetype=="InitValidator" || srcjo.settings == undefined ){srcjo.settings = new Array();}   
		var len = srcjo.settings.push( setting );
		srcjo.settings[len - 1].index = len - 1;
		return len - 1;
	},
	
	//\u5982\u679cvalidator\u5bf9\u8c61\u5bf9\u5e94\u7684element\u5bf9\u8c61\u7684validator\u5c5e\u6027\u8ffd\u52a0\u8981\u8fdb\u884c\u7684\u6821\u9a8c\u3002
	getInitConfig : function( validatorgroup )
	{
		if(jQuery_formValidator_initConfig!=null)
		{
		    for(i=0;i<jQuery_formValidator_initConfig.length;i++)
		    {
		        if(validatorgroup==jQuery_formValidator_initConfig[i].validatorgroup)
				{
					return jQuery_formValidator_initConfig[i];
				}
		    }
		}
		return null;
	},

	//\u89e6\u53d1\u6bcf\u4e2a\u63a7\u4ef6\u4e0a\u7684\u5404\u79cd\u6821\u9a8c
	triggerValidate : function(returnObj)
	{
		switch(returnObj.setting.validatetype)
		{
			case "InputValidator":
				$.formValidator.inputValid(returnObj);
				break;
			case "CompareValidator":
				$.formValidator.compareValid(returnObj);
				break;
			case "AjaxValidator":
				$.formValidator.ajaxValid(returnObj);
				break;
			case "RegexValidator":
				$.formValidator.regexValid(returnObj);
				break;
			case "FunctionValidator":
				$.formValidator.functionValid(returnObj);
				break;
			/********\u589e\u52a0\u8868\u8fbe\u5f0f\u9a8c\u8bc1 by \u9648\u4eae 20090312******/
			case "ExpressionValidator":
				$.formValidator.expressionValid(returnObj);
				break;
			/********\u589e\u52a0\u8868\u8fbe\u5f0f\u9a8c\u8bc1end by \u9648\u4eae 20090312******/
		}
	},
	
	//\u8bbe\u7f6e\u663e\u793a\u4fe1\u606f
	setTipState : function(elem,showclass,showmsg)
	{
		var setting0 = elem.settings[0];
		var initConfig = $.formValidator.getInitConfig(setting0.validatorgroup);
	    var tip = $("#"+setting0.tipid);
		if(showmsg==null || showmsg=="")
		{
			tip.hide();
		}
		else
		{
			if(initConfig.tidymode)
			{
				//\u663e\u793a\u548c\u4fdd\u5b58\u63d0\u793a\u4fe1\u606f
				$("#fv_content").html(showmsg);
				elem.Tooltip = showmsg;
				if(showclass!="onError"){tip.hide();}
			}
			tip.show();
			tip.removeClass();
			tip.addClass( showclass );
			tip.html( showmsg );
			
			var textId = $("#"+elem.id);
			var classArr = new Array();
			var classType = textId.attr("class");
			if (typeof(classType) == 'undefined') {
				textId.addClass(showclass + "Id");
			}else if (classType.substr(0, 2) == "on" && classType.substr(classType.length - 2, 2) == "Id") {
				textId.removeClass();
				textId.addClass(showclass + "Id");
			}else {
				classArr = classType.split(" ");
				textId.removeClass();
				if (classArr != null && classArr.length > 0) {
					textId.addClass(classArr[0]);
				}
				textId.addClass(showclass + "Id");
			}
		}
	},
	
	setIdState : function(elem,showclass,showmsg)
	{
		var setting0 = elem.settings[0];
		var initConfig = $.formValidator.getInitConfig(setting0.validatorgroup);
	    var tip = $("#"+setting0.tipid);
		if(showmsg==null || showmsg=="")
		{
			tip.hide();
		}
		else
		{
			if(initConfig.tidymode)
			{
				//\u663e\u793a\u548c\u4fdd\u5b58\u63d0\u793a\u4fe1\u606f
				$("#fv_content").html(showmsg);
				elem.Tooltip = showmsg;
				if(showclass!="onError"){tip.hide();}
			}
			tip.show();
			tip.removeClass();
			tip.addClass( showclass );
			tip.html( showmsg );
		}
	},
		
	resetTipState : function(validatorgroup)
	{
		var initConfig = $.formValidator.getInitConfig(validatorgroup);
		$(initConfig.validobjectids).each(function(){
			$.formValidator.setTipState(this,"onShow",this.settings[0].onshow);	
		});
	},
	
	//\u8bbe\u7f6e\u9519\u8bef\u7684\u663e\u793a\u4fe1\u606f
	setFailState : function(tipid,showmsg)
	{
	    var tip = $("#"+tipid);
	    tip.removeClass();
	    tip.addClass("onError");
	    tip.html(showmsg);
	},

	//\u6839\u636e\u5355\u4e2a\u5bf9\u8c61,\u6b63\u786e:\u6b63\u786e\u63d0\u793a,\u9519\u8bef:\u9519\u8bef\u63d0\u793a
	showMessage : function(returnObj)
	{
	    var id = returnObj.id;
		var elem = $("#"+id).get(0);
		var isvalid = returnObj.isvalid;
		var setting = returnObj.setting;//\u6b63\u786e:setting[0],\u9519\u8bef:\u5bf9\u5e94\u7684setting[i]
		var showmsg = "",showclass = "";
		var settings = $("#"+id).get(0).settings;
		var intiConfig = $.formValidator.getInitConfig(settings[0].validatorgroup);
		if (!isvalid)
		{		
			showclass = "onError";
			if(setting.validatetype=="AjaxValidator")
			{
				if(setting.lastValid=="")
				{
				    showclass = "onLoad";
				    showmsg = setting.onwait;
				}
				else
				{
				    showmsg = setting.onerror;
				}
			}
			else
			{
				showmsg = (returnObj.errormsg==""? setting.onerror : returnObj.errormsg);
				
			}
			if(intiConfig.alertmessage)		
			{
				var elem = $("#"+id).get(0);
				if(elem.validoldvalue!=$(elem).val()){alert(showmsg);}   
			}
			else
			{
				$.formValidator.setTipState(elem,showclass,showmsg);
			}
		}
		else
		{		
			//\u9a8c\u8bc1\u6210\u529f\u540e,\u5982\u679c\u6ca1\u6709\u8bbe\u7f6e\u6210\u529f\u63d0\u793a\u4fe1\u606f,\u5219\u7ed9\u51fa\u9ed8\u8ba4\u63d0\u793a,\u5426\u5219\u7ed9\u51fa\u81ea\u5b9a\u4e49\u63d0\u793a;\u5141\u8bb8\u4e3a\u7a7a,\u503c\u4e3a\u7a7a\u7684\u63d0\u793a
			showmsg = $.formValidator.isEmpty(id) ? setting.onempty : setting.oncorrect;
			$.formValidator.setTipState(elem,"onCorrect",showmsg);
		}
		return showmsg;
	},

	showAjaxMessage : function(returnObj)
	{
		var setting = returnObj.setting;			
		var elem = $("#"+returnObj.id).get(0);
		//alert("elem.validoldvalue:"+elem.validoldvalue+"$(elem).val():"+$(elem).val());

		//alert("isLoaded"+returnObj.setting.isLoaded);

		if(elem.validoldvalue!=$(elem).val() ||returnObj.setting.isLoaded == undefined)
		{
			
			$.formValidator.ajaxValid(returnObj);
			returnObj.setting.isLoaded='1';
			//alert("returnObj.setting.isLoaded:"+returnObj.setting.isLoaded +"setting.isvalid:"+setting.isvalid);
		}
		else
		{
			if(setting.isvalid!=undefined && !setting.isvalid){
				elem.lastshowclass = "onError"; 
				elem.lastshowmsg = setting.onerror;
			}
			$.formValidator.setTipState(elem,elem.lastshowclass,elem.lastshowmsg);
		}
	},

	//\u83b7\u53d6\u6307\u5b9a\u5b57\u7b26\u4e32\u7684\u957f\u5ea6
    getLength : function(id,trim)
    {
        var srcjo = $("#"+id);
		var elem = srcjo.get(0);
        sType = elem.type;
        var len = 0;
        switch(sType)
		{
			case "text":
			case "hidden":
			case "password":
			case "textarea":
			/***********\u5220\u9664\u5355\u9009\u4e0b\u62c9\u6846\u6309\u9009\u62e9\u7684\u4f4d\u7f6e\u6765\u5b9a\u957f\u5ea6,\u6539\u4e3a\u6309\u503c\u6765\u53d6\u957f\u5ea6 by \u9648\u4eae 20090504***************/
		    case "select-one":
		    /***********\u5220\u9664\u5355\u9009\u4e0b\u62c9\u6846\u6309\u9009\u62e9\u7684\u4f4d\u7f6e\u6765\u5b9a\u957f\u5ea6,\u6539\u4e3a\u6309\u503c\u6765\u53d6\u957f\u5ea6 by \u9648\u4eae 20090504***************/
			case "file":
		        var val = srcjo.val();
		        /***********\u589e\u52a0\u524d\u540e\u53bb\u7a7a\u5224\u65ad by \u9648\u4eae 20090313***************/
		        if(trim)
		        	val=val.trim();
		         /***********\u589e\u52a0\u524d\u540e\u53bb\u7a7a\u5224\u65adend by \u9648\u4eae 20090313***************/
				var initConfig = $.formValidator.getInitConfig(elem.settings[0].validatorgroup);
				if (initConfig.wideword)
				{
					for (var i = 0; i < val.length; i++) 
					{
						if (val.charCodeAt(i) >= 0x4e00 && val.charCodeAt(i) <= 0x9fa5){ 
							len += 2;
						}else {
							len++;
						}
					}
				}
				else{
					len = val.length;
				}
		        break;
			case "checkbox":
			case "radio": 
				len = $("input[@type='"+sType+"'][@name='"+srcjo.attr("name")+"'][@checked]").length;
				break;
			 /***********\u5220\u9664\u5355\u9009\u4e0b\u62c9\u6846\u6309\u9009\u62e9\u7684\u4f4d\u7f6e\u6765\u5b9a\u957f\u5ea6,\u6539\u4e3a\u6309\u503c\u6765\u53d6\u957f\u5ea6 by \u9648\u4eae 20090504***************/
		    /*case "select-one":
		        len = elem.options ? elem.options.selectedIndex : -1;
				break;*/
			 /***********\u5220\u9664\u5355\u9009\u4e0b\u62c9\u6846\u6309\u9009\u62e9\u7684\u4f4d\u7f6e\u6765\u5b9a\u957f\u5ea6,\u6539\u4e3a\u6309\u503c\u6765\u53d6\u957f\u5ea6 by \u9648\u4eae 20090504***************/
			case "select-multiple":
				len = $("select[@name="+elem.name+"] option[@selected]").length;
				break;
	    }
		return len;
    },
    
	//\u7ed3\u5408empty\u8fd9\u4e2a\u5c5e\u6027\uff0c\u5224\u65ad\u4ec5\u4ec5\u662f\u5426\u4e3a\u7a7a\u7684\u6821\u9a8c\u60c5\u51b5\u3002
    isEmpty : function(id)
    {
        if($("#"+id).get(0).settings[0].empty && $.formValidator.getLength(id)==0){
            return true;
        }else{
            return false;
		}
    },
    
	//\u5bf9\u5916\u8c03\u7528\uff1a\u5224\u65ad\u5355\u4e2a\u8868\u5355\u5143\u7d20\u662f\u5426\u9a8c\u8bc1\u901a\u8fc7\uff0c\u4e0d\u5e26\u56de\u8c03\u51fd\u6570
    isOneValid : function(id)
    {
	    return $.formValidator.oneIsValid(id,1).isvalid;
    },
    
	//\u9a8c\u8bc1\u5355\u4e2a\u662f\u5426\u9a8c\u8bc1\u901a\u8fc7,\u6b63\u786e\u8fd4\u56desettings[0],\u9519\u8bef\u8fd4\u56de\u5bf9\u5e94\u7684settings[i]
	oneIsValid : function (id,index)
	{
		var returnObj = new Object();
		returnObj.id = id;
		returnObj.ajax = -1;
		returnObj.errormsg = "";       //\u81ea\u5b9a\u4e49\u9519\u8bef\u4fe1\u606f
		var elem = $("#"+id).get(0);
	    var settings = elem.settings;
	    var settingslen = settings.length;
		//\u53ea\u6709\u4e00\u4e2aformValidator\u7684\u65f6\u5019\u4e0d\u68c0\u9a8c
		if (settingslen==1){settings[0].bind=false;}
		if(!settings[0].bind){return null;}
		for ( var i = 0 ; i < settingslen ; i ++ )
		{   
			if(i==0){
				if($.formValidator.isEmpty(id)){
					returnObj.isvalid = true;
					returnObj.setting = settings[0];
					break;
				}
				continue;
			}
			returnObj.setting = settings[i];
			if(settings[i].validatetype!="AjaxValidator") {
				$.formValidator.triggerValidate(returnObj);
			}else{
				returnObj.ajax = i;
			}
			if(!settings[i].isvalid) {
				returnObj.isvalid = false;
				returnObj.setting = settings[i];
				break;
			}else{
				returnObj.isvalid = true;
				returnObj.setting = settings[0];
				if(settings[i].validatetype=="AjaxValidator") break;
			}
		}
		return returnObj;
	},

	//\u9a8c\u8bc1\u6240\u6709\u9700\u8981\u9a8c\u8bc1\u7684\u5bf9\u8c61\uff0c\u5e76\u8fd4\u56de\u662f\u5426\u9a8c\u8bc1\u6210\u529f\u3002
	pageIsValid : function (validatorgroup)
	{	//alert("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	    if(validatorgroup == null || validatorgroup == undefined){validatorgroup = "1"};
		var isvalid = true;
		var isvalid1 = true;
		var thefirstid = "",thefirsterrmsg;
		var returnObj,setting;
		var error_tip = "^"; 	

		var initConfig = $.formValidator.getInitConfig(validatorgroup);
		var jqObjs = $(initConfig.validobjectids);
		jqObjs.each(function(i,elem)
		{ //alert("i:"+i+"id:"+elem.id);
		    if (elem.className == "tag_select") {
		    	elem = jqObjs[i-1];
		    } else if (elem.className == "tag_options") {
		    	elem = jqObjs[i-2];
		    } 
			if(elem.settings[0].bind){
				returnObj = $.formValidator.oneIsValid(elem.id,1);
				if(returnObj)
				{
					var tipid = elem.settings[0].tipid;
					//\u6821\u9a8c\u5931\u8d25,\u83b7\u53d6\u7b2c\u4e00\u4e2a\u53d1\u751f\u9519\u8bef\u7684\u4fe1\u606f\u548cID
					if (!returnObj.isvalid) {
						isvalid = false; 
						//isvalid1 = false; 
						if (thefirstid == ""){
							thefirstid = returnObj.id;
							thefirsterrmsg = (returnObj.errormsg==""?returnObj.setting.onerror:returnObj.errormsg)
						}
					}
					//\u4e3a\u4e86\u89e3\u51b3\u4f7f\u7528\u540c\u4e2aTIP\u63d0\u793a\u95ee\u9898:\u540e\u9762\u7684\u6210\u529f\u6216\u5931\u8d25\u90fd\u4e0d\u8986\u76d6\u524d\u9762\u7684\u5931\u8d25
					if (!initConfig.alertmessage){
						if (error_tip.indexOf("^" + tipid + "^") == -1) {
							if (!returnObj.isvalid) {
								error_tip = error_tip + tipid + "^";
							}
							
							if ( returnObj.ajax >= 0){
								returnObj.setting.async = false;
								$.formValidator.showAjaxMessage(returnObj,'sync');
								if(returnObj.isvalid){
								  isvalid = returnObj.isvalid;
								}
							}else{
								$.formValidator.showMessage(returnObj);
							}
							
						}
					}
				}
				isvalid1 = isvalid1&&returnObj.isvalid;
			}
		});
		//\u6210\u529f\u6216\u5931\u8d25\u540e\uff0c\u8fdb\u884c\u56de\u8c03\u51fd\u6570\u7684\u5904\u7406\uff0c\u4ee5\u53ca\u6210\u529f\u540e\u7684\u7070\u6389\u63d0\u4ea4\u6309\u94ae\u7684\u529f\u80fd
		if(isvalid1)
		{
            isvalid = initConfig.onsuccess();
			if(initConfig.submitonce){$("input[@type='submit']").attr("disabled",true);}
		}
		else
		{
			var obj = $("#"+thefirstid).get(0);
			initConfig.onerror(thefirsterrmsg,obj);
			if(thefirstid!="" && initConfig.errorfocus){$("#"+thefirstid).focus();}
		}
		//alert("isvalid"+isvalid);
		//alert("!initConfig.debug && isvalid"+(!initConfig.debug && isvalid));
		return !initConfig.debug && isvalid1;
	},

	//ajax\u6821\u9a8c
	ajaxValid : function(returnObj)
	{
		var id = returnObj.id;
	    var srcjo = $("#"+id);
		var elem = srcjo.get(0);
		var settings = elem.settings;
		var setting = settings[returnObj.ajax];
		var ls_url = setting.url;
	    if (srcjo.size() == 0 && settings[0].empty) {
			returnObj.setting = settings[0];
			returnObj.isvalid = true;
			$.formValidator.showMessage(returnObj);
			setting.isvalid = true;
			return;
		}
		if(setting.addidvalue)
		{	//Component 
			//alert(srcjo.val()+"\n"+encodeURI(srcjo.val())+"\n"+encodeURIComponent(srcjo.val()));
			//var parm = "clientid="+id+"&"+id+"="+encodeURIComponent(srcjo.val());
			var parm = "clientid="+id+"&"+id+"="+encodeURI(encodeURI(srcjo.val()));
			ls_url = ls_url + (ls_url.indexOf("?")>0?("&"+ parm) : ("?"+parm));
		}
		//by cl
		ls_url = ls_url +"&random="+parseInt(100000000*Math.random());
		//alert(ls_url);
		$.ajax(
		{	
			mode : "abort",
			type : setting.type, 
			url : ls_url, 
			cache : setting.cache,
			data : setting.data, 
			async : setting.async, 
			dataType : setting.datatype, 
			success : function(data){
			    if(setting.success(data))
			    {
			        $.formValidator.setTipState(elem,"onCorrect",settings[0].oncorrect);
			        setting.isvalid = true;
			        returnObj.isvalid = true;
			    }
			    else
			    {
			        $.formValidator.setTipState(elem,"onError",setting.onerror);
			        setting.isvalid = false;
			        returnObj.isvalid = false;
			    }
			},
			complete : function(){
				if(setting.buttons && setting.buttons.length > 0){setting.buttons.attr({"disabled":false})};
				setting.complete;
			}, 
			beforeSend : function(xhr){
				//\u518d\u670d\u52a1\u5668\u6ca1\u6709\u8fd4\u56de\u6570\u636e\u4e4b\u524d\uff0c\u5148\u56de\u8c03\u63d0\u4ea4\u6309\u94ae
				if(setting.buttons && setting.buttons.length > 0){setting.buttons.attr({"disabled":true})};
				var isvalid = setting.beforesend(xhr);
				if(isvalid)
				{
					setting.isvalid = false;		//\u5982\u679c\u524d\u9762ajax\u8bf7\u6c42\u6210\u529f\u4e86\uff0c\u518d\u6b21\u8bf7\u6c42\u4e4b\u524d\u5148\u5f53\u4f5c\u9519\u8bef\u5904\u7406
					$.formValidator.setTipState(elem,"onLoad",settings[returnObj.ajax].onwait);
				}
				setting.lastValid = "-1";
				return isvalid;
			}, 
			error : function(){
			    $.formValidator.setTipState(elem,"onError",setting.onerror);
			    setting.isvalid = false;
				setting.error();
			},
			processData : setting.processdata 
		});
	},

	//\u5bf9\u6b63\u5219\u8868\u8fbe\u5f0f\u8fdb\u884c\u6821\u9a8c\uff08\u76ee\u524d\u53ea\u9488\u5bf9input\u548ctextarea\uff09
	regexValid : function(returnObj)
	{
		var id = returnObj.id;
		var setting = returnObj.setting;
		var srcTag = $("#"+id).get(0).tagName;
		var elem = $("#"+id).get(0);
		//\u5982\u679c\u6709\u8f93\u5165\u6b63\u5219\u8868\u8fbe\u5f0f\uff0c\u5c31\u8fdb\u884c\u8868\u8fbe\u5f0f\u6821\u9a8c
		if(elem.settings[0].empty && elem.value==""){
			setting.isvalid = true;
		}
		else 
		{
			var regexpress = setting.regexp;
			if(setting.datatype=="enum"){regexpress = eval("regexEnum."+regexpress);}
			if(regexpress==undefined || regexpress==""){
				setting.isvalid = false;
				return;
			}
			setting.isvalid = (new RegExp(regexpress, setting.param)).test($("#"+id).val());
		}
	},
	
	//\u51fd\u6570\u6821\u9a8c\u3002\u8fd4\u56detrue/false\u8868\u793a\u6821\u9a8c\u662f\u5426\u6210\u529f;\u8fd4\u56de\u5b57\u7b26\u4e32\u8868\u793a\u9519\u8bef\u4fe1\u606f\uff0c\u6821\u9a8c\u5931\u8d25;\u5982\u679c\u6ca1\u6709\u8fd4\u56de\u503c\u8868\u793a\u5904\u7406\u51fd\u6570\uff0c\u6821\u9a8c\u6210\u529f
	functionValid : function(returnObj)
	{
		var id = returnObj.id;
		var setting = returnObj.setting;
	    var srcjo = $("#"+id);
		var lb_ret = setting.fun(srcjo.val(),srcjo.get(0));
		if(lb_ret != undefined) 
		{
			if(typeof lb_ret == "string"){
				setting.isvalid = false;
				returnObj.errormsg = lb_ret;
			}else{
				setting.isvalid = lb_ret;
			}
		}
	},
	
	//\u589e\u52a0\u8868\u8fbe\u5f0f\u6821\u9a8c\u3002\u8fd4\u56detrue/false\u8868\u793a\u6821\u9a8c\u662f\u5426\u6210\u529f;\u8fd4\u56de\u5b57\u7b26\u4e32\u8868\u793a\u9519\u8bef\u4fe1\u606f\uff0c\u6821\u9a8c\u5931\u8d25;\u5982\u679c\u6ca1\u6709\u8fd4\u56de\u503c\u8868\u793a\u5904\u7406\u51fd\u6570\uff0c\u6821\u9a8c\u6210\u529f
	//by \u9648\u4eae 20090312
	expressionValid : function(returnObj)
	{
		
		var id = returnObj.id;
		var setting = returnObj.setting;
		setting.isvalid = false;
		var result = eval(setting.exp);
		if( result)
			setting.isvalid = true;
		else 
			returnObj.errormsg= setting.onerror;
	},
	
	
	//\u5bf9input\u548cselect\u7c7b\u578b\u63a7\u4ef6\u8fdb\u884c\u6821\u9a8c
	inputValid : function(returnObj)
	{
		var id = returnObj.id;
		var setting = returnObj.setting;
		var srcjo = $("#"+id);
		var elem = srcjo.get(0);
		var val = srcjo.val();
		var sType = elem.type;
		/********\u589e\u52a0trim \u53c2\u6570\u5224\u65ad by \u9648\u4eae20090313*******/
		var len = $.formValidator.getLength(id,setting.trim);
		/********\u589e\u52a0trim \u53c2\u6570\u5224\u65ad by \u9648\u4eae20090313*******/
		var empty = setting.empty,emptyerror = false;
		switch(sType)
		{
			case "text":
			case "hidden":
			case "password":
			case "textarea":
			case "file":
				if (setting.type == "size") {
					empty = setting.empty;
					if(!empty.leftempty){
						emptyerror = (val.replace(/^[ \s]+/, '').length != val.length);
					}
					if(!emptyerror && !empty.rightempty){
						emptyerror = (val.replace(/[ \s]+$/, '').length != val.length);
					}
					if(emptyerror && empty.emptyerror){returnObj.errormsg= empty.emptyerror}
				}
			case "checkbox":
			case "select-one":
			case "select-multiple":
			case "radio":
				var lb_go_on = false;
				if(sType=="select-one" || sType=="select-multiple"){setting.type = "size";}
				var type = setting.type;
				if (type == "size") {		//\u83b7\u5f97\u8f93\u5165\u7684\u5b57\u7b26\u957f\u5ea6\uff0c\u5e76\u8fdb\u884c\u6821\u9a8c
					if(!emptyerror){lb_go_on = true}
					if(lb_go_on){val = len}
				}
				else if (type =="date" || type =="datetime")
				{
					var isok = false;
					/**********\u4fee\u6539\u65e5\u671f\u76f8\u5173\u4ee3\u7801  by \u9648\u4eae 20090312*************/
					if(type=="date"){
						lb_go_on = isDate(val);
						if(lb_go_on){
							var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
							var r = val.match(reg); 
							val= new Date(r[1], r[3]-1, r[4]);
							if (! (setting.min instanceof Date)){
								r = setting.min.match(reg); 
								setting.min=new Date(r[1], r[3]-1, r[4]);
								r = setting.max.match(reg); 
								setting.max=new Date(r[1], r[3]-1, r[4]);
							}
						}
					} else if(type=="datetime"){
						lb_go_on = isDateTime(val);
						var reg =/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
						var r = val.match(reg); 
						val=new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
						if (! (setting.min instanceof Date)){
							r = setting.min.match(reg); 
							setting.min=new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
							r = setting.max.match(reg); 
							setting.max=new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
						}
					}
					/**********\u4fee\u6539\u65e5\u671f\u76f8\u5173\u4ee3\u7801end  by \u9648\u4eae 20090312*************/
				}else{
					stype = (typeof setting.min);
					if(stype =="number")
					{
						val = (new Number(val)).valueOf();
						if(!isNaN(val)){lb_go_on = true;}
					}
					if(stype =="string"){lb_go_on = true;}
				}
				setting.isvalid = false;
				if(lb_go_on)
				{
					/*************\u589e\u52a0minExclusive maxExclusive\u5224\u65ad by \u9648\u4eae 20090312****************/
					if(setting.minExclusive != null || setting.maxExclusive!= null){
						if(val <= setting.minExclusive || val >= setting.maxExclusive){
							if(val <= setting.minExclusive && setting.onerrormin){
								returnObj.errormsg= setting.onerrormin;
							}
							if(val >= setting.minExclusive && setting.onerrormax){
								returnObj.errormsg= setting.onerrormax;
							}
						}else{
							setting.isvalid = true;
						}
					/*************\u589e\u52a0minExclusive maxExclusive\u5224\u65ad end by \u9648\u4eae 20090312****************/
					} else if(val < setting.min || val > setting.max){
						if(val < setting.min && setting.onerrormin){
							returnObj.errormsg= setting.onerrormin;
						}
						if(val > setting.max && setting.onerrormax){
							returnObj.errormsg= setting.onerrormax;
						}
					}
					else{
						setting.isvalid = true;
					}
				}
				break;
		}
	},
	
	compareValid : function(returnObj)
	{
		var id = returnObj.id;
		var setting = returnObj.setting;
		var srcjo = $("#"+id);
	    var desjo = $("#"+setting.desid );
		var ls_datatype = setting.datatype;
	    setting.isvalid = false;
		curvalue = srcjo.val();
		ls_data = desjo.val();
		if(ls_datatype=="number")
        {
            if(!isNaN(curvalue) && !isNaN(ls_data)){
				curvalue = parseFloat(curvalue);
                ls_data = parseFloat(ls_data);
			}
			else{
			    return;
			}
        }
		if(ls_datatype=="date" || ls_datatype=="datetime")
		{
			var isok = false;
			if(ls_datatype=="date"){isok = (isDate(curvalue) && isDate(ls_data))};
			if(ls_datatype=="datetime"){isok = (isDateTime(curvalue) && isDateTime(ls_data))};
			if(isok){
				curvalue = new Date(curvalue);
				ls_data = new Date(ls_data)
			}
			else{
				return;
			}
		}
		
	    switch(setting.operateor)
	    {
	        case "=":
	            if(curvalue == ls_data){setting.isvalid = true;}
	            break;
	        case "!=":
	            if(curvalue != ls_data){setting.isvalid = true;}
	            break;
	        case ">":
	            if(curvalue > ls_data){setting.isvalid = true;}
	            break;
	        case ">=":
	            if(curvalue >= ls_data){setting.isvalid = true;}
	            break;
	        case "<": 
	            if(curvalue < ls_data){setting.isvalid = true;}
	            break;
	        case "<=":
	            if(curvalue <= ls_data){setting.isvalid = true;}
	            break;
	    }
	},
	
	localTooltip : function(e)
	{
		e = e || window.event;
		var mouseX = e.pageX || (e.clientX ? e.clientX + document.body.scrollLeft : 0);
		var mouseY = e.pageY || (e.clientY ? e.clientY + document.body.scrollTop : 0);
		$("#fvtt").css({"top":(mouseY+2)+"px","left":(mouseX-40)+"px"});
	}
};

//\u6bcf\u4e2a\u6821\u9a8c\u63a7\u4ef6\u5fc5\u987b\u521d\u59cb\u5316\u7684
$.fn.formValidator = function(cs) 
{
	var setting = 
	{
		validatorgroup : "1",
		empty :false,
		submitonce : false,
		automodify : false,
		/*onshow :"\u8bf7\u8f93\u5165\u5185\u5bb9",*/
		onshow :"",
		onfocus: formValidator_onfocus,
		oncorrect: formValidator_oncorrect,
		onempty: " ",
		defaultvalue : null,
		bind : true,
		validatetype : "InitValidator",
		tipcss : 
		{
			"left" : "0px",
			"top" : "1px",
			"height" : "20px",
			"width":"350px"
		},
		triggerevent:"blur",
		forcevalid : false
	};

	//\u83b7\u53d6\u8be5\u6821\u9a8c\u7ec4\u7684\u5168\u5c40\u914d\u7f6e\u4fe1\u606f
	cs = cs || {};
	if(cs.validatorgroup == undefined){cs.validatorgroup = "1"};
	var initConfig = $.formValidator.getInitConfig(cs.validatorgroup);

	//\u5982\u679c\u4e3a\u7cbe\u7b80\u6a21\u5f0f\uff0ctipcss\u8981\u91cd\u65b0\u8bbe\u7f6e\u521d\u59cb\u503c
	if(initConfig.tidymode){setting.tipcss = {"left" : "2px","width":"22px","height":"22px","display":"none"}};
	
	//\u5148\u5408\u5e76\u6574\u4e2a\u914d\u7f6e(\u6df1\u5ea6\u62f7\u8d1d)
	$.extend(true,setting, cs);

	return this.each(function(e)
	{
		var jqobj = $(this);
		var setting_temp = {};
		$.extend(true,setting_temp, setting);
		var tip = setting_temp.tipid ? setting_temp.tipid : this.id+"Tip";
		//\u81ea\u52a8\u5f62\u6210TIP
		if(initConfig.autotip)
		{
			//\u83b7\u53d6\u5c42\u7684ID\u3001\u76f8\u5bf9\u5b9a\u4f4d\u63a7\u4ef6\u7684ID\u548c\u5750\u6807
			if($("body [id="+tip+"]").length==0)
			{
				aftertip = setting_temp.relativeid ? setting_temp.relativeid : this.id;
				var obj = getTopLeft(aftertip);
				var y = obj.top;
				var x = getElementWidth(aftertip) + obj.left;
				$("<div class='formValidateTip'></div>").appendTo($("body")).css({left: x+"px", top: y+"px"}).prepend($('<div id="'+tip+'"></div>').css(setting_temp.tipcss));
			}
			if(initConfig.tidymode){jqobj.showTooltips()};
		}
		
		//\u6bcf\u4e2a\u63a7\u4ef6\u90fd\u8981\u4fdd\u5b58\u8fd9\u4e2a\u914d\u7f6e\u4fe1\u606f
		setting.tipid = tip;
		$.formValidator.appendValid(this.id,setting);

		//\u4fdd\u5b58\u63a7\u4ef6ID
		var validobjectids = initConfig.validobjectids;
		if(validobjectids.indexOf("#"+this.id+" ")==-1){
			initConfig.validobjectids = (validobjectids=="" ? "#"+this.id : validobjectids + ",#" + this.id);
		}

		//\u521d\u59cb\u5316\u663e\u793a\u4fe1\u606f
		if(!initConfig.alertmessage){
			$.formValidator.setTipState(this,"onShow",setting.onshow);
		}

		var srcTag = this.tagName.toLowerCase();
		var stype = this.type;
		var defaultval = setting.defaultvalue;
		//\u5904\u7406\u9ed8\u8ba4\u503c
		if(defaultval){
			jqobj.val(defaultval);
		}

		if(srcTag == "input" || srcTag=="textarea")
		{
			//\u6ce8\u518c\u83b7\u5f97\u7126\u70b9\u7684\u4e8b\u4ef6\u3002\u6539\u53d8\u63d0\u793a\u5bf9\u8c61\u7684\u6587\u5b57\u548c\u6837\u5f0f\uff0c\u4fdd\u5b58\u539f\u503c
			jqobj.focus(function()
			{	
				if(!initConfig.alertmessage){
					//\u4fdd\u5b58\u539f\u6765\u7684\u72b6\u6001
					var tipjq = $("#"+tip);
					this.lastshowclass = tipjq.attr("class");
					this.lastshowmsg = tipjq.html();
					$.formValidator.setTipState(this,"onFocus",setting.onfocus);
				}
				if (stype == "password" || stype == "text" || stype == "textarea" || stype == "file") {
					this.validoldvalue = jqobj.val();
				}
			});
			//\u6ce8\u518c\u5931\u53bb\u7126\u70b9\u7684\u4e8b\u4ef6\u3002\u8fdb\u884c\u6821\u9a8c\uff0c\u6539\u53d8\u63d0\u793a\u5bf9\u8c61\u7684\u6587\u5b57\u548c\u6837\u5f0f\uff1b\u51fa\u9519\u5c31\u63d0\u793a\u5904\u7406
			jqobj.bind(setting.triggerevent, function(){
								
				var settings = this.settings;
				var returnObj = $.formValidator.oneIsValid(this.id,1);
				if(returnObj==null){return;}
				if(returnObj.ajax >= 0) 
				{
					$.formValidator.showAjaxMessage(returnObj);
				}
				else
				{
					var showmsg = $.formValidator.showMessage(returnObj);
					if(!returnObj.isvalid)
					{
						//\u81ea\u52a8\u4fee\u6b63\u9519\u8bef
						var auto = setting.automodify && (this.type=="text" || this.type=="textarea" || this.type=="file");
						if(auto && !initConfig.alertmessage)
						{
							alert(showmsg);
							$.formValidator.setTipState(this,"onShow",setting.onshow);
						}
						else
						{
							if(initConfig.forcevalid || setting.forcevalid){
								alert(showmsg);this.focus();
							}
						}
					}
				}
			});
		} 
		else if (srcTag == "select")
		{
			//\u83b7\u5f97\u7126\u70b9
			jqobj.bind("focus", function(){	
				if(!initConfig.alertmessage){
					$.formValidator.setTipState(this,"onFocus",setting.onfocus);
				}
			});
			//\u5931\u53bb\u7126\u70b9
			jqobj.bind("blur",function(){jqobj.trigger("change")});
			//\u9009\u62e9\u9879\u76ee\u540e\u89e6\u53d1
			jqobj.bind("change",function()
			{
				var returnObj = $.formValidator.oneIsValid(this.id,1);	
				if(returnObj==null){return;}
				if ( returnObj.ajax >= 0){
					$.formValidator.showAjaxMessage(returnObj);
				}else{
					$.formValidator.showMessage(returnObj); 
				}
			});
		}
	});
}; 

$.fn.inputValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : false,
		min : 0,
		max : 99999999999999,
		/*************\u589e\u52a0minExclusive maxExclusive\u521d\u59cb\u503c by \u9648\u4eae 20090312****************/
		minExclusive:null,
		maxExclusive:null,
		/*************\u589e\u52a0minExclusive maxExclusive\u521d\u59cb\u503cend by \u9648\u4eae 20090312****************/
		/*************\u589e\u52a0\u524d\u540e\u53bb\u7a7a\u53c2\u6570 by \u9648\u4eae 20090313****************/
		trim:false,
		/*************\u589e\u52a0\u524d\u540e\u53bb\u7a7a\u53c2\u6570end by \u9648\u4eae 20090313****************/
		type : "size",
		onerror:inputValidator_onerror,
		validatetype:"InputValidator",
		empty:{leftempty:true,rightempty:true,leftemptyerror:null,rightemptyerror:null},
		wideword:true
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function(){
		$.formValidator.appendValid(this.id,settings);
	});
};

$.fn.compareValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : false,
		desid : "",
		operateor :"=",
		onerror:compareValidator_onerror,
		validatetype:"CompareValidator"
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function(){
		$.formValidator.appendValid(this.id,settings);
	});
};

$.fn.regexValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : false,
		regexp : "",
		param : "i",
		datatype : "string",
		onerror:regexValidator_onerror,
		validatetype:"RegexValidator"
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function(){
		$.formValidator.appendValid(this.id,settings);
	});
};

$.fn.functionValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : true,
		fun : function(){this.isvalid = true;},
		validatetype:"FunctionValidator",
		onerror:functionValidator_onerror
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function(){
		$.formValidator.appendValid(this.id,settings);
	});
};

/********\u589e\u52a0\u8868\u8fbe\u5f0f\u9a8c\u8bc1 by \u9648\u4eae 20090312******/
$.fn.expressionValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : true,
		exp : "true",
		validatetype:"ExpressionValidator",
		onerror:expressionValidator_onerror
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function(){
		$.formValidator.appendValid(this.id,settings);
	});
};
/********\u589e\u52a0\u8868\u8fbe\u5f0f\u9a8c\u8bc1end by \u9648\u4eae 20090312******/

$.fn.ajaxValidator = function(controlOptions)
{
	var settings = 
	{
		isvalid : false,
		lastValid : "",
		type : "GET",
		url : "",
		addidvalue : true,
		datatype : "html",
		data : "",
		async : true,
		cache : false,
		beforesend : function(){return true;},
		success : function(){return true;},
		complete : function(){},
		processdata : false,
		error : function(){},
		buttons : null,
		onerror:ajaxValidator_onerror,
		onwait:ajaxValidator_onwait,
		validatetype:"AjaxValidator"
	};
	controlOptions = controlOptions || {};
	$.extend(true, settings, controlOptions);
	return this.each(function()
	{
		$.formValidator.appendValid(this.id,settings);
	});
};

$.fn.defaultPassed = function(onshow)
{
	return this.each(function()
	{
		var settings = this.settings;
		for ( var i = 1 ; i < settings.length ; i ++ )
		{   
			settings[i].isvalid = true;
			if(!$.formValidator.getInitConfig(settings[0].validatorgroup).alertmessage){
				var ls_style = onshow ? "onShow" : "onCorrect";
				$.formValidator.setTipState(this,ls_style,settings[0].oncorrect);
			}
		}
	}); 
};

$.fn.unFormValidator = function(unbind)
{
	return this.each(function()
	{
		this.settings[0].bind = !unbind;
		if(unbind){
			$("#"+this.settings[0].tipid).hide();
		}else{
			$("#"+this.settings[0].tipid).show();
		}
	});
};

$.fn.showTooltips = function()
{
	if($("body [id=fvtt]").length==0){
		fvtt = $("<div id='fvtt' style='position:absolute;z-index:56002'></div>");
		$("body").append(fvtt);
		fvtt.before("<iframe src='about:blank' class='fv_iframe' scrolling='no' frameborder='0'></iframe>");
		
	}
	return this.each(function()
	{
		jqobj = $(this);
		s = $("<span class='top' id=fv_content style='display:block'></span>");
		b = $("<b class='bottom' style='display:block' />");
		this.tooltip = $("<span class='fv_tooltip' style='display:block'></span>").append(s).append(b).css({"filter":"alpha(opacity:95)","KHTMLOpacity":"0.95","MozOpacity":"0.95","opacity":"0.95"});
		//\u6ce8\u518c\u4e8b\u4ef6
		jqobj.mouseover(function(e){
			$("#fvtt").append(this.tooltip);
			$("#fv_content").html(this.Tooltip);
			$.formValidator.localTooltip(e);
		});
		jqobj.mouseout(function(){
			$("#fvtt").empty();
		});
		jqobj.mousemove(function(e){
			$("#fv_content").html(this.Tooltip);
			$.formValidator.localTooltip(e);
		});
	});
}

})(jQuery);

function getElementWidth(objectId) {
	x = document.getElementById(objectId);
	return x.offsetWidth;
}

function getTopLeft(objectId) {
	obj = new Object();
	o = document.getElementById(objectId);
	oLeft = o.offsetLeft;
	oTop = o.offsetTop;
	while(o.offsetParent!=null) {
		oParent = o.offsetParent;
		oLeft += oParent.offsetLeft;
		oTop += oParent.offsetTop;
		o = oParent;
	}
	obj.top = oTop;
	obj.left = oLeft;
	return obj;
}

/********\u589e\u52a0\u8868\u5355\u63d0\u4ea4\u65f6,\u8c03\u7528\u8868\u5355\u9a8c\u8bc1\u65b9\u6cd5 by \u9648\u4eae 20090312******/
function comm_validators_check(validatorgroup){
	//$.formValidator.pageIsValid(validatorgroup);
	return $.formValidator.pageIsValid(validatorgroup);
}

/********\u589e\u52a0\u8868\u5355\u63d0\u4ea4\u65f6,\u8c03\u7528\u8868\u5355\u9a8c\u8bc1\u65b9\u6cd5 by \u9648\u4eae 20090312******/

/********\u589e\u52a0string\u7684\u524d\u540e\u53bb\u7a7a\u65b9\u6cd5 by \u9648\u4eae 20090312******/
String.prototype.trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
String.prototype.lTrim = function() 
{ 
	return this.replace(/(^\s*)/g, ""); 
} 
String.prototype.rTrim = function() 
{ 
	return this.replace(/(\s*$)/g, ""); 
} 
/********\u589e\u52a0string\u7684\u524d\u540e\u53bb\u7a7a\u65b9\u6cd5 by \u9648\u4eae 20090312******/
