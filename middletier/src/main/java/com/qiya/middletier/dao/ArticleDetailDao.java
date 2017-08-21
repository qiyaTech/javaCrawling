package com.qiya.middletier.dao;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.ArticleDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by qiyalm on 17/3/29.
 */
@Repository
@RepositoryRestResource(exported = false)
@Transactional
public interface ArticleDetailDao extends CrudRepository<ArticleDetail, Long>, JpaSpecificationExecutor<ArticleDetail> {
}