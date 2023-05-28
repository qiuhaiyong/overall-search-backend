package com.yukiha.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiha.springbootinit.common.ErrorCode;
import com.yukiha.springbootinit.common.ResultUtils;
import com.yukiha.springbootinit.datasource.*;
import com.yukiha.springbootinit.exception.ThrowUtils;
import com.yukiha.springbootinit.model.dto.post.PostQueryRequest;
import com.yukiha.springbootinit.model.dto.search.SearchRequest;
import com.yukiha.springbootinit.model.dto.user.UserQueryRequest;
import com.yukiha.springbootinit.model.entity.Picture;
import com.yukiha.springbootinit.model.enums.SearchTypeEnum;
import com.yukiha.springbootinit.model.vo.PostVO;
import com.yukiha.springbootinit.model.vo.SearchVO;
import com.yukiha.springbootinit.model.vo.UserVO;
import com.yukiha.springbootinit.service.PictureService;
import com.yukiha.springbootinit.service.PostService;
import com.yukiha.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门面模式(Facade)
 * @author (oWo)
 * @date 2023/5/28 16:02
 */
@Component
@Slf4j
public class SearchManger {
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(SearchRequest searchRequest){
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        SearchVO searchVO = new SearchVO();
        //搜索出所有数据
        if (searchTypeEnum == null){
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current,pageSize);
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current,pageSize);
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return searchVO;
        }else {
            DataSource dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> dataList = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(dataList.getRecords());
            return searchVO;

        }
    }
}
