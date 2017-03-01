${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.easyui.min.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/css/console.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/icon.css")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/"+parameters.skin+"/datagrid.css")?string}

<script>
	$(function() {
		var cols = [];
		var header = [];
	 