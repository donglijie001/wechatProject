<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//if(request.getParameter("mess")=="wrong") out.println("<script type=text/javascript>$('.popover-user').popover('show')</script>");
%>
<!DOCTYPE html>
<html lang="en" >
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="登录界面">
    <meta name="author" content="jly">

    <title>图书馆预订系统</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="scripts/jquery.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="scripts/angular-1.3.0.14/angular.min.js"></script>
    <!-- Custom styles for this template -->
    <link href="css/login.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/style.css" />
  </head>

  <body ng-app="myApp">

    <div class="container" ng-controller="LoginForm">

      <form class="form-signin" id="loginForm" > 
        <h2 class="form-signin-heading " id="usertitle" name="usertitle">管理员登录</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="text" id="username" name="username" ng-model="username" class="form-control" placeholder="用户名" required autofocus  >
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="userpwd" name="userpwd" ng-model="userpwd" class="form-control" placeholder="密码"   >
		
        <button class="btn btn-lg btn-primary btn-block " type="button"  ng-click="login()">登录</button>
        
      </form>


		
	
		
    </div> <!-- /container -->
  </body>
</html>
<script type="text/javascript">
angular.module("myApp", [])
.config(function($httpProvider){
            $httpProvider.defaults.headers.post = { 'Content-Type': 'application/x-www-form-urlencoded' }
})
.controller("LoginForm", function($scope,$http) {
	
	$scope.login=function()
	{
		if($scope.username==null){
			alert("请输入用户名");return;
		}
		if($scope.userpwd==null){
			alert("请输入密码");return;
		}
		$http({
            method: "post",
            data:$.param({"username":$scope.username,"pwd":$scope.userpwd}),
            url:"servlet/tadminservlet?method=loginadmin"
        }).success(function(data){
            //alert(data.msg);
            if(data.msg=="fail")
            	alert("用户或密码错误");
            else
            	window.location.href="adminmain.jsp?adminname="+$scope.username;
        });
	}
});
</script>