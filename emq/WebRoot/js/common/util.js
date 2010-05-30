//���ܣ�util.js��Ҫ����Ϊ�����ѯ�������и����ѯ����
//���ߣ�liuyt
//�������ڣ�2009-9-1
/*
 * @history 2009-10-30 guqiong add getCorpCodeFormHTMLControl
 */
/**
	 * �����ѯ����html
	 * �̶�������location:��ǰλ�� �������corpCode:�����в�ѯ������ǰλ�������򲻴���
	 * @param conditionArray ����������������
	 * ��������еĺϷ�ֵ��
	 * licenceNo:���֤�� storeName:�̵����� corporationName:�������� streetName:�ֵ����� 
  * areaName:�������� fareTypeName:��Ӫҵ̬ fareAddress:��Ӫ��ַ line:��·rank:�ȼ�
  * paperTime:��֤���� brd:Ʒ�� cig:��� corpCode:������ sellType:����ҵ̬ markType:�г�����
  * fareSize:��Ӫ��ģ logoutTime:ע������ noOrderMonth:���������� specialRank:ר���ȼ�
  * pc:��ͼУ���ϴ��ļ����� dataType:��ɵ�����(1),���ϴ�У������(2),���У������(3)
	 */
var orgId_ = '';
function getCondition(conditionArray){
	getCondition_cmp(conditionArray,'');
}
//��������ѯ��������
function getCondition_cmp(conditionArray,orgId){
	orgId_ = orgId;
	condition = conditionArray;
	var conditionHtml = "";
	var lineCount = 5;
	var flag = true;
	if(conditionArray.length>lineCount){
		conditionHtml = "<table><tr><td><table width='100%' align='left'><tr>";
		for(var i=0;i<lineCount;i++){
			 var parameter = conditionArray[i];
			 if(parameter=="corpCode"){
			 	flag = false;
			 }
			 conditionHtml += addCondition(parameter);
		}
		conditionHtml += "<td width='194'></td></tr></table></td></tr><tr><td><table width='100%'><tr>";
		for(var i=lineCount;i<conditionArray.length;i++){
			 var parameter = conditionArray[i];
			 if(parameter=="corpCode"){
			 	flag = false;
			 }
			 conditionHtml += addCondition(parameter);
		}
		conditionHtml += "<td><input id='queryId' type='button' onclick='query()' value='��ѯ'/></td>";
		if(orgId!=""){
			conditionHtml += "<td><input type='button' onclick='return_()' value='����'/></td>";
		}
	 if(flag){
	 	conditionHtml += "<td align='right' width='68'><font size='2'>��ǰλ��:</font></td><td id='locationSelect' align='right' width='1'></td>";
	 }
	 conditionHtml += "</tr></table></td></tr></table>";
	}else{
		conditionHtml = "<table width='100%'><tr>";
		for(var i=0;i<conditionArray.length;i++){
		 var parameter = conditionArray[i];
		 if(parameter=="corpCode"){
			 flag = false;
			}
		 conditionHtml += addCondition(parameter);
	 }
	 conditionHtml += "<td align='left'><input id='queryId' type='button' onclick='query()' value='��ѯ'/></td>";
	 if(flag){
	 	conditionHtml += "<td align='right' width='68'><font size='2'>��ǰλ��:</font></td><td id='locationSelect' align='right' width='1'></td>";
	 }
	 conditionHtml += "</tr></table>";
	}
	document.all.conditionTable.innerHTML = conditionHtml;
	if(flag){
		initLocationSelect();
	}
	for(var i=0;i<conditionArray.length;i++){
		var parameter = conditionArray[i];
		if(parameter=="streetName"){
			initStreetSelect('-99','-99');
		}else if(parameter=="fareTypeName"){
			initFareTypeSelect();
		}else if(parameter=="rank"){
			initRankSelect();
		}else if(parameter=="paperTime"){
			document.all.paperTime_start.value=getStartTime();
			document.all.paperTime_end.value=getEndTime();
		}else if(parameter=="brd"){
			initBrdSelect();
		}else if(parameter=="cig"){
			initCigSelect('');
		}else if(parameter=="corpCode"){
			initCorp();
		}else if(parameter=="logoutTime"){
			document.all.logoutTime_start.value=getStartTime();
			document.all.logoutTime_end.value=getEndTime();
		}else if(parameter=="specialRank"){
			initSpecialRankSelect();
		}
	}
}
/**
 * ��ȡĬ�Ͽ�ʼʱ��
 * @return {}
 */
function getStartTime(){
	var currentDate = new Date();
	var year = currentDate.getYear(); 
	var month = currentDate.getMonth()+1;
	if(month<10){
		month = "0"+month;
	}
	var day = currentDate.getDate();
	if(day<10){
		day = "0"+day;
	}
	var startTime = year+"-"+month+"-"+day;
	return startTime;
}

/**
 * ��ȡĬ�Ͻ���ʱ��
 * @return {}
 */
function getEndTime(){
	var currentDate = new Date();
	var year = currentDate.getYear(); 
	var month = currentDate.getMonth()+1;
	if(month<10){
		month = "0"+month;
	}
	var lastDay = new Date(year,month,0).getDate();
	var endTime = year+"-"+month+"-"+lastDay;
	return endTime;
}
/**
	 * ���ݲ������첻ͬ�Ĳ�ѯ����ؼ�
	 * @param par ��ѯ�������
	 * @return ĳ����ѯ����ؼ�html
	 */
function addCondition(par) {
	var temp = "";
	if (par == "licenceNo") {
		temp += "<td width='60' nowrap><font size='2'>���֤��:</font></td><td  align='left' width='1'><input size='10' id='licenceNo' name='licenceNo' type='text'/></td>";
	} else if (par == "storeName") {
		temp += "<td width='60' nowrap><font size='2'>�̵�����:</font></td><td  width='1'><input id='storeName' size='10' name='storeName' type='text'/></td>";
	} else if (par == "corporationName") {
		temp += "<td width='60'><font size='2'>��������:</font></td><td  width='1'><input id='corporationName' size='10' name='corporationName' type='text'/></td>";
	} else if (par == "fareTypeName") {
		temp += "<td width='60' nowrap><font size='2'>��Ӫҵ̬:</font></td><td id='fareTypeSelect' width='1'></td>";
	} else if (par == "streetName") {
		temp += "<td width='60' nowrap><font size='2'>�ֵ�����:</font></td><td id='streetSelect' width='1'></td>";
	} else if (par == "areaName") {
		temp += "<td width='60' nowrap><font size='2'>��������:</font></td><td id='areaSelect' width='1'></td>";
	} else if (par == "fareAddress") {
		temp += "<td width='60' nowrap><font size='2'>��Ӫ��ַ:</font></td><td  width='1'><input id='fareAddress' size='10' name='fareAddress' type='text'/></td>";
	} else if (par == "line") {
		temp += "<td width='60' nowrap><font size='2'>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;·:</font></td><td id='lineSelect' width='1'></td>";
	} else if (par == "rank") {
		temp += "<td width='60' nowrap><font size='2'>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��:</font></td><td id='rankSelect' width='1'></td>";
	} else if (par == "paperTime") {
		temp += "<td width='60' nowrap><font size='2'>��֤����:</font></td><td width='200'><input name='paperTime_start' id='paperTime_start' type='text' readonly size='6' readonly>"+
          "<img src='/KMGIS/imgs/time12.gif' style='cursor:hand;' alt='�������������˵�' " +
          "onClick='document.all.paperTime_start.value=showCalendar(document.all.paperTime_start.value,650,200)'>"+
          "<font size='2'>&nbsp;��</font><input name='paperTime_end' id='paperTime_end' type='text' readonly size='6' readonly>"+
          "<img src='/KMGIS/imgs/time12.gif' style='cursor:hand;' alt='�������������˵�'"+
          "onClick='document.all.paperTime_end.value=showCalendar(document.all.paperTime_end.value,650,200)'></td>";
	} else if (par == "brd") {
		temp += "<td width='60' nowrap><font size='2'>Ʒ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��:</font></td><td id='brdSelect' width='1'></td>";
	} else if (par == "cig") {
		temp += "<td width='60' nowrap><font size='2'>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��:</font></td><td id='cigSelect' width='1'></td>";
	} else if (par == "corpCode") {
		temp += "<td width='60' nowrap><font size='2'>��&nbsp;&nbsp;��&nbsp;&nbsp;��:</font></td><td id='corpSelect' width='1'></td>";
	} else if (par == "sellType") {
		temp += "<td width='60' nowrap><font size='2'>����ҵ̬:</font></td><td width='1'><select name='sellType' id='sellType' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option><option value='ʳ�ӵ�'>ʳ�ӵ�</option><option value='������'>������</option><option value='����'>����</option><option value='�̳�'>�̳�</option><option value='�̾��̵�'>�̾��̵�</option><option value='���ַ�����'>���ַ�����</option><option value='����'>����</option></select></td>";
	} else if (par == "markType") {
		temp += "<td width='60' nowrap><font size='2'>�г�����:</font></td><td width='1'><select name='markType' id='markType' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option><option value='����'>����</option><option value='���'>���</option></select></td>";
	} else if (par == "fareSize") {
		temp += "<td width='60' nowrap><font size='2'>��Ӫ��ģ:</font></td><td width='1'><select name='fareSize' id='fareSize' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option><option value='��'>��</option><option value='��'>��</option><option value='С'>С</option></select></td>";
	} else if (par == "logoutTime") {
		temp += "<td width='60' nowrap><font size='2'>ע������:</font></td><td width='200'><input name='logoutTime_start' id='logoutTime_start' type='text' readonly size='6' readonly>"+
          "<img src='/EMQ/imgs/time12.gif' style='cursor:hand;' alt='�������������˵�' " +
          "onClick='document.all.logoutTime_start.value=showCalendar(document.all.logoutTime_start.value,650,200)'>"+
          "<font size='2'>&nbsp;��</font><input name='logoutTime_end' id='logoutTime_end' type='text' readonly size='6' readonly>"+
          "<img src='/EMQ/imgs/time12.gif' style='cursor:hand;' alt='�������������˵�'"+
          "onClick='document.all.logoutTime_end.value=showCalendar(document.all.logoutTime_end.value,650,200)'></td>";
	} else if (par == "noOrderMonth") {
		temp += "<td width='68' nowrap><font size='2'>����������:</font></td><td  align='left' width='1'><input size='1' id='licenceNo' name='noOrderMonth' type='text' /></td>";
	} else if (par == "specialRank") {
		temp += "<td width='60' nowrap><font size='2'>ר���ȼ�:</font></td><td  align='left' width='1'><td id='specialRankSelect' width='1'></td>";
	} else if (par == "pc") {
		temp += "<td width='60' nowrap><font size='2'>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��:</font></td><td id='pcSelect' width='1'></td>";
	} else if (par == "dataType") {
		temp += "<td width='60' nowrap><font size='2'>��������:</font></td><td width='1'><select name='dataType' id='dataType' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option><option value='1'>��ɵ�����</option><option value='2'>���ϴ�У������</option><option value='3'>���У������</option></select></td>";
	} 
	return temp;
}

/**
	 * ��ʼ���ֵ�������
	 * @param area ��������
	 */
function initStreetSelect(corpCode,area){
	getLoadMessage(waitMessage);
	BaseService.getStreetSelect(corpCode,area,function callback(html){
		 document.all.streetSelect.innerHTML = html;
	});
}

/**
	 * ��ʼ������������
	 */
function initAreaSelect(){
	getLoadMessage(waitMessage);
	var corpCode = "";
	if(document.all.corpCode){
		corpCode = document.all.corpCode.value;
	}else{
		corpCode = document.all.location.value;
		corpCode = corpCode.substring(0,corpCode.indexOf("|"));
	}
	BaseService.getAreaSelect(corpCode,function callback(html){
		document.all.areaSelect.innerHTML = html;
	});
}
/**
	 * ��ʼ��ר���ȼ�������
	 */
function initSpecialRankSelect(){
	getLoadMessage(waitMessage);
	BaseService.getSpecialRankSelect(function callback(html){
		 document.all.specialRankSelect.innerHTML = html;
	});
}

/**
	 * ��ʼ����Ӫҵ̬������
	 */
function initFareTypeSelect(){
	getLoadMessage(waitMessage);
	BaseService.getFareTypeSelect(function callback(html){
		 document.all.fareTypeSelect.innerHTML = html;
	});
}

/**
	 * ��ʼ������������
	 */
function initPcSelect(){
	getLoadMessage(waitMessage);
	var corpCode = "";
	if(document.all.corpCode){
		corpCode = document.all.corpCode.value;
	}else{
		corpCode = document.all.location.value;
		corpCode = corpCode.substring(0,corpCode.indexOf("|"));
	}
	BaseService.getPcSelect(corpCode,function callback(html){
		 document.all.pcSelect.innerHTML = html;
	});
}

/**
	 * ��ʼ����ǰλ��������
	 */
function initLocationSelect(){
	getLoadMessage(waitMessage);
	BaseService.getLocationSelect(orgId_,function callback(html){
		 document.all.locationSelect.innerHTML = html;
		 if(document.all.areaSelect){
		 	initAreaSelect();
		 }
		 if(document.all.lineSelect){
		 	initLineSelect();
		 }
		 if(document.all.pcSelect){
		 	initPcSelect();
		 }
	});
}
/*
 function initLocationSelect(){
	getLoadMessage(waitMessage);
	setTimeout("getLocation()",100); 
}

function getLocation(){
	BaseService.getLocationSelect(orgId_,function callback(html){
		 document.all.locationSelect.innerHTML = html;
		 if(document.all.areaSelect){
		 	initAreaSelect();
		 }
		 if(document.all.lineSelect){
		 	initLineSelect();
		 }
	}); 
}
 */
/**
	 * ��ʼ����·������
	 */
function initLineSelect(){
	getLoadMessage(waitMessage);
	var corpCode = "";
	if(document.all.corpCode){
		corpCode = document.all.corpCode.value;
	}else{
		corpCode = document.all.location.value;
		corpCode = corpCode.substring(0,corpCode.indexOf("|"));
	}
	BaseService.getLineSelect(corpCode,function callback(html){
		 document.all.lineSelect.innerHTML = html;
	});
}
/**
	 * ��ʼ���ȼ�������
	 */
function initRankSelect(){
	getLoadMessage(waitMessage);
	BaseService.getRankSelect(function callback(html){
		 document.all.rankSelect.innerHTML = html;
	});
}
/**
	 * ����������ı亯���������ֵ�������
	 */
function areaChange(){
	getLoadMessage(waitMessage);
	if(document.all.streetName){
		 var areaName = document.all.areaName.value;
		 var corpCode = "";
		 if(document.all.corpCode){
		 	corpCode = document.all.corpCode.value;
		 }else{
		 	corpCode = document.all.location.value;
		 	corpCode = corpCode.substring(0,corpCode.indexOf("|"));
		 }
	  BaseService.getStreetSelect(corpCode,areaName,function callback(html){
		 document.all.streetSelect.innerHTML = html;
	});
	}
}

/**
	 * ��ǰλ��������ı亯����ˢ�²�ͬ�ĵ�ͼ
	 */
function locationChange(mapId){
	getLoadMessage(waitMessage);
	if(document.all.areaSelect){
		initAreaSelect();
	}
	if(document.all.lineSelect){
		initLineSelect();
	}
	if(document.all.streetSelect){
		initStreetSelect('-99','-99');
	}
	if(document.all.pcSelect){
	  initPcSelect();
 }
 parent.centerFrame.document.all.mapId.value=mapId.substring(mapId.indexOf("|")+1, mapId.length);
 parent.centerFrame.switchArea();
}

/**
	 * ��ʼ��������������
	 */
function initCorp(){
	getLoadMessage(waitMessage);
	BaseService.getCorpSelect(function callback(html){
		 document.all.corpSelect.innerHTML = html;
		 if(document.all.areaSelect){
		 	initAreaSelect();
		 }
		 if(document.all.lineSelect){
		 	initLineSelect();
		 }
	});
}

/**
	 * ������������ı亯������������������
	 */
function corpCodeChange(corpCode){
	getLoadMessage(waitMessage);
	if(document.all.lineSelect){
		initLineSelect();
	}
	if(document.all.areaSelect){
	  BaseService.getAreaSelect(corpCode,function callback(html){
		 document.all.areaSelect.innerHTML = html;
	});
	}
	if(document.all.streetSelect){
		initStreetSelect('-99','-99');
	}
	if(document.all.pcSelect){
	  initPcSelect();
 }
}
/**
	 * ��ʼ��Ʒ��������
	 */
function initBrdSelect(){
	getLoadMessage(waitMessage);
	BaseService.getBrdSelect(function callback(html){
		 document.all.brdSelect.innerHTML = html;
	});
}

/**
	 * Ʒ��������ı亯�����������������
	 */
function brdChange(){
	getLoadMessage(waitMessage);
	if(document.all.cig){
		 var brd = document.all.brd.value;
	  BaseService.getCigSelect(brd,function callback(html){
		 document.all.cigSelect.innerHTML = html;
	});
	}
}

/**
	 * ��ʼ�����������
	 */
function initCigSelect(brd){
	getLoadMessage(waitMessage);
	BaseService.getCigSelect(brd,function callback(html){
		 document.all.cigSelect.innerHTML = html;
	});
}
/**
	 * ����У��
	 */
function valueCheck(co){
	var corpCode = document.getElementById('corpCode');
	var noOrderMonth = document.getElementById('noOrderMonth');
	if(corpCode&&(corpCode.value==null||corpCode.value=="")){
		alert(corpCodeMessage);
	}else if(noOrderMonth&&(noOrderMonth.value==null||noOrderMonth.value=="")){
		alert("�����벻��������");
	}else{
	 var flag = false;
		if(co.streetName!=null&&co.streetName!=''){
			flag = true;
		}else if(co.areaName!=null&&co.areaName!=''){
			flag = true;
		}else if(co.fareTypeName!=null&&co.fareTypeName!=''){
			flag = true;
		}else if(co.line!=null&&co.line!=''){
			flag = true;
		}else if(co.rank!=null&&co.rank!=''){
			flag = true;
		}else if(co.brd!=null&&co.brd!=''){
			flag = true;
		}else if(co.cig!=null&&co.cig!=''){
			flag = true;
		}else if(co.sellType!=null&&co.sellType!=''){
			flag = true;
		}else if(co.markType!=null&&co.markType!=''){
			flag = true;
		}else if(co.fareSize!=null&&co.fareSize!=''){
			flag = true;
		}else if(co.specialRank!=null&&co.specialRank!=''){
			flag = true;
		}
		if(!flag){
			alert(checkMessage);
		}
		return flag;
	}
	
}
/*
* ��html�����ؼ���ȡ��˾����
*/
function getCorpCodeFormHTMLControl(){
	var corpCode = null;
	var locationSelect = document.getElementById('location');
 	if(locationSelect){
		var corpCode = locationSelect.value.split('|')[0];
 	}
 	return corpCode;
}
