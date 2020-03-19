<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>班级管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/css/public.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/layui/css/modules/laydate/default/laydate.css" media="all"/>
</head>
<body class="childrenBody">

<form class="layui-form">
    <div class="layui-form-item" id="toolbar">
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="attendanceTime1" placeholder="请选择考勤日期" name="attendanceTime"
                   style="height:30px;width:180px;">
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <button class="layui-btn" type="button" lay-submit lay-filter="query" id="btn-query">查询</button>
                <button type="button" class="layui-btn layui-btn-normal add" lay-event="add"><i class="layui-icon">&#xe608;</i>新增
                </button>
            </div>
        </div>
    </div>
</form>

<table class="layui-hide" id="test" lay-filter="test" lay-data="{id: 'test'}"></table>

<div id="pagesize"></div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="queryRecord">签到情况</a>
</script>

<script type="text/javascript" src="${basePath!}/static/layui/lay/modules/laydate.js"></script>
<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>

<script>
    layui.use(['table', 'form', 'jquery', 'laypage', 'laydate'], function () {
        var $ = layui.jquery, table = layui.table, form = layui.form, laypage = layui.laypage;

        //执行一个laydate实例
        laydate.render({
            elem: '#attendanceTime1' //指定元素
            , type: 'date'
            , format: 'yyyy-MM-dd'
            , trigger: 'click'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#attendanceTime2' //指定元素
            , type: 'date'
            , format: 'yyyy-MM-dd'
            , trigger: 'click'
        });

        form.on('submit(query)', function (data) {
            var param = data.field;//定义临时变量获取表单提交过来的数据，非json格式

            table.render({
                elem: '#test'
                , url: '${basePath!}/attendance/code/list'
                , method: 'post'
                , request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , where: {
                    attendanceTime: param.attendanceTime
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
                    , {field: 'codeId', title: '考勤码Id'}
                    , {field: 'code', title: '考勤码'}
                    , {field: 'attendanceTime', title: '考勤日期'}
                    , {field: 'createTime', title: '创建时间'}
                    , {field: 'expireTime', title: '过期时间'}
                    , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 120}
                ]]
                , done: function () {
                    $("[data-field='codeId']").css('display', 'none');
                }
            });
        });//end form

        function refreshTable() {
            $("#btn-query").trigger("click");
        }

        //新增
        $("#toolbar .add").click(function () {
            layer.open({
                type: 1,
                title: "新建考勤码",
                content: $('#addDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['460px', '260px'],
                cancel: function (index, layero) {
                    // 右上角关闭事件的逻辑
                    layer.close(index);
                },
                success: function () {
                    $("#addDialoge .update").trigger("click");
                }
            });
        })

        //新增提交
        form.on('submit(addformsub)', function (data, index) {
            $.post("${basePath!}/attendance/code/add", {
                code: data.field.code,
                attendanceTime: data.field.attendanceTime
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

        //刷新考勤码
        $("#addDialoge .update").click(function () {
            var code = randomWord(false, 4);
            $("#addDialoge input[name='code']").val(code);
        })


        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if (obj.event === 'queryRecord') {//查看
                layui.use(['table', 'form'], function () {
                    table2 = layui.table;
                    layer.open({
                        type: 1,
                        title: '考勤记录',
                        closeBtn: 1,
                        area: ['800px', '400px'], //宽高
                        content: $('#openRecordBox'),
                        success: function () {
                            table2.render({
                                elem: '#openRecordTable'
                                , url: '${basePath!}/attendance/code/queryRecord'
                                , method: 'post'
                                , title: "考勤记录"
                                , request: {
                                    pageName: 'pageNum' //页码的参数名称，默认：page
                                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                                }
                                , where: {
                                    codeId: data.codeId
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
                                    , limit: 5
                                    , limits: [5, 10]
                                }
                                , cols: [[
                                    {field: 'studentName', title: '学生'}
                                    , {field: 'statusCn', title: '考勤状态'}
                                    , {field: 'sAttendanceTime', title: '签到时间', width: 200}
                                ]]
                            });
                        }
                    });

                });

            }
        });

        $("#btn-query").trigger("click");

    });

    /*
    ** randomWord 产生任意长度随机字母数字组合
    ** randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位
    ** xuanfeng 2014-08-28
    */
    function randomWord(randomFlag, min, max) {
        var str = "",
            range = min,
            arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

        // 随机产生
        if (randomFlag) {
            range = Math.round(Math.random() * (max - min)) + min;
        }
        for (var i = 0; i < range; i++) {
            pos = Math.round(Math.random() * (arr.length - 1));
            str += arr[pos];
        }
        return str;
    }
</script>

<!--新增弹窗-->
<div id="addDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>考勤日期</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="attendanceTime2" placeholder="请选择考勤日期" name="attendanceTime"
                       style="height:30px;width:180px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>考勤码</label>
            <div class="layui-input-inline">
                <input name="code" required lay-verify="required" placeholder="请输入考勤码" autocomplete="off"
                       class="layui-input" type="text">
                <button type="button" class="layui-btn layui-btn-normal update" lay-event="updateCode"><i
                            class="layui-icon">&#xe608;</i>刷新
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="addformsub">新增</button>
            </div>
        </div>
    </form>
</div>

<!--查看考勤记录-->
<div id="openRecordBox" style="display: none; padding: 10px;">
    <table class="layui-hide" id="openRecordTable" lay-filter="openRecordTable"
           lay-data="{id: 'openRecordTable'}"></table>
</div>

</body>
</html>