<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<!--分页插件-->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">
	$(function () {
		//添加活动
		$("#addBtn").click(function (){
			//加载日历框架
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',//语言包
				format: 'yyyy-mm-dd',//格式
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left" //显示位置
			});

			getUserList();
			var id = "${sessionScope.user.id}";
			$("#create-marketActivityOwner").val(id);
			//手动打开模态窗口
			$("#createActivityModal").modal("show");
		});

		//提交活动
		$("#saveBtn").click(function () {
			var owner = $.trim($("#create-marketActivityOwner").val());
			var name = $.trim($("#create-marketActivityName").val());
			var startTime = $.trim($("#create-startTime").val());
			var endTime = $.trim($("#create-endTime").val());
			var cost = $.trim($("#create-cost").val());
			var describe = $.trim($("#create-describe").val());
			if(owner == "" || name == "" || startTime == "" || endTime=="" || cost==""){
				alert("表单未完善");
			}else if(parseFloat(startTime) > parseFloat(endTime)){
				alert("日期有误");
			}else{
				saveActivity();
			}
		});

		//修改活动
		$("#reviseBtn").click(function () {
			var id = $("input:checkbox:checked").val();
			if(id == undefined || id == null){
				alert("无选择项");
			}else if($("input:checkbox:checked").length>1){
				alert("选项过多");
			} else{
				getUserList();
				//加载日历框架
				$(".time").datetimepicker({
					minView: "month",
					language:  'zh-CN',//语言包
					format: 'yyyy-mm-dd',//格式
					autoclose: true,
					todayBtn: true,
					pickerPosition: "bottom-left" //显示位置
				});
				//获取选项详细内容
				queryOne(id);
				$("#editActivityModal").modal("show");
			}
		});

		//提交修改后的活动
		$("#updateBtn").click(function (){
			var id = $("input:checkbox:checked").val();
			var owner = $.trim($("#update-Owner").val());
			var name = $.trim($("#update-name").val());
			var startDate = $.trim($("#update-startTime").val());
			var endDate = $.trim($("#update-endTime").val());
			var cost = $.trim($("#update-cost").val());
			var description = $.trim($("#update-describe").val());
			if(owner == "" || name == "" || startDate == "" || endDate=="" || cost==""){
				alert("表单未完善")
			}else if(parseInt(startDate.replaceAll("-","1")) > parseInt(endDate.replaceAll("-","1"))){
				alert("修改日期有误")
			}else{
				$.ajax({
					url:"${pageContext.request.contextPath}/activity/update.action",
					data:{
						"id":id,
						"owner":owner,
						"name":name,
						"startDate":startDate,
						"endDate":endDate,
						"cost":cost,
						"description":description
					},
					dataType:"json",
					success:function (data) {
						if(data){
							$("#editActivityModal").modal("hide");
							pageActivity(0,5);
						}else{
							alert("修改失败");
						}
					}
				})
			}
		});

		//删除活动
		$("#strikeOutBtn").click(function () {
			var length = $("input:checkbox:checked").length;
			if (!(length == undefined || length == null || length == "")) {
				alert("确定要删除" + length + "条数据");
				var ids = "";
				$.each($("input:checkbox:checked"), function () {
					ids = ids + $(this).val() + "/";
				});
				var str = ids.substring(0, ids.length - 1);
				deleteActivity(str);
			} else {
				alert("无选择项");
			}
		});

		//全选
		$("#totalCheckbox").click(function () {
			$("input:checkbox").prop('checked',this.checked);
		});

		//加载分页活动内容
		pageActivity(0,5);
	});

	<!--获取单个对象-->
	function queryOne(id){
		$.ajax({
			url:"${pageContext.request.contextPath}/activity/queryByID.action",
			data:{"id":id},
			dataType:"json",
			success:function (data) {
				$("#update-name").val(data.name),
				$("#update-startTime").val(data.startDate),
				$("#update-endTime").val(data.endDate),
				$("#update-cost").val(data.cost),
				$("#update-describe").val(data.description)
			}
		})
	}

	<!--获取下拉框用户名-->
	function getUserList() {
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/user/all.action",
			dataType:"json",
			async:false,
			success:function (data){
				$("#create-marketActivityOwner").empty();
				$("#update-Owner").empty();
				//遍历出来的每一个n就是每一个用户
				$.each(data,function (i,n) {
					$("#create-marketActivityOwner").append("<option value='"+n.id+"'>"+n.name+"</option>");
					$("#update-Owner").append("<option value='"+n.id+"'>"+n.name+"</option>")
				});
			}
		})
	}

	<!--保存活动-->
	function saveActivity() {
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/activity/save.action",
			data:{
				"owner":$.trim($("#create-marketActivityOwner").val()),
				"name":$.trim($("#create-marketActivityName").val()),
				"startDate":$.trim($("#create-startTime").val()),
				"endDate":$.trim($("#create-endTime").val()),
				"cost":$.trim($("#create-cost").val()),
				"description":$.trim($("#create-describe").val())
			},
			dataType:"json",
			async: false,
			success:function (data){
				if(data.success){

					$("#activityAddForm")[0].reset();
					//手动关闭模态窗口
					$("#createActivityModal").modal("hide");
					//局部刷新页面
					pageActivity(0,5);
				}else{
					alert("添加市场活动失败");
				}
			}
		})
	}

	<!--删除活动-->
	function deleteActivity(ids) {
		$.ajax({
			url:"${pageContext.request.contextPath}/activity/delete.action",
			data:{"ids":ids},
			dataType:"json",
			success:function (data){
				if(data){
					alert("删除成功");
					pageActivity(0,5);
				}else{
					alert("删除失败");
				}
			}
		})
	}

	<!--基本获取页面数据-->
	function pageActivityBase() {
		pageActivity(0,5);
		return false;
	}

	<!--获取活动页面信息-->
	function pageActivity(pageNo,pageSize) {
		$("#activityBody").empty();
		$.ajax({
			url:"${pageContext.request.contextPath}/activity/pageInfo.action",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-owner").val()),
				"startDate":$.trim($("#search-startDate").val()),
				"endDate":$.trim($("#search-endDate").val())
			},
			dataType:"json",
			success:function (data){
				$.each(data.list,function (i,n) {
					$("#activityBody").append(
							"<tr class=\"active\">" +
							"<td><input type=\"checkbox\"/ value="+n.id+"></td>" +
							"<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='${pageContext.request.contextPath}/activity/doDetail.action?id="+n.id+"';\">"+n.activityName +"</a></td>\n" +
							"<td>"+n.userName+"</td>\n" +
							"<td>"+n.startDate+"</td>\n" +
							"<td>"+n.endDate+"</td>\n" +
							"</tr>");
				});
				page(data.pageNum,data.pageSize,data.pages,data.total);
			}
		})
	}

	<!--分页栏局部更新插件-->
	function page(pageNo,pageSize,totalPages,total) {
		$("#activityPage").bs_pagination({
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
				pageActivity(data.currentPage , data.rowsPerPage);
			}
		});
	}
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<!-- 创建市场活动的模态窗口 表单 -->
					<form id="activityAddForm" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="update-Owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="update-Owner">

								</select>
							</div>
                            <label for="update-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="update-name" value="">
                            </div>
						</div>

						<div class="form-group">
							<label for="update-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="update-startTime" value="" readonly>
							</div>
							<label for="update-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="update-endTime" value="" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="update-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="update-cost" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="update-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="update-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<!--
						data-dismiss="modal"
							表示关闭模态窗口
					-->
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!--头部文本-->
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>

	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;" id="activity-text">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="submit" class="btn btn-default" onclick="return pageActivityBase()">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">

					<!--
						data-toggle="modal"
							表示触发该按钮，将要打开一个模态窗口

						data-target="#createActivityModal"
							表示要打开哪个模态窗口
					-->
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="reviseBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="strikeOutBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="totalCheckbox"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id = "activityBody">
					</tbody>
				</table>
				<input type="hidden" id="pageNo">
			</div>

			<!--页码栏-->
			<div style="height: 50px; position: relative;top: 30px;" id = "page">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>