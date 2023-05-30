package com.yukiha.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiha.springbootinit.model.dto.post.PostQueryRequest;
import com.yukiha.springbootinit.model.entity.Post;
import com.yukiha.springbootinit.model.vo.PostVO;
import com.yukiha.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子数据源实现
 *
 * @author (oWo)
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;


    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setCurrent(pageNum);
        //获取当前httpServletRequest
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        Page<PostVO> postVOPage = postService.getPostVOPage(postPage, request);
        return postVOPage;
    }
}




