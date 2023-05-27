package com.yukiha.springbootinit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sun.javafx.binding.StringFormatter;
import com.yukiha.springbootinit.model.entity.Picture;
import com.yukiha.springbootinit.model.entity.Post;
import com.yukiha.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.List;
import java.util.Map;

/**
 * @author (oWo)
 * @date 2023/5/27 14:18
 */
@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;
    @Test
    public void  testFetchMessage(){
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result2 = HttpRequest.post(url)
                .body(json)
                .execute().body();
//        System.out.println(result2);
        Map<String,Object> map = JSONUtil.toBean(result2,Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray)  data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tmpRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tmpRecord.getStr("title"));
            post.setContent(tmpRecord.getStr("content"));
            JSONArray tags = (JSONArray) tmpRecord.get("tags");
            post.setTags(tags.toString());
            post.setUserId(1L);
            postList.add(post);
        }
        System.out.println(postList);
        postService.saveBatch(postList);
    }

    @Test
    public void testFetchPicture() throws IOException {
        int current = 1;
        String content = "放学后失眠的你";
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=1",content);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Element needElement = element.select(".iusc").get(0);
            String m = needElement.attr("m");
            Map<String,String>  map = JSONUtil.toBean(m,Map.class);
            Picture picture = new Picture();
            picture.setTitle(map.get("t"));
            picture.setUrl(map.get("murl"));
            pictureList.add(picture);
        }
    }
}
