/*
 * @author foolice
 */

Ext.onReady(function() {
			Ext.BLANK_IMAGE_URL = "../js/ext/resources/images/default/s.gif";
			createMianUI();
			queryInfo();
		});

function createMianUI() {
	var mainPanel = new Ext.Panel({
				id : "mainPanel",
				frame : true,
				collapsible : true,
				titleCollapse : true,
				html : ""
			});
	var view = new Ext.Viewport({
				enableTabScroll : true,
				layout : 'border',
				frame : true,
				autoScroll : true,
				split : true,
				items : [{
							region : "center",
							autoScroll : true,
							layout : "fit",
							tbar : [{
										xtype : "tbtext",
										text : "����һ"
									}, {
										xtype : "tbtext",
										text : "������"
									}, {
										pressed : true,
										text : '��ѯ',
										handler : queryInfo
									}],
							items : [mainPanel]
						}]
			});
}
function queryInfo() {
	var mainPanel = Ext.get("mainPanel");
	var loadMask = new Ext.LoadMask(mainPanel, {
				msg : "����ִ�в�ѯ���Ժ�"
			});
	loadMask.show();
	PlantService.getLikeGridHtml(function callback(str) {
				var tempCmp = Ext.getCmp("mainPanel");
				tempCmp.setHTML(str);
				loadMask.hide();
			});
}
Ext.Panel.prototype.setHTML = function(_html) {
	if (_html) {

		this.body.dom.innerHTML = "";
		this.body.update(typeof _html == 'object'
				? Ext.DomHelper.markup(_html)
				: _html);
		delete _html;
	}
}