package com.qiya.middletier.service;

import com.google.common.collect.Lists;
import com.qiya.framework.def.StatusEnum;
import com.qiya.framework.middletier.base.IService;
import com.qiya.framework.middletier.model.AdvPlace;
import com.qiya.framework.middletier.model.CmsCategory;
import com.qiya.framework.middletier.model.CmsCategoryResult;
import com.qiya.framework.middletier.model.Config;
import com.qiya.framework.model.DataSourceOption;
import com.qiya.framework.model.SearchCondition;
import com.qiya.middletier.bizenum.SiteType;
import com.qiya.middletier.dao.SiteDao;
import com.qiya.middletier.model.Site;
import com.qiya.middletier.model.SiteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by qiyalm on 17/3/31.
 */
@Service
public class SiteService implements IService<Site,SiteResult> {

    @Autowired
    private SiteDao siteDao;

    public List<Site> getSites(){
        return siteDao.getSites(StatusEnum.VALID.getValue());
    }

    public Site getSiteBYName(String name) {
        return siteDao.getSiteBYName(name);
    }


    public Page<SiteResult> search(SearchCondition sc,Integer type) {
        Pageable pageable = new PageRequest(sc.getIndex(), sc.getSize(), Sort.Direction.DESC, "createTime");
        Specification<Site> spec = new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate bp = builder.equal(root.get("status"), StatusEnum.VALID.getValue());
                if (type == null)
                    bp = builder.and(bp);
                else if (type > 0)
                    bp = builder.and(bp, builder.equal(root.get("type"), type));
                return sc.getPredicate(root, builder, bp);
            }
        };
        Page<Site> result = this.siteDao.findAll(spec, pageable);

        // 转换到可导出对象(IExportable)
        return result.map(new Converter<Site, SiteResult>() {
            @Override
            public SiteResult convert(Site source) {
                return SiteResult.toSiteResult(source, SiteType.getEnumById(source.getType()).getName());
            }
        });
    }


    public List<DataSourceOption> getDataSourceList() {
        List<DataSourceOption> list = Lists.newArrayList();
        for (Site ap : this.siteDao.findAll()) {
            list.add(ap.toDataSourceOption());
        }
        return list;
    }


    @Override
    public Page<SiteResult> search(SearchCondition sc) {
        return search(sc,0);
    }

    @Override
    public Site create(Site obj) {
        obj.setCreateTime(new Date());
        obj.setStatus(StatusEnum.VALID.getValue());

        return siteDao.save(obj);
    }

    @Override
    public Site read(String id) {
        return siteDao.findOne(Long.valueOf(id));
    }

    @Override
    public Site update(Site obj) {
        return siteDao.save(obj);
    }

    @Override
    public Site delete(Site obj) {
        obj.setStatus(StatusEnum.DELETE.getValue());
        return siteDao.save(obj);
    }
}
