/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
/**
 * �ļ�����Panel
 * @param {Object} config
 */
Ext.FileImportPanel = function(config){
	this.initConfig = config || {};
	Ext.FileImportPanel.superclass.constructor.call(this, config);
};
// �̳�
Ext.FileImportPanel = Ext.extend(Ext.form.FormPanel, {
	initComponent: function(){
		// -------------- ҵ������ --------------------
		// -------------- UI���� --------------------
		var pan = this;
		this.xtype='textfield';
		this.fieldLabel='ѡ�����ļ�';
		this.name='myFile';
		this.width=Util.getW(.6);
		this.height=Util.getH(.03);
		this.inputType='file';
		this.on("click",function(){
		  alert("");
	    });
		Ext.FileImportPanel.superclass.initComponent.call(this);
	},
	//��ʼ�����ļ�parameter=1:...2:...
	fileImport:function(parameter){
		this.getForm().submit({
		method:"post",
		url:'/EMQ/fileImportServlet?type='+parameter, 
		waitMsg:"���ڵ����ļ�,���Ե�.....",
		success:function(form,action){
			Msg.info("�ļ�����ɹ���");
		},
		failure:function(form,action){
			if(action){
				Msg.info("�ļ�����ʧ�ܣ�"+action.result.message);
			}
		},
		scope:this
	});
	}
});
// ע��
Ext.reg('Ext.FileImportPanel', Ext.FileImportPanel);