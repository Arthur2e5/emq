/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
 
/**
 * @class Ext.grid.EditorGridPanelEx
 * @extends Ext.grid.EditorGridPanel
 * @author ��Ңΰ
 * @version 1.0
 * <p>������Ϊ���Ӻϼ��ж���Ƶ�</p>
 * <br><br>Usage:
 * <pre><code>
	 function createPreplanDetailGrid()
	{
		 var preplanDetailStore = new Ext.data.DWRStore({id:'preplanDetailStore',fn:UIPreContractReport.getPreContractDetail,
	        fields: [
	           {name: 'bar'},
	           {name: 'itemName'},
	           {name: 'storeAmount', type: 'float'},
	           {name: 'endAmount',type:'float'},
	           {name: 'currentRate',type:'float'},
	           {name: 'weekPercent',type:'float'},
	           {name: 'confirmAmount',type:'float'},
	           {name: 'accPreAmount',type:'float'},
	           {name: 'surplusTargetAmount',type:'int'},
	           {name:'surplusHyearAmount',type:'float'}
	        ]
	    });
	    
		var sm = new Ext.grid.CheckboxSelectionModel();
	    var preplanDetailGrid = new Ext.grid.EditorGridPanelEx({
	    	id:'preplanDetailGrid',
	    	title:'���̹����ϸ',
	        store: preplanDetailStore,
	        cm: new Ext.grid.ColumnModelEx({sumheader:true,columns:[
	            sm,
	            {header: "��������", width:90,  sortable: true, dataIndex: 'bar',sumcaption:'�ϼ�'},
	            {header: "���̹��",  width:110,sortable: true,  dataIndex: 'itemName'},
	            {header: "Ԥ�ƻ���",  width:100,sortable: true, dataIndex: 'endAmount',editor:new Ext.form.NumberField(),align:'right',renderer:Util.rmbMoney,issum:true},
	            {header: "��ҵ���",  width:100,sortable: true, dataIndex: 'storeAmount',align:'right',renderer:Util.rmbMoney},
	            {header: "����������",  width:100,sortable: true, dataIndex: 'currentRate',align:'right',renderer:Util.rmbMoney},
	            {header: "�ܽ���",  width:100,sortable: true, dataIndex: 'weekPercent',align:'right'},
	            {header: "��ҵ������",  width:100,sortable: true, dataIndex: 'confirmAmount',align:'right',renderer:Util.rmbMoney,issum:true},
	            {header: "�ۼ�Ԥ�ƻ���",  width:100,sortable: true, dataIndex: 'accPreAmount',align:'right',renderer:Util.rmbMoney},
	            {header: "ʣ��ָ����",  width:100,sortable: true, dataIndex: 'surplusTargetAmount',align:'right',renderer:Util.rmbMoney,issum:true},
	            {header: "ʣ��Э����",  width:100,sortable: true, dataIndex: 'surplusHyearAmount',align:'right',renderer:Util.rmbMoney,issum:true}
	        ]}),
	        sm:sm,
	       	stripeRows: true,
	        height:Util.getH(0.4),
	        bbar:[
	        	{pressed:true,text:'ɾ��',handler:deletePreplanDetail},{xtype:'tbseparator'},
	        	{pressed:true,text:'����',handler:saveModified}
	        	]
	    });
	
	    return preplanDetailGrid;
	}
 </code></pre>
 * <b>Notes:</b> <br/>
 * - Ext.grid.ColumnModel��Ҫ��������:sumheader:true��ָʾ��Ҫʹ�úϼ���
 * - Ext.grid.ColumnModel��columns�����У���Ҫ����sumcaption:'�ϼ�'���ԣ���ָʾ��Ҫ��ʾ�ĺϼƱ�������
 * - Ext.grid.ColumnModel��columns�����У���Ҫ����issum:true���ԣ���ָʾ������Ҫ����ϼ�
 * - Ext.grid.ColumnModel��columns�����У���ѡ����sfn:function(sumvalue,colvalue,record){return ������;}��
 * ��ָʾ������Ҫʹ���û��Զ��庯�����м���sumvalue:���е�ǰ�ĺϼ�ֵ,colvalue��ǰ�������ֵ,record��ǰ��¼����
 * <br>
 * @constructor
 * @param {Object} config The config object
 */

Ext.grid.GridPanelEx = Ext.extend(Ext.grid.GridPanel, {
  getView : function() {
        if (!this.view) {
            this.view = new Ext.grid.GridViewEx(this.viewConfig);
        }
        return this.view;
    },
  initComponent : function() {
    if (!this.cm) {
    	
        this.cm = new Ext.grid.ColumnModelEx(this.columns);
        this.colModel = this.cm;
        delete this.columns;
    }
    if(!this.groupedHeaders)
    {
    	this.groupedHeaders = this.groupedHeaders;
    }
    if(!this.loadMask)
    {
    	this.loadMask = Global.getDefaultLoadMask();
    }
     this.getStore().on('load',this.onload,this);
   this.on('afteredit',this.onAfteredit,this);
    //�ڱ༭��ɺ���Ҫ���ºϼ���
    //this.on('afteredit',this.onAfteredit,this);
    Ext.grid.GridPanelEx.superclass.initComponent.call(this);
  },
  onload:function(ds,recs,opt){
	  if(ds)
	  {
	  	if(ds.getCount()==0){
	  		this.recalculation();
	  	}
	  	
	  }
  },
  getGroupedHeaders : function(){
        return this.groupedHeaders;
  },
  /**
   * �����޸ĺ󣬸��ºϼ�
   * @param {} event
   * grid - This grid
   * record - The record being edited
   * field - The field name being edited
   * value - The value being set
   * originalValue - The original value for the field, before the edit.
   * row - The grid row index
   * column - The grid column index
   */
  onAfteredit:function(event){
  	var grid = event.grid;
  	var cm = grid.getColumnModel();
  	if(false == cm.sumheader)
  	{
  		return;
  	}

  	var value = event.value;
  	var origValue = event.originalValue;
  	
  	cm.config[event.column].sumvalue -= origValue;
  	cm.config[event.column].sumvalue += value;
  	grid.getView().updateHeaders();
  },
  /**
   * ���¼���ϼ���
   */
  recalculation:function(){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	view.recalculation(this.getStore());
  },
   /**
   * ȡ��ָ����ID�����ϵĺϼ�����
   * @param {} dataIndex
   */
  getSumValue:function(dataIndex){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	return view.getSumValue(dataIndex);
  },
  /**
   * ���úϼ��еĺϼ�ֵ
   * @param {} dataIndex ��������ID
   * @param {} v �ϼ�ֵ
   */
  setSumValue:function(dataIndex,v){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	view.setSumValue(dataIndex,v);
  }
});


Ext.grid.EditorGridPanelEx = Ext.extend(Ext.grid.EditorGridPanel, {
	/**
     * @cfg {Number} ����clicksToEdit
     * The number of clicks on a cell required to display the cell's editor (defaults to 2)
     */
 	clicksToEdit: 1,
  getView : function() {
        if (!this.view) {
            this.view = new Ext.grid.GridViewEx(this.viewConfig);
        }
        return this.view;
    },
  initComponent : function() {
    if (!this.cm) {
    	
        this.cm = new Ext.grid.ColumnModelEx(this.columns);
        this.colModel = this.cm;
        delete this.columns;
    }
    if(!this.sm){
    	this.sm = new Ext.grid.RowSelectionModel();
    }
    if(!this.groupedHeaders)
    {
    	this.groupedHeaders = this.groupedHeaders;
    }
    if(!this.loadMask)
    {
    	this.loadMask = Global.getDefaultLoadMask();
    }
    this.getStore().on('load',this.onload,this);
    
    
    //�ڱ༭��ɺ���Ҫ���ºϼ���
    this.on('afteredit',this.onAfteredit,this);
    Ext.grid.EditorGridPanelEx.superclass.initComponent.call(this);
  },
  
   onload:function(ds,recs,opt){
	  if(ds.getCount()==0)
	  {
	  	this.recalculation(ds);
	  }
  },
  
  getGroupedHeaders : function(){
        return this.groupedHeaders;
  },
  /**
   * �����޸ĺ󣬸��ºϼ�
   * @param {} event
   * grid - This grid
   * record - The record being edited
   * field - The field name being edited
   * value - The value being set
   * originalValue - The original value for the field, before the edit.
   * row - The grid row index
   * column - The grid column index
   */
  onAfteredit:function(event){
  	var grid = event.grid;
  	var cm = grid.getColumnModel();
  	if(false == cm.sumheader)
  	{
  		return;
  	}

  	var value = event.value;
  	var origValue = event.originalValue;
  	
  	cm.config[event.column].sumvalue -= origValue;
  	cm.config[event.column].sumvalue += value;
  	grid.getView().updateHeaders();
  },
  /**
   * ���¼���ϼ���
   */
  recalculation:function(){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	view.recalculation(this.getStore());
  },
   /**
   * ȡ��ָ����ID�����ϵĺϼ�����
   * @param {} dataIndex
   */
  getSumValue:function(dataIndex){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	return view.getSumValue(dataIndex);
  },
  /**
   * ���úϼ��еĺϼ�ֵ
   * @param {} dataIndex ��������ID
   * @param {} v �ϼ�ֵ
   */
  setSumValue:function(dataIndex,v){
  	var cm = this.getColumnModel();
  	if(true != cm.sumheader)
  	{
  		return;
  	}  	
  	var view = this.getView();
  	view.setSumValue(dataIndex,v);
  }  
});

Ext.grid.GridViewEx = function(config) {
    Ext.apply(this, config);
    if (!this.templates) this.templates = {};
    
    
    //���Ӻϼ���ģ��
    if(!this.templates.header){
            this.templates.header = new Ext.Template(
                    '<table border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                    '<thead><tr class="x-grid3-hd-row">{cells}</tr><tr class="x-grid3-hd-row x-grid3-row-sum">{sumcells}</tr></thead>',
                    "</table>"
                    );
    }
    
    if(!this.templates.sumcells){
            this.templates.sumcells = new Ext.Template(
                    '<td class="x-grid3-hd x-grid3-cell x-grid3-td-{id}" style="{style}"><div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}">', '',
                    '{value}', '', '',
                    "</div></td>"
                    );
    }
    
    
    Ext.grid.GridViewEx.superclass.constructor.call(this);
};

Ext.extend(Ext.grid.GridViewEx, Ext.grid.GridView, {
	
	insertRows : function(ds, firstRow, lastRow, isUpdate){
        if(!isUpdate && firstRow === 0 && lastRow == ds.getCount()-1){
            this.refresh();
        }else{
            if(!isUpdate){
                this.fireEvent("beforerowsinserted", this, firstRow, lastRow);
            }
            var html = this.renderRows(firstRow, lastRow);
            var before = this.getRow(firstRow);
            if(before){
                Ext.DomHelper.insertHtml('beforeBegin', before, html);
            }else{
                Ext.DomHelper.insertHtml('beforeEnd', this.mainBody.dom, html);
            }
            if(!isUpdate){
                this.fireEvent("rowsinserted", this, firstRow, lastRow);
                this.processRows(firstRow);
            }
        }
    },
    onUpdate : function(ds, record){
        this.refreshRow(record);
    },
        // private
    refreshRow : function(record){
        var ds = this.ds, index;
        if(typeof record == 'number'){
            index = record;
            record = ds.getAt(index);
        }else{
            index = ds.indexOf(record);
        }
        var cls = [];
        this.insertRows(ds, index, index, true);
        this.getRow(index).rowIndex = index;
        this.onRemove(ds, record, index+1, true);
        this.fireEvent("rowupdated", this, index, record);
        /*
        if(true == record.dirty && true == this.cm.sumheader){
        	var cm = this.cm;
        	var colCount =  cm.getColumnCount();
        	for(var i=0; i<colCount;++i)
        	{
        		var c = cm.config[i];
        		if( true == c.issum && typeof record.modified[c.dataIndex] !== 'undefined'){
                    var value = record.get(c.dataIndex);
                    var origValue = record.modified[c.dataIndex];
                    if(origValue){
			  			cm.config[i].sumvalue -= origValue;
			  			cm.config[i].sumvalue += value;
                	}
        		}
        	}
		  	this.updateHeaders();
        }*/
    },
    onRemove : function(ds, record, index, isUpdate){
        if(isUpdate !== true){
            this.fireEvent("beforerowremoved", this, index, record);
        }
        this.removeRow(index);
        if(isUpdate !== true){
            this.processRows(index);
            this.applyEmptyText();
            this.fireEvent("rowremoved", this, index, record);
            
            //����ɾ����ʱ�ĺϼ�������
            this.processSumForRemoved(ds,record,index);
        }
    },
    
    //Template has changed and we need a few other pointers to keep track
    doRender : function(cs, rs, ds, startRow, colCount, stripe){
        var ts = this.templates, ct = ts.cell, rt = ts.row, last = colCount-1;
        var tstyle = 'width:'+this.getTotalWidth()+';';
        // buffers
        var buf = [], cb, c, p = {}, rp = {tstyle: tstyle}, r;
		//��������ڱ༭״̬�£�����Ҫ���ϼ��е�������0        
        if(false == rs[0].dirty && true ==(rs.length == ds.getCount())){
        	this.clearSumZero();
        }
        for(var j = 0, len = rs.length; j < len; j++){
            r = rs[j]; cb = [];
            var rowIndex = (j+startRow);
            for(var i = 0; i < colCount; i++){
                c = cs[i];
                p.id = c.id;
                p.css = i == 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
                p.attr = p.cellAttr = "";
                p.value = c.renderer(r.data[c.name], p, r, rowIndex, i, ds);
                p.style = c.style;
                if(p.value == undefined || p.value === "") p.value = "&#160;";
                if(r.dirty && typeof r.modified[c.name] !== 'undefined'){
                    p.css += ' x-grid3-dirty-cell';
                }
                cb[cb.length] = ct.apply(p);
                
                //�����������
                if(true == this.cm.sumheader)
        		{
	                if(true == this.cm.config[i].issum && false == r.dirty)
	                {//�����������Ҳ�Ϊ������
	                	if(!this.cm.config[i].sumvalue){
	                			this.cm.config[i].sumvalue = 0.0;
	                	}
	                	if(this.cm.config[i].sfn)
	                	{//���������ͺ����������
	                		this.cm.config[i].sumvalue = this.cm.config[i].sfn(this.cm.config[i].sumvalue,r.get(c.name),r);
	                	}
	                	else
	                	{//����ֱ���ۼ�
	                		if(r.data[c.name]){
	                			this.cm.config[i].sumvalue += r.data[c.name];
	                		}
	                	}
	                }
        		}
        		
            }
            var alt = [];
            if(stripe && ((rowIndex+1) % 2 == 0)){
                alt[0] = "x-grid3-row-alt";
            }
            if(r.dirty){
                alt[1] = " x-grid3-dirty-row";
            }
            rp.cols = colCount;
            if(this.getRowClass){
                alt[2] = this.getRowClass(r, rowIndex, rp, ds);
            }
            rp.alt = alt.join(" ");
            rp.cells = cb.join("");
            buf[buf.length] =  rt.apply(rp);
        }
        
        //����кϼ��У�����Ҫ������Ⱦ��ͷ
        if(true == this.cm.sumheader)
        {
        	this.updateHeaders();
        }
        return buf.join("");
    },
     /**
     * ����͵�����0
     */
    clearSumZero:function(){
    	this.hasSumColumn = false;
    	var cm = this.cm;
    	for(var i=0; i<cm.config.length;++i)
    	{
    		if(cm.config[i].issum)
    		{
    			this.hasSumColumn = true;
    			this.cm.config[i].sumvalue = 0.0;
    		}
    	}
    },
    /**
   * ȡ��ָ����ID�����ϵĺϼ�����
   * @param {} dataIndex
   */
	getSumValue:function(dataIndex){
		var colCount = this.cm.getColumnCount();
		
		for(var i=0; i<this.cm.config.length; ++i)
		{
			if(true == this.cm.config[i].issum)
			{
				if(this.cm.config[i].dataIndex == dataIndex)
				{
					return this.cm.config[i].sumvalue;
				}
				else if((typeof dataIndex == 'number') && dataIndex == i)
				{
					return this.cm.config[i].sumvalue; 	
				}
			}
		}
		return;
   },
   /**
   * ���úϼ��еĺϼ�ֵ
   * @param {} dataIndex ��������ID
   * @param {} v �ϼ�ֵ
   */
   setSumValue:function(dataIndex,v){
   		var colCount = this.cm.getColumnCount();
		
		for(var i=0; i<this.cm.config.length; ++i)
		{
			if(true == this.cm.config[i].issum)
			{
				if(this.cm.config[i].dataIndex == dataIndex)
				{
					this.cm.config[i].sumvalue = v;
					this.updateHeaders();
				}
				else if((typeof dataIndex == 'number') && dataIndex == i)
				{
					this.cm.config[i].sumvalue = v; 	
					this.updateHeaders();
				}
								
			}
		}
   },
   renderHeaders : function(){
        var cm = this.cm, ts = this.templates;
        var ct = ts.hcell;

        var cb = [], sb = [],lb = [], lsb = [], p = {};

        for(var i = 0, len = cm.getColumnCount(); i < len; i++){
            p.id = cm.getColumnId(i);
            p.value = cm.getColumnHeader(i) || "";
            p.style = this.getColumnStyle(i, true);
            p.tooltip = this.getColumnTooltip(i);
            if(cm.config[i].align == 'right'){
                p.istyle = 'padding-right:16px';
            } else {
                delete p.istyle;
            }
            cb[cb.length] = ct.apply(p);
        }
        
        //����ϼ��б�ͷ
        if(true == cm.sumheader)
        {
        	sb = this.buildSumHeaders(cm,ts);
        }
        
        return ts.header.apply({cells: cb.join(""), sumcells: sb.join(""),tstyle:'width:'+this.getTotalWidth()+';'});
        
        //return [ts.header.apply({cells: cb.join(""), sumcells: sb.join(""), tstyle:'width:'+(tw-lw)+';'}),
        //ts.header.apply({cells: lb.join(""), sumcells: lsb.join(""), tstyle:'width:'+(lw)+';'})];
        
        //return ts.header.apply({cells: cb.join(""), tstyle:'width:'+this.getTotalWidth()+';'});
    },
    
    
    /**
     * ���ɺϼƱ�ͷ
     * @param {} cm
     * @param {} ts
     * @param {} tw
     * @param {} lw
     * @return {}array
     */
    buildSumHeaders:function(cm,ts){
        var ct = ts.hcell;

        var sb = [], sp = {};

        for (var i = 0, len = cm.getColumnCount(); i < len; i++) {

            sp.id = 'sum' + cm.getColumnId(i);
            if(cm.config[i].sumcaption){
            	sp.value = cm.config[i].sumcaption;
            }else{
            	sp.value = "&nbsp;";
            }
            
            //cm.config[i].sumcaption = "&nbsp;";
            if (true == cm.config[i].issum && typeof cm.config[i].sumvalue == 'number'){
            	if(cm.config[i].renderer)
            	{
            		sp.value = cm.config[i].renderer(cm.config[i].sumvalue);
            	}else{
            		sp.value = cm.config[i].sumvalue;
            	}
            }
            
            
            sp.style = this.getColumnStyle(i, false);
            sp.tooltip = this.getColumnTooltip(i);
            
            if (cm.config[i].align == 'right') {
                sp.istyle = 'padding-right:16px';
            }

             sb[sb.length] = ct.apply(sp);

        }
        return sb;
    },
    /**
     * ����ɾ����ʱ�����ºϼ��е�����
     * @param {} ds
     * @param {} record
     * @param {} index
     */
    processSumForRemoved: function(ds,record,index){
    	var cm = this.cm;
    	if(true != cm.sumheader){
    		return;
    	}
    	
    	var cfg = cm.config;
    	for(var i=0; i<cfg.length; ++i){
    		if(true == cfg[i].issum && cfg[i].sumvalue){
    			var val = record.get(cfg[i].dataIndex);
    			if(val){
    				cfg[i].sumvalue -= val;
    			}
    		}
    	}
    	this.updateHeaders();
    },
	/**
	 * ���¼���ϼ���
	 */
	recalculation:function(ds){
	  	
	  	this.clearSumZero();
	  	if(true != this.hasSumColumn){
	  		return;
	  	}

	  	var colCount = this.cm.getColumnCount();
	  	var records = ds.getRange();
		for(var k=0; k<records.length; ++k){
			record = records[k];
			for(var i=0; i<this.cm.config.length; ++i){
				if(true == this.cm.config[i].issum){
					var val = record.get(this.cm.config[i].dataIndex);
					if(typeof val == 'number'){
						this.cm.config[i].sumvalue += val;
					}
				}
			}
		};
		
		this.updateHeaders();
	  	
	}
    
});


Ext.grid.ColumnModelEx = function(config) {
	//�Զ����ѡ��༭�ؼ�ʱ��ȫѡ����
	if(config && Ext.isArray(config.columns))
	{
		
		for(var i=0; i<config.columns.length; ++i)
		{	
			var c = config.columns[i];
			if(c.editor)
			{
				if(typeof(c.editor.selectOnFocus))
				{
					c.editor.selectOnFocus = true; 
				}
			}
		}
	}
    Ext.grid.ColumnModelEx.superclass.constructor.call(this, config);
    //alert('config.groupedHeaders ' + config.groupedHeaders);
	//this.groupedHeaders = config.groupedHeaders;
	
};

Ext.extend(Ext.grid.ColumnModelEx, Ext.grid.ColumnModel, {
    /**
     * Returns true if the specified column menu is disabled.
     * @param {Number} col The column index
     * @return {Boolean}
     */
    isMenuDisabled : function(col){
    	if(-1 == col){
    		return true;
    	}else{
        	return !!this.config[col].menuDisabled;
    	}
    }
});
Ext.reg('gridex', Ext.grid.GridPanelEx);
Ext.reg('editorgridex', Ext.grid.EditorGridPanelEx);


