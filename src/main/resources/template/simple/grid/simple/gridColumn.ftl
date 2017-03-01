header[${parameters.index?html}] = {field:'${parameters.field?html}',title:'${parameters.title?html}',width:${parameters.width?html},align:'${parameters.align?html}'
<#if parameters.formatter?exists>
	,formatter:function(val,rec) {
		var para = '${parameters.formatter?html}';
		var comm = para.indexOf(',');
		if (comm != -1) {
			var paraArr = para.split(',');
			var res = '<a href="${base}';
			for (var i = 0;i < paraArr.length;i++) {
				var arr = paraArr[i].split(':');
				if (arr[0] == 'url') {
					var url = 'rec.' + arr[1];
					res = res + eval(url);
				} else if (arr[0] == 'text') {
					res = res + '">'+arr[1]+'</a>'
				}
			}
			return res;
		} else {
			var arr = para.split(':');
			if (arr[0] == 'url') {
				var url = 'rec.' + arr[1];
				return '<a href="${base}'+ eval(url) +'">'+val+'</a>';
			}
		}
	}
</#if>
};