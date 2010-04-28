/**
 * @include "excel_const.js"
 * @include "excel_app.js"
 */
Ext.namespace("Ext.icss");
/**
 * ���ݹ�����Ļ���
 * @author ��Ңΰ zhangyw@icss.com.cn
 * @param {} config
 */
Ext.icss.DataTool = function(config) {
	config = config || {};
	this.initialConfig = config;
	Ext.icss.DataTool.superclass.constructor.call(this);
};

Ext.extend(Ext.icss.DataTool,Ext.util.Observable, {

});

/**
 * ���ݹ����࣬������Ҫʵ�ֽ�������ݵ�����Microsoft Excel����
 * @param {} config
 */
Ext.icss.Data2ExcelTool = function(config) {
	config = config || {};
	this.excelApp = Ext.icss.ExcelApp;
	Ext.icss.Data2ExcelTool.superclass.constructor.call(this);
};
Ext.extend(Ext.icss.Data2ExcelTool,Ext.icss.DataTool,{
	/**
	 * ����������ݵ�EXCEL
	 * @param {} grid
	 * @param {} filename �ļ���
	 * @param {} caption ����
	 * @param {} condition ����
	 * @param {} footer ҳ����Ϣ����һ����������
	 * footer:[{row:0,col:3,value:'�����',handler:callback},{row:0,col:6,value:'������',handler:callback},{row:0,col:9,value:'�Ƶ�����',,handler:callback}]
	 * row:�к�,ֵΪ0��undifnedʱ�����ԣ��Զ���ҳ�����ڱ����֮��
	 * col:�к�,��ѡ
	 * value:��Ԫ���ֵ
	 * callback ����:
	 * function callback(value){}
	 */
	export2Excel:function(grid,filename,caption,condition,footer){
			this.excelApp.close();
			var worksheet = this.excelApp.getWorksheet(1);
			/*
			var worksheet = {};
			worksheet.Cells = {};
			*/
			var cells = this.fillWorksheet(worksheet,grid,filename,caption,condition,footer);
	},
	fillWorksheet : function(worksheet,grid,filename,caption,condition,footer) {
			var cells = worksheet.Cells;
			
			var cm = grid.getColumnModel();
			var ds = grid.getStore();
			
			var cols = cm. getColumnCount();
			var rows = ds.getCount();
			var stepRows = 0;
			var noExcelNum = 0;
			stepRows = this.buildTitle(grid,cells,caption,condition);
			rows += stepRows;
			var curRows = stepRows;
			curRows++;
			var mhrows = this.getMuiltHeaderRows(grid);
			//stepRows += mhrows;
			var noNum = this.buildHeader(grid,cells,curRows+mhrows,1);
			this.buildMuiltHeader(grid,worksheet,cells,curRows,1);
			curRows += mhrows;
			curRows++;
			this.buildSumRow(grid,cells,curRows,1);
			curRows++;
			this.buildBody(grid,worksheet,curRows,1);
			
			rows += (mhrows+ 2);
//			//ȡ������������
//			//�����ֺ�
			var range = this.excelApp.getRange(worksheet,cells(1, 1),cells(rows,cols-noNum));
			this.excelApp.setFontSize(range,9);
			//���ÿ���
			range = this.excelApp.getRange(worksheet,cells(stepRows+1, 1),cells(rows,cols-noNum));
			this.excelApp.setBordersLineStyle(range,Excel.XlBorderWeight.xlHairline);
//			//ȡ�ϼ�������
			range = this.excelApp.getRange(worksheet,cells(stepRows+mhrows+2, 1), cells(stepRows+mhrows+2,cols-noNum));
			this.excelApp.setFontBold(range,Excel.XlBoolean.True);
//			//�Զ��������и߿�
			range = this.excelApp.getRange(worksheet,cells(stepRows+1, 1),cells(rows,cols-noNum));
			this.excelApp.columnAutoFit(range);
//			
			if(footer)
			{
				this.buildFooter(grid,cells,footer,rows,cols-noNum);
				var range = this.excelApp.getRange(worksheet,cells(rows+1, 1),cells(rows+1,cols-noNum));
				this.excelApp.setFontSize(range,9);
			}
			
			return cells;
	},
	/**
	 * ���ɱ����ⲿ��
	 * @param {} caption ����
	 * @param {} condition ����
	 * @return stepRows ռ�õ�����
	 */
	buildTitle:function(grid,cells,caption,condition){
		var stepRows = 0;
		if(caption)
		{
			stepRows += 1;
			this.excelApp.setCellValue(cells,1,1,caption);
		}
		if(condition)
		{
			stepRows += 1;
			this.excelApp.setCellValue(cells,2,1,condition);
		}
		
		return stepRows;
	},
	/**
	 * ����grid��cm�����ɱ���ͷ
	 * @param {} grid
	 * @param {} cells excel cell����
	 * @param {} row ��ʼ��
	 * @param {} col ��ʼ��
	 */
	buildHeader:function(grid,cells,row,col){
		var cm = grid.getColumnModel();
		var cfg = null;
		var noShowList = [];
		for(var i=0; i<cm.config.length; ++i){
			
			cfg = cm.config[i];
			if(cfg.id == 'checker')
			{
				noShowList[noShowList.length] = i;
			}else if (cfg.noExcel){
				noShowList[noShowList.length] = i;
			}else if(cfg.id == 'numberer'){
				noShowList[noShowList.length] = i;
			}else{
				this.excelApp.setCellValue(cells,row,col+i-noShowList.length,cfg.header);
			}
		}
		return noShowList.length;
	},
	getMuiltHeaderRows:function(grid){
		var mhrows = 0;//���ͷ����
		var cm = grid.getColumnModel();
		if(cm.rows)
		{
			mhrows = cm.rows.length;
		}
		return mhrows;
	},
	/**
	 * ���ɶ��ͷ
	 * @param {} grid
	 * @param {} cells
	 * @param {} row
	 * @param {} col
	 * @return {int} mhrows ���ͷ����
	 */
	buildMuiltHeader:function(grid,worksheet,cells,row,col){
		var mhrows = this.getMuiltHeaderRows(grid);//���ͷ����
		if(0 == mhrows)
		{
			return 0;
		}
		var cm = grid.getColumnModel();
		var hc = null;
		//�кϲ�
		for(var i=0; i<mhrows; ++i)
		{
			var pcs = 0;
			for(var j=0; j<cm.rows[i].length;++j)
			{
				hc = cm.rows[i][j];
				pcs += hc.colspan;
				
				if(hc.header && hc.colspan > 0)
				{
					var r = row+i;
					var c = 0;
					if(hc.colspan>1)
					{
						c = col +  pcs - 1;
					}
					else
					{
						c = col +  pcs;
					}
					this.excelApp.setCellValue(cells, r , c ,hc.header);
					if(hc.colspan > 1)
					{
						var range = this.excelApp.getRange(worksheet,cells(r, c - hc.colspan + 1),cells(r, c));
						range.merge();
					}
				}
			}
		}
		//�кϲ�
		var i = 0;
		for(var j=0; j<cm.rows[i].length;++j)
		{
			hc = cm.rows[i][j];
			if(!hc.header)
			{
				var r = row + i;
				var c = col + j;
				var range = this.excelApp.getRange(worksheet,cells(r, c),cells(r+mhrows,c));
				range.merge();
			}
		}
		
		return mhrows;
	},
	/**
	 * ����GRID��store,��������
	 * @param {} grid
	 * @param {} cells
	 * @param {} row
	 * @param {} col
	 */
	buildBody : function(grid, worksheet, row, col) {
		var cells = worksheet.Cells;
		var cm = grid.getColumnModel();
		var ds = grid.getStore();
		var cfg = null; 
		ds.each(function(rec) {
			var noShowList = [];
			for (var i = 0; i < cm.config.length; ++i) {
				cfg = cm.config[i];
				if(cfg.id == 'checker')
				{
					noShowList[noShowList.length] = i;
				}else if (cfg.noExcel){
					noShowList[noShowList.length] = i;
				}else if(cfg.id == 'numberer'){
					noShowList[noShowList.length] = i;
				}else {
					var val = rec.get(cfg.dataIndex);
					if(cfg.renderer){
						val = cfg.renderer(val, 'excel');
					}
					if(val){
						if(rec.fields.item(cfg.dataIndex).type == 'string')
						{
							var range = this.excelApp.getRange(worksheet,cells(row, col + i - noShowList.length),cells(row, col + i - noShowList.length));
							this.excelApp.setNumberFormat(range,'@');
							this.excelApp.setCellValue(cells, row, col + i - noShowList.length, val );							
						}/*
						else if(rec.fields.item(cfg.dataIndex).type == 'float')
						{
							var range = this.excelApp.getRange(worksheet,cells(row, col + i),cells(row, col + i));
							this.excelApp.setNumberFormat(range,'0.00');
							this.excelApp.setCellValue(cells, row, col + i, val );							
						}*/
						else
						{
							this.excelApp.setCellValue(cells, row, col + i - noShowList.length, val );
						}
					}					
					
				}
			}
			row++;
		},this);
	},
	
	/**
	 * ���Ӻϼ���
	 * @param {} grid
	 * @param {} cells
	 * @param {} row
	 * @param {} col
	 */
	buildSumRow:function(grid,cells,row,col){		
		var cm = grid.getColumnModel();
		var cfg = null;
		var noShowList = [];
		for (var i = 0; i < cm.config.length; ++i) {
			cfg = cm.config[i];
			if(cfg.id == 'checker')
			{
				noShowList[noShowList.length] = i;
			}else if (cfg.noExcel){
				noShowList[noShowList.length] = i;
			}else if(cfg.id == 'numberer'){
				noShowList[noShowList.length] = i;
			}else if(cfg.sumcaption){
				this.excelApp.setCellValue(cells,row,col+i-noShowList.length,cfg.sumcaption);
			}else if(cfg.sumvalue || (0 == cfg.sumvalue) ){
				this.excelApp.setCellValue(cells,row,col+i-noShowList.length,cfg.sumvalue);
			}
		}
	},
	/**
	 * ����ҳ��
	 * @param {} grid
	 * @param {} cells
	 * @param {} footer
	 * @param {} rows
	 * @param {} cols
	 * @return boolean true or false
	 */
	buildFooter:function(grid,cells,footer,rows,cols){
		if(!Ext.isArray(footer))
		{
			return false;	
		}
		
		for(var i=0; i<footer.length; ++i)
		{
			var value = footer[i].value;
			var row = footer[i].row;
			var col = footer[i].col;
			
			if(row <= 0 || 'undefined' == row)
			{
				row = rows+1;
			}
			
			if('undefined' != value)
			{
				if(footer[i].handler && 'function' == typeof(footer[i].handler))
				{
					value = footer[i].hander(value);
				}
				
				this.excelApp.setCellValue(cells,row,col,value);
			}
		}
	}
});