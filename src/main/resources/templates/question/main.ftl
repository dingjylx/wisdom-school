<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>问题管理</title>
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
            <input type="text" class="layui-input" id="title" placeholder="请输入标题" name="title"
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
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="queryComment">查看</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="comment">评论</a>
    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="like">点赞</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    {{# if(d.isMine){ }}
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    {{# } }}
</script>

<script type="text/javascript" src="${basePath!}/static/layui/lay/modules/laydate.js"></script>
<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>

<script>
    layui.use(['table', 'form', 'jquery', 'laypage', 'laydate'], function () {
        var $ = layui.jquery, table = layui.table, form = layui.form, laypage = layui.laypage;

        form.on('submit(query)', function (data) {
            var param = data.field;//定义临时变量获取表单提交过来的数据，非json格式

            table.render({
                elem: '#test'
                , url: '${basePath!}/qa/question/list'
                , method: 'post'
                , request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , where: {
                    title: param.title
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
                    , {field: 'id', title: '问题Id'}
                    , {field: 'title', title: '标题', width: 200}
                    , {field: 'description', title: '描述'}
                    , {field: 'isMine', title: '是否本人的帖子'}
                    , {field: 'creator', title: '作者'}
                    , {field: 'commentCount', title: '评论数', width: 80}
                    , {field: 'likeCount', title: '点赞数', width: 80}
                    , {field: 'createTime', title: '创建时间', width: 200}
                    , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 290}
                ]]
                , done: function () {
                    $("[data-field='id']").css('display', 'none');
                    $("[data-field='description']").css('display', 'none');
                    $("[data-field='isMine']").css('display', 'none');
                }
            });
        });//end form

        function refreshTable() {
            $("#btn-query").click();
        }

        //新增问题
        $("#toolbar .add").click(function () {
            layer.open({
                type: 1,
                title: "新建问题",
                content: $('#addDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['600px', '400px'],
                cancel: function (index, layero) {
                    // 右上角关闭事件的逻辑
                    layer.close(index);
                }
            });
        })

        //新增问题提交
        form.on('submit(addformsub)', function (data, index) {
            $.post("${basePath!}/qa/question/add", {
                title: data.field.title,
                description: data.field.description
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
        form.on('submit(editformsub)', function (data, index) {
            $.post("${basePath!}/qa/question/update", {
                id: data.field.id,
                title: data.field.title,
                description: data.field.description
            }, function (res) {
                console.log("data:" + res)
                if (res.success) {
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    layer.close(index);//关闭所有的弹窗
                    refreshTable();
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });

        //新增评论提交
        form.on('submit(addformsub2)', function (data, index) {
            $.post("${basePath!}/qa/comment/add", {
                questionId: data.field.id,
                content: data.field.content
            }, function (res) {
                console.log("data:" + res)
                if (res.success) {
                    layer.msg("操作成功", {time: 600, icon: 6});//成功信息提示
                    refreshTable();
                } else {
                    layer.msg("操作异常", {time: 600, icon: 5});//错误信息提示
                }
            });
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {//删除
                layer.confirm('确认删除该记录吗?', function (index) {
                    $.post("${basePath!}/qa/question/del", {
                        id: data.id,
                    }, function (res) {
                        console.log("data:" + res)
                        if (res.success) {
                            layer.msg("操作成功", {time: 600, icon: 6});//成功信息提示
                            layer.closeAll();//关闭所有的弹窗
                            refreshTable();
                        } else {
                            layer.msg("操作异常", {time: 600, icon: 5});//错误信息提示
                        }
                    });
                });
            } else if (obj.event === 'edit') {//编辑
                //获取编辑的值
                form.val('editform', {
                    "id": data.id,
                    "title": data.title,
                    "description": data.description
                })
                layer.open({
                    type: 1,
                    title: "编辑问题",
                    content: $('#editDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['600px', '400px'],
                });
            } else if (obj.event === 'comment') {//评论
                //获取编辑的值
                form.val('addform2', {
                    "id": data.id
                })
                layer.open({
                    type: 1,
                    title: "新建评论",
                    content: $('#addDialoge2'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['600px', '400px'],
                    cancel: function (index, layero) {
                        // 右上角关闭事件的逻辑
                        layer.close(index);
                    }
                });
            } else if (obj.event === 'like') {//点赞
                $.post("${basePath!}/qa/question/like", {
                    id: data.id
                }, function (res) {
                    if (res.success) {
                        layer.msg("操作成功", {time: 600, icon: 6});//成功信息提示
                        refreshTable();
                    } else {
                        layer.msg("操作异常", {time: 600, icon: 5});//错误信息提示
                    }
                });
            } else if (obj.event === 'queryComment') {//查看评论
                //获取编辑的值
                form.val('editform2', {
                    "title2": data.title,
                    "description2": data.description
                })
                layui.use(['table', 'form'], function () {
                    table2 = layui.table;
                    layer.open({
                        type: 1,
                        title: '评论列表',
                        closeBtn: 1,
                        area: ['800px', '500px'], //宽高
                        content: $('#openRecordBox'),
                        success: function () {
                            table2.render({
                                elem: '#openRecordTable'
                                , url: '${basePath!}/qa/queryComments'
                                , method: 'post'
                                , title: "评论列表"
                                , request: {
                                    pageName: 'pageNum' //页码的参数名称，默认：page
                                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                                }
                                , where: {
                                    id: data.id
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
                                    {field: 'content', title: '评论内容'}
                                    , {field: 'commentator', title: '评论者'}
                                    , {field: 'createTime', title: '评论时间', width: 200}
                                ]]
                            });
                        }
                    });

                });

            }
        });

        $("#btn-query").trigger("click");

    });

</script>

<!--新增评论弹窗-->
<div id="addDialoge2" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform2" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>评论</label>
            <div class="layui-input-inline">
                <textarea name="content" required lay-verify="required" placeholder="请输入问题描述" autocomplete="off"
                          style="width:300px;" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="hidden" name="id" id="id">
                <button class="layui-btn" lay-submit lay-filter="addformsub2">保存</button>
            </div>
        </div>
    </form>
</div>

<!--编辑问题弹窗-->
<div id="editDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="editform" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>标题</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="title" placeholder="请输入标题" name="title"
                       style="height:30px;width:300px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>描述</label>
            <div class="layui-input-inline">
                <textarea name="description" required lay-verify="required" placeholder="请输入问题描述" autocomplete="off"
                          style="width:300px;" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="hidden" name="id" id="id">
                <button class="layui-btn" lay-submit lay-filter="editformsub">保存</button>
            </div>
        </div>
    </form>
</div>

<!--新增问题弹窗-->
<div id="addDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>标题</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="title" placeholder="请输入标题" name="title"
                       style="height:30px;width:300px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>描述</label>
            <div class="layui-input-inline">
                <textarea name="description" required lay-verify="required" placeholder="请输入问题描述" autocomplete="off"
                          style="width:300px;" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="addformsub">保存</button>
            </div>
        </div>
    </form>
</div>

<!--查看问题详情及评论-->
<div id="openRecordBox" style="display: none; padding: 10px;">
    <form class="layui-form" action="" lay-filter="editform2" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 40px;">标题</label>
            <div class="layui-input-inline">
                <input readonly="readonly" type="text" class="layui-input" placeholder="请输入标题" name="title2"
                       style="height:30px;width:660px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 40px;">描述</label>
            <div class="layui-input-inline">
                <input readonly="readonly" name="description2" placeholder="请输入问题描述" autocomplete="off"
                       class="layui-textarea" style="width:660px;height: 90px;">
            </div>
        </div>
    </form>
    <table class="layui-hide" id="openRecordTable" lay-filter="openRecordTable"
           lay-data="{id: 'openRecordTable'}"></table>
</div>

</body>
</html>