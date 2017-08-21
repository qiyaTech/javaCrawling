/**
 * Created by dengduiyi on 2017/1/3.
 */
$(window).on('load', function () {

    var curreditId = "";
    var curreditheaderrow;
    var curreditelementrow;

    var mainrowSelection = $('#main-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange": false,
        "ordering": false,
        "aoColumnDefs": [
            {"sClass": "dt_col_hide", "aTargets": [1]}
        ],
        "columns": [
            {"data": "index", "title": "序号", "defaultContent": "", "width": "5%"},
            {"data": "id", "visible": false},
            {"data": "name", "title": "网站名称", "defaultContent": ""},
            {"data": "targetUrl", "title": "目标地址", "defaultContent": ""},
            {"data": "processName", "title": "处理器", "defaultContent": ""},
            {"data": "pipeNames", "title": "输出", "defaultContent": ""},
            {
                "data": "edit",
                "title": "操作",
                "width": "5%",
                "defaultContent": " <button class='btn btn-primary ' id='editDialog' >修改</button> "
            },


        ],
        "serverSide": true,
        "sAjaxSource": "/ruleconfig/list",//获取数据的url
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

    var headertables = $('#header-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange": false,
        "ordering": false,
        "columns": [
            {"data": "type", "title": "类型", "defaultContent": ""},
            {"data": "name", "title": "名称", "defaultContent": ""},
            {"data": "value", "title": "值", "defaultContent": "", "width": "70%"},
            {
                "data": "edit",
                "title": "操作",
                "width": "10%",
                "defaultContent": " <button  class='btn btn-info'   id='btn_header_tables_edit' >修改</button> " +
                "<button  class='btn btn-danger'   id='btn_header_tables_delete' >删除</button>"
            }
        ],
        "responsive": true,
        "language": {
            "url": "/bower_components/datatables/language.json"
        }
    });

    var elementtables = $('#element-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange": false,
        "ordering": false,
        "columns": [
            {"data": "name", "title": "名称", "defaultContent": ""},
            {"data": "value", "title": "值", "defaultContent": ""},
            {
                "data": "edit",
                "title": "操作",
                "width": "5%",
                "defaultContent": " <button  class='btn btn-info' id='btn_element_tables_edit' >修改</button> " +
                " <button  class='btn btn-danger' id='btn_element_tables_delete' >删除</button> "
            }


        ],
        "responsive": true,
        "language": {
            "url": "/bower_components/datatables/language.json"
        }
    });

    $('#element-tables,#header-tables').wrap('<div class="dataTables_scroll" style="height: 100px" />');
    // $('#main-tables').wrap('<div class="dataTables_scroll" style="height: 100px" />');

    $('#pipe-multiselect').chosen({width: '100%'});

    $('#btn_addheader').on('click', function () {

        if ($('#btn_addheader').text() == "修改") {
            var data = headertables.row(curreditheaderrow).data();
            data.name = $('#txt_header_name').val();
            data.value = $('#txt_header_value').val();
            data.type = $("#sel_header_type option:selected").val();
            headertables.row(curreditheaderrow).data(data).draw(false);
        }
        else {
            headertables.row.add({
                "name": $('#txt_header_name').val(),
                "value": $('#txt_header_value').val(),
                "type": $("#sel_header_type option:selected").val()
            }).draw(false);
        }


    });

    $('#btn_addelement').on('click', function () {


        if ($('#btn_addelement').text() == "修改") {
            var data = elementtables.row(curreditelementrow).data();
            data.name = $('#txt_element_name').val();
            data.value = $('#txt_element_value').val();
            elementtables.row(curreditelementrow).data(data).draw(false);
        }
        else {
            elementtables.row.add({
                "name": $('#txt_element_name').val(),
                "value": $('#txt_element_value').val()
            }).draw(false);
        }


    });

    //start===============================初始化增加弹出框===========================
    $.ajax({
        url: "/ds/getlist/RuleProcessEnum",
        data: "",
        type: "post",
        success: function (data) {
            var output = [];
            $.each(data.data, function (key, value) {
                $('#sel_process')
                    .append($("<option></option>")
                        .attr("value", value.value)
                        .text(value.name));

            });
            $('#sel_process').selectpicker('refresh');
        },
        error: function (data) {
            layer.alert('网络不稳定,请重试');
        }
    });

    $.ajax({
        url: "/ds/getlist/RulePipeEnum",
        data: "",
        type: "post",
        success: function (data) {
            var output = [];
            $.each(data.data, function (key, value) {
                $('#pipe-multiselect')
                    .append($("<option></option>")
                        .attr("value", value.value)
                        .text(value.name));

            });
            $("#pipe-multiselect").trigger("chosen:updated");

        },
        error: function (data) {
            layer.alert('网络不稳定,请重试');
        }
    });
    //end============================================================


    //click event============================================================
    //修改process change event
    $('#sel_process').on('change', function () {

        if (curreditId != "") {
            return;
        }
        var selected = $("#sel_process option:selected").val();

         $.getJSON("/js/pagejs/ruleconfig/"+selected + ".json", function (data) {

            $("#txt_name").val(data.name);
            $("#txt_target_url").val(data.targetUrl);
            $("#txt_list_xpath").val(data.listXpath);
            $("#txt_detail_url").val(data.detailUrl);
            $("#txt_domain").val(data.domain);
            $('#pipe-multiselect').val(JSON.parse(data.pipeNames)).trigger('chosen:updated');

            elementtables.clear().draw(false);
            $.each(JSON.parse(data.elements), function (key, value) {
                elementtables.row.add(value).draw(false);
            });

            headertables.clear().draw(false);
            $.each(JSON.parse(data.headers), function (key, value) {
                headertables.row.add(value).draw(false);
            });
        }).fail(function () {
            layer.alert('网络不稳定,请重试');
        })

    });
    //修改
    $('#main-tables tbody').on('click', '#editDialog', function () {
        var data = mainrowSelection.row($(this).parents('tr')).data();
        // console.log(JSON.stringify(data));
        $("#addmodal .modal-title").html("修改");
        $("#addmodal .modal-footer .btn-primary").html("修改");
        $("#txt_name").val(data.name);
        $("#txt_target_url").val(data.targetUrl);
        $("#txt_list_xpath").val(data.listXpath);
        $("#txt_detail_url").val(data.detailUrl);
        $("#txt_domain").val(data.domain);
        $('#sel_process').val(data.processName);
        $('#sel_process').selectpicker('refresh');
        $('#pipe-multiselect').val(JSON.parse(data.pipeNames)).trigger('chosen:updated');

        elementtables.clear().draw(false);
        $.each(JSON.parse(data.elements), function (key, value) {
            elementtables.row.add(value).draw(false);
        });

        headertables.clear().draw(false);
        $.each(JSON.parse(data.headers), function (key, value) {
            headertables.row.add(value).draw(false);
        });


        curreditId = data.id;

        $("#addmodal").modal();
    });

    //增加
    $('#btn_main_add').on('click', function () {
        $("#addmodal .modal-title").html("新增");
        $("#addmodal .modal-footer .btn-primary").html("增加");

        curreditId = "";

        $("#addmodal").modal();
    });

    //元素修改
    $('#element-tables tbody').on('click', '#btn_element_tables_edit', function () {
        var data = elementtables.row($(this).parents('tr')).data();
        $("#addelementmodal .modal-title").html("修改");
        $("#addelementmodal .modal-footer .btn-primary").html("修改");
        $("#txt_element_name").val(data.name);
        $("#txt_element_value").val(data.value);

        curreditelementrow = $(this).parents('tr');

        $("#addelementmodal").modal();
    });

    //头元素修改
    $('#header-tables tbody').on('click', '#btn_header_tables_edit', function () {
        var data = headertables.row($(this).parents('tr')).data();
        $("#addheadermodal .modal-title").html("修改");
        $("#addheadermodal .modal-footer .btn-primary").html("修改");
        $("#sel_header_type").val(data.type);
        $('#sel_header_type').selectpicker('refresh');
        $("#txt_header_name").val(data.name);
        $("#txt_header_value").val(data.value);

        curreditheaderrow = $(this).parents('tr');

        $("#addheadermodal").modal();
    });

    //头部增加
    $('#tab_btn_header_add').on('click', function () {
        $("#addheadermodal .modal-title").html("添加头部");
        $("#addheadermodal .modal-footer .btn-primary").html("添加");


        $("#addheadermodal").modal();
    });

    //头表格的删格
    $('#header-tables tbody').on('click', '#btn_header_tables_delete', function () {
        headertables.row($(this).parents('tr')).remove().draw();
    });

    //元素表格的删格
    $('#element-tables tbody').on('click', '#btn_element_tables_delete', function () {
        elementtables.row($(this).parents('tr')).remove().draw();
    });

    $('#searchbtn').on('click', function () {
        mainrowSelection.ajax.reload()
    });
    $('#btn_add').on('click', function () {

        var process = $("#sel_process option:selected").val();

        var pipeArray = [];
        $('#pipe-multiselect :selected').each(function (i, selected) {
            pipeArray[i] = $(selected).val();
        });

        var elementArray = [];
        elementtables.rows().every(function (rowIdx, tableLoop, rowLoop) {
            elementArray.push(this.data());
        });

        var headerArray = [];
        headertables.rows().every(function (rowIdx, tableLoop, rowLoop) {
            headerArray.push(this.data());
        });

        if ($("#txt_name").val() == "" || $("#txt_target_url").val() == "" || pipeArray.length == 0 || process == "" || elementArray.length == 0 ||
            $("#txt_domain").val() == "" || $("#txt_detail_url").val() == "" || $("#txt_list_xpath").val() == "") {
            layer.alert('必输项不能为空');
            return;
        }

        var params = {};
        params.name = $("#txt_name").val();
        params.targetUrl = $("#txt_target_url").val();
        params.processName = process;
        params.pipeNames = JSON.stringify(pipeArray);
        params.headers = JSON.stringify(headerArray);
        params.elements = JSON.stringify(elementArray);
        params.detailUrl = $("#txt_detail_url").val();
        params.listXpath = $("#txt_list_xpath").val();
        params.domain = $("#txt_domain").val();
        params.id = curreditId;

        $.ajax({
            url: "/ruleconfig/add",
            data: JSON.stringify(params),
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
                    if (curreditId == "") {
                        layer.msg('添加成功', {icon: 1, time: 800});
                    }
                    else {
                        layer.msg('修改成功', {icon: 1, time: 800});
                    }

                    mainrowSelection.ajax.reload()
                    // $('#addmodal').modal().hide();
                    $('#addmodal').modal('toggle');
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


    function getObjArrayValue(name, array) {
        for (var i = 0, len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }
})