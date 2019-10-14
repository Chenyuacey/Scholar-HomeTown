package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.Journal;
import ehu.ahu.journal.pojo.Register;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author:Keyu
 */
@Controller
public class RegisterController {

    @Autowired
    JournalService journalService;

    @Autowired
    RegisterService registerService;

    @PostMapping("/admin/enter")
    public String reg(Model model,
                      @Param("issn") String issn,
                      @Param("year") int year,
                      @Param("issue") int issue){
        Journal journal = journalService.selectJournalByIssn(issn);
        Register  registerold = registerService.selectRegisterByYearAndIssue(year,issue,journal.getId());
        if (registerold != null){
            model.addAttribute("msg","期刊已入库，请勿重复入库");
            return "enter";
        }

        if (journal == null){
            model.addAttribute("msg","期刊目录没有此期刊");
            return "enter";
        }
        Register register = new Register();
        register.setIssue(issue);
        register.setJournalId(journal.getId());
        register.setBorrow(-1);
        register.setYear(year);
        registerService.insertRegister(register);
        model.addAttribute("suc","成功登记《"+journal.getName()+"》"+"第"+register.getYear()+"年，第"+register.getIssue()+"期");


        return "enter";
    }

}
