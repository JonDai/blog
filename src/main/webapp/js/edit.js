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
