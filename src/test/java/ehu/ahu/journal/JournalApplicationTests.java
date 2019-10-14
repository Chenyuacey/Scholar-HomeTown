package ehu.ahu.journal;

import ehu.ahu.journal.dao.*;
import ehu.ahu.journal.pojo.*;
import ehu.ahu.journal.service.ArticleService;
import ehu.ahu.journal.service.SearchService;
import ehu.ahu.journal.utils.Md5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JournalApplicationTests {

//    @Autowired
//    JournalMapper journalMapper;
//
//    @Autowired
//    PubMapper pubMapper;
//
//    @Autowired
//    BorrowMapper borrowMapper;
//
//    @Autowired
//    SubscribeMapper subscribeMapper;
//
    @Autowired
    ArticleMapper articleMapper;

//    @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    RegisterMapper registerMapper;
//
    @Autowired
    SearchService searchService;
//
    @Autowired
    ArticleService articleService;
//
//    @Test
//    public void journalTest() {
//        Journal journal = new Journal();
//        journal.setId(2180);
//        journal.setName("自动化学报");
//        journal.setCn("11-1826/TP");
//        journal.setIssn("0254-4156");
//        journal.setMailCode("2-180");
//        journal.setPubCycle("月刊");
//        journal.setPubPlace("北京");
//        journal.setPublisher("中国自动化学会");
//        journal.setStartYear(1998);
//        journal.setLanguage("中文");
//        journal.setSize(80);
//
//        //journalMapper.insertJournal(journal);
//        List<Journal> journals = journalMapper.selectLatestJournal(0,10);
//        for (Journal journal1:journals){
//            System.out.println(journal1.getName());
//        }
//        System.out.println(journalMapper.selectById(2180).getName());
//
//    }
//
//    @Test
//    public void pubTest(){
////        Pub pub = new Pub();
////        pub.setArticleId(1);
////        pub.setJournalId(1);
////        pubMapper.insertPub(pub);
//
//
//    }
//
//    @Test
//    public void BorrowTest(){
//        Borrow borrow = new Borrow();
//        borrow.setId(1);
//        borrow.setReturnDate(new Date());
////        borrow.setJournalId(1);
////        borrow.setUserId(1);
//        //borrowMapper.insertBorrow(borrow);
//        borrowMapper.updateReturnDate(borrow);
//
//    }
//
//    @Test
//    public void SubTest(){
//        Subscribe sub = new Subscribe();
//        sub.setJournalId(2180);
//        sub.setYear(2018);
//        //subscribeMapper.insertSubscribe(sub);
//
//
//        Register register = new Register();
//        register.setJournalId(2180);
//        register.setYear(2000);
//        register.setIssue(1);
//        registerMapper.insertRegister(register);
//    }
//
//    @Test
//    public void ArtTest(){
//        Article article = new Article();
//        article.setArticleName("平行学习-机器学习的一个新型理论框架");
//        article.setJournalId(2180);
//        article.setAuthor("李力林");
//        article.setKeyword1("机器学习");
//        article.setKeyword2("平行学习");
//        article.setKeyword3("平行智能");
//        article.setPageNum(15);
//        //articleMapper.insertArticle(article);
//        List<BorrowInfo> borrowInfos = userMapper.selectBorrowInfoJournalbyId(1);
//        for (BorrowInfo borrowInfo:borrowInfos){
//            System.out.println(borrowInfo.getName()+borrowInfo.getBorrowDate());
//        }
//
//
//    }
    @Test
    public void insertsearch(){
        List<Article> articles = articleService.selectAll(0,50);
        try {
            for (Article article:articles){
                searchService.addArticleIndex(article);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
//
//    @Test
//    public void find(){
//        try {
//            List<Article> articles = articleMapper.selectLatestArticle(0,50);
//            for (Article article:articles){
//                System.out.println(article.getArticleName());
//                searchService.addArticleIndex(article);
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//    }

}
