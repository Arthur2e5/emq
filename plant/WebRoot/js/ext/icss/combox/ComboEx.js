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
	var specialTypeCombox = new Ext.form.CodeEnumComboBox({
		enumType:'SPECIALTYPE',
		fieldLabel:'��������',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'specialTypeCombox'
	});
	var inTypeCombox = new Ext.form.CodeEnumComboBox({
		enumType:'IN_TYPE',
		fieldLabel:'�������',
		width:200,
		all:false,
		id:'inTypeCombox'
	});
	var outTypeCombox = new Ext.form.CodeEnumComboBox({
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
 
Ext.form.CodeEnumComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.CodeEnumComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.CodeEnumComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	
    	if(!this.enumType)
    	{
    		this.enumType = Global.IN_TYPE;
    	}
    	if(!this.store)
    	{
    		if(typeof(UtilService) == 'undefined' || typeof(UtilService.getCodeBaseEnumerateEx) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UtilService.getCodeBaseEnumerateEx,
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
        Ext.form.CodeEnumComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	if(this.enumType){
    		queryEvent.query = [this.enumType,this.all];
    	}
    },
    init:function(enumType,all){
    	if(enumType && all)
    	{
    		this.store.load({params:[enumType,all]});
    	}else{
    		this.store.load({params:[this.enumType,this.all]});
    	}
    },
    initAllKey:function(){
    	this.setValue(Global.ALL_KEY);
    	this.setRawValue(Global.ALL_KEY_NAME);
    }
    
});
Ext.reg('CodeEnumComboBox', Ext.form.CodeEnumComboBox);


/**
 * �ֿ�����������
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
	var storageComboBox = new Ext.form.StorageComboBox({
		userId:null,
		fieldLabel:'�ֿ�',
		width:200,
		all:true,
		id:'storageComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',				
		items : [storageComboBox]
	});
	return p; 
}
</code>
 */
 
/**
 * ȡ�û�Ȩ�޿����µĲֿ�������
 * @param {} config
 */
Ext.form.StorageComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.StorageComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.StorageComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.userId) == 'undefined')
    	{
    		this.userId = null;
    	}
    	if(typeof(this.deptId) == 'undefined')
    	{
    		this.deptId = null;
    	}
    	if(typeof(this.limit) == 'undefined'){
    		this.limit = true;
    	}
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getStorage) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}    		
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn: UISMBaseService.getStorageByDept,
		    	fields:[{name:'id',type:'int'},
		    		{name:'storageName',type:'string'},
		    		{name:'manageDept',type:'int'},
		    		{name:'keeper',type:'string'},
		    		{name:'superId',type:'int'},
		    		{name:'orderid',type:'int'},
		    		{name:'sort',type:'string'},
		    		{name:'organization',type:'string'},
		    		{name:'tel',type:'string'},
		    		{name:'state',type:'string'},
		    		{name:'address',type:'string'},
		    		{name:'remark',type:'string'}
		    		]});  
		    
		    this.valueField = 'id';
		    this.displayField = 'storageName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.StorageComboBox.superclass.initComponent.call(this);
    },
    /**
     * ȡ�òִ���id
     */
    getDeptId:function(){
    	var deptId = this.deptId;
    	if(this.deptcbx)
    	{//��������ִ��ƿؼ�������
    		deptId = this.deptcbx.getValue();
    	}    	
    	return deptId;
    },
    getDetpIdBySelected:function(){
    	var storageId = this.getValue();
    	var index = this.store.find("id",storageId);
    	var storageDept = null;
    	if(index >= 0){
    		var record = this.store.getAt(index);
    		storageDept = record.get("manageDept");
    	}
    	return storageDept;
    },
    onBeforeQuery:function(queryEvent){
    	var deptId = this.getDeptId();
    	queryEvent.query = [this.userId,deptId,this.all,this.limit];
    },
    init:function(userId,deptId,all){
    	if(deptId ){
    		this.store.load({params:[userId,deptId,all,this.limit]});
    	}else{
    		var deptId = this.getDeptId();
    		this.store.load({params:[this.userId,deptId,this.all,this.limit]});
    	}
    }
    
});
Ext.reg('StorageComboBox', Ext.form.StorageComboBox);



//---

/**
 * ȡָ���ִ����µ����вֿ�
 * @param {} config
 */
Ext.form.StorageAllComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.StorageAllComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.StorageAllComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.userId) == 'undefined')
    	{
    		this.userId = null;
    	}
    	if(typeof(this.deptId) == 'undefined')
    	{
    		this.deptId = null;
    	}   	
    	
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getStorageAllByDept) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn: UISMBaseService.getStorageAllByDept,
		    	fields:[{name:'id',type:'int'},
		    		{name:'storageName',type:'string'},
		    		{name:'manageDept',type:'string'},
		    		{name:'keeper',type:'string'},
		    		{name:'superId',type:'string'},
		    		{name:'orderid',type:'int'},
		    		{name:'sort',type:'string'},
		    		{name:'organization',type:'string'},
		    		{name:'tel',type:'string'},
		    		{name:'state',type:'string'},
		    		{name:'address',type:'string'},
		    		{name:'remark',type:'string'}
		    		]});  
		    
		    this.valueField = 'id';
		    this.displayField = 'storageName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.StorageAllComboBox.superclass.initComponent.call(this);
    },
    /**
     * ȡ�òִ���id
     */
    getDeptId:function(){
    	var deptId = this.deptId;
    	if(this.deptcbx)
    	{//��������ִ��ƿؼ�������
    		deptId = this.deptcbx.getValue();
    	}    	
    	return deptId;
    },
    onBeforeQuery:function(queryEvent){
    	var deptId = this.getDeptId();
    	queryEvent.query = [deptId,this.all];
    },
    init:function(deptId,all){
    	if(deptId){
    		this.store.load({params:[deptId,all]});
    	}else{
    		var deptId = this.getDeptId();
    		this.store.load({params:[deptId,this.all]});
    	}
    }
    
});
Ext.reg('StorageAllComboBox', Ext.form.StorageAllComboBox);


/**
 * ͨ������ȡ�ֿ�������
 * @param
 */

Ext.form.StorageComboBoxExtend = function(config){
	this.initConfig = config || {};
		
	Ext.form.StorageComboBoxExtend.superclass.constructor.call(this, config);
	
};

Ext.form.StorageComboBoxExtend = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.userId) == 'undefined')
    	{
    		this.userId = null;
    	}
    	if(typeof(this.deptId) == 'undefined')
    	{
    		this.deptId = null;
    	}
    	if(typeof(this.storageId) == 'undefined')
    	{
    		this.storageId = null;
    	}
    	if(typeof(this.limit) == 'undefined')
    	{
    		this.limit = true;
    	}
    	if(typeof(this.storagecbx) == 'undefined')
    	{
    		this.storagecbx == null;
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getStorage) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}    		
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn: UISMBaseService.getStorageByDeptExtend,
		    	fields:[{name:'id',type:'int'},
		    		{name:'storageName',type:'string'},
		    		{name:'manageDept',type:'int'},
		    		{name:'keeper',type:'string'},
		    		{name:'superId',type:'int'},
		    		{name:'orderid',type:'int'},
		    		{name:'sort',type:'string'},
		    		{name:'organization',type:'string'},
		    		{name:'tel',type:'string'},
		    		{name:'state',type:'string'},
		    		{name:'address',type:'string'},
		    		{name:'remark',type:'string'}
		    		]});  
		    
		    this.valueField = 'id';
		    this.displayField = 'storageName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.StorageComboBox.superclass.initComponent.call(this);
    },
    /**
     * ȡ�òִ���id
     */
    getDeptId:function(){
    	var deptId = this.deptId;
    	if(this.deptcbx)
    	{//��������ִ��ƿؼ�������
    		deptId = this.deptcbx.getValue();
    	}    	
    	return deptId;
    },
    getDetpIdBySelected:function(){
    	var storageId = this.getValue();
    	var index = this.store.find("id",storageId);
    	var storageDept = null;
    	if(index >= 0){
    		var record = this.store.getAt(index);
    		storageDept = record.get("manageDept");
    	}
    	return storageDept;
    },
    /**
     * ����ȡ����Ĳֿ�ؼ���ֵ�����ֿ�ID
     */
    getStorageId:function(){
    	var storageId = this.storageId;
    	if(this.storagecbx){
    		storageId = this.storagecbx.getValue();
    	}
    	return storageId;
    },
    /**
     * ����ͨ������Ĳֿ������ؼ���øòֿ��Ӧ�Ĳִ���
     * @param {} cbx
     * @return {}
     */
    getDetpIdByStoragecbx:function(cbx){
    	var storageId = cbx.getValue();
    	var index = cbx.store.find("id",storageId);
    	var storageDept = null;
    	if(index >= 0){
    		var record = cbx.store.getAt(index);
    		storageDept = record.get("manageDept");
    	}
    	return storageDept;
    },
    onBeforeQuery:function(queryEvent){
    	var deptId = this.getDeptId();
    	var storageId = this.storageId;
    	
    	if(!this.limit){//�ֿⲻ��Ҫ��Ȩ�޹ҹ�
    		storageId = this.getStorageId();    		
    		var storageCbx = this.storagecbx;
    		deptId = this.getDetpIdByStoragecbx(storageCbx);
    	}
    	queryEvent.query = [this.userId,deptId,this.all,storageId,this.limit];
    },
    init:function(userId,deptId,all,storageId,limit){
    	if(deptId){
    		this.store.load({params:[userId,deptId,all,storageId,limit]});
    	}else{
    		var deptId = this.getDeptId();
    		this.store.load({params:[this.userId,deptId,this.all,this.storageId,this.limit]});
    	}
    }
    
});
Ext.reg('StorageComboBoxExtend', Ext.form.StorageComboBoxExtend);

/**
 * �ִ��Ƶ����������
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
	var storageDeptComboBox = new Ext.form.StorageDeptComboBox({
		fieldLabel:'�ִ���',
		width:200,
		all:true,
		id:'storageDeptComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',				
		items : [storageDeptComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.StorageDeptComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.StorageDeptComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.StorageDeptComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.userId) == 'undefined')
    	{
    		this.userId = null;
    	}
    	
    	if(typeof(this.limit) == 'undefined'){
    		this.limit = true;
    	}
    	
    	
    	this.areaInfo = [{areaId:1,areaName:'����'},{areaId:2,areaName:'����'},
    		{areaId:3,areaName:'����'},{areaId:4,areaName:'���'},
    		{areaId:5,areaName:'��ͨ'},{areaId:6,areaName:'�½�'},
    		{areaId:7,areaName:'֣��'},{areaId:8,areaName:'��ͤ'}];	
    		
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getStorageDept) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UISMBaseService.getStorageDept,
		    	fields:[{name:'id',type:'int'},
		    		{name:'deptName',type:'string'},
		    		{name:'areaId',type:'int'},
		    		{name:'orderid',type:'int'}
		    		]});  
		    
		    this.valueField = 'id';
		    this.displayField = 'deptName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.StorageDeptComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	queryEvent.query = [this.userId,this.all,this.limit];
    },
    /**
     * ȡ�õ�ǰѡ�е��м�¼����
     * @author ��Ңΰ
     * @return {Ext.data.Record}
     */
    getSelectedRecord:function(){
    	var idx = this.store.find('id',this.getValue());
    	return this.store.getAt(idx);
    },
    /**
     * ȡ�õ�ǰѡ�еĲִ��������ķ�������ID
     * @author ��Ңΰ
     * @return {areaId int}
     */
    getAreaId:function(id){
    	var deptid = id;
    	if(typeof(id) == 'undefined')
    	{
    		deptid = this.getValue();
    	}
    	var idx = this.store.find('id',deptid);
    	var rec = this.store.getAt(idx);
		if(rec){
			return rec.get('areaId');
		}
		
    },
    /**
     * ȡ�õ�ǰѡ��Ĳִ�����������
     * @param {} id
     * @return {}
     */
    getAreaInfo:function(id){
    	var areaId = this.getAreaId();
    	if(areaId)
    	{
    		for(var i=0; i<this.areaInfo.length; ++i)
    		{
    			var ai = this.areaInfo[i];
    			if(areaId == ai.areaId)
    			{
    				return this.areaInfo[i];
    			}
    		}
    	}
    	return null;
		
    },
    init:function(userId,all){
    	if(userId && all){
    		this.store.load({params:[userId,all,this.limit]});
    	}else{
    		this.store.load({params:[this.userId,this.all,this.limit]});
    	}
    }
    
});
Ext.reg('StorageDeptComboBox', Ext.form.StorageDeptComboBox);


/**
 * �ա�������λ�����������
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
	var storageDeptComboBox = new Ext.form.StorageDeptComboBox({
		fieldLabel:'�ִ���',
		width:200,
		all:true,
		id:'storageDeptComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',				
		items : [storageDeptComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.SmMoveDeptComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.SmMoveDeptComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.SmMoveDeptComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined'){
    		this.all = true;
    	}
    	if(typeof(this.moveType) == 'undefined'){
    		this.moveType = 0;
    	}
    	
    	if(typeof(this.state == 'undefined')){
    		//Ĭ��ֻȡû��ͣ�õĵ�λ��
    		this.state = 1;
    	}    	
    	
    	if(!this.store){
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getSmMoveDept) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UISMBaseService.getSmMoveDept,
		    	fields:[{name:'id',type:'int'},
		    		{name:'deptName',type:'string'},
		    		{name:'moveType',type:'int'},
		    		{name:'orderid',type:'int'}
		    		]});  

		    this.valueField = 'id';
		    this.displayField = 'deptName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.SmMoveDeptComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	queryEvent.query = [this.moveType,this.state,this.all];
    },
    init:function(moveType,all){
    	if(moveType && all){
    		this.store.load({params:[moveType,this.state,all]});
    	}else{
    		this.store.load({params:[this.moveType,this.state,this.all]});
    	}
    }
    
});
Ext.reg('SmMoveDeptComboBox', Ext.form.SmMoveDeptComboBox);


/**
 * ��λ���������
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
	var smCargoSpaceComboBox1 = new Ext.form.SmCargoSpaceComboBox({
		fieldLabel:'���û�λ',
		width:200,
		all:true,
		storageId:'1',
		state:'1',
		id:'smCargoSpaceComboBox1'
	});
	var smCargoSpaceComboBox0 = new Ext.form.SmCargoSpaceComboBox({
		fieldLabel:'�����λ',
		width:200,
		all:true,
		storageId:'1',
		state:'0',
		id:'smCargoSpaceComboBox0'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',				
		items : [smCargoSpaceComboBox1,smCargoSpaceComboBox0]
	});
	return p; 
}
</code>
 */
 
Ext.form.SmCargoSpaceComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.SmCargoSpaceComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.SmCargoSpaceComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.storageId) == 'undefined')
    	{
//    		Msg.info('�������ö����и���storageId');
    		this.storageId = '0';
    	}
    	
    	if(typeof(this.state) == 'undefined')
    	{//״̬ 1���� 0����
    		this.state = '1';
    	}
    	if(typeof(this.storagecbx) == 'undefined'){
    		this.storagecbx = null;
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getSmCargoSpace) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UISMBaseService.getSmCargoSpace,
		    	fields:[{name:'id',type:'int'},
		    		{name:'spaceName',type:'string'},
		    		{name:'storageId',type:'string'},
		    		{name:'content',type:'float'},
		    		{name:'orderid',type:'int'},
		    		{name:'state',type:'string'}
		    		]});  

		    this.valueField = 'id';
		    this.displayField = 'spaceName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.SmCargoSpaceComboBox.superclass.initComponent.call(this);
    },
    /**
     * ȡ�ֿ�id
     */
    getStorageId:function(){
    	var skid = null;
    	if(this.storagecbx){
    		skid = this.storagecbx.getValue(); 
    	}else{
    		skid = this.storageId;
    	}
    	return skid;
    },
    onBeforeQuery:function(queryEvent){
    	var skid = this.getStorageId();
    	queryEvent.query = [skid,this.state,this.all];
    },
    init:function(storageId,state,all){
    	if(storageId && state){
    		this.store.load({params:[storageId,state,all]});
    	}else{
    		var skid = this.getStorageId();
    		this.store.load({params:[skid,this.state,this.all]});
    	}
    }
    
});
Ext.reg('SmCargoSpaceComboBox', Ext.form.SmCargoSpaceComboBox);


/**
 * ������Ϣ���������
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
	var smProducingAreaComboBox = new Ext.form.SmProducingAreaComboBox({
		fieldLabel:'����',
		width:200,
		all:false,
		producingId:null,
		id:'smProducingAreaComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',				
		items : [smProducingAreaComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.SmProducingAreaComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.SmProducingAreaComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.SmProducingAreaComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.producingId) == 'undefined')
    	{
    		Msg.info('�������ö����и���producingId');
    		this.producingId = 0;
    	}
    	
    	    	
    	if(!this.store)
    	{
    		if(typeof(UISMBaseService) == 'undefined' || typeof(UISMBaseService.getSmProducingArea) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UISMBaseService.getSmProducingArea,
		    	fields:[{name:'id',type:'int'},
		    		{name:'producingName',type:'string'},		    		
		    		{name:'orderid',type:'int'}
		    		]});  

		    this.valueField = 'id';
		    this.displayField = 'producingName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.SmProducingAreaComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){
    	queryEvent.query = [this.producingId,this.all];
    },
    init:function(producingId,all){
    	if(producingId && all){
    		this.store.load({params:[producingId,all]});
    	}else{
    		this.store.load({params:[this.producingId,this.all]});
    	}
    }
    
});
Ext.reg('SmProducingAreaComboBox', Ext.form.SmProducingAreaComboBox);


/**
 * �����������������
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
	var sendOutAreaComboBox = new Ext.form.SendOutAreaComboBox({
		fieldLabel:'��������',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'sendOutAreaComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [sendOutAreaComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.SendOutAreaComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.SendOutAreaComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.SendOutAreaComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	
    	
    	if(!this.store)
    	{
    		if(typeof(UtilService) == 'undefined' || typeof(UtilService.getOutAreas) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UtilService.getOutAreas,
		    	fields:[{name:'id',type:'int'},{name:'areaName',type:'string'}]});  
		    
		    this.valueField = 'id';
		    this.displayField = 'areaName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.SendOutAreaComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){    	
    	queryEvent.query = [this.all];    	
    },
    init:function(all){
    	if(all)
    	{
    		this.store.load({params:[all]});
    	}else{
    		this.store.load({params:[this.all]});
    	}
    }
    
});
Ext.reg('SendOutAreaComboBox', Ext.form.SendOutAreaComboBox);

/**
 * ʡ�����������
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
	var ProvinceComboBox = new Ext.form.ProvinceComboBox({
		fieldLabel:'ʡ��',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'ProvinceComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [ProvinceComboBox]
	});
	return p; 
}
</code>
 */

Ext.form.ProvinceComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.ProvinceComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.ProvinceComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	
    	
    	if(!this.store)
    	{
    		if(typeof(AuthTreeService) == 'undefined' || typeof(AuthTreeService.getChildEx) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У�AuthTreeService');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:AuthTreeService.getChildEx,
		    	fields:[{name:'code',type:'string'},{name:'text',type:'string'}]});  
		    
		    this.valueField = 'code';
		    this.displayField = 'text';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	this.condition={childLevel:3};
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.ProvinceComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){    	
    	queryEvent.query = [this.condition,this.all];    	
    },
    init:function(all){
    	if(all)
    	{
    		this.store.load({params:[this.condition,all]});
    	}else{
    		this.store.load({params:[this.condition,this.all]});
    	}
    }
    
});
Ext.reg('ProvinceComboBox', Ext.form.ProvinceComboBox);

/**
 * ��ҵ��˾���������
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
	var OrgsComboBox = new Ext.form.OrgsComboBox({
		fieldLabel:'��ҵ��˾',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'OrgsComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [OrgsComboBox]
	});
	return p; 
}
</code>
 */
Ext.form.OrgsComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.OrgsComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.OrgsComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	
    	if(typeof(this.proId) == 'undefined')
    	{
    		this.proId = null;
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(AuthTreeService) == 'undefined' || typeof(AuthTreeService.getChildEx) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UISMBaseService��֧��');
    		}
    		
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		         fn:AuthTreeService.getChildEx,
		    	fields:[{name:'code',type:'string'},{name:'text',type:'string'}]});  
		    
		    
		    this.valueField = 'code';
		    this.displayField = 'text';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
        	
    	}
    	//this.condition={childLevel:5,};
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.OrgsComboBox.superclass.initComponent.call(this);
    },
    /**
     * ȡ��ʡ��id
     */
    getproId:function(){
    	var proId = this.proId;
    	if(this.procbx)
    	{//��������ʡ�ݿؼ�������
    		proId = this.procbx.getValue();
    	}    	
    	return proId;
    },
    onBeforeQuery:function(queryEvent){
    	var proId = this.getproId();
    	var condition={};
    	if(proId==null||proId=='-99')
    	condition={childLevel:5};
    	else condition={childLevel:5,provId:proId};
    	queryEvent.query = [condition,this.all];
    },
    init:function(all){
    	/*if(userId && deptId && all){
    		this.store.load({params:[userId,deptId,all]});
    	}else{
    		var deptId = this.getDeptId();
    		this.store.load({params:[this.userId,deptId,this.all]});
    	}*/
    }
    
});
Ext.reg('OrgsComboBox', Ext.form.OrgsComboBox);

/**
 * ����Ʒ�����������
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
	var cigTradeComboBox = new Ext.form.CigTradeComboBox({
		fieldLabel:'����Ʒ��',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'cigTradeComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [cigTradeComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.CigTradeComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.CigTradeComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.CigTradeComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.producer) == 'undefined')
    	{
    		this.producer = Global.getGroupCode();
    	}
    	//"0"�ǹ��� "1"�ǳ���
    	if(typeof(this.importflag) == 'undefined')
    	{
    		this.importflag = '0';
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(UtilService) == 'undefined' || typeof(UtilService.getCigTradesByProducer) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UtilService.getCigTradesByProducer,
		    	fields:[{name:'tradeCode',type:'string'},{name:'tradeName',type:'string'}]});  
		    
		    this.valueField = 'tradeCode';
		    this.displayField = 'tradeName';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.CigTradeComboBox.superclass.initComponent.call(this);
    },
    onBeforeQuery:function(queryEvent){    	
    	queryEvent.query = [this.producer,this.importflag,this.all];    	
    },
    init:function(producer,importflag,all){
    	if(producer && importflag && all)
    	{
    		this.store.load({params:[producer,importflag,all]});
    	}else{
    		this.store.load({params:[this.producer,this.importflag,this.all]});
    	}
    }
    
});
Ext.reg('CigTradeComboBox', Ext.form.CigTradeComboBox);

/**
 * ���̹�����������
 * @include "../global/Global.js"
 * @include "../msg/Msg.js"
 * @class Ext.form.ComboBox
 * @extends Ext.form.TriggerField
 * A combobox control with support for autocomplete, remote-loading, paging and many other features.
 * @constructor
 * Create a new ComboBox.
 * @param {Object} config Configuration options
 * <code>ʹ��ʾ��
 * 
 * ����:
 * importflag �����ڱ�־ 0���� 1����
 * barcarrier �������� 01-�� 02-�� 03-��04-����
 * barstatus ����״̬0-��Ч 1-��Ч��ȱʡ��
 * tradecbx ָ��Ʒ�ƿؼ�����
 * trade ָ��Ʒ�Ʊ��� ����Ѿ�ʹ����tradecbx������Ҫָ��
 * 
function createMainUI()
{
	var cigTradeComboBox = new Ext.form.CigTradeComboBox({
		fieldLabel:'����Ʒ��',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'cigTradeComboBox'
	});
	var cigaretteComboBox = new Ext.form.CigaretteComboBox({
		fieldLabel:'���̹���Զ�������',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		trade:'',
		id:'cigaretteComboBox'
	});
	cigaretteComboBox.on('beforeQuery',function(queryEvent){
		var trade = Ext.getCmp('cigTradeComboBox').getValue();
		queryEvent.query = [trade,false];
		
	});
	
	var autoCigaretteComboBox = new Ext.form.CigaretteComboBox({
		fieldLabel:'���̹���Զ�����',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		tradecbx:cigTradeComboBox,//ָ��Ʒ�ƿؼ�����
		id:'autoCigaretteComboBox'
	});
	
	var p = new Ext.FormPanel({
		renderTo : 'testdiv',
		title : '��������',
		id : 'testform',
		labelAlign : 'right',
				
		items : [cigaretteComboBox,autoCigaretteComboBox]
	});
	return p; 
}
</code>
 */
 
Ext.form.CigaretteComboBox = function(config){
	this.initConfig = config || {};
		
	Ext.form.CigaretteComboBox.superclass.constructor.call(this, config);
	
};

Ext.form.CigaretteComboBox = Ext.extend(Ext.form.ComboBox, {
    
    initComponent : function(){
    	if(typeof(this.all) == 'undefined')
    	{
    		this.all = true;
    	}
    	if(typeof(this.trade) == 'undefined' && typeof(this.tradecbx) != 'object')
    	{
    		Msg.info('��Ҫ�������Ʒ�Ʊ���');
    		this.trade = null;
    	}
	    
    	// �����ڱ�־ 0���� 1���� 
    	if(typeof(this.importflag) == 'undefined')
    	{
    		this.importflag = '0';
    	}
    	//�������� 01-�� 02-�� 03-��04-����
    	if(typeof(this.barcarrier) == 'undefined')
    	{
    		this.barcarrier = '02';
    	}
    	//����״̬0-��Ч 1-��Ч��ȱʡ��
    	if(typeof(this.barstatus) == 'undefined')
    	{
    		this.barstatus = '1';
    	}
    	
    	if(!this.store)
    	{
    		if(typeof(UtilService) == 'undefined' || typeof(UtilService.getCigaretteByTrade) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService��֧��');
    		}
    		
    		this.store = new Ext.data.DWRStore({id:this.id,
		        fn:UtilService.getCigaretteByTrade,
		    	fields:[{name:'cigCodeBar',type:'string'},
			    		{name:'cigTradeMark',type:'string'},
			    		{name:'tradeCode',type:'string'},
			    		{name:'tradeName',type:'string'}/*,
			    		{name:'orderId',type:'init'}*/]});  
		   
		    this.valueField = 'cigCodeBar';
		    this.displayField = 'cigTradeMark';
		    this.typeAhead =  true;
		    this.selectOnFocus = true;
		    this.mode = 'remote';
        	this.triggerAction = 'all';
        	this.readOnly = true;
    	}
    	
    	this.on('beforeQuery',this.onBeforeQuery);
        Ext.form.CigaretteComboBox.superclass.initComponent.call(this);
    },
    getTrade:function(){
    	var trade = this.trade;
    	if(this.tradecbx)
    	{//���ָ����Ʒ������������Ӹö���ȡ��Ʒ�Ʊ���
    		trade = this.tradecbx.getValue();
    	}
    	return trade;
    }
    ,
    onBeforeQuery:function(queryEvent){
    	//this.importflag �����ڱ�־ 0���� 1����
    	//this.barcarrier �������� 01-�� 02-�� 03-��04-����
    	//this.barstatus ����״̬0-��Ч 1-��Ч��ȱʡ��
    	queryEvent.query = [this.getTrade(),this.importflag,this.barcarrier,this.barstatus,this.all];    	
    },
    init:function(trade,importflag,barcarrier,barstatus,all){
    	if(trade && importflag && barcarrier && barstatus && all)
    	{
    		this.store.load({params:[trade,importflag,barcarrier,barstatus,all]});
    	}else{
    		this.store.load({params:[this.trade,this.importflag,this.barcarrier,this.barstatus,this.all]});
    	}
    }
    
});
Ext.reg('CigaretteComboBox', Ext.form.CigaretteComboBox);
