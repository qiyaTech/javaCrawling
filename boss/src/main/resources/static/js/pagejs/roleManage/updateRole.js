/**
 * Created by qiyalm on 16/6/28.
 */
$(function(){
    var rowData="";  //选中行的数据
    var table=$('#roleDataTables').DataTable();

    //修改绑定数据
    $("#roleDataTables tbody").on('click','#modifyRole',function(){
        rowData=table.row($(this).parents('tr')).data();
        var roleId=$(this).parents('tr').find("td .roleId").val();
        //console.log("角色ID:"+roleId);
        $('#roleEditModal').modal({backdrop: 'static', keyboard: false});

        var roleEditModal=$("#roleEditModal");
        roleEditModal.find('#id').val(roleId);
        roleEditModal.find('#roleName').val(rowData.roleName);
        roleEditModal.find('#remark').val(rowData.remark);

        $('#roleEditModal').modal("show");

    });

    $("#editRoleSubmit").click(function() {
        //获取需要修改的信息
        var roleEditModal = $("#roleEditModal");
        var roleId = roleEditModal.find('#id').val();
        var roleName = roleEditModal.find('#roleName').val();
        var remark = roleEditModal.find('#remark').val();

        if(roleName==null||roleName==""){
            roleEditModal.find($("#roleNameMsg")).html("角色名称不能为空");
            return;
        }else {
            roleEditModal.find($("#roleNameMsg")).html("");
        }

        var data={'roleId':roleId,'roleName':roleName,'remark':remark};

        $.ajax({
            url:"/roleManage/updateRole",
            data:data,
            type:"post",
            success:function(data){
                if(data.flag=true){
                    //$('#alertModal').modal('show');
                    layer.msg('操作成功!',{time: 1000});
                }
                $('#roleEditModal').modal('hide');
                table.ajax.reload(null,false);
            },
            error:function(){

            }
        });
    });
});