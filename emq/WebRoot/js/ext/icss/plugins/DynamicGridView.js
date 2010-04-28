/*!
 * ����Ext2.1ʵ�ַ�����̬��Ⱦ(dynamic)���Լ������У��кϼƣ�ȫѡ�ȹ�������
 *   icss/zhaoqy
 */
/**
 * @class Ext.ux.grid.icss.DynamicGridView
 * @extends Ext.grid.GridView
 *  2009-07-19  v0.1 
 *	1��������̬��Ⱦ���ݣ��������ԣ�2000�У�40�У�����5�У�ÿ����ʾ10�У�����Ⱦ�ٶ���3�����¡�
	2��֧�����������ܣ����С������в��ٳ����������������
	3��֧���кϼƹ���(�������е��У���������Ⱦ����)��
	4��֧�������ֶ�������(����������)
	5��֧�ְ���Ҫ������ĳ��Ϊ����ɫ(�����ɫ)�Ĺ��ܡ�
	6��֧�����ⶩ�Ʊ���е����ⵥԪ��ĸ�ʽ�Ĺ��ܡ�
	7��֧�ֶ�̬��չ�С�
	8��֧���е�ѡ���¼���
	�޸�����֪bug��
	1�����IE������ж�ʧ����ʾ��bug.
	2�����IE�º��������λ�ò���ȷ��bug.
	3�����IE�º���������϶�ʱ��ͷ������bug.
	4�����IE�¼���checkBox�������кͲ������жԲ����bug.
	5�������һ��IE�����û�������У��Ҳ�����������������bug.
 *  2009-07-20  v0.2��
	1�����֧��CheckboxSelectionModel��ѡ�Ĺ��� ������ʵ���˷�����Ⱦʱ����ѡ��ģʽ��Ext.ux.grid.icss.RowSelectionModel
	2�������IE8(7)��ż�����ֵģ���Ϊ������ʾ������׼ȷ,���´�Ƭ�հ��е�bug
 *  2009-07-22  v0.3��
	1��֧��������Grid����������Ļ��ֹ�����Ⱦ�¼������ҿ����Զ��廬�ֹ���һ����Ⱦ��������
	2������˵��������ߣ������ʱ�����ײ������հ׵�bug��
	3�����������ϸ��������,��������ж�ѡ,������ʱ��᲻ͣ��ѯϸ�����ݣ����¹�������������������bug��
	4��������ȫѡ���߲�ȫѡʱ���϶���������Ⱦʱ��Ч�ʡ�
	5������һ������refreshHeader����ʾ���϶��������Լ�������껬��ʱ���Ƿ�������Ⱦ��ͷĬ��Ϊfasle��
	      ����Ժ��и��ı�����ݣ���ͷ�ĺϼ����ἴʱ�仯��������ôֻ��Ҫ��Grid������һ�����ü��ɣ�
		viewConfig : {
			refreshHeader:true
		},
 *  2009-07-23  v0.4��
	1���������϶����������߹�������ʱ����ȾЧ�ʡ�IE8�²��ԣ�1000�����ݣ�40�У�����5�У��϶�(���ֹ���)��������Ⱦ��ʱ���ں��뼶��
	2��������ϸ���ҳ�棬ʹ��sm.isRefresh�������Ƿ�ˢ��ϸ����������ϸ��ҳ��ȫѡʱ�϶����������߹�������ʱ����ȾЧ�ʡ�
	      ����PreplanEndAuditMain.js��,�ڻ�ȡ��ϸ�Ĵ���֮ǰ��һ���жϣ����Է�ֹȫѡ״̬���϶�������ʱ��ͣˢ��ϸ��������
		//��װѡ����ʱ���¼�����
		var sm = preplanGrid.getSelectionModel(); 
		sm.on('rowselect',function(sm , row, rec){
	  		//ȡ��ϸ
			if(sm.isRefresh){//��һ���ж�
				getPreplanDetail(rec);
			}
		});
	3��������ȫѡʱ�����϶�����������ѡ��ĳ���޷�ȡ������ѡ���е�bug
	4�����������²�ѯ�󣬹���������ͣ�������²�ѯ֮ǰ��λ�õ�bug
 *  2009-07-23  v0.5��
	1�������˴�������(2000������)ȫѡ�϶�ʱ��IE�����ġ��Ƿ�ֹͣ���нű�����bug��
	2��������������(2000������)ȫѡ�󣬵��ĳ��ȡ��������ѡ��״̬��Ч�ʡ�
 *  2009-07-25  v0.6��
	1��ȥ���˲���Ҫ�����ԡ���Ext.ux.grid.icss.CheckboxSelectionModel��ʵ�����˵�����
	2����DynamicGridView��Lockinggrid���룬������Ŀ���Լ�������ЩGrid��Ҫʹ�ö�̬��Ⱦ�����
	3����BufferView����ΪDynamicGridView�����ӷ���ʵ�ֵ�ԭ�������˱�Ҫ��ע�͡�
	4��������������һ������ʾʱ��������������Ȳ���ȷ��bug��
 */
 
Ext.namespace('Ext.ux.grid.icss');

Ext.ux.grid.icss.DynamicGridView = function(config) {
    Ext.apply(this, config);
    this.templates = {};
    this.templates.master = new Ext.Template(
        '<div class="x-grid3" hidefocus="true"><div class="liveScroller"><div></div></div>',
            '<div class="x-grid3-viewport"">',
                '<div class="x-grid3-header"><div class="x-grid3-header-inner"><div class="x-grid3-header-offset">{header}</div></div><div class="x-clear"></div></div>',
                '<div class="x-grid3-scroller" style="overflow-y:hidden !important;"><div class="x-grid3-body">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
            "</div>",
            '<div class="x-grid3-resize-marker">&#160;</div>',
            '<div class="x-grid3-resize-proxy">&#160;</div>',
        "</div>"
    );
    this._gridViewSuperclass = Ext.ux.grid.icss.DynamicGridView.superclass;
    this._gridViewSuperclass.constructor.call(this);
};
Ext.extend(Ext.ux.grid.icss.DynamicGridView,Ext.grid.GridView, {
	/**
	 * @cfg {Number} rowHeight
	 * ÿ�еĸ߶ȣ�Ĭ����19�������Ĭ�ϸ߶ȣ���Ҫ�޸ġ�
	 */
	rowHeight:19,
	borderHeight:2,
	scrollDelay:50,
	liveScroller:null,
    liveScrollerInset:null,
	horizontalScrollOffset:17,//IE7��8��ֵ�̶�����Ҫ�޸�
    hdHeight:0,
    rowClipped:0,
	lastRowIndex:0,
    visibleRows:1,
	scrollOnceAmount:3,//��껬��ÿ�ι���ʱ��Ⱦ������
	refreshHeader:false,//�Ƿ�ˢ�±�ͷ���ڲ���Ҫ��̬���±�ͷ����(����ϼ�)������£�ѡ��false���������ȾЧ��
	renderHeader:false,
	
	initElements : function(){
        var E = Ext.Element;
        var el = this.grid.getGridEl().dom.firstChild;
	    var cs = el.childNodes;
	    this.el = new E(el);
        this.mainWrap = new E(cs[1]);

        // liveScroller and liveScrollerInset
        this.liveScroller = new E(cs[0]);
        this.liveScrollerInset = this.liveScroller.dom.firstChild;
        this.liveScroller.on('scroll', this.onLiveScroll,this);

        var thd = this.mainWrap.dom.firstChild;
	    this.mainHd = new E(thd);
	    this.hdHeight = thd.offsetHeight;
	    this.innerHd = this.mainHd.dom.firstChild;
        this.scroller = new E(this.mainWrap.dom.childNodes[1]);
        if(this.forceFit){
            this.scroller.setStyle('overflow-x', 'hidden');
        }
        this.mainBody = new E(this.scroller.dom.firstChild);
        this.mainBody.on('mousewheel', this.syncMousewheel,this);
	    this.focusEl = new E(this.scroller.dom.childNodes[1]);
        this.focusEl.swallowEvent("click", true);

        this.resizeMarker = new E(cs[2]);
        this.resizeProxy = new E(cs[3]);
    },

	getStyleRowHeight : function(){
		return Ext.isBorderBox ? (this.rowHeight + this.borderHeight) : this.rowHeight;
	},

	getCalculatedRowHeight : function(){
		return this.rowHeight + (Ext.isBorderBox ?this.borderHeight : 0);
	},

	getVisibleRowCount : function(){
		var rh = this.getCalculatedRowHeight();
		var visibleHeight = this.scroller.dom.clientHeight;
		return (visibleHeight < 1) ? 0 : Math.floor(visibleHeight / rh);
	},

	getVisibleRows: function(){
		var scrollTop = this.liveScroller.dom.scrollTop;
		var count = this.getVisibleRowCount();
		var start = (scrollTop == 0 ? 0 : Math.floor(scrollTop/this.getCalculatedRowHeight())-1);
		return {
			first: Math.max(start, 0),
			last: Math.min(start + count, this.ds.getCount()-1)
		};
	},
	
	// private
	refreshRow : function(record){
        var ds = this.ds, index;
		var vr = this.getVisibleRows();
        if(typeof record == 'number'){
            index = record;
            record = ds.getAt(index);
        }else{
            index = ds.indexOf(record);
        }
		//������ڵ�ǰ����ˢ��û���壬�϶���ʱ����Ȼ��ˢ��
		if(index>=vr.first&&index<=vr.last){
			var cls = [];
			this.insertRows(ds, index, index, true);
			var  currentIndex = index - vr.first;
			this.getRow(currentIndex).rowIndex = index;
			this.onRemove(ds, record, currentIndex+1, true);
			this.fireEvent("rowupdated", this, currentIndex, record);
		}
    },
	
	isRowRendered: function(index){
		var row = this.getRow(index);
		return row && row.childNodes.length > 0;
	},
	
	isRowHolderRendered: function(index){
		var row = this.getRow(index);
		return row;
	},

	//�϶��������������¼�
	onLiveScroll : function(){
		if (this.scrollDelay) {
			if (!this.renderTask) {
				this.renderTask = new Ext.util.DelayedTask(this.refreshBody, this);
			}
			this.renderTask.delay(this.scrollDelay);
			var mb = this.scroller.dom;
			this.lockedScroller.dom.scrollTop = mb.scrollTop;
		}
	},
	
	//��Ⱦ
	refreshBody : function(){
        this.grid.stopEditing();
        var result = this.renderBody();
        this.mainBody.update(result);
        this.processRows(0, true);
		this.refreshPart();
	},
	
	renderBody : function() {
		var vr = this.getVisibleRows();
        var markup = this.renderRows(vr.first,vr.last);//ֻ��Ⱦ��ǰ��Ļ��ʾ����
        return this.templates.body.apply({rows: markup});
    },
	
	scrollToTop : function() {
        Ext.ux.grid.icss.DynamicGridView.superclass.scrollToTop.call(this);
        this.liveScroller.dom.scrollTop = 0;
        this.liveScroller.dom.scrollLeft = 0;
    },
	
	syncLiveScroll : function(first){
		var scrollTopsss = (first+1)*this.getCalculatedRowHeight();
		this.liveScroller.dom.scrollTop = scrollTopsss;
	},
	
	//�����������¼���ֻ֧��IE
	syncMousewheel : function(e){
		var vr = this.getVisibleRows();
		if(e.getWheelDelta()==-1){//��������
			var last = Math.min(vr.last+this.scrollOnceAmount,this.ds.getCount()-1);
			var first = vr.first + this.scrollOnceAmount;
			if(last == (this.ds.getCount()-1)){
				first = last - this.getVisibleRowCount();
			}
			this.syncLiveScroll(first);
		}else if(e.getWheelDelta()==1){//��������
			var first = Math.max(vr.first-this.scrollOnceAmount,0);
			var last = vr.last - this.scrollOnceAmount;
			if(first == 0){
				last = first + this.getVisibleRowCount();
			}
			this.syncLiveScroll(first);
		}
		var mb = this.scroller.dom;
		this.lockedScroller.dom.scrollTop = mb.scrollTop;
	},
	
	onColumnWidthUpdated : function(col, w, tw){
        this.adjustVisibleRows();
        this.adjustBufferInset();
    },
	
	onAllColumnWidthsUpdated : function(ws, tw){
        this.adjustVisibleRows();
        this.adjustBufferInset();
    },
	
	layout : function(){
        if(!this.mainBody){
            return;
        }
        var g = this.grid;
        var c = g.getGridEl(), cm = this.cm,
                expandCol = g.autoExpandColumn,
                gv = this;

        var csize = c.getSize(true);
        var vw = csize.width;
        if(vw < 20 || csize.height < 20){
            return;
        }

        if(g.autoHeight){
            this.scroller.dom.style.overflow = 'visible';
        }else{
            this.el.setSize(csize.width, csize.height);
            var hdHeight = this.mainHd.getHeight();
            var vh = csize.height - (hdHeight);
            this.scroller.setSize(vw, vh);
            if(this.innerHd){
                this.innerHd.style.width = (vw)+'px';
            }
        }
        this.liveScroller.dom.style.top = this.hdHeight+"px";
        if(this.forceFit){
            if(this.lastViewWidth != vw){
                this.fitColumns(false, false);
                this.lastViewWidth = vw;
            }
        }else {
            this.autoExpand();
        }
        this.adjustVisibleRows();
        this.adjustBufferInset();
        this.onLayout(vw, vh);
    },
    
    
	
    adjustBufferInset : function(){
        var liveScrollerDom = this.liveScroller.dom;
        var g = this.grid, ds = g.store;
        var c  = g.getGridEl();
        var elWidth = c.getSize().width;

		if(!ds.totalLength){
			ds.totalLength = 0;
		}
        var hiddenRows = (ds.totalLength == this.visibleRows-this.rowClipped)
                       ? 0
                       : Math.max(0, ds.totalLength-(this.visibleRows-this.rowClipped));
        if (hiddenRows == 0) {
            liveScrollerDom.style.display = 'none';
            return;
        } else {
            liveScrollerDom.style.display = '';
        }
        var scrollbar = this.cm.getTotalWidth()+this.scrollOffset > elWidth;
        var contHeight = liveScrollerDom.parentNode.offsetHeight +
                         ((ds.totalLength > 0 && scrollbar)
                         ? - this.horizontalScrollOffset
                         : 0)
                         - this.hdHeight;
        liveScrollerDom.style.height = Math.max(contHeight, this.horizontalScrollOffset*2)+"px";
        if (this.rowHeight == -1) {
            return;
        }
		this.liveScrollerInset.style.height = (hiddenRows == 0 ? 0 : contHeight+(hiddenRows*this.getCalculatedRowHeight()))+"px";
    },
	
    adjustVisibleRows : function(){
		if (this.rowHeight == 19) {
			if (this.getRows()[0]) {
				this.rowHeight = this.getRows()[0].offsetHeight;
                if (this.rowHeight <= 0) {
                    this.rowHeight = 19;
                    return;
                }
            } else {
                return;
            }
		}
        var g = this.grid, ds = g.store;
        var c = g.getGridEl();
        var cm = this.cm;
        var size = c.getSize();
        var width = size.width;
        var vh = size.height;
        var vw = width-this.scrollOffset;
        if (cm.getTotalWidth() > vw) {
            vh -= this.horizontalScrollOffset;
        }
        vh -= this.mainHd.getHeight();
        var totalLength = ds.totalLength || 0;
		var visibleRows = Math.max(1, Math.floor(vh/this.getCalculatedRowHeight()));
        this.rowClipped = 0;
		if (totalLength > visibleRows && this.getCalculatedRowHeight() / 3 < (vh - (visibleRows*this.getCalculatedRowHeight()))) {
            visibleRows = Math.min(visibleRows+1, totalLength);
            this.rowClipped = 1;
        }
        if (this.visibleRows == visibleRows) {
            return;
        }
        this.visibleRows = visibleRows;
        if (this.rowIndex + (visibleRows-this.rowClipped) > totalLength) {
            this.rowIndex     = Math.max(0, totalLength-(visibleRows-this.rowClipped));
            this.lastRowIndex = this.rowIndex;
        }
    },
	
	refreshPart : function(){
        var ds = this.grid.store, index;
		var view = this.grid.getView();
		var sm = this.grid.getSelectionModel();
		sm.isRefresh = false;
		var s = sm.getSelections();
		var vr = view.getVisibleRows();
		var endRow = Math.min(vr.last,ds.getCount()-1);
		var rs = ds.getRange(vr.first, endRow);		
		for(var i = 0 ,len = rs.length; i < len; i++){
			if(s.length==0){
				break;//���Ч�ʣ�û��ѡ����Ļ�ֱ��break
			}
			var r = rs[i];
			var isSelect = sm.isIdSelected(r.id);
			if(isSelect){
				index = ds.indexOfId(r.id);
				var ri = index - vr.first;
				sm.selectRow(index, true,false,ri);
			}
        }
        if(s.length != sm.selections.getCount()){
            sm.fireEvent("selectionchange", sm);
        }
    }
});