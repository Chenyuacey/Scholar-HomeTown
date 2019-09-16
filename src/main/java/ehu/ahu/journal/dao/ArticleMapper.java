package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface ArticleMapper {
    String TABLE_NAME = "article";
    String INSERT_FIELDS = " article_name,journal_id,author,keyword1,keyword2,keyword3,keyword4,keyword5,page_num ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{articleName},#{journalId},#{author},#{keyword1},#{keyword2},#{keyword3},#{keyword4},#{keyword5},#{pageNum})"})
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertArticle(Article article);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where journal_id=#{id}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
            @Result(property = "articleName",column = "article_name"),
            @Result(property = "pageNum",column = "page_num"),
    })
    List<Article> selectByBookId(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
            @Result(property = "articleName",column = "article_name"),
            @Result(property = "pageNum",column = "page_num"),
    })
    Article selectById(int id);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, "  order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
            @Result(property = "articleName",column = "article_name"),
            @Result(property = "pageNum",column = "page_num"),
    })
    List<Article> selectLatestArticle(@Param("offset") int offset,
                                        @Param("limit") int limit);


}
