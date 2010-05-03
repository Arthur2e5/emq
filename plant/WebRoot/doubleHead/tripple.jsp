<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../comm/comm.jsp"%>
		<link rel="stylesheet" type="text/css" href="../css/GroupHeaderPlugin.css" />
		<style type="text/css">
		 .x-grid3-hd-row TD{border-top:#ccc 1px solid ;}
		</style>
		<script type="text/javascript" src="../js/GroupHeaderPlugin.js"></script>
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '../resources/images/default/s.gif';
Ext.onReady(function() {
   var grid = {
			xtype: 'grid',
			name:'gridLike',
			buttonAlign:"right",
			bbar:[{text:"����EXCEL",handler:copy}],
			title: '����ͷ',
			store: new Ext.data.SimpleStore({
				fields: ['id', 'nr1', 'text1', 'info1', 'special1', 'nr2', 'text2', 'info2', 'special2', 'special3', 'changed'],
				data: [
					['�ױ�', '��', '1', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�1', '��', '2', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�2', '��', '3', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�3', '��', '4', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�4', '��', '5', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�5', '��', '6', '4', '5', '6', '7', '8', '9', '10', '11'],
					['�ױ�6', '��', '7', '4', '5', '6', '7', '8', '9', '10', '11']
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
				defaultSortable: true,
				rows: [
					[
						{rowspan: 2},
						{header: '������Ϣ', colspan: 4, align: 'center'},
						{header: '������Ϣ', colspan: 4, align: 'center'},
						{header: '��ע', colspan: 2, align: 'center', rowspan: 2}
					], [
						{},
						{header: '�������', colspan: 3, align: 'center'},
						{},
						{header: '����״��', colspan: 3, align: 'center'}
					]
				]
			}),
			enableColumnMove: false,
			viewConfig: {
				forceFit: true
			},
			plugins: [new Ext.ux.plugins.GroupHeaderGrid()]
		};
	new Ext.Viewport({
		layout: 'fit',
		items: [grid]
	});
	//alert(Ext.get("gridLike"));
});

function copy(){
      var curTbl = document.getElementById('gridLike');
      var oXL = new ActiveXObject("Excel.Application");
      var oWB = oXL.Workbooks.Add();
      var oSheet = oWB.ActiveSheet;
      var sel = document.body.createTextRange();
      sel.moveToElementText(curTbl);
      sel.select();
      sel.execCommand("Copy","false",null);
      oSheet.Paste();
      oXL.
      oXL.Visible = true;
}

		</script>
	</head>
	<body>
		


	</body>
</html>
