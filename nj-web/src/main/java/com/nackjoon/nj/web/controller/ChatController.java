package com.nackjoon.nj.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nackjoon.nj.core.controller.NjBaseController;


@Controller
public class ChatController extends NjBaseController{

	@RequestMapping(value = "/chatting.do", method = RequestMethod.GET)
    public String chat(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String,Object> params) throws Exception {
        
        return "chat/chat";
    }
}
