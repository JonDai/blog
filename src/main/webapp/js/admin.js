

$(function(){
	/*文章列表加载事件*/
	$("#m-article").click(function(){
		$.ajax({
			url:"articles",
			type:"GET",
			dataType:"json",
			success:function(data){
				if(data.faild){
					alert(data.faild);
				}else{
					var tbody = $(".article-group tbody");
					$.each(data,function(k,v){
						tbody.append("<tr><td>"+v.id+"</td><td><a href='#'>"+v.title+"</a></td><td>"+v.createtime+"</td><td>"+v.status+"</td><td>编辑 删除</td></tr>");
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
			type:"GET",
			dataType:"json",
			success:function(data){
				alert(data.title);
			}
		});
	});
	
	/*sidebar 点击样式调整*/
	$(".sidebar").on("click","li",function(){
		$(".sidebar .active").removeClass("active");
		$(this).addClass("active");
	});
})

