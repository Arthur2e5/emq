

Ext.onReady(function() {
			createMianUI();
			initCombox();
		});

function createMianUI() {
	
	var combo = new Ext.form.commonCombox({
				id : "combo",
				fn : PlantService.testCombox1,
				fieldLabel:"��ҵ��˾",
				emptyText:"��ѡ����ҵ��˾"
			});
	var frm = new Ext.form.FormPanel({
	  items:[combo],
	  bbar:[{text:"ok",handler:advent}]
	});
	frm.render(Ext.getBody());
	
}

function initCombox(){
  Ext.getCmp("combo").on("beforequery",function(queryEvent){
     queryEvent.query =["11","22"];
  });
}

function advent(){
  //var id =(Ext.getCmp("combo").getValue());
 // alert(Ext.getCmp("combo").getRawValue());
  /*var other ;
  var store = Ext.getCmp("combo").store;
  store.each(function(rec){
     var rid =rec.get("code");
     if(id==rid){
       other = rec.get("otherInfo");
       alert(other);
       return ;
     }
  });
  alert("here");*/
  //Ext.Msg.alert(Ext.getCmp("combo").getOtherInfo());
	alert(Ext.getCmp("combo").getValue());
}