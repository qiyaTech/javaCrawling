/**
 * Created by qiyalm on 16/6/28.
 */
$(function(){
    var table=$('#roleDataTables').DataTable();

    $("#roleAddModal").on('shown.bs.modal',function() {
        $(this).find("#addRoleName").val("");
        $(this).find("#addRemark").val("");
    });

    //添加角色信息
    $("#addRoleSubmit").click(function() {
        //获取需要添加的信息
        var roleAddModal = $("#roleAddModal");
        var roleName = roleAddModal.find('#addRoleName').val();
        var remark = roleAddModal.find('#addRemark').val();

        if(roleName==null||roleName==""){
            roleAddModal.find($("#addRoleNameMsg")).html("角色名称不能为空");
            return;
        }else {
            roleAddModal.find($("#addRoleNameMsg")).html("");
        }

        var data={'roleName':roleName,'remark':remark};

        $.ajax({
            url:"/roleManage/addRole",
            data:data,
            type:"post",
            success:function(data){
                if(data.flag=true){
                    layer.msg('操作成功!',{time: 1000});
                }
                $('#roleAddModal').modal('hide');
                table.ajax.reload(null,false);
            },
            error:function(){

            }
        });
    });
});

