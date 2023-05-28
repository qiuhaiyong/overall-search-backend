package com.yukiha.springbootinit.controller;

import com.yukiha.springbootinit.common.BaseResponse;
import com.yukiha.springbootinit.common.ResultUtils;
import com.yukiha.springbootinit.manager.SearchManger;
import com.yukiha.springbootinit.model.dto.search.SearchRequest;
import com.yukiha.springbootinit.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author (oWo)
 * @date 2023/5/27 23:05
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private SearchManger searchManger;


    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearch(@RequestBody SearchRequest searchRequest, HttpServletRequest httpServletRequest){
        SearchVO searchVO = searchManger.searchAll(searchRequest);
        return ResultUtils.success(searchVO);
    }
}
