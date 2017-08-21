/**
 *  系统数据配置
 *  系统基础数据
 *  系统模块数据
 **/
var heitu = (function () {
    //var config = {
    //    ddd:'dsf',
    //    page:[{"login":{}}],
    //    model:[{"nav":{}}]
    //}

    var heitu = {};

    // 系统配置
    heitu.config = {
        //  主标题
        mainTitle: '业务系统',
        //  副标题
        subTitle: '管理平台',
        //  系统图标
        logo: 'dist/nifty/img/logo.png',
        loading:'http://wl.swao.cn/heitu/images/loading.gif',
        // 图标信息
        logoInfo: 'boss',
        // 背景
        bg: './dist/heitu/img/bg-img/bg-img-1.jpg',
        // 首页链接
        url: 'index.html',
        // 登录链接
        loginUrl: 'user-login.html',
        // 忘记密码链接
        forgetUrl: 'user-forgetPwd.html',
        // 注册链接
        registerUrl: 'user-register.html',
        // 页面底部信息
        footer: {
            id: 'footer',
            templateId: 'footerContainer',
            websiteVersion: 'V1.0',
            websiteYear: '2015',
            websiteCompany: '上海超级后台管理模板有限公司'
        },
        templateContainerId: 'templateWrapper', //模版包裹层id
        pageContainerId: 'content-container', // 页面级包裹层id
        pageReadTopToolBarId:'pageRead-top-toolBar',// 读页面的工具栏容器id
        templatePageContainerId: 'templatePageContainer', // 页面级模版id
        isShowPageTitle: false,
        pageContentId: 'page-content',
        isDemoEnv: false // 使用demo测试
    };

    // 登录页面配置
    heitu.config.login = {
        titleId: 'loginTitle',
        title: '账户登录Test',
        formId: 'login-form',
        formAction: heitu.config.url,
        rememberPasswordId: 'rememberPassword',
        isShowRememberPassword: true,
        forgetPwdUrlId: 'forgetPwdUrl',
        forgetPwdUrl: heitu.config.forgetUrl,
        registerUrlId: 'registerUrl',
        registerUrl: heitu.config.registerUrl
    };

    // 注册页面配置
    heitu.config.register = {
        formId: 'register-form',
        formAction: '#',
        loginUrl: heitu.config.loginUrl,
        loginUrlId: 'loginUrl'
    };

    // 忘记密码页面配置
    heitu.config.forgetPwd = {
        formId: 'registerPwd-form',
        formAction: '#',
        loginUrl: heitu.config.loginUrl,
        loginUrlId: 'loginUrl'
    };

    // login register forgetPwd 三个页面通用配置
    heitu.config.userCenter = {
        bgOverlayId: 'bg-overlay',
        bg: heitu.config.bg,
        brandTitleId: 'brandTitle',
        brandTitle: heitu.config.mainTitle,
        brandThinId: 'brandThin',
        brandThin: heitu.config.subTitle
    };

    // 系统模块配置
    heitu.model = {};

    // 导航条
    heitu.model.navBar = {
        // 导航条头部
        header: {
            url: heitu.config.url,
            logoSrc: heitu.config.logo,
            logoInfo: heitu.config.logoInfo,
            text: heitu.config.mainTitle
        },
        // 控制左侧菜单
        toggleMenuBtn: {
            isShow: false,
            url: '#',
            icon: 'fa fa-navicon fa-lg',
            newWindow: false
        },
        message: {
            isShow: false,
            url: '#',
            newWindow: true,
            icon: 'fa fa-envelope fa-lg',
            text: '7',
            textCssName: 'badge badge-header badge-warning',
            header: {
                text: '您有0个回复'
            },
            data: [],
            footer: {
                icon: 'fa fa-angle-right fa-lg pull-right',
                text: '显示所有回复',
                url: '#',
                newWindow: true
            }
        },
        notifications: {
            isShow: false,
            url: '#',
            icon: 'fa fa-bell fa-lg',
            text: '3',
            textCssName: 'badge badge-header badge-danger',
            header: {
                text: '您有5个消息.'
            },
            data: [],
            footer: {
                icon: 'fa fa-angle-right fa-lg pull-right',
                text: '显示更多消息',
                url: '#',
                newWindow: true
            }
        },
        mega: {
            isShow: false,
            url: '#',
            icon: 'fa fa-th-large fa-lg',
            data: []
        },
        language: {
            isShow: false,
            flag: './dist/heitu/img/flags/china-flag-small.png',
            shortName: 'CN',
            name: '中国',
            url: '#',
            data: []
        },
        user: {
            isShow: false,
            url: '#',
            headPortrait: '',
            headPortraitInfo: '',
            userName: '',
            header: {
                title: '',
                percent: ''
            },
            footer: {
                url: heitu.config.loginUrl,
                newWindow: false,
                text: '退出',
                icon: 'fa fa-sign-out fa-fw'
            },
            data: []
        }
    };

    // 导航菜单
    heitu.model.nav = {
        shortcut: {
            isShow: false,
            data: [
                {
                    text: '额外的侧边栏',
                    icon: 'fa fa-magic',
                    id: 'js-toggle-aside',
                    url: '#'
                },
                {
                    text: '消息',
                    icon: 'fa fa-bullhorn',
                    id: 'js-alert',
                    url: '#'
                },
                {
                    text: '页面提示',
                    icon: 'fa fa-bell',
                    id: 'js-page-alert',
                    url: '#'
                }
            ]
        },
        mainNav: []
    };

    heitu.id = {
        // 导航栏id和模版id 及导航栏下各个模块的id和模版id
        navBar: {
            id: 'navbar',
            templateId: 'navBarContainer',
            toggleMenuBtn: {
                id: 'navBarToggleMenuBtn-container',
                templateId: 'navBarToggleMenuBtn',
            },
            message: {
                id: 'navBarMessage-container',
                templateId: 'navBarMessage',
                dataId: {
                    id: 'navBarMessageList-container',
                    templateId: 'navBarMessageList'
                }
            },
            notifications: {
                id: 'navBarNotify-container',
                templateId: 'navBarNotify',
                dataId: {
                    id: 'navBarNotifyList-container',
                    templateId: 'navBarNotifyList'
                }
            },
            mega: {
                id: 'navBarMega-container',
                templateId: 'navBarMega',
                dataId: {
                    id: 'navBarMegaList-container',
                    templateId: 'navBarMegaList'
                }
            },
            language: {
                id: 'navBarLanguage-container',
                templateId: 'navBarLanguage',
                dataId: {
                    id: 'navBarLanguageList-container',
                    templateId: 'navBarLanguageList'
                }
            },
            user: {
                id: 'dropdown-user',
                templateId: 'navBarUser',
                dataId: {
                    id: 'navBarUserList-container',
                    templateId: 'navBarUserList'
                }
            }
        },
        // 菜单id和模版id 及菜单下各个模块的id和模版id
        nav: {
            id: 'mainnav-container',
            templateId: 'mainnavContainer',
            shortcut: {
                id: 'mainnavShutcut-container',
                templateId: 'mainnavShutcut'
            },
            mainNav: {
                id: 'mainnavMenu-container',
                templateId: 'mainnavMenu'
            }
        }
    };

    // 设置表单数据
    heitu.CRUDFormUI = {};
    heitu.CRUDFormData = {};

    heitu.imageUploadAccept = {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    };

    heitu.baiduEditorUE = {
        toolbar: [
            [
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload','insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
            ]
        ]
    };

    return heitu;
})();