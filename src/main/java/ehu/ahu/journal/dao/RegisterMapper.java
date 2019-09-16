package ehu.ahu.journal.dao;


import ehu.ahu.journal.pojo.Register;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface RegisterMapper {
    String TABLE_NAME = "register";
    String INSERT_FIELDS = " journal_id,year,issue,borrow ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{journalId},#{year},#{issue},#{borrow})"})
    int insertRegister(Register register);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
    })
    Register selectByBookId(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where year=#{year} and issue=#{issue} and journal_id=#{journalId}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
    })
    Register selectByYearAndIssue(@Param("year") int year,
                                  @Param("issue") int issue,
                                  @Param("journalId") int journalId);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, "  order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
    })
    List<Register> selectLatestRegister(@Param("offset") int offset,
                                      @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set borrow=#{borrow} where id=#{id}"})
    void updateBorrow(@Param("id") int id,@Param("borrow") int borrow);

}
