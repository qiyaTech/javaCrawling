$(function(){
    createUserTable();

    $("#alertModal").on('hidden.bs.modal',function(){
       $("#alertCancel").hide();
        $("#alertInfo").html("操作成功!")
    });
});

/*
 用户基本信息Table
 */
function createUserTable(){
    var table=$('#userDataTable').DataTable({
        "aLengthMenu": [[2, 10, 20, -1], [2, 10, 20, "所有"]],
        "iDisplayLength":10,
        "bFilter": true, //过滤功能(搜索)
        "bAutoWidth": true, //自动宽度
        "bLengthChange" : false,  //每页条数
        //"sDom": 'Rfrtpil',
        "ajax": {
            "url": "/userManage/getAllUsers",
            "dataSrc": "data",    //默认为data,这里是设置下面列数据的名称
            "type": "get",
            "error":function(){}
        },
        "responsive": true,
        "columns": [
            {"data":"index",title:"","defaultContent":""},
            //{ "data": "id", "title":"序号","defaultContent":"","visible":false},
            { "data": "name", "title":"姓名","defaultContent":""},
            { "data": "phone", "title":"电话","defaultContent":""},
            { "data": "username", "title":"登录名","defaultContent":""},
            { "data": "gender", "title":"性别","defaultContent":""},
            // { "data": "areaDesc", "title":"所在地区","defaultContent":""},
            // { "data": "district", "title":"所属街道","defaultContent":""},
            { "data": "edit", "title":"操作","defaultContent":
            "<button class='btn btn-primary' type='button' id='modifyUser'>修改</button>  " +
            "<button class='btn btn-primary remove-btn' id='userRemoveBtn' type='button'>停用</button>  "+
            "<button class='btn btn-primary' type='button' id='updatePwd'>重置密码</button>  "+
            "<button class='btn btn-primary' type='button' id='setUserRole'>分配角色</button>"}
        ],
        sPaginationType: "full_numbers",
        bJQueryUI: true,
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
                '<input type="hidden" class="userId" value="'+ data.id +'" />'
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