<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>作业管理</title>
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
            <input class="layui-input" name="name" id="name" autocomplete="off" placeholder="作业名称">
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <button class="layui-btn" type="button" lay-submit lay-filter="query" id="btn-query">查询</button>
                <#if sessionUser.userType=="T" || sessionUser.userType=="ADMIN">
                    <button type="button" class="layui-btn layui-btn-normal add" lay-event="add"><i class="layui-icon">&#xe608;</i>上传作业
                    </button>
                </#if>
            </div>
        </div>
    </div>
</form>

<table class="layui-hide" id="test" lay-filter="test" lay-data="{id: 'test'}"></table>

<div id="pagesize"></div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="downloadHomework">下载</a>
    <#if sessionUser.userType=="S">
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="uploadAnswer">上传答案</a>
    </#if>
    <#if sessionUser.userType=="T" || sessionUser.userType=="ADMIN">
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="queryAnswer">答案</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </#if>

</script>

<script type="text/html" id="answerBar">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="downloadAnswer">下载</a>
</script>

<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>

<script>
    layui.use(['table', 'form', 'jquery', 'laypage'], function () {
        var $ = layui.jquery, table = layui.table, form = layui.form, laypage = layui.laypage;

        form.on('submit(query)', function (data) {
            var param = data.field;//定义临时变量获取表单提交过来的数据，非json格式

            table.render({
                elem: '#test'
                , url: '${basePath!}/homework/list'
                , method: 'post'
                , request: {
                    pageName: 'pageNum' //页码的参数名称，默认：page
                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
                , where: {
                    name: param.name
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
                    , {field: 'id', title: 'id', width: 80}
                    , {field: 'homeworkName', title: '作业名称'}
                    , {field: 'fileId', title: '文件id', width: 80}
                    , {field: 'fileName', title: '附件'}
                    , {field: 'teacherName', title: '老师'}
                    , {field: 'createTime', title: '创建时间', width: 200}
                    , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 300}
                ]]
                , done: function () {
                    $("[data-field='fileId']").css('display', 'none');
                }

            });
        });//end form

        function refreshTable() {
            $("#btn-query").click();
        }

        //上传作业
        $("#toolbar .add").click(function () {
            layer.open({
                type: 1,
                title: "上传作业",
                content: $('#addDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['500px', '300px'],
                cancel: function (index, layero) {
                    // 右上角关闭事件的逻辑
                    layer.close(index);
                }
            });
        })

        //上传作业提交
        form.on('submit(addformsub)', function (data, index) {
            $.post("${basePath!}/homework/addHomework", {
                name: data.field.name,
                fileId: data.field.addfileid
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

        //上传答案提交
        form.on('submit(uploadAnswerformsub)', function (data, index) {
            $.post("${basePath!}/homework/addAnswer", {
                homeworkId: data.field.homeworkid,
                fileId: data.field.answerfileid
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
            $.post("${basePath!}/homework/update", {
                name: data.field.homeworkname,
                id: data.field.homeworkid
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


        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {//删除
                layer.confirm('确认删除该记录吗?', function (index) {
                    $.post("${basePath!}/homework/del", {
                        id: data.id,
                    }, function (res) {
                        console.log("data:" + res)
                        if (res.success) {
                            layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                            refreshTable();
                        } else {
                            layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                        }
                    });
                });
            } else if (obj.event === 'edit') {//编辑
                //获取编辑的值
                form.val('editform', {
                    "homeworkid": data.id,
                    "homeworkname": data.homeworkName
                })
                layer.open({
                    type: 1,
                    title: "编辑作业",
                    content: $('#editDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['500px', '300px'],
                });
            } else if (obj.event === 'downloadHomework') {//下载
                var fileName = data.fileName;
                window.location = "${basePath!}/homework/download?fileName=" + fileName;
            } else if (obj.event === 'uploadAnswer') {//上传答案
                //获取编辑的值
                form.val('uploadAnswerDialoge', {
                    "homeworkid": data.id,
                    "homeworkname": data.homeworkName
                })
                layer.open({
                    type: 1,
                    title: "上传答案",
                    content: $('#uploadAnswerDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['500px', '300px'],
                    cancel: function (index, layero) {
                        // 右上角关闭事件的逻辑
                        layer.close(index);
                    }
                });
            } else if (obj.event === 'queryAnswer') {//查看
                layui.use(['table', 'form'], function () {
                    table2 = layui.table;
                    layer.open({
                        type: 1,
                        title: '作业答案',
                        closeBtn: 1,
                        area: ['900px', '450px'], //宽高
                        content: $('#openAnswerBox'),
                        success: function () {
                            table2.render({
                                elem: '#openAnswerTable'
                                , url: '${basePath!}/homework/queryAnswers'
                                , method: 'post'
                                , title: "作业答案"
                                , request: {
                                    pageName: 'pageNum' //页码的参数名称，默认：page
                                    , limitName: 'pageSize' //每页数据量的参数名，默认：limit
                                }
                                , where: {
                                    homeworkId: data.id
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
                                    , limits: [5, 10]
                                }
                                , cols: [[
                                    {field: 'answerId', title: '答案id', width: 80}
                                    , {field: 'homeworkId', title: '作业id', width: 80}
                                    , {field: 'answerFileName', title: '答案附件'}
                                    , {field: 'studentName', title: '学生'}
                                    , {field: 'createTime', title: '提交时间', width: 200}
                                    , {fixed: 'right', title: '操作', toolbar: '#answerBar', width: 80}
                                ]]
                                , done: function () {
                                    $("[data-field='homeworkId']").css('display', 'none');
                                }
                            });
                        }
                    });

                    //监听行工具事件
                    table2.on('tool(openAnswerTable)', function (obj) {
                        var data = obj.data;
                        if (obj.event === 'downloadAnswer') {//下载答案
                            window.location = "${basePath!}/homework/download?fileName=" + data.answerFileName;
                        }
                    })
                });

            }
        });

        $("#btn-query").trigger("click");

    });

    layui.use('upload', function () {
        var upload = layui.upload,
            $2 = layui.jquery

        //老师上传作业
        upload.render({
            elem: '#filehomework'
            , url: '${basePath!}/homework/upload'
            , auto: false
            //,multiple: true
            , bindAction: '#uploadhomework'
            , size: 2048 //最大允许上传的文件大小 2M
            , accept: 'file' //允许上传的文件类型
            , exts: 'txt|xls|xlsx|doc|docx|zip'//只上传pdf文档
            , done: function (res) {
                console.log(res)
                if (res.code == 1) {//成功的回调
                    //do something （比如将res返回的图片链接保存到表单的隐藏域）
                    $2('#addfilename').val(res.data.fileName);
                    $2('#addfileid').val(res.data.fileId);
                    $2('#uploadhomework').hide();
                    layer.msg(res.msg, {
                        icon: 6
                    });
                } else if (res.code == 2) {
                    layer.msg(res.msg, {
                        icon: 5
                    });
                }
            }
        });

        //学生上传答案
        upload.render({
            elem: '#fileanswer'
            , url: '${basePath!}/homework/upload'
            , auto: false
            //,multiple: true
            , bindAction: '#uploadanswer'
            , size: 2048 //最大允许上传的文件大小 2M
            , accept: 'file' //允许上传的文件类型
            , exts: 'txt|xls|xlsx|doc|docx|zip'//只上传pdf文档
            , done: function (res) {
                console.log(res)
                if (res.code == 1) {//成功的回调
                    //do something （比如将res返回的图片链接保存到表单的隐藏域）
                    $2('#answerfilename').val(res.data.fileName);
                    $2('#answerfileid').val(res.data.fileId);
                    $2('#uploadanswer').hide();
                    layer.msg(res.msg, {
                        icon: 6
                    });
                } else if (res.code == 2) {
                    layer.msg(res.msg, {
                        icon: 5
                    });
                }
            }
        });
    });
</script>

<!--新增弹窗-->
<div id="addDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>作业名称</label>
            <div class="layui-input-inline">
                <input name="name" required lay-verify="required" placeholder="请输入作业名称" autocomplete="off"
                       class="layui-input" type="text">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <div class="layui-upload">
                    <button type="button" class="layui-btn layui-btn-normal" id="filehomework">选择文件</button>
                    <button type="button" class="layui-btn" id="uploadhomework">开始上传</button>
                    <input type="hidden" name="addfilename" id="addfilename">
                    <input type="hidden" name="addfileid" id="addfileid">
                </div>
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
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>作业名称</label>
            <div class="layui-input-inline">
                <input name="homeworkname" required lay-verify="required" placeholder="请输入作业名称" autocomplete="off"
                       class="layui-input" type="text">
                <input type="hidden" name="homeworkid" id="homeworkid">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="editformsub">更新</button>
            </div>
        </div>
    </form>
</div>

<!--上传答案-->
<div id="uploadAnswerDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="uploadAnswerDialoge" style="margin-top:10px">
        <div class="layui-form-item">
            <label class="layui-form-label">作业名称：</label>
            <div class="layui-input-inline">
                <input name="homeworkname" readonly="readonly" required lay-verify="required" placeholder="请输入作业名称"
                       autocomplete="off" class="layui-input" type="text">
                <input type="hidden" name="homeworkid" id="homeworkid">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <div class="layui-upload">
                    <button type="button" class="layui-btn layui-btn-normal" id="fileanswer">选择文件</button>
                    <button type="button" class="layui-btn" id="uploadanswer">开始上传</button>
                    <input type="hidden" name="answerfilename" id="answerfilename">
                    <input type="hidden" name="answerfileid" id="answerfileid">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="uploadAnswerformsub">新增</button>
            </div>
        </div>
    </form>
</div>

<!--查看学生-->
<div id="openAnswerBox" style="display: none; padding: 10px;">
    <table class="layui-hide" id="openAnswerTable" lay-filter="openAnswerTable"
           lay-data="{id: 'openAnswerTable'}"></table>
</div>

</body>
</html>