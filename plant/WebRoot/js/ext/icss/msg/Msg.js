/**
 * ��ʾ������Ϣ��
 * @include "../global/Global.js"
 */
Ext.namespace("Ext.icss");

Ext.icss.Msg = function(){
    return {  
    	
        /**
         * ��ʾ�ȴ���Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.waiting('msg','title');
         * </code></pre>
         * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @param strTitle ����
         */
        waiting: function(strMsg,strTitle){
        	
        	var title = strTitle;
        	if(!title)
        	{
        		title = Global.INFOMATION_TITLE;
        	}
        	Ext.Msg.wait(strMsg,title,{hideParent:false});
        },
        /**
         * ��ʾ��Ϣ��Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.info('msg','title');
         * </code></pre>
         * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @param strTitle ����
         */
        info:function(strMsg,strTitle){
        	/*
        	var title = strTitle;
        	if(!title)
        	{
        		title = Global.INFOMATION_TITLE;
        	}
        	
        	Ext.Msg.show({
		 		title:title,
				msg: strMsg,
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			*/
        	alert(strMsg);
        	this.hide();
        },
        /**
         * ��ʾ������Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.error('msg','title');
         * </code></pre>
         * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @param strTitle ����
         */
        error:function(strMsg,strTitle){
        	/*
        	var title = strTitle;
        	if(!title)
        	{
        		title = Global.ERROR_TITLE;
        	}
        	Ext.Msg.show({
		 		title:title,
				msg: strMsg,
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.ERROR
			});*/
        	alert(strMsg);
        	this.hide();
        },
        /**
         * ��ʾ������Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.warning('msg','title');
         * </code></pre>
         * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @param strTitle ����
         */
        warning:function(strMsg,strTitle){
        	alert(strMsg);
        	this.hide();
        	/*
        	var title = strTitle;
        	if(!title)
        	{
        		title = Global.WARNING_TITLE;
        	}
        	Ext.Msg.show({
		 		title:title,
				msg: strMsg,
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.WARNING
			});*/
        },
        /**
         * ��ʾȷ����ʾ��Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.confirm('msg','title');
         * </code></pre>
    	 * @param fn �û�ѡ��ť��Ļص��������˷���������ʽΪ function fn(btn){},
    	 * 		btnΪ�û�ѡ��İ�ťidֵ��
    	 * 		- yes �û�ѡ����ȷ��
    	 * 		- no  �û�ѡ����ȡ��
    	 * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @param strTitle ����
    	 * @return boolean true �û�ѡ����ȷ�� false �û�ѡ����ȡ�� 
         */
        confirm:function(fn,strMsg,strTitle){
        	/*
        	var title = strTitle;
        	if(!title)
        	{
        		title = Global.INFOMATION_TITLE;
        	}
        	Ext.Msg.show({
		 		title:title,
				msg: strMsg,
				buttons: Ext.MessageBox.YESNO,
				icon: Ext.MessageBox.QUESTION,
				fn:fn
			});*/

        	var bOk = confirm(strMsg);
        	this.hide();
        	if(typeof(fn) == 'function')
        	{
        		var btn = (bOk ? 'yes' : 'no');
	        	fn(btn);
        	}
        	
        	return bOk;

        },
        /**
         * ����HTML DOM��confirm������ʾȷ����ʾ��Ϣ����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Msg.confirmDOM('msg');
         * </code></pre>
    	 * @param strMsg ��Ҫ��ʾ����Ϣ
    	 * @return true �û�ѡ����ȷ�� false �û�ѡ����ȡ��
         */
        confirmDOM:function(strMsg){
        	return confirm(strMsg);
        },
        /**
         * ���������봰��
         * @param title ����
         * @param msg ��ʾ��Ϣ
         * @param fn ������
         * @param value Ĭ��ֵ
         * @param multiline true ���� false ���У�Ĭ��Ϊ����
         * @param scope ���÷�Χ
         */
        prompt:function(title, msg, fn,value,multiline,scope){
        	Ext.Msg.prompt(title, msg,fn,scope,multiline,value);
        },        
        hide: function(){
        	Ext.Msg.hide();
        }    
    };
}
();
/**
 * �������
 * @type 
 */
Msg = Ext.icss.Msg;