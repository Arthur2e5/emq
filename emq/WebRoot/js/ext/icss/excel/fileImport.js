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
 * type:el-���ܹؿ�̨��ģ��,mi-�������ؿ�̨��ģ��
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
		var panelId = this.id;
		if(typeof(this.type)=="undefined"){
			this.type = "el";
		}
		var fileType = this.type;
		this.height=20;
		this.method='POST';
		this.frame=false;
		this.border=false;
		this.fileUpload=true;
		this.labelAlign="left";	
		this.labelWidth=76;
		this.layout="column";
		this.items=[
		     {region:"west",border:false,split:true,layout:'form',items:[{xtype:'textfield',
				fieldLabel: 'ѡ�����ļ�',						
				id:'fileName',
				name: 'myFile',
				inputType:'file'
		     }]},
		     {region:"center",height:20,border:false,split:true,layout:'form',items:[{xtype:'button',pressed:true,
		        id:'ExcelImport',text:'�ļ�����',
		        handler:function(parameter){
					if(Ext.getCmp("fileName").getValue()==""){
						Msg.info("��ѡ����Ҫ������ļ���");
						return false;
					}
					Ext.getCmp(panelId).getForm().submit({
						method:"post",
						url:'/EMQ/fileImportServlet?type='+fileType, 
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
	      }]}
	    ];	
		this.on("click",function(){
		  alert("");
	    });
		Ext.FileImportPanel.superclass.initComponent.call(this);
	}
});
// ע��
Ext.reg('Ext.FileImportPanel', Ext.FileImportPanel);