/**
 * Created by qiyalm on 16/6/28.
 */
/*
修改用户信息
 */
$(function(){
    var table= $("#userDataTable").DataTable();
    var editPhone;
    var editName;
    var isOK=true;
    var isUserNameOk=true;
    $("#userDataTable tbody").on('click','#updatePwd',function(){
        console.log("哈哈哈");
        rowsData=table.row($(this).parent().parent()).data();
        var userId = rowsData.id;
        var userName = rowsData.name;

        layer.confirm('是否重置用户"'+userName+'"的密码？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url:"/userManage/updatePwd",
                type:"post",
                data:{'userId':userId},
                success:function(data){
                    if(data.count>0){
                        layer.msg('重置成功!',{time: 1000});
                        return;
                    }
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                },
                error:function(data){
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                    return;
                }
            });
        }, function(){
            return;
        });
    });


    /*
     修改弹出框绑定数据
     */
    $("#userDataTable tbody").on('click','#modifyUser',function(){
        rowsData=table.row($(this).parent().parent()).data();

        var userEditModal=$("#userEditModal");
        userEditModal.find('#userId').val(rowsData.id);
        userEditModal.find('#name').val(rowsData.name);
        userEditModal.find('#phone').val(rowsData.phone);
        // userEditModal.find('#username').val(rowsData.username);

        editPhone=rowsData.phone;
        // editName=rowsData.username;
        if (rowsData.gender=="女"){
            $("#female").attr("checked", "checked");
        }else {
            $("#male").attr("checked", "checked");
        }
        // userEditModal.find('#areaDesc').val(rowsData.areaDesc);
        // userEditModal.find('#district').val(rowsData.district);

        $("#userEditModal").modal("show");
    });

    /*
     确认修改用户信息
     */
    $("#editUserSubmit").click(function(){
        if(isOK==false||isUserNameOk==false){
            return;
        }
        //获取需要修改的信息
        var userEditModal=$("#userEditModal");
        var userId= userEditModal.find('#userId').val();
        // var username= userEditModal.find('#username').val();
        var name=userEditModal.find('#name').val();
        var phone= userEditModal.find('#phone').val();  //用户原来手机号
        var gender=$('input[type="radio"][name="gender"]:checked').val(); // 获取一组radio被选中项的值

        // var areaDesc= userEditModal.find('#areaDesc').val();
        // var district = userEditModal.find('#district').val();

        if(name==""||name==null){
            $("#nameErr").html("请填写用户名称");
            return;
        }else {
            $("#nameErr").html("");
        }

        if(phone==""||phone==null){
            $("#phoneErr").html("请填写手机号");
            return;
        }else {
            $("#phoneErr").html("");
        }

       // var data={'userId':userId,'name':name,'phone':phone,'gender':gender,'areaDesc':areaDesc,'district':district};

        $.ajax({
            url:"/userManage/updateUser",
            data:{'userId':userId,'name':name,'phone':phone,'gender':gender},
            type:"post",
            success:function(data){
                if(data.count>0){
                    //$('#alertModal').modal('show');
                    layer.msg('操作成功!',{time: 1000});

                    table.ajax.reload(null,false);
                }
                $('#userEditModal').modal('hide');
            },
            error:function(data){

            }
        });

    });

    /*
     修改信息时检验手机号
     */
    $("#phone").blur(function(){
        var phone=$(this).val();
        if(phone==""||phone==null){
            $("#phoneErr").html("请填写手机号");
            return;
        }else {
            $("#phoneErr").html("");
        }

        if(editPhone==phone) {
            $("#phoneErr").html("");
            return;
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
                    $("#phoneErr").html("手机号已存在");
                    $("#phone").focus();
                }else {
                    $("#phoneErr").html("");
                    isOK=true;
                }
            },
            error:function(){
                $("#phoneErr").html("");
            }
        })
    });

    // $("#username").blur(function(){
    //     var username=$(this).val();
    //     if(username==""||username==null){
    //         $("#userloginErr").html("请输入用户登录名！");
    //         return;
    //     }else {
    //         $("#userloginErr").html("");
    //     }
    //
    //     if(editName==username) {
    //         $("#phoneErr").html("");
    //         return;
    //     }
    //
    //
    //     if ((/^[0-9]*$/).test(username)){
    //         $("#userloginErr").html("用户登录名不能是纯数字！");
    //         return;
    //     }else {
    //         $("#userloginErr").html("");
    //     }
    //
    //     $.ajax({
    //         url:"/userManage/existUserName",
    //         type:"post",
    //         data:{"username":username},
    //         success:function(data){
    //             if (!data.result){
    //                 isUserNameOk=false;
    //                 $("#userloginErr").html("用户名已存在!");
    //                 $("#username").focus();
    //             }else {
    //                 isUserNameOk=true;
    //                 $("#userloginErr").html("");
    //             }
    //         }
    //     })
    // });
    /*
     修改信息时检验用户名称
     */
    $("#name").blur(function(){
        var name=$(this).val();
        if(name==""||name==null){
            $("#nameErr").html("请填写用户名称");
        }else {
            $("#nameErr").html("");
        }
    });
});
