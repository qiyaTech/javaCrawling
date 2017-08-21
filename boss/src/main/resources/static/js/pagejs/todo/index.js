$(window).on('load', function () {



    var mainrowSelection = $('#main-tables').DataTable({
        "bAutoWidth": true,
        "bFilter": false,
        "bLengthChange" : false,
        "ordering":false,
        "columns": [
            {"data":"id","title":"id","defaultContent":"","width": "5%"},
            // { "data": "id", "title":"序号","defaultContent":"","visible":false},
            { "data": "workName", "title":"流程类型","defaultContent":""},
            { "data": "currentStatus", "title":"当前流程环节","defaultContent":""},
            { "data": "businessDate", "title":"业务发生时间","defaultContent":""},
            { "data": "createtime", "title":"工单创建时间","defaultContent":""},
            { "data": "edit", "title":"操作","defaultContent":""},


        ],
        "serverSide": true,
        // "ajax": "/data/todo.json",
        "sAjaxSource": "/todo/list",//获取数据的url
        "fnServerData": function (sSource, aoData, fnCallback) {
            retrieveData(sSource, aoData, fnCallback);
        },

        "createdRow": function ( row, data, index ) {

            $('td:eq(5)', row).append("  <a  href='/todo/details/"+data.id+"' class='btn btn-primary   ' >处理工单 " +
                "</a> <button class='btn btn-primary  '>查看历史</button> " +
                "<button class='btn btn-primary   '>查看流程图</button>");


        },
        "fnDrawCallback": function ( oSettings ) {
            // for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
            // {
            //     $('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
            // }
        },
        "responsive": true,
        "language": {
            "url":"/data/language.json"
        }
    });

    $("#searchbtn").click(function(){
        mainrowSelection.ajax.reload()
    })
    function retrieveData( sSource, aoData, fnCallback ) {


      //  var searchData = $.extend(true, {}, searchTerms);
        //  每页多少条数据
      //  searchData.size = heituUtils.getObjArrayValue('iDisplayLength', aoData);
       // self.setSize(searchData.size);
        //  请求第几页数据  初始iDisplayStart为0
      //  searchData.index = getObjArrayValue('iDisplayStart', aoData) / searchData.siz);
        //将客户名称加入参数数组
     //   aoData.push( { "name": "customerName", "start": $("#starttime").val(),"end": $("#endtime").val() } );


        // var self = this;
        var searchData = $.extend(true, {}, []);
        //  每页多少条数据
        searchData.size = getObjArrayValue('iDisplayLength', aoData);
        searchData.terms= [{"type" : "dateRange",  "value" : [  $("#starttime").val(),  $("#endtime").val()  ],  "code" : "businessDate"    }];

       // self.setSize(searchData.size);
        //  请求第几页数据  初始iDisplayStart为0
        searchData.index = getObjArrayValue('iDisplayStart', aoData) / searchData.size ;
        console.info(searchData)
        $.ajax( {
            "type": "POST",
            "contentType": "application/json",
            "url": sSource,
            "dataType": "json",
            "data": JSON.stringify(searchData), //以json格式传递
            "success": function(d) {
                var page=d.data;
               var data = d.data.content;

                // for (var i = 0, jLen = data.length; i < jLen; i++) {
                //     data[i]['DT_RowData'] = {'id': data[i].id};
                // };
                // for (var j = 0, jLen = data.length; j < jLen; j++) {
                //     for (var i = 0, len = columns.length; i < len; i++) {
                //         if (!data[j][columns[i].data]) {
                //             data[j][columns[i].data] = '';
                //         }
                //     }
                // }
                var returnData = {};
                //returnData.draw =indexdraw++;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = page.numberOfElements;//返回数据全部记录
                returnData.recordsFiltered = page.totalElements;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = data
                fnCallback(returnData)
                //fnCallback({aaData: data});
            }
        });
    }
    function getObjArrayValue(name, array) {
        for (var i = 0 , len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }

});
