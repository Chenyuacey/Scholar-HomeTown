package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.*;
import ehu.ahu.journal.service.BorrowService;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Keyu
 */
@Controller
public class MeController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    BorrowService borrowService;

    @Autowired
    RegisterService registerService;

    @Autowired
    JournalService journalService;

    @GetMapping("/user/me")
    public String me(Model model){
        List<Borrow> borrows = borrowService.selectBorrowByUserId(hostHolder.getUser().getId());
        List<BorrowInfo> borrowInfos = new ArrayList<>();
        for (Borrow borrow:borrows){
            int registerId = borrow.getRegisterId();
            Register register = registerService.selectRegisterbyId(registerId);
            Journal journal = journalService.selectJournalbyId(register.getJournalId());
            BorrowInfo borrowInfo = new BorrowInfo();
            borrowInfo.setName(journal.getName());
            borrowInfo.setBorrowDate(borrow.getBorrowDate());
            borrowInfo.setReturnDate(borrow.getReturnDate());
            borrowInfo.setYear(register.getYear());
            borrowInfo.setIssue(register.getIssue());
            borrowInfo.setJournalId(borrow.getRegisterId());
            borrowInfos.add(borrowInfo);
        }
        model.addAttribute("borrow",borrowInfos);

        return "mybook";
    }

}
