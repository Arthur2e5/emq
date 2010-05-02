/**
 * fool create
 * 
 * @type String
 */

Ext.BLANK_IMAGE_URL = '../js/ext/resources/images/default/s.gif';

Ext.onReady(function() {
			Ext.QuickTips.init();
			createMainUI();
		});

function createMainUI() {
	var form = new Ext.FormPanel({
				height : 100,
				width : 300,
				frame : true,
				labelWidth : 80,
				labelAlign : 'right',
				baseCls : 'x-plain',
				items : [{
							xtype : 'textfield',
							name : 'name',
							fieldLabel : '�û���',
							allowBlank : false,
							msgTarget : 'side'
						}, {
							xtype : 'textfield',
							name : 'password',
							inputType : 'password',
							fieldLabel : '�ܡ���',
							allowBlank : false,
							msgTarget : 'side'
						}]
			});
	var window = new Ext.Window({
				title : '�û���¼',
				width : 300,
				height : 150,
				plain : false,
				draggable:false,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				closable : false,
				resizable : false,
				items : form,
				buttons : [{
					text : '��¼',
					listeners : {
						'click' : function() {
							if (form.getForm().isValid()) {
								form.getForm().submit({
											url : '../servlet/Login',
											waitMsg : 'ϵͳ������֤���ĵ�¼��Ϣ,���Ժ�...',
											method:'post',
											success : function(form, action) {
												Ext.Msg.alert('��ӭ','��ӭ��¼��̨����ϵͳ!');
												this.document.location='login_suc.jsp';
												//Ext.Msg.alert('��ʾ',
														//action.result.msg);
											},
											failure : function(form, action) {
												//Ext.Msg.alert('��ʾ',
														//action.result.msg);
												Ext.Msg.alert('����','��˶��û�������ٵ�¼!');
											}
										});
							}
						}
					}
				}, {
					text : '����',
					listeners : {
						'click' : function() {
							
							form.getForm().reset();
						}
					}
				}]
			});
	window.show();

}