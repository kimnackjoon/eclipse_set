package com.nackjoon.nj.inmodule.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nackjoon.nj.inmodule.mapper.MemberMapper;

@Service("memberService")
public class MemberService {

	@Autowired
	MemberMapper mapper;
	 
	public HashMap<String,Object> loginCheck(HashMap<String, Object> params) throws Exception {
		return mapper.loginCheck(params);
	}
	
}
