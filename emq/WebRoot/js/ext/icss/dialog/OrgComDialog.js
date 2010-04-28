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
	var specialTypeCombox = new Ext.form.OrganizationComboBox({
		enumType:'SPECIALTYPE',
		fieldLabel:'��������',
		width:200,
		all:false,//�Ƿ����'ȫ��'
		id:'specialTypeCombox'
	});
	var inTypeCombox = new Ext.form.ProvinceComboBox({
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
 
Ext.icss.OrgComDialog = function(config){
	this.initConfig = config || {};
		
	Ext.icss.OrgComDialog.superclass.constructor.call(this, config);
	
};

Ext.icss.OrgComDialog = Ext.extend(Ext.Window, {
    maximizable :true,
    initComponent : function(){
    	if(typeof(this.fn) !== 'function')
    	{
    		Msg.info('���ڴ������ȷ����Ĵ�����');
    	}

    	if(typeof(UtilService) == 'undefined' || typeof(UtilService.getComOrgInfo) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService.getComOrgInfo��֧��');
    	}
    		
		var orgComDialogStore = new Ext.data.DWRStore({id:'orgComDialogStore',
	        fn:UtilService.getComOrgInfo,
	    	fields:[{name:'code',type:'string'},
	    		{name:'text',type:'string'},
	    		{name:'data',type:'string'}]
	    	});  
	    var pytext = new Ext.form.TextField({xtype:'textfield',width:190,
	    					emptyText:'����ƴ������ĸ',enableKeyEvents:true});
		pytext.on('keyup',this.onFilter,this);
		
		var sm = new Ext.grid.RowSelectionModel();
		var torgComDialogGrid = new Ext.grid.EditorGridPanelEx({
		    	id:'orgComDialogGrid',
		        store: orgComDialogStore,
		        frame:true,
		        //border:true,
		        height:Util.getH(.12),
		        cm: new Ext.grid.ColumnModelEx({columns:[
		            {header: "��ҵ��˾ID", width:90,  sortable: true, dataIndex: 'code'},
		            {header: "��ҵ��˾����", width:250,  sortable: true, dataIndex: 'text'},
		            {header: "ƴ����", width:100,  sortable: true, dataIndex: 'data'}
		        ]}),
		        sm:sm,
		       	stripeRows: true,
		       	tbar:[{xtype:'tbtext',text:'ƴ����'},pytext]
		    });
			
		    this.grid = torgComDialogGrid;
		    this.width = Util.getW(0.5);
			this.height = Util.getH(0.5);
		    this.items = [this.grid];
		    this.layout = 'fit';
		    this.title = 'ѡ����ҵ��˾';	    		
    		this.modal = true;
    		this.buttons = [{text:'ȷ��',handler:this.onOK, scope:this.ownerCt},{text:'ȡ��',handler:this.onCancel,scope:this.ownerCt}];
	        this.buttonAlign = 'center';
	        this.closeAction = 'hide';
	        this.grid.on('rowdblclick',this.onRowdbclick);
	        
	        
        Ext.icss.OrgComDialog.superclass.initComponent.call(this);
    },
    refresh:function(){
    	if(this.grid){
    		var ds = this.grid.getStore();
    		ds.load();
    	}
    },
    onRowdbclick:function(g, ridx, e ){
    	this.ownerCt.fn(this.ownerCt,g);
    },
    onOK:function(btn,event){
    	this.ownerCt.fn(this.ownerCt,this.ownerCt.grid);
    },
    onCancel:function(btn,event){
    	this.ownerCt.hide();
    },
    onFilter:function(txt,e){
		var text = txt.getValue();
    	if(this.grid)
    	{
	    	var re = new RegExp(text, 'i'); 
	    	this.grid.store.filter('data',text,true)
    	}
	}
								
    
});
Ext.reg('OrgComDialog', Ext.icss.OrgComDialog);

