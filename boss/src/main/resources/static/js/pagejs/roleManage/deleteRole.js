/**
 * Created by qiyalm on 16/6/28.
 */

$(function(){
    var rowData="";
    var table=$('#roleDataTables').DataTable();

    //删除角色
    $("#roleDataTables tbody").on('click','.remove-btn',function(){
        rowData=table.row($(this).parents('tr')).data();
        var row=$(this);
        var roleId=$(this).parents('tr').find("td .roleId").val();

        layer.confirm("是否删除角色: "+rowData.roleName+" ?", {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url:"/roleManage/delRole",
                data:{'roleId':roleId},
                type:"post",
                success:function(data){
                    //console.log(data.flag);

                    if(data.flag == true){
                        layer.msg("操作成功!",{time: 2000});
                        table.row(row.parents('tr')).remove().draw();
                    }else {
                        var str = data.showMsg;
                        //console.log(str);
                        layer.msg(str,{time: 2000});
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


        //$("#alertInfo").html("是否删除角色:"+rowData.roleName+" ?");
        //$("#alertCancel").show();
        //$('#alertModal').modal('show');
        //var row=$(this);
        //
        //var roleId=$(this).parents('tr').find("td .roleId").val();
        ////console.log("角色ID:"+roleId);
        //
        //$("#alertSure").click(function(){
        //
        //    $.ajax({
        //        url:"/roleManage/delRole",
        //        data:{'roleId':roleId},
        //        type:"post",
        //        success:function(data){
        //            //console.log(data.flag);
        //
        //            if(data.flag == true){
        //                layer.msg("操作成功!",{time: 2000});
        //                table.row(row.parents('tr')).remove().draw();
        //            }else {
        //                var str = data.showMsg;
        //                //console.log(str);
        //                layer.msg(str,{time: 2000});
        //            }
        //        },
        //        error:function(data){
        //            console.log(data);
        //        }
        //    });
        //});
    });
});