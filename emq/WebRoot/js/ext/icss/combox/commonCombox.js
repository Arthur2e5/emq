/**
 * use common combox
 * 
 * @param co(condition)
 *            ��ѯ��������,fn����Դ����,
 *            isAll:false,�Ƿ���ȫ��,Ĭ��Ϊû�С�hasSelect:false�Ƿ������ѡ��,Ĭ�ϲ������� ����ʾ����var
 *            combo = Ext.form.commonCombox({ id:"combo", fn:Util.getCombo,
 *            valueField:"code",displayField:"text"})
 *            ��ȡֵ��Ext.getCmp('').getValue(); ��ȡ��ʾֵ��Ext.getCmp('').getRawValue();
 * 
 */

Ext.form.commonCombox = function(config) {
	this.initConfig = config || {};
	Ext.form.commonCombox.superclass.constructor.call(this, config);
};

Ext.form.commonCombox = Ext.extend(Ext.form.ComboBox, {
			initComponent : function() {
				if (typeof(this.co) == 'undefined') {
					this.co = null;
				}
				if (typeof(this.fn) == 'undefined') {
					new Ext.Msg.alert("����!", "���������Ч������Դ��");
				} else {
					this.store = new Ext.data.DWRStore({
								fn : this.fn,
								fields : [{
											name : this.valueField
										}, {
											name : this.displayField
										}]
							});
				}
				this.typeAhead = true;
				this.selectOnFocus = true;
				this.mode = 'remote';
				this.triggerAction = 'all';
				this.readOnly = true;
				this.on('beforequery', this.onBeforeQuery);
				Ext.form.commonCombox.superclass.initComponent.call(this);
			},
			onBeforeQuery : function(queryEvent) {
				if (this.co!=null) {
					queryEvent.query = [this.co];
				}
			},
			init : function(co) {
				if (co!=null) {
					this.store.load({
								params : [co]
							});
				} 
			}
		});
Ext.reg('commonCombox', Ext.form.commonCombox);

/*
 * ��������Դ����ʾ���� var carstore = new Ext.data.SimpleStore({ fields: ['yearflag',
 * 'yearname'], data : [ ['0','ȫ��'], ['1','�ϰ���'], ['2','�°���'] ] }); var
 * halfyearcombo = new Ext.form.ComboBox({ store: carstore, id:'halfyearcombo',
 * editable:false, displayField:'yearname', valueField:'yearflag', typeAhead:
 * true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
 * fieldLabel:'�����־' });
 */
