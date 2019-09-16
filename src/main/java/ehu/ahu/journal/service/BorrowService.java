package ehu.ahu.journal.service;

import ehu.ahu.journal.dao.BorrowMapper;
import ehu.ahu.journal.pojo.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author:Keyu
 */
@Service
public class BorrowService {
    @Autowired
    BorrowMapper borrowMapper;

    public Borrow selectBorrowById(int id){
        return borrowMapper.selectById(id);
    }

    public int insertBorrow(Borrow borrow){
        return borrowMapper.insertBorrow(borrow) > 0 ? borrow.getId():0;
    }

    public List<Borrow> selectBorrowByUserId(int id){
        return borrowMapper.selectByUserId(id);
    }

    public void updateReturnById(Borrow borrow){
        borrowMapper.updateReturnDate(borrow);
    }

    public List<Borrow> selectLastedBrrow(int offset,int limit){
        return borrowMapper.selectLatestBorrow(offset,limit);
    }
}
