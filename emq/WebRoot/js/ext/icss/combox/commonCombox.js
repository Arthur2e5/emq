/**
 * use common combox
 * 
 * @param co(condition)
 *            ��ѯ��������,fn����Դ����,
 *          ����ʾ����var
 *            combo = Ext.form.commonCombox({ id:"combo", fn:Util.getCombo,
 *            ��ȡֵ��Ext.getCmp('').getValue(); ��ȡ��ʾֵ��Ext.getCmp('').getRawValue();
 *            ��ȡ������Ϣ��getOtherInfo()
 * 
 */

Ext.form.commonCombox = function(config) {
	this.initConfig = config || {};
	Ext.form.commonCombox.superclass.constructor.call(this, config);
};

Ext.form.commonCombox = Ext.extend(Ext.form.ComboBox, {
			initComponent : function() {
				var CommonCheckbox = Ext.data.Record.create([{
							name : "code",
							mapping : "code"
						}, {
							name : "text",
							mapping : "text"
						}, {
							name : "otherInfo",
							mapping : "otherInfo"
						}]);
				if (typeof(this.co) == 'undefined') {
					this.co = null;
				}
				if (typeof(this.fn) == 'undefined') {
					new Ext.Msg.alert("����!", "���������Ч������Դ��");
				} else {
					this.store = new Ext.data.DWRStore({
								fn : this.fn,
								fields : CommonCheckbox
							});
				}
				this.valueField = "code";
				this.displayField = "text";
				this.typeAhead = true;
				this.selectOnFocus = true;
				this.mode = 'remote';
				this.triggerAction = 'all';
				this.readOnly = true;
				Ext.form.commonCombox.superclass.initComponent.call(this);
			},
			init : function(co) {
				if (co != null) {
					this.store.load({
								params : [co]
							});
				}
			},
			getOtherInfo : function() {
				var id = this.getValue();
				var index = this.store.find("code", id);
				var otherInfo = null;
				if (index >= 0) {
					var record = this.store.getAt(index);
					otherInfo = record.get("otherInfo");
				}
				return otherInfo;
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
