/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

/**
 * ö��ֵ�����������
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
	var specialTypeCombox = new Ext.form.CodeEnumByUserComboBox({
		enumType:'SPECIALTYPE',
		fieldLabel:'��������',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'specialTypeCombox'
	});
	var inTypeCombox = new Ext.form.CodeEnumByUserComboBox({
		enumType:'IN_TYPE',
		fieldLabel:'�������',
		width:200,
		all:false,
		id:'inTypeCombox'
	});
	var outTypeCombox = new Ext.form.CodeEnumByUserComboBox({
		enumType:'OUT_TYPE',
		fieldLabel:'��������',
		width:200,
		all:true,
		id:'outTypeCombox'
	});
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [specialTypeCombox,inTypeCombox,outTypeCombox]
	});
	return p; 
}
</code>
 */
 
Ext.form.CodeEnumByUserComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.CodeEnumByUserComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.CodeEnumByUserComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	
    	if(!this.enumType)
    	{
    		this.enumType = Global.IN_TYPE;
    	}
    	
    	if(typeof(this.userId) == 'undefined')
    	{
    		this.userId = null;
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(UtilService) == 'undefined' || typeof(UtilService.getCodeBaseEnumerateByUser) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UtilService.getCodeBaseEnumerateByUser,
		    	fields:[{name:'enumid'},{name:'enumname'}]});  
		    
		    this.valueField = 'enumid';
		    this.displayField = 'enumname';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.CodeEnumByUserComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	if(this.enumType){
    		queryEvent.query = [this.userId,this.enumType,this.all];
    	}
    },
    init:function(userId,enumType,all){
    	if(userId && enumType && all)
    	{
    		this.store.load({params:[userId,enumType,all]});
    	}else{
    		this.store.load({params:[this.userId,this.enumType,this.all]});
    	}
    }
    
});
Ext.reg('CodeEnumByUserComboBox', Ext.form.CodeEnumByUserComboBox);

