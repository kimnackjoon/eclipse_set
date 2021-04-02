package com.nackjoon.nj.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookUtil {
	
	//쿠키 생성
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookName, String cookValue) {
		try {
			Cookie[] cookies = request.getCookies();
			if(cookies!=null) {
				for(int i=0; i<cookies.length; i++) {
					if(cookies[i].getName().equals(cookName)) {
						cookies[i].setMaxAge(0);
						response.addCookie(cookies[i]);
					}
				}
			}
			
			Cookie cookie = new Cookie(cookName, cookValue);
			cookie.setMaxAge(60*60*24*365); //쿠키 유지 기간(이부분이 없으면 브라우저 종료시 사라짐)
			cookie.setPath("/");			//모든 경로에서 접근 가능하도록
			//cookie.setDomain("test.com"); //이부분을 적용하면 서브 도메인간 공유 가능
			response.addCookie(cookie);		//쿠키 저장
		}catch(Exception e) {
			
		}
	}
	
	public static String getCookie(HttpServletRequest request, String cookName) {
		
		String reStr = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookName.equals(cookie.getName())) {
					reStr = cookie.getValue();
					break;
				}
			}
		}
		
		return reStr;
	}
	
	//전체 쿠키 삭제하기
	public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i <cookies.length; i++) {
				//쿠키의 유효시간을 0으로 설정하여 만료시킨다.
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				//응답 헤더에 추가한다.
				response.addCookie(cookies[i]);
			}
		}
	}
}
