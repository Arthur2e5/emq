/**
 * EXT ��ڷ���
 */
Ext.onReady(function(){
	createMainUI();
});
/*Ext.onReady(function() {
   var grid = {
			xtype: 'grid',
			name:'gridLike',
			buttonAlign:"right",
			title: '˫��ͷ',
			store: new Ext.data.SimpleStore({
				fields: ['id', 'nr1', 'text1', 'info1', 'special1', 'nr2', 'text2', 'info2', 'special2', 'special3', 'changed'],
				data: [
					['A1', '��', '1', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A1', '��', '2', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A2', '��', '3', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A3', '��', '4', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A4', '��', '5', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A5', '��', '6', '4', '5', '6', '7', '8', '9', '10', '11'],
					['A6', '��', '7', '4', '5', '6', '7', '8', '9', '10', '11']
				]
			}),
			colModel: new Ext.grid.ColumnModel({
				columns: [
					{header: '����', width: 25, dataIndex: 'id'},
					{header: '�Ա�', width: 25, dataIndex: 'nr1'},
					{header: '����', width: 50, dataIndex: 'text1'},
					{header: 'ѧ��', width: 50, dataIndex: 'info1'},
					{header: '����״��', width: 60, dataIndex: 'special1'},
					{header: 'סַ', width: 25, dataIndex: 'nr2'},
					{header: '��˾', width: 50, dataIndex: 'text2'},
					{header: 'ְҵ', width: 50, dataIndex: 'info2'},
					{header: '����', width: 60, dataIndex: 'special2'},
					{header: '��������', width: 60, dataIndex: 'special3'},
					{header: '���˷���', width: 50, dataIndex: 'changed'}
				],
				rows: [
					[
						{},
						{header: '������Ϣ', colspan: 4, align: 'center'},
						{header: '������Ϣ', colspan: 4, align: 'center'},
						{header: '��ע', colspan: 2, align: 'center'}
					]
				]
			}),
			plugins: [new Ext.ux.plugins.GroupHeaderGrid()]
		};
	new Ext.Viewport({
		layout: 'fit',
		items: [grid]
	});
});*/
/**
 * ����������
 */
function createMainUI(){
	var airTicketPanel = getAirTicketGrid();//������Ʊ��ʾ�б�	
	var tbar = getAirTicketTbar();	//������Ʊ������
	var panel = new Ext.Panel({
		id:"panel",
		layout:"border",
		tbar:tbar,
		items:[
			//{region:"north",border:false,split:true,layout:'fit',height:23,items:[form]},
				{region:"center",split:true,layout:"fit",items:[airTicketPanel]}]
	});
	var view = new Ext.Viewport({
		layout:"fit",
		items:[panel]
	});
	return view;
}
/**
 * ������Ʊ�б�
 * @return {}
 */
function getAirTicketGrid(){
    var gridEmq = new GridEmq();
    gridEmq.head="����,�Ա�,����,ѧ��,����״��,סַ,��˾,ְҵ,����,��������,���˷���";
//    gridEmq.tableType=2;
    gridEmq.renderer="renderIcon";
//    gridEmq.issum="false,true";
//    gridEmq.moreHead=[[";������Ϣ,4;������Ϣ,4;��ע,2"]];
	var airListGrid = new Ext.grid.GridPanelEx({
	  id:"airListGrid",
	  extdata:gridEmq,
	  fn:PlantService.testExtGrid
    });
	
    return airListGrid;
}
/**
 * ������Ʊ��Ϣ��
 * @return {}
 */
function getAirTicketTbar(){
	var form = new Ext.FileImportPanel({
		id:"docForm",
		type:"commonFile"
	});
	var commbo = new Ext.form.commonCombox({
		id:"combo",
		fn:PlantService.testCombox,
		fieldLabel:"��ҵ��˾",
		emptyText:"��ѡ����ҵ��˾"
	});
	var tbar = new Ext.Toolbar(	
		{id:'airtbar',items:[
				{xtype:'datefield',fieldLabel:'��ֹ����',id:'importDateEnd',format:'Y-m-d',width:85},
				{xtype:'tbspacer'},
				{xtype:'tbtext',text:'����'},
				{xtype:'textfield',id:'personName',width:85,listeners:{"focus":function(){openPersonWindow();}}},
				{xtype:'tbspacer'},
				{xtype:'tbtext',text:'���'},
				{xtype:'textfield',id:'sortId',name:'sortId',fieldLabel: '���',width:80},
				{xtype:'tbspacer'},
				{pressed:true,id:'querryAir',text:'��ѯ',handler:querryAir},
				{xtype:'tbseparator'},
				form,
				{pressed:true,id:'downLoad',text:'����',handler:handlerClickBtnDownLoad},
				{xtype:'tbtext',text:'������'},
				commbo,
				{pressed:true,text:'����word',handler:handlerClickBtnAllAreaWord}

	]});
	return tbar;
}
/**
 * ��ѯ
 */
function querryAir(){
//	var co = {};
//	if("" ==Ext.getCmp('takeStartDate').getValue() ||  null == Ext.getCmp('takeStartDate').getValue()){
//		co.boardingSDate = null;		
//	}else{
//		co.boardingSDate = Ext.getCmp('takeStartDate').getValue();	
//	}
//	if("" ==Ext.getCmp('takeEndDate').getValue() ||  null == Ext.getCmp('takeEndDate').getValue()){
//		co.boardingEDate = null;		
//	}else{
//		co.boardingEDate = Ext.getCmp('takeEndDate').getValue();	
//	}
//	if("" ==Ext.getCmp('personName').getValue() ||  null == Ext.getCmp('personName').getValue()){
//		co.name = null;		
//	}else{
//		co.name = Ext.getCmp('personName').getValue();	
//	}
//	co.sortId = Ext.getCmp("sortId").getValue();
//	co.queryType = 2;
//	co.auditState = null;
	Ext.getCmp("airListGrid").load();
}


function handlerClickBtnAllAreaWord()  
{  
  var oWD = new ActiveXObject("Word.Application");  
  Msg.info(oWD);
  var oDC = oWD.Documents.Add("",0,1);  
  var orange =oDC.Range(0,1);  
  var sel = document.body.createTextRange();  
  sel.moveToElementText(airListGrid);  
  sel.select();  
  sel.execCommand("Copy");  
  orange.Paste();  
  oWD.Application.Visible = true;  
}  

function setAirStartDate(com){
	var firstDayOfMonth = (new Date()).getFirstDateOfMonth().format("Y-m-d");
	com.setValue(firstDayOfMonth);	
}

function setAirEndDate(com){
	var today = (new Date()).format("Y-m-d");
	com.setValue(today);
}

function handlerClickBtnDownLoad(){
	PlantService.getDataList(function(fileName){
	    location="do_download.jsp?name="+fileName;
	});
}

function createValidBarDetailGrid()
{
	var grid = Ext.getCmp('validBarDetailGrid');
	if(grid)
	{
		return grid;
	}
	var validBarDetailStore = new Ext.data.DWRStore({id:'validBarDetailStore',fn:null,
	    fields:[{name:'yyear',type:'int'},
	        {name:'mmonth',type:'int'},
	        {name:'wweek',type:'int'},
	        {name:'weekName',type:'string'},
	        {name:'bar',type:'string'},
	        {name:'barName',type:'string'},
	        {name:'comId',type:'string'},
	        {name:'comName',type:'string'},
	        {name:'weekStartDate',type:'date'},
	        {name:'weekEndDate',type:'date'},
	        {name:'targetValue',type:'float'},
	        {name:'state',type:'int'},
	        {name:'stateName',type:'string'}]}); 
	        
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var cm = new Ext.grid.ColumnModelEx({sumheader:true,columns:[sm,	 		 	
	 	{header: "���̹��", dataIndex: 'bar',sortable:true,sumcaption:'�ϼ�'},
	 	{header: "��������", dataIndex: 'barName',sortable:true},
	 	{header: "ָ��", dataIndex: 'targetValue',align:'right',sortable:true,renderer: Util.rmbMoney,issum:true}
       ]});
        
    var validBarDetailGrid = new Ext.grid.GridPanelEx({
        id:'validBarDetailGrid', 
        //title:'����ƻ���Ϣ',
        height:Util.getH(.4),
        store: validBarDetailStore,
        cm: cm,
        sm: sm,
        frame:true,
        border:true
    });
    
    return validBarDetailGrid;
}

/**
 * �ӵ�����֯�ṹ�����ڻ�ȡ��Ա
 */
function getPersonInfo(rec){
	Ext.getCmp("personName").setValue(rec.get("hrname"));
}

