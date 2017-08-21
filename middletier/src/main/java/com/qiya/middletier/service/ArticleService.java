package com.qiya.middletier.service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.qiya.framework.def.StatusEnum;
import com.qiya.middletier.bizenum.CmsCatergoryEnum;
import com.qiya.middletier.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.qiya.framework.middletier.base.IService;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.bizenum.CmsCatergoryEnum;
import com.qiya.middletier.dao.ArticleDao;
import com.qiya.middletier.dao.ArticleDetailDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
/**
 * Created by qiyalm on 17/3/24.
 */
@Service
public class ArticleService implements IService<Article, Article> {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleDetailDao articleDetailDao;
    @Autowired
    private SiteService siteService;
    public Boolean isExists(String linkUrl) {
        if (articleDao.isExist(linkUrl) > 0) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean isExistTiteSiteTime(Long siteid, String title, Date time) {
        if (articleDao.isExistTiteSiteTime(siteid, title, time) > 0) {
            return false;
        } else {
            return true;
        }
    }
    public Article getArticlebyTiteSiteAuthor(Long siteid, String title, String author) {
        return articleDao.getArticlebyTiteSiteAuthor(siteid, title, author);
    }
    @Override
    public Page<Article> search(SearchCondition sc) {
        Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createTime");
        Specification<Article> spec = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
                return sc.getPredicate(root, builder, bp);
            }
        };
        return   articleDao.findAll(spec, pageable);
    }
    public Page<ArticleResult> searchBySite(SearchCondition sc) {
        Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createTime");
        Specification<Article> spec = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
                return sc.getPredicate(root, builder, bp);
            }
        };
        Site site=  siteService.read(sc.getTerm("siteId").getValue().toString()) ;
        Page<Article> result = articleDao.findAll(spec, pageable);
        return  result.map(new Converter<Article, ArticleResult>() {
            @Override
            public ArticleResult convert(Article article) {
                return ArticleResult.toSiteArticleResult(article,site);
            }
        });
    }
    @Override
    public Article create(Article obj) {
        return articleDao.save(obj);
    }
    @Override
    public Article read(String id) {
        return articleDao.findOne(Long.valueOf(id));
    }
    @Override
    public Article update(Article obj) {
        return articleDao.save(obj);
    }
    @Override
    public Article delete(Article obj) {
        return articleDao.save(obj);
    }
    public PageImpl getRecommdArticleList(int index, int size) {
        List<Map> result = new ArrayList<>();
        List<Object[]> objects = articleDao.getRecommdArticleList(Long.valueOf(index * size), size, CmsCatergoryEnum.aritic.code);
        for (Object[] info : objects) {
            Map<String, Object> o = new HashMap<String, Object>();
            o.put("site_id", info[0]);
            o.put("head_image", info[1]);
            o.put("intrduce", info[2]);
            o.put("site_name", info[3]);
            o.put("site_nick", info[4]);
            o.put("site_type", info[5]);
            o.put("title", info[6]);
            o.put("article_id", info[7]);
            o.put("read_count", info[8]);
            o.put("public_time", info[9]);
            o.put("pic", info[10]);
            result.add(o);
        }
        return new PageImpl(result, new PageRequest(index, size), articleDao.getRecommdArticleListCount());
    }
    public PageImpl getArticleList(int index, int size) {
        List<Map> result = new ArrayList<>();
        List<Object[]> objects = articleDao.getArticleList(Long.valueOf(index * size), size);
        for (Object[] info : objects) {
            Map<String, Object> o = new HashMap<String, Object>();
            o.put("site_id", info[0]);
            o.put("head_image", info[1]);
            o.put("intrduce", info[2]);
            o.put("site_name", info[3]);
            o.put("site_nick", info[4]);
            o.put("site_type", info[5]);
            o.put("title", info[6]);
            o.put("article_id", info[7]);
            o.put("read_count", info[8]);
            o.put("public_time", info[9]);
            o.put("pic", info[10]);
            result.add(o);
        }
        return new PageImpl(result, new PageRequest(index, size), articleDao.getArticleListCount());
    }
    public ArticleDetail get(Long id) {
        return articleDetailDao.findOne(id);
    }
    public HashMap<String, Object> getOneArticleList(String id) throws Exception {
        List<Object[]> infoList = articleDao.getOneArticleList(Long.parseLong(id), CmsCatergoryEnum.aritic.code);
        if (infoList.size() == 0) {
            throw new Exception("找不到该文章");
        }
        Object[] info = infoList.get(0);
        HashMap<String, Object> o = new HashMap<String, Object>();
        o.put("site_id", info[0]);
        o.put("head_image", info[1]);
        o.put("intrduce", info[2]);
        o.put("site_name", info[3]);
        o.put("site_nick", info[4]);
        o.put("site_type", info[5]);
        o.put("title", info[6]);
        o.put("article_id", info[7]);
        o.put("read_count", info[8]);
        o.put("public_time", info[9]);
        o.put("pic", info[10]);
        return o;
    }
    public byte[] getWechatImage(String redirectUrl) throws Exception {
        URL obj = new URL(redirectUrl);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setReadTimeout(5000);
        conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.addRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        conn.addRequestProperty("Referer", "http://mp.weixin.qq.com/s?timestamp=1487925204&src=3&ver=1&signature=qoDKjm5Udr8iZ*a591eYZywUYURtYJeoerXNa*DZZtQVrZUReulnX3Z2pnciMZY-QiFrI5p67snT9RdazeFOkLF85Iv9mTvKrFDsJkMhc4x4ERG3fuF8rTMzELD69vGVtEp1P2F-gmHKlDv6Nsr9U8Ay9vDTS4f5fUzEIx7l8Ps=");
        conn.addRequestProperty("Host", "mp.weixin.qq.com");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = conn.getInputStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;
            while ((n = is.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", "", e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return baos.toByteArray();
    }
}