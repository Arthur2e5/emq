package com.emq.util;

import com.emq.model.CommonCheckbox;

public class Global {

	public static CommonCheckbox IS_ALL = new CommonCheckbox();

	public static CommonCheckbox HAS_SELECT = new CommonCheckbox();

	static {

		// �Ƿ���ȫ����������

		IS_ALL.setCode("");
		IS_ALL.setText("--ȫ��--");

		// �Ƿ������ѡ����������

		HAS_SELECT.setCode(null);
		HAS_SELECT.setText("--��ѡ��--");
	}

}
