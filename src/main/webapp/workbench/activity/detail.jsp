<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<!--日历插件-->
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<!--分页插件-->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		getUserList();

		<!--ajax请求所有备注信息-->
		getPageInfoRemark(0,5);

		<!--添加备注点击事件-->
		$("#save-remark").click(function () {
			 var text = $("#remark").val();
			 if(text == undefined || text == null || text == ""){
			 	alert("备注为空");
			 }else{
				 saveRemark(text);
				 $("#remark").val("");
			 }
		});

		<!--单击备注更新-->
		$("#updateRemarkBtn").on("click",function () {
			var text = $("#noteContent").val();
			if(text == ""){
				alert("备注内容不能修改为空")
			}else{
				var id = $("#remarkId").val();
				updateRemark(id,text);
			}
		});

		<!--Activity编辑按钮点击事件-->
		$("#updateBnt").click(function () {
			//加载日历框架
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',//语言包
				format: 'yyyy-mm-dd',//格式
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left" //显示位置
			});
			$("#editActivityModal").modal("show");
		});

		<!--编辑点击-->
		$("#updateActivity").click(function () {
			var owner = $("#update-ActivityOwner").val();
			var name  = $("#update-ActivityName").val();
			var startDate = $("#update-startTime").val();
			var endDate = $("#update-endTime").val();
			var cost = $("#update-cost").val();
			var description = $("#update-describe").val();
			if(owner == "" || name == "" || startDate == "" || endDate == "" || cost == ""){
				alert("必要内容不能为空");
			}else if(parseInt(startDate.replaceAll("-","1")) > parseInt(endDate.replaceAll("-","1"))){
				alert("输入日期有误");
			}else{
				updateActivity(owner,name,startDate,endDate,cost,description)
			}
		});

		<!--点击删除-->
		$("#deleteBnt").on("click",function () {
			deleteActivity();
		});

		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});
	});

	<!--获取下拉框用户名-->
	function getUserList() {
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/user/all.action",
			dataType:"json",
			async:false,
			success:function (data){
				$("#edit-marketActivityOwner").empty();
				//遍历出来的每一个n就是每一个用户
				$.each(data,function (i,n) {
					if(n.id == "${activity.owner}"){
						$("#update-ActivityOwner").append("<option value='"+n.id+"' selected='selected'>"+n.name+"</option>");
					}else{
						$("#update-ActivityOwner").append("<option value='"+n.id+"'>"+n.name+"</option>");
					}
				});
			}
		})
	}

	<!--ajax添加备注-->
	function saveRemark(text) {
		$.ajax({
			url:"${pageContext.request.contextPath}/activityRemark/save.action",
			data:{"noteContent":text,"activityId":$("#activity-id").val()},
			dataType:"json",
			success:function (data) {
				if(data){
					//刷新评论区
					getPageInfoRemark(0,5);
				}else{
					alert("添加失败")
				}
			}
		})
	}

	<!--ajax请求所有备注信息-->
	function getPageInfoRemark(pageNo,pageSize) {
		$(".remarkDiv").remove();
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/activityRemark/pageInfoRemark.action",
			data:{"pageNo":pageNo,"pageSize":pageSize,"activityId":$("#activity-id").val()},
			dataType:"json",
			async:false,
			success:function (data) {
				$.each(data.list,function(i,n) {
					$("#page").before(
							"<div class=\"remarkDiv\" style=\"height: 60px;\">\n" +
							"<img title=\"zhangsan\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">\n" +
							"<div style=\"position: relative; top: -40px; left: 40px;\" >\n" +
							"<h5>"+n.noteContent+"</h5>\n" +
							"<font color=\"gray\">市场活动</font> <font color=\"gray\">-</font> <b>"+$("#activity-name").val()+"</b> <small style=\"color: gray;\"> "+n.createTime+" 由"+n.createBy+"</small>\n" +
							"<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">\n" +
							"<a class=\"myHref\" onclick=\"openUpdateRemark('"+n.id+"')\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
							"<a class=\"myHref\" onclick=\"deleteRemark('"+n.id+"')\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
							"</div>\n" +
							"</div>\n" +
							"</div>");
				});
				page(data.pageNum,data.pageSize,data.pages,data.total);
			},
			error:function () {
				alert("getPageInfoRemark出错了");
			}
		})
	}

	<!--打开编辑备注页面-->
	function openUpdateRemark(remarkId){
		$("#editRemarkModal").modal("show");
		$("#remarkId").val(remarkId);
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/activityRemark/queryById.action",
			data:{"id":remarkId},
			dataType:"json",
			success:function (data) {
				$("#noteContent").val(data.noteContent);
			}
		})
	}

	<!--Ajax发起编辑备注-->
	function updateRemark(id,text) {
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/activityRemark/updateRemark.action",
			data:{"id":id,"noteContent":text},
			dataType:"json",
			success:function (data) {
				if(data){
					$("#editRemarkModal").modal("hide");
					Reset();
				}else{
					alert("备注编辑失败");
				}
			}
		})
	}

	<!--删除备注-->
	function deleteRemark(id) {
		$.ajax({
			url:"${pageContext.request.contextPath}/activityRemark/deleteRemark.action",
			data:{"id":id},
			dataType:"json",
			success:function (data) {
				if(data){
					Reset();
				}else{
					alert("删除失败");
				}
			}
		})
	}

	<!--分页栏局部更新插件-->
	function page(pageNo,pageSize,totalPages,total) {
		$("#activityRemarkPage").bs_pagination({
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
				$("#pageNo").val(data.currentPage);
				getPageInfoRemark(data.currentPage , data.rowsPerPage);
			}
		});
	}

	<!--提交编辑活动-->
	function updateActivity(owner,name,startDate,endDate,cost,description){
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/activity/update.action",
			data:{"id":$("#activity-id").val(),"owner":owner,"name":name,"startDate":startDate,"endDate":endDate,"cost":cost,"description":description},
			dataType:"json",
			success:function (data) {
				if(data){
					Reset();
					$("#editActivityModal").modal("hide");
				}else{
					alert("编辑失败");
				}
			}
		})
	}

	<!--删除活动-->
	function deleteActivity() {
		window.location.href = "${pageContext.request.contextPath}/activity/deleteDoIndex.action?ids=${activity.id}";
	}

	<!--重置页面-->
	function Reset() {
		window.location.href = "${pageContext.request.contextPath}/activity/doDetail.action?id="+$("#activity-id").val();
	}
	
	<!--返回index.jsp页面-->
	function returnIndex() {
		window.location.href = "${pageContext.request.contextPath}/workbench/activity/index.jsp"
	}
</script>

</head>
<body>
	<input type="hidden" id="activity-id" value="${activity.id}">
	<input type="hidden" id="activity-name" value="${activity.name}">
	<input type="hidden" id="activity-owner" value="${activity.owner}">
	<input type="hidden" id="pageNo">
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="noteContent" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
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
                    <h4 class="modal-title" id="myModalLabel-Activity">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="update-ActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="update-ActivityOwner">
                                </select>
                            </div>
                            <label for="update-ActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="update-ActivityName" value="${activity.name}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="update-startTime" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="update-startTime" value="${activity.startDate}" readonly>
                            </div>
                            <label for="update-endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="update-endTime" value="${activity.endDate}" readonly>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="update-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="update-cost" value="${activity.cost}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="update-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="update-describe">${activity.description}</textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="updateActivity">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="returnIndex()"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>市场活动-${activity.name} <small>${activity.startDate} ~ ${activity.endDate}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="updateBnt"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger" id="deleteBnt"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${ownerName}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${activity.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;">
		<div class="page-header" >
			<h4>备注</h4>
		</div>

			<!-- 备注1 -->
		<div class="remarkDiv" style="height: 60px;">
				<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
				<div style="position: relative; top: -40px; left: 40px;" >
					<h5>哎呦！</h5>
					<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
					<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
						<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
					</div>
				</div>
			</div>


		<!--页码栏-->
		<div style="height: 120px; position: relative;top: 30px;" id = "page">
			<div id="activityRemarkPage"></div>
		</div>

		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="save-remark">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
</html>