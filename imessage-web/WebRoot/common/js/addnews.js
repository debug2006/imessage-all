var editor;

var addNews = {

	init:function()

	{

			KindEditor.ready(function(K){

				editor =K.create('textarea[name="content"]',{

					allowFileManager :true,

					cssPath : '../manage/common/kindEditor-4.1.10/plugins/code/prettify.css',

					uploadJson:'../manage/common/jsp/upload_json.jsp',

					fileManagerJson:'../manage/common/jsp/file_manager_json.jsp'

				});

			});

			

			KindEditor.options.cssData = 'body{font-size:16px;}';

	},

	edit : function(infoId) {

		$('#edit_btn').show();

		$('#audit_dom').hide();

	},

	randomNum:function(n){

		var rnd = "";

		for(var i=0;i<n;i++){

			rnd+=Math.floor(Math.random()*10);

		}

		return rnd;

	},

	complete : function() { 

		if(confirm("确定保存编辑吗？")){

			var  news_title    = document.getElementById("newsTitle").value;

			var content = editor.html();

		//	console.log(content);

			re = new RegExp("/manage/","g");
			
			if(content.indexOf("/manage/")>0)
			{
				content = content.replace(re,"http://10.123.118.201:8080/manage/");
			}
		//	console.log(new_content instanceof String);

		//	console.log(new_content);

			if(null == news_title ||  ""==news_title){

				alert("标题不能为空！");

				return;

			}

			if(null == content ||  ""==content){

				alert("请输入内容！");

				return;

			}

			var infoId = "TC"+addNews.randomNum(6);

		//	console.log("infoId="+infoId);

		//	console.log("content="+new_content);

			$.post("/manage/service_gate.htm", {

				htmlContent:encodeURI(content),infoId:infoId,title:encodeURI(news_title),viewname:'audit_news_list.htm',service:'insert_news_rescord',opType :'5'}, function() {
					alert("保存成功！");
			});

		}

	},

	cancel : function() {

		$('#editor').html("");

		location.reload();
	}

};

addNews.init();

addNews.edit(10001);