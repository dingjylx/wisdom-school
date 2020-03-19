<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>课程表管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/css/public.css" media="all"/>
</head>
<body class="childrenBody">

<form class="layui-form">
    <div class="layui-form-item" id="toolbar">
        <div class="layui-input-inline">
            <input class="layui-input" name="courseTitle" id="courseTitle" autocomplete="off" placeholder="课程名称">
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <button class="layui-btn" type="button" lay-submit lay-filter="query" id="btn-query">查询</button>
                <#if sessionUser.userType=="T" || sessionUser.userType=="ADMIN">
                    <button type="button" class="layui-btn layui-btn-normal add" lay-event="add"><i class="layui-icon">&#xe608;</i>新增
                    </button>
                </#if>
            </div>
        </div>
    </div>
</form>

<table class="layui-hide" id="test" lay-filter="test" lay-data="{id: 'test'}"></table>

<div id="pagesize"></div>

<script type="text/html" id="barDemo">
    <#if sessionUser.userType=="T" || sessionUser.userType=="ADMIN">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </#if>
</script>


<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>

<script>
    layui.use(['table', 'form', 'jquery', 'laypage'], function () {
        var $ = layui.jquery, table = layui.table, form = layui.form, laypage = layui.laypage;


        form.on('submit(query)', function (data) {
            var param = data.field;//定义临时变量获取表单提交过来的数据，非json格式

            table.render({
                elem: '#test'
                , url: '${basePath!}/course/list'
                , method: 'post'
                , request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , where: {
                    courseTitle: param.courseTitle,
                    fromDate: param.fromDate,
                    toDate: param.toDate
                }
                , parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": res.code, //解析接口状态
                        "msg": res.msg, //解析提示文本
                        "count": res.total, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                }
                , even: true
                , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next'] //自定义分页布局
                    , groups: 3 //只显示 3 个连续页码
                    , first: '首页' //首页
                    , prev: '上一页'
                    , next: '下一页'
                    , last: '尾页' //尾页
                    , limit: 10
                    , limits: [10, 20, 50]
                }
                , cols: [[
                    {type: 'checkbox', fixed: 'left'}
                    , {field: 'id', title: 'id'}
                    , {field: 'courseTitle', title: '课程名称'}
                    , {field: 'courseTeacher', title: '课程老师'}
                    , {field: 'courseTime', title: '上课时间'}
                    , {field: 'courseClassroom', title: '上课教室'}
                    , {field: 'selectedNumber', title: '已选人数'}
                    , {field: 'maxNumber', title: '最大可选人数'}
                    , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
                ]]
            });
        });//end form


        function refreshTable() {
            $("#btn-query").trigger("click");
        }


        //新增
        $("#toolbar .add").click(function () {
            layer.open({
                type: 1,
                title: "新增课程",
                content: $('#addDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['650px', '400px'],
                cancel: function (index, layero) {
                    // 右上角关闭事件的逻辑
                    layer.close(index);
                }
            });
        })

        //新增提交
        form.on('submit(addformsub)', function (data, index) {
            $.post("${basePath!}/course/add", {
                courseTitle: data.field.courseTitle,
                courseTeacher: data.field.courseTeacher,
                courseClassroom: data.field.courseClassroom,
                courseTime: data.field.courseTime,
                maxNumber: data.field.maxNumber
            }, function (res) {
                console.log("data:" + res)
                if (res.success) {
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    layer.close(index);//关闭当前弹窗
                    refreshTable();
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });
        //编辑的提交
        form.on('submit(editformsub)', function (data) {
            $.post("${basePath!}/course/update", {
                id: data.field.id,
                courseTitle: data.field.courseTitle,
                courseTeacher: data.field.courseTeacher,
                courseClassroom: data.field.courseClassroom,
                courseTime: data.field.courseTime,
                maxNumber: data.field.maxNumber
            }, function (res) {
                console.log("data:" + res)
                if (res.success) {
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    layer.closeAll();//关闭所有的弹窗
                    refreshTable();
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            //console.log(obj)
            if (obj.event === 'del') {//删除
                layer.confirm('确认删除该记录吗?', function (index) {
                    $.post("${basePath!}/course/del", {
                        id: data.id
                    }, function (res) {
                        console.log("data:" + res)
                        if (res.success) {
                            layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                            layer.closeAll();//关闭所有的弹窗
                            refreshTable();
                        } else {
                            layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                        }
                    });
                });
            } else if (obj.event === 'edit') {//编辑
                //获取编辑的值
                form.val('editform', {
                    "id": data.id,
                    "courseTitle": data.courseTitle,
                    "courseTeacher": data.courseTeacher,
                    "courseClassroom": data.courseClassroom,
                    "courseTime": data.courseTime,
                    "maxNumber": data.maxNumber
                })
                layer.open({
                    type: 1,
                    title: "编辑公告",
                    content: $('#editDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['650px', '400px'],
                });
            }
        });

        $("#btn-query").trigger("click");

    });
</script>

<!--新增弹窗-->
<div id="addDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>课程名称</label>
            <div class="layui-input-inline">
                <input name="courseTitle" required lay-verify="required" placeholder="请输入课程名称" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>课程老师</label>
            <div class="layui-input-inline">
                <input name="courseTeacher" required lay-verify="required" placeholder="请输入课程老师" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>上课教室</label>
            <div class="layui-input-inline">
                <input name="courseClassroom" required lay-verify="required" placeholder="请输入上课教室" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>上课时间</label>
            <div class="layui-input-inline">
                <input name="courseTime" required lay-verify="required" placeholder="请输入上课时间" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>最大可选人数</label>
            <div class="layui-input-inline">
                <input name="maxNumber" required lay-verify="required" placeholder="请输入人数" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="addformsub">新增</button>
            </div>
        </div>
    </form>
</div>
<!--编辑弹窗-->
<div id="editDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="editform" style="margin-top:10px">
        <input name="id" style="display: none">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>课程名称</label>
            <div class="layui-input-inline">
                <input name="courseTitle" required lay-verify="required" placeholder="请输入课程名称" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>课程老师</label>
            <div class="layui-input-inline">
                <input name="courseTeacher" required lay-verify="required" placeholder="请输入课程老师" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>上课教室</label>
            <div class="layui-input-inline">
                <input name="courseClassroom" required lay-verify="required" placeholder="请输入上课教室" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>上课时间</label>
            <div class="layui-input-inline">
                <input name="courseTime" required lay-verify="required" placeholder="请输入上课时间" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>最大可选人数</label>
            <div class="layui-input-inline">
                <input name="maxNumber" required lay-verify="required" placeholder="请输入人数" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="editformsub">更新</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>