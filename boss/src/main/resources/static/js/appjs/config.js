/**
 * 项目的通用参数、页面、模块的数据配置
 */
var projectConfig = (function(heitu) {
	var projectSwao = {
		urlRule : heituFramework.urlRule
	// URL路由规则
	};

	var ENV = _env; // DEMO,DEV,TEST,PROD
	var PARAM = {
		'DEMO' : {
			SITE_URL : '',
			API_URL : ''
		},
		'DEV' : {
			//SITE_URL : 'http://120.132.7.71:9021/',
			//API_URL : 'http://120.132.7.71:9021/',
			//fileUploadUrl : 'http://120.132.7.71:9021/uploadfile'

			SITE_URL : 'http://localhost:8015/',
			API_URL : 'http://localhost:8015/',
			fileUploadUrl : 'http://localhost:8015/uploadfile'
			//SITE_URL : 'http://localhost:9021/',
			//API_URL : 'http://localhost:9021/',
			//fileUploadUrl : 'http://localhost:9021/uploadfile',
			//fileDelUrl : 'http://localhost:9021/deletefile/'
		},
		'TEST' : {
			SITE_URL : 'http://120.132.7.71:9021/',
			API_URL : 'http://120.132.7.71:9021/',
			fileUploadUrl : 'http://120.132.7.71:9021/uploadfile'
		},
		'PROD' : {
			SITE_URL : 'http://daydayupboss.qiyadeng.com/',
			API_URL : 'http://daydayupboss.qiyadeng.com/',
			fileUploadUrl : 'http://daydayupboss.qiyadeng.com/'
		}
	};

	// 系统配置
	projectSwao.config = {
		// 通用配置
		bossUrl : PARAM[ENV].SITE_URL, // 系统网址
		ApiBaseUrl : PARAM[ENV].API_URL, // API地址
		fileUploadUrl : PARAM[ENV].fileUploadUrl, // 文件上传网址
		fileDelUrl : PARAM[ENV].fileDelUrl, // 文件删除API地址

		// 通用参数/页面
		mainTitle : '业务系统', // 主标题
		subTitle : '管理平台', // 副标题
		logo : '/images/logo.png', // 系统图标
		logoInfo : 'boss', // 系统图标文字信息
		bg : 'images/bg.jpg', // 系统默认背景（用于登录、注册、找回密码页面等）
		url : 'index.html', // 首页地址
		loginUrl : 'login.html', // 登录地址
		forgetUrl : 'forgetPwd.html', // 忘记密码地址
		registerUrl : 'register.html', // 注册地址
		footer : { // 通用页底部配置
			websiteVersion : 'V1.0',
			websiteYear : '2016',
			websiteCompany : '智橙生活'
		}
	};

	projectSwao.config.useLocalApi = false;// 使用本地api测试

	// 登录页面配置
	projectSwao.config.login = {
		title : '登录',
		isShowRememberPassword : true
	};
	// 注册页面配置
	projectSwao.config.register = {
		formAction : '#'
	};
	// 忘记密码页面配置
	projectSwao.config.forgetPwd = {
		formAction : '#'
	};

	// 系统模块配置
	projectSwao.model = {};
	// 导航条
	projectSwao.model.navBar = {
		// 导航条头部
		header : {
			url : projectSwao.config.url,
			logoSrc : projectSwao.config.logo,
			logoInfo : projectSwao.config.logoInfo,
			text : projectSwao.config.mainTitle
		},
		user : {
			headPortraitInfo : '',
			header : {
				title : '',
				percent : ''
			},
			data : [ {
				url : '#',
				icon : 'fa fa-user fa-fw fa-lg',
				text : '菜单1',
				labelTxt : '',
				labelCssName : ''
			}, {
				url : '#',
				icon : 'fa fa-envelope fa-fw fa-lg',
				text : '菜单2',
				labelTxt : '9',
				labelCssName : 'badge badge-danger pull-right'
			} ],
			footer : {
				url : projectSwao.config.loginUrl,
				text : '退出'
			}
		}
	};
	// 导航菜单
	projectSwao.model.nav = {
		shortcut : {
			isShow : false
		}
	};

	if (!!heitu) {
		$.extend(true, heitu, projectSwao);
	}

	return projectSwao;
})(heitu);
