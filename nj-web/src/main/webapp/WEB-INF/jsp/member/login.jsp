<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>
<div id="login_vue_app">
	<div>로그인</div>
	id : <input type="text" v-model="user.id"/>
	pw : <input type="password" v-model="user.password"/>
	<button @click="login($event)">로그인</button>
</div>
</body>
<script lang="ts">
Vue.createApp({
        data() {
            return {
                user : {
                    id : '',
                    password : ''
                }
            }
        },
        methods : {
            login(event){
            	alert('id : '+this.user.id+','+'pw : '+this.user.password);
            	
                if (this.user.id == '') {alert('아이디를 입력해주세요.');return;}
                if (this.user.password == '') {alert('비밀번호를 입력해주세요.');return;}
               	
              /*   axios.get('/api/login.do',{params:{
                    user_id : this.user.id,
                    user_pw : this.user.password
                }})
                  .then(function (response) {
                    console.log(response);
                  })
                  .catch(function (error) {
                    console.log(error);
                  }); */
                  
                axios.post('/api/login.do',null,{params:{
                    user_id : this.user.id,
                    user_pw : this.user.password
                }})
                  .then(function (response) {
                    console.log(response);
                  })
                  .catch(function (error) {
                    console.log(error);
                  });   
            }
        },
        created () {
        }
    }).mount("#login_vue_app")
</script>
</html>