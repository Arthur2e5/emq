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
					{header: '����', width: 50, dataIndex: 'id'},
					{header: '�Ա�', width: 50, dataIndex: 'nr1'},
					{header: '����', width: 50, dataIndex: 'text1'},
					{header: 'ѧ��', width: 50, dataIndex: 'info1'},
					{header: '����״��', width: 60, dataIndex: 'special1'},
					{header: 'סַ', width: 50, dataIndex: 'nr2'},
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
    gridEmq.tableType=2;
    gridEmq.moreHead=[[";������Ϣ,4;������Ϣ,4;��ע,2"]];
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
		type:"mi"
	});
	var commbo = new Ext.form.commonCombox({
		id:"combo",
		fn:PlantService.testCombox,
		fieldLabel:"��ҵ��˾",
		emptyText:"��ѡ����ҵ��˾"
	});
	var tbar = new Ext.Toolbar(	
		{id:'airtbar',items:[
				{xtype:'datefieldex',fieldLabel:'��ֹ����',id:'importDateEnd',format:'Y-m-d',width:85},
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
				commbo
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

/**
 * �ӵ�����֯�ṹ�����ڻ�ȡ��Ա
 */
function getPersonInfo(rec){
	Ext.getCmp("personName").setValue(rec.get("hrname"));
}

