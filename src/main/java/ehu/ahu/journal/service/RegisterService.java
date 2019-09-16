package ehu.ahu.journal.service;

import ehu.ahu.journal.dao.RegisterMapper;
import ehu.ahu.journal.pojo.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Keyu
 */
@Service
public class RegisterService {

    @Autowired
    RegisterMapper registerMapper;

    public Register selectRegisterbyId(int id){
        return registerMapper.selectByBookId(id);
    }

    public int insertRegister(Register register){
        return registerMapper.insertRegister(register) > 0? register.getId():0;
    }

    public Register selectRegisterByYearAndIssue(int year,int issue,int journalId){
        return registerMapper.selectByYearAndIssue(year,issue,journalId);
    }

    public List<Register> selectLastedRegister(int offset,int limit){
        return  registerMapper.selectLatestRegister(offset,limit);
    }

    public void updateBorrowById(int id,int borrow){
        registerMapper.updateBorrow(id,borrow);
    }

}
