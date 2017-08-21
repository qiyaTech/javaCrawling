$(window).on('load', function () {

    console.log($("#initSourceId").val());

    var userId = $("#initUserId").val().length == 0 ? 0:$("#initUserId").val();
    var deviceId = $("#initDeviceId").val().length == 0 ? 0:$("#initDeviceId").val();
    var platform="";
    var siteId = $("#initSiteId").val().length == 0 ? 0 : $("#initSiteId").val();
    var sourceId=$("#initSourceId").val().length == 0 ? 0 : $("#initSourceId").val();


    var mainrowSelection = $('#main-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange" : false,
        "ordering":false,
        //"iDisplayLength" : 1000, //默认显示的记录数
        "columns": [
            { "data":" ","title":"","defaultContent":"","width": "5%"},
            { "data": "title", "title":"标题","defaultContent":""},
            { "data": "readCount", "title":"浏览次数","defaultContent":""},
            { "data": "lookTimes", "title":"总时长","defaultContent":""},
            { "data": "endTime", "title":"最近阅读时间","defaultContent":""},
            { "data": "publicTime", "title":"文章发布时间","defaultContent":""},
            { "data": "loveCount", "title":"喜欢次数","defaultContent":""},
            { "data": "hateCount", "title":"不喜欢次数","defaultContent":""}
        ],
        "serverSide": true,
        "sAjaxSource": "/userLook/searchDetails",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            retrieveData(sSource, aoData, fnCallback);
        },
        "fnDrawCallback": function ( oSettings ) {
            for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
            {
                $('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html(i+1);
            }
        },
        "responsive": true,
        "language": {
            "url":"/data/language.json"
        }
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
            },
            {
                "type" : "text",
                "value" : $("#deviceId").val(),
                "code" : "deviceId"
            },
            {
                "type" : "text",
                "value" : $("#userId").val(),
                "code" : "userId"
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
        userId = $("#userId").val();
        deviceId = $("#deviceId").val();
        siteId = $("#site").val();
        sourceId= $("#platform").val();

        mainrowSelection.ajax.reload();
    });

    //选择门店
    $("#choseApartment").click(function(){
        $("#chooseUser").modal("show");
    });

    $("#searchbtnfang").click(function(){
        layer.load();
        userTables.ajax.reload();
        layer.closeAll();
    });

    $("#userName").keydown(function(event){
        if(event.which==8 || event.which == 46)       //13等于回车键(Enter)键值,ctrlKey 等于 Ctrl
            $("#userId").val("0");
        $("#userName").val("");
    });

    //用户数据
    var userTables = $('#user-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange" : false,
        "ordering":false,
        "columns": [
            {"data":"","title":"选择","defaultContent":"","width": "5%"},
            { "data": "name", "title":"用户名称","defaultContent":""},
            { "data": "createtime", "title":"创建时间","defaultContent":""}
        ],
        "serverSide": true,
        "sAjaxSource": "/userLook/searchUsers",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            retrievefangData(sSource, aoData, fnCallback);
        },
        "createdRow": function ( row, data, index ) {
            $('td:eq(0)', row).append(
                '<input type="radio" name="radioname"  class="'+data.name+'" value="'+ data.id +'" />'
            );
        },
        "responsive": true,
        "language": {
            "url":"/data/language.json"
        }
    });

    function retrievefangData( sSource, aoData, fnCallback ) {
        var searchData = $.extend(true, {}, []);
        //  每页多少条数据
        searchData.size = getObjArrayValue('iDisplayLength', aoData);
        searchData.index = getObjArrayValue('iDisplayStart', aoData) / searchData.size ;
        searchData.terms = [
            {"type" : "text",  "value" : $("#searchName").val(),  "code" : "word"}
        ];

        $.ajax( {
            "type": "POST",
            "url": sSource,
            "dataType": "json",
            "contentType": "application/json",
            "data": JSON.stringify(searchData), //以json格式传递
            "success": function(d) {
                var page=d.data;
                var data = d.data.content;
                //console.log(data);
                var returnData = {};
                //returnData.draw =indexdraw++;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = page.numberOfElements;//返回数据全部记录
                returnData.recordsFiltered = page.totalElements;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = data;
                fnCallback(returnData);
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


    /*
     确定选择门店
     */
    $("#addUser").click(function(){
        var userIds= $('input[type="radio"]:checked').val();
        var userName=$('input[type="radio"]:checked').attr('class');

        //console.log("userId:"+userId+",userName:"+userName);

        $("#userId").val(userIds);
        $("#userName").val(userName);

        if (userIds.length==0){
            layer.msg('请先选择用户!',{time: 1000});
            return;
        }

        $("#deviceId").val("0");

        $('#chooseUser').modal('hide');
    });


    //导出
    $("#export").click(function(){

        layer.load(3);
        var searchData = $.extend(true, {}, []);

        var siteName = $("#site").find("option[value='"+siteId+"']").html();

        //console.log("user:"+userId+",site:"+siteId+",source:"+sourceId+",device:"+deviceId);

        //  每页多少条数据
        searchData.terms= [
            {"type" : "text",  "value" : siteId,  "code" : "siteId"},
            {"type" : "text",  "value" : sourceId,  "code" : "source"},
            {"type" : "text",  "value" : siteName,  "code" : "siteName"},
            {"type" : "text",  "value" : userId,  "code" : "userId"},
            {"type" : "text",  "value" : deviceId,  "code" : "deviceId"}
        ];

        token=(Math.random()*1024)+new Date().getTime();
        //console.info(JSON.stringify(searchData));
        var url= "/userLook/detailExport?exportToken="+token+"&&sc="+JSON.stringify(searchData);
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
