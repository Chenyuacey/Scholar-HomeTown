package ehu.ahu.journal.dao;

import ehu.ahu.journal.pojo.Borrow;
import ehu.ahu.journal.pojo.Register;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface BorrowMapper {
    String TABLE_NAME = "borrow";
    String INSERT_FIELDS = " user_id,register_id,borrow_date,return_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{registerId},#{borrowDate},#{returnDate})"})
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertBorrow(Borrow borrow);

    @Update({"update ", TABLE_NAME, " set return_date=#{returnDate} where user_id=#{userId} and register_id=#{registerId}"})
    void updateReturnDate(Borrow borrow);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "registerId",column = "register_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "borrowDate",column = "borrow_date"),
            @Result(property = "returnDate",column = "return_date"),
    })
    Borrow selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id=#{id} order by id desc" })
    @Results({
            @Result(property = "registerId",column = "register_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "borrowDate",column = "borrow_date"),
            @Result(property = "returnDate",column = "return_date"),
    })
    List<Borrow> selectByUserId(int id);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, "  order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "registerId",column = "register_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "borrowDate",column = "borrow_date"),
            @Result(property = "returnDate",column = "return_date"),
    })
    List<Borrow> selectLatestBorrow(@Param("offset") int offset,
                                        @Param("limit") int limit);


}
