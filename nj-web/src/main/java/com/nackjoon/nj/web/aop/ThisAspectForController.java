package com.nackjoon.nj.web.aop;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.nackjoon.nj.inmodule.aop.ModuleAspectForController;

@Service
public class ThisAspectForController extends ModuleAspectForController{

	@Override
	protected boolean afterAction(ProceedingJoinPoint pjp, Map<String, Object> pjpMap) throws Exception {
		
		return super.afterAction(pjp, pjpMap);
	}
}
