/**
 * use common tree
 * 
 * @param
 * 
 * rootNameΪ������ָ��һ����,idΪ�ڵ��Ψһ��־,nid��¼�ڵ��������Ϣ,û��������ָ��,����Ϊ�ա�
 * treeTypeָ����������;('1':�����+������+���վ),('2':�����+ά������+��Ա),('3':�õ继+���վ),('4':��·+���վ)�������ָ��Ĭ��'1'��
 * treeLevel
 * �ڵ���ĳ�����в�εı��¡����磺('1':�����+������+���վ)��'1_1'��ʾ���ڵĲ��Ϊ�������;'1_2'��ʾ���ڵĲ��Ϊ���������������ơ�
 * ���ڵ�Ϊ���ڵ���Ϊroot��
 */

Ext.tree.commonTree = function(config) {
	this.initConfig = config || {};
	Ext.tree.commonTree.superclass.constructor.call(this, config);
};

Ext.tree.commonTree = Ext.extend(Ext.tree.TreePanel, {
			initComponent : function() {
				var treeLoader = new Ext.tree.DWRTreeLoader({
							fn : PlantService.getCommonTreeNode
						});
				var treeType;
				if (undefined == this.treeType) {
					treeType = '1';
				} else {
					treeType = this.treeType;
				}
				var rootName;
				if (undefined == this.rootName) {
					rootName = '����';
				} else {
					rootName = this.rootName;
				}
				var root = new Ext.tree.AsyncTreeNode({
							text : rootName,
							treeType : "1",
							id : '-1',
							treeLevel : 'root',
							nid : '-1'
						});

				this.root = root;
				this.border = false;
				this.loader = treeLoader;
				this.autoScroll = true;
				treeLoader.on("beforeload", function(loader, node) {
							loader.args[0] = node.id;
							loader.args[1] = node.attributes.treeLevel;
							loader.args[2] = treeType;
							loader.args[3] = node.attributes.nid;
						});
				Ext.tree.commonTree.superclass.initComponent.call(this);
				this.init();
			},
			// ȡѡ�нڵ�
			getSelectedNode : function() {
				return this.getSelectionModel().getSelectedNode();
			},
			init : function() {
				this.getRootNode().expand();
			},
			// ȡѡ�нڵ�ĸ��ڵ�
			getParentNode : function() {
				return this.getSelectionModel().getSelectedNode().parentNode;
			}
		});

Ext.reg('commonTree', Ext.tree.commonTree);
