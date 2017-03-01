${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.loadmask.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/resource/skin/${parameters.skin?html}/jquery.loadmask.css")?string}
  <script>
  
	  $(document).ready(function(){
		 $("#${parameters.id?html}").mask();
	  });
	  
	  function mask(){
	  	$("#${parameters.id?html}").mask();
	  }
	  
	  function unmask(){
	  	$("#${parameters.id?html}").unmask();
	  }
	  
  </script>

<div id="${parameters.id?html}" >