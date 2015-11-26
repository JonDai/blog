//var ROOTPATH = "127.0.0.1:8080/blog/";

$(function(){
	
	//加载最近更新blog
	$.ajax({
		type:"GET",
		url:"postlately",
		dataType:"json",
		success:function(data){
			$.each(data,function(k,v){
				$(".page-content").append("<div id=post-container"+k+" class='post-container'></div>");
				$("#post-container"+k+"").append("<h2 id=post-title"+k+" class='post-title'><a href='#'>"+v.title+"</a></h2>");
				$("#post-container"+k+"").append("<div id=post-content"+k+" class='post-content'>"+v.content+"</div>");
				//保存对象的ID
				$("#post-title"+k+"").append("<div class='input-id'>"+v.id+"</div>");
			})
		}
	});
	
	//blog-post详情
	$(".page-content").on("click",".post-title",function(){
		var articleid = $(this).children("div").text();
		$(".content").hide();
		$.ajax({
			type:"GET",
			url:"article/"+articleid,
			dataType:"json",
			success:function(data){
				$(".article-title").html(data.title);
				$(".panel-body").html(data.content);
			}
		})
	});
	
	//sgin in登陆后台
	$("#submit-btn").click(function(){
		var name = $("#name-input").val();
		var password = $("#password-input").val();
		if(!name){
			$("#name-group").addClass("has-error").focus();
			$("#name-input").focus();
		}else if(!password){
			$("#password-group").addClass("has-error");
			$("#password-input").focus();
		}else{
			var shaPassword = hex_sha1(password);
			$.ajax({
				url:"signin",
				type:"POST",
				dataType:"json",
				data:{username:name,password:shaPassword},
				success:function(data){
					if(data.faild){
						$("#name-group").addClass("has-error").focus();
						$("#password-group").addClass("has-error");
						$("#name-input").focus();
					}else{
						if(window.localStorage){   
					         localStorage.setItem("username",name);
					         localStorage.setItem("password",shaPassword);
							 location.href="admin/main.html";
					    }else{  
					         alert("浏览器还不支持 web storage 功能");  
					    }
					}
				}
			})
		}
	});
	
	/*Sgin in reset-btn 重置按钮*/
	$("#reset-btn").click(function(){
		$("#password-input").val("");
		$("#name-input").val("").focus();
	});
})
