var param = {username:localStorage.getItem("username"), password:localStorage.getItem("password")};

$(function(){
	/*验证是否登陆*/
	$.ajax({
		url:"vervifi",
		type:"POST",
		data:param,
		dataType:"json",
		success:function(data){
			if(data.faild){errorHandle(data)}
		}
	});
	
	/*override 点击事件*/
	$("#m-override").click(function(){
		$(".main-content>div:visible").hide();
		$(".jumbotron").show();
	})
	/*文章列表加载事件*/
	$("#m-article").click(function(){
		$(".main-content>div:visible").hide();
		$(".article-group").show();
		$(".article-group tbody").empty();
		$.ajax({
			url:"articles",
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				if(data.faild){
					errorHandle(data);
				}else{
					var tbody = $(".article-group tbody");
					$.each(data,function(k,v){
						tbody.append("<tr><td>"+v.id+"</td><td><a href='#'>"+v.title+"</a></td><td>"+v.createtime+"</td><td>"+v.status+"</td>" +
								"<td><button type='button' class='btn btn-primary'>编辑</button> <button type='button' class='btn btn-danger'>删除</button></td></tr>");
					});
				}
			}
		});
	});
	
	/*查看文章*/
	$(".article-group tbody").on("click","a",function(){
		var id = $(this).parent().prev().html();
		$.ajax({
			url:"article/"+id,
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				alert(data.title);
			}
		});
	});
	
	/*删除文章*/
	$(".article-group tbody").on("click","delete-btn",function(){
		var id = $(this).parent().parent().children(":first").html();
		$.ajax({
			url:"article/"+id,
			data:param,
			type:"DELETE",
			success:function(data){
				$(this).parent().parent().remove();
			}
		})
	});
	
	/*分类列表 事件处理*/
	
	/*左侧sidebar m-classify点击*/
	$("#m-classify").click(function(){
		$(".main-content>div:visible").hide();
		$(".classify-group").show();
		$("#class-tech").click();
	});
	
	/*content中 m-classify列表点击 获取一级目录下的所有二级目录*/
	$(".classify-group li[role='presentation']").click(function(){
		$(".classify-group .active").removeClass("active");
		$(this).addClass("active");
		$(".classify-group tbody").empty();
		var clssify = $(this).attr("value");
		if(clssify == "dream"){param.pclassify = 1}
		if(clssify == "life"){param.pclassify = 2}
		if(clssify == "tech"){param.pclassify = 3}
		$.ajax({
			url:"classifies",
			type:"POST",
			dataType:"json",
			data:param,
			success:function(data){
				var tbody = $(".classify-group tbody");
				$.each(data,function(k,v){
					tbody.append("<tr><td>"+v.id+"</td><td><a href='#'>"+v.name+"</a></td><td>"+v.count+"</td><td>"+v.description+"</td>" +
					"<td><button type='button' class='btn btn-primary'>编辑</button> <button type='button' class='btn btn-danger'>删除</button></td></tr>");
				});
			}
		});
		
	});
	/*添加二级目录*/
	$("#addModal #submit-btn").click(function(){
		$("#addTips").hide();
		var clssify = $("#level-one :checked").attr("value");
		if(clssify == "dream"){param.pclassify = 1}
		if(clssify == "life"){param.pclassify = 2}
		if(clssify == "tech"){param.pclassify = 3}
		if(!$("#classifyname").val()){
			$("#classify-group").addClass("has-error");
			$("#classifyname").focus();
		}else{
			param.name = $("#classifyname").val();
		}
		param.description = $("#classifydesc").val();
		$.ajax({
			url:"addclassify",
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				$("#addTips").show();
			}
		});
	});
	
	/*sidebar 点击样式调整*/
	$(".sidebar").on("click","li",function(){
		$(".sidebar .active").removeClass("active");
		$(this).addClass("active");
	});
	
	/*初始化多选按钮样式*/
	$('#addModal input').iCheck({
	    checkboxClass: 'icheckbox_flat-green',
	    radioClass: 'iradio_flat-green'
	  });
})
/*错误处理*/
function errorHandle(data){
	if(data.faild == "logon"){location.href="../index.html";}
	else{alert(data.faild)}
}
