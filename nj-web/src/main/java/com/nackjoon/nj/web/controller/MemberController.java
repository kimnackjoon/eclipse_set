package com.nackjoon.nj.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nackjoon.nj.core.controller.NjBaseController;
import com.nackjoon.nj.inmodule.service.MemberService;
import net.sf.json.JSONArray;

@Controller
public class MemberController extends NjBaseController{

	@Autowired
	MemberService memberService;
	
	
	@RequestMapping(value={"/member/login.do"})
	public String login(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> params) throws Exception {
		
		return "/member/login";
	}
	
	
	@RequestMapping(value={"/api/login.do"})
	@ResponseBody
	public HashMap<String, Object> loginProc(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> params) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		System.out.println("params : " + params);
		
		HashMap<String, Object> user = memberService.loginCheck(params);
		
		JSONArray json_user=null;
		
		if(user != null) {
			request.getSession().setAttribute("USER", user);
			json_user = JSONArray.fromObject(user);
		}
		
		result.put("user_info", json_user);
		
		return result;
	}
}
