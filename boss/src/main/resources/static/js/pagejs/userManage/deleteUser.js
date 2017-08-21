/**
 * Created by qiyalm on 16/6/28.
 */
/*
 删除用户
 */
$(function(){
    var table= $("#userDataTable").DataTable();

    //确认删除
    $("#userDataTable tbody").on('click','.remove-btn',function(){
        rowsData=table.row($(this).parent().parent()).data();
        var userId=$(this).parents('tr').find("td .userId").val();
        var row=$(this);

        layer.confirm("是否停用用户:"+rowsData.name+" ?", {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url:"/userManage/delUser",
                data:{'userId':userId},
                type:"post",
                success:function(data){
                    if(data.flag=true){
                        table.row(row.parents('tr')).remove().draw();
                        layer.msg('操作成功!',{time: 1000});
                    }
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