package com.emq;

import java.util.HashMap;
import java.util.Map;

public class Global {

	// ������ȫ��
	public static Map IS_ALL = new HashMap();

	// ������ȫ����ѡ��
	public static Map HAS_SELECT = new HashMap();

	public static String TREE_ROOT = "root";

	// �����+������+���վ �����ͱ�־
	public static String TREE_TYPE_1 = "1";

	// �����+ά������+��Ա �����ͱ�־
	public static String TREE_TYPE_2 = "2";

	// �õ继+���վ �����ͱ�־
	public static String TREE_TYPE_3 = "3";

	// ��·+���վ �����ͱ�־
	public static String TREE_TYPE_4 = "4";

	// �����+������+���վ ��������β�α�־
	public static String TREE_LEVEL_1_1 = "1_1";

	// �����+������+���վ ���������β�α�־
	public static String TREE_LEVEL_1_2 = "1_2";

	// �����+������+���վ ���վ���β�α�־(���Ӳ����չ,��δ�õ�)
	public static String TREE_LEVEL_1_3 = "1_3";

	// �����+ά������+��Ա ��������β�α�־
	public static String TREE_LEVEL_2_1 = "2_1";

	// �����+ά������+��Ա ά���������β�α�־
	public static String TREE_LEVEL_2_2 = "2_2";

	// �����+ά������+��Ա ��Ա���β�α�־(���Ӳ����չ,��δ�õ�)
	public static String TREE_LEVEL_2_3 = "2_3";

	static {

		// �Ƿ���ȫ����������

		IS_ALL.put("code", "all");
		IS_ALL.put("text", "-ȫ��-");

		// �Ƿ������ѡ����������

		HAS_SELECT.put("code", "");
		HAS_SELECT.put("text", "-��ѡ��-");
	}

}