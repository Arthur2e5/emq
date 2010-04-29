/*!
 * icss zhaoqy 
 *  2009-07-21 v0.1 
 *  ������̬��Ⱦʱ�������Ҫʹ��ȫѡ��ť���Լ���ѡ���¼�����ô��ʹ�ô��ࡣ
 *  �Ѿ������bug��
 *	1��ѡ��ʱ���޷�׼ȷ�Ļ�ȡ��ѡ�е�record�����bug
          2����һ����ѡ��Ȼ���϶�����������һ����ʾ������Ҳ�ᱻѡ�е�bug������ѡ���3��4��ʱ��������ʱ�������й�����3��4�е�λ��ʱ����ѡ��״̬��
	3����һ���ڵ�һ������ѡ���е�bug
	4��ȫѡ���϶����������ֿհ�����bug
	5�������϶�������ʱ���޷���¼��ѡ���е�bug
	6������ȡ��ѡ���е�bug
 *  2009-07-21 v0.2
 * �Ѿ������bug��
	1�����CheckboxSelectionModel.ocked = trueʱ���޷�ѡ������һ�е�bug
	2������϶����������޷�ʹ��shift+�������ѡ��bug
	3������϶���������ctrl+�����ѡ���ſ�ctrl��ѡ�������У���ǰѡ�е��л��ᱻ������bug
	4�����ȫѡȻ�����϶���������ȫѡ��Թ���ʧ��bug
	5�������һ����ѡ��Ȼ���϶�����������һ����ʾ������Ҳ�ᱻѡ�е�bug������ѡ���3��4��ʱ��������ʱ�������й�����3��4�е�λ��ʱ����ѡ��״̬��
	6�����ctrl+�������ȡ��ѡ���е�bug
 * 
 */
Ext.namespace('Ext.ux.grid.icss');

Ext.ux.grid.icss.RowSelectionModel = Ext.extend(Ext.grid.RowSelectionModel, {
	
	//������Ⱦ��ʱ���Ƿ�ˢ��
	isRefresh : true,
	handleMouseDown : function(g, rowIndex, e){
		if(e.button !== 0){
            return;
        };
        var view = this.grid.getView();
		var vr = view.getVisibleRows();
		var index = rowIndex+vr.first;
        if(e.shiftKey && this.last !== false){
			this.isRefresh = false;
            var last = this.last;
            this.selectRange(last, index, e.ctrlKey,(last-vr.first),rowIndex);
            this.last = last; 
			view.focusRow(rowIndex);
        }else{
			this.isRefresh = true;
            var isSelected = this.isSelected(index);
            if(e.ctrlKey && isSelected){
                this.deselectRow(index,false,rowIndex);
            }else if(!isSelected || this.getCount() > 1){
                this.selectRow(index, e.ctrlKey || e.shiftKey,false,rowIndex);
                view.focusRow(rowIndex);
            }
        }
    },
	
	selectRow : function(index, keepExisting, preventViewNotify,ri){
		if((index < 0 || index >= this.grid.store.getCount())) return;
        var r = this.grid.store.getAt(index);
        if(r && this.fireEvent("beforerowselect", this, index, keepExisting, r) !== false){
            if(!keepExisting || this.singleSelect){
				if(this.grid.store.getCount()==this.getSelections().length){
					this.clearSelections(true);
				}else{
					this.clearSelections();
				}
				var checkers = Ext.query(".x-grid3-hd-inner");
				if(Global.isSelectAll&&checkers.length>0){
					for(var i=0,len =checkers.length ;i<len;i++){
						var hd = new Ext.Element(checkers[i]);
						var isChecker = hd.hasClass('x-grid3-hd-checker');
						if(isChecker){
							hd.removeClass('x-grid3-hd-checker-on');
							Global.isSelectAll = false;
							break;
						}
					}
				}
            }
            this.selections.add(r);
            this.last = this.lastActive = index;
            if(!preventViewNotify){
				if(ri>=0){
					this.grid.getView().onRowSelect(ri);
				}else{
					this.grid.getView().onRowSelect(index);
				}
            }
            this.fireEvent("rowselect", this, index, r);
            this.fireEvent("selectionchange", this);
        }
    },
	
	deselectRow : function(index, preventViewNotify,ri){
        if(this.last == index){
            this.last = false;
        }
        if(this.lastActive == index){
            this.lastActive = false;
        }
        var r = this.grid.store.getAt(index);
        if(r){
            this.selections.remove(r);
            if(!preventViewNotify){
				if(ri>=0){
					this.grid.getView().onRowDeselect(ri);
				}else{
					this.grid.getView().onRowDeselect(index);
				}
            }
            this.fireEvent("rowdeselect", this, index, r);
            this.fireEvent("selectionchange", this);
        }
    },
	
	selectRange : function(startRow, endRow, keepExisting,sindex,eindex){
        if(!keepExisting){
            this.clearSelections();
        }
        if(startRow <= endRow){
            for(var i = startRow,k=sindex; i <= endRow; i++){
                this.selectRow(i, true,false,k);
				k++;
            }
        }else{
            for(var i = startRow,k=sindex; i >= endRow; i--){
                this.selectRow(i, true,false,k);
				k--;
            }
        }
    },
	
	clearSelections : function(fast){
		var ds = this.grid.store;
		var view = this.grid.getView();
		var vr = view.getVisibleRows();
        if(fast !== true){
            var s = this.selections;
            s.each(function(r){
				var index = ds.indexOfId(r.id);
                this.deselectRow(index,false,index-vr.first);
            }, this);
            s.clear();
        }else{
			var rs = ds.getRange(vr.first, vr.last);
			for(var i = 0 ,len = rs.length; i < len; i++){
				var r = rs[i];
				var index = ds.indexOfId(r.id);
				this.deselectRow(index,false,index-vr.first);
			}
            this.selections.clear();
        }
        this.last = false;
    },
	
	selectAll : function(){
        this.selections.clear();
        for(var i = 0, len = this.grid.store.getCount(); i < len; i++){
            this.selectRow(i, true);
        }
    },
	
	onRefresh : function(){
        var ds = this.grid.store, index;
        var s = this.getSelections();
		var view = this.grid.getView();
		var vr = view.getVisibleRows();
		var endRow = Math.min(vr.last,ds.getCount()-1);
		var rs = ds.getRange(vr.first, endRow);
		for(var i = 0 ,len = rs.length; i < len; i++){
			if(s.length==0){
				break;
			}
			var r = rs[i];
			var isSelect = this.isIdSelected(r.id);
			if(isSelect){
				index = ds.indexOfId(r.id);
				var ri = index - vr.first;
				this.selectRow(index, true,false,ri);
			}
        }
        if(s.length != this.selections.getCount()){
            this.fireEvent("selectionchange", this);
        }
    }
});