package com.yukiha.springbootinit.datasource;

import com.yukiha.springbootinit.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author (oWo)
 * @date 2023/5/28 17:21
 */

@Component
public class DataSourceRegistry {
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    private Map<String,DataSource<T>> dataSourceMap;

    @PostConstruct
     public void doInit(){
        dataSourceMap = new HashMap(){{
            put(SearchTypeEnum.POST.getValue(),postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
            put(SearchTypeEnum.USER.getValue(),userDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type){
        if (dataSourceMap == null){
            return null;
        }
        return dataSourceMap.get(type);
    }
}
