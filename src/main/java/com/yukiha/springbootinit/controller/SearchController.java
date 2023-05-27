package com.yukiha.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiha.springbootinit.common.BaseResponse;
import com.yukiha.springbootinit.common.ResultUtils;
import com.yukiha.springbootinit.model.dto.post.PostQueryRequest;
import com.yukiha.springbootinit.model.dto.search.SearchRequest;
import com.yukiha.springbootinit.model.dto.user.UserQueryRequest;
import com.yukiha.springbootinit.model.entity.Picture;
import com.yukiha.springbootinit.model.vo.PostVO;
import com.yukiha.springbootinit.model.vo.SearchVO;
import com.yukiha.springbootinit.model.vo.UserVO;
import com.yukiha.springbootinit.service.PictureService;
import com.yukiha.springbootinit.service.PostService;
import com.yukiha.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author (oWo)
 * @date 2023/5/27 23:05
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private PictureService pictureService;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;
    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearch(@RequestBody SearchRequest searchRequest, HttpServletRequest httpServletRequest){
        String searchText = searchRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVoByPage(userQueryRequest);
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVoByPage(postQueryRequest, httpServletRequest);
        SearchVO searchVO = new SearchVO();
        searchVO.setUserList(userVOPage.getRecords());
        searchVO.setPostList(postVOPage.getRecords());
        searchVO.setPictureList(picturePage.getRecords());
        return ResultUtils.success(searchVO);
    }
}
