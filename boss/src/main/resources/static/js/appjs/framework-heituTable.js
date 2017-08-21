/**
 * Created by dengduiyi on 16/10/19.
 */
function HeituTable(options) {
    this.options = {
        templateId: 'templateTable'
    };
    this.options = $.extend(true, this.options, options);
    this.init(options);
}
HeituTable.prototype = {
    init: function (options, isRender) {
        if (!!options) {
            this.options = $.extend(true, this.options, options);
            this.options.tableId = this.options.id + '-Table';
        }
        if (!!isRender) {
            this.render();
        }
    },
    render: function () {
        this._template();
        this.initDataTableOpts();
        this._dataTable();
        this._event();
    },
    initDataTableOpts:function(){
        this.setSearchTerms(this.options.searchTerms);
        this.setSize(this.options.pagesize);
    },
    refresh: function (searchTerms) {
        this.setSearchTerms(searchTerms);
        this._dataTable();
    },
    setSearchTerms:function(searchTerms){
        this.searchTerms = searchTerms;
    },
    getSearchTerms:function(){
        return this.searchTerms;
    },
    setData: function (data) {
        this.options.data = data;
    },
    setHeadData: function (data) {
        if (!!data)
            this.options.thead = data;
    },
    getHeadData: function () {
        return this.options.thead;
    },
    refreshHeadData: function () {

    },
    setPageSize: function (pageSize) {
        this.options.pagesize = pageSize;
    },
    getTableEl: function () {
        return $("#" + this.options.tableId);
    },
    getToolBarId: function () {
        return this.options.id + '-toolBarContainer';
    },
    getTableId: function () {
        return this.options.id + '-toolBarContainer';
    },
    getDataTable: function () {
        if (!!this.dataTable) {
            return this.dataTable;
        }
        return false;
    },
    IDataSource: function () {
        var self = this;
        // 获取选中行id
        var getSelect = function () {
            var selected = [];
            if (self.getTableEl().find('tr.selected').length > 0) {
                var idName = self.options.entityId; // self.options.entityId;
                $.each(self.getTableEl().find('tr.selected'), function (i, row) {
                    selected.push($(row).data(idName));
                });
            }
            return selected;
        };
        // 返回操作url
        var getActionUrl = function (action, select) {
            return self.options.urlRule.getUrl(self.options.entity, action, select);
        };
        // 返回操作url
        var getActionApiUrl = function (action, select) {
            return self.options.urlRule.getApiUrl(self.options.entity, action, select);
        };
        // 根据选中行id 删除选中行
        var deleteRow = function (remoteDelete) {
            if (remoteDelete) {  // 同时删除服务端数据
                var selected = getSelect();
                var successDelLen = 0,          // 服务端已经删除数据的个数
                    delLen = selected.length;   // 需要删除数据的总个数
                for (id in selected) {
                    var url = getActionApiUrl('delete', selected[id]);
                    heituUtils.ajax({
                            url: url,
                            success: function (d) {
                                successDelLen++;
                                if (successDelLen == delLen) {
                                    self.getDataTable().row('.selected').remove().draw(false);
                                }
                                //self.getDataTable().row('.selected').remove().draw(false); // TODO : 应该是删除一条UI去一条。现在是删除第一条的时候UI就完成了
                            }
                        }
                    );
                }
            } else {
                self.getDataTable().row('.selected').remove().draw(false);
                //self.getTableEl().find('tr.selected').remove().draw();
            }
        };
        // 获取表格id
        var getCtrlId = function () {
            return self.options.tableId;
        };
        // 获取插件表格对象
        var _getCtrl = function () {
            return self;
        };
        return {
            getSelect: getSelect,
            getActionUrl: getActionUrl,
            getActionApiUrl: getActionApiUrl,
            deleteRow: deleteRow,
            getCtrlId: getCtrlId,
            _getCtrl: _getCtrl
        }
    },
    _template: function () {
        var self = this;
        if (!this.options.row || (!!this.options.row && !this.options.row.disableUpdate) || (!!this.options.row && !this.options.row.disableRead)) {
            this.options.thead.unshift({isCheck: true, name: "操作", dataName: '_operation'});
        }
        if (!this.options.row || (!!this.options.row && !this.options.row.disableSelect)) {
            this.options.thead.unshift({isCheck: true, name: "checkbox", dataName: 'checkbox'});
        }
        heituUtils.renderTemplate({id: this.options.id, templateId: this.options.templateId}, this.options);
    },
    _getColumnDefs: function () {
        var self = this;
        // 禁止排序的行的配置
        var columnDefs = [];
        var secColumDefs;
        //  是否显示复选框
        if (!this.options.row || (!!this.options.row && !this.options.row.disableSelect)) {
            $.each(self.options.data, function (i, data) {
                self.options.data[i]['checkbox'] = "<input type='checkbox' name='" + self.options.tableId + "-select' style='margin-left:7px;'/>";
            });
            columnDefs.push({
                orderable: false,
                targets: 0
            });
        }
        var idName = self.options.entityId;
        // 是否显示查看按钮
        if (!this.options.row || (!!this.options.row && !this.options.row.disableRead)) {
            $.each(self.options.data, function (i, data) {
                var row_id = data[idName];
                var url = self.options.urlRule.getUrl(self.options.entity, 'read', row_id);
                self.options.data[i]['_operation'] = "<a href='" + url + "' target='_blank' class='btn-link' alt='查看' title='查看'><i class='heitu-icon-read'></i></a>";
            });
            secColumDefs = {
                orderable: false,
                targets: 1
            };
        }
        // 是否显示编辑按钮
        if (!this.options.row || (!!this.options.row && !this.options.row.disableUpdate)) {
            $.each(self.options.data, function (i, data) {
                var row_id = data[idName];
                var url = self.options.urlRule.getUrl(self.options.entity, 'update', row_id);
                self.options.data[i]['_operation'] += "<a href='" + url + "'  target='_blank'  class='btn-link' style='margin-left:10px;'  alt='编辑' title='编辑'><i class='heitu-icon-edit'></i></a>";
            });
            secColumDefs = {
                orderable: false,
                targets: 1
            };
        }
        // 是否显示编辑按钮
        if (typeof self.options.parentCode != 'undefined') {
            $.each(self.options.data, function (i, data) {
                var row_id = data[idName];
                var updateurl = self.options.urlRule.getParentUrl(self.options.entity, 'search', self.options.parentCode, row_id);
                var createurl = self.options.urlRule.getParentUrl(self.options.entity, 'create', self.options.parentCode, row_id);
                self.options.data[i]['_operation'] += "<a href='" + updateurl + "'  target='_blank'  class='btn-link' style='margin-left:10px;'   alt='管理下级' title='管理下级'><i class='heitu-icon-manageChild'></i></a>";
                self.options.data[i]['_operation'] += "<a href='" + createurl + "'  target='_blank'  class='btn-link' style='margin-left:10px;'   alt='创建下级' title='创建下级'><i class='heitu-icon-createChild'></i></a>";
            });
            secColumDefs = {
                orderable: false,
                targets: 1
            };
        }

        if (!!secColumDefs) {
            columnDefs.push(secColumDefs);
        }

        return columnDefs;
    },
    _getColumns: function () {
        var self = this;
        var columns = [];
        self._columns = []; // 用于转换含有datasource的表格
        $.each(self.options.thead, function (i, item) {
            if (item.isCheck) {
                self._columns.push(item);
                columns.push({
                    data: item.dataName
                });
            }
        });
        return columns;
    },
    _ctrlPaging: function () {
        var self = this;
        // 默认可以分页
        if (!self.options.bpaging && self.options.bpaging == false) {
            self.options.bpaging = false;
        } else {
            self.options.bpaging = true;
        }
    },
    _dataTable: function () {
        var self = this;
        // 如果存在apiUrl 则执行 ajax分页
        // 否则执行前端分页
        if (!!self.options.apiUrl) {
            self._ajaxUrl_dataTable();
        } else {
            self._data_dataTable();
        }

    },
    // 如果是有数据渲染datatable
    _data_dataTable: function () {
        var self = this;
        if (!self.options.data) { // 如果没有数据 默认给空数据
            self.options.data = [];
        }
        // 根据表头获取相对应的表格数据的配置
        var columns = self._getColumns();

        var columnDefs = self._getColumnDefs();

        self._ctrlPaging(); // 控制是否可以分页

        // 保存行的唯一标识
        $.each(self.options.data, function (i, data) {
            self.options.data[i]['DT_RowData'] = {'id': data[self.options.entityId]};
        });
        if (!!self.options.data) {
            var data = self._getTableUI(columns);
            if(!self.iDisplayLength){
                self.iDisplayLength = self.options.pagesize;
            }
            var dataTableOpts = {
                "data": data,
                "columns": columns,
                "bFilter": false, //开关，是否启用客户端过滤功能
                "info": false, //开关，是否显示表格的一些信息
                //"sScrollY": "500px",
                "bScrollInfinite": true, //开关，以指定是否无限滚动（与sScrollY配合使用），在大数据量的时候很有用。当这个标志为true的时候，分页器就默认关闭
                "bSort": false, // 开关，是否让各列具有按列排序功能
                "bRetrieve": true,
                "columnDefs": columnDefs,
                "paging": self.options.bpaging,
                "ordering": false
            };
            dataTableOpts = !!self.options.opt ? $.extend(true,dataTableOpts,self.options.opt) :dataTableOpts;
            console.log('dataTableOpts',dataTableOpts)
            if (!self.dataTable) {
                self.dataTable = self.getTableEl().DataTable(dataTableOpts);
            } else {
                if ($("#" + self.options.tableId + '-checkAll').get(0)) {
                    $("#" + self.options.tableId + '-checkAll').prop('checked', false);
                }
                self.dataTable.destroy();
                //self.getTableEl().empty();
                self.dataTable = $('#' + self.options.id + '-Table').DataTable(dataTableOpts);
            }
            self._changeValue();
        }
    },
    setSize:function(size){
        this.iDisplayLength = size;
    },
    getSize:function(){
        return this.iDisplayLength;
    },
    // 如果是ajax链接渲染datatable
    _ajaxUrl_dataTable: function () {
        var self = this;
        // 根据表头获取相对应的表格数据的配置
        var columns = self._getColumns();
        var dataTableOpts = {
            "columns": columns,
            "bFilter": false, //开关，是否启用客户端过滤功能
            "info": false, //开关，是否显示表格的一些信息
            //"sScrollY": "500px",
            "bScrollInfinite": true, //开关，以指定是否无限滚动（与sScrollY配合使用），在大数据量的时候很有用。当这个标志为true的时候，分页器就默认关闭
            "bSort": false, // 开关，是否让各列具有按列排序功能
            "bRetrieve": true,
//                "columnDefs": columnDefs,
            "iDisplayLength": self.getSize(),//每页显示10条数据
            "oLanguage": {
                "sLengthMenu": "每页显示 _MENU_条",
                "sZeroRecords": "没有找到符合条件的数据",
                "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                "sInfoEmpty": "木有记录",
                "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
                "sSearch": "搜索：",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            },
            "paging": self.options.bpaging,
            "ordering": false,
            "bProcessing": true, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
            "bServerSide": true, //开启服务器模式，使用服务器端处理配置datatable。注意：sAjaxSource参数也必须被给予为了给datatable源代码来获取所需的数据对于每个画。 这个翻译有点别扭。开启此模式后，你对datatables的每个操作 每页显示多少条记录、下一页、上一页、排序（表头）、搜索，这些都会传给服务器相应的值。
            "sAjaxSource": self.options.apiUrl, //给服务器发请求的url
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {// 当创建了行，但还未绘制到屏幕上的时候调用，通常用于改变行的class风格
                self._rowCallBack(nRow, aData, iDisplayIndex);
            },
            "fnServerData": function (sSource, aoData, fnCallback) {
                self._fnServerData(sSource, aoData, fnCallback, columns,self.getSearchTerms());
            }
        };
        if (!self.dataTable) {
            self.dataTable = self.getTableEl().DataTable(dataTableOpts);
        } else {
            if ($("#" + self.options.tableId + '-checkAll').get(0)) {
                $("#" + self.options.tableId + '-checkAll').prop('checked', false);
            }
            self.dataTable.destroy();
            self.dataTable = $('#' + self.options.id + '-Table').DataTable(dataTableOpts);
            self._changeValue();
        }
    },
    _rowCallBack: function (nRow, aData, iDisplayIndex) {
        var self = this;
        var url;
        //  是否显示复选框
        if (!self.options.row || (!!self.options.row && !self.options.row.disableSelect)) {
            $('td:eq(0)', nRow).html("<input type='checkbox' name='" + self.options.tableId + "-select' style='margin-left:7px;'/>");
        }
        // 是否显示查看按钮
        if (!self.options.row || (!!self.options.row && !self.options.row.disableRead)) {
            url = self.options.urlRule.getUrl(self.options.entity, 'read', aData[self.options.entityId]);
            $('td:eq(1)', nRow).append($("<a href='" + url + "' target='_blank' class='btn-link' alt='查看' title='查看'><i class='heitu-icon-read'></i></a>"));
        }
        // 是否显示编辑按钮
        if (!self.options.row || (!!self.options.row && !self.options.row.disableUpdate)) {
            url = self.options.urlRule.getUrl(self.options.entity, 'update', aData[self.options.entityId]);
            $('td:eq(1)', nRow).append($("<a href='" + url + "'  target='_blank'  class='btn-link' style='margin-left:10px;'  alt='编辑' title='编辑'><i class='heitu-icon-edit'></i></a>"));
        }
        // 是否显示管理下级和创建下级
        if (typeof self.options.parentCode != 'undefined') {
            var updateurl = self.options.urlRule.getParentUrl(self.options.entity, 'search', self.options.parentCode, aData[self.options.entityId]);
            var createurl = self.options.urlRule.getParentUrl(self.options.entity, 'create', self.options.parentCode, aData[self.options.entityId]);
            $('td:eq(1)', nRow).append($("<a href='" + updateurl + "'  target='_blank'  class='btn-link' style='margin-left:10px;'   alt='管理下级' title='管理下级'><i class='heitu-icon-manageChild'></i></a>"));
            $('td:eq(1)', nRow).append($("<a href='" + createurl + "'  target='_blank'  class='btn-link' style='margin-left:10px;'   alt='创建下级' title='创建下级'><i class='heitu-icon-createChild'></i></a>"));
        }
    },
    _fnServerData: function (sSource, aoData, fnCallback, columns,searchTerms) {
        var self = this;
        var searchData = $.extend(true, {}, searchTerms);
        //  每页多少条数据
        searchData.size = heituUtils.getObjArrayValue('iDisplayLength', aoData);
        self.setSize(searchData.size);
        //  请求第几页数据  初始iDisplayStart为0
        searchData.index = (heituUtils.getObjArrayValue('iDisplayStart', aoData) / searchData.size);
        heituUtils.ajax({
                url: sSource + heituFramework.getQueryString(),
                data: JSON.stringify(searchData),
                success: function (d) {
                    var data = d.content;
                    $.each(data, function (i, d) {
                        data[i]['DT_RowData'] = {'id': d[self.options.entityId]};
                    });
                    for (var j = 0, jLen = data.length; j < jLen; j++) {
                        for (var i = 0, len = columns.length; i < len; i++) {
                            if (!data[j][columns[i].data]) {
                                data[j][columns[i].data] = '';
                            }
                        }
                    }
                    fnCallback({aaData: data});
                }
            }
        );
    },
    _getTableUI: function (columns) {
        var self = this;
        var data = self.options.data;
        for (var j = 0, jLen = data.length; j < jLen; j++) {
            for (var i = 0, len = columns.length; i < len; i++) {
                if (!data[j][columns[i].data]) {
                    self.options.data[j][columns[i].data] = '';
                }
            }
        }
        return self.options.data;
    },
    _changeValue: function () {
        var self = this;
        $.each(self._columns, function (i, item) {
            if (typeof item.dataSource != 'undefined') {
                if (heitu.config.isDemoEnv) {
                    var testData = demoApi.getTableColData();
                    $.each(self.options.data, function (j, row) {
                        $.each(testData, function (n, m) {
                            if (m.value == row[item.dataName]) {
                                var $row = $(self.getTableEl().find('tbody tr')[j]);
                                var $col = $($row.find('td')[i]);
                                $col.html(m.name);
                            }
                        });
                    });
                } else {

                }
            }
        });
    },
    _event: function () {
        var self = this;
        var allCheckBoxId = self.options.tableId + '-checkAll';
        var checkBoxName = self.options.tableId + '-select';
        // 默认是可以选择多行
        if (!self.options.selectMore && self.options.selectMore == false) {
            self.options.selectMore = false;
        } else {
            self.options.selectMore = true;
        }
        self.getTableEl().find("tBody").off("click").on("click", "tr", function (e) {
            var $tr = $(this);
            var $this = $(e.target);
            if (!!self.options.selectMore) { // 可以多选
                var $checkbox = $tr.find(("input[name='" + checkBoxName + "']"));
                $tr.toggleClass('selected');
                if (!!$checkbox && $checkbox.length > 0) {
                    var selected = function () {
                        if ($checkbox.is(':checked')) {
                            $checkbox.prop('checked', false);
                            $('#' + allCheckBoxId).prop('checked', false);
                        } else {
                            $checkbox.prop('checked', true);
                            var $tmp = $('[name=' + checkBoxName + ']:checkbox');
                            $('#' + allCheckBoxId).prop('checked', $tmp.length == $tmp.filter(':checked').length);
                        }
                    };
                    selected();
                    if ($this.prop('name') == checkBoxName) {
                        selected();
                    }
                }
            } else { // 只能单选
                if ($tr.hasClass('selected')) {
                    $tr.removeClass('selected');
                } else {
                    self.getTableEl().find("tBody tr").removeClass('selected');
                    $tr.addClass('selected');
                }
            }
        });
        //checkbox全选
        if ($("#" + allCheckBoxId).get(0)) {
            $("#" + allCheckBoxId).off("click").on("click", function () {
                if ($(this).prop("checked") === true) {
                    $("input[name='" + checkBoxName + "']").prop("checked", $(this).prop("checked"));
                    self.getTableEl().find('tBody tr').addClass('selected');
                } else {
                    $("input[name='" + checkBoxName + "']").prop("checked", false);
                    self.getTableEl().find('tBody tr').removeClass('selected');
                }
            });
        }
    }
};