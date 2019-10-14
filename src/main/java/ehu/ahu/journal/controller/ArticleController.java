package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.Article;
import ehu.ahu.journal.pojo.Journal;
import ehu.ahu.journal.pojo.Register;
import ehu.ahu.journal.service.ArticleService;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import ehu.ahu.journal.service.SearchService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author:Keyu
 */
@Controller
public class ArticleController {

    @Autowired
    JournalService journalService;

    @Autowired
    ArticleService articleService;

    @Autowired
    RegisterService registerService;

    @Autowired
    SearchService searchService;

    @PostMapping("/admin/enterpaper")
    public String enterpaper(Model model,
                             @Param("issn") String issn,
                             @Param("author") String author,
                             @Param("articleName") String articleName,
                             @Param("key1") String key1,
                             @Param("key2") String key2,
                             @Param("key3") String key3,
                             @Param("key4") String key4,
                             @Param("key5") String key5,
                             @Param("year") int year,
                             @Param("issue") int issue,
                             @Param("pageNum") int pageNum){

        Journal journal = journalService.selectJournalByIssn(issn);
        if (journal == null){
            model.addAttribute("msg","期刊目录没有此期刊");
            return "enterpaper";
        }
        Register register = registerService.selectRegisterByYearAndIssue(year,issue,journal.getId());
        if (register == null){
            model.addAttribute("msg","该期刊还没有入库");
            return "enterpaper";
        }
        Article article = new Article();
        article.setPageNum(pageNum);
        article.setAuthor(author);
        article.setArticleName(articleName);
        article.setJournalId(register.getId());
        article.setKeyword1(key1);
        article.setKeyword2(key2);
        article.setKeyword3(key3);
        article.setKeyword4(key4);
        article.setKeyword5(key5);
        article.setJournalId(register.getId());
        articleService.insertArticle(article);
        int id = article.getId();
        try {
            article.setId(id);
            searchService.addArticleIndex(article);
        }catch (Exception e){
            e.printStackTrace();
        }


        model.addAttribute("suc","成功登记入《"+journal.getName()+"》"+"第"+register.getYear()+"年，第"+register.getIssue()+"期");
        return "enterpaper";

    }

}
