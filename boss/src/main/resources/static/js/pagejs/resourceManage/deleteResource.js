/**
 * Created by qiyalm on 16/6/28.
 */

/*
  删除菜单
 */
$(function(){
    //删除
    $("#resDelBtn").click(function(){
        var selectChild=$("#selectChild").val();
        var resId = $("#selectResId").val();
        var name=$("#selectName").val();

        if (selectChild>0){
            //console.log("哈哈")
            //$("#alertInfo").html("请先删除菜单:' "+name+" ' 下的子级菜单...");
            layer.msg("请先删除菜单:' "+name+" ' 下的子级菜单!",{time: 2000});
            return;
        }

        if(resId==null||resId==""){
            $("#alertInfo").html("请选择菜单!");
            $("#alertModal").modal('show');
            return;
        }

        layer.confirm("是否删除菜单:"+name+" ?", {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url:"/resourceManage/delResource",
                data:{'resId':resId},
                type:"post",
                success:function(data){
                    $('#resChange').trigger('change');
                    layer.msg('操作成功!',{time: 1000});
                },
                error:function(data){
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                }
            });
            layer.closeAll();
        }, function(){
            return;
        });
    });
});