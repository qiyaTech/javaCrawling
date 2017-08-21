package com.qiya.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JavaType;
import com.qiya.middletier.webmagic.comm.configmodel.RinseRule;
import com.qiya.middletier.webmagic.util.RinseHtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiya.middletier.webmagic.comm.configmodel.WebmagicConfig;
import com.qiya.middletier.webmagic.monitor.MySpiderMonitor;
import com.qiya.middletier.webmagic.process.AbstractCommPageProcess;
import com.qiya.middletier.webmagic.scheduler.DelayQueueScheduler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by qiyamac on 2017/3/24.
 */
@RunWith(SpringRunner.class)
public class JsonTest {


	private ApplicationContext context;

	@Test
	public void testEnableJsoupHtmlEntityEscape() throws Exception {
		String json = "{\n" + "  \"spider\" : {\n" + "    \"pipeline\" : [\n" + "      \"mailPioelne\"\n" + "    ],\n" + "    \"thread\" : 11,\n" + "    \"startUrl\" : \"www.qqq.com\\/new\",\n" + "    \"downloader\" : \"weichatDownloaer\",\n" + "    \"processer\" : \"AppWei\",\n" + "    \"siteid\" : \"sxx\"\n" + "  },\n" + "  \"site\" : {\n" + "    \"domain\" : \"www.qq.com\",\n" + "    \"proxy\" : [\n" + "      {\n" + "        \"schema\" : \"\",\n" + "        \"username\" : \"\",\n" + "        \"password\" : \"\",\n" + "        \"name\" : \"xxx\",\n" + "        \"prot\" : 8080,\n" + "        \"address\" : \"\"\n" + "      }\n" + "    ],\n" + "    \"retry\" : 3,\n" + "    \"sleepTime\" : 222,\n" + "    \"headers\" : [\n" + "      {\n" + "        \"name\" : \"Content-Type:\",\n"
				+ "        \"value\" : \"application\\/json\"\n" + "      }\n" + "    ],\n" + "    \"cookies\" : [\n" + "      {\n" + "        \"name\" : \"mm_session\",\n" + "        \"value\" : \"application\\/json\"\n" + "      }\n" + "    ],\n" + "    \"userAgent\" : \"Mozilla\\/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit\\/601.1.46 (KHTML, like Gecko) Version\\/9.0 Mobile\\/13B143 Safari\\/601.1\"\n" + "  },\n" + "  \"condition\" : {\n" + "    \"endTime\" : \"\",\n" + "    \"startTime\" : \"\"\n" + "  },\n" + "  \"wechat\" : {\n" + "    \"name\" : \"yazhu\",\n" + "    \"uid\" : \"\",\n" + "    \"url\" : \"\"\n" + "  },\n" + "  \"rule\" : {\n" + "    \"detailregex\" : \"url\",\n" + "    \"listregex\" : \"url\",\n"
				+ "    \"listxpath\" : \"\\/\\/div[@class='unlogin-content-info']\",\n" + "    \"detailxpath\" : [\n" + "      {\n" + "        \"name\" : \"title\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/div[@class='unlogin-content-info']\\/\\/h1[@class='unlogin-content-info-title']\\/text()\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"publicTime\",\n" + "        \"reg\" : \"\",\n" + "\t\t  \"simpleDateFormat\"   :  \"yyyy-MM-dd\",\n" + "        \"value\" : \"\\/\\/div[@class='unlogin-content-info']\\/\\/h1\\/\\/strong\\/text()\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"introduce\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"div[@class='deng']\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"author\",\n"
				+ "        \"reg\" : \"\",\n" + "        \"value\" : \"div[@class='unlogin-content-detail fl']\\/\\/ul\\/\\/li[4]\\/\\/a\\/text()\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"content\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/p[@class='unlogin-content-desc-cont hide']\\/text()\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"readCount\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/p[@class='unlogin-content-desc-cont hide']\\/text()\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  \"isCircle\" : false\n" + "}";
		ObjectMapper objectMapper = new ObjectMapper();
		WebmagicConfig webmagicConfig = objectMapper.readValue(json, WebmagicConfig.class);
		System.out.print("111");
	}

	public void ma() throws Exception {

		String sss = "https://mp\\.weixin\\.qq\\.com/mp/profile_ext?action=getmsg\\S+";
		String bbb = "  https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MzA5MTEzNTc3MQ==&f=json&frommsgid=&count=10&scene=124&is_ok=1&uin=MTk0MTAwODY0MA%3D%3D&key=98f7f7f6606d9d65f5f2e09a0b39b6b54218d17fa935c4eb68e738b83c01c9c616cc545e40701b227aeca6ad54a2f59f18501d485ece8f33ce882f62f2aa1e6fb1d5bd8499536f370d8ff0535d7fa18f&pass_ticket=Aj4oK1AhuKdIM0wxTzvrbjsfpQDDig7dPp2gRoqcvZKYVt8fzg4NzP376VNm4OAc&wxtoken=&x5=1&f=json";

		String json = "{\n" + "  \"spider\" : {\n" + "    \"thread\" : 11,\n" + "    \"startUrl\" : \" https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5MTY2MjYwMA==&f=json&frommsgid=&count=10&scene=124&is_ok=1&uin=MTk0MTAwODY0MA%3D%3D&key=9965a44fdc3b4b596395b6da52157ca07cd0a91c745513b4ea13d2e3b439093de96a0fa92c3e25960a2ceeec2b425fd786150f5ef2360258fb096a7914084dad3691d2741a6a7efbd06944c9c347065a&pass_ticket=itT6MQXX7cc0UIdiypZcg31lav4VeJaOBkWIzKaO7k6P7bim7s8NEPhS9C4MNVq8&wxtoken=&x5=1&f=json\",\n" + "    \"downloader\" : null,\n" + "    \"processer\" : \"wechatProcesser\",\n" + "    \"siteid\" : \"sxx\",\n" + "    \"pipeline\" : [\n" + "      \"consolePipeline\"\n" + "    ]\n" + "  },\n" + "  \"site\" : {\n" + "    \"domain\" : \"mp.weixin.qq.com\",\n"
				+ "    \"proxy\" : [\n" + "     \n" + "    ],\n" + "    \"retry\" : 3,\n" + "    \"sleepTime\" : 222,\n" + "    \"headers\" : [\n" + "      {\n" + "        \"name\" : \"Cookie\",\n"
				+ "        \"value\" : \"pgv_pvid=689463799; pgv_pvi=9056338944; qm_sid=dcccaf4c2ea9cdb581a2dbd1295b18c9; qm_username=3016717937; qm_domain=https://m.exmail.qq.com; qm_qz_key=1_5571bc0e588efb1b68744379da3a24cf010d0107030100070205; qm_sk=-1278249359&39PThPMS; qm_ssum=-1278249359&f7295e207b8ce869b3a79b495fd3cb13; wxtokenkey=a45864683ac4f3944ee22610990f807a8f3ddac43ddfd8765fb10f23288fb9d7; wxticket=689214848; wxticketkey=a31fb576cb222b4a403a6ddd54a184ed8f3ddac43ddfd8765fb10f23288fb9d7; wap_sid=CIDixZ0HEkAxNE9PdWxxcHRvME9ZSHpJX3piSzlsNFFEQXM0a0F4TkozQ2dtRjJvZXpoZERveWU5MXhWdkhoX0hEdUJSVkJPGAQgpBQoiMC39Agw7ffixgU=; wap_sid2=CIDixZ0HEogBVTlUcjZncmszdWd6RG8xUGxxZ1pFcWhWZlBBbjJlZGs3ZmtVYXpZZDB5eG9lVVRsR2RMcGdSWVF3WDRucDJaa1RaRWoxRlltWUE5R25FdTlZMDF1UnI3dmltVEFCYmlJWVVuVVdzUHgwR1lYa2RJZ1JXblNQQjgxUk12bkY0WUdnUU1BQUF+fg==\"\n"
				+ "      }\n" + "    ],\n" + "    \"userAgent\" : \"Mozilla\\/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit\\/601.1.46 (KHTML, like Gecko) Version\\/9.0 Mobile\\/13B143 Safari\\/601.1\"\n" + "  },\n" + "  \"condition\" : {\n" + "    \"endTime\" : \"\",\n" + "    \"startTime\" : \"\"\n" + "  },\n" + "  \"wechat\" : {\n" + "    \"name\" : \"\",\n" + "    \"uid\" : \"\",\n"
				+ "    \"url\" : \"https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5MTY2MjYwMA==&f=json&frommsgid=&count=10&scene=124&is_ok=1&uin=MTk0MTAwODY0MA%3D%3D&key=9965a44fdc3b4b596395b6da52157ca07cd0a91c745513b4ea13d2e3b439093de96a0fa92c3e25960a2ceeec2b425fd786150f5ef2360258fb096a7914084dad3691d2741a6a7efbd06944c9c347065a&pass_ticket=itT6MQXX7cc0UIdiypZcg31lav4VeJaOBkWIzKaO7k6P7bim7s8NEPhS9C4MNVq8&wxtoken=&x5=1&f=json\"\n" + "  },\n" + "  \"rule\" : {\n" + "    \"detailregex\" : \"url\",\n" + "    \"listregex\" : \"url\",\n" + "    \"listxpath\" : \"\\/\\/div[@class='unlogin-content-info']\",\n" + "    \"detailxpath\" : [\n" + "      {\n" + "        \"name\" : \"title\",\n" + "        \"reg\" : \"\",\n"
				+ "        \"value\" : \"\\/\\/div[@class='unlogin-content-info']\\/\\/h1[@class='unlogin-content-info-title']\\/text()\"\n" + "      },\n" + "      {\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/div[@class='unlogin-content-info']\\/\\/h1\\/\\/strong\\/text()\",\n" + "        \"name\" : \"publicTime\",\n" + "        \"simpleDateFormat\" : \"yyyy-MM-dd\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"introduce\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"div[@class='deng']\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"author\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"div[@class='unlogin-content-detail fl']\\/\\/ul\\/\\/li[4]\\/\\/a\\/text()\"\n" + "      },\n" + "      {\n"
				+ "        \"name\" : \"content\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/p[@class='unlogin-content-desc-cont hide']\\/text()\"\n" + "      },\n" + "      {\n" + "        \"name\" : \"readCount\",\n" + "        \"reg\" : \"\",\n" + "        \"value\" : \"\\/\\/p[@class='unlogin-content-desc-cont hide']\\/text()\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  \"isCircle\" : false\n" + "}";
		ObjectMapper objectMapper = new ObjectMapper();
		WebmagicConfig webmagicConfig = objectMapper.readValue(json, WebmagicConfig.class);
		MySpiderMonitor spiderMonitor = MySpiderMonitor.instance();

		// MySpiderStatus spiderStatuses = spiderMonitor.getSpiderStatuses().get(ruleConfig.getName());
		// if(spiderStatuses!=null)
		// {
		// spiderStatuses.start();
		// }
		// else
		// {
		AbstractCommPageProcess xPathProjectBasic = (AbstractCommPageProcess) context.getBean("WeChatHistoryPageProcess");
		xPathProjectBasic.init(webmagicConfig);

		Spider zhihuSpider = Spider.create(xPathProjectBasic).thread(2);

		zhihuSpider.setUUID("WeChatHistoryPageProcess");

		List<String> pipelne = webmagicConfig.getSpider().getPipeline();
		for (String p : pipelne) {
			zhihuSpider.addPipeline((Pipeline) context.getBean(p));
		}

		zhihuSpider.setScheduler(new DelayQueueScheduler(10, TimeUnit.SECONDS));

		spiderMonitor.register(zhihuSpider);

		zhihuSpider.run();
	}


	@Test
	public void main111() throws Exception {

		Document document= Jsoup.parse("<div class=\"rich_media_content \" id=\"js_content\"> \n" +
				" <p style=\"text-align: center; white-space: normal;\"><span style=\"color: #595959; font-size: 12px;\">作者：雨山薪</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 12px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">心理学上有个术语叫“元认知”，即：<strong>人对自我认知过程的认知</strong>。是人类对自我认知监控的一种极其重要能力，这种能力在个体间差异也是很大。优秀的元认知能力可影响人的一生。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">同样，对于产品经理来讲，每天会和大量的各种需求打交道。有的来自不同用户，有的来自跨部门或管理层甚至老板。当面对这些需求时，我们如何高效、准确地把握和解决需求，提出对需求本身的需求？即“元需求”。下面就谈谈自己对于需求本身的理解。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">通常作为产品从业者，可能会遇到以下场景：</span></p> \n" +
				" <p style=\"white-space: normal;\"><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"23\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <section class=\"\" style=\"font-size: 14px; line-height: 22.39px; margin-top: 10px; margin-bottom: 10px; padding: 15px 20px 15px 45px; outline: 0px; border: 0px currentcolor; color: rgb(62, 62, 62); vertical-align: baseline; background-image: url(http://mmbiz.qpic.cn/mmbiz_jpg/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz9dh00NllyQDACibuorkBfJlia1HtcGxaSzMdza6tDUD3SYoGAmftAmBQ/0?wx_fmt=jpeg); background-color: rgb(241, 241, 241); box-sizing: border-box; background-position: 1% 5px; background-repeat: no-repeat no-repeat;\"> \n" +
				"   <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">路人甲：这里有个XX需求，你就按照XX方法，帮我加个XX的功能（或XX的流程）。</span></p> \n" +
				"   <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">路人乙：用户A反映有XX问题，觉得要加个XX按钮（功能）解决。请给个排期。</span></p> \n" +
				"  </section> \n" +
				" </section> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">是不是有种熟悉的配方？熟悉的味道的感觉？特别是类似需求从高层下达，更是让很多产品人感觉压力山大。那么这里我并不推崇“大干快上”的赶紧解决的思路。产品的快速迭代不代表思想上的偷懒。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">我的经验是，首先得对需求本身进行“过滤”和“加工提炼”。分成三大步骤：需求反思、需求定义、需求解决策略。下面我将就此三点分开来谈：</span></p> \n" +
				" <p style=\"white-space: normal;\"><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"23\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <section class=\"\" style=\"font-size: 14px; line-height: 22.39px; margin-top: 10px; margin-bottom: 10px; padding: 15px 20px 15px 45px; outline: 0px; border: 0px currentcolor; color: rgb(62, 62, 62); vertical-align: baseline; background-image: url(http://mmbiz.qpic.cn/mmbiz_jpg/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz9dh00NllyQDACibuorkBfJlia1HtcGxaSzMdza6tDUD3SYoGAmftAmBQ/0?wx_fmt=jpeg); background-color: rgb(241, 241, 241); box-sizing: border-box; background-position: 1% 5px; background-repeat: no-repeat no-repeat;\"> \n" +
				"   <span style=\"color: #595959; font-size: 13px;\">PS：本阶段思考分析的“慢”是为了后面执行解决的“快”，这步急不得。</span> \n" +
				"  </section> \n" +
				" </section> \n" +
				" <p><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"87417\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <p style=\"text-align: center; white-space: normal;\"><img data-ratio=\"0.45454545454545453\" data-src=\"http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" data-type=\"gif\" data-w=\"22\" style=\"display: inline; width: 100% !important; height: auto !important; visibility: visible !important;\" title=\"音符\" src=\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" class=\" __bg_gif\" data-order=\"0\" data-fail=\"0\" width=\"100%\" height=\"auto\"></p> \n" +
				" </section> \n" +
				" <p style=\"text-align: center; white-space: normal;\"><strong><span style=\"color: #59C3F9; font-size: 20px;\">需求反思</span></strong><br></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">首先，需要对各种管道收集上来的需求进行反思（注：这里的反思是指对于思维本身的思考，而不是“反省”的含义），需要从如下几个方面进行分析。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong>▍需求的合理性</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">需求本身是否合理，这是一切需求发生的原点。</span></strong><span style=\"color: #595959; font-size: 15px;\">如果需求本身的合理性遭到质疑，那么后面的一切针对此问题的构建都是毫无意义。所以最开始需要花点时间对需求本身进行批判性思考。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">实际情况中，可能会有种特例。即：该需求是来自你的直属上司，或者VP、老板。那么思考需求合理性有意义吗？</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">我认为有两点。第一，思考事务本身的行为是中性的，结论是不会以对象是谁而发生改变。其次，来自高层的需求本身也是需求管理一部分。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">换句话说，产品经理也得懂得管理上级的需求。如果管理需求这种需求本身是不能执行的，那么参见“需求搁置”处理方式，后面会具体谈到。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">具体从哪几个可执行点上来思考合理性问题。这里我给出相关建议，如果补充或争议欢迎提出。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">是否符合法律法规要求</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">这点原因不需要做具体说明。所有可能实现的需求都须在现行法律法规框架下运行。对于互金行业的企业尤为重要，在这一点上产品经理需多和公司法律顾问及风控部门进行沟通确认，此点尤为重要。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">是否符合用户利益</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">一切产品都是以用户利益为依归。这里需强调是：在作分析时，需要回归到“用户通过使用产品，达到什么样的目标”这个问题的本质上，而不是以“BAT是这么做的”为背书来评判需求。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">至于如何知道是否符合用户利益，业内也有许多方（tao）法（lu）如：MVP、灰度发布等技巧来验证假设需求。这一点展开内容很多，限于篇幅，可自行网上搜索。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">是否符合企业目标</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">此需求对于企业是否能实现商业目标，或者在盈利模式比较模糊的情况下，能否带给企业正向价值，甚至创造良好社会价值。例如，<span style=\"white-space: pre-wrap;\">前不久</span>摩拜单车CEO接收采访坦言尚未找到盈利模式。虽然高昂的维护、教育市场成本，及面临乱停乱放等问题。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">但作为创始人在杭州租单车不便的个人痛点，所衍生出来的一个创新解决方案，给人们最后5公里出行带来了巨大的社会价值。这为企业带来巨大正向价值。从最新一轮融资可见一斑。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">是否超出目前企业资源</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">很多需求本身出发点很好，并且符合以上三点。但超出企业当前的资源约束。通常情况下，需考虑几点资源约束：人力、资金成本、时间成本、技术条件等。特别对于创业企业，以上约束尤为明显。所以需要对问题做优先级分类。以MVP作为衡量标准，确定最核心需求边界。在边界以外的需求在商业模式验证成功后，后期快速迭代补上。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">是否符合相关利益者目标</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">这一点对于B2B模式，或者B2B2C模式极为重要。因为利益相关者和企业为联盟。比如在大多数O2O项目中，B端是企业间接触达C端的通道。如果这个通路的利益或诉求得不到很好满足，就会极大影响终端用户的转化率、达成率，甚至带来负面影响。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">在消费金融行业，初创企业往往非常关注审核时效性，但忽略商户本金及时发放。导致商户以后将优质客户介绍给竞争对手，形成一个信誉漏斗，最终使企业得到次级信誉的客户。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong><span style=\"white-space: pre-wrap;\">▍</span>需求的负面性</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">需求之所有称之为需求，即为现有模式不能满足的但又期望得到满足的痛点。本质上是一种对现有状况的改变或补充。那么一定会涉及到对存量的影响，这种影响往往最初带来的效应的负面的。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">对改变的恐惧</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">既然涉及到对现状的改变、那么即为对现有习惯的挑战。更多的是心理上人们对于不确定性结果的消极预期。这样的负面效果会有多大，需要谨慎评估。有些现状虽然理性分析上不合理，但长期已经培养起的用户习惯导致难以轻易改变。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">举个栗子，我们现在使用的键盘布局为QWERTY型。当时为了减慢打字速度防止打字机卡死。显然当今已不存在技术障碍，但由于人们长久习惯的惯性导致保持旧有规则即为最合理。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">存量利益的反抗</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">由于需求带来的改变也会对存量利益关系的改变，导致需求本身遇到反（si）抗(bi)。比如在互金行业，有时为了更好的防范风控增加信审安全级别，就会导致申请步骤增加，间接影响到销售团队的业绩。这时提前预估阻力，做好相应的应对措施是很有必要的。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">新需求的风险</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">一言以蔽之，没有风险的新需求都是耍流氓。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">是否能承受相应风险，愿意付出创新成本都是需要提前做好准备。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong>▍需求搁置</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">有些需求不解决可能是最好的解决。因为由于许多特殊原因，搁置需求本身是一种明智选择。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">例如前面提到的特例，关于管理上级的需求。如果你改变上级原有需求的这个需求本身无法实现，那么请搁置自己这个需求。执行上级安排的需求。<br></span></p> \n" +
				" <p style=\"white-space: normal;\"><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"87417\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <p style=\"text-align: center; white-space: normal;\"><img data-ratio=\"0.45454545454545453\" data-src=\"http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" data-type=\"gif\" data-w=\"22\" style=\"display: inline; width: 100% !important; height: auto !important;\" title=\"音符\" src=\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" class=\" __bg_gif\" data-order=\"1\" width=\"100%\" height=\"auto\"></p> \n" +
				" </section> \n" +
				" <p style=\"text-align: center; white-space: normal;\"><strong><span style=\"color: #59C3F9; font-size: 20px;\">需求定义</span></strong><br></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong>▍需求类型</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">完成上一大步后，需要知道需求本身的属性。首先做一个归纳。把不同需求标记为不同属性和优先级、以便更好管理。这里优先级从高到低分为“IT技术问题”、“业务逻辑需求”、“用户体验需求”以及“用户认知问题需求”。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">IT技术问题</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">狭义定义来讲，此类型不能算作需求。但此处是从广义定义上来理解。具体类型细分为：Bug型、代码质量型、服务器性能型。优先级从高到低。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">业务逻辑需求</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">业务本身的变化需求。大致为对存量业务逻辑的改变，“逻辑自洽型”、“流程通畅性型”、“流程冗余性型”。以及对增量业务逻辑的添加，“功能点完备性型”。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">用户体验需求</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">用户体验主要细分为：“人机交互效率型”、“用户心理预期型”、“界面布局合理型”、“操作习惯符合型”、“外观美学型”、“教育文化符合型”。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">解释下“用户心理预期型”和“教育文化符合型”。前者指的产品在某个场景下或流程中的操作反馈或产生的结果要符合用户当时的预期。不然用户就会有被打扰感和失控感。后者指产品需符合国家或地域文化。比如欧美的线下party、中国的线上社交、日本的御宅文化都催生出不同的产品形态。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><strong><span style=\"color: #595959; font-size: 15px;\">用户认知问题需求</span></strong></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">主流用户的认知是有一个门槛的。虽然多数情况下，普通人都在此门槛之上，但还是不能排除在我们熟知的圈子之外或者一些小众群体是低于这个认知门槛的。导致产品本身不被这部分用户理解，需要先要教育这部分用户，培养起认知习惯。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong>▍需求制造方</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">需求是由谁制造的，或者称为需求产生的源头方。弄清谁是真正的源头才能回溯到需求的本质去解决。这里想强调，需求的提出方（需求从哪来）不一定是需求的制造方。这一点会在后面需求的渠道具体展开。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">例如，表面上需求是由运营提出的，但实际可能是一部分用户制造的需求。有些看似是用户的需求，但本质上可能是内部管理问题所反映。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">需求的制造方细分如下：</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <ul class=\"list-paddingleft-2\"> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">终端用户</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">利益相关者</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">内部管理层</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">产品设计者</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 13px;\">程序开发者</span></p></li> \n" +
				" </ul> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">可能会觉得程序开发者也会制造需求吗？确实在某些情况下，开发者处于自身工作便捷性考虑，会主动对某些逻辑流程提出需求。这时候产品也需要综合考虑分析。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #59C3F9; font-size: 18px;\"><strong>▍需求核心内容</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">需求到底是什么？他的核心内容是什么。理解和描述之前是否有歧义或二义性，这是需要沟通分析中注意的。</span></p> \n" +
				" <p><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"87417\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <p style=\"text-align: center; white-space: normal;\"><img data-ratio=\"0.45454545454545453\" data-src=\"http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" data-type=\"gif\" data-w=\"22\" style=\"display: inline; width: 100% !important; height: auto !important;\" title=\"音符\" src=\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" class=\" __bg_gif\" data-order=\"2\" width=\"100%\" height=\"auto\"></p> \n" +
				" </section> \n" +
				" <p style=\"text-align: center; white-space: normal;\"><strong><span style=\"color: rgb(89, 195, 249); font-size: 20px;\">需求解决策略</span></strong><br></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: rgb(89, 195, 249); font-size: 18px;\"><strong>▍需求解决方</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">做完前两大步（需求反思、需求定义）后，需要设计需求的解决策略。在解决方案拟定之前，还需要知道谁能解决这个需求。对于产品来讲，这些解决方可能会是如下：</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <ul class=\"list-paddingleft-2\"> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">产品经理</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">交互设计</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">视觉设计</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">前端开发</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">后台开发</span></p></li> \n" +
				" </ul> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: rgb(89, 195, 249); font-size: 18px;\"><strong>▍需求的渠道</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">需求渠道区别于需求的制造方在于，需求制造方是需求的真正源头，而需求的渠道只是需求的转述或反映。有时候一个需求源头会在多个需求渠道中反应出来。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">简单的讲：<strong>需求制造方就是“从哪来”，需求的渠道是“通过什么来”。</strong></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">当然需求的制造方可以和需求的渠道为同一方。</span></p> \n" +
				" <p style=\"white-space: normal;\"><br></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"87417\" style=\"border: 0px none; box-sizing: border-box;\"> \n" +
				"  <p style=\"text-align: center; white-space: normal;\"><img data-ratio=\"0.45454545454545453\" data-src=\"http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" data-type=\"gif\" data-w=\"22\" style=\"display: inline; width: 100% !important; height: auto !important;\" title=\"音符\" src=\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz_gif/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz84AHVfnmdUPMlT7Olic4IdVTvdra0FghMMxs9qaMmOoSj0anc5IsOZA/0?wx_fmt=gif\" class=\" __bg_gif\" data-order=\"3\" width=\"100%\" height=\"auto\"></p> \n" +
				" </section> \n" +
				" <p style=\"text-align: center; white-space: normal;\"><strong><span style=\"color: rgb(89, 195, 249); font-size: 20px;\">总结</span></strong><br></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">三大步骤：需求反思、需求定义、需求解决策略：</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <ul class=\"list-paddingleft-2\"> \n" +
				"  <li><p style=\"white-space: normal;\"><strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求反思：</span></strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求合理性、需求负面性、需求搁置</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求定义：</span></strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求类型、需求制造方、需求核心内容</span></p></li> \n" +
				"  <li><p style=\"white-space: normal;\"><strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求解决策略：</span></strong><span style=\"color: rgb(89, 89, 89); font-size: 13px;\">需求解决方、需求的渠道</span></p></li> \n" +
				" </ul> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">以上为在提出具体解决需求方案前，所做的处理工作。对比拿到需求就马上拿出应对方案；而是慢思考、快执行。防止了因为考虑不到位导致解决了某个问题产生新问题，反复来回不停硬试错的状况。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\">老话说得好：磨刀不误砍柴工。希望各位产品经理都是“亲妈”，而不是“奶妈”。</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"><br></span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 12px;\">作者：雨山薪，微信公众号：山语录（sumitalk）</span></p> \n" +
				" <p style=\"white-space: normal;\"><span style=\"color: rgb(89, 89, 89); font-size: 12px;\">本文由 @雨山薪 原创发布于人人都是产品经理。未经许可，禁止转载。</span></p> \n" +
				" <p style=\"text-align: center; margin-bottom: 10px; margin-top: 10px; white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"></span></p> \n" +
				" <p><img data-s=\"300,640\" data-type=\"jpeg\" data-src=\"http://mmbiz.qpic.cn/mmbiz/icHOSb47jqpXiafOKshZw8NYica55rGk6W61fZeic97wFD0ITk4KHMVoRYOAaprc2Vw7G4M3hLJuUPciar9awke9Q2A/0?wx_fmt=jpeg\" data-ratio=\"0.54609375\" data-w=\"1280\" src=\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz/icHOSb47jqpXiafOKshZw8NYica55rGk6W61fZeic97wFD0ITk4KHMVoRYOAaprc2Vw7G4M3hLJuUPciar9awke9Q2A/0?wx_fmt=jpeg\" style=\"width: 100% !important; height: auto !important;\" width=\"100%\" height=\"auto\"></p> \n" +
				" <p style=\"text-align: center; margin-bottom: 10px; margin-top: 10px; white-space: normal;\"><span style=\"color: #595959; font-size: 15px;\"></span></p> \n" +
				" <section class=\"\" data-tools=\"135编辑器\" data-id=\"28\" style=\" border: 0px none;  box-sizing: border-box; \"> \n" +
				"  <p class=\"\" style=\"font-size: 15.5556px; max-width: 100%; min-height: 1.5em; line-height: 2em; word-wrap: break-word; word-break: normal; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; color: rgb(255, 255, 255); text-align: center; background-color: rgb(0, 176, 240); white-space: normal;\" data-brushtype=\"text\">点击“阅读原文”下载APP</p> \n" +
				" </section> \n" +
				"</div>");


		String json="[\n" +
				"    {\n" +
				"        \"action\": \"add\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"        \"name\": \"width\",\n" +
				"        \"vluae\": \" 100%\"\n" +
				"    },\n" +
				"   {\n" +
				"        \"action\": \"add\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"        \"name\": \"height\",\n" +
				"        \"vluae\": \"auto\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"        \"name\": \"style\",\n" +
				"        \"source\": \"width: \\\\d+[.\\\\d+px]\",\n" +
				"        \"target\": \"width: 100%\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"        \"name\": \"style\",\n" +
				"        \"source\": \"height: \\\\d+[.\\\\d+px]\",\n" +
				"        \"target\": \"height: auto\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"\t     \"name\": \"style\",\n" +
				"        \"source\": \"height: \",\n" +
				"        \"target\": \"height: auto\"\n" +
				"    },\n" +
				"   {\n" +
				"        \"action\": \"copy\",\n" +
				"        \"type\":\"attr\",\n" +
				"        \"cssquery\": \"img\",\n" +
				"        \"source\": \"data-src\",\n" +
				"        \"target\": \"src\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"          \"type\":\"attr\",\n" +
				"        \"cssquery\": \"[style]\",\n" +
				"\t\t\"name\": \"style\",\n" +
				"        \"source\": \"http://mmbiz.qpic.cn/mmbiz\",\n" +
				"        \"target\": \"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz\"\n" +
				"    }\n" +
				",\n" +
				"  {\n" +
				"        \"action\": \"replace\",\n" +
				"          \"type\":\"attr\",\n" +
				"        \"cssquery\": \"[src]\",\n" +
				"\t\t\"name\": \"style\",\n" +
				"        \"source\": \"http://mmbiz.qpic.cn/mmbiz\",\n" +
				"        \"target\": \"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz\"\n" +
				"    }\n" +
				",\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"          \"type\":\"attr\",\n" +
				"        \"cssquery\": \"section[style]\",\n" +
				"\t\t\"name\": \"style\",\n" +
				"        \"source\": \"width: \\\\d+[.\\\\d+px]\",\n" +
				"        \"target\": \"width: 100%\"\n" +
				"    }\n" +
				"   \n" +
				",\n" +
				"    {\n" +
				"        \"action\": \"replace\",\n" +
				"          \"type\":\"attr\",\n" +
				"        \"cssquery\": \"section[style]\",\n" +
				"\t\t\"name\": \"style\",\n" +
				"        \"source\": \"width: \\\\d+[.\\\\d+px]\",\n" +
				"        \"target\": \"width: 100%\"\n" +
				"    }\n" +
				"   \n" +
				"]\n";

		ObjectMapper objectMapper = new ObjectMapper();
		List<RinseRule> rinseRules = objectMapper.readValue(json, getCollectionTypeList(objectMapper,List.class, RinseRule.class));
		System.out.println("大小："+rinseRules.size());
			Document document1=	  RinseHtmlUtil.rinseHtml(document, rinseRules);
		System.out.println("修改后："+document1.body().children().outerHtml());
//		String s="font-size: 14px; line-height: 22.39px; margin-top: 10px; margin-bottom: 10px; padding: 15px 20px 15px 45px; outline: 0px; border: 0px currentcolor; color: rgb(62, 62, 62); vertical-align: baseline; background-image: url(http://mmbiz.qpic.cn/mmbiz_jpg/icHOSb47jqpXKocc4icVJuiby3pYBl9pJvz9dh00NllyQDACibuorkBfJlia1HtcGxaSzMdza6tDUD3SYoGAmftAmBQ/0?wx_fmt=jpeg); background-color: rgb(241, 241, 241); box-sizing: border-box; background-position: 1% 5px; background-repeat: no-repeat no-repeat;";
//		String r="url\\(\"http://mmbiz.qpic.cn/mmbiz";
//		String t="url\\(\"http://daydayupapi.qiyadeng.com/getWeChatImage?redir_url=http://mmbiz.qpic.cn/mmbiz";
//		String c=  s.replaceAll(r,t);
//		System.out.println("aaaa:"+c);
	}

	public static JavaType getCollectionTypeList(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
//	public static void main(String[] args) {
//		String s="<a style='width: 10px'> Title</a>";
//		String regex="(?<=\\<|\\<\\/)a(?=\\>)";
//		System.out.println(s.replaceAll(regex, "b"));
//
//		String regex3=	"width: \\d+[.\\d+px]";
//		System.out.println(s.replaceAll(regex3, "width: auto"));
//	}


	public static void main(String[] args) {
		String s="<a style='width: 10px'> Title</a>";
		String regex="(?<=\\<|\\<\\/)a(?=\\>|)|(?<=\\<|\\<\\/)a(?<=\\s+>)";
		System.out.println(s.replaceAll(regex, "b"));

		String regex3=	"width: \\d+[.\\d+]px";
		System.out.println(s.replaceAll(regex3, "width: auto"));
	}

}
