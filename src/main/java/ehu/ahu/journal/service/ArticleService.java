package ehu.ahu.journal.service;

import ehu.ahu.journal.dao.ArticleMapper;
import ehu.ahu.journal.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Keyu
 */
@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    public List<Article> selectArticleByBookId(int id){
        return articleMapper.selectByBookId(id);
    }

    public int insertArticle(Article article){
        return articleMapper.insertArticle(article);
    }

    public Article selectArtcleById(int id){
        return articleMapper.selectById(id);
    }

    public List<Article> selectAll(int offset,int limit){
        return articleMapper.selectLatestArticle(offset,limit);
    }

}
