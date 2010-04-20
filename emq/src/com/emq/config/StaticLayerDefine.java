package com.emq.config;

/**
 * ��̬��ʾͼ�㶨��
 * 
 * @author guqiong
 * @created 2009-10-23
 */
public class StaticLayerDefine implements Cloneable {
	
	/**
	 * map-def��Ψһ
	 */
	private String id;	

	/**
	 * ģ����������@see gis-map.xml
	 */
	private String template;
	/**
	 * ����
	 */
	private String tableName;
	/**
	 * x��������
	 */
	private String x;
	/**
	 * y��������
	 */
	private String y;
	/**
	 * ������
	 */
	private String[] pk;
	
	/**
	 * ��ע����
	 */
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * ��ȡģ����������@see gis-map.xml
	 */
	public String getTemplate() {
		return template == null ? null :template.trim();
	}

	/**
	 * ����ģ����������@see gis-map.xml
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * ��ȡ����
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * ���ñ���
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * ��ȡx��������
	 */
	public String getX() {
		return x;
	}

	/**
	 * ����x��������
	 */
	public void setX(String x) {
		this.x = x;
	}

	/**
	 * ��ȡy��������
	 */
	public String getY() {
		return y;
	}

	/**
	 * ����y��������
	 */
	public void setY(String y) {
		this.y = y;
	}

	public String[] getPk() {
		return pk;
	}

	public void setPk(String[] pk) {
		this.pk = pk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
