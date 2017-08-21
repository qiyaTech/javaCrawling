package com.qiya.middletier.service;
import com.qiya.framework.middletier.base.IService;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.dao.ArticleDetailDao;
import com.qiya.middletier.model.ArticleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
/**
 * Created by qiyalm on 17/3/29.
 */
@Service
public class ArticleDetailService implements IService<ArticleDetail,ArticleDetail>{
    @Autowired
    private ArticleDetailDao articleDetailDao;
    @Override
    public Page<ArticleDetail> search(SearchCondition sc) {
        return null;
    }
    @Override
    public ArticleDetail create(ArticleDetail obj) {
        return articleDetailDao.save(obj);
    }
    @Override
    public ArticleDetail read(String id) {
        return articleDetailDao.findOne(Long.valueOf(id));
    }
    @Override
    public ArticleDetail update(ArticleDetail obj) {
        return articleDetailDao.save(obj);
    }
    @Override
    public ArticleDetail delete(ArticleDetail obj) {
        return articleDetailDao.save(obj);
    }
}