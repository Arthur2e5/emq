/**
 * use common tree
 * �������в���ֵ䣺
 * (�����:Gdj)(������:Xqs)(���վ:Bdz)(ά������:Whbz)(��Ա:Ry)(�õ继:Ydh)(��·:Xl)
 * ������getGdj���ֶ��塣����getGdjXqs
 * ('1':�����+������+���վ),('2':�����+ά������+��Ա),('3':�õ继+���վ),('4':��·+���վ)
 */

Ext.tree.commonTree = function(config) {
	this.initConfig = config || {};
	Ext.tree.commonTree.superclass.constructor.call(this, config);
};

Ext.tree.commonTree = Ext.extend(Ext.tree.TreePanel, {
			initComponent : function() {
				var serviceJs ="PlantService" ;
				var treeAssemble = this.treeAssemble ;
				var levelNum = treeAssemble.length ;
				var initRootFn = serviceJs+".get"+treeAssemble[0];
				//��ʼ��loader��һ�㷽������rootΪ���ڵ�ȡ����
				var treeLoader = new Ext.tree.DWRTreeLoader({
							fn :eval(initRootFn)
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
					        var nextLevel = node.attributes.nextLevel;
					        var ifleaf =false;
					        if(treeLevel!="root"){
					          var curL = Number(treeLevel.substring(4,treeLevel.length));
					          var nextL =curL+1;
					          nextLevel ="root"+nextL;
					          if((nextL+1)==levelNum){
					            ifleaf = true ;
					          }
					          var cfn ="";
					          for(var i=0;i<=nextL;i++){
					          	cfn +=treeAssemble[i];
					          }
					          loader.fn =eval(serviceJs+".get"+cfn);
					        }
					        loader.args[0] = node.id;
							loader.args[1] = treeLevel;
							loader.args[2] = nextLevel;
							loader.args[3] = ifleaf;
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
