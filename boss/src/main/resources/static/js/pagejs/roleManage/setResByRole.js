/**
 * Created by qiyalm on 16/6/28.
 */

$(function(){
    var rowData="";
    var table=$('#roleDataTables').DataTable();

//点击分配权限获取选中行角色信息
    $("#roleDataTables tbody").on('click','#setRoleRes',function(){
        rowData=table.row($(this).parents('tr')).data();
        var roleId=$(this).parents('tr').find("td .roleId").val();
        //console.log("角色ID:"+roleId);
        $('#roleResModal').modal("show");

        var trees;
        $.ajax({
            url:"/roleManage/getAllRes",
            data:{'roleId':roleId},
            type:"post",
            success:function(data){
                trees=data.map;
                $('#resourceTree').bind("loaded.jstree", function (e, data) {
                    $('#resourceTree').jstree("open_all") // 所有节点都打开
                }).jstree({
                    "core": {
                        "animation" : 0,
                        "check_callback" : true,
                        "themes" : { "stripes" : true },
                        'data': trees,
                        'three-state':false
                    },
                    lang: {
                        loading: "信息加载中……"  //在用户等待数据渲染的时候给出的提示内容，默认为loading
                    },
                    "plugins": [
                        "search", "checkbox"
                    ]
                });
            },
            error:function(){

            }
        })
    });

//确认分配权限
    $("#editRoleRes").click(function(){
        var resArray=[];
        resArray=$("#resourceTree").jstree('get_checked');

        $('#resourceTree').data('jstree', false);//清空数据，必须

        var url="/roleManage/updateResByRole/"+rowData.id+"/"+resArray;
        if (resArray.length==0){
            url="/roleManage/delResByRole/"+rowData.id;
        }

        $.ajax({
            url:url,
            type:"get",
            success:function(){
                //$('#alertModal').modal('show');
                layer.msg('操作成功!',{time: 1000});
            },
            error:function(){

            }
        })
    });

//菜单关闭时清空树
    $("#roleResExit").click(function(){
        $('#resourceTree').data('jstree', false);//清空数据，必须
    });

//提示信息关闭时
    $("#resourceTree").on('hidden.bs.modal',function(){
        $('#resourceTree').jstree('destroy');
    });
    $("#roleResModal").on('hidden.bs.modal',function(){
        $('#resourceTree').data('jstree', false);//清空数据，必须
        $('#resourceTree').jstree('destroy');
    });

});
