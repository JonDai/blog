//var ROOTPATH = "127.0.0.1:8080/blog/";
var currPage = 1;
var isCanLoad = true;  //后台是否还有未加载的数据
var articleIndex = 0;  
var lock = true;   //方法锁
$(function(){
	//加载最近更新所有blog
		LoadPage();
		/*
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
	*/
	//blog-post详情
	$(".page-content").on("click",".post-title",function(){
		var articleid = $(this).children("div").text();
		$(".content").hide();
		$.ajax({
			url:"article/"+articleid,
			type:"GET",
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
	
	/*无限滚动分页加载*/
	  $(window).scroll(function() {  
	      //当内容滚动到底部时加载新的内容  
		  if ($(this).scrollTop() + $(window).height() + 100 >= $(document).height() && $(this).scrollTop() > 100){
	      //当前要加载的页码  
			  if(lock){ LoadPage();}
	      }  
	  }); 
	  
	  /*导航栏 点击切换样式*/
	  $(".navbar-nav li").click(function(){
		  $(".navbar-nav .active").removeClass("active");
		  $(this).addClass("active");
	  });
	  
	  /*注册按钮*/
	  $("#add-btn").click(function(){
		 var name = $("#name-input").val(); 
		 var password = $("#password-input").val();
		 $.ajax({
			url:"adduser",
			type:"POST",
			dataType:"json",
			data:{username:name,password:password},
			seccess:function(data){
				alert("ok");
			},
		 });
	  });
})

/*分页加载数据，并加锁*/
function LoadPage(){
	lock = false;
	$(".la-pacman").show();
	setTimeout(function(){console.log("2")},5000);
	if(isCanLoad){
		$.ajax({
			url:"loadpage/"+currPage,
			type:"POST",
			async:false,
			dataType:"json",
			success:function(data){
				if(data.length < 1){
					isCanLoad=false;
				}else{
					$.each(data,function(k,v){
						$(".page-content").append("<div id=post-container"+articleIndex+" class='post-container'></div>");
						$("#post-container"+articleIndex+"").append("<h2 id=post-title"+articleIndex+" class='post-title'><a href='#'>"+v[1]+"</a></h2>");
						$("#post-container"+articleIndex+"").append("<div id=post-content"+articleIndex+" class='post-content'>"+v[2]+"</div>");
						//保存对象的ID
						$("#post-title"+articleIndex+"").append("<div class='input-id'>"+v[0]+"</div>");
						articleIndex++;
					});
				}
				currPage++;
			}
		});
	}
	$(".la-pacman").hide();
	lock = true;
}