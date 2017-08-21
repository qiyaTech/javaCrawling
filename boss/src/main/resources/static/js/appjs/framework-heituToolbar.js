/**
 * Created by dengduiyi on 16/10/19.
 */
/**
 * toolbar的控制
 */

var toolBarCtrl = (function () {
    // toolbar的设置功能
    function _setting(table) {
        var headData = table.getHeadData();
        var dialogTableId = table.getTableId() + '-setting-dialog';
        // 获取弹窗的html代码
        var _getDialogHtml = function () {
            var isCheckAll = (function () {
                var isCheckAll = true;
                $.each(headData, function (i, item) {
                    if (!item.isCheck) {
                        isCheckAll = false;
                    }
                });
                return isCheckAll;
            })();
            var tHtml = heituUtils.getTemplateHtml('toolBarSettingDialog', {
                id: dialogTableId,
                isCheckAll: isCheckAll,
                data: headData
            });

            return tHtml;
        };
        bootbox.dialog({
            title: "表格设置",
            message: _getDialogHtml(),
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                    }
                },
                success: {
                    label: "保存",
                    className: "btn-primary",
                    callback: function () {
                        var _dataTable = table.getDataTable();
                        $.each($dialogTable.find("tBody tr"), function (i, item) {
                            var $tr = $(item);
                            var index = parseInt($tr.attr('data-index'));
                            var dataname = $tr.attr('data-dataname');
                            var isCheck = $tr.find(".checkOne").is(":checked");
                            //var newName = $tr.find('p').html();
                            var column = _dataTable.column(index);
                            //_dataTable.column[dataname].setOrdinal(index);
                            //_dataTable.column[dataname].columnName = newName;
                            column.visible(isCheck);
                            headData[i].isCheck = isCheck;
                        });
                        table.setHeadData(headData);
                    }
                }
            }
        });
        var $dialogTable = $("#" + dialogTableId); // 当前弹窗容器
        var len = $dialogTable.find("tBody tr").length; // 用于全选个数判断
        var isEdit = true; // 判断操作是否正在修改表头名字
        // 交换位置
        var _switchPosition = function ($this) {
            var $parentTr = $this.parent().parent();
            var index = parseInt($parentTr.attr("data-index"));
            var $prev, $now, $next;
            if (index > 0 && index < len) {
                $prev = $dialogTable.find("[data-index='" + (index - 1) + "']");
                $now = $dialogTable.find("[data-index='" + index + "']");
                $next = $dialogTable.find("[data-index='" + (index + 1) + "']");
                if ($this.hasClass('up')) {
                    $now.attr("data-index", index - 1);
                    $prev.attr("data-index", index)
                    $now.insertBefore($prev);
                }
                if ($this.hasClass('down')) {
                    $now.attr("data-index", index + 1);
                    $next.attr("data-index", index)
                    $now.insertAfter($next);
                }
            } else if (index == 0) {
                $now = $dialogTable.find("[data-index='" + index + "']");
                $next = $dialogTable.find("[data-index='" + (index + 1) + "']");
                if ($this.hasClass('down')) {
                    $now.attr("data-index", index + 1);
                    $next.attr("data-index", index)
                    $now.insertAfter($next);
                }
            } else if (index == len) {
                $prev = $dialogTable.find("[data-index='" + (index - 1) + "']");
                $now = $dialogTable.find("[data-index='" + index + "']");
                if ($this.hasClass('up')) {
                    $now.attr("data-index", index - 1);
                    $prev.attr("data-index", index)
                    $now.insertBefore($prev);
                }
            }
        };
        // 修改表头名字
        var _rename = function ($this) {
            if (isEdit) {
                isEdit = false;
                var $input = $('<input type="text" value="' + $this.html() + '"/>')
                $this.hide().after($input);
                $input.focus();
                $input.off("blur").on("blur", function () {
                    $this.html($input.val()).show();
                    $input.remove();
                    isEdit = true;
                });
            }
        };
        // 全选和反选
        var _chooseAll = function ($this) {
            if ($this.hasClass("checkAll")) {
                if ($this.is(':checked')) {
                    $this.prop("checked", true);
                    $dialogTable.find(".checkOne").prop("checked", true);
                } else {
                    $this.prop("checked", false);
                    $dialogTable.find(".checkOne").prop("checked", false);

                }
            }
            if ($this.hasClass("checkOne")) {
                if ($this.is(':checked')) {
                    if ($dialogTable.find(".checkOne").length == $dialogTable.find("input:checkbox[name='tableHeadShow']:checked").length) {
                        $dialogTable.find(".checkAll").prop("checked", true);
                    }
                    $this.prop("checked", true);
                } else {
                    $dialogTable.find(".checkAll").prop("checked", false);
                    $this.prop("checked", false);
                }
            }
        };
        // 操作弹窗容器，委托点击事件给子元素，根据元素类型执行相对应操作
        $dialogTable.off("click").on("click", function (e) {
            var $this = $(e.target);
            switch (e.target.tagName.toLowerCase()) {
                case 'a': // 按钮  上移 下移
                    _switchPosition($this);
                    break;
                case 'p': // 文字 修改表头名字
                    _rename($this);
                    break;
                case 'input': // 复选框 选中一行
                    _chooseAll($this);
                    break;
            }
        });
    }

    return {
        setting: _setting
    }
})();

function HeituToolbar(options) {
    var self = this;
    this.el = null;
    this.options = {
        dataSource: {},
        items: [
            {
                action: 'create',
                name: '添加',
                enable: true,
                icon: 'fa fa-plus',
                fnList: [self.events().create]
            },
            {
                action: 'read',
                name: '查看',
                enable: false,
                icon: 'fa fa-read',
                fnList: [self.events().read]
            },
            {
                action: 'update',
                name: '编辑',
                enable: false,
                icon: 'fa fa-edit',
                fnList: [self.events().update]
            },
            {
                action: 'del',
                name: '删除',
                enable: true,
                icon: 'fa fa-trash',
                fnList: [self.events().del]
            },
            {
                action: 'setting',
                name: '设置',
                enable: true,
                icon: 'fa fa-cog',
                fnList: [self.events().setting]
            }
        ]
    };
    this.init(options);
}

HeituToolbar.prototype = {
    init: function (options) {
        var options = options || {};
        var self = this;
        var newItems = self.options.items;
        if (!!options && !!options.items) {
            $.each(options.items, function (i, item) {
                var isHave = false;
                $.each(newItems, function (j, jtem) {
                    if (item.action == jtem.action) {
                        newItems[j] = $.extend(newItems[j], item, true);
                        isHave = true;
                    }
                });
                if (!isHave) {
                    newItems.push(item);
                }
            });
        }
        options.items = newItems;
        self.options = $.extend(self.options, options, true);
    },
    render: function () {
        this._template();
        this._handle();
        this._trigger();
    },
    refresh: function () {
        this._trigger();
    },
    appendEvent: function (action, fn, params) {
        var self = this;
        $(self).on(action, fn);
    },
    resetEvent: function (action, fn) {
        var self = this;
        $(self).off(action);
        $(self).off(action).on(action, fn);
    },
    // 设置toolbar的容器id
    setContainerId: function (containerId) {
        this.options.containerId = containerId; // 相关对象的参数
    },
    // 设置数据源
    setDataSource: function (iDataSource) {
        this.options.dataSource = iDataSource;
    },
    // 获取数据源
    getDataSource: function () {
        if (!!this.options.dataSource) {
            return this.options.dataSource;
        } else {
            return null;
        }
    },
    // 绑定事件
    _handle: function () {
        var self = this;
        var items = this.options.items;
        $.each(items, function (i, item) {
            if (!!item.enable && !!item.fnList) {
                $.each(item.fnList, function (j, fnItem) {
                    $(self).on(item.action, fnItem);
                });
            }
        });
    },
    // 执行绑定事件
    _trigger: function () {
        var self = this;
        // 给toolbar预绑定事件
        $('#' + this.options.containerId).off("click").on("click", "a", function (e) {
            e.preventDefault();
            var $this = $(this);
            var dataAction = $this.attr("data-action");
            // 执行默认的自定义事件
            try {
                $(self).trigger(dataAction, [self]);
            } catch (e) {
                console.log('toolBar 执行自定义事件有问题');
                console.log(e);
            }
        });
    },
    events: function () {
        var self = this;

        var _newWindow = function (type, select) {
            var ds = self.getDataSource();
            var url = ds.getActionUrl(type, select);
            window.open(url);
            //window.open(url, type, qipanUtils.getNewWindowConfig(80,80));
        };

        var create = function () {
            _newWindow('create', '');
        };

        var read = function () {
            var ds = self.getDataSource();
            var select = ds.getSelect();
            if (!!select) {
                if (select.length > 1) {
                    heituUtils.alert('请选择一行查看！');
                } else {
                    _newWindow('read', select[0]);
                }
            } else {
                heituUtils.alert('请选择一行查看！');
            }
        };

        var update = function () {
            var ds = self.getDataSource();
            var select = ds.getSelect();
            if (!!select) {
                if (select.length > 1) {
                    heituUtils.alert('请选择一行编辑！');
                } else {
                    _newWindow('update', select[0]);
                }
            } else {
                heituUtils.alert('请选择一行编辑！');
            }
        };

        var del = function () {
            var ds = self.getDataSource();
            var select = ds.getSelect();
            if (!!select && select.length > 0) {
                heituUtils.confirm({
                    title: '删除',
                    message: '是否要删除选中' + select.length + '行数据？',
                    yes: function () {
                        ds.deleteRow(true);
                    }
                });
            } else {
                heituUtils.alert('请选中要删除的行！');
            }
        };

        var setting = function () {
            var ds = self.getDataSource();
            toolBarCtrl.setting(ds._getCtrl());
        };

        return {
            create: create,
            read: read,
            update: update,
            del: del,
            setting: setting
        }
    },
    _template: function () {
        heituUtils.renderTemplate({
            id: this.options.containerId,
            templateId: this.options.templateId || 'templateToolBar'
        }, this.options);
    }
};