/**
 * Created by qiyalm on 16/6/28.
 */
/*
用户分配角色
 */
var oTable;
var mapList;
$(function(){
    var table= $("#userDataTable").DataTable();

    //点列与行时触发
    $('#userRoleTb').on('click', 'tbody td,tbody td .check,thead th:first-child', function(e){
        $(this).parent().find('input[type="checkbox"]').trigger('click');
    });


    /*
     点击分配角色按钮触发
     */
    $("#userDataTable tbody").on('click','#setUserRole',function() {
        rowsData = table.row($(this).parent().parent()).data();
        //var userId=rowsData.id;

        var userId = $(this).parents('tr').find("td .userId").val();
        $.ajax({
            url:"/userManage/getUserRoleByUser",
            type:"post",
            data:{'userId':userId},
            success:function(data){
                mapList=data.map;
                $("#userRoleModal").modal('show');
                userRole();
            },
            error:function(){

            }
        })
    });


    /*
     确定分配角色
     */
    $("#editUserRole").click(function(){
        var roleIds=[];
        $('input[type="checkbox"]', "#userRoleTb").each(function() {
            var a=this;
            if (this.checked) {

                $("input[type='checkbox']").parents('tr').addClass("selected");
                roleIds.push(this.value);
            }else {
                $("input[type='checkbox']").parents('tr').removeClass("selected");
            }
        });
        var rows=oTable.rows(".selected").data();  //获取选中的行

        var userId=rowsData.id;
        var url="/userManage/updateUserRoleByUser/"+userId+"/"+roleIds;
        if(roleIds.length==0){
            url="/userManage/deleteUserRoleByUser/"+userId;
        }

        $.ajax({
            url:url,
            type:"get",
            success:function(data){
                //$('#alertModal').modal('show');
                layer.msg('操作成功!',{time: 1000});
            },
            error:function(){

            }
        })

        $("#userRoleTb").DataTable().destroy();
    });

    /*
     取消分配角色
     */
    $("#userRoleExit").click(function(){
        $("#userRoleTb").DataTable().destroy();
    });
});

/*
 分配角色Table
 */
    function userRole(){
        if(oTable==null||oTable==undefined){
        oTable=$('#userRoleTb').DataTable({
            "aLengthMenu": [[2, 10, 20, -1], [2, 10, 20, "所有"]],
            "iDisplayLength":10,
            "bFilter": true, //过滤功能(搜索)
            "bAutoWidth": true, //自动宽度
            "bLengthChange" : true,  //每页条数
            "select": true,
            "sDom": 'Rfrtpi',
            "ajax": {
                "url": "/userManage/getRole",
                "dataSrc": "data",//默认为data
                "type": "post",
                "error":function(){
                    layer.msg('网络连接不稳定,请稍后再试...',{time: 1000});
                }
            },
            "columns": [
                {"data":"index","title":"","defaultContent":""},
                { "data": "id", "title":"序号","defaultContent":"","visible":false},
                { "data": "roleName", "title":"角色名称","defaultContent":""},
                { "data": "remark", "title":"备注","defaultContent":""}
            ],
            // 'columnDefs': [{
            //     'targets': 0,
            //     'searchable':false,
            //     'orderable':false,
            //     'className': 'dt-body-center',
            //     'render': function (data, type, full, meta){
            //         return '<input type="checkbox" name="check" value=""'+ data.id +'" />';
            //     }
            // }],
            "createdRow": function ( row, data, index ) {
                $('td:eq(0)', row).append(
                    '<input type="checkbox" class="check" name="check" value="'+ data.id +'" />'
                );
            },


            'order': [[1, 'asc']],
            'rowCallback': function(row, data, dataIndex){
                if(mapList!=null||mapList!=undefined){
                    $(row).find('input[type="checkbox"]').removeAttr('checked')
                    $.each(mapList,function(index,content){//遍历JSON
                        if(content.roleId==data.id){
                            $(row).find('input[type="checkbox"]').prop('checked', true);
                        }
                    });
                }
            },

            "language": {
                "bAutoWidth": true, //自动宽度
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 条结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 条结果，共 _TOTAL_ 条",
                "sInfoEmpty": "显示第 0 至 0 条结果，共 0 条",
                "sInfoFiltered": "(由 _MAX_ 条结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "查询:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }
        });
        }else {
            oTable.ajax.reload();

        }
    }


