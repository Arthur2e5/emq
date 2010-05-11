 /**
  * ����Ext��񹤾ߣ�֧�ֶ��ͷ
  */
 function GridEmq(){
	/**
	 * �������1:simple,2:double,3:third
	 */
	var tableType=1;
	
	/**
	 * ����1,����2...
	 */
	var head="";
	
	/**
	 * [[����1,�������;����2,�������],[����1,�������;����2,�������]]
	 */
	var moreHead=[];
	
	/**
	 * �п�100,200...
	 */
	var winth="";
	
	/**
	 * ��������ĳ��
	 */
	var lockColumn=0;
	
	/**
	 * ������ string,int,date,float
	 */
	var colunmType="";
	
	/**
	 * ��Ⱦ���� Ext.util.Format.dateRenderer('Y-m-d'),Util.rmbMoney
	 */
	var renderer="";
	
	/**
	 * ���뷽ʽ center,right.... ,
	 */
	var alignType="";
	
	/**
	 * �Ƿ���Ҫ�ϼ� false,true.... ,
	 */
	var issum="";
	
	/**
	 * ���ݴ������ݻ�ȡExtGrid�ĸ�ʽ����
	 * @return
	 */
	this.getExtGrid = function(){
		var renturnList = [];
		var headList = [];
		var windthList = [];
		var colunmTypeList = [];
		var alignTypeList = [];
		var issumTypeList = [];
		var rendererList = [];
		
		//��ͷ
		if(this.head!=null){
			headList = this.head.split(",");
		}
//		�п�
		if(this.winth!=null&&this.winth!=""){
			windthList = this.winth.split(",");
		}else{
			for(var i=0;i<headList.length;i++){
				windthList.push("80");
			}
		}
		if(windthList.length<headList.length){
			for(var i=windthList.size();i<headList.length;i++){
				windthList.push("80");
			}
		}
//		������
		if(this.colunmType!=null&&this.colunmType!=""){
			colunmTypeList = this.colunmType.split(",");
		}else{
			for(var i=0;i<headList.length;i++){
				colunmTypeList.push("string");
			}
		}
		if(colunmTypeList.length<headList.length){
			for(var i=colunmTypeList.length;i<headList.length;i++){
				colunmTypeList.push("string");
			}
		}
//		���뷽ʽ
		if(this.alignType!=null&&this.alignType!=""){
			alignTypeList = this.alignType.split(",");
		}else{
			for(var i=0;i<headList.length;i++){
				alignTypeList.push("center");
			}
		}
		if(alignTypeList.length<headList.length){
			for(var i=alignTypeList.length;i<headList.length;i++){
				alignTypeList.push("center");
			}
		}
		
//		�Ƿ�ϼ�
		if(this.issum!=null&&this.issum!=""){
			issumTypeList = this.issum.split(",");
		}else{
			for(var i=0;i<headList.length;i++){
				issumTypeList.push("false");
			}
		}
		if(issumTypeList.length<headList.length){
			for(var i=colunmTypeList.length;i<headList.length;i++){
				issumTypeList.push("false");
			}
		}
		
//		�Ƿ���Ҫ��Ⱦ
		if(this.renderer!=null&&this.renderer!=""){
			rendererList = this.renderer.split(",");
			for(var i=0;i<rendererList.length;i++){
				if(rendererList[i]!=""){
					rendererList[i]=",renderer:"+rendererList[i];
				}
			}
		}else{
			for(var i=0;i<headList.length;i++){
				rendererList.push("");
			}
		}
		if(rendererList.length<headList.length){
			for(var i=rendererList.length;i<headList.length;i++){
				rendererList.push("");
			}
		}
		
		//����
		var header =[];
		//Map dataMap = (HashMap)valueMap.get(0);
		var locked = true;
		for(var i=0;i<headList.length;i++){
			if(i>=this.lockColumn){
				locked = false;
			}
			if(this.issum==null||this.issum.length==0){
				header.push("{header:'"+headList[i]+"',dataIndex:'"+headList[i]+"',width:"+windthList[i]+",locked:"+locked+",sortable:true,align:'"+alignTypeList[i]+"'"+rendererList[i]+"}");
			}else{
				if(i==0){
					header.push("{header:'"+headList[i]+"',dataIndex:'"+headList[i]+"',width:"+windthList[i]+",locked:"+locked+",sortable:true,align:'"+alignTypeList[i]+"',sumcaption:'�ϼ�'"+rendererList[i]+"}");
				}else{
					header.push("{header:'"+headList[i]+"',dataIndex:'"+headList[i]+"',width:"+windthList[i]+",locked:"+locked+",sortable:true,align:'"+alignTypeList[i]+"',issum:"+issumTypeList[i]+rendererList[i]+"}");
				}
			}
		}
		
		//����
		var fields = [];
		for(var i=0;i<headList.length;i++){
			fields.push("{name:'"+headList[i]+"',type:'"+colunmTypeList[i]+"'}");
		}
		
		renturnList.push(header);
		renturnList.push(fields);
		
		if(typeof(this.tableType)=="undefined"){
			this.tableType = 1;
		}
		if(this.tableType!=1){
			var moreHeadList = [];
			for(var i=0;i<this.moreHead.length;i++){
				var moreHeadValue = this.moreHead[i];
				var tempHeadList = ["{}","{}"];
				for(var j=0;j<moreHeadValue.length;j++){
					var tempHeadValue = moreHeadValue[j];
					if(tempHeadValue!=""){
						var mHead = tempHeadValue.split(";");
						for(var x=0;x<mHead.length;x++){
							var tempValue = mHead[x];
							if(tempValue==""){
								tempHeadList.push("{}");
							}else{
								var t = tempValue.split(",");
								tempHeadList.push("{header:'"+t[0]+"',colspan:"+t[1]+",align:'center'}");
							}
						}
					}
				}
			    moreHeadList.push(tempHeadList);
		    }
		   renturnList.push(moreHeadList);
		}
		return renturnList;
	}

	
	
};
