package com.yukiha.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yukiha.springbootinit.model.entity.Post;
import com.yukiha.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取初始帖子列表
 * @author (oWo)
 * @date 2023/5/27 15:13
 */
//开启注解后，每次启动SpringBoot项目时会执行一次run方法
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {
    
    @Resource
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
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
        boolean isSuccessful = postService.saveBatch(postList);
        if (isSuccessful){
            log.info("获取初始化帖子列表成功，数据条数{}",postList.size());
        }else {
            log.error("获取初始化帖子列表失败~~~~");
        }
    }
}
