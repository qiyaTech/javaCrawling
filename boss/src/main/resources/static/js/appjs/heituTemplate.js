/**
 * 模版集合
 * 宾语管理
 */
;
var heituTemplate = (function () {
    var heituTemplate = {
        'templatePageContainer': '<script type="text/html" id="templatePageContainer">' +
            //'            <header id="navbar">' +
            //'            </header>' +
            //'            <div class="boxed">' +
            //'                <div id="content-container">' +
            //'    <%if(!!isShowPageTitle){%>' +
            //'                   <div id="page-title"></div>' +
            //'<%}%>' +
            '                   <div id="page-content"></div>' +
            //'                </div>' +
            //'                <nav id="mainnav-container" style="display: none;">' +
            //'                </nav>' +
            //'            </div>' +
            //'            <footer id="footer">' +
            //'            </footer>' +
            //'            <button id="scroll-top" class="btn"><i class="fa fa-chevron-up"></i></button>' +
            '    </script>',
        'templateSearch': '<script type="text/html" id="templateSearch">' +
            '            <div class="row">' +
            '                <div class="col-lg-12">' +
            '                    <div class="panel">' +
            '                        <div class="panel-heading">' +
            '                            <h3 class="panel-title"><%=title%></h3>' +
            '                        </div>' +
            '                                <form id="<%=id%>-form">' +
            '                        <div class="panel-body">' +
            '                            <div class="col-lg-12">' +
            '                                <div id="<%=id%>-form-list" class=" form-horizontal form-padding">' +
            '                                </div>' +
            '                            </div>' +
            '                        </div>' +
            '                        <div class="panel-footer text-right">' +
            '                            <button class="btn btn-info" type="button" id="<%=id%>-search"><%= !!btnName?btnName:"查询"%></button>' +
            '                        </div>' +
            '                                </form>' +
            '                    </div>' +
            '                </div>' +
            '            </div>' +
            '    </script>',
        'templateTable-container': '<script type="text/html" id="templateTable-container"><div id="<%=id%>"></div></script>',
        'navBarContainer': '<script type="text/html" id="navBarContainer">' +
            '    <div id="navbar-container" class="boxed">' +
            '        <div class="navbar-header">' +
            '            <a href="<%= url%>" class="navbar-brand">' +
            '                <img src="<%= logoSrc%>" alt="<%= logoInfo%>" class="brand-icon">' +
            '                <div class="brand-title">' +
            '                    <span class="brand-text"><%= text %></span>' +
            '                </div>' +
            '            </a>' +
            '        </div>' +
            '        <div class="navbar-content clearfix">' +
            '            <ul class="nav navbar-top-links pull-left">' +
            '                <li class="tgl-menu-btn" id="navBarToggleMenuBtn-container" style="display:none;"></li>' +
            '                <li class="dropdown" id="navBarMessage-container" style="display:none;"></li>' +
            '                <li class="dropdown" id="navBarNotify-container" style="display:none;"></li>' +
            '                <li class="mega-dropdown" id="navBarMega-container" style="display:none;"></li>' +
            '            </ul>' +
            '            <ul class="nav navbar-top-links pull-right">' +
            '                <li class="dropdown" id="navBarLanguage-container" style="display:none;"></li>' +
            '                <li id="dropdown-user" class="dropdown" style="display:none;"></li>' +
            '            </ul>' +
            '        </div>' +
            '    </div>' +
            '</script>',
        'navBarToggleMenuBtn': '<script type="text/html" id="navBarToggleMenuBtn">' +
            '    <!--<li class="tgl-menu-btn">-->' +
            '        <a class="mainnav-toggle" href="<%= url%>" target="<%= !!newWindow?\'_blank\':\'_self\'%>">' +
            '            <i class="<%= icon%>"></i>' +
            '        </a>' +
            '    <!--</li>-->' +
            '</script>',
        'navBarMessage': '<script type="text/html" id="navBarMessage">' +
            '    <!--<li class="dropdown">-->' +
            '        <a href="<%= url%>" data-toggle="dropdown" target="<%= !!newWindow?\'_blank\':\'_self\'%>" class="dropdown-toggle">' +
            '            <i class="<%= icon%>"></i>' +
            '            <span class="<%= textCssName%>"><%= text%></span>' +
            '        </a>' +
            '        <div class="dropdown-menu dropdown-menu-md with-arrow">' +
            '            <div class="pad-all bord-btm">' +
            '                <p class="text-lg text-muted text-thin mar-no"><%= header.text%></p>' +
            '            </div>' +
            '            <div class="nano scrollable">' +
            '                <div class="nano-content template-container" id="navBarMessageList-container" style="display: none;">' +
            '                </div>' +
            '            </div>' +
            '            <div class="pad-all bord-top">' +
            '                <a href="<%= footer.url%>" target="<%= !!footer.newWindow?\'_blank\':\'_self\'%>" class="btn-link text-dark box-block">' +
            '                    <i class="<%= footer.icon%>"></i><%= footer.text%>' +
            '                </a>' +
            '            </div>' +
            '        </div>' +
            '    <!--</li>-->' +
            '</script>',
        'navBarMessageList': '<script type="text/html" id="navBarMessageList">' +
            '    <%if(!!data && data.length > 0){%>' +
            '        <ul class="head-list">' +
            '            <%for(var i =0,len = data.length;i < len; i++){%>' +
            '            <li>' +
            '                <a href="<%= data[i].url %>" target="<%= !!data[i].newWindow?\'_blank\':\'_self\'%>" class="media">' +
            '                    <div class="media-left">' +
            '                        <img src="<%= data[i].headPortrait%>"' +
            '                             alt="<%= data[i].headPortraitInfo%>"' +
            '                             class="img-circle img-sm">' +
            '                    </div>' +
            '                    <div class="media-body">' +
            '                        <div class="text-nowrap"><%= data[i].msg%></div>' +
            '                        <small class="text-muted"><%= data[i].interval%></small>' +
            '                    </div>' +
            '                </a>' +
            '            </li>' +
            '            <%}%>' +
            '        </ul>' +
            '    <%}%>' +
            '</script>',
        'navBarNotify': '<script type="text/html" id="navBarNotify">' +
            '    <!--<li class="dropdown">-->' +
            '        <a href="<%= url%>" data-toggle="dropdown" class="dropdown-toggle">' +
            '            <i class="<%=icon%>"></i>' +
            '            <span class="<%=textCssName%>"><%=text%></span>' +
            '        </a>' +
            '        <div class="dropdown-menu dropdown-menu-md with-arrow">' +
            '            <div class="pad-all bord-btm">' +
            '                <p class="text-lg text-muted text-thin mar-no"><%=header.text%></p>' +
            '            </div>' +
            '            <div class="nano scrollable">' +
            '                <div class="nano-content template-container" id="navBarNotifyList-container">' +
            '                </div>' +
            '            </div>' +
            '            <!--Dropdown footer-->' +
            '            <div class="pad-all bord-top">' +
            '                <a href="<%=footer.url%>" target="<%= !!footer.newWindow?\'_blank\':\'_self\'%>" class="btn-link text-dark box-block">' +
            '                    <i class="<%=footer.icon%>"></i><%=footer.text%>' +
            '                </a>' +
            '            </div>' +
            '        </div>' +
            '    <!--</li>-->' +
            '    <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->' +
            '    <!--End notifications dropdown-->' +
            '</script>',
        'navBarNotifyList': '<script type="text/html" id="navBarNotifyList">' +
            '    <%if(!!data && data.length > 0 ){%>' +
            '    <ul class="head-list">' +
            '        <%for(var i =0,len = data.length;i < len; i++){%>' +
            '        <%if(data[i].type == \'progress\'){%>' +
            '        <li>' +
            '            <a href="#" target="<%= !!data[i].newWindow?\'_blank\':\'_self\'%>">' +
            '                <div class="clearfix">' +
            '                    <p class="pull-left"><%=data[i].title%></p>' +
            '                    <p class="pull-right"><%=data[i].percent%></p>' +
            '                </div>' +
            '                <div class="progress progress-sm">' +
            '                    <div style="width:<%= data[i].percent %>"' +
            '                         class="progress-bar  <%=data[i].progressCssName%>">' +
            '                        <span class="sr-only"><%=data[i].percent%> 已完成</span>' +
            '                    </div>' +
            '                </div>' +
            '            </a>' +
            '        </li>' +
            '        <%}%>' +
            '        <%if(data[i].type == \'notify\'){%>' +
            '        <li>' +
            '            <a href="#" class="media" target="<%= !!data[i].newWindow?\'_blank\':\'_self\'%>">' +
            '                <%if(!!data[i].notifyNumb){%>' +
            '                <span class="<%=data[i].notifyNumbCssName%>"><%=data[i].notifyNumb%></span>' +
            '                <%}%>' +
            '                <%if(!!data[i].icon){%>' +
            '                <div class="media-left">' +
            '                    <span class="<%=data[i].iconWrapper%>">' +
            '                        <i class="<%=data[i].icon%>"></i>' +
            '                    </span>' +
            '                </div>' +
            '                <%}%>' +
            '                <div class="media-body">' +
            '                    <div class="text-nowrap"><%=data[i].notify%></div>' +
            '                    <small class="text-muted"><%=data[i].interval%></small>' +
            '                </div>' +
            '            </a>' +
            '        </li>' +
            '        <%}%>' +
            '        <%}%>' +
            '    </ul>' +
            '    <%}%>' +
            '</script>',
        'navBarMega': '<script type="text/html" id="navBarMega">' +
            '    <!--<li class="mega-dropdown">-->' +
            '        <a href="<%=url%>" class="mega-dropdown-toggle">' +
            '            <i class="<%=icon%>"></i>' +
            '        </a>' +
            '        <div class="dropdown-menu mega-dropdown-menu">' +
            '            <div class="clearfix" id="navBarMegaList-container" style="display:none;">' +
            '            </div>' +
            '        </div>' +
            '    <!--</li>-->' +
            '</script>',
        'navBarMegaList': '<script type="text/html" id="navBarMegaList">' +
            '    <%if(!!data && data.length > 0){%>' +
            '    <%for(var i =0, len = data.length; i < len; i++){%>' +
            '    <%var item = data[i];%>' +
            '    <div class="col-sm-4 col-md-4">' +
            '        <!--Mega menu list-->' +
            '        <ul class="list-unstyled">' +
            '            <li class="dropdown-header"><%= item.title%></li>' +
            '            <%for(var j = 0,jLen = item.data.length; j < jLen; j++){%>' +
            '            <li><a href="<%=item.data[j].url%>">' +
            '                <%if(!!item.data[j].badgeText){%>' +
            '                <span class="<%=item.data[j].badge%>"><%=item.data[j].badgeText%></span>' +
            '                <%}%>' +
            '                <%=item.data[j].title%></a>' +
            '                <%}%>' +
            '        </ul>' +
            '    </div>' +
            '    <%}%>' +
            '    <%}%>' +
            '</script>',
        'navBarLanguage': '<script type="text/html" id="navBarLanguage">' +
            '    <!--<li class="dropdown">-->' +
            '        <a id="demo-lang-switch" class="lang-selector dropdown-toggle" href="<%=url%>" data-toggle="dropdown">' +
            '            <span class="lang-selected">' +
            '            <img class="lang-flag" src="<%=flag%>" alt="<%=name%>">' +
            '            <span class="lang-id"><%=shortName%></span>' +
            '            <span class="lang-name"><%=name%></span>' +
            '            </span>' +
            '        </a>' +
            '        <div id="navBarLanguageList-container" style="display: none;"></div>' +
            '    <!--</li>-->' +
            '</script>',
        'navBarLanguageList': '<script type="text/html" id="navBarLanguageList">' +
            '    <%if(!!data && data.length>0){%>' +
            '    <ul class="head-list dropdown-menu with-arrow" >' +
            '    <%for(var i =0 , len = data.length; i < len;i++){%>' +
            '    <%var country = data[i];%>' +
            '    <li>' +
            '        <a href="<%=country.url%>" class="<%= (i==0?\'active\':\'\')%>">' +
            '            <img class="lang-flag" src="<%=country.flag%>" alt="<%=country.name%>">' +
            '            <span class="lang-id"><%=country.shortName%></span>' +
            '            <span class="lang-name"><%=country.name%></span>' +
            '        </a>' +
            '    </li>' +
            '    <%}%>' +
            '    </ul>' +
            '    <%}%>' +
            '</script>',
        'navBarUser': '<script type="text/html" id="navBarUser">' +
            '    <!--<li id="dropdown-user" class="dropdown">-->' +
            '        <a href="<%=url%>" data-toggle="dropdown" class="dropdown-toggle text-right">' +
            '            <span class="pull-right">' +
            '                <img class="img-circle img-user media-object" src="<%=headPortrait%>"' +
            '                     alt="<%=headPortraitInfo%>">' +
            '            </span>' +
            '            <div class="username hidden-xs"><%=userName%></div>' +
            '        </a>' +
            '        <div class="dropdown-menu dropdown-menu-md dropdown-menu-right with-arrow panel-default template-container">' +
            '            <div class="pad-all bord-btm">' +
            '                <p class="text-lg text-muted text-thin mar-btm"><%=header.title%></p>' +
            '                <div class="progress progress-sm">' +
            '                    <div class="progress-bar" style="width: <%=header.percent%>;">' +
            '                        <span class="sr-only"><%=header.percent%></span>' +
            '                    </div>' +
            '                </div>' +
            '            </div>' +
            '            <!-- User dropdown menu -->' +
            '            <ul class="head-list  template-container" id="navBarUserList-container" style="display: none;">' +
            '            </ul>' +
            '            <!-- Dropdown footer -->' +
            '            <div class="pad-all text-right">' +
            '                <a href="<%=footer.url%>" class="btn btn-primary">' +
            '                    <i class="<%=footer.icon%>"></i><%=footer.text%>' +
            '                </a>' +
            '            </div>' +
            '        </div>' +
            '    <!--</li>-->' +
            '</script>',
        'navBarUserList': '<script  type="text/html" id="navBarUserList">' +
            '    <%for(var i=0 ,len = data.length;i < len;i++){%>' +
            '    <li>' +
            '        <a href="<%=data[i].url%>">' +
            '            <%if(!!data[i].labelTxt){%>' +
            '            <span class="<%=data[i].labelCssName%>"><%=data[i].labelTxt%></span>' +
            '            <%}%>' +
            '            <i class="<%=data[i].icon%>"></i> <%=data[i].text%>' +
            '        </a>' +
            '    </li>' +
            '    <%}%>' +
            '</script>',
        'mainnavContainer': '<script type="text/html" id="mainnavContainer">' +
            '    <div id="mainnav">' +
            '        <div id="mainnav-shortcut">' +
            '            <ul class="list-unstyled" id="mainnavShutcut-container"  style="display:block;">' +
            '            </ul>' +
            '        </div>' +
            '        <div id="mainnav-menu-wrap">' +
            '            <div class="nano">' +
            '                <div class="nano-content" id="mainnavMenu-container" style="display:none;">' +
            '                </div>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</script>',
        'mainnavShutcut': '<script type="text/html" id="mainnavShutcut">' +
            '    <%if(!!data && data.length > 0){%>' +
            '        <%for(var i = 0 , len = data.length; i < len; i++){%>' +
            '        <li class="col-xs-4" data-content="<%=data[i].text%>">' +
            '            <a id="<%=data[i].id%>" class="shortcut-grid" href="<%=data[i].url%>">' +
            '                <i class="<%=data[i].icon%>"></i>' +
            '            </a>' +
            '        </li>' +
            '        <%}%>' +
            '    <%}%>' +
            '</script>',
        'mainnavMenu': '<script type="text/html" id="mainnavMenu">' +
            '    <%var aoeData = data;%>' +
            '    <ul id="mainnav-menu" class="list-group">' +
            '        <%for(var aoe1 = 0 , len1 = aoeData.length; aoe1 < len1; aoe1++){%>' +
            '        <li class="list-header"><%=aoeData[aoe1].name%></li>' +
            '        <%var aoeData1 = aoeData[aoe1].children;%>' +
            '        <%for(var aoe2 = 0 , len2 = aoeData1.length; aoe2 < len2; aoe2++){%>' +
            '        <%var aoeData2 = aoeData1[aoe2];%>' +
            '        <%if(!!aoeData2.hasChild){%>' +
            '        <li data-level="1" class="<%= (aoe1==0&&aoe2==0?\'active-sub active\':\'\')%>">' +
            '            <a href="<%=aoeData2.url%>">' +
            '                <i class="<%=aoeData2.icon%>"></i>' +
            '                                                        <span class="menu-title">' +
            '                                                            <strong><%=aoeData2.name%></strong>' +
            '                                                        </span>' +
            '                <i class="<%=aoeData2.foldIcon%>"></i>' +
            '            </a>' +
            '            <ul class="collapse">' +
            '                <%for(var aoe3 = 0 , len3 = aoeData2.children.length; aoe3 < len3; aoe3++){%>' +
            '                <%var aoeData3 = aoeData2.children[aoe3];%>' +
            '                <li data-level="2" class="<%= (aoe1==0&&aoe2==0&&aoe3==0?\'active-link\':\'\')%>"><a' +
            '                        href="<%=aoeData3.url%>"><%=aoeData3.name%></a></li>' +
            '                <%}%>' +
            '            </ul>' +
            '        </li>' +
            '        <%}else{%>' +
            '        <li data-level="1" class="<%= (aoe1==0&&aoe2==0?\'active-sub active\':\'\')%>">' +
            '            <a href="<%=aoeData2.url%>">' +
            '                <i class="<%=aoeData2.icon%>"></i>' +
            '                                        <span class="menu-title">' +
            '                                            <strong><%=aoeData2.name%></strong>' +
            '                                            <%if(!!aoeData2.msg){%>' +
            '                                                <span class="<%=aoeData2.msgCssName%>"><%=aoeData2.msg%></span>' +
            '                                            <%}%>' +
            '                                        </span>' +
            '            </a>' +
            '        </li>' +
            '        <%}%>' +
            '        <%}%>' +
            '        <%}%>' +
            '    </ul>' +
            '</script>',
        'footerContainer': '<script type="text/html" id="footerContainer">' +
            '    <div class="show-fixed pull-right">' +
            '        <ul class="footer-list list-inline">' +
            '            <li>' +
            '                <p class="text-sm">SEO Proggres</p>' +
            '                <div class="progress progress-sm progress-light-base">' +
            '                    <div style="width: 80%" class="progress-bar progress-bar-danger"></div>' +
            '                </div>' +
            '            </li>' +
            '            <li>' +
            '                <p class="text-sm">Online Tutorial</p>' +
            '                <div class="progress progress-sm progress-light-base">' +
            '                    <div style="width: 80%" class="progress-bar progress-bar-primary"></div>' +
            '                </div>' +
            '            </li>' +
            '            <li>' +
            '                <button class="btn btn-sm btn-dark btn-active-success">Checkout</button>' +
            '            </li>' +
            '        </ul>' +
            '    </div>' +
            '    <div class="hide-fixed pull-right pad-rgt"><%=websiteVersion%></div>' +
            '    <p class="pad-lft">&#0169; <span style="margin:0 5px;"><%=websiteYear%></span><%=websiteCompany%></p>' +
            '</script>',
        'templateForm': '<script type="text/html" id="templateForm">' +
            '    <% var opts = form;%>' +
            '    <div class="panel">' +
            '        <div class="panel-heading">' +
            '            <h3 class="panel-title"><%=opts.title%></h3>' +
            '        </div>' +
            '        <div class="panel-body">' +
            '            <form action="<%=opts.action%>" id="opts.id">' +
            '                <div class="panel-body">' +
            '                    <%for(var i = 0,len = opts.formList.length; i < len;i++){%>' +
            '                    <%var row = opts.formList[i];%>' +
            '                    <div class="row">' +
            '                        <%if(row.length > 0){%>' +
            '                        <%for(var j=0,jLen = row.length;j< jLen;j++){%>' +
            '                        <div class="col-sm-<%=(12/jLen)%>">' +
            '                            <div class="form-group">' +
            '                                <label class="control-label"><%=row[j].label%></label>' +
            '                                <%if(row[j].type == \'text\'){%>' +
            '                                <input type="text" class="form-control" name="<%=row[j].name%> "' +
            '                                       placeholder="请输入<%=row[j].label%>">' +
            '                                <%}%>' +
            '                                <%if(row[j].type == \'password\'){%>' +
            '                                <input type="password" class="form-control" name="<%=row[j].name%>"' +
            '                                       placeholder="请输入<%=row[j].label%>">' +
            '                                <%}%>' +
            '                                <%if(row[j].type == \'select\'){%>' +
            '                                <select class="form-control" name="<%=row[j].name%>">' +
            '                                    <%for(var k = 0,kLen = row[j].options.length; k < kLen;k++){%>' +
            '                                    <option value="<%=row[j].options[k].value%>"><%=row[j].options[k].name%></option>' +
            '                                    <%}%>' +
            '                                </select>' +
            '                                <%}%>' +
            '                                <%if(row[j].type == \'radio\'){%>' +
            '                                <div class="radio">' +
            '                                    <%for(var k =0,kLen = row[j].radios.length;k < kLen;k++){%>' +
            '                                    <label class="form-radio form-normal form-text active">' +
            '                                        <%if(k==0){%>' +
            '                                        <input type="radio" checked name="<%=row[j].name%>">' +
            '                                        <%}else{%>' +
            '                                        <input type="radio" name="<%=row[j].name%>">' +
            '                                        <%}%>' +
            '                                        <%=row[j].radios[k]%>' +
            '                                    </label>' +
            '                                    <%}%>' +
            '                                </div>' +
            '                                <%}%>' +
            '                                <%if(row[j].type == \'checkbox\'){%>' +
            '                                <div class="checkbox">' +
            '                                    <%for(var k =0,kLen = row[j].checkboxs.length;k < kLen;k++){%>' +
            '                                    <label class="form-checkbox form-normal form-primary active form-text">' +
            '                                        <input type="checkbox" name="<%=row[j].name%>"> <%=row[j].checkboxs[k]%></label>' +
            '                                    <%}%>' +
            '                                </div>' +
            '                                <%}%>' +
            '                                <%if(row[j].type == \'textarea\'){%>' +
            '                                <textarea class="form-control" rows="3" name="<%=row[j].name%> "' +
            '                                          placeholder="请输入<%=row[j].label%>"></textarea>' +
            '                                <%}%>' +
            '                            </div>' +
            '                        </div>' +
            '                        <%}%>' +
            '                        <%}else{%>' +
            '                        <div class="col-sm-12">' +
            '                            <div class="form-group">' +
            '                                <label class="control-label"><%=row.label%></label>' +
            '                                <%if(row.type == \'text\'){%>' +
            '                                <input type="text" class="form-control" name="<%=row.name%>">' +
            '                                <%}%>' +
            '                                <%if(row.type == \'textarea\'){%>' +
            '                                <textarea class="form-control" rows="3" name="<%=row.name%> "' +
            '                                          placeholder="请输入<%=row.label%>"></textarea>' +
            '                                <%}%>' +
            '                            </div>' +
            '                        </div>' +
            '                        <%}%>' +
            '                    </div>' +
            '                    <%}%>' +
            '                </div>' +
            '                <div class="text-right">' +
            '                    <%for(var i = 0 ,bLen = opts.btns.length; i < bLen ;i++){%>' +
            '                    <button class="<%=opts.btns[i].btnClass%>" type="submit" id="<%=opts.btns[i].btnId%>">' +
            '                        <%=opts.btns[i].btnName%>' +
            '                    </button>' +
            '                    <%}%>' +
            '                </div>' +
            '            </form>' +
            '        </div>' +
            '    </div>' +
            '</script>',
        'templateTable': '<script type="text/html" id="templateTable">' +
            '    <div class="panel">' +
            '        <div class="panel-heading">' +
            '            <h3 class="panel-title"><%=title%></h3>' +
            '        </div>' +
            '        <div class="panel-body">' +
            '<div id="<%=id%>-toolBarContainer"></div>' +
            '            <div class="table-responsive" data-pattern="priority-columns">' +
            '                <table id="<%=tableId%>" class="<%=tableClass%>" cellspacing="0" width="100%">' +
            '                    <thead>' +
            '                    <tr>' +
            '                        <%for(var i =0, len = thead.length; i < len; i++){%>' +
            '                             <%if(thead[i].isCheck){%>' +
            '                               <%if(thead[i].name == "checkbox"){%>' +
            '                                   <th style="width:30px;padding:10px 0 10px 15px"">' +
            '                                           <input id="<%=tableId%>-checkAll" type="checkbox"/></th>' +
            '                               <%}else{%>' +
            '                                   <%if(thead[i].dataName == "_operation"){%>' +
            '                                   <th style="width:120px;padding:10px 0 10px 0;text-align: center;"><%=thead[i].name%></th>' +
            '                                   <%}else{%>' +
            '                                   <th><%=thead[i].name%></th>' +
            '                                   <%}%>' +
            '                               <%}%>' +
            '                             <%}%>' +
            '                        <%}%>' +
            '                    </tr>' +
            '                    </thead>' +
            '                </table>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</script>',
        'templateToolBar': '<script type="text/html" id="templateToolBar">' +
            '    <div class="tableTools" style="margin-bottom:20px;">' +
            '        <%for(var i = 0 , len = items.length; i < len; i ++){%>' +
            '        <%var item = items[i];%>' +
            '        <%if(!!item.enable){%>' +
            '        <a href="javascript:void(0)" data-action="<%=item.action%>" class="btn btn-primary"><i class="<%=item.icon%>"></i> <%=item.name%></a>' +
            '        <%}%>' +
            '        <%}%>' +
            '    </div>' +
            '</script>',
        'toolBarSettingDialog': '<script type="text/html" id="toolBarSettingDialog">' +
            '<table class="table" id="<%=id%>">' +
            '<thead>' +
            '<tr>' +
            '<th><input type="checkbox" <%= !!isCheckAll?"checked":""%> class="checkAll"/>全选</th>' +
            '<th>默认</th>' +
            //'<th>自定义</th>' +
            '<th>移动</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            '<%for(var i =0,len = data.length;i < len;i++){%>' +
            '<% var item = data[i];%>' +
            '<tr data-index="<%=i%>" data-name="<%= item.name %>" data-dataName="<%= item.dataName %>">' +
            '<td><input type="checkbox" name="tableHeadShow" <%= !!item.isCheck?"checked":""%>  class="checkOne"/></td>' +
            '<td><%= item.name%></td>' +
            //'<td class="newName"><p><%= item.newName %></p></td>' +
            '<td><a href="javascript:void(0)" class="up fa fa-arrow-circle-o-up" style="font-size: 18px;"></a>' +
            '<a href="javascript:void(0)" class="down fa fa-arrow-circle-o-down"  style="font-size: 18px;"></a></td>' +
            '</tr>' +
            '<%}%>' +
            '</tbody>' +
            '</table>' +
            '</script>',
        'templateCtrl-head': '<script type="text/html" id="templateCtrl-head">' +
            '        <div id="<%=prefix%><%=code%>-group" style="min-height:56px;position:relative;z-index:<%=!!zIndex?zIndex:999%>;" class="col-lg-<%=!!className?className: (!!layout && !!layout.col)?12/layout.col:\'12\'%>">' +
            '            <div class="form-group" >' +
            '                <label class="col-md-3 control-label"><%=name%>' +
            '                   <%if(!!notEmpty){%>' +
            '                               <i class="fa fa-asterisk text-danger" style="font-size:12px;"></i>' +
            '                   <%}%>' +
            '               </label>' +
            '               <div class="col-md-9">' +
            '</script>',
        'templateCtrl-after': '<script type="text/html" id="templateCtrl-after">' +
            //'<%if(!!notEmpty){%>' +
            //'<i class="fa fa-asterisk text-danger" style="font-size:12px;"></i>' +
            //'<span class="text-info  text-danger"><i>*</i><%=name%>不能为空</span>' +
            //'<%}%>'+
            '</script>',
        'templateCtrl-footer': '<script type="text/html" id="templateCtrl-footer">' +
            '                <small class="help-block"><%=tip%></small>' +
            '                </div>' +
            '           </div>' +
            '        </div>' +
            '</script>',
        'templateCtrl-text': '<script type="text/html" id="templateCtrl-text">' +
            '<%include("templateCtrl-head")%>' +
            '                    <input type="text" <%= !!readonly?"readonly":""%> name="<%=code%>" class="form-control" placeholder="请输入<%=name%>" value="<%=value%>"/>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-number': '<script type="text/html" id="templateCtrl-number">' +
            '<%include("templateCtrl-head")%>' +
            '                    <input type="text" <%= !!readonly?"readonly":""%> name="<%=code%>" class="form-control" placeholder="请输入<%=name%>" value="<%=value%>"/>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-money': '<script type="text/html" id="templateCtrl-money">' +
            '<%include("templateCtrl-head")%>' +
            '                    <input type="text" tvalue="" ovalue="" <%= !!readonly?"readonly":""%> name="<%=code%>" class="form-control" placeholder="请输入<%=name%>" value="<%=value%>"/>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-switch': '<script type="text/html" id="templateCtrl-switch">' +
            '<%include("templateCtrl-head")%>' +
            '                    <input type="checkbox"  <%= !!readonly?"readonly":""%>  name="<%=code%>" class="form-control" placeholder="请输入<%=name%>" value="<%=value%>"/>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-password': '<script type="text/html" id="templateCtrl-password">' +
            '<%include("templateCtrl-head")%>' +
            '                    <input type="password" <%= (!!readonly)?"readonly":""%> name="<%=code%>" class="form-control" value="<%=value%>"' +
            '                           placeholder="请输入<%=name%>"/>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-textarea': '<script type="text/html" id="templateCtrl-textarea">' +
            '<%include("templateCtrl-head")%>' +
            '                    <textarea <%= (!!readonly)?"readonly":""%> name="<%=code%>" rows="15" class="form-control"' +
            '                       placeholder="请输入<%=name%>"><%=value%></textarea>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-date': '<script type="text/html" id="templateCtrl-date">' +
            '<%include("templateCtrl-head")%>' +
            '                    <div id="<%=code%>-date">' +
            '                        <div class="input-group date">' +
            '                            <input type="text" <%= (!!readonly)?"readonly":""%> name="<%=code%>" class="form-control" value="<%=value%>">' +
            '                            <span class="input-group-addon"><i class="fa fa-calendar fa-lg"></i></span>' +
            '                        </div>' +
            '                    </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-dateRange': '<script type="text/html" id="templateCtrl-dateRange">' +
            '<%include("templateCtrl-head")%>' +
            '                    <div id="<%=code%>-rangeDate">' +
            '                        <div class="input-daterange input-group"  id="<%=code%>-datepicker">' +
            '                            <input type="text" <%= (!!readonly)?"readonly":""%> class="form-control text-left" name="<%=data[0].code%>"' +
            '                                   value="<%=data[0].value%>">' +
            '                            <span class="input-group-addon">to</span>' +
            '                            <input type="text" <%= (!!readonly)?"readonly":""%> class="form-control text-left" name="<%=data[1].code%>"' +
            '                                   value="<%=data[1].value%>">' +
            '                        </div>' +
            '                    </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-cascading': '<script type="text/html" id="templateCtrl-cascading">' +
            '<%include("templateCtrl-head")%>' +
            '                     <%for(var i = 0 , len = data.length; i < len; i++){%>' +
            '                       <%var item = data[i];%>' +
            '                       <div class="col-xs-<%= 12/data.length || 12%>" id>' +
            '                                <div class="form-group">' +
            '                                    <label class="col-md-3 control-label"><%=item.name%></label>' +
            '                                    <div class="col-md-9">' +
            '                                        <select class="selectpicker" style="width:90%;" name="<%=item.code%>" id="<%=code%>-<%=item.code%>">' +
            '                                        </select>' +
            '                                   </div>' +
            '                               </div>' +
            '                       </div>' +
            '                       <%}%>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-editor': '<script type="text/html" id="templateCtrl-editor">' +
            '<%include("templateCtrl-head")%>' +
            '                     <div id="<%=code%>-editor" name="<%=code%>" type="<%=type%>"></div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-list': '<script type="text/html" id="templateCtrl-list">' +
            '<%include("templateCtrl-head")%>' +
            '                   <div class="col-md-12" id="<%=code%>-table-container"></div>' +
            '                   <div class="col-md-12" id="<%=code%>-form-list"></div>' +
            '                   <div class="col-md-12 text-right" style="display: none;" id="<%=code%>-btns">' +
            '                   <button type="button" class="btn btn-default" id="<%=code%>-cancel">取消</button>' +
            '                   <button type="button" class="btn btn-info" id="<%=code%>-save">保存</button>' +
            '                   </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-imgListUpload': '<script type="text/html" id="templateCtrl-imgListUpload">' +
            '<%include("templateCtrl-head")%>' +
            '                       <div id="<%=code%>-imgListUpload" class="wu-example">' +
            '                              <div id="<%=code%>-thelist" class="uploader-list">' +
            '                           <div class="bigShow">请上传图片！</div>' +
            '                       <div class="smallShow"></div>' +
            '</div>' +
            '                              <div id="<%=code%>-picker">选择图片</div>' +
            '                        </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-imgUpload': '<script type="text/html" id="templateCtrl-imgUpload">' +
            '<%include("templateCtrl-head")%>' +
            '                       <div id="<%=code%>-imgUpload" class="wu-example">' +
            '                              <div id="<%=code%>-thelist" class="uploader-list" style="overflow: hidden;">' +
            '                              </div>' +
        '     <%if(!readonly){%>' +
            '                              <div id="<%=code%>-picker" style="margin-top:10px;">选择图片</div>' +
        '<%}%>' +
            '                        </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +

            '</script>',
        'templateCtrlImgUpload-imgWrapper': '<script type="text/html" id="templateCtrlImgUpload-imgWrapper">' +
            '<div class="img-wrapper" data-code="<%=code%>"  data-src="<%=url%>">' +
            '<img src="<%=url%>" class="singleUploadImg"/>' +
            '<%if(!readonly){%><a href="javascript:void(0)" class="img-del"><span class="fa fa-trash"></span></a><%}%>' +
            '</div>' +
            '</script>',
        'templateCtrlImgListUpload-imgWrapper': '<script type="text/html" id="templateCtrlImgListUpload-imgWrapper">' +
            '<div class="img-wrapper <%=!!isDefault?\'active\':\'\'%>" data-code="<%=code%>"  data-src="<%=url%>">' +
            '<img data-code="<%=code%>" src="<%=url%>" />' +
            '<%if(!isBig ){%><a href="javascript:void(0)" class="img-del"><span class="fa fa-trash"></span></a><%}%>' +
            '</div>' +
            '</script>',
        'templateCtrl-checkbox': '<script type="text/html" id="templateCtrl-checkbox">' +
            '<%include("templateCtrl-head")%>' +
            '                       <div class="form-group">' +
            '                           <div class="col-lg-12" id="<%=code%>-checkbox-wrapper">' +
            '                           </div>' +
            '                       </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-checkbox-value': '<script type="text/html" id="templateCtrl-checkbox-value">' +
            '                               <%for(var i = 0 , len = data.length; i< len; i++){%>' +
            '                               <div class="checkbox col-xs-3">' +
            '                                   <input type="checkbox" <%= (!!readonly)?"readonly":""%>  name="<%=code%>" data-value="<%=data[i].value%>" value="<%=data[i].value%>"/>' +
            '                                   <%=data[i].name%>' +
            '                                   </div>' +
            '                               <%}%>' +
            '</script>',
        'templateCtrl-radio': '<script type="text/html" id="templateCtrl-radio">' +
            '<%include("templateCtrl-head")%>' +
            '                   <div class="form-group"> ' +
            '                       <div class="col-lg-12"  id="<%=code%>-radio-wrapper">' +
            '                       </div>' +
            '                   </div>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-radio-value': '<script type="text/html" id="templateCtrl-radio-value">' +
            '                       <%for(var i = 0 , len = data.length; i< len; i++){%>' +
            '                           <div class="radio col-xs-3">' +
            '                            <input type="radio" <%= (!!readonly)?"readonly":""%>  name="<%=code%>" data-value="<%=data[i].value%>" value="<%=data[i].value%>"/>' +
            '                            <%=data[i].name%>' +
            '                            </div>' +
            '                       <%}%>' +
            '</script>',
        'templateCtrl-dropdown': '<script type="text/html" id="templateCtrl-dropdown">' +
            '<%include("templateCtrl-head")%>' +
            '                    <select  name="<%=code%>">' +
            '                    </select>' +
            '<%include("templateCtrl-after")%>' +
            '<%include("templateCtrl-footer")%>' +
            '</script>',
        'templateCtrl-dropdown-value': '<script type="text/html" id="templateCtrl-dropdown-value">' +
            '                        <option value="0">---请选择<%=name%>---</option>' +
            '                       <%if(typeof data !="undefined" && data.length>0){%>' +
            '                        <%for(var i = 0 , len = data.length; i< len; i++){%>' +
            '                        <option value="<%=!!data[i].value?data[i].value:data[i].code%>"><%=data[i].name%></option>' +
            '                        <%}%>' +
            '<%}%>' +
            '</script>',
        'templateCtrl-hide': '<script type="text/html" id="templateCtrl-hide">' +
            '            <input type="hide" id="<%=code%>-hide" name="<%=code%>" value="<%=value%>"/>' +
            '</script>',
        'templateEntity': '<script type="text/html" id="templateEntity">' +
            '        <div class="row">' +
            '            <div class="col-lg-12">' +
            '                <div class="panel">' +
            '                    <div class="panel-heading">' +
            '                        <h3 class="panel-title"><%=title%></h3>' +
            '                    </div>' +
            '                    <form id="<%=id%>-form">' +
            '                    <div class="panel-body">' +
            '                        <div class="col-lg-12">' +
            '                            <div id="<%=id%>-form-list" class=" form-horizontal form-padding">' +
            '                            </div>' +
            '                        </div>' +
            '                    </div>' +
            '                    <div class="panel-footer text-right">' +
            '                        <button class="btn btn-info" type="submit" id="<%=id%>-entity"><%=btnName%></button>' +
            '                    </div>' +
            '                    </form>' +
            '                </div>' +
            '            </div>' +
            '        </div>' +
            '    </script>',
        'templateRead': '<script type="text/html" id="templateRead">' +
            '<%if(!!toolBarContainerId){%>' +
            '<div class="pageRead-top-toolBar" id="<%=toolBarContainerId%>"></div>' +
            '<%}%>' +
            '        <div class="row" style="<%=!!toolBarContainerId?\'margin-top:60px\':\'\'%> ">' +
            '            <div class="panel">' +
            '                <div class="panel-heading">' +
            '                    <h3 class="panel-title"><%=title%></h3>' +
            '                </div>' +
            '                <div class="panel-body">' +
            '                    <%for(var i = 0 , len = data.length; i < len;i++){%>' +
            '                    <%var item = data[i];%>' +
            '                    <%if(item.name == "nogroup"){%>' +
            '                        <%for(var j =0 ,jLen = item.data.length;j < jLen;j++){%>' +
            '                        <%var jItem = item.data[j];%>' +
            '                            <div class="row">' +
            '                                <div class="col-lg-12">' +
            '                                    <p><%=jItem.name%>：<span id="<%=jItem.code%>-read"><%=jItem.value%></span></p>' +
            '                                </div>' +
            '                            </div>' +
            '                        <%}%>' +
            '                    <%}else{%>' +
            '                    <div class="col-md-6 col-lg-4">' +
            '                        <div class="panel" style="border:1px solid rgba(0,0,0,0.1);">' +
            '                            <div class="panel-heading">' +
            '                                <h3 class="panel-title"><%=item.name%></h3>' +
            '                            </div>' +
            '                            <div class="panel-body">' +
            '                                <ul class="list-group">' +
            '                                    <%for(var j =0 ,jLen = item.data.length;j < jLen;j++){%>' +
            '                                    <%var jItem = item.data[j];%>' +
            '                                    <li class="list-group-item">' +
            '                                        <div class="row">' +
            '                                            <div class="col-xs-4" style="text-align: right;">' +
            '                                                <%=jItem.name%>：' +
            '                                            </div>' +
            '                                            <div class="col-xs-8" id="<%=jItem.code%>-read">' +
            '                                                <%=jItem.value%>' +
            '                                            </div>' +
            '                                        </div>' +
            '                                    </li>' +
            '                                    <%}%>' +
            '                                </ul>' +
            '                            </div>' +
            '                        </div>' +
            '                    </div>' +
            '                    <%}%>' +
            '                    <%}%>' +
            '                </div>' +
            '            </div>' +
            '        </div>' +
            '    </script>',
        'templateOptions': '<script type="text/html" id="templateOptions">' +
            '<%for(var i =0,len= data.length;i < len;i++){%>' +
            '<option value="<%=!!data[i].value?data[i].value:data[i].code%>"><%=data[i].name%></option>' +
            '<%}%>' +
            '</script>'
    };
    return heituTemplate;
    //$("#templateWrapper").html(swaoTemplate.join("\n"));
})();
