<%@ page contentType="text/html;charset=gb2312"
import="com.jspsmart.upload.*,java.io.File" %><%
        request.setCharacterEncoding("gb2312");
		// �½�һ��SmartUpload����
	SmartUpload su = new SmartUpload();
		// ��ʼ��
	su.initialize(pageContext);
		// �趨contentDispositionΪnull�Խ�ֹ������Զ����ļ���
		//��֤������Ӻ��������ļ��������趨�������ص��ļ���չ��Ϊ
		//docʱ����������Զ���word��������չ��Ϊpdfʱ��
		//���������acrobat�򿪡�
	su.setContentDisposition(null);
	String name=request.getParameter("name");
	
	
        try {
          // �����ļ�
          //String name = su.toUtf8String(request.getParameter("name"));
          
          su.downloadFile(name);
        } catch (Exception e) {
        } finally {
          out.clear();
          out=pageContext.pushBody();
        }
        File wordFile = new File(name);
        wordFile.delete();
%>
