package ehu.ahu.journal.service;

import ehu.ahu.journal.pojo.Article;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Keyu
 */
@Service
public class SearchService {
    //solr服务器地址
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/demo";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String ARTICLE_NAME = "article_article_name";
    private static final String AUTHOR = "article_author";
    private static final String KEY1 = "article_keyword1";
    private static final String KEY2 = "article_keyword2";
    private static final String KEY3 = "article_keyword3";
    private static final String KEY4 = "article_keyword4";
    private static final String KEY5 = "article_keyword5";

    public List<Article> searchArticle(String keyword, int offset, int count,
                                        String hlPre, String hlPos) throws Exception{
        List<Article> articles = new ArrayList<>();

        //搜索设置
        SolrQuery query = new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl",ARTICLE_NAME+","+AUTHOR+","+KEY1+","+KEY2+","+KEY3+","+KEY4+","+KEY5);
        query.setFields("article_name","author","keyword1","keyword2","keyword3","keyword4","keyword5");
        QueryResponse response = client.query(query);
        //取出信息
        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Article article = new Article();
            article.setId(Integer.parseInt(entry.getKey()));

            //名字
            if (entry.getValue().containsKey(ARTICLE_NAME)) {
                List<String> nameList = entry.getValue().get(ARTICLE_NAME);
                if (nameList.size() > 0) {
                    article.setArticleName(nameList.get(0));
                }
            }
            //作者
            if (entry.getValue().containsKey(AUTHOR)) {
                List<String> authorList = entry.getValue().get(AUTHOR);
                if (authorList.size() > 0) {
                    article.setAuthor(authorList.get(0));
                }
            }
            //KEY
            if (entry.getValue().containsKey(KEY1)) {
                List<String> key1List = entry.getValue().get(KEY1);
                if ( key1List.size() > 0) {
                    article.setKeyword1(key1List.get(0));
                }
            }
            //KEY
            if (entry.getValue().containsKey(KEY2)) {
                List<String> key2List = entry.getValue().get(KEY2);
                if ( key2List.size() > 0) {
                    article.setKeyword2(key2List.get(0));
                }
            }
            //KEY
            if (entry.getValue().containsKey(KEY3)) {
                List<String> key3List = entry.getValue().get(KEY3);
                if ( key3List.size() > 0) {
                    article.setKeyword3(key3List.get(0));
                }
            }
            //KEY
            if (entry.getValue().containsKey(KEY4)) {
                List<String> key4List = entry.getValue().get(KEY4);
                if ( key4List.size() > 0) {
                    article.setKeyword4(key4List.get(0));
                }
            }
            //KEY
            if (entry.getValue().containsKey(KEY5)) {
                List<String> key5List = entry.getValue().get(KEY5);
                if ( key5List.size() > 0) {
                    article.setKeyword5(key5List.get(0));
                }
            }
            articles.add(article);
        }


        return articles;
    }

    public boolean addArticleIndex(Article article) throws Exception{
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",article.getId());
        document.setField(ARTICLE_NAME,article.getArticleName());
        document.setField(AUTHOR,article.getAuthor());
        document.setField(KEY1,article.getKeyword1());
        document.setField(KEY2,article.getKeyword2());
        document.setField(KEY3,article.getKeyword3());
        document.setField(KEY4,article.getKeyword4());
        document.setField(KEY5,article.getKeyword5());
        UpdateResponse response = client.add(document,1000);
        return response != null && response.getStatus() == 0;
    }
}
