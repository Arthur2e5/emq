/**
 * 
 * Microsoft Excel Ӧ�ã���������
 * @author ��Ңΰ zhangyw@icss.com.cn
 */
Ext.namespace("Ext.icss");
Ext.icss.ExcelApp = function() {

	return {
		excelApp :  null,
		EXCEL_APP : "Excel.Application",
		/**
		 * ����ExcelӦ�ó������
		 * @param visible{} true or false ��ʾ�����򴰿ڶ����Ƿ�ɼ�
		 * @param displayAlerts{} true or false ��������ں�����ʱ�������޾�����ʾ�;�����Ϣ�����ţ�
		 * �뽫����������Ϊ False������ÿ�γ������û�Ӧ�����Ϣʱ��Microsoft Excel ��ѡ��Ĭ��Ӧ��
		 * @return Application ����
		 */
		createApp : function(visible, displayAlerts) {

			if (!this.excelApp) {
				this.excelApp = new ActiveXObject(this.EXCEL_APP);
			}
			if (this.excelApp) {
				this.excelApp.Visible = visible;
				this.excelApp.DisplayAlerts = displayAlerts;
			}

			return this.excelApp;
		},

		/**
		 * �ر�ָ����Ӧ�ó������
		 * @param exApp ��Ҫ�رյ�EXCELӦ�ó������
		 */
		closeApp : function(exApp) {
			if (exApp) {
				exApp.Quit();
			}
		},

		/**
		 * ��ָ����Ӧ�ó�������У�����һ���µĹ�����
		 * @param exApp EXCELӦ�ó������
		 * @return Workbook ����
		 */
		addWorkbook : function(exApp) {
			if (!exApp) {
				return;
			}

			var workbook = exApp.Workbooks.Add();
			if (workbook) {
				workbook.Activate();
			}
			return workbook;
		},

		/**
		 * ��ָ����workbook�����У���������Ϊindex�Ĺ��������
		 * @param workbook Workbook ����
		 * @param index �����������
		 * @return Worksheet ����
		 */
		findWorksheet : function(workbook, index) {
			var worksheet = null;
			if (index) {
				worksheet = workbook.Worksheets(index);
			} else {
				worksheet = workbook.ActiveSheet;
			}

			return worksheet;
		},
		/**
		 * ����ָ���ĵ�Ԫ��ֵ
		 * @param cell ��Ԫ�����ȡWorksheet.Cells���� 
		 * @param row cell����
		 * @param col cell����
		 * @param value ֵ
		 */
		setCellValue:function(cell,row,col,value){
			cell(row,col).Value = value;
		},

		/**
		 * ����һ��ָ����ʼ���������Range����
		 * @param worksheet Worksheet����
		 * @param startCell eg. cell(1,1)
		 * @param endCell eg. cell(10,1)
		 * @return Range����
		 */
		getRange:function(worksheet,startCell,endCell){
			return worksheet.Range(startCell, endCell);
		},
		/**
		 * ��������߿��ߵ���ʽ
		 * @param range Ŀ������
		 * @param style Ҫ���õ���ʽ,����ȡֵ�ο�Ext.icss.Excel.XlBorderWeight
		 */
		setBordersLineStyle:function(range,style){
			range.Borders.LineStyle = style;
		},
		/**
		 * ��������range��������ʽ
		 * @param range
		 * @param xlboolean ȡֵ�ο�Ext.icss.Excel.XlBoolean
		 */
		setFontBold:function(range,xlboolean){
			range.Font.Bold = xlboolean;
		},
		/**
		 * �����С
		 */
		setFontSize:function(range,size)
		{
			range.Font.Size = size;
		},
		/**
		 * ���õ�Ԫ���ʽ
		 */
		setNumberFormat:function(range,fmt){
			range.NumberFormat = fmt;
		},
		/**
		 * �Զ����������п�
		 */
		columnAutoFit:function(range){
			range.Columns.AutoFit();
		},
		
		printPreview : function() {
			var visible = this.excelApp.Visible;
			if (this.excelApp.ActiveSheet) {

				if (false == visible) {
					this.excelApp.Visible = true;
				}
				this.excelApp.ActiveSheet.PrintPreview();

				this.excelApp.Visible = visible;
			}
		},

		printOut : function() {
			if (this.excelApp.ActiveSheet) {

				this.excelApp.ActiveSheet.PrintOut();
			}

		},

		close : function() {
			this.closeApp(this.excelApp);
			this.excelApp = null;
		},
		
		getWorksheet:function(index){
			if(!this.excelApp){
				this.createApp(true,false);
			}
			
			var workbook = null;
			if(!this.excelApp.ActiveWorkbook){
				workbook = this.addWorkbook(this.excelApp);
			}
			return this.findWorksheet(workbook,index);
			
		}
	};
}();

ExcelApp = Ext.icss.ExcelApp;

