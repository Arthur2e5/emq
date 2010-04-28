/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


 
Ext.icss.CigComDialog = function(config){
	this.initConfig = config || {};
	//this.importFlag = null;
	Ext.icss.CigComDialog.superclass.constructor.call(this, config);
	
};

Ext.icss.CigComDialog = Ext.extend(Ext.Window, {
    
    initComponent : function(){
    	if(typeof(this.fn) !== 'function')
    	{
    		Msg.info('���ڴ������ȷ����Ĵ�����');
    	}
    	this.isStillSelling = true;
    	if(typeof(this.isStillSelling)=='undefined'){
    		//�ж��Ƿ�������Ĭ��Ϊ�����������jira�ϵ�HYGSXT-792��
    		this.isStillSelling = true;
    	}

    	if(typeof(UtilService) == 'undefined' || typeof(UtilService.getDistinctCigarette) == 'undefined'){
    			Msg.info('�������ô�js�ļ���jsp�ļ��У����Ӷ�UtilService.getDistinctCigarette��֧��');
    	}
    		
    	var _function = null;
    	if(!this.isStillSelling){
    		//FIXME: ��Ҫͳһʹ��CigTreeNodeģ�ͣ�������TreeNodeģ��
    		_function = UtilService.getDistinctCigarette;
    	}else{
    		_function = UtilService.getDistinctSellingCigarette;
    	}
		var cigComDialogStore = new Ext.data.DWRStore({id:'cigComDialogStore',
	        fn:_function,
	    	fields:[{name:'code',type:'string'},
	    		{name:'text',type:'string'},
	    		{name:'data',type:'string'},
	    		{name: 'pieceCode',type:'string'}]
	    	});  
	    var pytext = new Ext.form.TextField({xtype:'textfield',width:190,
	    					emptyText:'����ƴ������ĸ����̼���',enableKeyEvents:true});
		pytext.on('keyup',this.onFilter,this);
		
		var sm = new Ext.grid.RowSelectionModel();
		var cigComDialogGrid = new Ext.grid.EditorGridPanelEx({
		    	id:'cigComDialogGrid',
		        store: cigComDialogStore,
		        frame:true,
		        //border:true,
		        height:Util.getH(.12),
		        cm: new Ext.grid.ColumnModelEx({columns:[
		            {header: "���̼���", width:150,  sortable: true, dataIndex: 'pieceCode'},
		            {header: "��������", width:350,  sortable: true, dataIndex: 'text'}/*,
		            {header: "ƴ����", width:100,  sortable: true, dataIndex: 'data'}*/
		        ]}),
		        sm:sm,
		       	stripeRows: true,
		       	tbar:[{xtype:'tbtext',text:'ƴ����/���̼���'},pytext]
		    });
			
		    this.grid = cigComDialogGrid;
		    this.width = Util.getW(0.5);
			this.height = Util.getH(0.5);
		    this.items = [this.grid];
		    this.layout = 'fit';
		    this.title = 'ѡ����̹��';	    		
    		this.modal = true;
    		this.buttons = [{text:'ȷ��',handler:this.onOK, scope:this.ownerCt},{text:'ȡ��',handler:this.onCancel,scope:this.ownerCt}];
	        this.buttonAlign = 'center';
	        this.closeAction = 'hide';
	        if(typeof(this.importFlag) =='undefined'){
	        	this.importFlag = 0;
	        }
	        this.cigBarcarrier = '02';
	        this.cigStatus = '1';
	        this.all = false;
	        this.grid.on('rowdblclick',this.onRowdbclick);	   
	        this.pytext = pytext;
	        
        Ext.icss.CigComDialog.superclass.initComponent.call(this);
    },
    refresh:function(){
    	if(this.grid){
    		var ds = this.grid.getStore();
    		if( !this.isStillSelling){	    		
	    		ds.load({params:[this.importFlag,this.cigBarcarrier,this.cigStatus,this.all]});
 		   	}else {
 		   		ds.load({params:[this.importFlag]});
    		}
    	}
    	
    },
    onRowdbclick:function(g, ridx, e ){
    	this.ownerCt.fn(this.ownerCt,this.ownerCt.grid);
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
    		var parten = /[0-9,]/;
    		if(parten.exec(text)){
    			this.grid.store.filter('pieceCode',text,true);
    		}else{
    			var re = new RegExp(text, 'i'); 
	    		this.grid.store.filter('data',text,true);
    		}	    	
    	}
	}								
    
});
Ext.reg('CigComDialog', Ext.icss.CigComDialog);

