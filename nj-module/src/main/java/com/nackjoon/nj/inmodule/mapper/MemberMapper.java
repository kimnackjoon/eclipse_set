package com.nackjoon.nj.inmodule.mapper;

import java.util.HashMap;

import com.nackjoon.nj.core.annotation.Mapper;

@Mapper
public interface MemberMapper {
	
	public HashMap<String,Object> loginCheck(HashMap<String,Object> params) throws Exception;
}
