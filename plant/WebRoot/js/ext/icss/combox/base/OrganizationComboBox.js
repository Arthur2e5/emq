/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

/**
 * ��Ȩ�޿��Ƶ�ʡ������
 * @include "../global/Global.js"
 * @include "../msg/Msg.js"
 * @class Ext.form.ComboBox
 * @extends Ext.form.TriggerField
 * A combobox control with support for autocomplete, remote-loading, paging and many other features.
 * @constructor
 * Create a new ComboBox.
 * @param {Object} config Configuration options
 * <code>ʹ��ʾ��
function createMainUI()
{
	
	//�г��ܲ�
	var areaComboBox = new Ext.form.OrganizationComboBox({
			id:'areaComboBox',
			fieldLabel : '�г��ܲ�',
			anchor:anchorSize,
			co:{childLevel: Global.CONDITION_LEVEL.LEVEL2,all:true}
		});
	//ʡ
	var provinceComboBox = new Ext.form.OrganizationComboBox({
			id:'provinceComboBox',
			fieldLabel : 'ʡ',
			anchor:anchorSize,
			co:{childLevel: Global.CONDITION_LEVEL.LEVEL3,all:true}
		});
	//ʡ�����г��ܲ�������ѯ����
	provinceComboBox.on('beforequery',function(queryEvent){
		var areaCbx = Ext.getCmp('areaComboBox');
		var co = Ext.getCmp('provinceComboBox').getCo();
		co.areaId = areaCbx.getValue();
		co.childLevel = Global.CONDITION_LEVEL.LEVEL3;
		if(Global.isAllKey(co.areaId))
		{
			co.areaId = null;
		}
		queryEvent.query = [co];
	});
		
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [areaComboBox,provinceComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.OrganizationComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.OrganizationComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.OrganizationComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.co) == 'undefined')
    	{
    		this.co = {};
    	}
    	if(typeof(this.cbx) == 'undefined')
    	{
    		this.cbx = null;
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(AuthTreeService) == 'undefined' || typeof(AuthTreeService.getOrganization) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�getOrganization��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:AuthTreeService.getOrganization,
		    	fields:[{name:'code',type:'string'},
		    		{name:'text',type:'string'}]
		    		/*,
		    		{name:'leaf',type:'int'},
		    		{name:'nodeType',type:'int'},
		    		{name:'qtip',type:'string'},
		    		{name:'data',type:'string'},
		    		{name:'level',type:'int'},
		    		{name:'parentId',type:'string'}*/
		    });  
		    
		    this.valueField = 'code';
		    this.displayField = 'text';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforequery',this.onBeforeQuery);
        Ext.form.OrganizationComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	if(this.co){
    		queryEvent.query = [this.co];
    	}
    },
    init:function(co){
    	if(co)
    	{
    		this.store.load({params:[co]});
    	}else{
    		this.store.load({params:[this.co]});
    	}
    },
    initAllKey:function(){
    	if(this.co.all)
        {
	        	this.setValue(Global.ALL_KEY);
	        	this.setRawValue(Global.ALL_KEY_NAME);
        }
    },
    getCo:function(){
    	return this.co;
    }
    
});
Ext.reg('OrganizationComboBox', Ext.form.OrganizationComboBox);

