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
//	var airListStore = new Ext.data.DWRStore({
//		id:'airListStore',
//		fn:null,
//	 	pruneModifiedRecords:true,
//        fields: [
//           {name: 'id',type:'int'},//��ˮ��
//           {name: 'organise',type:'string'},//������������
//           {name: 'organiseId',type:'int'},//��������ID
//           {name: 'sortId',type:'string'}, // �г̵���       
//           {name: 'boardingDate',type:'date'},//�˻�����
//           {name: 'personId',type:'int'},//�˻���ID
//           {name: 'name',type:'string'},//�˻�������
//           {name: 'duty',type:'string'},//�˻���ְ��
//           {name: 'dutyId',type:'int'},//�˻���ְ��ID
//           {name: 'voyage',type:'string'},//����
//           {name: 'fullPrice',type:"float"},//ȫ��
//		   {name: 'agio',type:'string'},//�ۿ�
//		   {name: 'agioPrice',type:"float"},//�ۿۼ�
//		   {name: 'tax',type:"float"},//˰��
//		   {name: 'totalPrice',type:"float"},//Ʊ����  С��
//		   {name: 'writeOffFee',type:"float"},//�޶��׼
//		   {name: 'backingSum',type:"float"},//���ڽ���
//		   {name: 'booker',type:'string'},//��Ʊ��
//		   {name: 'remark',type:'string'},//
//		   {name: 'auditState',type:'int'},//����״̬
//		   {name: 'pid',type:'string'},//����
//		   {name: 'auditDate',type:'date'},//�ۺϹ������ʱ��
//  		   {name: 'auditPersonId',type:'string'},//�ۺϹ��������ID
//  		   {name: 'auditPersonName',type:'string'},//�ۺϹ��������NAME
//  		   {name: 'finalPersonId',type:'string'},//���������ID
//  		   {name: 'finalPersonName',type:'string'},//���������NAME
//  		   {name: 'finalAuditDate',type:'string'},//�������ʱ��
//  		   {name: 'writeState',type:'int'},//����÷ѱ���״̬
//  		   {name: 'importDate',type:'date'}//��Ʊ����ʱ��
//        ]
//    });
//    var rowNum =  new Ext.grid.RowNumberer({
//     width : 30
//    });
//    rowNum.locked = true;
// 	 var sm = new Ext.grid.CheckboxSelectionModel();
//     sm.locked = true;
//	var airListCM = new Ext.grid.LockingColumnModel({sumheader:true,columns:[
//		//rowNum,
//		//sm,
//		{header:'��ˮ��',dataIndex:'id',width:50,locked:true,sortable:true,align:'right',sumcaption:'�ϼ�'}
//		,{header:'����',dataIndex:'pid',width:80,locked:true,sortable:true,align:'left'}
//		,{header:'�г̵���',dataIndex:'sortId',locked:true,width:130,sortable:true,align:'right'}
//		,{header:'����״̬',dataIndex:'auditState',locked:true,sortable: true,hidden:false}
//		,{header:'����',dataIndex:'organise',width:90,locked:true,sortable:true,align:'left'}
//		,{header:'����',dataIndex:'name',width:80,locked:true,sortable:true,align:'left'}
//		,{header:'ְ��',dataIndex:'duty',width:80,locked:true,sortable:true,align:'left'}
//		,{header:'�˻�ʱ��',dataIndex:'boardingDate',width:80,locked:false,sortable:true,align:'left',renderer: Ext.util.Format.dateRenderer('Y-m-d')}
//		,{header:'����',dataIndex:'voyage',width:80,locked:false,sortable:true,align:'left'}
//		,{header:'ȫ��',dataIndex:'fullPrice',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'�ۿ�',dataIndex:'agio',locked:false,sortable:true,align:'right'}
//		,{header:'�ۿۼ�',dataIndex:'agioPrice',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'˰��',dataIndex:'tax',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'Ʊ����',dataIndex:'totalPrice',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'���ƽ��',dataIndex:'writeOffFee',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'���ڽ���',dataIndex:'backingSum',locked:false,sortable:true,align:'right',renderer:Util.rmbMoney,issum:true}
//		,{header:'����ʱ��',dataIndex:'importDate',locked:false,width:120,sortable:true,align:'left',renderer: Ext.util.Format.dateRenderer('Y-m-d')}
//		,{header:'��ע',dataIndex:'remark',locked:false,sortable:true,align:'left'}
//	]});	
//	var airListGrid = null;
//	PlantService.testExtGrid(function(list){
//	  var airListGrid = new Ext.grid.GridPanelEx({
//	  	id:"airListGrid"
//	  });
//	  return airListGrid;
//	});
//	var gridEmq = new GridEmq();
	/* 
	 * ����ͷ��װ����
	gridEmq.head="���,����";
	
	var airListGrid = new Ext.grid.GridPanelEx({
	  id:"airListGrid",
	  extdata:gridEmq.getExtGrid(),
	  fn:PlantService.testExtGrid
    });*/
    var gridEmq = new GridEmq();
    gridEmq.head="����,�Ա�,����,ѧ��,����״��,סַ,��˾,ְҵ,����,��������,���˷���";
    gridEmq.tableType=2;
    gridEmq.moreHead=[[";������Ϣ,4;������Ϣ,4;��ע,2"]];
	var airListGrid = new Ext.grid.GridPanelEx({
	  id:"airListGrid",
	  extdata:gridEmq,
	  fn:PlantService.testExtGrid
    });
	
//    	height: Ext.icss.Util.getH(0.7),
//    	trackMouseOver:true,//�����ٹ켣
    	//store:airListStore,
	  	//cm:"undefined", 
//	    loadMask: {msg:'���ڼ������ݣ����Ժ򡭡�'},
//	    frame:true,
//	    border:false
  
	//airListGrid.on('headerclick',Util.Grid.selectAll);
    return airListGrid;
}
/**
 * ������Ʊ��Ϣ��
 * @return {}
 */
function getAirTicketTbar(){
	var form = new Ext.FileImportPanel({
		id:"docForm"
	});
	var tbar = new Ext.Toolbar(	
		{id:'airtbar',items:[
				{xtype:'tbtext',text:'����ʱ��:'},
				{xtype:'tbspacer'},
				{xtype:'datefieldex',fieldLabel:'����ʱ��',id:'importDateStart',format:'Y-m-d',width:85},
				{xtype:'tbtext',text:'��'},
				{xtype:'tbspacer'},
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
				form
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

/**
 * �ӵ�����֯�ṹ�����ڻ�ȡ��Ա
 */
function getPersonInfo(rec){
	Ext.getCmp("personName").setValue(rec.get("hrname"));
}

