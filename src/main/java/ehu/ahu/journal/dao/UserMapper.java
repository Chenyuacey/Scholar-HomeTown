package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.BorrowInfo;
import ehu.ahu.journal.pojo.Journal;
import ehu.ahu.journal.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface UserMapper {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password,authority ";
    String SELECT_FIELDS = " id, name, password,authority ";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{authority})"})
    int insertUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Select({"select register.journal_id,journal.name,register.year,register.issue,borrow.borrow_date,borrow.return_date from borrow,register,journal where register.id = borrow.register_id and register.journal_id=journal.id and user_id=#{id}"})
    @Results({
            @Result(property = "journalId",column = "journal_id"),
            @Result(property = "borrowDate",column = "borrow_date"),
            @Result(property = "returnDate",column = "return_date"),
    })
    List<BorrowInfo> selectBorrowInfoJournalbyId(int id);


}
