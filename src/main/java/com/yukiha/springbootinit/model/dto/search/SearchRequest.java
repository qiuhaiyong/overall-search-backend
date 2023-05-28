package com.yukiha.springbootinit.model.dto.search;

import com.yukiha.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author (oWo)
 * @date 2023/5/27 23:08
 */
@Data
public class SearchRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 搜索类型
     */
    private String type;

}
