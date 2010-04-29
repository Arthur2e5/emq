/*!
 *  2009-07-20 v0.1 icss zhaoqy
 *  ������̬��Ⱦʱ�������Ҫʹ��ȫѡ��ť����ô��ʹ�ô��ࡣ
 *  �Ѿ������bug��
	1��ѡ��ʱ���޷�׼ȷ�Ļ�ȡ��ѡ�е�record�����bug
          2����һ����ѡ��Ȼ���϶�����������һ����ʾ������Ҳ�ᱻѡ�е�bug������ѡ���3��4��ʱ��������ʱ�������й�����3��4�е�λ��ʱ����ѡ��״̬��
	3����һ���ڵ�һ������ѡ���е�bug
	4��ȫѡ���϶����������ֿհ�����bug
	5�������϶�������ʱ���޷���¼��ѡ���е�bug
	6������ȡ��ѡ���е�bug
	7�����⣬�������Ŀ��Ŀǰ��Grid��ֻ����ctrl+��������ѡ������.--�д���֤
 * 
 */
 
Ext.namespace('Ext.ux.grid.icss');

Ext.ux.grid.icss.CheckboxSelectionModel = Ext.extend(Ext.ux.grid.icss.RowSelectionModel, {
    header: '<div class="x-grid3-hd-checker">&#160;</div>',
    width: 20,
    sortable: false,
    menuDisabled:true,
    fixed:true,
    dataIndex: '',
    id: 'checker',
	
	initEvents : function(){
        Ext.grid.CheckboxSelectionModel.superclass.initEvents.call(this);
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
            Ext.fly(view.innerHd).on('mousedown', this.onHdMouseDown, this);

        }, this);
    },
	
    onMouseDown : function(e, t){
		if(e.button === 0 && t.className == 'x-grid3-row-checker'){
            e.stopEvent();
			var g = this.grid, view = g.getView();
			var vr = view.getVisibleRows();
            var row = e.getTarget('.x-grid3-row');
            if(row){
				var index = row.rowIndex;
                index = index+vr.first;
                if(this.isSelected(index)){
                    this.deselectRow(index,false,row.rowIndex);
                }else{
                    this.selectRow(index, true,false,row.rowIndex);
                }
            }
        }
    },
	
	onHdMouseDown : function(e, t){
        if(t.className == 'x-grid3-hd-checker'){
            e.stopEvent();
            var hd = Ext.fly(t.parentNode);
            var isChecked = hd.hasClass('x-grid3-hd-checker-on');
            if(isChecked){
                hd.removeClass('x-grid3-hd-checker-on');
                if(this.grid.store.getCount()==this.getSelections().length){
					this.clearSelections(true);
				}else{
					this.clearSelections();
				}
            }else{
                hd.addClass('x-grid3-hd-checker-on');
				if(!Global.isSelectAll){
					this.selectAll();
				}
            }
        }
    },
	
    renderer : function(v, p, record){
        return '<div class="x-grid3-row-checker">&#160;</div>';
    }
});