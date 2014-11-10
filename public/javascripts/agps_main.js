function loadInitData(url, method, flag){
	xmlhttp = null;
	if (window.XMLHttpRequest){
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	} else {
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange=handlestatechange;
	xmlhttp.open(method,url,flag);
	xmlhttp.send();
	return xmlhttp;
}

function handlestatechange(){
	if (xmlhttp.readyState==4 && xmlhttp.status==200){
		return xmlhttp;
	}
} 

function setCountry(xmlhttp, mcc){
	if (xmlhttp.readyState==4 && xmlhttp.status==200){
		var jsonData = eval("(" + xmlhttp.responseText + ")");
		if(jsonData.state == 1){
			var jsonObject = jsonData.results;
			var jsonArr = jsonObject.list;
			var htmlData = "<option value='0'>Global</option>";
			var isSel = null;
			for(var i=0; i<jsonArr.length;i++){
				if(mcc == jsonArr[i].code+'')isSel = 'selected=selected';
				htmlData += "<option "+isSel+" value="+jsonArr[i].code+">"+jsonArr[i].name+"  "+jsonArr[i].abbr+"</option>";
				isSel = null;
			}
			document.getElementById("t_mcc").innerHTML = htmlData;
		}else{
			alert(jsonData.msg);
		}
	}
}