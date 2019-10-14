package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.Article;
import ehu.ahu.journal.pojo.Borrow;
import ehu.ahu.journal.pojo.Register;
import ehu.ahu.journal.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author:Keyu
 */
@Controller
public class BookController {
    @Autowired
    JournalService journalService;

    @Autowired
    RegisterService registerService;

    @Autowired
    ArticleService articleService;

    @Autowired
    BorrowService borrowService;

    @Autowired
    UserService userService;

    @GetMapping("/book")
    public String show(Model model, @Param("id") int id){
        Register register = registerService.selectRegisterbyId(id);
        List<Article> articles = articleService.selectArticleByBookId(id);
        if (register.getBorrow() != -1){
            Borrow borrow = borrowService.selectBorrowById(register.getBorrow());
            model.addAttribute("borrow",borrow);
            model.addAttribute("borrower",userService.selectUserbyId(borrow.getUserId()).getName());
        }

        model.addAttribute("articles",articles);
        model.addAttribute("book",register);
        model.addAttribute("journal",journalService.selectJournalbyId(register.getJournalId()));
        return "book";}
}
