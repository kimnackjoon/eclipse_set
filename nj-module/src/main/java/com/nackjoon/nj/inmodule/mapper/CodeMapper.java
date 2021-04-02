package com.nackjoon.nj.inmodule.mapper;

import java.util.HashMap;
import java.util.List;

import com.nackjoon.nj.core.annotation.Mapper;


@Mapper
public interface CodeMapper {

	public HashMap<String, Object> selectCode(HashMap<String, Object> params) throws Exception;
	public List<HashMap<String, Object>> selectCodeList(HashMap<String, Object> params) throws Exception;

}
