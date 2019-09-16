package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.Journal;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface JournalMapper {
    String TABLE_NAME = "journal";
    String INSERT_FIELDS = " name,cn,issn,mail_code,pub_cycle,pub_place,publisher,start_year,language,size,img ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", SELECT_FIELDS,
            ") values (#{id},#{name},#{cn},#{issn},#{mailCode},#{pubCycle},#{pubPlace},#{publisher},#{startYear},#{language},#{size},#{borrow},#{img})"})
    int insertJournal(Journal journal);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "mailCode",column = "mail_code"),
            @Result(property = "pubCycle",column = "pub_cycle"),
            @Result(property = "pubPlace",column = "pub_place"),
            @Result(property = "startYear",column = "start_year"),
    })
    Journal selectById(int id);


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where issn=#{issn}"})
    @Results({
            @Result(property = "mailCode",column = "mail_code"),
            @Result(property = "pubCycle",column = "pub_cycle"),
            @Result(property = "pubPlace",column = "pub_place"),
            @Result(property = "startYear",column = "start_year"),
    })
    Journal selectByIssn(String name);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, "  order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "mailCode",column = "mail_code"),
            @Result(property = "pubCycle",column = "pub_cycle"),
            @Result(property = "pubPlace",column = "pub_place"),
            @Result(property = "startYear",column = "start_year"),
    })
    List<Journal> selectLatestJournal(@Param("offset") int offset,
                                        @Param("limit") int limit);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);




}
