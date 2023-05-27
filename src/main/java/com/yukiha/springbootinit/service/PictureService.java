package com.yukiha.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiha.springbootinit.model.entity.Picture;

/**
 * 图片服务
 *
 * @author (oWo)
 */
public interface PictureService {
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
