<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>说说评论</title>
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

</style>
<body class="childrenBody" style="margin-top: 20px;">
<div class="content">
    <span style="display: none" id="sayId">${sayId}</span>
    <input type="hidden" id="toname">
    <input type="hidden" id="pid">
    <div id="addComment-container">
        <textarea id="addComment" name="addComment" placeholder="请输入内容" class="layui-textarea fly-editor"
                  style="height: 90px;"></textarea>
        <button type="button" class="layui-btn layui-btn-warm" id="addComment-btn" style="margin: 5px 0px 5px 0px">
            发表评论
        </button>
    </div>

    <ul class="fly-list">
        <script id="posts" type="text/html">
            <ul>
                {{# layui.each(d.data, function(index, item){ }}
                <li class="fly-list-li" style="height: 120px;">
                    <span name="id" style="display:none;">{{ item.id }}</span>
                    <div class="f-single-head f-aside">
                        <div class="user-pto" style="float: left"><a class="img"><img
                                        src="${basePath!}/static/logo2.png" class="layui-nav-img userAvatar"
                                        width="50" height="50"></a>
                        </div>
                        <div class="user-info">
                            <div class="f-nick">
                                <span name="owner">{{ item.owner }}</span>
                                回复 <span>{{ item.toname }}</span>
                            </div>
                            <div class="info-detail"><span class=" ui-mr8 state">{{item.sendTime}}</span></div>
                        </div>
                    </div>
                    <div class="fly-tip f-single-content f-aside">
                        <div class="f-info">{{ item.commentContent }}</div>
                        <button type="button" class="layui-btn layui-btn-normal layui-btn-xs" onclick="comment(this)"
                                style="float: right;margin-right: 10px;">回复
                        </button>
                    </div>
                </li>
                {{# }); }} {{# if(d.total === 0){ }}
                <li class="fly-none">没有任何帖子</li>
                {{# } }}
            </ul>
        </script>
        <div id="postss"></div>
    </ul>

    <div id="replyComment-container" style="display: none">
        <textarea id="replyComment" name="replyComment" placeholder="请输入内容" class="layui-textarea fly-editor"
                  style="height: 90px;"></textarea>
        <button type="button" class="layui-btn layui-btn-warm" id="replyComment-btn" style="margin: 5px 0px 5px 0px">
            回复
        </button>
    </div>

    <div id="commentPager"></div>

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
            $.post("${basePath!}/myspace/queryComments", {
                sayId: $("#sayId").text(),
                pageNum: pageNo,
                pageSize: 5
            }, function (data) {

                var posts = $("#posts").html();
                laytpl(posts).render(data, function (html) {
                    $("#postss").html(html);
                });

                if (!renderpager) return;

                laypage.render({
                    elem: 'commentPager'
                    , limit: 5
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

        //回复作者
        $("#addComment-btn").click(function () {
            $.post("${basePath!}/myspace/replyComment", {
                sayId: $("#sayId").text(),
                commentContent: $("#addComment").val(),
                pid: 0,
                toname: "作者"
            }, function (res) {
                if (res.success) {
                    $("#addComment").val("");
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    loadPosts(laytpl, laypage, 1, true);
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });

        //回复他人
        $("#replyComment-btn").click(function () {
            var pid = $("#pid").val();
            var toname = $("#toname").val();
            $.post("${basePath!}/myspace/replyComment", {
                sayId: $("#sayId").text(),
                commentContent: $("#replyComment").val(),
                pid: pid,
                toname: toname
            }, function (res) {
                if (res.success) {
                    $("#replyComment").val("");
                    layer.msg(res.msg, {time: 600, icon: 6});//成功信息提示
                    loadPosts(laytpl, laypage, 1, true);
                } else {
                    layer.msg(res.msg, {time: 600, icon: 5});//错误信息提示
                }
            });
        });

    });


    // 回复
    function comment(e) {
        var pid = $(e).parents("li").find("span[name='id']").text();
        var toname = $(e).parents("li").find("span[name='owner']").text();
        $("#pid").val(pid);
        $("#toname").val(toname);

        $("#replyComment-container").toggle();
        $(document).one("click",
            function () { //对document绑定一个影藏Div方法
                $("#replyComment-container").hide();
            });

        event.stopPropagation(); //阻止事件向上冒泡

        $("#replyComment").attr('placeholder', "回复 " + toname + "：");
        $("#replyComment").focus();
    }

    $("#replyComment-container").click(function (event) {
        event.stopPropagation(); //阻止事件向上冒泡
    });

</script>


</body>
</html>