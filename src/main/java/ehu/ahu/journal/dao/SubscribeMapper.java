package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.Subscribe;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Keyu
 */
@Mapper
public interface SubscribeMapper {
    String TABLE_NAME = "subscribe";
    String INSERT_FIELDS = " journal_id,year ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{journalId},#{year})"})
    int insertSubscribe(Subscribe subscribe);

}
