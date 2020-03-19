<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>首页--layui后台管理模板 2.0</title>
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
<blockquote class="layui-elem-quote layui-bg-green">
    <div id="nowTime"></div>
</blockquote>

<form id="completeUserInfo" class="layui-form" action="">
    <input id="id" name="id" style="display: none">
    <input id="userType" style="display: none">
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input id="userName" name="userName" lay-verify="required" autocomplete="off" class="layui-input"
                   type="text"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-inline">
            <input id="mobile" name="mobile" autocomplete="off" class="layui-input" type="tel" readonly="readonly"/>
        </div>
    </div>
    <div id="student" style="display: none">
        <div class="layui-form-item">
            <label class="layui-form-label">班级</label>
            <div class="layui-input-inline">
                <input id="class" name="class" autocomplete="off" class="layui-input" type="text" readonly="readonly"/>
            </div>
            <button type="button" class="layui-btn layui-btn-normal add" id="addClass-btn"><i class="layui-icon">&#xe608;</i>加入班级
            </button>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">学号</label>
            <div class="layui-input-inline">
                <input id="studentNo" name="studentNo" autocomplete="off" class="layui-input" type="text"/>
            </div>
        </div>
    </div>
    <div id="teacher" style="display: none">
        <div class="layui-form-item">
            <label class="layui-form-label">学院</label>
            <div class="layui-input-inline">
                <input id="college" name="college" autocomplete="off" class="layui-input" type="text"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">科目</label>
            <div class="layui-input-inline">
                <input id="subject" name="subject" autocomplete="off" class="layui-input" type="text"/>
            </div>
        </div>
    </div>

    <div class="layui-form-item loginBtn">
        <div class="layui-input-block">
            <button id="completeUserInfo" class="layui-btn layui-btn-warm" lay-submit="" lay-filter="completeUserInfo">
                保存
            </button>
        </div>
    </div>
</form>

<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>
<script type="text/javascript" src="${basePath!}/static/js/main.js"></script>

<script>
    layui.use(['table', 'form', 'jquery', 'laypage'], function () {
        var $ = layui.jquery, table = layui.table, form = layui.form, laypage = layui.laypage;

        //查询基本信息
        $.post("${basePath!}/user/query", {}, function (result) {
            console.log("data:" + result)
            if (result.success) {
                var user = result.currentUser;
                $("#id").val(user.id);
                $("#mobile").val(user.mobile);
                $("#userType").val(user.userType);
                if (user.userName == null) {
                    $("#userName").attr('placeholder', "请填写你的姓名");
                } else {
                    $("#userName").val(user.userName);
                }
                if (user.userType == "S") {
                    if (user.studentNo == "") {
                        $("#studentNo").attr('placeholder', "请填写你的学号");
                    } else {
                        $("#studentNo").val(user.userName);
                    }
                    if (user.className == null) {
                        $("#class").attr('placeholder', "请加入你的班级");
                    } else {
                        $("#class").val(user.className);
                        $("#addClass-btn").css("display", "none");
                    }
                    $("#student").css('display', 'block');
                } else {
                    if (user.college == null) {
                        $("#college").attr('placeholder', "请填写你的学院");
                    } else {
                        $("#college").val(user.college);
                    }
                    if (user.subject == null) {
                        $("#subject").attr('placeholder', "请填写你的科目");
                    } else {
                        $("#subject").val(user.subject);
                    }
                    $("#teacher").css('display', 'block');
                }

            }
        });

        $("#addClass-btn").click(function () {
            layer.open({
                type: 1,
                title: "加入班级",
                content: $('#addDialoge'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['400px', '200px'],
                cancel: function (index, layero) {
                    // 右上角关闭事件的逻辑
                    layer.close(index);
                }
            });
        });

        $("#addclass-btn").click(function (index) {
            if ($("#classCode").val().trim() == "") {
                layer.msg("请输入邀请码！");
                return;
            }
            $.post("${basePath!}/class/join", {
                classCode: $("#classCode").val()
            }, function (res) {
                console.log("data:" + res)
                if (res.success) {
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    layer.close(index);//关闭所有的弹窗
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        })

        //监听表单提交事件
        form.on('submit(completeUserInfo)', function (data) {

            var userType = $("#userType").val();
            var userName = $("#userName").val().trim();
            if (userName == "") {
                layer.alert("请填写姓名！");
                return;
            }
            if (userType == "T") {
                var college = $("#college").val().trim();
                var subject = $("#subject").val().trim();
                if (college == "" || subject == "") {
                    layer.alert("请填写学院和科目！");
                    return;
                }
            } else {
                var studentNo = $("#studentNo").val().trim();
                if (studentNo == "") {
                    layer.alert("请填写学号！");
                    return;
                }
            }

            //保存基础信息
            $.post("${basePath!}/user/saveInfo", $("#completeUserInfo").serialize(), function (result) {
                console.log("data:" + result)
                if (result.success) {
                    layer.alert("保存成功！");
                } else {
                    layer.alert("系统异常！");
                }
            });

        })


    })
</script>

<div id="addDialoge" class="dialogue" style="display: none">
    <form class="layui-form" action="" lay-filter="addform">
        <div class="layui-form-item">
            <label class="layui-form-label"><i class="layui-red">*</i>班级邀请码</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="classCode" placeholder="请输入邀请码" name="content"
                       style="height:30px;width:120px;">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="hidden" name="id" id="id">
                <button class="layui-btn" id="addclass-btn">加入</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>