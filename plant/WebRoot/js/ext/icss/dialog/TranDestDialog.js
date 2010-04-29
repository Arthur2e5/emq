/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
 
Ext.icss.TranDestDialog = function(config){
	this.initConfig = config || {};
		
	Ext.icss.TranDestDialog.superclass.constructor.call(this, config);
	
};

Ext.icss.TranDestDialog = Ext.extend(Ext.Window, {
    maximizable :true,
    initComponent : function(){
    	if(typeof(this.fn) !== 'function')
    	{
    		Msg.info('���ڴ������ȷ����Ĵ�����');
    	}
    	if(typeof(this.compComponent) == 'undefined')
    	{
    		this.compId = null;
    	}
    	else
    	{
    		this.compId = this.compComponent.getValue();
    	}
    	if(typeof(this.compId)=='undefined'||this.compId == '')
    	{
    		this.compId = null;
    	}

    	if(typeof(UtilService) == 'undefined' || typeof(UtilService.getArriveAreaInfo) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService.getArriveAreaInfo��֧��');
    	}
    		
		var tranDestDialogStore = new Ext.data.DWRStore({id:'tranDestDialogStore',
	        fn:UtilService.getArriveAreaInfo,
	    	fields:[{name:'arriveRegionShortName',type:'string'},
 	               {name:'provinceShortName',type:'string'},
 	               {name:'arriveRegionName',type:'string'},
 	               {name:'id.arriveRegionId',type:'string'},
 	               {name:'pyCode',type:'string'}
 	               ]
	    	});  
	    var pytext = new Ext.form.TextField({xtype:'textfield',width:190,
	    					emptyText:'����ƴ������ĸ',enableKeyEvents:true});
		pytext.on('keyup',this.onFilter,this);
		
	 	var sm = new Ext.grid.RowSelectionModel();
	 	var cm =new Ext.grid.ColumnModelEx({columns:[
	 	    {header: "�˴�ؼ��", width:150,  sortable: false, dataIndex: 'arriveRegionShortName'},
	 	    {header: "ʡ�ݼ��", width:150,  sortable: false, dataIndex: 'provinceShortName'},
	 	    {header: "�˴������", width:150,  sortable: false, dataIndex: 'arriveRegionName'},
	 	    {header: "�˴��id", width:150,  sortable: false,hidden:true, dataIndex: 'id.arriveRegionId'}
	 	 ]});
	 	var tranDestDialogGrid = new Ext.grid.GridPanelEx({
	 	    id:'tranDestDialogGrid',
	 	    store:tranDestDialogStore,
	 	    cm:cm,
	 	    sm:sm,
	 	    height:320,
	 	    tbar:[{xtype:'tbtext',text:'ƴ����'},pytext]
	 	 });
			
	    this.grid = tranDestDialogGrid;
	    this.width = Util.getW(0.5);
		this.height = Util.getH(0.5);
	    this.items = [this.grid];
	    this.layout = 'fit';
	    this.title = 'ѡ���˴��';	    		
		this.modal = true;
		this.buttons = [{text:'ȷ��',handler:this.onOK, scope:this.ownerCt},
						{text:'ȡ��',handler:this.onCancel,scope:this.ownerCt}];
        this.buttonAlign = 'center';
        this.closeAction = 'hide';
        this.grid.on('rowdblclick',this.onRowdbclick);
	        
        Ext.icss.TranDestDialog.superclass.initComponent.call(this);
    },
    refresh:function(){
    	if(typeof(this.compComponent) == 'undefined')
    	{
    		this.compId = null;
    	}
    	else
    	{
    		this.compId = this.compComponent.getValue();
    	}
    	if(typeof(this.compId)=='undefined'||this.compId == '')
    	{
    		this.compId = null;
    	}
    	if(this.grid){
    		var ds = this.grid.getStore();
    		ds.load({params:[this.compId]});
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
	    	this.grid.store.filter('pyCode',text,true)
    	}
	}
});
Ext.reg('TranDestDialog', Ext.icss.TranDestDialog);

