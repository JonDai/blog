var param = {username:localStorage.getItem("username"), password:localStorage.getItem("password")};
var DRAFT = 0 ; //草稿
var PUBLISH = 1;  //发布
$(function(){
	/*草稿按钮*/
	$("#draft-btn").click(function(){
		saveArticle(DRAFT);
	});
	
	/*取消按钮*/
	$("#reset-btn").click(function(){
		location.href = "main.html";
	});
	
	/*选择一级分类之后，显示其下的所有二级分类*/
	$('input').on('ifChecked', function(event){
		var leveltwo = $("#level-two");
		leveltwo.empty();
		if($(this).val() == "dream"){param.pclassify = 1}
		if($(this).val() == "life"){param.pclassify = 2}
		if($(this).val() == "tech"){param.pclassify = 3}
		$.ajax({
			url:"classifies",
			type:"POST",
			dataType:"json",
			data:param,
			success:function(data){
				$.each(data,function(k,v){
					leveltwo.append("<input type='checkbox'  value="+v.id+"><label class='classify'>"+v.name+"</label>");
				});
			}
		});
	});
	
	/*初始化多选按钮样式*/
	$('#subModal input').iCheck({
	    checkboxClass: 'icheckbox_flat-green',
	    radioClass: 'iradio_flat-green'
	  });
});

/*保存文章*/
function saveArticle(status){
	param.title = $("#create-log-title").val();
	param.content = $("#editor").html();
	param.status = status;
	if(!param.title){alert("标题不能为空！");}
	else if(!param.content){alert("内容不能为空！");}
	else{
		$.ajax({
			url:"savearticle",
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				alert("ok");
			}
		});
	}
}
