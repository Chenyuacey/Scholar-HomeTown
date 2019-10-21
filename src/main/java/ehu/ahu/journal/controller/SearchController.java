package ehu.ahu.journal.controller;

import ehu.ahu.journal.pojo.*;
import ehu.ahu.journal.service.ArticleService;
import ehu.ahu.journal.service.JournalService;
import ehu.ahu.journal.service.RegisterService;
import ehu.ahu.journal.service.SearchService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Keyu
 */
@Controller
public class SearchController {

    @Autowired
    SearchService searchService;
    @Autowired
    JournalService journalService;
    @Autowired
    RegisterService registerService;
    @Autowired
    ArticleService articleService;

    /**
     * 查询期刊
     */
    @PostMapping("/search")
    public String search(Model model,
                         @Param("word") String word) {
        List<SearchInfo> searchinfos = new ArrayList<>();
        try {
            List<Article> articles = searchService.searchArticle(word, 0, 10, "<b>", "</b>");
            if (articles != null) {
                for (Article article : articles) {
                    if (article.getId() == 0) {
                        continue;
                    }
                    Article article1 = articleService.selectArtcleById(article.getId());
                    Register register = registerService.selectRegisterbyId(article1.getJournalId());
                    Journal journal = journalService.selectJournalbyId(register.getJournalId());
                    if (article.getArticleName() == null) {
                        article.setArticleName(article1.getArticleName());
                    }
                    if (article.getAuthor() == null) {
                        article.setAuthor(article1.getAuthor());
                    }
                    if (article.getKeyword1() == null) {
                        article.setKeyword1(article1.getKeyword1());
                    }
                    if (article.getKeyword2() == null) {
                        article.setKeyword2(article1.getKeyword2());
                    }
                    if (article.getKeyword3() == null) {
                        article.setKeyword3(article1.getKeyword3());
                    }
                    if (article.getKeyword4() == null) {
                        article.setKeyword4(article1.getKeyword4());
                    }
                    if (article.getKeyword5() == null) {
                        article.setKeyword5(article1.getKeyword5());
                    }
                    if (article.getJournalId() == 0) {
                        article.setJournalId(article1.getJournalId());
                    }
                    SearchInfo searchInfo = new SearchInfo();
                    searchInfo.setArticle(article);
                    searchInfo.setJournal(journal);
                    searchInfo.setRegister(register);
                    searchinfos.add(searchInfo);
                }
                if (searchinfos.size() > 0) {
                    model.addAttribute("searchs", searchinfos);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "list";
        }
    }


}
