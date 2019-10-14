package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.Borrow;
import ehu.ahu.journal.pojo.Journal;
import ehu.ahu.journal.pojo.Register;
import ehu.ahu.journal.pojo.User;
import ehu.ahu.journal.service.BorrowService;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import ehu.ahu.journal.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

/**
 * @Author:Keyu
 */
@Controller
public class BorrowController {

    @Autowired
    BorrowService borrowService;

    @Autowired
    RegisterService registerService;

    @Autowired
    JournalService journalService;

    @Autowired
    UserService userService;

    /**
     * 借书
     */
    @PostMapping("/admin/borrow")
    public String borrow(Model model,
                         @Param("userNmae") String userName,
                         @Param("issn") String issn,
                         @Param("year") int year,
                         @Param("issue") int issue,
                         @Param("type") String type){
        Journal journal = journalService.selectJournalByIssn(issn);
        if (journal == null){
            model.addAttribute("msg","期刊目录没有此期刊");
            return "borrow";
        }
        Register register = registerService.selectRegisterByYearAndIssue(year,issue,journal.getId());
        if (register == null){
            model.addAttribute("msg","该期刊还没有入库");
            return "borrow";
        }
        if (register.getBorrow() != -1){
            model.addAttribute("msg","该期刊已被借阅");
            return "return";
        }
        User user = userService.selectUserbyName(userName);
        if (user == null){
            model.addAttribute("msg","用户不存在");
            return "borrow";
        }
        Borrow borrow = new Borrow();
        borrow.setBorrowDate(new Date());
        borrow.setUserId(user.getId());
        borrow.setRegisterId(register.getId());
        borrowService.insertBorrow(borrow);

        registerService.updateBorrowById(register.getId(),borrow.getId());
        model.addAttribute("suc","成功借阅《"+journal.getName()+"》"+"第"+register.getYear()+"年，第"+register.getIssue()+"期");

        return "borrow";
    }

    /**
     * 还书
     */
    @PostMapping("/admin/return")
    public String returnbook(Model model,
                         @Param("userNmae") String userName,
                         @Param("issn") String issn,
                         @Param("year") int year,
                         @Param("issue") int issue,
                         @Param("type") String type){
        Journal journal = journalService.selectJournalByIssn(issn);
        if (journal == null){
            model.addAttribute("msg","期刊目录没有此期刊");
            return "return";
        }
        Register register = registerService.selectRegisterByYearAndIssue(year,issue,journal.getId());
        if (register == null){
            model.addAttribute("msg","该期刊还没有入库");
            return "return";
        }
        User user = userService.selectUserbyName(userName);
        if (user == null){
            model.addAttribute("msg","用户不存在");
            return "return";
        }
        Borrow borrowold = borrowService.selectBorrowById(register.getBorrow());
        if (borrowold.getUserId() != user.getId()){
            model.addAttribute("msg","该期刊不是目标用户所借阅");
            return "return";
        }
        Borrow borrow = new Borrow();
        borrow.setUserId(user.getId());
        borrow.setRegisterId(register.getId());
        borrow.setReturnDate(new Date());
        borrowService.updateReturnById(borrow);
        registerService.updateBorrowById(register.getId(),-1);
        model.addAttribute("suc","成功归还《"+journal.getName()+"》"+"第"+register.getYear()+"年，第"+register.getIssue()+"期");

        return "return";
    }
}
