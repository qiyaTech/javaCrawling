var heituUtils = (function () {
    function gEl(id) {
        return $("#" + id);
    }

    function renderTemplate(opts, data) {
        if (!opts || !opts.templateId || !data) {
            return;
        }
        addTemplate(opts.templateId);
        var $container = !!opts.id ? gEl(opts.id) : $(opts.el);
        try {
            var html = template(opts.templateId, data);
            $container.show();
            if (!!opts.reset)
                $container.html(html);
            else
                $container.append($(html));
        } catch (e) {
            console.log("执行renderTemplate失败，模版渲染失败");
            console.log(e);
        }
    }

    // 在模版容器里添加模版
    function addTemplate(templateName) {
        var template;
        if (!$('#' + templateName).get(0)) {
            template = heituTemplate[templateName];
            if (!!template) {
                _getTemplatesEl().html(_getTemplatesEl().html() + template);
            }
        }
    }

    // 获取页面模版容器
    function _getTemplatesEl() {
        return $('#templateWrapper');
    }

    // 根据templateid 和 data获取对应的html代码
    function getTemplateHtml(templateId, data) {
        addTemplate(templateId);
        return template(templateId, data);
    }

    function confirm(opts) {
        var dialog = bootbox.dialog({
            title: opts.title,
            message: opts.message,
            buttons: {
                cancel: {
                    label: opts.noTitle || "取消",
                    className: "btn-default",
                    callback: function () {
                        if (!!opts.no) {
                            opts.no();
                        }
                        bootbox.hideAll();
                        //dialog.modal('hide');
                    }
                },
                success: {
                    label: opts.yesTitle || "确定",
                    className: "btn-primary",
                    callback: function () {
                        if (!!opts.yes) {
                            opts.yes();
                        }
                        bootbox.hideAll();
                        //dialog.modal('hide');
                    }
                }
            }
        });
    }

    function _alert(msg) {
        if (!!msg)
            bootbox.alert({
                size: 'small',
                message: msg
            });
    }

    function msg(text,time){
        //layer.msg(text,{
        //    time:time||2000
        //});
    }

    function getAjaxUrl(apiName,names,values){
        return projectConfig.config.ApiBaseUrl + apiName + (!!names&&!!values)?('?'+qipanUtils.genQueryString(names, values)):'';
    }

    function closeAll() {
        bootbox.hideAll();
        //layer.closeAll();
    }

    function load() {
       // layer.load();
        bootbox.dialog({
            closeButton: false,
            className: 'loading',
            message: '<img src="' + heitu.config.loading + '">'
        });
    }

    // 显示和关闭“加载中”
    function showLoading(show, type) {
        if (show) {
            load();
        } else {
            closeAll();
        }
    }

    // 显示加载失败
    function showError(errorMsg) {
        if (!errorMsg)
            return;
        _alert(errorMsg);
    }

    // jquery 的ajax二次封装
    function ajax(opts) {
        var async = true;
        if (opts.async == false) {
            async = false;
        }
        var loadType = opts.loadType || 'Page';
        showLoading(opts.loadShow || true, loadType);
        $.ajax({
            type: opts.type || 'POST',
            url: opts.url,
            async: async,
            data: opts.data || {},
            dataType: "json",
            contentType: "application/json",
            success: function (d) {
                showLoading(false, loadType);
                if (d.code != 0) { // 返回码错误
                    if (d.code == commonDefine.returnCode.NOT_EXIST)
                        d.message = "数据不存在";
                    _alert(d.message);
                    return;
                }
                if (!!opts.success && typeof opts.success == 'function') {
                    opts.success(qipanUtils.parseJson(d.data));
                }
            },
            error: function (e) {
                showLoading(false, loadType);
                if (!!opts.error && typeof opts.error == 'function') {
                    opts.error(e);
                } else {
                    showError(opts.errorMsg || '网络不给力，加载数据失败.');
                }
            }
        });
    }

    function getUrlParams(key) {
        var paramStr = window.location.search.substring(1);
        var option = null;
        if (paramStr.length > 0) {
            var params = paramStr.split("&");
            option = {};
            for (var i = 0; i < params.length; i++) {
                option[params[i].split("=")[0]] = params[i].split("=")[1];
            }
            if (key) {
                return option[key] || null;
            }
        }
        return option;
    }

    function setBaiduUeditorUrl(url) {
        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function (action) {
            if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'listimage') {
                return url;
            } else if (action == 'uploadvideo') {
                return url;
            } else {
                return this._bkGetActionUrl.call(this, action);
            }
        }
    }

    // TODO 判断是否存在
    function isFalse(value) {
        if (!value) {
            if (value == false) {
                return false;
            } else {
                return 'undefined';
            }
        } else {
            return true;
        }
    }

    // 根据name获取对象数组中 该name对应的value
    function getObjArrayValue(name, array) {
        for (var i = 0 , len = array.length; i < len; i++) {
            if (name == array[i].name)
                return array[i].value;
        }
    }

    return {
        gEl: gEl,
        renderTemplate: renderTemplate,
        addTemplate: addTemplate,
        getTemplateHtml: getTemplateHtml,
        confirm: confirm,
        alert: _alert,
        load: load,
        closeAll: closeAll,
        ajax: ajax,
        getUrlParams: getUrlParams,
        setBaiduUeditorUrl: setBaiduUeditorUrl,
        getObjArrayValue: getObjArrayValue,
        getAjaxUrl:getAjaxUrl,
        msg:msg
    }
})();