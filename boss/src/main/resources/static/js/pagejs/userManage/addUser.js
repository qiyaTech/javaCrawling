/**
 * Created by qiyalm on 16/6/28.
 */
/*
 添加用户
 */
$(function(){
    var table= $("#userDataTable").DataTable();
    var isOk=false;
    var isUserNameOk=false;

    //添加用户Modal弹出时触发(清空内容)
    $("#userAddModal").on('shown.bs.modal',function() {
        $(this).find("#addName").val("");
        $(this).find("#addPhone").val("");
   //     $(this).find("#addAreaDesc").val("");
        $(this).find("#addusername").val("");
        $(this).find("#password").val("");
        $(this).find("#rpassword").val("");
        //$(this).find("#addDistrict").val("");
    });

    /*
     * 添加信息时检验手机号是否存在
     * */
    $("#addPhone").blur(function(){
        var phone=$(this).val();
        if(phone==""||phone==null){
            $("#addPhoneErr").html("请填写手机号");
            return;
        }else {
            $("#addPhoneErr").html("");
        }

        if (!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(phone)){
            $("#phoneErr").html("请填写正确的手机号");
            return;
        }else {
            $("#phoneErr").html("");
        }

        $.ajax({
            url:"/userManage/existUser",
            type:"post",
            data:{'phone':phone},
            success:function(data){
                if (!data.result){
                    isOK=false;
                    $("#addPhoneErr").html("手机号已存在");
                    $("#addPhone").focus();
                }else {
                    isOK=true;
                    $("#addPhoneErr").html("");
                }
            }
        })
    });

    $("#addusername").blur(function(){
        var addusername=$(this).val();
        if(addusername==""||addusername==null){
            $("#adduserloginErr").html("请输入用户登录名！");
            return;
        }else {
            $("#adduserloginErr").html("");
        }

        if ((/^[0-9]*$/).test(addusername)){
            $("#adduserloginErr").html("用户名不能是纯数字！");
            return;
        }else {
            $("#adduserloginErr").html("");
        }

        $.ajax({
            url:"/userManage/existUserName",
            type:"post",
            data:{'username':addusername},
            success:function(data){
                if (!data.result){
                    isUserNameOk=false;
                    $("#adduserloginErr").html("用户名已存在!");
                    $("#addusername").focus();
                }else {
                    isUserNameOk=true;
                    $("#adduserloginErr").html("");
                }
            }
        })
    });

    $("#addName").blur(function(){
        var name=$(this).val();
        if(name==""||name==null){
            $("#addNameErr").html("请填写姓名");
        }else {
            $("#addNameErr").html("");
        }
    });

    /*
     确认添加
     */
    $("#addUserSubmit").click(function(){
        if(isOK==false||isUserNameOk==false){
            return;
        }

        //获取需要添加的信息
        var userAddModal=$("#userAddModal");
        var name=userAddModal.find('#addName').val();
        var phone= userAddModal.find('#addPhone').val();
        var gender= "";
        var radio = document.getElementsByName("addGender");
        for (i=0; i<radio.length; i++) {
            if (radio[i].checked) {
                gender=radio[i].value;
            }
        }

        var addusername= userAddModal.find('#addusername').val();
        // var areaDesc= userAddModal.find('#addAreaDesc').val();
        // var district = userAddModal.find('#addDistrict').val();
        var password=userAddModal.find('#password').val();
        // var password=userAddModal.find('#password').val();
        var rpassword=userAddModal.find('#rpassword').val();
        if(name==""||name==null){
            $("#addNameErr").html("请填写用户名称");
            return;
        }else if(phone==""||phone==null){
            $("#addPhoneErr").html("请填写手机号");
            return;
        }else if (!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(phone)){
            $("#addPhoneErr").html("请填写正确的手机号");
            return;
        }else if (password==""||password==null){
            $("#addpasswordErr").html("请输入密码");
            return;
        }else if (rpassword==""||rpassword==null){
            $("#addrpasswordErr").html("请输入确认密码");
            return;
        }else if(rpassword!=password){
            $("#addrpasswordErr").html("两次密码不一致！");
            return;
        }


        if(addusername==""||addusername==null||addusername == undefined){
            $("#adduserloginErr").html("请输入用户登录名！");
            return;
        }else {
            $("#adduserloginErr").html("");
        }
        $("#adduserloginErr").html("");
        $("#addNameErr").html("");
        $("#addPhoneErr").html("");

        var data={'name':name,'phone':phone,'gender':gender,'username':addusername,'password':password};

        $("#lodding").css("display","block");

        $.ajax({
            url:"/userManage/addUser",
            data:data,
            type:"post",
            success:function(data){
                $("#lodding").css("display","none");
                layer.msg('操作成功!',{time: 1000});
                table.ajax.reload(null,false);
                $('#userAddModal').modal('hide');
            },
            error:function(data){
                $("#lodding").css("display","none");
                $('#userAddModal').modal('hide');
            }
        });
    });
});
