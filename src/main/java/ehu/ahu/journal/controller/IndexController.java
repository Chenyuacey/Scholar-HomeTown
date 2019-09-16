package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.Borrow;
import ehu.ahu.journal.pojo.Journal;
import ehu.ahu.journal.pojo.JournalInfo;
import ehu.ahu.journal.pojo.Register;
import ehu.ahu.journal.service.BorrowService;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Keyu
 */
@Controller
public class IndexController {
    @Autowired
    JournalService journalService;

    @Autowired
    RegisterService registerService;

    @Autowired
    BorrowService borrowService;

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/")
    public String home(Model model){
        List<JournalInfo> journalInfos = new ArrayList<>();
        List<Register> registers = registerService.selectLastedRegister(0,7);
        List<Borrow> borrows = borrowService.selectLastedBrrow(0,7);
        List<Map> borrowinfo = new ArrayList<>();
        for (Borrow borrow:borrows){
            Register register = registerService.selectRegisterbyId(borrow.getRegisterId());
            Journal journal = journalService.selectJournalbyId(register.getJournalId());
            Map<String,Object> map = new HashMap<>();
            map.put("reg",register);
            map.put("jour",journal);
            map.put("bor",borrow);
            borrowinfo.add(map);
        }
        for (Register register:registers){
            Journal journal = journalService.selectJournalbyId(register.getJournalId());
            JournalInfo info = new JournalInfo();
            info.setName(journal.getName());
            info.setLanguage(journal.getLanguage());
            info.setIssn(journal.getIssn());
            info.setPublisher(journal.getPublisher());
            info.setYear(register.getYear());
            info.setStartYear(journal.getStartYear());
            info.setIssue(register.getIssue());
            info.setBookId(register.getId());
            journalInfos.add(info);
        }
        model.addAttribute("journals",journalInfos);
        model.addAttribute("binfo",borrowinfo);
        return "base";}


    @GetMapping(value = "/failed")
    public String failed(){return "failed";}

    @GetMapping(value = "/admin/borrow")
    public String borrow(){return "borrow";}

    @GetMapping(value = "/admin/enter")
    public String enter(){return "enter";}

    @GetMapping(value = "/admin/enterpaper")
    public String enterPaper(){return "enterpaper";}

    @GetMapping(value = "/admin/zd")
    public String zd(){return "zhengding1";}

    @GetMapping(value = "/search")
    public String search(){return "list";}

    @GetMapping(value = "/admin/return")
    public String areturn(){return "return";}

    @PostMapping(value = "/admin/zd")
    public String zd2(){return "bookqq";}


}
