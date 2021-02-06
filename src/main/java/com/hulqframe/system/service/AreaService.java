package com.hulqframe.system.service;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.AreaMapper;
import com.hulqframe.system.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

@Service
public class AreaService extends BaseService<Area,Integer> {
@Autowired
private AreaMapper areaMapper;
@Override
public BaseMapper<Area, Integer> getBaseMapper() {
return areaMapper;
}
    private static final String areaDataPath="";
    /**
     * 初始化areaData.js
     * */
    public void initAreaData() throws IOException {
        String path=ResourceUtils.getURL("classpath:").getPath();
        List<Map<String,Object>> list=areaMapper.getInitAreaData();
        String jsonArray=JSONArray.toJSONString(list);
//        File file=new File(areaDataPath);
//        if(!file.exists()){
//            file.mkdirs();
//        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+"/static/js/datas/areaData.js")),"utf-8"));
        out.write("var areaData="+jsonArray);
        out.close();
    }
}
