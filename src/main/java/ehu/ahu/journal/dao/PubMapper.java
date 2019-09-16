package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.Pub;
import ehu.ahu.journal.pojo.Subscribe;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Author:Keyu
 */
@Mapper
public interface PubMapper {
    String TABLE_NAME = "pub";
    String INSERT_FIELDS = " article_id,journal_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{articleId},#{journalId})"})
    int insertPub(Pub pub);
    
}
