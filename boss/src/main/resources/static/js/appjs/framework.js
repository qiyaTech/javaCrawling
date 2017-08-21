//模块渲染
(function (heitu) {
    function _renderTemplateData(id, data) {
        var data = {data: data};
        heituUtils.renderTemplate(id, data);
    }

    function _renderTemplate(id, data) {
        heituUtils.renderTemplate(id, data);
    }

    var _gEl = heituUtils.gEl;

    heitu.render = {};
    heitu.render.navBar = {
        init: function () {
            _renderTemplate(heitu.id.navBar, heitu.model.navBar.header);
        },
        toggleMenuBtn: {
            init: function () {
                if (!!heitu.model.navBar.toggleMenuBtn.isShow) {
                    _renderTemplate(heitu.id.navBar.toggleMenuBtn, heitu.model.navBar.toggleMenuBtn);
                }
            },
            refresh: function () {
                if (!!heitu.model.navBar.toggleMenuBtn.isShow) {
                    _renderTemplate(heitu.id.navBar.toggleMenuBtn, heitu.model.navBar.toggleMenuBtn);
                } else {
                    heituUtils.gEl(heitu.id.navBar.toggleMenuBtn.id).hide()
                }
            }
        },
        message: {
            init: function () {
                if (heitu.model.navBar.message.isShow) {
                    _renderTemplate(heitu.id.navBar.message, heitu.model.navBar.message);
                    _renderTemplateData(heitu.id.navBar.message.dataId, heitu.model.navBar.message.data);
                }
            },
            refresh: function (isOnlyRefreshData) {
                if (!isOnlyRefreshData) {
                    if (heitu.model.navBar.message.isShow) {
                        _renderTemplate(heitu.id.navBar.message, heitu.model.navBar.message);
                        _renderTemplateData(heitu.id.navBar.message.dataId, heitu.model.navBar.message.data);
                    } else {
                        heituUtils.gEl(heitu.id.navBar.message.id).hide();
                    }
                } else {
                    heituUtils.gEl(heitu.id.navBar.message.data.dataId).show();
                    _renderTemplateData(heitu.id.navBar.message.dataId, heitu.model.navBar.message.data);
                }
            }
        },
        notifications: {
            init: function () {
                if (heitu.model.navBar.notifications.isShow) {
                    _renderTemplate(heitu.id.navBar.notifications, heitu.model.navBar.notifications);
                    _renderTemplateData(heitu.id.navBar.notifications.dataId, heitu.model.navBar.notifications.data);
                }
            },
            refresh: function (isOnlyRefreshData) {
                if (!isOnlyRefreshData) {
                    if (heitu.model.navBar.notifications.isShow) {
                        _renderTemplate(heitu.id.navBar.notifications, heitu.model.navBar.notifications);
                        _renderTemplateData(heitu.id.navBar.notifications.dataId, heitu.model.navBar.notifications.data);
                    } else {
                        _gEl(heitu.id.navBar.notifications.id).hide();
                    }
                } else {
                    _renderTemplateData(heitu.id.navBar.notifications.dataId, heitu.model.navBar.notifications.data);
                }
            }
        },
        mega: {
            init: function () {
                if (heitu.model.navBar.mega.isShow) {
                    _renderTemplate(heitu.id.navBar.mega, heitu.model.navBar.mega);
                    _renderTemplateData(heitu.id.navBar.mega.dataId, heitu.model.navBar.mega.data);
                }
            },
            refresh: function (isOnlyRefreshData) {
                if (!isOnlyRefreshData) {
                    if (heitu.model.navBar.mega.isShow) {
                        _renderTemplate(heitu.id.navBar.mega, heitu.model.navBar.mega);
                        _renderTemplateData(heitu.id.navBar.mega.dataId, heitu.model.navBar.mega.data);
                    } else {
                        _gEl(heitu.id.navBar.mega.id).hide();
                    }
                } else {
                    _renderTemplateData(heitu.id.navBar.mega.dataId, heitu.model.navBar.mega.data);
                }
            }
        },
        language: {
            init: function () {
                if (heitu.model.navBar.language.isShow) {
                    _renderTemplate(heitu.id.navBar.language, heitu.model.navBar.language);
                    _renderTemplateData(heitu.id.navBar.language.dataId, heitu.model.navBar.language.data);
                }
            },
            refresh: function (isOnlyRefreshData) {
                if (!isOnlyRefreshData) {
                    if (heitu.model.navBar.language.isShow) {
                        _renderTemplate(heitu.id.navBar.language, heitu.model.navBar.language);
                        _renderTemplateData(heitu.id.navBar.language.dataId, heitu.model.navBar.language.data);
                    } else {
                        _gEl(heitu.id.navBar.language.id).hide();
                    }
                } else {
                    _renderTemplateData(heitu.id.navBar.language.dataId, heitu.model.navBar.language.data);
                }
            }
        },
        user: {
            init: function () {
                if (heitu.model.navBar.user.isShow) {
                    _renderTemplate(heitu.id.navBar.user, heitu.model.navBar.user);
                    _renderTemplateData(heitu.id.navBar.user.dataId, heitu.model.navBar.user.data);
                }
            },
            refresh: function (isOnlyRefreshData) {
                if (!isOnlyRefreshData) {
                    if (heitu.model.navBar.user.isShow) {
                        _renderTemplate(heitu.id.navBar.user, heitu.model.navBar.user);
                        _renderTemplateData(heitu.id.navBar.user.dataId, heitu.model.navBar.user.data);
                    } else {
                        _gEl(heitu.id.navBar.user.id).hide();
                    }
                } else {
                    _renderTemplateData(heitu.id.navBar.user.dataId, heitu.model.navBar.user.data);
                }
            }
        }
    };

    heitu.render.nav = {
        init: function () {
            _renderTemplate(heitu.id.nav, []);
            if (heitu.model.nav.shortcut.isShow) {
                _renderTemplate(heitu.id.nav.shortcut, heitu.model.nav.shortcut);
            }
            _renderTemplateData(heitu.id.nav.mainNav, heitu.model.nav.mainNav);
        }
    };

    heitu.render.footer = {
        init: function () {
            _renderTemplate(heitu.config.footer, heitu.config.footer);
        },
        refresh: function () {
            _renderTemplate(heitu.config.footer, heitu.config.footer);
        }
    };

    heitu.render.login = {
        init: function () {
            _gEl(heitu.config.login.titleId).html(heitu.config.login.title);
            _gEl(heitu.config.login.formId).attr("action", heitu.config.login.formAction);
            _gEl(heitu.config.login.registerUrlId).attr("href", heitu.config.login.registerUrl);
            _gEl(heitu.config.login.forgetPwdUrlId).attr("href", heitu.config.login.forgetPwdUrl);
            if (!!heitu.config.login.isShowRememberPassword) {
                _gEl(heitu.config.login.rememberPasswordId).show();
            } else {
                _gEl(heitu.config.login.rememberPasswordId).hide();
            }
        },
        refresh: function () {
            _gEl(heitu.config.login.titleId).html(heitu.config.login.title);
            _gEl(heitu.config.login.formId).attr("action", heitu.config.login.formAction);
            _gEl(heitu.config.login.registerUrlId).attr("href", heitu.config.login.registerUrl);
            _gEl(heitu.config.login.forgetPwdUrlId).attr("href", heitu.config.login.forgetPwdUrl);
            if (!!heitu.config.login.isShowRememberPassword) {
                _gEl(heitu.config.login.rememberPasswordId).show();
            } else {
                _gEl(heitu.config.login.rememberPasswordId).hide();
            }
        }
    };

    heitu.render.register = {
        init: function () {
            _gEl(heitu.config.register.formId).attr("action", heitu.config.register.formAction);
            _gEl(heitu.config.register.loginUrlId).attr("href", heitu.config.register.loginUrl);
        },
        refresh: function () {
            _gEl(heitu.config.register.formId).attr("action", heitu.config.register.formAction);
            _gEl(heitu.config.register.loginUrlId).attr("href", heitu.config.register.loginUrl);
        }
    };

    heitu.render.forgetPwd = {
        init: function () {
            _gEl(heitu.config.forgetPwd.formId).attr("action", heitu.config.forgetPwd.formAction);
            _gEl(heitu.config.forgetPwd.loginUrlId).attr("href", heitu.config.forgetPwd.loginUrl);
        },
        refresh: function () {
            _gEl(heitu.config.forgetPwd.formId).attr("action", heitu.config.forgetPwd.formAction);
            _gEl(heitu.config.forgetPwd.loginUrlId).attr("href", heitu.config.forgetPwd.loginUrl);
        }
    };

    heitu.render.userCenter = {
        init: function () {
            _gEl(heitu.config.userCenter.brandTitleId).html(heitu.config.mainTitle);
            _gEl(heitu.config.userCenter.brandThinId).html(heitu.config.subTitle);
            _gEl(heitu.config.userCenter.bgOverlayId).css({
                background: 'url(' + heitu.config.bg + ')'
            });
        },
        refresh: function () {
            _gEl(heitu.config.userCenter.brandTitleId).html(heitu.config.mainTitle);
            _gEl(heitu.config.userCenter.brandThinId).html(heitu.config.subTitle);
            _gEl(heitu.config.userCenter.bgOverlayId).css({
                background: 'url(' + heitu.config.userCenter.bg + ')'
            });
        }
    };
})(heitu);
var heituFramework = (function () {
    // 页面渲染类
    function HeituStart(options) {
        this.options = {};
        this.init(options);
    }

    HeituStart.prototype = {
        // 渲染 初始化数据Heitu
        init: function (options) {
            if (!!options) {
                this.options = $.extend(true, this.options, options);
                var navBarSetData = heitu.setData.navBar;
                if (!!options.navBarMessage) {
                    navBarSetData.message(options.navBarMessage);
                }
                if (!!options.navBarNotifications) {
                    navBarSetData.notifications(options.navBarNotifications);
                }
                if (!!options.navBarMega) {
                    navBarSetData.mega(options.navBarMega);
                }
                if (!!options.navBarLanguage) {
                    navBarSetData.language(options.navBarLanguage);
                }
                if (!!options.navBarUser) {
                    navBarSetData.user(options.navBarUser);
                }
                if (!!options.navMainData) {
                    heitu.setData.nav.mainNav(options.navMainData);
                }
            }
        },
        render: function () {
            this.initPage()[$("body").attr("data-page-type")]();
        },
        refresh: function () {
            this.render();
        },
        initPage: function () {
            var self = this;

            var _page = function () {
                heituUtils.renderTemplate({
                    id: heitu.config.pageContainerId,
                    templateId: heitu.config.templatePageContainerId
                }, {isShowPageTitle: heitu.config.isShowPageTitle});

                var navBarRender = heitu.render.navBar;
                // navBarRender.init();
                // navBarRender.toggleMenuBtn.init();
                // navBarRender.message.init();
                // navBarRender.notifications.init();
                // navBarRender.mega.init();
                // navBarRender.language.init();
                // navBarRender.user.init();

                // heitu.render.nav.init();
                // 定位菜单
                var locateMenu = function () {
                    var $nav = $("#mainnav-menu");
                    var links = $nav.find("a");
                    $nav.find("li").removeClass("active-link").removeClass("active").removeClass("active-sub");
                    var url = window.location.href;
                    $.each(links, function (i, link) {
                        var $link = $(link);
                        var thisUrl = $link.attr("href").replace("./", "").replace("../", "");
                        if (url.indexOf(thisUrl) > -1) {
                            switch (parseInt($link.parent().attr("data-level"))) {
                                case 1:
                                    $link.parent().addClass("active-sub");
                                    break;
                                case 2:
                                    $link.parent().addClass("active-link");
                                    $link.parent().parent().parent().addClass("active-sub active");
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                };
                locateMenu();
                heitu.render.footer.init();
            };

            var home = function () {
                _page();
            };

            var entity = function () {
                _page();
                //heituUtils.renderTemplate({
                //    id: heitu.config.pageContainerId,
                //    templateId: heitu.config.templatePageContainerId
                //}, {isShowPageTitle: heitu.config.isShowPageTitle});
            };

            var login = function () {
                heitu.render.userCenter.init();
                heitu.render.login.init();
            };
            var register = function () {
                heitu.render.userCenter.init();
                heitu.render.register.init();
            };
            var forgetPwd = function () {
                heitu.render.userCenter.init();
                heitu.render.forgetPwd.init();
            };
            return {
                home: home,
                entity: entity,
                login: login,
                register: register,
                forgetPwd: forgetPwd
            }
        }
    };

    function _renderFormUI(options) {
        heituUtils.renderTemplate(
            {
                id: options.containerId || heitu.config.pageContainerId || heitu.config.pageContentId,
                templateId: options.templateId || 'templateEntity'
            }, options);
        heituCtrl.formUI(options);
        heitu.CRUDFormUI = options;
    }

    function renderPage(options) {
        var hs = new HeituStart(options);
        hs.render();
        return hs;
    }

    function getSearchUI(ui, ds) {
        ui.id = ui.id || 'searchContainer';
        ui.urlRule = ui.urlRule || projectConfig.urlRule;
        return heituCtrl.getFormUIDSValue(ui, ds);
    }

    function renderSearchUI(searchOpts, tableOpts, toolBarOpts) {
        var table;
        var toolBar;
        searchOpts.btnName = '查询';
        searchOpts.readonly = true;
        searchOpts.valid = false;

        tableOpts.id = tableOpts.id || 'firstTable';
        tableOpts.entityId = tableOpts.entityId || 'id';
        tableOpts.templateId = tableOpts.templateId || 'templateTable';
        tableOpts.urlRule = tableOpts.urlRule || projectConfig.urlRule;
        tableOpts.pagesize = tableOpts.pagesize || 100;
        tableOpts.tableClass = 'table table-complex table-bordered table-striped';

        heituUtils.renderTemplate(
            {
                id: searchOpts.containerId || heitu.config.pageContentId,
                templateId: searchOpts.templateId || 'templateSearch'
            }, searchOpts);
        heituCtrl.formUI(searchOpts);
        // 获取初始查询的条件
        var getSearchTerms = function (data) {
            var result = [];
            $.each(data, function (i, d) {
                if (d.value != undefined && d.value != null && d.value != '') {
                    switch (d.type) {
                        case 'dateRange': // 如果是日期间隔，则变换数据格式
                            var begin = d.data[0].code;
                            var end = d.data[1].code;
                            result.push({
                                code: d.code,
                                type: d.type,
                                value: [d.value[begin], d.value[end]]
                            });
                            break;
                        default:
                            result.push({
                                code: d.code,
                                type: d.type,
                                value: d.value
                            });
                            break;
                    }
                }
            });
            return result;
        };
        tableOpts.searchTerms = {terms:getSearchTerms(ui_opts.data),size: 100, index: 0};
        tableOpts.apiUrl = searchOpts.apiUrl;
        if (arguments.length == 2) {
            renderTable({id: tableOpts.id});
            table = new HeituTable(tableOpts);
            table.render();
        } else if (arguments.length == 3) {
            renderTable({id: tableOpts.id});
            table = new HeituTable(tableOpts);
            table.render();
            toolBar = new HeituToolbar(toolBarOpts);
            bindTableAndToolbar(table, toolBar);
        }
        return table;
    }

    function _getQueryString() {
        var result = window.location.href.split('?')[1];
        if (typeof result != 'undefined') {
            return '?' + result;
        } else {
            return '';
        }
    }

    function search(ui_opts, table, searchFirst) {
        var $searchBtn = $('#' + ui_opts.id + '-search');
        $searchBtn.off('click').on('click', function () {
            var data = heituCtrl.getSearchFormData(ui_opts);
            var searchData = {terms: data, size: table.getSize(), index: 0};
            table.refresh(searchData);
        });
    }

    function renderTable(options) {
        heituUtils.renderTemplate(
            {
                id: options.containerId || heitu.config.pageContentId,
                templateId: options.templateId || 'templateTable-container'
            }, options);
    }

    function renderCreateUI(uiOpts, ds) {
        return _renderCreateUI(heituCtrl.getFormUIDSValue(uiOpts, ds));
    }

    function _renderCreateUI(uiOpts) {
        uiOpts.id = 'entity-container';
        uiOpts.btnName = '创建';
        uiOpts.readonly = false;
        uiOpts.valid = true;
        uiOpts.fnSubmit = function () {
            var data = heituCtrl.getCreateFromData();
            if (!!data && !$.isEmptyObject(data)) {
                console.log('create submit data:', data);
                if (heitu.config.isDemoEnv) {
                    alert('编辑结束，提交服务器！');
                    console.log('提交编辑内容', data);
                } else {
                    heituUtils.ajax({
                            url: uiOpts.submitUrl + _getQueryString(),
                            data: JSON.stringify(data),
                            success: function (d) {
                                heituUtils.alert('创建成功');
                            },
                            error: function (e) {
                                heituUtils.alert('创建失败');
                            }
                        }
                    );
                }
            } else {
                heituUtils.alert(commonDefine.tip.NO_CHANGE);
            }
        };
        _renderFormUI(uiOpts);
        return uiOpts;
    }

    function renderUpdateUI(uiOpts, ds) {
        uiOpts.containerId = uiOpts.containerId || heitu.config.pageContainerId;
        if (heitu.config.isDemoEnv) {
            _renderUpdateUI(heituCtrl.getFormUIDSValue(uiOpts, ds, demoApi.getUserUpdateData()));
        } else {
            heituUtils.ajax({
                    url: uiOpts.apiUrl,
                    success: function (d) {
                        var result = _renderUpdateUI(heituCtrl.getFormUIDSValue(uiOpts, ds, d));
                        //heituFramework.renderUpdateData(d, result);
                    }
                }
            );
        }
    }

    function _renderUpdateUI(uiOpts) {
        uiOpts.id = 'entity-container';
        uiOpts.btnName = '编辑';
        uiOpts.readonly = true;
        uiOpts.valid = true;
        uiOpts.fnSubmit = function () {
            var data = heituCtrl.getUpdateFormData();
            if (!!data && !$.isEmptyObject(data)) {
                console.log('create submit data:', data);
                if (heitu.config.isDemoEnv) {
                    alert('编辑结束，提交服务器！');
                    console.log('提交编辑内容', data);
                } else {
                    heituUtils.ajax({
                            url: uiOpts.submitUrl + _getQueryString(),
                            data: JSON.stringify(data),
                            success: function (d) {
                                heituUtils.alert('编辑成功');
                            },
                            error: function (e) {
                                heituUtils.alert('编辑失败');
                            }
                        }
                    );
                }
            } else {
                heituUtils.alert(commonDefine.tip.NO_CHANGE);
            }
        };
        _renderFormUI(uiOpts);
        return uiOpts;
    }

    function renderReadUI(uiOpts, ds) {
        uiOpts.containerId = uiOpts.containerId || heitu.config.pageContainerId;
        if (heitu.config.isDemoEnv) {
            var result = _renderReadUI(heituCtrl.getFormUIDSValue(uiOpts, ds, demoApi.getUserReadData()));
            heituFramework.renderReadData(result);
        } else {
            heituUtils.ajax({
                    url: uiOpts.apiUrl,
                    success: function (d) {
                        var result = _renderReadUI(heituCtrl.getFormUIDSValue(uiOpts, ds, d));
                        heituFramework.renderReadData(result);
                    }
                }
            );
        }
    }

    function _renderReadUI(uiOpts) {
        var _getReadData = function (uiOpts) {
            var readData = [];
            var groups = uiOpts.layout.group;
            var data = uiOpts.data;
            var rdJson = {};
            for (var g in groups) {
                rdJson[g] = {};
                rdJson[g].name = groups[g];
                rdJson[g].data = [];
            }
            rdJson.nogroup = {
                name: 'nogroup',
                data: []
            };
            $.each(data, function (i, item) {
                if (!!item.group) {
                    rdJson[item.group].data.push(item);
                } else {
                    rdJson.nogroup.data.push(item);
                }
            });
            for (var rd in rdJson) {
                readData.push(rdJson[rd]);
            }
            return readData;
        };
        var data = _getReadData(uiOpts);
        var toolBarContainerId = (!!uiOpts.layout && !!uiOpts.layout.floatTop) ? heitu.config.pageReadTopToolBarId : null;
        heituUtils.renderTemplate(
            {
                id: uiOpts.containerId || heitu.config.pageContentId,
                templateId: uiOpts.templateId || 'templateRead'
            }, {data: data, title: uiOpts.title, toolBarContainerId: toolBarContainerId});
        if (!!uiOpts.layout && !!uiOpts.layout.floatTop) {
            var toolBarOpts = uiOpts.layout.floatTop;
            toolBarOpts.containerId = toolBarContainerId;
            var toolBar = new HeituToolbar(toolBarOpts);
            toolBar.render();
        }
        return data;
    }

    var _readElementFnMap = (function () {
        function _setHtml(data, fn) {
            var $read = $('#' + data.code + '-read');
            if (typeof fn != 'undefined') {
                fn(data);
            } else {
                $read.html(data.value);
            }
        }

        function _number(data) {
            _setHtml(data);
        }

        function _money(data) {
            _setHtml(data);
        }

        function _text(data) {
            _setHtml(data);
        }

        function _password(data) {
            _setHtml(data);
        }

        function _textarea(data) {
            _setHtml(data);
        }

        function _editor(data) {
            _setHtml(data);
        }

        function _switch(data) {
            _setHtml(data);
        }

        function _radio(data) {
            var _setRadioHtml = function (data) {
                var $read = $('#' + data.code + '-read');
                if (data.datasource == 'out') {
                    $.each(data.data, function (i, item) {
                        if (item.value == data.value) {
                            $read.html(item.name);
                        }
                    });
                } else {
                    heituUtils.ajax({
                        url: projectConfig.config.ApiBaseUrl + data.datasourceAjax,
                        success: function (d) {
                            data.data = d;
                            $.each(data.data, function (i, item) {
                                if (item.value == data.value) {
                                    $read.html(item.name);
                                }
                            });
                        }
                    })
                }
            };
            _setRadioHtml(data);
        }

        function _checkbox(data) {
            var _setCheckboxHtml = function (data) {
                var $read = $('#' + data.code + '-read');
                var readHtml = '';
                if (data.datasource == 'out') {
                    $.each(data.data, function (i, item) {
                        $.each(data.value, function (j, value) {
                            if (item.value == value) {
                                readHtml += (' ' + item.name);
                            }
                        })
                    });
                    $read.html(readHtml);
                } else {
                    heituUtils.ajax({
                        url: projectConfig.config.ApiBaseUrl + data.datasourceAjax,
                        success: function (d) {
                            data.data = d;
                            $.each(data.data, function (i, item) {
                                $.each(data.value, function (j, value) {
                                    if (item.value == value) {
                                        readHtml += (' ' + item.name);
                                    }
                                })
                            });
                            $read.html(readHtml);
                        }
                    })
                }
            };
            _setCheckboxHtml(data);
        }

        function _dropdown(data) {
            var _setDropdownHtml = function (data) {
                var $read = $('#' + data.code + '-read');
                if (data.datasource == 'out') {
                    $.each(data.data, function (i, item) {
                        if (item.value == data.value) {
                            $read.html(item.name);
                        }
                    });
                } else {
                    if(!!data.valueAjax){
                        heituUtils.ajax({
                            url: qipanUtils.format(projectConfig.config.ApiBaseUrl + data.valueAjax,data.value),
                            success: function (d) {
                                console.log(d);
                                $read.html(d);
                            }
                        })
                    }else{
                        heituUtils.ajax({
                            url: projectConfig.config.ApiBaseUrl + data.datasourceAjax,
                            success: function (d) {
                                data.data = d;
                                $.each(data.data, function (i, item) {
                                    if (item.value == data.value) {
                                        $read.html(item.name);
                                    }
                                });
                            }
                        })
                    }
                }
            };
            _setDropdownHtml(data);
        }

        function _imgUpload(data) {
            function _setImgUploadHtml(data) {
                var $read = $('#' + data.code + '-read');
                $read.html('<img src="' + data.value + '"/>');
            }

            _setHtml(data, _setImgUploadHtml);
        }

        function _imgListUpload(data) {
            _setHtml(data);
        }

        function _date(data) {
            _setHtml(data);
        }

        function _dateRange(data) {
            function _setDateRangeHtml(data) {
                var $read = $('#' + data.code + '-read');
                $read.html(data.data[0].value + ' - ' + data.data[1].value);
            }

            _setHtml(data, _setDateRangeHtml);
        }

        function _cascading(data) {
            function _setCascading(data) {
                var $read = $('#' + data.code + '-read');
                var readHtml = '';
                heituUtils.ajax({
                    url: qipanUtils.format(projectConfig.config.ApiBaseUrl + data.valueAjax, data.value),
                    success: function (d) {
                        var _value = d;
                        $.each(data.data, function (i, select) {
                            if (!!_value[i]) {
                                data.data[i].value = _value[i];
                            } else {
                                data.data[i].value = 0;
                            }
                        });
                        $.each(data.data, function (i, select) {
                            var $thisRead = $('<span id="' + data.code + '-' + select.code + '-read"></span>');
                            $read.append($thisRead);
                            var $thisSelect = $('#' + data.code + '-' + select.code);
                            if (select.datasource == 'out') {
                                $.each(c.data, function (j, selectVal) {
                                    if (selectVal.value == select.value && select.value != 0) {
                                        $thisRead.html(selectVal.name);
                                    }
                                });
                            } else {
                                heituUtils.ajax({
                                    url: qipanUtils.format(projectConfig.config.ApiBaseUrl + select.datasourceAjax, (i == 0 ? null : data.data[i - 1].value)),
                                    success: function (list) {
                                        select.data = list;
                                        $.each(select.data, function (j, selectVal) {
                                            if (selectVal.value == select.value && select.value != 0) {
                                                $thisRead.html(selectVal.name);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }

            _setCascading(data);
        }

        function _list(data) {
            function _renderListValue(data) {
                var $read = $('#' + data.code + '-read');
                $read.html('<div id="' + data.code + '-table-container"></div>');
                data.id = data.code;
                var _createData = $.extend(true, {}, data);
                // 获取行渲染数据
                var _getRowUIData = function (item, cData) {
                    var nRow = {};
                    $.each(item.data, function (j, uiItem) {
                        if (heituCtrl.getDisplayValue(uiItem, cData[uiItem.code]) != false) {
                            nRow[uiItem.code] = heituCtrl.getDisplayValue(uiItem, cData[uiItem.code]);
                        }
                    });
                    return nRow
                };
                // 获取表格渲染参数
                var _getTableOpts = function () {
                    var tableOpts = {
                        entityId: 'id',
                        row: {
                            disableSelect: true,
                            disableRead: true,
                            disableUpdate: true
                        },
                        id: data.code + '-table-container',
                        title: data.name,
                        tableClass: 'table table-complex table-bordered table-striped',
                        bpaging: false,
                        selectMore: false
                    };
                    var tHead = [];
                    tableOpts.thead = tHead;
                    var tData = [];
                    $.each(data.value, function (i, row) {
                        var nRow = {};
                        nRow.id = row.id;
                        $.each(data.data, function (j, uiItem) {
                            if (heituCtrl.getDisplayValue(uiItem, row[uiItem.code]) != false) {
                                if (i == 0) {
                                    tHead.push({
                                        isCheck: true,
                                        name: uiItem.name,
                                        dataName: uiItem.code
                                    })
                                }
                                nRow[uiItem.code] = heituCtrl.getDisplayValue(uiItem, row[uiItem.code]);
                            }
                        });
                        tData.push(nRow);
                    });
                    tableOpts.data = tData; // item.value;
                    return tableOpts;
                };

                // 表格插件参数配置
                var tableOpts = _getTableOpts();
                var table = new HeituTable(tableOpts);
                table.render();
            }

            _renderListValue(data);
        }

        function _tree(data) {
            _setHtml(data);
        }

        return {
            number: _number,
            money: _money,
            text: _text,
            password: _password,
            textarea: _textarea,
            editor: _editor,
            switch: _switch,
            radio: _radio,
            checkbox: _checkbox,
            dropdown: _dropdown,
            imgUpload: _imgUpload,
            imgListUpload: _imgListUpload,
            date: _date,
            dateRange: _dateRange,
            cascading: _cascading,
            list: _list,
            tree: _tree
        };
    })();

    function renderReadData(opts) {
        $.each(opts, function (i, group) {
            $.each(group.data, function (j, item) {
                _readElementFnMap[item.type](item);
            })
        });
    }

    function bindTableAndToolbar(table, toolbar) {
        toolbar.setContainerId(table.getToolBarId());
        toolbar.setDataSource(table.IDataSource());
        toolbar.render();
    }

    var urlRule = {
        getParentUrl: function (type, entity, parentCode, parentValue) {
            return qipanUtils.format('{0}/{1}/{2}.html?{3}={4}', projectConfig.config.bossUrl, type, entity, parentCode, parentValue);
        },
        getUrl: function (entity, action, id) {
            if (!!id)
                return qipanUtils.format('{0}/{1}/{2}/{3}.html', projectConfig.config.bossUrl, entity, action, id);
            else
                return qipanUtils.format('{0}/{1}/{2}.html', projectConfig.config.bossUrl, entity, action);
        },
        getApiUrl: function (entity, action, id) {
            if (!!id)
                return qipanUtils.format('{0}/{1}/{2}/{3}', projectConfig.config.ApiBaseUrl, entity, action, id);
            else
                return qipanUtils.format('{0}/{1}/{2}', projectConfig.config.ApiBaseUrl, entity, action);
        }
    };

    return {
        // 页面
        renderPage: renderPage,
        renderTable: renderTable,
        bindTableAndToolbar: bindTableAndToolbar,
        // CRUD
        renderReadUI: renderReadUI,
        renderReadData: renderReadData,
        renderCreateUI: renderCreateUI,
        renderUpdateUI: renderUpdateUI,
        getSearchUI: getSearchUI,
        renderSearchUI: renderSearchUI,
        search: search,
        getQueryString: _getQueryString,
        // 设置
        urlRule: urlRule
    }
})();