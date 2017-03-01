/*公用js方法**/
/**add Tab**/
function addTab(tabId,title,href,closable){
	var exists= $('#'+tabId).tabs('exists',title);
	if(exists){
		$('#'+tabId).tabs('select',title);
	}else{
		$('#'+tabId).tabs('add',{
			title:title,
			href:href,
			loadMode : 'iframe',
			closable:closable
		});
	}
}