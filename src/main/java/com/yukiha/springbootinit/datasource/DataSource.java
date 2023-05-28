package com.yukiha.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口 （接入d的数据源必须实现）
 * @author (oWo)
 * @date 2023/5/28 16:24
 */
public interface DataSource <T>{

    /**
     * 搜索
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
