package com.nackjoon.nj.inmodule.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nackjoon.nj.core.model.CodeVO;

public class CommonUtil {
	public static List<HashMap<String, Object>> SETTING_LIST = null;
	public static List<HashMap<String, Object>> CODE_LIST = null;
	public static String filePath = "/WEB-INF/files";
	
	public static long LOAD_TIME = 0;
	
	
	public static void systemSettingUpdate(Map<String, Object> params, Map<String, Object> data) {
		HashMap<String, Object> settingMap = new HashMap<String, Object>();

		if (CommonUtil.SETTING_LIST != null) {
			for (int i = 0; i < CommonUtil.SETTING_LIST.size(); ++i) {
				HashMap<String, Object> map = CommonUtil.SETTING_LIST.get(i);
				
				settingMap.put(map.get("ST_NAME").toString(), map.get("ST_VAL"));
			}
		}

		List<HashMap<String, Object>> codeList = new ArrayList<HashMap<String, Object>>();
		
		if (CommonUtil.CODE_LIST != null) {
			for (int i = 0; i < CommonUtil.CODE_LIST.size(); ++i) {
				HashMap<String, Object> map = CommonUtil.CODE_LIST.get(i);
				
				codeList.add(map);
			}
		}
		
		CodeVO codeVo = new CodeVO();
		codeVo.setCodeList(codeList);
		
		params.put("SETTING", settingMap);
		params.put("CODE", codeVo);
	}
}
