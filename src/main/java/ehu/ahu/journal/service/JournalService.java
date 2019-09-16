package ehu.ahu.journal.service;

import ehu.ahu.journal.dao.JournalMapper;
import ehu.ahu.journal.pojo.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Keyu
 */
@Service
public class JournalService {
    @Autowired
    JournalMapper journalMapper;

    public List<Journal> selectlastedJournal(int offset,int limit){
        return journalMapper.selectLatestJournal(offset,limit);
    }

    public Journal selectJournalbyId(int id){
        return journalMapper.selectById(id);
    }

    public Journal selectJournalByIssn(String issn){
        return journalMapper.selectByIssn(issn);
    }

}
