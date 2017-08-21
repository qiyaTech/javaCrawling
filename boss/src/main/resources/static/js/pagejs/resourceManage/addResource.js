/**
 * Created by qiyalm on 16/6/28.
 */

/*
添加菜单
 */
$(function(){
    var isOk=false;

    $(".downImg").click(function(){
        //console.log("哈啊哈1");
    });

    $("#resAddModal").on('shown.bs.modal',function() {
        var fatherId=$("#selectResId").val();
        var fatherName=$("#selectName").val();
        $(this).find("#addFatherId").val(fatherId);
        $(this).find("#addFatherName").val(fatherName);
        $(this).find("#addName").val("");
        $(this).find("#addUrl").val("");
        $(this).find("#addCode").val("");
    });

    $("#addCode").blur(function(){
        var code=$(this).val();
        if(code==""||code==null){
            $("#addCodeMsg").html("请填写菜单编号");
            return;
        }else {
            $("#addCodeMsg").html("");
        }

        $.ajax({
            url:"/resourceManage/existCode",
            type:"post",
            data:{'code':code},
            success:function(data){
                if (!data.result){
                    $("#addCodeMsg").html("菜单编号已存在");
                    $("#addCode").focus();
                    isOk=false;
                }else{
                    isOk=true;
                    $("#addCodeMsg").html("");
                }
            }
        })

    });

    $("#addName").blur(function(){
        var name=$(this).val();
        if (name==""||name==null){
            $("#addNameMsg").html("请填写菜单名称");
            return;
        }else {
            $("#addNameMsg").html("");
        }
    });


    //确认添加菜单
    $("#addResSubmit").click(function(){
        var modal=$("#resAddModal");
        var name=modal.find("#addName").val();
        var code=modal.find("#addCode").val();
        var url=modal.find("#addUrl").val();
        var fatherId=modal.find("#addFatherId").val();
        if (fatherId==null||fatherId==""){
            fatherId=0;
        }

        if (name==""||name==null){
            $("#addNameMsg").html("请填写菜单名称");
            return;
        }else{
            modal.find("#addNameMsg").html("");
        }

        if (isOk==false){
            $("#addCode").focus();
            return;
        }

        if (code==""||code==null){
            $("#addCodeMsg").html("请填写菜单编号");
            return;
        }else {
            modal.find("#addCodeMsg").html("");
        }

        var data={'name':name,'code':code,'url':url,'fatherId':fatherId};
        $.ajax({
            url:"/resourceManage/addResource",
            type:"post",
            data:data,
            success:function(data){
                if(data.isSuccess=="true"){
                    $('#resAddModal').modal('hide');
                    //$('#alertModal').modal('show');
                    layer.msg('操作成功!',{time: 1000});
                    $('#resChange').trigger('change');
                }
            },
            error:function(){
                layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
            }
        })

    });
});
