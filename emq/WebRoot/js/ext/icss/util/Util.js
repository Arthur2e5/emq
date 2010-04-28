/*
* @history	
*	2009-9-18 modified by guqiong:
*			add Ext.icss.Util.Format 
*				Ext.icss.Util.Format.ExtStrategy 
*				Ext.icss.Util.Format.ExcelStrategy 
* @history	
*	2010-4-16 modified by zhenggang:
*			add Ext.icss.Util.Browser
*/


// ��ʽ��������ʾ��ʽ��Ҳ�������ڶ������ض����ȡ�

/*
 * ���� alert(formatNumber(0,'')); alert(formatNumber(12432.21,'#,###'));
 * alert(formatNumber(12432.21,'#,###.000#'));
 * alert(formatNumber(12432,'#,###.00'));
 * alert(formatNumber('12432.415','#,###.0#'));
 */
Ext.namespace("Ext.icss");

Ext.icss.Util = function(){
    return {
    
        /**
         * ��ָ���ĸ�ʽ��ģʽ��ʽ������
         * <p>
         * Example code:
         * <pre><code>
         var ret = Ext.icss.Util.formatNumber(12432.21,'#,###');
         var ret = Ext.icss.Util.formatNumber(12432.21,'#,###.000#');
         var ret = Ext.icss.Util.formatNumber(12432,'#,###.00');
         var ret = Ext.icss.Util.formatNumber('12432.415','#,###.0#');
         * </code></pre>
         * @param {Number/String} number ��Ҫ��ʽ��������
         * @param {String} pattern ��ʽ��ģʽ
         * @return {String} ��ʽ����Ĵ�
         */
        formatNumber: function(number, pattern){
            var str = number.toString();
            var strInt;
            var strFloat;
            var formatInt;
            var formatFloat;
            if (/\./g.test(pattern)) {
                formatInt = pattern.split('.')[0];
                formatFloat = pattern.split('.')[1];
            }
            else {
                formatInt = pattern;
                formatFloat = null;
            }
            
            if (/\./g.test(str)) {
                if (formatFloat != null) {
                    var tempFloat = Math.round(parseFloat('0.' +
                    str.split('.')[1]) *
                    Math.pow(10, formatFloat.length)) /
                    Math.pow(10, formatFloat.length);
                    strInt = (Math.floor(number) + Math.floor(tempFloat)).toString();
                    strFloat = /\./g.test(tempFloat.toString()) ? tempFloat.toString().split('.')[1] : '0';
                }
                else {
                    strInt = Math.round(number).toString();
                    strFloat = '0';
                }
            }
            else {
                strInt = str;
                strFloat = '0';
            }
            if (formatInt != null) {
                var outputInt = '';
                var zero = formatInt.match(/0*$/)[0].length;
                var comma = null;
                if (/,/g.test(formatInt)) {
                    comma = formatInt.match(/,[^,]*/)[0].length - 1;
                }
                var newReg = new RegExp('(\\d{' + comma + '})', 'g');
                
                if (strInt.length < zero) {
                    outputInt = new Array(zero + 1).join('0') + strInt;
                    outputInt = outputInt.substr(outputInt.length - zero, zero)
                }
                else {
                    outputInt = strInt;
                }
                
                var outputInt = outputInt.substr(0, outputInt.length % comma) +
                outputInt.substring(outputInt.length % comma).replace(newReg, (comma != null ? ',' : '') + '$1')
                outputInt = outputInt.replace(/^,/, '');
                
                strInt = outputInt;
            }
            
            if (formatFloat != null) {
                var outputFloat = '';
                var zero = formatFloat.match(/^0*/)[0].length;
                
                if (strFloat.length < zero) {
                    outputFloat = strFloat + new Array(zero + 1).join('0');
                    //outputFloat        = outputFloat.substring(0,formatFloat.length);
                    var outputFloat1 = outputFloat.substring(0, zero);
                    var outputFloat2 = outputFloat.substring(zero, formatFloat.length);
                    outputFloat = outputFloat1 +
                    outputFloat2.replace(/0*$/, '');
                }
                else {
                    outputFloat = strFloat.substring(0, formatFloat.length);
                }
                
                strFloat = outputFloat;
            }
            else {
                if (pattern != '' || (pattern == '' && strFloat == '0')) {
                    strFloat = '';
                }
            }
            
            return strInt + (strFloat == '' ? '' : '.' + strFloat);
        },
        /**
         * �����ָ�ʽ��Ϊ����Ҹ�ʽ������2λ��Ч����
         * @param {Number/String} value The numeric value to format
         * @return {String} The formatted currency string
         */
        rmbMoney: function(v){
            v = (Math.round((v - 0) * 100)) / 100;
            v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v + "0" : v);
            v = String(v);
            var ps = v.split('.');
            var whole = ps[0];
            var sub = ps[1] ? '.' + ps[1] : '.00';
            var r = /(\d+)(\d{3})/;
            while (r.test(whole)) {
                whole = whole.replace(r, '$1' + ',' + '$2');
            }
            v = whole + sub;
            if (v.charAt(0) == '-') {
                return '-' + v.substr(1);
            }
            return v;
        },
        
        /**
         * �����ֽ�����������
         * @param v ����
         * @param dot ��Ч�����е�С����λ��
         * @return ���������Ľ��
         */
        roundNumber: function(v,dot){
            var v = String(Math.round(v * Math.pow(10,dot)) / Math.pow(10,dot));
            var ps = v.split('.');
            var i=0;
            var sub0 = ".";
            for(;i<dot;i++){
            	sub0 = sub0+'0';
            }
            var whole = ps[0];
            var sub = ps[1] ? '.' + ps[1] : sub0;
            var r = /(\d+)(\d{3})/;
            while (r.test(whole)) {
                whole = whole.replace(r, '$1' + ',' + '$2');
            }
            v = whole + sub;
            if (v.charAt(0) == '-') {
                return '-' + v.substr(1);
            }
            return v;
        },
            /**
         * ����������ĵ�һ��
         * @param ������id,field:�ֶ���
         */
     setCombox:function(id,field) {
	DWREngine.setAsync(false);
	var combox = Ext.getCmp(id);
	combox.init();
	var rec = combox.store.getAt(0);
	combox.setValue(rec.get(field));
	DWREngine.setAsync(true);
},
 /**
         * ����Ext.data.Record[]
         */
copyRecords:function(records)
{var newRecords=[];
    
	 
	 
	for(var i=0;i<records.length;i++)
	{
	newRecords[newRecords.length]=records[i].copy(['recId'+i]);
	
	}
	return newRecords;
},
         /**
         * �����ָ�ʽ��Ϊ����������Ҹ�ʽ������2λ��Ч����
         * @param {Number/String} value The numeric value to format
         * @return {String} The formatted currency string
         */
        
        rmbNegativeMoney: function(v){
           
            var  num=parseFloat(v);
              
           if(num&&num>0)
           {
           num=0-num;
            
           }
           return Ext.icss.Util.rmbMoney(num);;
        },
        
        screenHeight: function(){
            return screen.availHeight;
        },
        screenWidth: function(){
            return screen.availWidth;
        },
        getH: function(percentage){
            return screen.availHeight * percentage;
        },
        getW: function(percentage){
            return screen.availWidth * percentage;
        },
        /**
        * ɾ���������˵Ŀո�
        * */
        trim:function(str) { 
	   return str.replace(/(^\s*)|(\s*$)/g, "");
        },
        
	/**
	 * form�����У��������������ֵ
	 * <p>
	 * @param {} form from����
	 * @param {} rec ��¼
	 * @param {} idexp id���ʽ
	 * @param {} pattern id����Ҫ�滻�ı���
	 * <pre><code>
	 ʹ��ʾ��:
	 ������preplan_ ��ʼ�Ķ����ֵ,����{id}Ϊrec�е�id�����¶���������������ϴ�Լ����
	 {
				xtype : 'datefield',
				id : 'preplan_arriveDate',
				fieldLabel : 'Ԥ�Ƶ�������',
				name : 'arriveDate',
				format : 'Y-m-d',
				width : w
		}
		����:setFormData(frm,rec,'preplan_{id}',/{id}/);
		
		var bbank_idCombo = new Ext.form.ComboBox({
	    	id:'preplan_bbankIdCombo',
	        store: bbank_idStore,
	        fieldLabel:'�跽�˺�',
	        readOnly:true,
	        valueField:'bankId',
	        displayField:'bank',
	        typeAhead: true,
	        mode: 'remote',
	        triggerAction: 'all',
	        emptyText:'ѡ���跽�˺�',
	        selectOnFocus:true,
	        width:w
	    });
	    ����:setFormData(frm,rec,'preplan_{id}Combo',/{id}/);
	 * </code></pre>
	 */
	setFormData : function(form, rec, idexp, pattern) {

			var values = rec.data;
			if (Ext.isArray(values)) {
				for (var i = 0, len = values.length; i < len; i++) {
					var v = values[i];
					var f = null;
					var nid = null;
					if (idexp && pattern) {
						nid = idexp.replace(pattern, v.id);
					} else {
						nid = v.id;
					}
					
					f = form.findField(nid);
					if(!f){
						f = Ext.getCmp(nid);
					}
					
					if (f) {
						f.setValue(v.value);
						if (form.trackResetOnLoad) {
							f.originalValue = f.getValue();
						}
					}
				}
			} else {
				var field, id, nid;
				for (id in values) {

					if (typeof values[id] != 'function') {
						if (idexp && pattern) {
							nid = idexp.replace(pattern, id);
						} else {
							nid = id;
						}
						field = form.findField(nid);
						if(!field){
							field = Ext.getCmp(nid);
						}
						if (field) {
							field.setValue(values[id]);
							if (form.trackResetOnLoad) {
								field.originalValue = field.getValue();
							}
						}
					}
				}
			}
		},
		/**
		 * �ж��Ƿ�Ϊȫ��
		 * @param allKey ȫ���ı���
		 * @return true or false
		 */
		isAll:function(allKey){
			return (Global.ALL_KEY == allKey);
		},
		/**
		 * �����ִ�תΪnull
		 * @param {} str
		 * @return {}
		 */
		 str2NULL:function(str){
			if(typeof(str) == 'string' && str.trim() == '')
			{
				str = null;	
			}
			
			return str;
		}
    };
}
();


/**
 * ����Ĳ�������
 */
Ext.icss.Util.Array = function(){
    return {
    
        /**
         * ������a�У�ָ����pos֮ǰ������o����
         * @param a ����a
         * @param pos ����a��λ��
         * @param o Ҫ���뵽����a�Ķ���
         */
        insert: function(a, pos, o){
            if (a) {
                var newpos = pos;
                var tmpo = null;
                a[a.length] = null;
                for (var i = (a.length - 1); i > pos; --i) {
                    a[i] = a[i - 1];
                    
                }
                a[pos] = o;
            }
            return a;
        },
        
        /**
         *�Ѷ���o׷�ӵ�����a��β��
         *@param a ����
         *@param o Ҫ׷�ӵĶ���
         */
        append: function(a, o){
            if (a) {
                a[a.length] = o;
            }
            return a;
        },
        setAt:function(a,pos,o){
        	if(a){
        		a[pos] = o;
        	}
        }
        
    };
}
();


/**
 * ����Ĳ�������
 */
Ext.icss.Util.Grid = function(){
    return {
    
        /**
         * ������a�У�ָ����pos֮ǰ������o����
         * @param a ����a
         * @param pos ����a��λ��
         * @param o Ҫ���뵽����a�Ķ���
         */
        selectAll: function(grid, colid, e){
            var sm = grid.getSelectionModel();
            var cm = grid.getColumnModel();
            
            //�������в���checkbox����������
            if (cm.config[colid].id && cm.config[colid].id != "checker") {
                return true;
            }
            
            //����ȫѡ�뷴ѡ
            if (grid.getStore().getTotalCount() > sm.getCount()) {
                sm.suspendEvents();
                sm.selectAll();
                sm.resumeEvents();
				Global.isSelectAll = true;
            }
            else {
                sm.clearSelections();
				Global.isSelectAll = false;
            }
            
            //��ͷ��ѡ��״̬����
            cm.config[colid].onHdMouseDown(e, e.getTarget());
            
            return true;
        },
        
		/**
		 * ȡ�ñ���е����ѡ�е��м�¼����
		 * @param {grid} Ext.grid object
		 * @return {record}
		 */
		getSelectedRecord:function(grid){
			var sm = grid.getSelectionModel();
			return sm.getSelected();
		},
		/**
		 * ȡ�ñ���е����ѡ�е��м�¼����
		 * @param {grid} Ext.grid object
		 * @return {record}
		 */
		getSelectedRecords:function(grid){
			var sm = grid.getSelectionModel();
			return sm.getSelections();
		}
    };
}
();

/**
 * DWR���ߺ���
 */
Ext.icss.Util.DWR = function(){
    return {
    
       /**
        * ����DWR�����Ƿ�ʹ��ͬ�������첽
        * @param isSyn false ͬ�� true �첽
        * @note �˺�����óɶԳ���
        */
        setAsync: function(isSyn){
           if(DWREngine)
           {
           		DWREngine.setAsync(isSyn);
           }
        },
        /**
         * ��װDWR��������
         * @param errorHandler DWR����������������ʽΪ: function error(msg,e)
         */
        setErrorHandler: function(errorHandler){
        	DWREngine.setErrorHandler(errorHandler);
        }
    };
}
();
/**
 * ������صĹ��ߺ���
 */
Ext.icss.Util.Date = function(){
	return {
		/**
		 * ȡ��ָ���ؼ���ʱ�����
		 * @param {} datepicker date�ؼ�
		 * @param {} today ���ؼ�ֵ��Чʱ���Ƿ񷵻ص�ǰ���� true or false
		 * @return {Date}
		 */
		getDate:function(datepicker,today){
			
			var dt = null;
			if(datepicker)	{
				dt = datepicker.getValue();
			}
			
			if(!dt && (true == today))	{
				dt = new Date();
			} 
			return dt;
		},
		/**
		 * ȡ��ָ���ؼ���ʱ��,����Ҫ���ʽ��
		 * @param {} datepicker date�ؼ�
		 * @param fmt ����DateҪ��ĸ�ʽ���ִ�
		 * @return {yyyy-mm-dd} string
		 */
		getFormatDate:function(datepicker,fmt){
			if(!fmt){
				fmt = 'Y-m-d';
			}
			var querydate = null;
			if(datepicker)
			{
				querydate = datepicker.getValue();
			}
				
			if(querydate)
			{
				return querydate.format(fmt);
			}
			else
			{
				return (new Date()).format(fmt);
			}
		},		
		/**
		 * ����ָ����ʽ�ĵ�ǰ����
		 * @param fmt ����DateҪ��ĸ�ʽ���ִ�
		 * @return date string
		 */
		getCurrentDate:function(fmt){
			if(fmt){
				return (new Date()).format(fmt);
			}else{
				return new Date();
			}
		},
		/**
        * �Ƚ��������ڵĴ�С
        * @param d1 date1
        * @param d2 date 2
        * @param time true ��ʱ��Ƚ� ,false ����ʱ��Ƚ�
        * @return 0: d1 == d2, >0: d1 > d2 , <0: d1<d2, -2:error 
        */
        compare: function(d1,d2,time){
            if(!Ext.isDate(d1) || !Ext.isDate(d2)){
            	return -2;
            }            
            var n = 0;
            if(true == time){
            	n = (d1 - d2);
            }else{
            	n = (d1.clearTime() - d2.clearTime());
            }
			return n;
            
        },
        /**
         * �������d�Ƿ���[lhd .. rhd]������
         * @param d date 
         * @param lhd left handle date
         * @param rhd right handle date
         * @return true or false
         */
        between:function(d,lhd,rhd){
        	return d.between(lhd,rhd);
        },
        /**
         * ���d2�Ƿ���[d1 .. (d1 + days)]������
         * @param d1 date 1
         * @param d2 date 2
         * @param days �������
         * @return true or false
         */
        verifyPeriod:function(d1,d2,days){
        	var td1 = d1.add(Date.DAY,days);
        	return d2.between(d1,td1);
        },
        /**
         * ���date�Ƿ��Խ����ִ��
         * @param d date
         * @return true û��Խ false ��Խ
         */
        verifyHalfYear:function(d){
        	var sysdate = Global.getSystemDate();
        	var sysm = sysdate.getMonth();
        	var dm = d.getMonth();
        	var sy = sysdate.getYear();
        	var dy = d.getYear();
        	if(sysm < 6)
        	{//�������
        		return (sy == dy && (dm < 6 ));
        	}
        	else
        	{//������
        		return (sy == dy && (dm > 5 && (dm < 12)));
        	}
        },
         /**
         * ���ݼƻ��������¼��date�Ƿ��Խ����ִ��
         * @param d date
         * @return true û��Խ false ��Խ
         */
        verifyHalfYearBaseYear:function(d,baseDate){
        	var sysdate = baseDate;
        	var sysm = sysdate.getMonth();
        	var dm = d.getMonth();
        	var sy = sysdate.getYear();
        	var dy = d.getYear();
        	if(sysm < 6)
        	{//�������
        		return (sy == dy && (dm < 6 ));
        	}
        	else
        	{//������
        		return (sy == dy && (dm > 5 && (dm < 12)));
        	}
        },

        /**
         * �������ڵ��·ݣ��������°���ı�ʶ
         * @param date ����
         * @return 1 �ϰ��� 2 �°���
         */
        getHalfYear:function(date){
        	var m = date.format('n');
        	return (m < 7) ? 1 : 2;   
        },
        /**
         * ������������֮�������
         * @param sdt ��ʼ����
         * @param edt ��������
         * @return number 
         */
        getDayInterval:function(sdt,edt){
        	var days = Math.floor((edt - sdt)/(24*60*60*1000));
        	return days;
        },
        /**
         * ���ݴ���������ͳ�ʼ���ڣ���ȡ���������ں�����
         * @param sdt ��ʼ����
         * @param days ����
         * @return {}, weekinfo.date:���ڣ�weekinfo.weekIndex�����ڣ�weekinfo.weekCN����������
         */
        getWeekInterval:function(sdt,days){
        	var weeks = [];        	
        	var weekCN = ['������','����һ','���ڶ�','������','������','������','������'];
        	for(var i=0; i<days; ++i)
        	{
        		var weekinfo = {};
        		var date = new Date(sdt.add(Date.DAY,i));
        		weekinfo.date = date;
        		weekinfo.weekIndex = date.getDay();
        		weekinfo.weekCN = weekCN[weekinfo.weekIndex];
        		weeks[weeks.length] = weekinfo;
        	}        	
        	return weeks;
        },
        addDays:function(d,days)
        {
        	//var dtmp = new Date(d);
        	return d.add(Date.DAY,days);
        }
	};
}
();

/**
 * ������λת��
 */
Ext.icss.Util.ConvertUnit = function(){
    return {
    
        /**
         * ���̻�����λת��������֧->��
         * ��֧/5 = ��
         * ���̻�����λ����֧
         */
        BaseToBox: function(v){
            return v / 5.0;
        },
        /**
         * ���̻�����λת��������->��֧
         * ��֧ = �� * 5
         * ���̻�����λ����֧
         */
        BoxToBase: function(v){
            return v * 5.0;
        }
    };
}
();

/**
 * �������
 * @type 
 */
Util = Ext.icss.Util;

/**
*�������������ת��Ϊ�ַ���
**
*/
function date2string(d)
{if(d){var s_date = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
	return s_date;}
	else return d;
	
}
/*
**������С����λ����JS����
**
*/
function tofloat(f,dec)   
  {     
        if(dec<0)   return   "Error:dec<0!";     
        result   =   parseInt(f)+(dec==0?"":".");     
        f  -= parseInt(f);     
        if(f==0)     
            for(var   i=0;   i<dec;   i++)   result   +=   '0';     
        else   
        {     
            for(var   i=0;   i<dec;   i++)   f   *=   10;     
            result   +=   parseInt(Math.round(f));     
        }     
        return   result;     
  }   
  
/*
**ȡǰ��������������
**
*/
function getYearArray(){
	
	var rearray = new Array();
	var now = new Date();
	var year = now.getFullYear();
	for(i=4;i > 0;i--){
		var farray = new Array(2);
		farray[0]=year-i;
		farray[1]=year-i;
		rearray[rearray.length]=farray;
	}
	var yarray = new Array(2);
	yarray[0]=year;
	yarray[1]=year;
	rearray[rearray.length]=yarray;
	for(i=1;i<5;i++){
		var barray = new Array(2);
		barray[0]=year+i;
		barray[1]=year+i;
		rearray[rearray.length]=barray;
	}		
	return rearray;
} 
/*
**ȡ�·ݵ�����
**
*/
function getMonthArray(){
	var monthArray = [
        ['1', '1'],
        ['2', '2'],
        ['3', '3'],
        ['4', '4'],
        ['5', '5'],
        ['6', '6'],
        ['7', '7'],
        ['8', '8'],
        ['9', '9'],
        ['10', '10'],
        ['11', '11'],
        ['12', '12']
        ]; 
    return monthArray;
}
	
	//������¼ѡ�еļ�¼�������ύ�ľ��Ǵ�����
	var selection = [];
	
	//�ж���ѡ�������Ƿ���������
	function inArray(value){
		for(var i = 0; i < selection.length; i++) {
			if(selection[i] == value) return true;
		}
		return false;
	}
	//����������������
	function addValue(value){
		selection[selection.length] = value;
	}
	
	//��������ɾ������
	function removeValue(value){
		var focus = new Array();
		for(var i = 0; i < selection.length; i++) {
			if(selection[i] == value) {
				focus.push(i);
			}
		}
		if(focus.length > 0) {
			for(var k = focus.length-1; k >= 0; k--) selection.splice(focus[k], 1);
		}
	}
	
	//ȫѡ
	function selectAll(ds){
	   selection = [];//�����ȫѡ����ô���֮ǰҪ���������
	   for(var i=0;i<ds.getTotalCount();i++){
			selection[selection.length] = ds.getAt(i).get('replycount');
	   }
	}
	
	//����checkBox��ѡ���¼�
	function listenInCheck(value,w){
		if(w.checked){
			addValue(value);
		}else{
			removeValue(value);
		}
	}

/**
 * ��ʽ�����������
 */
Ext.icss.Util.Format = function(){
    return {
    	/*
    	* ��ʽ��״ֵ̬
    	* @param formatStrategy ָ�������ʽ 'excel'��null
    	* @param v ���ʽ����ֵ 0..2���
    	*/
        rendererBaseState: function(v,formatStrategy){
        	if(formatStrategy !=null && formatStrategy =='excel')
        		return 	Ext.icss.Util.Format.ExcelStrategy.rendererBaseState(v); 
        	else
        		return 	Ext.icss.Util.Format.ExtStrategy.rendererBaseState(v); 	       
		}
    };
}
();

/**
 * ��Ӧ��Excel����ĸ�ʽ��������
 */
Ext.icss.Util.Format.ExcelStrategy = function(){
    return {
    	/*
    	* ��ʽ��״ֵ̬
    	* @param v ���ʽ����ֵ 0..2���
    	*/
        rendererBaseState: function(v){ 
       		if(v==1 || v==2) 
				return "��";
			else 
				return "";			 
        }
    };
}
(); 

/**
 * ��Ӧ��ext js����ĸ�ʽ��������
 */
Ext.icss.Util.Format.ExtStrategy = function(){
    return {
    	/*
    	* ��ʽ��״ֵ̬
    	* @param v ���ʽ����ֵ 0..2���
    	*/
        rendererBaseState: function(v){
			if(v==1 || v==2)
				return "<span style='color:red;font-weight:900;font-size:130%';font-family:'Arial Black'>��</span>";
			else
				return "";						 
        },
        
        rendererState: function(v){
			if(v==1 || v==2)
				return "<span style='color:red;font-weight:900;font-size:130%';font-family:'Arial Black'>��</span>";
			else
				return "";						 
        }
    };
}
(); 
/**
 * �������ع���
 */
Ext.icss.Util.Browser = function(){
	var rslt = {};
	var ua = navigator.userAgent.toLowerCase();
	rslt.isOpera= ua.indexOf("opera")>-1; // ��Opera
	rslt.isIE7= !rslt.isOpera && ua.indexOf("msie 7")>-1; // IE7
	rslt.isIE8= !rslt.isOpera && ua.indexOf("msie 8")>-1; //IE8
	rslt.isIE6= !rslt.isOpera && !rslt.isIE7 && !rslt.isIE8 && ua.indexOf("msie")>-1; //IE6
	rslt.isFireFox= !rslt.isOpera && ua.indexOf("firefox")>-1 //FireFox
	ua = undefined;
	return rslt;
}
();



