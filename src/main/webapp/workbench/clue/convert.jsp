<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<!--分页插件-->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">
	$(function(){
		//加载日历框架
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',//语言包
			format: 'yyyy-mm-dd',//格式
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left" //显示位置
		});

		//打开活动模态窗口
		$("#openSearchModalBtn").click(function () {
			$("#searchActivityModal").modal("show");
			getRelation(0,5);
		});

		//点击 关联
		$("#add_bundModal_relation").click(function () {
			var clueId = $.trim($("#clueId").val());
			var length = $(".activityCheckbox:checkbox:checked").length;
			var activityId = "";
			if (length <= 0) {
				alert("未选择关联项");
			} else {
				alert("你确定要关联这个活动嘛");
				$.each($(".activityCheckbox:checkbox:checked"), function () {
					activityId = activityId + $(this).val() + "/";
				});
				activityId = activityId.substring(0, activityId.length - 1);
				var activityName = $("#"+activityId).html();
				$("#activityId").val(activityId);
				$("#activityName").val(activityName);
				$("#searchActivityModal").modal("hide");
			}
		});

		//添加活动模态窗口的所有触发事件
		$("#bundModal_search").blur(function () {
			getRelation(0,5);
		});

		//点击转换
		$("#convertBtn").click(function () {
			if($("#isCreateTransaction").prop("checked")){
				alert("进行表单提交请求");
				$("#convertForm_tran").submit();
			}else{
				window.location.href="${pageContext.request.contextPath}/clue/convert.action?clueId=${param.clueId}&userId=${param.userId}"
			}
		});

		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
	});

	<!--获取该CLue关联的活动-->
	function getRelation(pageNo,pageSize) {
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/clue/queryRelations.action",
			data:{"clueId":$("#clueId").val()},
			dataType:"json",
			async:false,
			success:function (relations) {
				getActivitys(pageNo,pageSize,relations);
			}
		});
	}

	<!--获取添加活动模态窗口关联活动数据-->
	function getActivitys(pageNo,pageSize,relations){
		$("#activityText").empty();
		var text = $("#bundModal_search").val();
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/activity/pageInfo.action",
			data:{"pageNo":pageNo,"pageSize":pageSize,"name":text},
			dataType:"json",
			async:false,
			success:function (data) {
				$.each(data.list,function (i,n) {
					var falg = true;
					$.each(relations,function (x,y) {
						if(y.activityId == n.id){
							falg = false;
						}
					});
					if(falg){
						$("#activityText").append("<tr>\n" +
								"<td><input type=\"checkbox\" class='activityCheckbox' value='"+n.id+"'/></td>\n" +
								"<td id='"+n.id+"'>"+n.activityName+"</td>\n" +
								"<td>"+n.startDate+"</td>\n" +
								"<td>"+n.endDate+"</td>\n" +
								"<td>"+n.userName+"</td>\n" +
								"</tr>");
					}else{
						$("#activityText").append("<tr>\n" +
								"<td></td>\n" +
								"<td>"+n.activityName+"</td>\n" +
								"<td>"+n.startDate+"</td>\n" +
								"<td>"+n.endDate+"</td>\n" +
								"<td>"+n.userName+"</td>\n" +
								"</tr>");

					}
				});
				page(pageNo,pageSize,data.pages,data.total);
			}
		})
	}

	<!--获取活动关联活动数据分页栏局部更新插件-->
	function page(pageNo,pageSize,totalPages,total) {
		$("#activitys_Page").bs_pagination({
			currentPage: pageNo, // 页码
			rowsPerPage: pageSize, // 每页显示的记录条数
			maxRowsPerPage: 20, // 每页最多显示的记录条数
			totalPages: totalPages, // 总页数
			totalRows: total, // 总记录条数

			visiblePageLinks: 6, // 显示几个卡片

			showGoToPage: true,
			showRowsPerPage: true,
			showRowsInfo: true,
			showRowsDefaultInfo: true,

			onChangePage : function(event, data){
				getRelation(data.currentPage,data.rowsPerPage);
			}
		});
	}

</script>
</head>
<body>
	<input type="hidden" id="clueId" value="${param.clueId}">
	<input type="hidden" id="userId" value="${param.userId}">

	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="bundModal_search" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activityText">
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
						</tbody>
					</table>
					<!--页码栏-->
					<div style="height: 120px; position: relative;top: 30px;" id = "bundModal_page">
						<div id="activitys_Page"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-default" id="add_bundModal_relation">关联</button>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${param.name}-${param.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${param.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${param.name}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >

		<form action="${pageContext.request.contextPath}/clue/convert.action" method="post" id="convertForm_tran">
			<input type="hidden" name="clueId" value="${param.clueId}">
			<input type="hidden" name="userId" value="${param.userId}">
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="money">金额</label>
		    <input type="text" class="form-control" id="money" name="money">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="name">交易名称</label>
		    <input type="text" class="form-control" id="name" name="name" value="动力节点-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedDate" name="expectedDate" readonly>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage" name="stage" class="form-control">
				<c:forEach items="${applicationScope.dicAll.stage}" var="stage">
					<option>${stage.text}</option>
				</c:forEach>
		    </select>
		  </div>
			<div class="form-group" style="width: 400px;position: relative; left: 20px;">
				<label for="transactionType">类型</label>
				<select id="transactionType" name="type" class="form-control">
					<c:forEach items="${applicationScope.dicAll.transactionType}" var="transactionType">
						<option>${transactionType.text}</option>
					</c:forEach>
				</select>
			</div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="openSearchModalBtn" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
			  <input type="hidden" id="activityId" name="activityId"/>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${param.username}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" id="convertBtn" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>