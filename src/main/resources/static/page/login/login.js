
layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    $(".loginBody .seraph").click(function(){
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
            time:5000
        });
    })

    //登录按钮
    /*form.on("submit(login)",function(data){
        $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        setTimeout(function(){
            window.location.href = "/layuicms2.0";
        },1000);
        return false;
    })*/

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})

//定时发送验证码
var wait = 60;
var startJob;
//o 对象
function send(o, flag) {
    if (!flag) {
        return false;
    }
    //第一次秒数
    if (wait == 60) {
        o.setAttribute("disabled", true);
        //自定义验证规则
        $.post("/user/sendMsg", $("#useLogin").serialize(), function (data) {
            console.log("data:" + data)
            if (data.code == "1000") {
                layer.msg("发送短信成功");
            } else {
                $("#password").val("");
                picCode = drawPic();
                $("#code").val("");
                //禁用发送短信验证码按钮
                o.removeAttribute("disabled");
                //o.value = "获取验证码";
                wait = 60;
                flag = false;
                layer.alert(data.message);
            }
            return false;
        });
    }
    if (wait == 0) {
        o.removeAttribute("disabled");
        $("#msgBtn").html("获取验证码");
        wait = 60;
    } else {
        o.setAttribute("disabled", true);
        if (wait <60) {
            $("#msgBtn").html("<span style='margin-left: -12px;'>"+wait + "s后可重新发送</span>");
        }
        wait--;
        startJob=setTimeout(function () {
            if (wait == 0) {
                flag = true
            }
            ;
            send(o, flag)
        }, 1000)
    }
}
function closeSend(){
    $("#msgBtn").removeAttr("disabled");
    $("#msgBtn").html("获取验证码");
    clearTimeout(startJob);
}
