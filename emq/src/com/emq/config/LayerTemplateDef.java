package com.emq.config;

/**
 * ͼ��ģ�嶨��
 * 
 * @author guqiong
 * @created 2009-10-23
 */
public class LayerTemplateDef {
	/**
	 * ģ��id����ӦdefineFine��ģ��ͼ������
	 */
	private String id;
	/**
	 * mapinfo�����ļ�
	 */
	private String defineFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefineFile() {
		return defineFile == null ? null : defineFile.trim();
	}

	public void setDefineFile(String defineFile) {
		this.defineFile = defineFile;
	}

}
