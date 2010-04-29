/**
 * ��������η�װ
 * @author �ΰ���
 */
selectsupport = Class.create();

selectsupport.prototype = {
  initialize: function(selectId, initSql, valueProp, textProp){
    this.selectId = selectId;
    this.initSql = initSql;
    this.valueProp = valueProp;
    this.textProp = textProp;
    this.sendRequestForInit();
  },
  sendRequestForInit: function() {
    if (this.handlingInitRequest) {
      this.pendingInitRequest = true;
      return;
    }
    
    this.handlingInitRequest = true;
    this.callDWRInitEngine();
  },
  callDWRInitEngine: function(){
    // ���浱ǰ����ָ��    
    var tempThis = this;
    DOMSelectManager.getInitComboObjects(this.initSql, function(dwrResponse) {
   		tempThis.initSelect(dwrResponse);
      tempThis.handlingInitRequest = false;
      if (tempThis.pendingInitRequest) {
        tempThis.pendingInitRequest = false;
        tempThis.sendRequestForInit();
      }
    });
  },
  initSelect: function(data) {
    DWRUtil.removeAllOptions(this.selectId); // �Ƴ�������ǰ����
    var defaultSelect = [{ name:'--ȫ��--', id:'-1' }];
		DWRUtil.addOptions(this.selectId, defaultSelect, 'id', 'name');
    DWRUtil.addOptions(this.selectId, data, this.valueProp, this.textProp);
  }
}