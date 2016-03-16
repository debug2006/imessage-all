var editor;
var auditPage = {
	init:function(){
			KindEditor.ready(function(K){
				editor =K.create('textarea[name="content"]',{
					allowFileManager :true,
					cssPath : '../manage/common/kindEditor-4.1.10/plugins/code/prettify.css',
					uploadJson:'../manage/common/jsp/upload_json.jsp',
					fileManagerJson:'../manage/common/jsp/file_manager_json.jsp'
				});
			});

			KindEditor.options.cssData = 'body{font-size:16px;}';

			$('#audit_dom').show();
	},	

	edit : function(infoId) {

		document.getElementById('audit_content').src = "/manage/service_gate.htm?service=query_news_details&infoId="+infoId+"&viewname=editIframe.htm";

		$('#edit_btn').show();

		$('#audit_dom').hide();
	},

	complete : function(infoId,source) {

		if(confirm("确定保存编辑吗？")){

			var content = editor.html();
		//	console.log(content);
			re = new RegExp("/manage/","g");
			var new_content = content.replace(re,"http://10.123.118.201:8080/manage/");
			$.post("/manage/service_gate.htm", {
				
				htmlContent:encodeURI(new_content),source:encodeURI(source),infoId:infoId,viewname:'audit_news_detail.htm',service:'update_news_audit_state',opType : '5'}, function() {
					
					window.location.href="/manage/service_gate.htm?service=query_news_details&infoId="+infoId+"&viewname=remark_details.htm";

			});
		}
	},

	cancel : function() {
		location.reload();
	},
	see : function(infoId) {

		window.open("/manage/service_gate.htm?service=query_news_details&infoId="+infoId+"&viewname=see.htm");
	},

	audit_list : function(infoId,tips,source,opType) {

		if(confirm(tips)){

			$.post("/manage/service_gate.htm", {

				source:encodeURI(source),infoId:infoId,viewname:'audit_news_detail.htm',service:'update_news_audit_state',opType : opType}, function() {

					window.location.reload();

			});
		}
	},

	audit_list_top_analysis:function(infoId,tips,source,opType,tradeDate,title,url) {

		var subtitle=prompt(tips,"股市收盘点评");

		if(subtitle){

			$.post("/manage/service_gate.htm", {

				source:encodeURI(source),infoId:infoId,viewname:'audit_news_detail.htm',service:'update_news_audit_state',opType : opType,tradeDate:tradeDate,title:

				encodeURI(title),url:url,subtitle:encodeURI(subtitle)}, function() {

					window.location.reload();

			});
		}
	},

	audit : function(infoId,tips,source,opType) {

		if(confirm(tips)){

			var note = document.getElementById('note').value;

			$.post("/manage/service_gate.htm", {

				source:encodeURI(source),infoId:infoId,viewname:'audit_news_detail.htm',service:'update_news_audit_state',opType : opType,note:encodeURI(note)}, function() {

					window.location.reload();
			});
		}

	},


	audit_top_analysis : function(infoId,tips,source,opType,tradeDate,title,url) {



		if(confirm(tips)){



			var note = document.getElementById('note').innerHTML;



			$.post("/manage/service_gate.htm", {

				source:encodeURI(source),infoId:infoId,viewname:'audit_news_detail.htm',service:'update_news_audit_state',opType : opType,note:encodeURI(note),tradeDate:tradeDate,title:encodeURI(title),url:url}, function() {

					window.location.reload();

			});

		}

	}

};



auditPage.init();