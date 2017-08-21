/**
 * Created by dengduiyi on 2017/1/3.
 */
$(window).on('load', function () {

    var curreditId = "";
    var firstLoad = true;

    //操作按钮栏行号
    var num = 9;
    //状态栏行号
    var stutascoluml = 6;


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
            {"data": "id", "defaultContent": ""},
            {"data": "siteName", "title": "网站名称", "defaultContent": ""},
            {"data": "taskName", "title": "任务名称", "defaultContent": ""},
            {"data": "processName", "title": "处理器", "defaultContent": ""},
            {"data": "pipeName", "title": "输出", "defaultContent": ""},
            {"data": "run", "title": "监控状态", "defaultContent": ""},
            {"data": "runTaskResult", "title": "需手动执行", "defaultContent": ""},
            {"data": "timerTaskResult", "title": "定时任务", "defaultContent": ""},
            {
                "data": "edit",
                "title": "操作",
                "width": "10%",
                "defaultContent": ""
            }
        ],
        "serverSide": true,
        "sAjaxSource": "/taskManager/list",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            var searchData = $.extend(true, {}, []);
            searchData.size = getObjArrayValue('iDisplayLength', aoData);
            searchData.terms = [{"type": "text", "value": $("#txt_search_name").val(), "code": "taskName"}];
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
                    returnData.data = data;
                    fnCallback(returnData);
                }, "error": function (d) {

                    layer.closeAll();
                     layer.msg('网络错误！');
                }, "beforeSend": function () {
                    layer.load(2);
                }, "complete": function () {
                    layer.closeAll();
                }
            });

        },
        "fnDrawCallback": function (oSettings) {

            for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
                $('td:eq(0)', oSettings.aoData[oSettings.aiDisplay[i]].nTr).html(i + 1);
            }
        },
        "createdRow": function (row, data, index) {
            if (data.run == 'Running') {
                //console.log("run");
                $('td:eq(' + num + ')', row).append("<button class='btn  btn-info' style='margin-right: 5px;' id='btn_stop'>停止</button>"
                    + "<button class='btn  btn-info rinseArticle' type='button'>清洗</button>");
            }
            else {
                //console.log("stop");
                $('td:eq(' + num + ')', row).append("<button class='btn btn-info' style='margin-right: 5px;' id='btn_start'>运行</button>"
                    + " <button class='btn  btn-info rinseArticle' type='button'>清洗</button>");
            }
        },
        "responsive": true,
        "language": {
            "url": "/bower_components/datatables/language.json"
        }
    });


    //click event============================================================
    $('#main-tables tbody').on('click', '#btn_start', function () {
        var data = mainrowSelection.row($(this).parents('tr')).data();
        var row = $(this).parents('tr');
        $.ajax({
            url: "/taskManager/start",
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
                if (data.data.task.runState == 'running') {
                    layer.msg('操作成功', {icon: 1, time: 800});
                    $('td:eq(' + num + ')', row).html("<button class='btn  btn-info' style='margin-right: 5px;' id='btn_stop'>停止</button> <button class='btn  btn-info rinseArticle' type='button'>清洗</button>");
                    $('td:eq(' + stutascoluml + ')', row).html('Running')


                    // mainrowSelection.ajax.reload();


                }
                else {
                    layer.alert('网络不稳定,请重试');
                }

            },
            error: function (data) {
                layer.alert('网络不稳定,请重试');
            }
        });
    });

    $('#main-tables tbody').on('click', '#btn_stop', function () {
        var data = mainrowSelection.row($(this).parents('tr')).data();
        var row = $(this).parents('tr');
        $.ajax({
            url: "/taskManager/stop",
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
                if (data.data.success == 1) {
                    layer.msg('操作成功', {icon: 1, time: 800});
                    $('td:eq(' + num + ')', row).html("<button class='btn  btn-info' style='margin-right: 5px;' id='btn_start'>开始</button><button class='btn  btn-info rinseArticle' type='button'>清洗</button>");
                    //  Stopped  Running
                    $('td:eq(' + stutascoluml + ')', row).html('Stopped')

                }
                else {
                    layer.alert('网络不稳定,请重试');
                }

            },
            error: function (data) {
                layer.alert('网络不稳定,请重试');
            }
        });
    });
    $('#btn_test_circle').click(function () {

        $.ajax({
            url: "/taskManager/circleStart",
            data: {},
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
                    layer.alert('定时任务队列爬取已启动');
                }
                else {
                    layer.alert('网络不稳定,请重试');
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


    var article_tables;
    $("#main-tables tbody").on('click', '.rinseArticle', function () {

        rowsData = mainrowSelection.row($(this).parent().parent()).data();
        console.info(rowsData);
        $("#am_siteid").val(rowsData.siteId)
        $("#am_taskid").val(rowsData.id)

        $("#articleModal").modal("show");


        if (article_tables == undefined) {
            article_tables = $('#article-tables').DataTable({
                "bAutoWidth": true,
                "bFilter": false,
                "bLengthChange": false,
                "ordering": false,
                "aoColumnDefs": [
                    {"sClass": "dt_col_hide", "aTargets": [1]}
                ],

                "columns": [
                    {
                        "data": "index",
                        "title": "  <input type='checkbox' class='checkall' id='amcheckall'   />",
                        "defaultContent": "",
                        "width": "5%"
                    },
                    {"data": "id", "defaultContent": ""},
                    {"data": "siteName", "title": "站点", "defaultContent": ""},
                    // {"data": "author", "title": "作者", "defaultContent": ""},
                    {"data": "title", "title": "标题", "defaultContent": ""},
                    {"data": "", "title": "链接", "defaultContent": ""},
                    {"data": "createTimeStr", "title": "创建时间", "defaultContent": ""}

                ],
                "serverSide": true,
                "sAjaxSource": "/taskManager/articlelist",//获取数据的url
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var searchData = $.extend(true, {}, []);
                    searchData.size = getObjArrayValue('iDisplayLength', aoData);
                    searchData.terms = [
                        {"type": "dropdown", "value": $("#am_siteid").val(), "code": "siteId"},
                    ];
                    if ($("#am_title").val() != "") {
                        searchData.terms.push({
                            "type": "text", "value": $("#am_title").val(), "code": "title"
                        });
                    }
                    if (($("#am_starttime").val() != "" || $("#am_endtime").val() != "")) {
                        searchData.terms.push({
                            "type": "dateRange",
                            "value": [$("#am_starttime").val(), $("#am_endtime").val()],
                            "code": "createTime"
                        })
                    }

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
                            returnData.data = data;
                            fnCallback(returnData);
                        }, "error": function (d) {
                            layer.closeAll();
                            layer.msg('网络错误！');
                        }, "beforeSend": function () {
                            layer.load(2);
                        }, "complete": function () {
                            layer.closeAll();
                        }
                    });

                },
                "fnDrawCallback": function (oSettings) {

                    // for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
                    //     $('td:eq(0)', oSettings.aoData[oSettings.aiDisplay[i]].nTr).html(i + 1);
                    // }
                },
                "createdRow": function (row, data, index) {
                    $('td:eq(0)', row).append(
                        '<input type="checkbox" class="check" name="am_check" value="' + data.id + '" />'
                    );
                    $('td:eq(4)', row).append(
                        '<a  target="_blank"  href="http://daydayup.qiyadeng.com/?detailId=' + data.id + '#redirec/home_aritic_detail"  >查看</a>'
                    );


                },
                "responsive": true,
                "language": {
                    "url": "/bower_components/datatables/language.json"
                }
            });
        } else {
            article_tables.ajax.reload()
        }


    });


    $('#article-tables').on('click', 'tbody td,tbody td .check ', function (e) {
        $(this).parent().find('input[type="checkbox"]').trigger('click');
    });
    $('#article-tables').on('change', '#amcheckall', function (e) {
        if (this.checked) {
            $('#article-tables').find('td input[type="checkbox"]').prop('checked', true);

        } else {
            $('#article-tables').find('td input[type="checkbox"]').removeAttr('checked');
        }


    });

    $("#btn_rinse").click(function () {
        var aids = [];
        $('td input[type="checkbox"]', "#article-tables").each(function () {
            if (this.checked) {
                $("input[type='checkbox']").parents('tr').addClass("selected");
                aids.push(this.value);
            }
        });
        if (aids.length == 0) {
            layer.alert('请选择需要清洗的文章');
        } else {
            layer.confirm("是否确定清洗当前选择的 " + aids.length + "篇文章？", {
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.closeAll('dialog');
                var url = "/taskManager/rinseById/" + $("#am_taskid").val() + "/" + aids;
                $.ajax({
                    "type": "POST",
                    "contentType": "application/json",
                    "url": url,
                    "dataType": "json",
                    "data": {}, //以json格式传递
                    "success": function (d) {
                        layer.msg('已开始清洗!', {time: 1000});
                    }, "error": function (d) {
                        layer.closeAll();
                        layer.msg('网络错误！');
                    }, "beforeSend": function () {
                        layer.load(2);
                    }, "complete": function () {
                        layer.closeAll();
                    }


                });
            }, function () {

            });
        }

    });
    $("#btn_rinseAll").click(function () {

        layer.confirm("是否确定清洗当前查询所有文章？", {
            btn: ['确定', '取消'] //按钮
        }, function () {
            layer.closeAll('dialog');

            var searchData = $.extend(true, {}, []);
            searchData.index = 0;
            searchData.size = 99999
            searchData.terms = [
                {"type": "dropdown", "value": $("#am_siteid").val(), "code": "siteId"},
            ];
            if ($("#am_title").val() != "") {
                searchData.terms.push({
                    "type": "text", "value": $("#am_title").val(), "code": "title"
                });
            }
            if (($("#am_starttime").val() != "" || $("#am_endtime").val() != "")) {
                searchData.terms.push({
                    "type": "dateRange",
                    "value": [$("#am_starttime").val(), $("#am_endtime").val()],
                    "code": "createTime"
                })
            }

            $.ajax({
                "type": "POST",
                "contentType": "application/json",
                "url": "/taskManager/rinseBySearchCondition/" + $("#am_taskid").val(),
                "dataType": "json",
                "data": JSON.stringify(searchData), //以json格式传递
                "success": function (d) {
                    layer.msg('已开始清洗!', {time: 1000});
                }, "error": function (d) {
                    layer.closeAll();
                    layer.msg('网络错误！');
                }, "beforeSend": function () {
                    layer.load();
                }, "complete": function () {
                    layer.closeAll();
                }
            });
        }, function () {

        });


    });

    $('#searcharticlebtn').click(function () {
        article_tables.ajax.reload();
    });
    function getObjArrayValue(name, array) {
        for (var i = 0, len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }
})