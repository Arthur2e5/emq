//���ܣ���������������Ӧ�¼�
//���ߣ�liuyt
//�ⲿ������state: small=������ͼ��pan=�϶���big=����ѡ��circle=Բ��ѡ��region=�����ѡ��
//        mapId�����õ�ͼID��points:����ε��������飬eventstate��ʱ��״̬���¼���ʼ=eventbegin��ʱ�����=eventend��
//        oldx�����x���꣬oldy�����y���꣬newx���յ�x���꣬newy���յ�y���� startx����ʼx���꣬starty����ʼy���꣬tempState����ʱ״̬
//        mapserviceurl������ͼ��Ⱦ·����mapboundserviceurl ����ͼ��Ⱦ·��
//create��2009-10-20


//����ѡ���ͼˢ�� 
function mapbigger(){
 startx = oldx-document.all.mapframe.offsetLeft;
 starty = oldy-document.all.mapframe.offsetTop;
	document.all.imgmap.src=mapserviceurl+"?action=select&selecttype=rectangle&left="+startx+"&top="+starty+"&right="+newx+"&down="+newy+"&refresh=" + Math.random();
	selectByType('rectangle','');
	startx = 0;
	starty = 0;
	newx = 0;
	newy = 0;
}
//�����ѡ���ͼˢ��
function regioner(){
 var pointArray=points.split(";");
 var pcount = pointArray.length;
 document.getElementById("mydiv").innerHTML="";
 var temp_p = points.substring(0,points.length-1);
 var p=temp_p.split(";");
 var t_point="";
 for(var i=0;i<p.length;i++){
 	var x = p[i].substring(0,p[i].indexOf(","));
 	var y = p[i].substring(p[i].indexOf(",")+1,p[i].length);
 	x = x-document.all.mapframe.offsetLeft;
 	y = y-document.all.mapframe.offsetTop;
 	t_point += x+","+y+";";
 }
 points = t_point.substring(0,t_point.length-1);
 if(p.length>2){
 	document.all.imgmap.src=mapserviceurl+"?action=select&selecttype=region&pcount="+pcount+"&points="+points+"&refresh=" + Math.random();
	 selectByType('region','');
 }else{
 	points="";
 }
}

//Բ��ѡ���ͼˢ��
function circle(){
 var x_ = Math.abs(window.event.clientX-oldx);
 var y_ = Math.abs(window.event.clientY-oldy);
 var radius = Math.sqrt(Math.pow(x_,2)+Math.pow(y_,2));
 document.getElementById("mydiv").innerHTML="";
 oldx = oldx-document.all.mapframe.offsetLeft;
 oldy = oldy-document.all.mapframe.offsetTop;
	document.all.imgmap.src=mapserviceurl+"?action=select&selecttype=circle&x="+oldx+"&y="+oldy+"&radius="+radius+"&refresh=" + Math.random();
	parent.rightFrame.queryByRadius(mapId,oldx,oldy,radius);
}

//����ͼͬ��
function mapbounder(){
	document.getElementById('boundmap').src=mapboundserviceurl + "&refresh=" + Math.random();
}
