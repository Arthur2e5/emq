/**
 * ���DWR������Ĺ�����������
 * @param {} msg
 * @param {} e
 */
//DWR�Ĵ�����
function DWRErrorHandler(msg,e)
{
	var errmsg = '';
	if(e){
		errmsg = e.message;
	}
	
	if(typeof(msg) == 'object')
	{
		errmsg = '�ļ���:' + msg.fileName + '<br>' + '�к�:' + msg.lineNumber + '<br>' + '��������:' + msg.message;
	}else if(typeof(msg) == 'string'){
		errmsg = msg;
	}
	Ext.Msg.hide();
	alert(errmsg);
	/*		
	Ext.Msg.show({
				title:'���ô���',
		   		msg: errmsg,
		   		buttons: Ext.Msg.OK,
		   		//fn: fun,
		   		//animEl: 'elId',
		   		icon: Ext.MessageBox.ERROR
			});
	*/
}