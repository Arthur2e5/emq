

Ext.onReady(function() {
			createMianUI();
		});

function createMianUI() {
	var combo = new Ext.form.commonCombox({
				id : "combo",
				fn : PlantService.testCombox,
				emptyText:"��ѡ����ҵ��˾"
			});
	var frm = new Ext.form.FormPanel({
	  items:[{xtype:'tbtext',text:'��ҵ��˾'},combo],
	  bbar:[{text:"ok",handler:advent}]
	});
	frm.render(Ext.getBody());
	
}

function advent(){
  alert(Ext.getCmp("combo").getValue());
  alert(Ext.getCmp("combo").getRawValue());
}