var param = {username:localStorage.getItem("username"), password:localStorage.getItem("password")};
var DRAFT = 0 ; //草稿
var PUBLISH = 1;  //发布
$(function(){
	//在初始化页面时候，判断url中是否存在id，如果有则加载内容
	var articleid = getUrlParam("id");
	if(articleid){
		param.id = articleid;
		$.ajax({
			url:"article/"+articleid,
			type:'POST',
			data:param,
			dataType:'json',
			success:function(data){
				$("#create-log-title").val(data.title);
				$("#editor").html(data.content);
				$("#submit-btn").hide();
				$("#modify-btn").show();
			}
		});
	}
	
	/*修改文章内容*/
	$("#modify-btn").click(function(){
		saveArticle(PUBLISH);
	});
	
	/*初始化多选按钮样式*/
	$('#subModal input').iCheck({
	    checkboxClass: 'icheckbox_flat-green',
	    radioClass: 'iradio_flat-green'
	  });
	/*草稿按钮*/
	$("#draft-btn").click(function(){
		saveArticle(DRAFT);
	});
	
	/*取消按钮*/
	$("#reset-btn").click(function(){
		location.href = "main.html";
	});
	
	/*发布按钮*/
	$("#submit-btn").click(function(){
		param.title = $("#create-log-title").val();
		param.content = $("#editor").html();
		param.status = PUBLISH;
		if(!param.content){sweetAlert("Oops...", "你好像什么也没写...", "error");}
		else if(!param.title){sweetAlert("Oops...", "给这篇Blog起个名字吧!", "error");}
		else{
			$("#subModal").modal("show");
		}
	});
	/*确认发布提交*/
	$("#publish-btn").click(function(){
		param.pclassify = $("#level-one .checked>input").attr("value");
		param.pid = $("#level-two .checked>input").attr("value");
		if(param.pclassify && param.pid){
			$.ajax({
				url:"savearticle",
				type:"POST",
				data:param,
				dataType:"json",
				success:function(data){
					if(data.faild){
						sweetAlert("Oops...", data.faild, "error");
						$("#subModal").modal("hide");
					}else{
						$("#subModal").modal("hide");
						swal("OK!", "保存成功!", "success");
					}
				}
			});
		}else{
			sweetAlert("Oops...", "给这篇Blog找个归属吧!", "error");
		}
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
					leveltwo.append("<input type='radio' name='iiCheck' id='inlineRadio"+v.id+"'  value="+v.id+"><label class='classify'>"+v.name+"</label>");
				});
				/*此处不能将一级分类的radio也实例化，那么不会触发该函数的触发器*/
				$('#subModal input:not(.iRadio)').iCheck({
				   // checkboxClass: 'icheckbox_flat-green',
				    radioClass: 'iradio_flat-green'
				  });
			}
		});
	
	});
});


/*保存文章*/
function saveArticle(status){
	param.title = $("#create-log-title").val();
	param.content = $("#editor").html();
	param.status = status;
	if(!param.title){sweetAlert("Ooops...", "取个好名字吧!", "error");}
	else if(!param.content){sweetAlert("Ooops...","你好像什么也没写...","error");}
	else{
		$.ajax({
			url:"savearticle",
			type:"POST",
			data:param,
			dataType:"json",
			success:function(data){
				swal("OK!","保存成功!","success");
			}
		});
	}
}
function convertClassify(classify){
	var intClassify = 0;
	if(classify == "dream"){intClassify = 1}
	if(classify == "life"){intClassify = 2}
	if(classify == "tech"){intClassify = 3}
	return intClassify;
}

function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return unescape(r[2]); return null;
}