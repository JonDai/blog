//var ROOTPATH = "127.0.0.1:8080/blog/";
var localparam = {username:localStorage.getItem("username"), password:localStorage.getItem("password")};
var currPage = 1;
var isCanLoad = true;  //后台是否还有未加载的数据
var articleIndex = 0;  //article id
var lock = true;   //方法锁
var DREAM = 1;
var LIFE = 2;
var TECH = 3;
var LOGON_STORGE = false;   //默认未使用本地存储密码
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
	//左侧分页导航
	$("#dream-list").empty();
	$.ajax({
		url:"twoclassify",
		type:"POST",
		data:{pclassify:DREAM},
		dataType:"json",
		success:function(data){
			var sidelist = $("#dream-list");
			$.each(data,function(k,v){
				sidelist.append('<a href="#" value="'+v.id+'"><span class="glyphicon glyphicon-send"> '+v.name+'-'+v.count+'</span></a>');
			});
		}
	});
	$("#life-list").empty();
	$.ajax({
		url:"twoclassify",
		type:"POST",
		data:{pclassify:LIFE},
		dataType:"json",
		success:function(data){
			var sidelist = $("#life-list");
			$.each(data,function(k,v){
				sidelist.append('<a href="#" value="'+v.id+'"><span class="glyphicon glyphicon-heart"> '+v.name+'-'+v.count+'</span></a>');
			});
		}
	});

	$("#tech-list").empty();
	$.ajax({
		url:"twoclassify",
		type:"POST",
		data:{pclassify:TECH},
		dataType:"json",
		success:function(data){
			var sidelist = $("#tech-list");
			$.each(data,function(k,v){
				sidelist.append('<a href="#" value="'+v.id+'"><span class="glyphicon glyphicon-tags"> '+v.name+'</span><span>-'+v.count+'</span></a>');
			});
		}
	});
	//blog-post详情
	$(".page-content").on("click",".post-title",function(){
		$("#article").css("height",$(window).height()).show();
		var articleid = $(this).children("div").text();
		$(".blog-content").hide();
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
/** 登陆、注册modal框*/
	//导航栏signin点击触发事件
	$("#signin").click(function(){
		$('#myModal').modal('toggle')
		if(localparam.username){
			$("#name-input").val(localparam.username);
		}
		if(localparam.password){
			$("#password-input").val(localparam.password);
			LOCAL_STORGE = true;
		}
	});
	/*登陆、注册切换事件*/
	$('#m-title-login').click(function(){
		$('.logon-nav .active').removeClass('active');
		$(this).addClass('active');
		$('#modal-newaccount').hide();
		$('#modal-login').show();
	});
	$('#m-title-new').click(function(){
		$('.logon-nav .active').removeClass('active');
		$(this).addClass('active');
		$('#modal-login').hide();
		$('#modal-newaccount').show();
	});

	/*注册按钮*/
	$("#add-btn").click(function(){
		if(checkinput()){
			var name = $("#n-name-input").val(); 
			var password = $("#n-password-input").val();
			var email = $("#n-password-input").val();
			$.ajax({
				url:"adduser",
				type:"POST",
				dataType:"json",
				data:{username:name,password:password,email:email,type:1},
				success:function(data){
					if(data.faild){
						swal("Ooop!",data.faild,"error");
					}else{
						swal("OK!","注册成功!","success");
						localStorage.setItem("username",data.name);
						localStorage.setItem("password",data.shaPassword);
						localStorage.setItem('type',data.type);	
					}
				},
			});
		}
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
			var shaPassword
			if(!LOCAL_STORGE){
				shaPassword = hex_sha1(password);
			}else{
				shaPassword = password;
			}
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
							alert('支持');
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

	/*导航栏 点击切换样式*/
	$(".navbar-nav li").click(function(){
		$(".navbar-nav .active").removeClass("active");
		$(this).addClass("active");
	});

	/*左侧列表按钮隐藏事件*/
	$("#open-dream").click(function(){
		$("#dream-list").show();
		$("#life-list").hide(); 
		$("#tech-list").hide(); 
	});
	$("#open-life").click(function(){
		$("#life-list").show(); 
		$("#dream-list").hide();
		$("#tech-list").hide(); 
	});
	$("#open-tech").click(function(){
		$("#tech-list").show();
		$("#dream-list").hide();
		$("#life-list").hide();
	});

	/*左侧列表导航按钮加载article列表*/
	$("#menu-list").on("click","a",function(){
		$(".blog-content").show();
		$(".page-content").css("margin-top","75px");
		$(".big-pic").hide();
		$("#article").hide();
		var classifyid = $(this).attr("value");
		var classifyname = $(this).children("span:first").html();
		$.ajax({
			url:"articlesbyclass",
			type:"POST",
			data:{classifyid:classifyid},
			dataType:'json',
			success:function(data){
				if(data.faild){sweetAlert("Oops...", data.faild, "error");}
				else{
					$(".page-content").empty();
					$.each(data,function(k,v){
						$(".page-content").append("<div id=post-container"+k+" class='post-container'></div>");
						$("#post-container"+k+"").append("<h2 id=post-title"+k+" class='post-title'><a href='#'>"+v.title+"</a></h2>");
						$("#post-container"+k+"").append("<div id=post-content"+k+" class='post-content'>"+v.content+"</div>");
						$("#post-container"+k+"").append("<div id=post-footer"+k+" class='post-footer'>代鹏伟/"+classifyname+"/"+v.createtime+"/阅读:"+v.readcount+"</div>");
						//保存对象的ID
						$("#post-title"+k+"").append("<div class='input-id'>"+v.id+"</div>");
					});
				}
				//关闭左侧导航
				$("#close-button").click();
			}
		});
	});

	/*无限滚动分页加载*/
	$(".content-wrap").scroll(function(event){
		//当内容滚动到底部时加载新的内容  
		// if ($(this).scrollTop() + $(window).height() + 100 >= $(document).height() && $(this).scrollTop() >20){
		if(isCanLoad){
			var $this =$(this),
			viewH =$(this).height(),//可见高度
			contentH =$(this).get(0).scrollHeight,//内容高度
			scrollTop =$(this).scrollTop();//滚动高度
			//if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
			if(scrollTop/(contentH -viewH)>=0.95){ //到达底部100px时,加载新内容
				if(lock){ LoadPage();}
			}  
		}
	}); 

})
/*分页加载数据，并加锁*/
function LoadPage(){
	lock = false;
	//$(".la-pacman").show();
	//setTimeout(function(){console.log("2")},2000);
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
						$("#post-container"+articleIndex+"").append("<h2 id=post-title"+articleIndex+" class='post-title'><a>"+v.title+"</a></h2>");
						$("#post-container"+articleIndex+"").append("<div id=post-content"+articleIndex+" class='post-content'>"+v.content+"</div>");
						$("#post-container"+articleIndex+"").append("<div id=post-footer"+articleIndex+" class='post-footer'>代鹏伟/"+v.name+"/"+v.createtime+"/阅读:"+v.readcount+"</div>");
						//保存对象的ID
						$("#post-title"+articleIndex+"").append("<div class='input-id'>"+v.id+"</div>");
						articleIndex++;
					});
				}
				currPage++;
			}
		});
	}
	//$(".la-pacman").hide();
	lock = true;
}
/*登陆modal检查数据*/
function checkinput(){
	var flag = true;
	var name = $("#n-name-input").val();
	var password = $("#n-password-input").val();
	var email = $('#n-email-input').val();
	if(!name){
		$("#n-name-group").addClass("has-error").focus();
		$("#n-name-input").focus();
		flag = false;
	}else if(!email){
		$("#n-email-group").addClass("has-error").focus();
		$("#n-email-input").focus();
		flag = false
	}else if(!password){
		$("#n-password-group").addClass("has-error");
		$("#n-password-input").focus();
		flag = false;
	}
	return flag;
}
