package com.nackjoon.nj.core.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import jakarta.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nackjoon.nj.core.controller.NjBaseController;
import com.nackjoon.nj.core.util.FormatUtil;
import com.nackjoon.nj.core.util.PageUtil;

@Aspect
public abstract class NjAspectForController {

	@Resource(name = "config")
	private Properties config;
	
	@Value("#{config['SERVICE.ID']}")
	private String serviceId;
	
	@Value("#{config['FRAMEWORK.MSG.INFO']}")
	private String isInfoMessage;
	
	protected abstract boolean beforeAction(ProceedingJoinPoint pjp, Map<String,Object> pjpMap) throws Exception;
	
	protected abstract boolean afterAction(ProceedingJoinPoint pjp, Map<String,Object> pjpMap) throws Exception;
	
	// * - 모든 접근제어자 반환형을 가지는
	// *.. - 모든 패키지의 하위 경로(임의의 깊이)에 있는
	// *. - 모든 서브패키지 안(하나의 깊이)에 있는
	// controller.* - controller 패키지 안의 모든 클래스
	// ..* - 그 클래스 하위에 있는 모든 메서드
	// (..) 그 메서드는 임의의 파라미터를 가질 수 있음
	@Pointcut("execution(* *..*.controller.*..*(..))&& !@annotation(com.nackjoon.nj.core.annotation.NoLogging)")
	protected void pointCutForController() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Around("pointCutForController()")
	private final Object action(ProceedingJoinPoint pjp) throws Throwable {
		NjBaseController controller = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Model model = null;
		Map<String, Object> params = null;
		
		long startTime = System.currentTimeMillis();
		Object returnObject = null;
		
		StringBuffer report = new StringBuffer();
		StringBuffer reportByController = new StringBuffer();
		
		if(pjp.getTarget() instanceof NjBaseController) {
			controller = (NjBaseController)pjp.getTarget();
		}
		
		if(controller == null) {
			return pjp.proceed();
		}
		
		Object[] args = pjp.getArgs();
		
		for(Object arg : args) {
			if(arg instanceof HttpServletRequest) {
				request = (HttpServletRequest)arg;
			} else if(arg instanceof HttpServletResponse) {
				response = (HttpServletResponse)arg;
			} else if(arg instanceof Model) {
				model = (Model)arg;
			} else if(arg instanceof Map) {
				params = (Map<String, Object>)arg;
			}
		}
		
		if (request != null && params == null) {
			params = PageUtil.getParameterMap(request);
		}
		
		
		if(request != null && model != null) {
			model.addAttribute("paramsSearch", new HashMap<String, Object>(params));
			model.addAttribute("params", params);
			model.addAttribute("report", reportByController);
			
			boolean havePageNum = false, haveRowCount = false;
			
			for(String key : params.keySet()) {
				if (key.toLowerCase().matches("(?i)(page|no|num)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 1);
					}

					havePageNum = true;
				}

				if (key.toLowerCase().matches("(?i)(row|list)(count|size)")) {
					if (FormatUtil.toInt(params.get(key)) < 1) {
						params.put(key, 10);
					}

					haveRowCount = true;
				}
			}
			
			if(!havePageNum) {
				params.put("page", 1);
			}
			
			if(!haveRowCount) {
				params.put("rowcount", 10);
			}
		}
		
		reportInit(report, pjp, request, params);
		
		HashMap<String,Object> pjpMap = new HashMap<String, Object>();
		pjpMap.put("controller", controller);
		pjpMap.put("request", request);
		pjpMap.put("response", response);
		pjpMap.put("model", model);
		pjpMap.put("params", params);
		pjpMap.put("returnObject", returnObject);
		pjpMap.put("HTMLMessage", null);
		
		long readyTime = System.currentTimeMillis();
		report.append("started time = "+ startTime + "\n");
		report.append("elapsed time(ready) = " + (readyTime - startTime) + "ms\n");
		
		if (!beforeAction(pjp, pjpMap)) {
			returnObject = getDefaultReturnObject(pjp, (pjpMap.get("returnObject") == null ? "message/before" : pjpMap.get("returnObject").toString()));

			reportFinish(controller, report, reportByController, System.currentTimeMillis() - startTime, params, returnObject, model);

			return returnObject;
		}
		
		long beforeTime = System.currentTimeMillis();
		report.append("elapsed time(before) = " + (beforeTime - readyTime) + "ms\n");

		params.put("httpRequest", request);
		Object result = pjp.proceed();
		params.remove("httpRequest");

		long proceedTime = System.currentTimeMillis();
		report.append("elapsed time(proceed) = " + (proceedTime - beforeTime) + "ms\n");
		
		if (!afterAction(pjp, pjpMap)) {
			returnObject = getDefaultReturnObject(pjp, (pjpMap.get("returnObject") == null ? "message/after" : pjpMap.get("returnObject").toString()));

			reportFinish(controller, report, reportByController, System.currentTimeMillis() - startTime, params, returnObject, model);

			return returnObject;
		}
		
		long afterTime = System.currentTimeMillis();
		report.append("elapsed time(after) = " + (afterTime - proceedTime) + "ms\n");

		if (model != null) {
			model.addAttribute("paging", PageUtil.getPaging(params));

			if (params.get("paging") != null) {
				String[] paging = params.get("paging").toString().split(",");

				for (int i = 0; i < paging.length; ++i) {
					String[] pagingFix = paging[i].split("[*]");

					String pagingPrefix = pagingFix[0];
					String pagingSuffix = "";

					if (pagingFix.length > 1) {
						pagingSuffix = pagingFix[1];
					}

					model.addAttribute(pagingPrefix + "paging" + pagingSuffix, PageUtil.getPaging(params, pagingPrefix, pagingSuffix));
				}
			}
		}
		
		returnObject = (result == null ? getDefaultReturnObject(pjp, "message/nopage") : result);		
		
		if (model != null) {
			Map<String, Object> modelMap = model.asMap();
			modelMap.remove("report");
			
			for (String key : modelMap.keySet()) {
				if (key.equals("paramsSearch")) {
					for (String paramsKey : params.keySet()) {
						if (modelMap.get(paramsKey) instanceof String) {
							modelMap.put(paramsKey, FormatUtil.encodeXSS(modelMap.get(paramsKey).toString()));
						}
					}
				//} else if (!key.equals("params")) {
				} else {
					modelMap.put(key, FormatUtil.encodeObjectForXSS(modelMap.get(key), key));
				}
			}
		}
		
		long finishTime = System.currentTimeMillis();
		reportFinish(controller, report, reportByController, finishTime - startTime, params, returnObject, model);

		return result == null ? returnObject : result;
	}
	
	protected final Object getDefaultReturnObject(ProceedingJoinPoint pjp, Object returnObject) {
		if (pjp.getSignature().toString().startsWith("String ")) {
			return returnObject.toString();
		} else {
			return returnObject;
		}
	}
	
	private final void reportInit(StringBuffer report, ProceedingJoinPoint pjp, HttpServletRequest request, Map<String, Object> params) {
		if (isInfoMessage != null && "FALSE".equals(isInfoMessage.toUpperCase())) {
			return;
		}

		report.append("\n################################################################################\n");
		report.append("signature = " + pjp.getSignature() + "\n");
		//report.append("uri = " + request.getRequestURI() + "\n");
		report.append("paramsSearch = " + params + "\n");
	}
	
	private final void reportFinish(NjBaseController controller, StringBuffer report, StringBuffer reportByController, long total, Map<String, Object> params, Object returnObject, Model model) {
		if (isInfoMessage != null && "FALSE".equals(isInfoMessage.toUpperCase())) {
			return;
		}

		if (report == null) {
			return;
		}

		if (model != null) {
			model.asMap().remove("report");
		}

		report.append("elapsed time(total) = " + total + "ms\n");
		report.append("params = " + params + "\n");
		report.append("returnObject = " + returnObject + "\n");

		if (reportByController != null && !"".equals(reportByController.toString())) {
			report.append("\n[Message from Controller]\n");
			report.append(reportByController);
		}

		report.append("################################################################################");

		controller.loggerInfo(report.toString());
	}
	
	
}
