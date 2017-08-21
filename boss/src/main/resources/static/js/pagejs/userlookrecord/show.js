$(window).on('load', function () {

    var userId = 0;
    var deviceId = 0;
    var platform="";
    var siteId = 0;
    var sourceId=0;

    var mainrowSelection = $('#main-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange" : false,
        "ordering":false,
        "iDisplayLength" : 1000, //默认显示的记录数
        "columns": [
            { "data":" ","title":"","defaultContent":"","width": "5%"},
            { "data": "source", "title":"平台","defaultContent":""},
            { "data": "name", "title":"用户名","defaultContent":""},
            { "data": "deviceId", "title":"设备标识","defaultContent":""},
            { "data": "articleCount", "title":"阅读篇数","defaultContent":""},
            { "data": "lookTimes", "title":"阅读时长","defaultContent":""},
            { "data": "firstTime", "title":"开始阅读时间","defaultContent":""},
            { "data": "endTime", "title":"最近阅读时间","defaultContent":""},
            { "data": "loveCount", "title":"喜欢篇数","defaultContent":""},
            { "data": "hateCount", "title":"不喜欢篇数","defaultContent":""}
        ],
        "serverSide": true,
        "sAjaxSource": "userLook/searchTotal",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            retrieveData(sSource, aoData, fnCallback);
        },
        "fnDrawCallback": function ( oSettings ) {
            for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
            {
                $('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( '<span class="textBorder">'+(i+1)+'</span>' );
            }
        },
        "createdRow": function ( row, data, index ) {
            //城市Id
            $('td:eq(1)',row).append(
                '<input type="hidden" class="userId" value="'+ data.userId +'" />'
            );
        },
        "responsive": true,
        "language": {
            "url":"/data/language.json"
        }
    });


    $("#main-tables tbody").on('mouseover','tr',function(){
        console.log("hover");
        $(this).css("cursor","pointer");
    });

    //跳到详细页
    $("#main-tables tbody").on('click','tr td',function(){
        var rowsData=mainrowSelection.row($(this).parent()).data();

        userId = rowsData.userId;
        deviceId = rowsData.deviceId;
        platform = rowsData.source;
        siteId = $("#site").val();

        var url = "/userLook/details?userId="+userId+"&&deviceId="+deviceId+
            "&&platform="+platform+"&&siteId="+siteId;

        var win = window.open(url, '_blank');
        win.focus();
    });

    function retrieveData( sSource, aoData, fnCallback ) {

        var searchData = $.extend(true, {}, []);
        //  每页多少条数据
        searchData.size = getObjArrayValue('iDisplayLength', aoData);
        searchData.index = getObjArrayValue('iDisplayStart', aoData) / searchData.size ;
        searchData.terms = [
            {
                "type" : "text",
                "value" : $("#site").val(),
                "code" : "siteId"
            },
            {
                "type" : "text",
                "value" : $("#platform").val(),
                "code" : "source"
            }
        ];


        $.ajax( {
            "type": "POST",
            "contentType": "application/json",
            "url": sSource,
            "dataType": "json",
            "data": JSON.stringify(searchData), //以json格式传递
            "success": function(d) {
                var page=d.data;
                var data = d.data.content;
                var returnData = {};
                //returnData.draw =indexdraw++;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = page.numberOfElements;//返回数据全部记录
                returnData.recordsFiltered = page.totalElements;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = data
                fnCallback(returnData)
            },"error":function (d) {
                layer.msg('网络错误！');
                layer.closeAll()
            },"beforeSend":function () {
                layer.load();
            },"complete":function () {
                layer.closeAll();
            }
        });
    }
    function getObjArrayValue(name, array) {
        for (var i = 0 , len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }

    $("#searchbtn").click(function(){
        siteId = $("#site").val();
        sourceId = $("#platform").val();
        mainrowSelection.ajax.reload();
    });


    //导出
    $("#export").click(function(){

        layer.load(3);
        var searchData = $.extend(true, {}, []);

        var siteName = $("#site").find("option[value='"+siteId+"']").html();

        //  每页多少条数据
        searchData.terms= [
            {"type" : "text",  "value" : siteId,  "code" : "siteId"},
            {"type" : "text",  "value" : sourceId,  "code" : "sourceId"},
            {"type" : "text",  "value" : siteName,  "code" : "siteName"}
        ];

        token=(Math.random()*1024)+new Date().getTime();
        //console.info(JSON.stringify(searchData));
        var url= "/userLook/totalExport?exportToken="+token+"&&sc="+JSON.stringify(searchData);
        $("#hidden_iframe").attr('src',url);
        //清理一次，下面再执行
        var test = setInterval(function(){
            var  exportToken=  $.cookie('exportToken');
            if(token==exportToken){
                layer.closeAll();
                clearInterval(test);
            }

        },1000);


    });
});
