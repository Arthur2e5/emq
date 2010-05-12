/*
 * @author foolice
 */

Ext.onReady(function() {
			createMianUI();
			init();
		});
function init() {
	var root = Ext.getCmp('mainTree').getRootNode();
	root.expand();
}

function createMianUI() {
	var combo = new Ext.form.commonCombox({
				id : 'numCombo',
				fn : PlantService.testCombox,
				emptyText : '��ѡ����ʾ����'
			});
	var mainGrid = createMainGrid();
	var mainTree = createMainTree();
	var panel = new Ext.Panel({
				id : "panel",
				layout : "border",
				tbar : [{
							xtype : 'tbspacer'
						}, {
							xtype : 'tbspacer'
						}, {
							xtype : 'tbtext',
							text : '��ʾ����'
						}, combo, {
							xtype : 'tbseparator'
						}, {
							pressed : true,
							text : '��  ѯ',
							id : 'btn_query',
							handler : queryInfo
						}],
				items : [
						// {region:"north",border:false,split:true,layout:'fit',height:23,items:[form]},
						{
					region : "center",
					split : true,
					layout : "fit",
					items : [mainTree]
				}]
			});
	var mainUI = new Ext.Viewport({
				layout : "fit",
				items : [panel]
			});
}

function queryInfo() {
	var curNode = Ext.getCmp('mainTree').getSelectedNode();
	alert(curNode.id);
	alert(curNode.text);
	alert(curNode.attributes.nid);
	Ext.getCmp('mainGrid').getStore().load({
				params : [Ext.getCmp('numCombo').getValue()]
			});
}

function createMainTree() {
	var tree = new Ext.tree.commonTree({
				id : 'mainTree',
				fn : PlantService.getTreeNode,
				rootName : '����'
			});
	var treeLoader = tree.getLoader();
	treeLoader.on("load", function(o, node, response) {
				node.on("click", function(node, event) {
                     alert(node.isLeaf());
                     alert(node.id);
						});
				var childNodes = node.childNodes;
				for (var i = 0; i < childNodes.length; i++) {
					childNodes[i].on("click", function(node, event) {
                      alert(node.isLeaf());
                     alert(node.id);
							});
				}
			});
	return tree;
}

function createMainGrid() {
	var gridEmq = new GridEmq();
	gridEmq.head = "����,�Ա�,����,ѧ��,����״��,סַ,��˾,ְҵ,����,��������,���˷���";
	gridEmq.tableType = 2;
	gridEmq.moreHead = [[";������Ϣ,4;������Ϣ,4;��ע,2"]];
	var mainGrid = new Ext.grid.GridPanelEx({
				id : "mainGrid",
				extdata : gridEmq,
				fn : PlantService.getInstanceExtGrid
			});

	return mainGrid;
}