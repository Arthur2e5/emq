package com.emq.ui.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.emq.exception.ErrorMsgConstants;
import com.emq.service.BaseService;
import com.emq.util.DateUtil;
import com.emq.util.TimeUtil;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 
 * �ļ�����Servlet
 * type:  passBook �ϴ��������������浽��Ӧ��Ա���ļ��У�������Excel�ļ������ݴ������ݿ�
 *        commonFile �ϴ������������浽upload\commonFile�ļ����£���������
 *        picture  �豸��ȡ�乩ҳ��չʾ�õ�ͼƬ�ļ���������imgs�ļ�����
 */
public class FileImportServlet extends HttpServlet {

	private static final long serialVersionUID = 8968112076191958384L;
	/**
	 * ���ڴ���ϴ��ļ�����ʱĿ¼
	 */
	public String UPLOAD_DIR = "\\upload\\";	
	/**
	 * ��������type �ļ�����
	 */
	public static final String TYPE = "type"; 
	/**
	 * ֧������ļ���С
	 */
	public static final int MAX_FILE_SIZE = 500 * 2048;	
	
	String importSuccessLog = ""; //���ڼ�¼����ɹ���Log
	String importFailLog = "";//���ڼ�¼����ʧ�ܵ�LOG
	
	private static Logger logger = Logger.getLogger(FileImportServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	} 
	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//�����������
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter(); 
		String type = req.getParameter("type"); 
		
		try { 
			String userName = "test";
			String personUnit = "testUnit";
			if(type.equalsIgnoreCase("passBook")){
				UPLOAD_DIR = "\\upload\\"+userName+"\\";
			}else if(type.equalsIgnoreCase("commonFile")){
				UPLOAD_DIR = "\\upload\\commonFile\\";
			}else if(type.equalsIgnoreCase("picture")){
				UPLOAD_DIR = "\\imgs\\";
			}else{
				UPLOAD_DIR = "\\upload\\";
			}
			List<String> files = smartUploadFile(req, res,userName,personUnit);
			boolean flag = true;
			if(type.equals("passBook")){
				for(String fileName: files){
					File f = new File(fileName);
					if (!f.exists())
						throw new Exception(ErrorMsgConstants.EMQ_UI_01);
					BaseService baseService = getBaseService();	
					int fileId = baseService.getImportMaxId();
					String name = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
					//����
					List sqlList = parseFile(fileName, type,fileId);
					//����	 
					flag = baseService.exeUpdateSqlByBach(sqlList);
				}
			}
			if(true == flag){//����ɹ�
				out.println("{success:true}");
				out.flush(); 
			}else{
				out.println("{success:false");
				out.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{success:false,message:'" + e.getMessage() + "'}");
			out.flush();
			logger.error(e);
		}
	}
	
	
	/**
	 * ��ȡbaseService
	 * @return
	 */
	private BaseService getBaseService() {
		ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		BaseService baseService = (BaseService)factory.getBean("baseService");
		return baseService; 
	}
	

	/**
	 * �����ж����Ƿ���ȷ
	 * @param sheet
	 * @return
	 * 	
	 * true:��ȷ
	 */
	private boolean isValidColumns(Sheet sheet,String type){ 
		//��鱣֤������һ��
		int rows = sheet.getRows(); 
		if(rows<1) {
			importFailLog += "���ļ���û������\n";
			return false;
		}
		return true;
	}
	
	/**
	 * ��ȡ��Ԫ���ڵ�ֵ
	 * @param cell
	 * @return
	 */
	private Object getCellValue(Cell cell){
		Object value = null;
		//������
		if (cell.getType().equals(CellType.DATE)) {  
			value = DateUtil.addZeroTime(((DateCell) cell).getDate());
		} 
		//��ֵ
		if (cell.getType().equals(CellType.NUMBER)) {
			value = ((NumberCell) cell).getValue();
		}
		 
		//�ַ���
		if(cell.getType().equals(CellType.LABEL)){
			//ȥ������Ŀո�
			value = cell.getContents().trim();
			//ȥ������
			value = value.toString().replace("\n",";");
			value = value == "" ? null : value;
			//ͳһת��GBK
			if(value!=null){
				try{
					byte[] bs = value.toString().getBytes();
					value =  new String(bs, "GBK");
				}catch(Exception e){
					e.printStackTrace();
				}
//				�滻�����ַ���
				value = value.toString().replace("?","��");
			}
		}
		//��
		if(cell.getType().equals(CellType.EMPTY)){
			value = "";
		}
		return value;
	}
	
	/**
	 * ����Excel2007
	 * @return
	 */
	private List<String> parseFileByXlsx(String fileName,int fileId) throws Exception{
		List sqlList = new ArrayList();
		XSSFWorkbook xwb = null;
		String tableColName = "insert into EMQ_PASS_BOOK(STATION_NAME,BELONG_UNIT_NAME," +
			"MEASURE_POINT_NAME,SUB_POINT_NAME,SUB_POINT_ADDRESS,MEASURE_POINT_TYPE,MEASURE_POINT_KIND," +
			"PASS_TYPE,E_CONFIGURE,E_MODEL,E_TYPE,E_MANUFACTURER,E_FACTORY_NUMBER,E_CONNECTION,E_TENSION," +
			"E_ELECTRICITY,E_RANK,E_MESSAGE_INTERFACE,E_MESSAGE_TYPE,E_COL_MODEL,E_COL_FACTORY,E_TV_RATE," +
			"E_TA_RATE,E_MULTIPLE,E_CHECK_TIME,E_CHECK_RESULT,T_MODEL,T_TYPE,T_FACTORY,T_DISCREPANCY," +
			"T_UNMBER,T_TENSION_FIRST,T_TENSION_SECOND,T_TENSION_SECOND_CHARGE,T_RANK,T_TV_KIND," +
			"T_IS_SPECIAL,T_IS_SPECIAL_TV,T_CHECK_TIME,T_CHECK_RESULT,EI_MODEL,EI_TYPE,EI_MANUFACTURER," +
			"EI_DISCREPANCY,EI_UNMBER,EI_RATED_TENSION_FIRST,EI_RATED_TENSION_SECOND," +
			"EI_RATED_TENSION_SECOND_CHARGE,EI_VERACITY_RANK,EI_TV_KIND,EI_IS_SPECIAL,EI_IS_SPECIAL_TV," +
			"EI_CHECK_TIME,EI_CHECK_RESULT,LT_MODEL,LT_TYPE,LT_MANUFACTURER,LT_UNMBER,GPS_MODEL,GPS_TYPE," +
			"GPS_MANUFACTURER,GPS_UNMBER,TV_AREA,TV_LENGTH,TA_LENGTH,TV_TIME,TV_RESULT,TV_CHARGE_TIME," +
			"TV_CHARGE_RESULT,TA_CHARGE_TIME,TA_CHARGE_RESULT,FILE_ID) values(";
		try{
			xwb = new XSSFWorkbook(fileName);
			XSSFSheet sheet = xwb.getSheetAt(0);
			//����ļ�
			int rows = sheet.getPhysicalNumberOfRows(); 
			if(rows<1) {
				importFailLog += "���ļ���û������\n";
				throw new Exception(ErrorMsgConstants.EMQ_UI_02);
			}
			XSSFRow row;   
			String cell;
			this.fillMergedCell(sheet);
			for (int i = sheet.getFirstRowNum()+2; i < sheet.getPhysicalNumberOfRows(); i++) {
				StringBuffer sql = new StringBuffer(tableColName);
			    row = sheet.getRow(i);
			    StringBuffer sqlValue = new StringBuffer();
			    for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
			        // ��ȡ��Ԫ�����ݣ�   
			        cell = row.getCell(j).toString();
			        if(cell==null||cell.equals("null")){
			        	cell = "";
			        }
			        sqlValue.append("'"+cell+"',");
			    }
			    if(sqlValue.length()>220){
					sql.append(sqlValue+"'"+fileId+"')");
					sqlList.add(sql.toString());
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(ErrorMsgConstants.EMQ_UI_01, e);
			throw new Exception(ErrorMsgConstants.EMQ_UI_01);
		}finally{
			File f = new File(fileName+"_");
			if(f.exists()){
				f.delete();
			}
		}
		return sqlList;
	}
	
	/**
	 * ����Excel2003
	 * @param fileName,type:el-���ܹؿ�̨��ģ��,mi-�������ؿ�̨��ģ��
	 * @return
	 */
	private List<String> parseFileByXls(String fileName,int fileId) throws Exception{
		List sqlList = new ArrayList();
		Workbook rw = jxl.Workbook.getWorkbook(new File(fileName)); 
		WritableWorkbook wb = null;
		String tableColName = "insert into EMQ_PASS_BOOK(STATION_NAME,BELONG_UNIT_NAME," +
			"MEASURE_POINT_NAME,SUB_POINT_NAME,SUB_POINT_ADDRESS,MEASURE_POINT_TYPE,MEASURE_POINT_KIND," +
			"PASS_TYPE,E_CONFIGURE,E_MODEL,E_TYPE,E_MANUFACTURER,E_FACTORY_NUMBER,E_CONNECTION,E_TENSION," +
			"E_ELECTRICITY,E_RANK,E_MESSAGE_INTERFACE,E_MESSAGE_TYPE,E_COL_MODEL,E_COL_FACTORY,E_TV_RATE," +
			"E_TA_RATE,E_MULTIPLE,E_CHECK_TIME,E_CHECK_RESULT,T_MODEL,T_TYPE,T_FACTORY,T_DISCREPANCY," +
			"T_UNMBER,T_TENSION_FIRST,T_TENSION_SECOND,T_TENSION_SECOND_CHARGE,T_RANK,T_TV_KIND," +
			"T_IS_SPECIAL,T_IS_SPECIAL_TV,T_CHECK_TIME,T_CHECK_RESULT,EI_MODEL,EI_TYPE,EI_MANUFACTURER," +
			"EI_DISCREPANCY,EI_UNMBER,EI_RATED_TENSION_FIRST,EI_RATED_TENSION_SECOND," +
			"EI_RATED_TENSION_SECOND_CHARGE,EI_VERACITY_RANK,EI_TV_KIND,EI_IS_SPECIAL,EI_IS_SPECIAL_TV," +
			"EI_CHECK_TIME,EI_CHECK_RESULT,LT_MODEL,LT_TYPE,LT_MANUFACTURER,LT_UNMBER,GPS_MODEL,GPS_TYPE," +
			"GPS_MANUFACTURER,GPS_UNMBER,TV_AREA,TV_LENGTH,TA_LENGTH,TV_TIME,TV_RESULT,TV_CHARGE_TIME," +
			"TV_CHARGE_RESULT,TA_CHARGE_TIME,TA_CHARGE_RESULT,FILE_ID) values(";
		try {
			//����
			wb = Workbook.createWorkbook(new File(fileName+"_"),rw);
//			 �õ��������еĵ�һ��������
			WritableSheet sheet = wb.getSheet(0);
//			����ļ�
			int rows = sheet.getRows(); 
			if(rows<1) {
				importFailLog += "���ļ���û������\n";
				throw new Exception(ErrorMsgConstants.EMQ_UI_02);
			}
			this.fillMergedCell(sheet);
//			ѭ������ÿ������(�ӵ����п�ʼ)
			for(int i=2;i<rows;i++){
				StringBuffer sql = new StringBuffer(tableColName);
				StringBuffer sqlValue = new StringBuffer();
				for(int j=0;j<sheet.getColumns();j++){
					sqlValue.append("'"+this.getCellValue(sheet.getCell(j, i))+"',");
				}
				if(sqlValue.length()>220){
					sql.append(sqlValue+"'"+fileId+"')");
					sqlList.add(sql.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ErrorMsgConstants.EMQ_UI_01, e);
			throw new Exception(ErrorMsgConstants.EMQ_UI_01);
		}finally{
			if(wb != null){
				wb.close();
			}
			if(rw != null){
				rw.close();
			}
			File f = new File(fileName+"_");
			if(f.exists()){
				f.delete();
			}
		}
		return sqlList;
	}
	
	/**
	 * ��ȡ�������ݿ������2003
	 * @param sheet
	 * @return
	 */
	private List getRowspanList(Sheet sheet){
		List rowspanList = new ArrayList();
		int maxRow = sheet.getRows();
		int rowspan = 2;
		Range[] ranges = sheet.getMergedCells();
		for(int j=0;j<maxRow;){
			int tempRowspan = 0;
			for(int i=0;i<ranges.length;i++){
				Range range = ranges[i];
				int topLeftRow = range.getTopLeft().getRow();
				if(topLeftRow==rowspan){
					int bottomRightRow = range.getBottomRight().getRow();
					int temp = bottomRightRow-topLeftRow;
					if(tempRowspan<temp){
						tempRowspan = temp;
					}
				}
			}
			rowspanList.add(rowspan);
			rowspan += tempRowspan+1;
			j=rowspan;
		}
		return rowspanList;
	}
	
	/**
	 * ����ϲ���Ԫ��ֵ2003
	 * @param sheet
	 * @return
	 */
	private void fillMergedCell(WritableSheet sheet){
		Range[] ranges = sheet.getMergedCells();
		for(int i=0;i<ranges.length;i++){
			Range range = ranges[i];
			int topLeftRow = range.getTopLeft().getRow();
			int topLeftCol = range.getTopLeft().getColumn();
			int bottomRightRow = range.getBottomRight().getRow();
			String firstCellValue = this.getCellValue(sheet.getCell(topLeftCol, topLeftRow)).toString();
			try{
				if(topLeftRow>=2){
					for(int x=topLeftRow+1;x<=bottomRightRow;x++){
						Cell cell = sheet.getCell(topLeftCol, x);
						if(cell.getType().equals(CellType.LABEL)){
							Label label = (Label)cell;
							label.setString(firstCellValue);
						}else if(cell.getType().equals(CellType.EMPTY)){
							Label temp = new Label(cell.getColumn(),cell.getRow(),firstCellValue);
					        sheet.addCell(temp);     
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡ�������ݿ������2007
	 * @param sheet
	 * @return
	 */
	private List getRowspanList(XSSFSheet sheet){
		List rowspanList = new ArrayList();
		int maxRow = sheet.getPhysicalNumberOfRows();
		int rowspan = 2;
		List ranges = new ArrayList();
		for(int i=0;i<sheet.getNumMergedRegions();i++){
			ranges.add(sheet.getMergedRegion(i));
		}
		for(int j=0;j<maxRow;){
			int tempRowspan = 0;
			for(int i=0;i<ranges.size();i++){
				CellRangeAddress range = (CellRangeAddress)ranges.get(i);
				int topLeftRow = range.getFirstRow();
				if(topLeftRow==rowspan){
					int bottomRightRow = range.getLastRow();
					int temp = bottomRightRow-topLeftRow;
					if(tempRowspan<temp){
						tempRowspan = temp;
					}
				}
			}
			rowspanList.add(rowspan);
			rowspan += tempRowspan+1;
			j=rowspan;
		}
		return rowspanList;
	}
	
	/**
	 * ����ϲ���Ԫ��ֵ2007
	 * @param sheet
	 * @return
	 */
	private void fillMergedCell(XSSFSheet sheet){
		List ranges = new ArrayList();
		for(int i=0;i<sheet.getNumMergedRegions();i++){
			ranges.add(sheet.getMergedRegion(i));
		}
		for(int i=0;i<ranges.size();i++){
			CellRangeAddress range = (CellRangeAddress)ranges.get(i);
			int topLeftRow = range.getFirstRow();
			int topLeftCol = range.getFirstColumn();
			int bottomRightRow = range.getLastRow();
			String firstCellValue = sheet.getRow(topLeftRow).getCell(topLeftCol).toString();
			if(topLeftRow>=2){
				for(int x=topLeftRow+1;x<=bottomRightRow;x++){
					XSSFCell cell = sheet.getRow(x).getCell(topLeftCol);
					if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
						cell.setCellValue(firstCellValue);
					}
				}
			}
		}
	}
	/**
	 * �����ļ���ȡSQL
	 * @param
	 * 		fileName �ļ�������·����
	 * @return
	 * 		List<EmFeeAirticket>
	 * @throws HYEMException
	 */
	private List<String> parseFile(String fileName, String type,int fileId) throws Exception{
		List sqlList = new ArrayList();
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		if(fileType.equalsIgnoreCase("xlsx")){
			sqlList = this.parseFileByXlsx(fileName,fileId);
		}else if(fileType.equalsIgnoreCase("xls")){
			sqlList = this.parseFileByXls(fileName,fileId);
		}
		return sqlList;
	}

	/**
	 * �����ϴ����ļ�������
	 * @param path
	 * 			����·��
	 * @param file
	 * 			�ϴ��ļ�
	 * @throws SmartUploadException
	 * @throws IOException
	 */
	private String saveFile(String path, com.jspsmart.upload.File file,String userName,String personUnit) throws SmartUploadException, IOException {
		//��ȡ�ļ��� 
		String fileName =  TimeUtil.dateToString(new Date(),"yyyyMMddHHmmss")+"."+file.getFileExt(); 
		String filePath = path+UPLOAD_DIR+fileName;
		File dir = new File(path + UPLOAD_DIR);
		if(!dir.exists())
			dir.mkdirs();
		File exsit = new File(filePath);
		if(exsit.exists()){
			exsit.delete(); 
		}
		//�����¼
		BaseService baseService = getBaseService();	
		String oldName = file.getFileName();
		String fileType = file.getFileExt();
		if(fileType.equals("xls")||fileType.equals("xlsx")){
			fileType = "Excel";
		}else if(fileType.equals("doc")||fileType.equals("docx")){
			fileType = "Word";
		}else if(fileType.equals("png")||fileType.equals("gif")||fileType.equals("jpg")){
			fileType = "Picture";
		}else{
			fileType = "Other";
		}
		file.saveAs(filePath, com.jspsmart.upload.File.SAVEAS_PHYSICAL); 
		baseService.exeUpdateSql("insert into EMQ_BOOK_IMPORT(NEW_FILE_NAME,OLD_FILE_NAME,FILE_PATH,IMPORT_PERSON,PERSON_UNIT,IMPORT_TIME,STATE,FILE_TYPE) values('"+fileName+"','"+oldName+"','"+filePath+"','"+userName+"','"+personUnit+"',getDate(),0,'"+fileType+"')");
		return filePath;
	}
	/**
	 * �ϴ��ļ�
	 * @param req
	 * @param res
	 * @return
	 * 		�ϴ��ļ���(��·��)�б�
	 * @throws HYEMException
	 */
	private List<String> smartUploadFile(HttpServletRequest req, HttpServletResponse res,String userName,String personUnit)
			throws  Exception {
		List<String> fileList = new ArrayList<String>(); 
		try{
			//�ϴ��ļ�
		  	SmartUpload supload = new SmartUpload();
			supload.initialize(this.getServletConfig(), req, res);
//			supload.setAllowedFilesList("xls,xlsx");
			supload.setMaxFileSize(MAX_FILE_SIZE);
			supload.upload();
			//�����ļ�
			com.jspsmart.upload.Files files = supload.getFiles();			
			int fcount = files.getCount(); 
			for (int i = 0; i < fcount; i++) {
				com.jspsmart.upload.File file = files.getFile(i);
				if (file.isMissing()) {
					continue;
				} 
				String path = req.getSession().getServletContext().getRealPath("");
				String fileName = saveFile(path, file,userName,personUnit); 
				fileList.add(fileName);
			} 
			 
		}catch(Exception e){
			e.printStackTrace();
			importFailLog +="�ϴ��ļ�ʧ�ܣ�\n";
			logger.error(ErrorMsgConstants.EMQ_UI_01, e);
			throw new Exception(ErrorMsgConstants.EMQ_UI_01);
		}
		return fileList;
	} 
}
