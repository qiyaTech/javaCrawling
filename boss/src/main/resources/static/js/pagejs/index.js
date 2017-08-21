$(function() {
    $('#side-menu').metisMenu();
    //所有弹出框点空白不消失
    $('.modal').modal({backdrop: 'static', keyboard: false, show:false});
});


//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    // $(window).bind("load resize", function() {
    //     topOffset = 50;
    //     width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
    //     if (width < 768) {
    //         $('div.navbar-collapse').addClass('collapse');
    //         topOffset = 100; // 2-row-menu
    //     } else {
    //         $('div.navbar-collapse').removeClass('collapse');
    //     }
    //
    //     height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
    //     height = height - topOffset;
    //     if (height < 1) height = 1;
    //     if (height > topOffset) {
    //         $("#page-wrapper").css("min-height", (height) + "px");
    //     }
    // });
    //桌面通知申请
    if(Notification && Notification.permission !== "granted"){
        Notification.requestPermission(function(status){
            if(Notification.permission !== status){
                Notification.permission = status;
            }
        });
    }
    var $nav = $("#mainnav-menu");
    var links = $nav.find("a");
    $nav.find("li").removeClass("active-link").removeClass("active").removeClass("active-sub");
    var url = window.location.href;
    $.each(links, function (i, link) {
        var $link = $(link);
        var thisUrl = $link.attr("href").replace("./", "").replace("../", "");
        if (url.indexOf(thisUrl) > -1) {
            switch (parseInt($link.parent().attr("data-level"))) {
                case 1:
                    $link.parent().addClass("active-sub");
                    break;
                case 2:
                    $link.parent().addClass("active-link");
                    $link.parent().parent().parent().addClass("active-sub active");
                    break;
                default:
                    break;
            }
        }
    });
    $('.input-group.date').datepicker(
        {
        autoclose: true,
        format: "yyyy-mm-dd",
        todayBtn: "linked",
        todayHighlight: true,
        language:"zh-CN"
    });

    $('.input-daterange').datepicker({
        format: "yyyy-mm-dd",
        todayBtn: "linked",
        autoclose: true,
        todayHighlight: true,
        language:"zh-CN"
    });


    $(".pwdUpdate").click(function () {
        $("#password").val("");
        $("#rePassword").val("");

        $("#editPwdModal").modal('show');
    });

    //确认修改密码
    $("#editPwdSubmit").click(function(){
        var oldPwd = $("#oldPassword").val();
        var pwd = $("#password").val();
        var rePwd = $("#rePassword").val();
        var phone = $("#adminPhone").val();

        if (oldPwd == undefined || oldPwd.length==0){
            layer.msg('请输入原密码!',{time: 1000});
            return;
        }

        if (pwd == undefined || pwd.length==0){
            layer.msg('请输入新密码!',{time: 1000});
            return;
        }

        if (rePwd == undefined || rePwd.length==0){
            layer.msg('请输入确认密码!',{time: 1000});
            return;
        }

        if (pwd != rePwd) {
            layer.msg('两次输入密码不一致!',{time: 1000});
            return;
        }

        $.ajax({
            url:"/updatePwd",
            "contentType": "application/json",
            data:JSON.stringify({'oldPwd':oldPwd,'pwd':pwd,'phone':phone}),
            type:"post",
            success:function(data){
                if (data.data != undefined){

                    if (data.data.isSuccess ==0){
                        layer.msg('操作成功!',{time: 1000});
                        $("#editPwdModal").modal('hide');
                    }

                    if (data.data.isSuccess == 1){
                        layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                    }

                    if (data.data.isSuccess == 2){  //原密码不正确
                        layer.msg('原密码错误!',{time: 1000});
                    }

                }else {
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                }


                //if(data.data != undefined && data.data.isSuccess ==0){
                //    layer.msg('操作成功!',{time: 1000});
                //    $("#editPwdModal").modal('hide');
                //}else{
                //    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                //}
            },
            error:function(data){
                layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
            }
        });
    });

    var readMsg =  function (id) {
        clickMsg(id,null);
    };
    var clickMsg =  function (id,url) {
        $.ajax({
            url:"/msg/readMsg",
            "contentType": "application/json",
            data:JSON.stringify({msgId:id}),
            type:"post",
            success:function(data){
                if(url!=null){
                    location.href=url;
                }
            }});


    };

    //点击关闭通知
    var closeMsg =  function (id,url,mynotify) {
        $.ajax({
            url:"/msg/readMsg",
            "contentType": "application/json",
            data:JSON.stringify({msgId:id}),
            type:"post",
            success:function(data){
                if(url!=null){
                    location.href=url;
                }else {
                    mynotify.close();
                }
            }});


    };
    var clicknotifMsg =  function (id,url,n) {
        $.ajax({
            url:"/msg/readMsg",
            "contentType": "application/json",
            data:JSON.stringify({msgId:id}),
            type:"post",
            success:function(data){
                n.close();
                if(url!=null){
                    location.href=url;
                    window.focus();
                }else {

                }
            }});


    };
    var getMsg =  function () {
        $.ajax({
            url:"/msg/msglist",
            "contentType": "application/json",
            data:{},
            type:"post",
            success:function(data){
                if (data.data != undefined){

                    if (data.code ==0){
                        data.data.forEach(function(value,index,array){
                            var  abcurl='"'+value.url+'"';
                            if($("#alertmsg"+value.id).length==0)  {
                                var mynotify=   $.notify({
                                    icon: '/img/todolist.png',
                                    title:value.title,
                                    message: value.context,
                                    url: value.url,
                                    target: '_self'
                                },{
                                    offset: {x:10,y:60}
                                    ,
                                    allow_dismiss: true,
                                    type: 'info',
                                    delay: 0,
                                    icon_type: 'image',
                                    onClose:function () {

                                    },
                                    onClosed:function () {
                                        readMsg(value.id);
                                    },
                                    template: '<div   data-notify="container" style="background-color: #EE8222" class="col-xs-12 col-sm-4 alert alert-{0}"  role="alert">' +
                                    '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                                    '<div class="row"  id="alertmsg'+value.id+'">' +
                                    '<div class="col-sm-1 text-center"><img   data-notify="icon" style="width: 40px;height: 40px"  class=" pull-left"></div> ' +
                                    '<div class="col-sm-11" style="padding-left: 20px">' +
                                    '<span style="font-size: 16px" data-notify="title">{1}</span> <br/>' +
                                    '<span data-notify="message">{2}</span>' +
                                    '</div> ' +

                                    '</div>' +
                                    '' +
                                    '</div>'
                                });
                                $("#alertmsg"+value.id).click(function () {
                                    closeMsg(value.id,value.url,mynotify);

                                });
                                //桌面通知
                                var options={
                                    dir: "auto",
                                    lang: "utf-8",
                                    icon:"/img/todolistback.png",
                                    body: value.context,

                                };
                                if(Notification && Notification.permission === "granted"){
                                    var n = new Notification(value.title, options);
                                    n.onshow = function(){

                                    };
                                    n.onclick = function() {
                                        clicknotifMsg(value.id,value.url,n);
                                    };
                                    n.onclose = function(e){
                                        console.info("关闭了！")
                                        //readMsg(value.id);
                                    };
                                    n.onerror = function() {

                                    }

                                }

                            }

                        });
                    }



                }else {
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                }


                //if(data.data != undefined && data.data.isSuccess ==0){
                //    layer.msg('操作成功!',{time: 1000});
                //    $("#editPwdModal").modal('hide');
                //}else{
                //    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                //}
            }});



    };
    getMsg();
    //通知轮询
    var test = setInterval(function(){
        getMsg();
    },1200000);


});

