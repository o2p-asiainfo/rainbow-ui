var regexEnum = 
{
	int:"^-?[1-9]\\d*$",					//\u6574\u6570
	int1:"^[1-9]\\d*$",					//\u6b63\u6574\u6570
	int2:"^-[1-9]\\d*$",					//\u8d1f\u6574\u6570
	num:"^([+-]?)\\d*\\.?\\d+$",			//\u6570\u5b57
	num1:"^[1-9]\\d*|0$",					//\u6b63\u6570\uff08\u6b63\u6574\u6570 + 0\uff09
	num2:"^-[1-9]\\d*|0$",					//\u8d1f\u6570\uff08\u8d1f\u6574\u6570 + 0\uff09
	double:"^([+-]?)\\d*\\.\\d+$",			//\u6d6e\u70b9\u6570
	double1:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",//\u6b63\u6d6e\u70b9\u6570
	double2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",//\u8d1f\u6d6e\u70b9\u6570
	double3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",//\u6d6e\u70b9\u6570
	double4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$",//\u975e\u8d1f\u6d6e\u70b9\u6570\uff08\u6b63\u6d6e\u70b9\u6570 + 0\uff09
	double5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",//\u975e\u6b63\u6d6e\u70b9\u6570\uff08\u8d1f\u6d6e\u70b9\u6570 + 0\uff09

	email:"^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\.[A-Za-z0-9-]+)*((\.[A-Za-z0-9]{2,})|(\.[A-Za-z0-9]{2,}\.[A-Za-z0-9]{2,}))$", //\u90ae\u4ef6
	color:"^[a-fA-F0-9]{6}$",				//\u989c\u8272
	url:"^(ftp|http|https):\/\/(\.[_A-Za-z0-9-]+)*(@?([A-Za-z0-9-])+)?(\.[A-Za-z0-9-]+)*((\.[A-Za-z0-9]{2,})|(\.[A-Za-z0-9]{2,}\.[A-Za-z0-9]{2,}))(:[0-9]+)?([/A-Za-z0-9?#_-]*)?$",
	chinese:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",					//\u4ec5\u4e2d\u6587
	ascii:"^[\\x00-\\xFF]+$",				//\u4ec5ACSII\u5b57\u7b26
	zipcode:"^\\d{6}$",						//\u90ae\u7f16
	mobile:"^(13|15|18)[0-9]{9}$",				//\u624b\u673a
	ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",	//ip\u5730\u5740
	notempty:"^\\S+$",						//\u975e\u7a7a
	picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",	//\u56fe\u7247
	rar:"(.*)\\.(rar|zip|7zip|tgz)$",								//\u538b\u7f29\u6587\u4ef6
	date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",					//\u65e5\u671f
	qq:"^[1-9]*[1-9][0-9]*$",				//QQ\u53f7\u7801
	tel:"^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{1,8}))?$",	//\u7535\u8bdd\u53f7\u7801\u7684\u51fd\u6570(\u5305\u62ec\u9a8c\u8bc1\u56fd\u5185\u533a\u53f7,\u56fd\u9645\u533a\u53f7,\u5206\u673a\u53f7)
	username:"^\\w+$",						//\u7528\u6765\u7528\u6237\u6ce8\u518c\u3002\u5339\u914d\u7531\u6570\u5b57\u300126\u4e2a\u82f1\u6587\u5b57\u6bcd\u6216\u8005\u4e0b\u5212\u7ebf\u7ec4\u6210\u7684\u5b57\u7b26\u4e32
	letter:"^[A-Za-z]+$",					//\u5b57\u6bcd
	letter_u:"^[A-Z]+$",					//\u5927\u5199\u5b57\u6bcd
	letter_l:"^[a-z]+$" 					//\u5c0f\u5199\u5b57\u6bcd
}

var aCity={11:"\u5317\u4eac",12:"\u5929\u6d25",13:"\u6cb3\u5317",14:"\u5c71\u897f",15:"\u5185\u8499\u53e4",21:"\u8fbd\u5b81",22:"\u5409\u6797",23:"\u9ed1\u9f99\u6c5f",31:"\u4e0a\u6d77",32:"\u6c5f\u82cf",33:"\u6d59\u6c5f",34:"\u5b89\u5fbd",35:"\u798f\u5efa",36:"\u6c5f\u897f",37:"\u5c71\u4e1c",41:"\u6cb3\u5357",42:"\u6e56\u5317",43:"\u6e56\u5357",44:"\u5e7f\u4e1c",45:"\u5e7f\u897f",46:"\u6d77\u5357",50:"\u91cd\u5e86",51:"\u56db\u5ddd",52:"\u8d35\u5dde",53:"\u4e91\u5357",54:"\u897f\u85cf",61:"\u9655\u897f",62:"\u7518\u8083",63:"\u9752\u6d77",64:"\u5b81\u590f",65:"\u65b0\u7586",71:"\u53f0\u6e7e",81:"\u9999\u6e2f",82:"\u6fb3\u95e8",91:"\u56fd\u5916"} 

function isCardID(sId){ 
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) return "\u4f60\u8f93\u5165\u7684\u8eab\u4efd\u8bc1\u957f\u5ea6\u6216\u683c\u5f0f\u9519\u8bef"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) return "\u4f60\u7684\u8eab\u4efd\u8bc1\u5730\u533a\u975e\u6cd5"; 
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "\u8eab\u4efd\u8bc1\u4e0a\u7684\u51fa\u751f\u65e5\u671f\u975e\u6cd5"; 
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) return "\u4f60\u8f93\u5165\u7684\u8eab\u4efd\u8bc1\u53f7\u975e\u6cd5"; 
	return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"\u7537":"\u5973") 
} 




//\u77ed\u65f6\u95f4\uff0c\u5f62\u5982 (13:04:06)
function isTime(str)
{
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {return false}
	if (a[1]>24 || a[3]>60 || a[4]>60)
	{
		return false;
	}
	return true;
}

//\u77ed\u65e5\u671f\uff0c\u5f62\u5982 (2003-12-05)
function isDate(str)
{
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(r==null)return false; 
	var d= new Date(r[1], r[3]-1, r[4]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

//\u957f\u65f6\u95f4\uff0c\u5f62\u5982 (2003-12-05 13:04:06)
function isDateTime(str)
{
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg); 
	if(r==null) return false; 
	var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}
