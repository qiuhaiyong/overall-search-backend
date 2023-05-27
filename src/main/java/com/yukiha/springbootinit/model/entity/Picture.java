package com.yukiha.springbootinit.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片
 * @author (oWo)
 * @date 2023/5/27 16:27
 */
@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private String url;
}
