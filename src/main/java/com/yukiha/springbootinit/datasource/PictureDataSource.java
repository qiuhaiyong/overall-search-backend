package com.yukiha.springbootinit.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiha.springbootinit.common.ErrorCode;
import com.yukiha.springbootinit.exception.BusinessException;
import com.yukiha.springbootinit.model.entity.Picture;
import com.yukiha.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片数据源实现
 *
 * @author (oWo)
 */
@Service
@Slf4j
public class PictureDataSource implements DataSource<Picture> {

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum -1 ) * pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=" + current,searchText);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Element needElement = element.select(".iusc").get(0);
            String m = needElement.attr("m");
            Map<String,String> map = JSONUtil.toBean(m,Map.class);
            Picture picture = new Picture();
            picture.setTitle(map.get("t"));
            picture.setUrl(map.get("murl"));
            pictureList.add(picture);
            if (pictureList.size() >= pageSize){
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum,pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
