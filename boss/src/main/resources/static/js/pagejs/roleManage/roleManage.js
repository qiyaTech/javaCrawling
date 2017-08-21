/**
 * Created by qiyalm on 16/6/7.
 */
$(function(){
    createTable();

    $("#alertModal").on('hidden.bs.modal',function(){
        $("#alertCancel").hide();
        $("#alertInfo").html("操作成功!");
    });
});

function createTable(){
    var table=$('#roleDataTables').DataTable({
        "aLengthMenu": [[2, 10, 20, -1], [2, 10, 20, "所有"]],
        "iDisplayLength":10,
        "bFilter": true, //过滤功能(搜索)
        "bAutoWidth": true, //自动宽度
        "bLengthChange" : false,  //每页条数
        //"sDom": 'Rfrtpil',
        "ajax": {
            "url": "/roleManage/getRole",
            //"dataSrc": "data",//默认为data
            "type": "post",
            "error":function(){}
        },
        "responsive": true,
        "columns": [
            {"data":"index",title:"","defaultContent":""},
            //{ "data": "id", "title":"序号","defaultContent":"","visible":false},
            { "data": "roleName", "title":"角色名称","defaultContent":""},
            { "data": "remark", "title":"备注","defaultContent":"","width":"30%"},
            { "data": "edit", "title":"操作","defaultContent":
            "<button class='btn  btn-primary' type='button' id='modifyRole'>修改</button>    " +
            "<button class='btn btn-primary remove-btn' id='roleRemoveBtn' type='button'>删除</button>    " +
            "<button class='btn btn-primary' type='button' id='setRoleRes' >分配权限</button>"}
        ],
        sPaginationType: "full_numbers",
        //bJQueryUI: true,
        "fnDrawCallback": function ( oSettings ) {
            /* Need to redo the counters if filtered or sorted */
            if ( oSettings.bSorted || oSettings.bFiltered )
            {
                for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
                {
                    $('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
                }
            }
        },
        "createdRow": function ( row, data, index ) {
            $('td:eq(1)',row).append(
                '<input type="hidden" class="roleId" value="'+ data.id +'" />'
            );
        },
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0 ] }
        ],
        "aaSorting": [[ 1, 'asc' ]],
        "language": {
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
}

