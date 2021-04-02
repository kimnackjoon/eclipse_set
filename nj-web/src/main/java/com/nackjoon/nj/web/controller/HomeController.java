package com.nackjoon.nj.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nackjoon.nj.core.controller.NjBaseController;
import com.nackjoon.nj.inmodule.service.CodeService;
import net.sf.json.JSONArray;

@Controller
public class HomeController extends NjBaseController{

	@Autowired
	private CodeService codeService;
	
	@RequestMapping(value={"/index.do","/home.do",""})
	public String home(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String,Object> params) throws Exception {
		
		model.addAttribute("codeList",JSONArray.fromObject(codeService.selectCodeList(params)));
		
		return "home";
	}
}
