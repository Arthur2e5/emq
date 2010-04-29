/**
 * ȫ����Ϣ����
 */
Ext.namespace("Ext.icss");

Ext.icss.Global = function(){
    return {  
	
		isSelectAll:false,
    	/**
    	 * ������֯����
    	 */
    	GROUP_CODE:'12530104',
    	/**
    	 * ��ͬ������������
    	 */
    	ALERTDAY:3,
    	/**
    	 * ���Ҿֽ���ϵͳ�����Ƽ��ű���
    	 */
    	PRODUCER_CODE:'12530101',
        /**
         * ���ؼ�����֯����
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Global.getGroupCode();
         * </code></pre>
         * @return GROUP_CODE
         */
        getGroupCode: function(){
        	return this.GROUP_CODE;
        },
         /**
         * ���ع��Ҿֽ���ϵͳ�����Ƽ��ű���
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Global.getProdurcerCode();
         * </code></pre>
         * @return GROUP_CODE
         */
        getProdurcerCode: function(){
        	return this.PRODUCER_CODE;
        },
        CONDITION_LEVEL : {
        			LEVEL1 : 1,// ȫ��
        			LEVEL2 : 2,// �г��ܲ�
					LEVEL3 : 3,// ʡ
					LEVEL4 : 4,// �г���
					LEVEL5 : 5,  // ��(��ҵ��˾)
					LEVEL6 : 6  // ����
		     },
		/**
		* ȡ����������ļ�����Ϣ
		*/
		getConditionLevel : function() {
			return this.CONDITION_LEVEL;
		},
        /**
         * ������ͬ
         */
        CONTRACT_1253:{code:'12530104',name:'������ͬ'},
        /**
         * ������ͬ
         */
        CONTRACT_9953:{code:'99530103',name:'������ͬ'},
        
        /**
         * �������Ĭ��ֵ�������
         */
        sendRegionDefault:1,
        sendAreaDefault:2,
        sendAreaDefaultName:'����ʡ�����б�����ׯ�������̳��ֿ�',
        trantypeDefault:1,
        trantypeDefaultName:'����',
        
        /**
         * ���غ�ͬ���ͱ����Ӧ������
         * <p>
         * Example code:
         * <pre><code>
         Ext.icss.Global.getContractTypeName('12530101');
         * </code></pre>
         * @return ��ͬ���ͱ����Ӧ������
         */
        getContractTypeName:function(code){
        	if(code == this.CONTRACT_1253.code)
        	{
        		return this.CONTRACT_1253.name;
        	}else if (code == this.CONTRACT_9953.code)
        	{
        		return this.CONTRACT_9953.name;
        	}
        },
        INFOMATION_TITLE:'��ʾ',
        ERROR_TITLE:'����',
        WARNING_TITLE:'����',
        /**
         * �������
         */
        IN_TYPE:'IN_TYPE',
        getInType:function(){
        	return this.IN_TYPE;
        },
        /**
         * ȫ������
         */
        ALL_KEY:-99,
        ALL_KEY_NAME:'ȫ��',
        /**
         * ��ʡ������Ϣ
         * @type 
         */
        LOCAL_PROVINCE:{code:'530000',name:'����ʡ'},
        getLocalProvince:function(){
        	return this.LOCAL_PROVINCE;
        },
        /**
         * Ĭ������
         */
        BATCHNUM:'0',
        getDefaultLoadMask:function(){
        	return {msg:'���ڼ������ݣ����Ժ򡭡�'};
        },
        getNoneState: function(){
        	return 99;
        },
        
        /**
         * �����������͵�ID
         */
        CAR_TRAN_TYPE:'1',
        /**
         * Ĭ����������Ϊ����
         */
        DEFAULT_TRAN_TYPE:'1',
        DEFAULT_TRAN_TYPE_NAME:'����',
        /**
         * ��������
         */
        TIMELIMIT:2,
        /**
         * �������Ԥ�ƻ������Ч����
         */
        getValidityPeriod:function(){
        	return 29;
        },
        /**
         * �������Ԥ�ƻ���С��Ч����
         */
         getValidityPeriodReverse:function(){
        	return -29;
        },
        /**
         * Ĭ��Ԥ�Ƶ�������
         */
        getDefaultArriveDays:function()
        {
        	return 7;
        },
        /**
         * �������Ԥ�ƻ�������������Ч����
         */
        getValidityGapPeriod:function(){
        	return 45;
        },
        getSystemDate:function(){
        	return new Date();
        },
        
        getCurrentDate:function(){
        	return new Date();
        },
	
		/**
		 * @author liuguo
		 * @description ˰��
		 */
		getTaxRate:function(){
			return 0.17;
		},
		/**
		 * @author liuguo
		 * @description ��ȡ���˲���Ϊ"����"�Ĳ���Id
		 */
		getTranDepIdWithSelfFetch:function(){
			return '04131';
		},
		
		/**
		 * @author liuguo
		 * @description ��ȡ���˲���Ϊ"����"�Ĳ�������
		 */
		getTranDepNameWithSelfFetch:function(){
			return '����';

		},
		
		/**
		 * @author liuguo
		 * @description ��ȡ���˹�˾Ϊ"����"�Ĺ�˾Id
		 */
		getTranCompIdWithSelfFetch:function(){
			return '0413';
		},
		
		/**
		 * @author liuguo
		 * @description ��ȡ���˹�˾Ϊ"����"�Ĺ�˾����
		 */
		getTranCompNameWithSelfFetch:function(){
			return '����';
		},
		
		/**
		 * @author ��Ңΰ
		 * @description ȡ�ó����̼ƻ��ļ۸�����
		 */
		getPriceTypeByExport:function(){
			return {priceType:'3',priceTypeName:'Э���'};
		}, 
		/**
        * ��ֵ˰��Ʊ����
        */
        InfoTypeCode:'1101001140',
        /**
         * ��˰��Ʊ��ʽ���� true ,falseΪ���Ի���
         */
        isTaxcard:true,
		/**
		 * @author liuguo
		 * @description �����Ƿ�ʵ�ڿ��������д�ӡ����֮��Ե�����������
		 * @return ������ڿ��������д�ӡ�򷵻�true�������������д�ӡ�򷵻�false
		 */
		isPrintInDeveloping:function(){
			//FIXME:����Ŀ��ʽ���ߵ�ʱ����Ҫ�޸�Ϊ����������Ӧ����Ϣ������Ϊfalse��
			//TODO:�йس��⡢����֪ͨ����ӡ�ĵط�����Ҫ�����������ơ�
			return false;
		},
		/**
    	* ���ȼ�����������
    	*/
	    VerifyPlanResult:{
	        	OK : 0,//ͨ��
				OK_AND_ERROR: -1,//���гɹ��ģ�Ҳ��ʧ�ܵ�
				TARGET_MONTH_UNDEFINED : 100,//��ָ�����û�ж���
				TARGET_MONTH_ERROR : 101,//����ָ�����
				TARGET_WEEK_UNDEFINED : 200,//��ָ�����û�ж���
				TARGET_WEEK_ERROR : 201,//����ָ�����
				XY_ERROR : 300//��Э��ִ��
	   },
        /**
         * �ͻ����������key����
         */
        clientPXKey:{
        	precontract:100
        },
        /**
         * ͨ���ͻ����������key����,ȡ�ö�Ӧ�ĸ߿�
         */
        getClientHW:function(key){
        	switch(key)
        	{
        	case this.clientPXKey.precontract:
        		return {width:Util.getW(0.33),height:Util.getH(0.37)};
        	}
        },
        getClientH:function(key){
        	return this.getClientHW(key).height;
        },
        getClientW:function(key){
        	return this.getClientHW(key).width;
        },
        isAllKey:function(key){
        	return (this.ALL_KEY == key);
        },
        /**
         * double or float precision
         */
        PRECISION : 0.000001,
        getTranRetMsg:function(code){
        	var msg = '';
        	if(code == '000')
        	{
        		msg =  '000	����ɹ�!';
        	}
        	else if(code == '100')
        	{
        		msg =  '100	�û�������!';
        	}
        	else if(code == '100')
        	{
        		msg =  '100	�û�������!';
        	}
        	else if(code == '101')
        	{
        		msg =  '101	�û���ͣ��!';
        	}
        	else if(code == '102')
        	{
        		msg =  '102	�ʻ������벻��ȷ!';
        	}
        	else if(code == '103')
        	{
        		msg =  '103	���ýӿ�������!';
        	}
        	else if(code == '104')
        	{
        		msg =  '104	Ӧ��ϵͳ����! ';
        	}
        	return msg;
        },
        
        /**
         * rendererOK״̬��ʽ
         */
        RENDERER_OK_STATE:"<span style='color:red;font-weight:900;font-size:130%';font-family:'Arial Black'>��</span>"
        
    };
}
();
/**
 * �������
 * @type 
 */
Global = Ext.icss.Global;
