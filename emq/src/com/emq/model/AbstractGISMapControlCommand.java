package com.emq.model;

import com.emq.exception.GISException;
import com.emq.logger.Logger;

/**
 * ��ͼ�������������
 * <p>
 * ���жԵ�ͼ�Ĳ�������Ϊ���ʵ�ֲ�ͬ��������֧�ֲ�ͬ�ĵ�ͼ������
 * <p>
 * AbstractGISMapControlCommand�ṩ�˷��ʵ�ͼ������getGISMap����������AbstractGISMap����
 * ����ͨ������AbstractGISMap�ķ������ı��ͼ��״̬�����ԣ� ���������Ĳ�����
 * <p>
 * �ṩexecute�������ⲿ����,����Ӧʵ�ֳ��󷽷�doExecute���������Ĳ�����
 * 
 * @author guqiong
 * @created 2009-9-21
 */
public abstract class AbstractGISMapControlCommand {
	// �����ĵ�ͼ����
	protected AbstractGISMap map;

	private static Logger log = Logger
			.getLogger(AbstractGISMapControlCommand.class);

	protected AbstractGISMapControlCommand(AbstractGISMap map) {
		this.map = map;
	}

	/**
	 * ִ�жԵ�ͼ���������ز����ɹ���ĵ�ͼģ��
	 * 
	 * @return AbstractGISMap
	 */
	public AbstractGISMap execute() throws GISException {
		this.doExecute();
		map.refresh();
		return map;
	}

	protected AbstractGISMap getGISMap() {
		return map;
	}

	/**
	 * ����ִ�еĲ���
	 */
	protected abstract void doExecute() throws GISException;

}
