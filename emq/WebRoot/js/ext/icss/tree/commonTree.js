/**
 * use common tree
 * �������в���ֵ䣺
 * (�����:Gdj)(������:Xqs)(���վ:Bdz)(ά������:Whbz)(��Ա:Ry)(�õ继:Ydh)(��·:Xl)
 * ���磺(�����+������+���վ)������getGdj���ֶ��塣����getGdjXqs,����getGdjXqsBdz
 * ('1':�����+������+���վ),('2':�����+ά������+��Ա),('3':�õ继+���վ),('4':��·+���վ)
 */

Ext.tree.commonTree = function(config) {
	this.initConfig = config || {};
	Ext.tree.commonTree.superclass.constructor.call(this, config);
};

Ext.tree.commonTree = Ext.extend(Ext.tree.TreePanel, {
			initComponent : function() {
				var treeAssemble = this.treeAssemble ;
				//��ʼ��loader��һ�㷽������rootΪ���ڵ�ȡ����
				var treeLoader = new Ext.tree.DWRTreeLoader({
							fn :eval("BaseService.get"+treeAssemble[0])
						});
				var rootName;
				if (undefined == this.rootName) {
					rootName = '����';
				} else {
					rootName = this.rootName;
				}
				var root = new Ext.tree.AsyncTreeNode({
							text : rootName,
							id : '-1',
							treeLevel : 'root',
							nextLevel : 'root0'
						});

				this.root = root;
				this.border = false;
				this.loader = treeLoader;
				this.autoScroll = true;
				treeLoader.on("beforeload", function(loader, node) {
					        var treeLevel = node.attributes.treeLevel;
					        var ifleaf =false;
					        if(treeLevel!="root"){
						        loader.fn = eval("BaseService.get"+treeAssemble[treeLevel]);
						        if((treeLevel+1)==treeAssemble.length){
						        	ifleaf = true;
						        }
					        }
					        if(treeLevel=="root"){
					        	treeLevel = 0;
					        }
					        loader.args[0] = node.id;
							loader.args[1] = treeLevel+1;
							loader.args[2] = ifleaf;
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
