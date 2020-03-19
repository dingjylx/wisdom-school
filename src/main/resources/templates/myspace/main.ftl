<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的空间</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/css/public.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/css/fly.css" media="all"/>
    <link rel="stylesheet" href="${basePath!}/static/layui/css/modules/laydate/default/laydate.css" media="all"/>
</head>
<style type="text/css">

    .f-single-head {
        margin-bottom: 12px;
        padding: 0 16px;
        position: relative;
        min-height: 50px;
    }

    .f-info {
        padding: 0 16px 0;
        font-weight: normal;
        zoom: 1;
        margin: 0 0 8px;
    }

    .comments-container {
        display: none;
    }
</style>
<body class="childrenBody" style="margin-top: 20px;">

<div class="main layui-clear">
    <div class="wrap">
        <div class="content">
            <form method="post">
				<textarea id="sayContent" name="sayContent" placeholder="请输入内容" class="layui-textarea fly-editor"
                          style="height: 150px;"></textarea>
                <button type="button" class="layui-btn layui-btn-normal add" id="publish"
                        style="margin: 10px 0px 10px 0px"><i class="layui-icon">&#xe608;</i>发布动态
                </button>
            </form>

            <ul class="fly-list">
                <script id="posts" type="text/html">
                    <ul>
                        {{# layui.each(d.data, function(index, item){ }}
                        <li class="fly-list-li" style="height: 120px;">
                            <span style="display: none" name="sayId">{{ item.id }}</span>
                            <div class="f-single-head f-aside">
                                <div class="user-pto" style="float: left"><a class="img"><img
                                                src="${basePath!}/static/logo2.png" class="layui-nav-img userAvatar"
                                                width="50" height="50"></a>
                                </div>
                                <div class="user-info">
                                    <div class="f-nick"><a href="#">{{ item.userName }}</a></div>
                                    <div class="info-detail"><span class=" ui-mr8 state">{{item.sendTime}}</span></div>
                                </div>
                            </div>
                            <div class="fly-tip f-single-content f-aside">
                                <div class="f-info">{{ item.sayContent }}</div>
                            </div>
                            <div class="f-single-foot f-aside">
                                <p>
                                <span class="fly-list-hint">
                                        <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;"
                                           onclick="praise(this)" title="点赞">&#xe6c6;</i> {{ item.likeCount }}
                                        <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;"
                                           onclick="reply(this)" title="回复">&#xe611;</i> {{ item.commentCount }}
                                </span>
                                </p>
                            </div>

                        </li>
                        {{# }); }} {{# if(d.total === 0){ }}
                        <li class="fly-none">没有任何帖子</li>
                        {{# } }}
                    </ul>
                </script>
                <div id="postss"></div>
            </ul>
            <div id="pager"></div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${basePath!}/static/layui/lay/modules/laydate.js"></script>
<script type="text/javascript" src="${basePath!}/static/layui/layui.js"></script>
<script type="text/javascript" src="${basePath!}/static/js/jquery.min.js"></script>

<script>
    layui.use(['laypage', 'laytpl', 'jquery', 'form'], function () {
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var laypage = layui.laypage;
        loadPosts(laytpl, laypage, 1, true);

        function loadPosts(laytpl, laypage, pageNo, renderpager) {
            $.post("${basePath!}/myspace/querySayList", {
                pageNum: pageNo,
                pageSize: 15
            }, function (data) {

                var posts = $("#posts").html();
                laytpl(posts).render(data, function (html) {
                    $("#postss").html(html);
                });

                if (!renderpager) return;

                laypage.render({
                    elem: 'pager'
                    , limit: 15
                    , count: data.total //总页数
                    , jump: function (obj, first) {
                        //得到了当前页，用于向服务端请求对应数据
                        if (!first) {
                            loadPosts(laytpl, laypage, obj.curr, false);
                            $('body').prop('scrollTop', '0');
                        }
                    }
                });
            });
        }

        //发布动态提交
        $("#publish").click(function () {
            $.post("${basePath!}/myspace/publish", {
                sayContent: $("#sayContent").val()
            }, function (res) {
                if (res.success) {
                    $("#sayContent").val("");
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    loadPosts(laytpl, laypage, 1, true);
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });

    });

    // 点赞
    function praise(e) {
        var sayId = $(e).parents("li").find("span[name='sayId']").text();
        $.post("${basePath!}/myspace/like", {
            id: sayId
        }, function (res) {
            if (res.success) {
                layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
            } else {
                layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
            }
        });
    }

    // 展开评论
    function reply(e) {
        var sayId = $(e).parents("li").find("span[name='sayId']").text();
        console.log(sayId);
        layer.open({
            type: 2,
            title: '回复列表',
            shadeClose: true,//开启遮罩关闭
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['800px', '500px'],
            content: '${basePath!}/myspace/viewComments?sayId=' + sayId
        });
    }

</script>


</body>
</html>