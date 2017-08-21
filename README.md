# **奇伢爬虫使用介绍**


----------
## **导航**


----------
1. [简介](https://github.com/qiyaTech/javaCrawling#一.简介) 
2. [运行项目](https://github.com/qiyaTech/javaCrawling#运行项目)
*  [运行环境](https://github.com/qiyaTech/javaCrawling#运行环境)
*  [运行必备配置](https://github.com/qiyaTech/javaCrawling#运行必备配置)
* [项目运行](https://github.com/qiyaTech/javaCrawling#项目运行)
*  [运行效果及爬取操作](https://github.com/qiyaTech/javaCrawling#运行效果及爬取操作)
3. [爬取网站文章操作手册](https://github.com/qiyaTech/javaCrawling#爬取网站文章操作手册)
4. [爬取文章配置手册](https://github.com/qiyaTech/javaCrawling#爬取文章配置手册)
* [微信公众号爬取配置](https://github.com/qiyaTech/javaCrawling#微信公众号爬取配置)
* [普通网站爬取配置](https://github.com/qiyaTech/javaCrawling#普通网站爬取配置)
5. [爬取效果展现](https://github.com/qiyaTech/javaCrawling#爬取效果展现)

## 一.**简介**


----------
[奇伢爬虫](https://github.com/qiyaTech/javaCrawling)基于spring boot 、 WebMagic 实现 微信公众号文章、新闻、csdn、info等网站文章爬取，可以动态设置文章爬取规则、清洗规则，基本实现了爬取大部分网站的文章。

#### **(奇伢爬虫技术讨论群：365155351，大家可以加群一起来讨论哦～）**

## 二.**运行项目**


----------
### 

###  1.**运行环境**
项目开发环境：IntelliJ IDEA 15 , **JDK 1.8**

### 2.**运行必备配置**

#### 2.1.**数据库配置**

* 先创建数据库，然后修改配置文件[application-dev.properties](https://github.com/qiyaTech/javaCrawling/blob/master/boss/src/main/resources/application-dev.properties)的如下属性:
* ```spring.datasource.url  =  jdbc:mysql:// 数据库ip地址 : 数据库端口号/数据库名称?useUnicode=true&characterEncoding=UTF-8```
* ```spring.datasource.username = 数据库用户名```
* ```spring.datasource.password = 数据库密码```

#### 2.2.**数据导入**
我们提供了一些基础数据供大家测试，在新建的数据库中执行文件[db_sql.txt](https://github.com/qiyaTech/javaCrawling/blob/master/boss/src/main/resources/data/db_sql.txt)中的sql，就可以将一些必备的表及数据导入数据库了。

#### 2.3.**redis 配置**
* 项目中用到 redis 对一些数据的存储, 如果之前没有安装过 redis, 需要先安装 redis ,然后修改配置文件 [application-dev.properties](https://github.com/qiyaTech/javaCrawling/blob/master/boss/src/main/resources/application-dev.properties)的如下属性:
* ```spring.redis.host = redis ip 地址```
* ```spring.redis.password = redis 密码```
* ```spring.redis.port = redis 端口```


#### 2.4.**七牛云存储配置**
* 注册七牛云账号，登录后在控制台新建“存储空间“，（保存存储空间的名称，配置会用到），并在“个人中心——密钥管理“中查看 ak 与 sk。

* 修改配置文件 [application-dev.properties](https://github.com/qiyaTech/javaCrawling/blob/master/boss/src/main/resources/application-dev.properties)的如下属性:
* `qiniu.bucket = 七牛存储空间名称`
* `qiniu.accessKey = 七牛 ak`
* `qiniu.secretKey = 七牛 sk`
* `qiniu.http.context = 七牛外链域名`

### 3.**项目运行**
* 完成了[运行必备配置](https://github.com/qiyaTech/javaCrawling#运行必备配置)就可以运行项目了**（注：上述配置步骤缺一不可哦～～）**

* 启动项目只需要运行java文件[BossApplication.java](https://github.com/qiyaTech/javaCrawling/blob/master/boss/src/main/java/com/qiya/boss/BossApplication.java)就可以了。

* 项目成功启动后，访问 http://localhost:8015 进入爬虫管理平台。

* 平台登录用户名：admin

* 平台登录密码：admin

### 4.**运行效果及爬取操作**
* 成功登录爬虫管理平台后的界面*（当你看到这个界面时就说明你已成功启动项目了～～）*：

![爬虫管理平台界面]( http://ot2ajy8ew.bkt.clouddn.com/pachong_show.png)

* **爬取操作：**

项目已经运行起来了，现在我们就来看一下怎么爬取一个网站的文章吧。（现在你打开的爬取平台上已经有了我们做好的一些爬取任务配置，只需要按照下面的步骤操作就可以爬取到很多文章了～接下来想自己试着爬取某网站文章可参照[爬取网站文章操作手册](https://github.com/qiyaTech/javaCrawling#爬取网站文章操作手册)。

*  (1).请点击界面上左侧菜单：“爬虫管理“——“任务监控管理“，可以看到有一些我们配置好的爬取任务,下图是“任务监控管理“界面，以及对个别参数进行了说明：

![任务监控界面](http://ot2ajy8ew.bkt.clouddn.com/paqu.png)

*  (2).**执行爬取任务：**接下来我们点击“运行“按钮，这时你可以去查看下控制台，控制台中有输出爬取文章的日志。

* (3).**查看爬取的文章**：我们将爬取到的文章进行解析后保存到了数据库中。
* 你可以在表 “**article**“ 中查看到爬取文章的相关属性：文章标题、作者、列表图片url、发布时间、点赞数、浏览数等等。

* 在表 “**article_detail**“ 中可以查看到爬取文章的内容。

* (4).**清洗爬取的文章：**清洗，即是对所爬文章的样式的调整。具体操作如下：
* 点击你刚爬取任务对应的“清洗“按钮，会出现如下界面：

![清洗文章界面](http://ot2ajy8ew.bkt.clouddn.com/qingxi.png)

* 这个清洗界面中会显示出你爬取的文章，你可以针对个别文章进行清洗，也可以一次性选中多条，或全部清洗。

* 清洗完成后你可以在 “article_detail“ 中找到对应的文章内容，看下与之前对比是不是样式有了一些变化呢～

*（到这里，整个爬取文章以及清洗的操作就完成了。）*

## 三.**爬取网站文章操作手册**


----------

这里主要向大家介绍爬取一个网站文章的具体配置操作：

* 1.**添加站点：**

* 站点即是各个网站或公众号。爬取网站文章首先要添加一个站点，下图是站点管理界面：

![站点管理](http://ot2ajy8ew.bkt.clouddn.com/site_show.png)

* 添加站点界面如下图，添加站点是爬取文章的第一步。

![添加站点](http://ot2ajy8ew.bkt.clouddn.com/add_site.png)

* 2.**添加爬取任务：**

* 任务是对每个站点爬取任务进行管理的，我们每个站点可以有多个爬取任务，这里我们主要针对两种情况进行爬取（**具体爬取的任务配置在[爬取文章的配置手册](https://github.com/qiyaTech/javaCrawling#爬取文章的配置手册)中有详细说明**）：
1. 对网站或公众号每日更新文章进行爬取；
2. 对网站或公众号历史文章进行爬取。

* 任务管理界面如下：

![任务管理界面](http://ot2ajy8ew.bkt.clouddn.com/task_show.png)

* 添加任务：

![添加任务界面](http://ot2ajy8ew.bkt.clouddn.com/add_task.png)

添加任务时，大家要注意两个字段哦：

1. **是否需要手动执行：**
如果选择需要手动执行，则每次重启完项目后需要手动点击 **“任务监控管理“** 界面中对应任务的启动按钮哦～ 否则每次重启完项目后系统中会自动启动爬取任务的。

2. **是否定时执行：**
如果选择定时执行，则在任务启动后系统中每天会有两个时间点定时去爬取文章，否则会一直爬取哦～（在爬取一定时间后线程会休眠一段时间，再继续爬取的）


添加任务最重要的是任务规则配置，详见[爬取文章配置手册](https://github.com/qiyaTech/javaCrawling#爬取文章的配置手册)。

*（添加完任务后就可以执行爬取任务了，爬取任务的操作我们上面已经讲过了哦～）*


## 四.**爬取文章配置手册**


----------
### **网站爬取任务规则配置**


#### 1.**任务规则配置参数介绍：**
* **任务规则配置参数**如下图：

![任务规则](http://ot2ajy8ew.bkt.clouddn.com/rule.png)

* **参数说明：**
* spider : 爬虫配置信息；        
* site : 站点信息； 
*  condition：历史文章爬取条件（只限于历史爬取使用，爬取更新可去掉此参数）；
* rule : 爬取内容的规则配置；  
* wechat: 爬取内容的规则配置；    
* rinseRules : 清理数据配置；  
* isCircle : 是否需要循环爬取（主要用于爬取更新，值为：true / false）；
*  circleInterval   循环间隔时间单位（秒）。

#### **任务规则参数详细配置：**
##### (1).**spider 爬虫配置：** 

* **参数说明：**
* thread : 线程数；
* startUrl：开始爬取页面url，以infoQ网站为列，我们爬取新闻列表，开始地址为下图所示：

* downloader：下载器设置，普通网站一般设为空值；
weChatDownloader ：微信爬取专用。微信爬取网址必须设置参数。
dynamicProxyHttpClientDownloader：支持动态代理ip，Downloader。在没有设置下载器，动态动态代理参数 （dynamicProxy）为true时 会自动设置本下载器。设参数置参考site 站点配置：
* **processer：处理器**
* **项目中处理器主要有以下处理器：**

1.	**weChatUpdatePageProcess**：微信爬取更新专用；
需设置下载器weChatDownloader
**微信公众号更新爬取示例：**
```{```
```"spider": {```
```"thread": 1,```
``` "startUrl": "http://weixin.sogou.com/weixin?type=1&s_from=input&query=oschina2013&ie=utf8&_sug_=n&_sug_type_=",```
``` "downloader": "weChatDownloader",```
```"processer": "weChatUpdatePageProcess",```
```"siteid": 13,```
```"pipeline": ["exceptionPipeline","rinseCommPipeline","rinseWeChatPipeline","mySqlArticlePipeline"]```
```},```
``` "site": {```
``` "domain": "mp.weixin.qq.com",```
```"proxy": [],```
```"retry": 3,```
```"sleepTime": 5000,```
```"headers": [],```
```"userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36"```
```},```
```"condition": {```
```"endTime": "",```
``` "startTime": ""```
```},```
`"wechat": {`
`"name": "oschina2013",`
`"uid": "oschina2013",`
`"url": "http://weixin.sogou.com/weixin?type=1&s_from=input&query=oschina2013&ie=utf8&_sug_=n&_sug_type_="`
`},`
`"rule": {`
`"detailregex": "http://mp\\.weixin\\.qq\\.com/s?\\S+",`
`"listregex": "http://mp.weixin.qq.com/profile?src",`
`"loadlistxpath":"//*[@id='main']/div[4]/ul/li[1]/div/div[2]/p[1]",`
`"listxpath": "",`
`"detailxpath": [`
` {`
`"name": "title",`
`"reg": "",`
`"value": "//*[@id='activity-name']\/text()"`
`},`
` {`
` "reg": "",`
`"value": "//*[@id='post-date']\/text()",`
`"name": "publicTime",`
`"simpleDateFormat": "yyyy-MM-dd"`
` },`
` {`
` "name": "author",`
` "reg": "",`
`"value": "//*[@id='post-user']\/text()"`
`},`
`{`
`"name": "beforeContent",`
` "reg": "",`
` "value": "//*[@id='js_content']"`
`}`
`]`
` },`
`"isCircle": true,`
`"circleInterval":10800,`
`"rinseRules":[`
`{`
` "action": "add",`
` "type":"attr",`
`  "cssquery": "img",`
` "name": "width",`
` "vluae": " 100%"`
`},`
` {`
` "action": "add",`
` "type":"attr",`
`  "cssquery": "img",`
`  "name": "height",`
`"vluae": "auto"`
`},`
` {`
` "action": "replace",`
`   "type":"attr",`
`  "cssquery": "img",`
` "name": "style",`
` "source": "width: \\d+[.\\d+]px",`
` "target": "width: 100%"`
`},`
` {`
`"action": "replace",`
` "type":"attr",`
` "cssquery": "img",`
`  "name": "style",`
`   "source": "width: auto",`
` "target": "width: 100%"`
` },`
` {`
`  "action": "copy",`
` "type":"attr",`
`  "cssquery": ".video_iframe",`
` "source": "data-src",`
`"target": "src"`
` }`
`]`
`}`

2.  **weChatHistoryPageProcess**：微信爬取历史专用；
需设置下载器weChatDownloader
**微信公众号历史爬取示例:**
`{`
`"spider": {`
`"thread": 1,`
`"startUrl": "https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5NzM0MjcyMQ==&f=json&frommsgid=&count=10&scene=124&is_ok=1&uin=777&key=777&pass_ticket=QRnKzE8eCui1gEzcgTzGlMMUSP9d7DzbTGtoQOU1OCKoI9yMgM1eTn2dNoBQlv0K&wxtoken=&x5=1&f=json",`
`"downloader": "weChatDownloader",`
`"processer": "weChatHistoryPageProcess",`
`"siteid": 13,`
` "pipeline": ["rinseCommPipeline","rinseWeChatPipeline","mySqlArticlePipeline"]`
` },`
`"site": {`
`"domain": "mp.weixin.qq.com",`
` "proxy": [],`
` "retry": 3,`
` "sleepTime": 3000,`
` "headers": [`
` {`
` "name": "Cookie",`
`"value": "news_commid=oDOGxv_sCQKLRbPUnpFOlBxc12sk; wxtokenkey=3af5afc4ad6dc8d50ddb4155017fee8169c40528ecbaedb532c0e00323feea4d; wxticket=898662085; wxticketkey=28a040f7ad3354057a1de315330438a969c40528ecbaedb532c0e00323feea4d; wap_sid=CIDixZ0HEkBiWmtxZTF6QkpTazIzWk1sbUhINUFUek9kM1NBOTFrUE9hOGJId19aeVJOYUlxQy1lMTdXcWxYNmxZR1FuSUYtGAQgpBQogZiS9wgwiYGZxwU=; wap_sid2=CIDixZ0HElxvMXNia1Z0Q1ZrTHNfYWdiODlFVWRlUTJ0cS1qU0U5YUlrbmFLX1h6bDN3WFJDUW5RaG14TVNTc0xUb1hLNEszNHB2UHgxQUt6SEFpcDFKaFpyVDh5b01EQUFBfjCJgZnHBQ=="`
`}`
` ],`
`"userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"`
`},`
` "condition": {`
` "startTime":"",`
` "endTime":""`
` },`
` "wechat": {`
` "name": "",`
` "uid": "",`
` "url": "https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5NzM0MjcyMQ==&f=json&frommsgid=[MESSAGEID]&count=10&scene=124&is_ok=1&uin=777&key=777&pass_ticket=QRnKzE8eCui1gEzcgTzGlMMUSP9d7DzbTGtoQOU1OCKoI9yMgM1eTn2dNoBQlv0K&wxtoken=&x5=1&f=json"`
` },`
` "rule": {  //同上例子配置  },`
`"isCircle": false,`
`"rinseRules":[   // 同上例子配置 ]`
` }`

3. **XPathWebPageProcess**：普通网站爬取使用（包括下一页地址页面上有的历史爬取）。
**普通网站爬取示例：**
`{`
`"spider" : {`
` "thread" : 1,`
`"startUrl" : "http://www.ceibsreview.com/list/index/classid/128",`
`"downloader" : "",`
`"processer" : "XPathWebPageProcess",`
`"siteid" : "18",`
`"pipeline" : [`
` "rinseCommPipeline",`
` "mySqlArticlePipeline"`
`  ]`
`},`
`"site" : {`
` "domain" : "www.ceibsreview.com",`
` "retry" : 3,`
` "sleepTime" : 5000,`
` "headers" : [`
`  {`
`  "name" : "Content-Type:",`
` "value" : "application\/json"`
`   }`
`],`
`"cookies" : [`
` {`
`"name" : "mm_session",`
`  "value" : "application\/json"`
`  }`
`  ],`
`"userAgent" : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31"`
` },`
` "rule" : {`
`  "detailregex" : "http://www\\.ceibsreview\\.com/show/index/classid/\\d+/id/\\d+",`
`"listregex" : "http://www\\.ceibsreview\\.com/list/index/classid/128",`
` "listxpath" : "//*[@id='right']/ul[@class='lists']/li/div[1]/a",`
` "detailxpath" : [`
`  {`
`"name" : "title",`
` "reg" : "",`
`  "value" : "//*[@id='article']/h3/text()"`
` },`
` {`
` "reg" : "\\d{4}年\\d+月",`
` "value" : "//*[@id='article']/div[1]/span/text()",`
` "name" : "publicTime",`
` "simpleDateFormat" : "yyyy年MM月"`
`  },`
`  {`
`"name" : "author",`
` "reg" : "(.*?) ",`
` "value" : "//*[@id='article']/div[1]/text()"`
` },`
` {`
`  "name" : "beforeContent",`
`  "reg" : "",`
`  "value" : "//*[@id='article']/div[3]"`
` },`
`  {`
`"name":"introduce",`
`"reg":"",`
`"value":"//div[@class='detail-box']/div[@class='summary']/text()"`
`  }`
]`
` },`
` "isCircle" : true,`
`"rinseRules":[ //同上列子 ]`
`}`

4.  **XPathWebMorePageProcess**：普通网站历史爬取使用（使用条件：历史文章列表的地址类似于下一页拼接的形式，如：infoQ: 、csdn: ）；
**普通网址历史爬取示例：**
`{`
`"spider" : {`
` "thread" : 1,`
`"startUrl" : "http://blog.csdn.net/?&page=2",`
` "downloader" : "",`
` "processer" : "XPathWebMorePageProcess",`
` "siteid" : "1",`
` "pipeline" : [`
` "rinseCommPipeline",`
` "mySqlArticlePipeline"`
` ]`
` },`
`"site" : {`
`"domain" : "blog.csdn.net",`
` "retry" : 3,`
` "sleepTime" : 222,`
`"headers" : [`
` {`
`  "name" : "Content-Type:",`
` "value" : "application\/json"`
`    }`
` ],`
`  "cookies" : [`
`  {`
` "name" : "mm_session",`
`  "value" : "application\/json"`
`    }`
`  ],`
`  "userAgent" : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31"`
` },`
`"condition" : {`
`  "addPage":1,`
` "thisPage":2,`
`"startTime":"2017-03-01",`
`"endTime":"2017-03-30"`
`  },`
` "rule" : { //同上列子配置 },`
`  "isCircle" : false,`
` "rinseRules":[ //同上列子配置 ]`
`}`

5.  **XPathWebPageListImageProcess**：如果网站中含有图片列表，需要取图片，需在规则里增加两个元素，一个是列表的xpath，一个是图片的xpath，如果之前有文章详情的xpath，详情的xpath要在列表的xpath路径范围之内。
**图片列表网址爬取示例：**
`{`
`"spider" : {`
` "thread" : 1,`
`  "startUrl" : "http:\/\/www.iresearch.cn",`
`"downloader" : "",`
`"processer" : "XPathWebPageListImageProcess",`
`  "siteid" : "36",`
`"pipeline" : [`
` "rinseCommPipeline",`
`"mySqlArticlePipeline"`
` ]`
` },`
` "site" : {`
`"retry" : 3,`
` "sleepTime" : 5000,`
` "cookies" : [`
` {`
` "name" : "mm_session",`
` "value" : "application\/json"`
`}`
`],`
`   "headers" : [`
`{`
` "name" : "Content-Type:",`
` "value" : "application\/json"`
` }`
`],`
`"domain" : "www.iresearch.cn",`
`"userAgent" : "Mozilla\/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit\/537.31 (KHTML, like Gecko) Chrome\/26.0.1410.65 Safari\/537.31"`
`},`
` "rule" : {`
`"itemxpath" : "\/\/div[@class='m-item f-cb z-sort-1']",`
` "detailregex" : "http:\/\/news\\.iresearch.cn\/content\/\\d+\/\\d+\/\\d+.shtml",`
`"listregex" : "http:\/\/www\\.iresearch\\.cn",`
`"listxpath" : "\/\/div[2]\/h3\/a",`
` "itemImagexpath" : "\/\/div[@class='u-img']\/a\/img\/@src",`
`"detailxpath" : [],`
` },`
`"rinseRules" : [],`
`"isCircle" : true`
`}`

* siteid：站点Id ,需要在后台管理中新建站点后生成的Id，站点管理页面获取。

* pipeline：管道设置。值为数组，后台会根据数组中的顺序执行。
项目中现有的管道主要有以下几种：
1.  exceptionPipeline：异常处理pipe；现在微信文章更新爬取需要。 

2.  rinseCommPipeline：数据清洗pipe (公众号网站必写，可根据参数rinseRules的配置进行清洗)；

3. rinseWeChatPipeline：微信列表图片修改（公众号爬取必选pipe）；

4.  mySqlArticlePipeline：数据库保存文章操作（公众号网站必写）。

5. 网站爬取应用场景示例管道顺序：
**普通网址爬取**：
rinseCommPipeline 、 mySqlArticlePipeline

**微信公众号历史爬取**：exceptionPipeline 、rinseCommPipeline、rinseWeChatPipeline 、mySqlArticlePipeline

**微信公众号更新爬取**： rinseCommPipeline、rinseWeChatPipeline 、 mySqlArticlePipeline


#####  (2).**Site 站点配置：**
* 站点配置如下图：

![站点配置](http://ot2ajy8ew.bkt.clouddn.com/site_peizhi.png)

![站点配置](http://ot2ajy8ew.bkt.clouddn.com/site_peizhi1.png)

* **参数说明：**
* retry：网址爬取失败重试次数
* sleepTime：休眠时间
* cookies：请求Cookie设置，值为数组，一般网站爬取不需要设置。
* headers：请求头设置，一般网站爬取不需要设置。
* domain：网站域名
* userAgent：浏览器标示设置
* dynamicProxy：动态代理设置默认false 设置ture 会启动动态代理ip爬取文章。（可以不填）

##### (3).**Condition 配置：(目前仅用于爬取历史)**
* condition的配置如下：

![爬取历史配置](http://ot2ajy8ew.bkt.clouddn.com/condition.png)

* **参数说明：**
* startTime：爬取的开始时间（公众号和网站爬取历史必用参数）
* endTime：爬取的结束时间（公众号和网站爬取历史必用参数）
* thisPage：个别网站爬取使用参数，与参数addPage 一起使用。

（注：若用了thisPage 和 addPage 两个参数，则上述参数“sprider”中process配置为：XPathWebMorePageProcess)*

* **适用场景：**

当网站加载更多时当前地址后面会增加一个参数，参数值为数字，并且有规律的增加。下一页地址类似于（重点是要有“＝”，并且后面紧跟数字），如：http://blog.csdn.net/?&page=2

##### (4).**rule规则配置**
* rule规则配置这部分配置是获取页面上的链接或抓取文章内容的字端的规则配置。

![rule配置](http://ot2ajy8ew.bkt.clouddn.com/rule_prizhi.png)

* detailregex：文章详细页面url 规则
* listregex：文章列表页面url规则
* loadlistxpath：爬取历史文章专用，用来获取页面上历史文章列表url。

loadlistxpath 参数适用场景：文章列表页面中有下一页（或加载更多）的地址。用了此参数就不可再用condition 配置中的thisPage 和addPage 两个参数。

* listxpath：列表页面url
* detailxpath：爬取详细内容规则配置，值为数组
* name : 爬取的名称，对应实体中的属性名。必输字段
* reg：匹配正则表达式，可为空。
* value：爬取规则xpath, 可在网站开发者工具中选择你需要爬取的内容，右键－复制xpath,这样可复制获取某一元素的xpath，方便快捷，准确率高。
* simpleDateFormat：目前只用于时间格式化。

##### (5).**wechat规则配置（微信仅公众号爬取有用）**
* name： 微信名
* uid： 微公众号id
* url ： url 地址用法见微信爬取不同点

##### (6).**rinseRules 清洗配置**
此清洗配置可以解决我们在爬取网站文章时纠结与文章排版与你需要的不一致问题。

* 清洗配置：

![清洗配置](http://ot2ajy8ew.bkt.clouddn.com/rule_clean.png)

*（上图包含了我们清洗页面中的常用配置。如：增加(add)、替换局部(replace)、替换全部(fullreplace)、删除(delete)，还有一个复制操作（copy），目前主要复制属性值）。*

* **清洗参数说明：**
* action：操作动作说明
值为：add(添加)、replace(替换局部)、fullreplace(全部  替换)、delete(删除)、
copy(复制)。

* type：操作类型。
值为：attr(属性)、text（文本）、node（元素）

* cssquery：css选择器   
选择器详细规则请见：https://jsoup.org/cookbook/extracting-data/selector-syntax

* name：属性名，只适于对元素属性操作中使用。

* source：替换的源数据，所有类型替换都支持正则表达式。

* target：替换后的目标数据。

* value：值（添加 到attr／text／node 中的值）。

* **添加（add）操作**

* 支持类型： attr(属性)、text（文本）、node（元素）、 style(内联样式)
* **添加属性（attr）**：需要参数：action、type 、cssquery、name（属性名）value（属性值）  
* 应用场景：例我们要在所有的图片标签上加上alt提示属性。
* 事例：
`{`
`"action": " add ",`
` " type ":" attr ",`
`" cssquery ": "img",`
`" name": "alt",`
`" value ":"精益阅读"`
`}`

* **添加文本（text）**：需要参数：action、type 、cssquery、value （值）  
* 应用场景：例如我们要在所有的文章最后加上上加上一句话。版权归奇伢科技所有。

* **添加节点（node）**：需要参数：action、type 、cssquery、value （值格式为html） 
* 应用场景：例如我们要在所有的文章最后加上上加上一句话。版权归奇伢科技所有。
* 事例：
`{`
`  "action": " add ",`
` " type ":" node ",`
` " cssquery ": "body",`
` "value ":"<p>版权归奇伢科技所有</p>"   `
}

* **添加内联系样式（style）**：需要参数：action、type 、cssquery、vluae （样式属性值） ，name（样式属性名） 
* 事例：
`{`
`  "action": "add",`
` "type":"style",`
` "cssquery": "p",`
` "name": "font-size",`
`"value": " 19px"`
`  }`

* **替换局部（replace）：**
* 支持类型： attr(属性)、text（文本）、node（元素）

* 替换属性 （attr）：需要参数：action、type 、cssquery、name（属性名）、source（源数据）、 target（目标数据数据）  

* 应用场景：如微信的图片都有防盗链处理，我们要将微信的图片地址前加上改为我们地址进行处理。

* 事例：
`{`
` "action": "replace",`
` "type":"attr",`
` "cssquery": "img[src]",`
`"name": "style",`
` "source": "http://mmbiz.qpic.cn/mmbiz",`
`"target": "http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz"`
` }`

* 替换样式属性（style）：需要参数：action、type 、cssquery、，name（样式属性名） ，source（源样式属性值数据）、 target（目标样式属性值数据）

* **替换全部（fullrepalce）：**

* 替换节点是将整个节点变成字符后进行替换的.
* 替换全部无类型，需要参数：action、source（源数据）、 target（目标数据数据
* 事例(将所有“程序员”修改为“程序猿)：
`{`
` "action": " fullreplace",`
`"source":"程序员",`
` "target": "程序猿"`
`}`

* **删除 （delete）：**
* 支持类型： attr(属性)、text（文本）、node（元素）、内联样式（style）
* 删除属性（attr）需要参数：action、type 、cssquery、name（属性名）
* 事例：（删除图片标签上的样式style属性）
`{`
` "action": "delete",`
`"type":" attr ",`
`  "cssquery": "img",`
`"name": "style",`
` }`

* 删除文本（text）：需要参数：action、type 、cssquery
* 删除样式属性（style）：需要参数：action、type 、cssquery、，name（样式属性名，支持单个或多个属性名，多个用 “:” 分隔。

* **复制（copy）**
* 支持类型： attr(属性)
* 复制属性（attr） ：需要参数：action、type 、cssquery、source（源属性名） 、target（目标属性名）


### **微信爬取**
微信爬取的基本规则于普通网址一样，主要注意以下几点就可以了：

#### **历史爬取** 

**历史爬取配置步骤如下：**

* 1.电脑设置开启代代理手机设置代理为电脑的ip
* 2.微信关注要爬取的公众号
* 3.选择公众号点击查看历史 ，进入工作号文章列表 开始向上滑我们在代理软件找到如下地址：

![](http://ot2ajy8ew.bkt.clouddn.com/wechat.png)

`https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5MTcyMzEyMA==&f=json&frommsgid=1000000027&count=10&scene=124&is_ok=1&uin=777&key=777&pass_ticket=RLFa08mwKkLcExQhkugeJbL5fWrSbxHkAsITPcEc0BTVHSc6tqW9bJt71z9pWIWw&wxtoken=&x5=1&f=json`

* 4.将以上地址中的 frommsgid 的值  如: 1000000027  删除后再设置为 json配置中 spider ——> startUrl 的值

* 5.将以上地址中的frommsgid 的值  如1000000027  改成 [MESSAGEID]  删除后设置为  json配置 wechat->url参数的值。如下图:

![设置url](http://ot2ajy8ew.bkt.clouddn.com/we_pei.png)

* 6.将第三点图中 红色部分 Cookie 参数和值，设置到 json 配置 headers 参数数组中如下：

![设置cookie](http://ot2ajy8ew.bkt.clouddn.com/cookie.png)

* 7.剩下就是文章字段规则和清洗规则的配置了，可参考前文中的详解哦。

#### **更新爬取**

**下面是微信公众号更新爬取的步骤：**

* 1.找到公众号的微信号 
* 2.将以下链接中 "woshipm" 修改为你要爬取的公众号的微信号
`http://weixin.sogou.com/weixin?type=1&s_from=input&query=woshipm&ie=utf8&_sug_=n&_sug_type_= `

* 3.将修改好的地址配置为 spider ——> startUrl 和 wechat——>url 参数的值。uuid 必须设置微信号如图:
![](http://ot2ajy8ew.bkt.clouddn.com/weichat_geng.png)

* 4.剩下就是文章字段规则和清洗规则的配置了，可参考前文中的详解哦。

**（到这里我们整个配置就已经完成了，大家可以爬取文章了～）**

## 五.**爬取效果展现**


----------
下面给大家展示下我们用**奇伢爬虫**爬取文章后在我们 **精益阅读APP** 以及  **精益阅读小程序** 上的展现：

大家可以扫描下方二维码进入小程序哦。进入小程序后可以 **点击右上角——>关于精益阅读——>相关公众号**  进入我们公众号，有什么问题也可以在公众号中联系客服哦～
![精益阅读小程序](http://ot2ajy8ew.bkt.clouddn.com/gh_7edeabb4068b_258.jpg)

![精益阅读界面展现](http://ot2ajy8ew.bkt.clouddn.com/jingyi5.jpg)

![精益阅读界面展现](http://ot2ajy8ew.bkt.clouddn.com/jingyi4.jpg)








