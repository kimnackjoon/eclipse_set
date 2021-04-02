<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈페이지</title>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>
<div id="home_vue_app">
<ul>
	<li v-for="item in items">
		<div>{{item.PRT_CD}}</div>
		<div>{{item.CD}}</div>
	</li>
</ul>
</div>
<script>
const HomeVueApp = {
  data() {
    return {
    		items : ${codeList}
    	}
  },
}
Vue.createApp(HomeVueApp).mount('#home_vue_app')
</script>
</body>
</html>