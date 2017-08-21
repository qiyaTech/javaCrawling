package com.qiya.middletier.dao;
import com.qiya.middletier.model.Article;
import com.qiya.middletier.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
/**
 * Created by qiyalm on 17/3/24.
 */
@Repository
@RepositoryRestResource(exported = false)
@Transactional
public interface ArticleDao extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    @Query("select count(1) FROM Article WHERE linkUrl=:linkUrl ")
    public Integer isExist(@Param("linkUrl") String linkUrl);
    @Query("select count(1) FROM Article WHERE siteId=:siteid and title= :title and publicTime= :time")
    public Integer isExistTiteSiteTime( @Param("siteid") Long siteid, @Param("title") String title,
                                        @Param("time") Date time);
    @Query(" FROM Article WHERE siteId=:siteid and title= :title and author= :author")
    public Article getArticlebyTiteSiteAuthor( @Param("siteid") Long siteid, @Param("title") String title,
                                        @Param("author") String author);
    @Query(value ="select * from (  " +
            "select s.id as site_id," +
            "s.head_image,s.intrduce,s.site_name,s.site_nick,s.type as site_type, " +
            "l.title,l.id as article_id," +
            "l.read_count+IFNULL((select sum(count) from page_view where biz_id=l.id and biz_type=:article),0) as read_count, " +
            "CAST(r.create_time AS char) as public_time,l.pic " +
            "from site s ,article l,recommend_list r " +
            "where s.id = l.site_id and l.id=r.article_list_id) a " +
            "order by  a.public_time  desc  limit :offset ,:rows",nativeQuery = true)
    public List<Object[]> getRecommdArticleList(@Param("offset") Long offset, @Param("rows") int rows, @Param("article") String article);
    @Query(value ="   " +
            "select count(1) " +
            "from recommend_list" +
            " ",nativeQuery = true)
    public Integer getRecommdArticleListCount();
    @Query(value =" " +
            "select s.id as site_id," +
            "s.head_image,s.intrduce,s.site_name,s.site_nick,s.type as site_type, " +
            "l.title,l.id as article_id," +
            "l.read_count, " +
            "CAST(l.public_time AS char) as public_time,l.pic " +
            "from site s ,article l " +
            "where s.id = l.site_id" +
            " and not exists (select 1 from recommend_list r where l.id=r.article_list_id)" +
            "order by  l.create_time  desc  limit :offset ,:rows",nativeQuery = true)
    public List<Object[]> getArticleList(@Param("offset") Long offset, @Param("rows") int rows);
    @Query(value ="   " +
            "select count(1) " +
            "from site s ,article l " +
            "where s.id = l.site_id  " +
            " and not exists (select 1 from recommend_list r where l.id=r.article_list_id)" +
            " ",nativeQuery = true)
    public Integer getArticleListCount();
    @Query(value ="  " +
            "select s.id as site_id," +
            "s.head_image,s.intrduce,s.site_name,s.site_nick,s.type as site_type, " +
            "l.title,l.id as article_id," +
            "l.read_count+IFNULL((select sum(count) from page_view where biz_id=l.id and biz_type=:article),0) as read_count, " +
            "CAST(l.create_time AS char) as public_time,l.pic " +
            "from site s ,article l " +
            "where s.id = l.site_id " +
            " and l.id = :id " ,nativeQuery = true)
    public List<Object[]> getOneArticleList(@Param("id") Long id, @Param("article") String article);
    @Query(value ="  " +
            "select l " +
            "from Site s ,Article l " +
            "where s.id = l.siteId " +
            " and l.id = :id " +
            " and s.type=:type " )
    public  Article getWeChatArticleById(@Param("id") Long id, @Param("type") Integer type);
}