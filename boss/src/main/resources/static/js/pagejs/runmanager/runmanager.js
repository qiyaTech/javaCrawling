/**
 * Created by dengduiyi on 2017/1/3.
 */
$(window).on('load', function () {

    var curreditId = "";

    var mainrowSelection = $('#main-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange": false,
        "ordering": false,
        "aoColumnDefs": [
            {"sClass": "dt_col_hide", "aTargets": [1]}
        ],
        "columns": [
            {"data": "index", "title": "", "defaultContent": "", "width": "5%"},
            {"data": "id", "visible": false},
            {"data": "name", "title": "网站名称", "defaultContent": ""},
            // {"data": "targetUrl", "title": "目标地址", "defaultContent": ""},
            {"data": "processName", "title": "处理器", "defaultContent": ""},
            {"data": "pipeNames", "title": "输出", "defaultContent": ""},
            {"data": "totalpage", "title": "总页数", "defaultContent": ""},
            {"data": "leftpage", "title": "剩余页数", "defaultContent": ""},
            {"data": "sucesspage", "title": "成功页数", "defaultContent": ""},
            {"data": "run", "title": "状态", "defaultContent": ""},
            {
                "data": "edit",
                "title": "操作",
                "width": "10%",
                "defaultContent": ""
            }


        ],
        "serverSide": true,
        "sAjaxSource": "/runmanager/list",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            var searchData = $.extend(true, {}, []);
            searchData.size = getObjArrayValue('iDisplayLength', aoData);
            searchData.terms = [{"type": "text", "value": $("#txt_search_name").val(), "code": "name"}];
            searchData.index = getObjArrayValue('iDisplayStart', aoData) / searchData.size;
            $.ajax({
                "type": "POST",
                "contentType": "application/json",
                "url": sSource,
                "dataType": "json",
                "data": JSON.stringify(searchData), //以json格式传递
                "success": function (d) {
                    var page = d.data;
                    var data = d.data.content;

                    var returnData = {};
                    returnData.recordsTotal = page.numberOfElements;//返回数据全部记录
                    returnData.recordsFiltered = page.totalElements;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = data
                    fnCallback(returnData)
                }
            });

        },
        "createdRow": function (row, data, index) {
            if (data.run == "Running") {
                $('td:eq(8)', row).append("<button class='btn  btn-info' style='margin-right: 5px;' id='btn_stop'>停止</button>");
            }
            else {
                $('td', row).eq(8).append("<button class='btn btn-info' style='margin-right: 5px;' id='btn_start'>运行</button>");
            }

        },
        "fnDrawCallback": function (oSettings) {
            for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
                $('td:eq(0)', oSettings.aoData[oSettings.aiDisplay[i]].nTr).html(i + 1);
            }
        },
        "responsive": true,
        "language": {
            "url": "/bower_components/datatables/language.json"
        }
    });


    //click event============================================================
    $('#main-tables tbody').on('click', '#btn_start', function () {
        var data = mainrowSelection.row( $(this).parents('tr') ).data();
        var row = $(this).parents('tr');
        $.ajax({
            url: "/runmanager/run",
            data: JSON.stringify(data.id),
            type: "post",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            beforeSend: function () {
                layer.load(2);
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (data) {
                if (data.code == 0) {
                    $('td:eq(9)', row).html("<button class='btn  btn-info' style='margin-right: 5px;' id='btn_stop'>停止</button>");
                    layer.msg('操作成功', {icon: 1,time:800});
                }
                else {
                    layer.alert(data.msg);
                }

            },
            error: function (data) {
                layer.alert('网络不稳定,请重试');
            }
        });
    });

    $('#main-tables tbody').on('click', '#btn_stop', function () {
        var data = mainrowSelection.row( $(this).parents('tr') ).data();
        var row = $(this).parents('tr');
        $.ajax({
            url: "/runmanager/stop",
            data: JSON.stringify(data.id),
            type: "post",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            beforeSend: function () {
                layer.load(2);
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (data) {
                if (data.code == 0) {
                    $('td:eq(9)', row).html("<button class='btn btn-info' style='margin-right: 5px;' id='btn_start'>运行</button>");
                    layer.msg('操作成功', {icon: 1,time:800});
                }
                else {
                    layer.alert(data.msg);
                }

            },
            error: function (data) {
                layer.alert('网络不稳定,请重试');
            }
        });
    });

    $('#searchbtn').on('click', function () {
        mainrowSelection.ajax.reload()
    });


    function getObjArrayValue(name, array) {
        for (var i = 0, len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }
})