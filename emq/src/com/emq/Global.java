package com.emq;

import java.util.HashMap;
import java.util.Map;

public class Global {

	public static Map IS_ALL = new HashMap();

	public static Map HAS_SELECT = new HashMap();

	static {

		// �Ƿ���ȫ����������

		IS_ALL.put("code", "all");
		IS_ALL.put("text", "-ȫ��-");

		// �Ƿ������ѡ����������

		HAS_SELECT.put("code", "");
		HAS_SELECT.put("text", "-��ѡ��-");
	}

}