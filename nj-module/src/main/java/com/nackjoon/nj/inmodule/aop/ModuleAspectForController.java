package com.nackjoon.nj.inmodule.aop;

import java.util.HashMap;
import java.util.Map;



import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import com.nackjoon.nj.core.aop.NjAspectForController;
import com.nackjoon.nj.core.controller.NjBaseController;
import com.nackjoon.nj.core.model.PageAuth;
import com.nackjoon.nj.core.util.FormatUtil;

public class ModuleAspectForController extends NjAspectForController{
	
	//@Autowired
	//private ManagerService managerService;
	
	//@Autowired
	//private CodeService codeService;

	//@Autowired
	//private MemberService memberService;
	
	/**
	 * @throws Exception
	 *
	 */
	@Override
	protected boolean beforeAction(ProceedingJoinPoint pjp, Map<String, Object> pjpMap) throws Exception {
		NjBaseController controller = (NjBaseController)pjpMap.get("controller");
		HttpServletRequest request = (HttpServletRequest)pjpMap.get("request");
		//HttpServletResponse response = (HttpServletResponse)pjpMap.get("response");
		// Model model = (Model)pjpMap.get("model");
		@SuppressWarnings("unchecked")
		HashMap<String, Object> params = (HashMap<String, Object>)pjpMap.get("params");
		@SuppressWarnings("unchecked")
		HashMap<String, Object> user = (HashMap<String, Object>)request.getSession().getAttribute("USER");
		/*
		if (CommonUtil.LOAD_TIME == 0 || (((System.currentTimeMillis() - CommonUtil.LOAD_TIME) / 1000 / 60)  > 9) 
				|| CommonUtil.COLLEGE_LIST == null || CommonUtil.CODE_LIST == null || CommonUtil.SETTING_LIST == null 
				|| "Y".equals(FormatUtil.toString(params.get("SYSTEM_SETTING_RELOAD")))) {
			CommonUtil.CODE_LIST = codeService.selectCodeList(request, params);
			CommonUtil.SETTING_LIST = managerService.selectSettingList(request, params);
			CommonUtil.COLLEGE_LIST = managerService.selectCustomCollageList(request,params);
			CommonUtil.LOAD_TIME = System.currentTimeMillis();
		}
		params.put("COLLEGE_LIST", CommonUtil.COLLEGE_LIST);
		CommonUtil.systemSettingUpdate(params, null);
		*/
		params.put("auth_no", user == null ? "" : FormatUtil.toString(user.get("USER_NO")));
		params.put("auth_type", user == null ? "" : FormatUtil.toString(user.get("USER_TYPE")));
		params.put("auth_sub_type", user == null ? "" : FormatUtil.toString(user.get("SUB_TYPE")));
		params.put("auth_lv", user == null ? "" : FormatUtil.toString(user.get("USER_LV")));

		if (user == null) {
			//로그인이 되어있지 않고, 권한이 필요한 페이지일 경우
			if (controller.getPageAuth().get("USER").getValue() > PageAuth.OPEN.getValue()
					|| controller.getPageAuth().get("ADMIN").getValue() > PageAuth.OPEN.getValue()) {
				
				pjpMap.put("returnObject", "member/login");
				return false;
			}
		} else {
			//로그인 되었지만 유저가 페이지에 접근권한이 없는경우
			if ((controller.getPageAuth().get("USER").getValue() == PageAuth.DENY.getValue() && "U".equals(user.get("USER_TYPE")))
					|| (controller.getPageAuth().get("ADMIN").getValue() == PageAuth.DENY.getValue() && "A".equals(user.get("USER_TYPE")))) {
				pjpMap.put("returnObject", "message/auth");
				return false;
			}
		}
		
		
		//권한관리
		/*
		HashMap<String, Object> authMap = new HashMap<String, Object>();
		authMap.put("auth_no", params.get("auth_no"));
		for (HashMap<String, Object> thisAuthMap : memberService.selectUserAuth(authMap)) {
			authMap.put(thisAuthMap.get("AUTH_TYPE").toString(), thisAuthMap.get("AUTH_LV"));
		}
		params.put("AUTH", authMap);
		*/
		
		
		return true;
	}

	/**
	 *
	 */
	@Override
	protected boolean afterAction(ProceedingJoinPoint pjp, Map<String, Object> pjpMap) throws Exception {
		// HttpServletRequest request = (HttpServletRequest)pjpMap.get("request");
		// HttpServletResponse response = (HttpServletResponse)pjpMap.get("response");
		// Model model = (Model)pjpMap.get("model");
		// Map<String, Object> params = (Map<String, Object>)pjpMap.get("params");

		return true;
	}

}
