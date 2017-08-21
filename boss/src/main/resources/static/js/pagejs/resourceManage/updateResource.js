/**
 * Created by qiyalm on 16/6/28.
 */

/*
修改菜单
 */
$(function(){
    //绑定修改的数据
    $("#resEditBtn").click(function(){
        var resId = $("#selectResId").val();
        if(resId==null||resId==""){
            $("#alertInfo").html("请选择菜单!")
            $("#alertModal").modal('show');
            return;
        }
        var name=$("#selectName").val();
        var code=$("#selectCode").val();
        var url=$("#selectUrl").val();
        var fatherId=$("#selectFatherId").val();
        var fatherName=$("#selectFatherName").val();


        $("#resEditModal").find("#resId").val(resId);
        $("#resEditModal").find("#name").val(name);
        $("#resEditModal").find("#code").val(code);
        $("#resEditModal").find("#url").val(url);
        $("#resEditModal").find("#fatherId").val(fatherId);
        $("#resEditModal").find("#fatherName").val(fatherName);

        $("#resEditModal").modal('show');
    });

    $("#name").blur(function(){
        var name=$(this).val();
        if (name==""||name==null){
            $("#nameMsg").html("请填写菜单名称");
        }else{
            $("#nameMsg").html("");
        }

    });

    //确认修改
    $("#editResSubmit").click(function(){
        var modal=$("#resEditModal");
        var resId=modal.find("#resId").val();
        var name=modal.find("#name").val();
        var code=modal.find("#code").val();
        var url=modal.find("#url").val();
        var fatherId=$("#fatherId").val();

        if (name==""||name==null){
            modal.find("#nameMsg").html("菜单名称不允许为空");
            return;
        }else{
            modal.find("#nameMsg").html("");
        }

        var data= {'resId':resId,'name':name,'code':code,'url':url,'fatherId':fatherId};
        $.ajax({
            url:"/resourceManage/updateResource",
            type:"post",
            data:data,
            success:function(data){
                if(data.isSuccess=="true"){
                    $('#resChange').trigger('change');
                    $('#resEditModal').modal('hide');
                    //$("#alertModal").modal('show');
                    layer.msg('操作成功!',{time: 1000});
                    return;
                }
            },
            error:function(){
                layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
            }
        })
    });
});