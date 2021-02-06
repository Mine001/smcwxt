package com.hulqframe.base.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class LayUIPage {
    private int code;
    private String msg;
    private long count;
    private List data;
    public static LayUIPage  getLayUIPageByPageInfo(PageInfo page){
        LayUIPage layUIPage=new LayUIPage();
        layUIPage.setCount(page.getTotal());
        layUIPage.setData(page.getList());
        layUIPage.setCode(0);
        return layUIPage;
    }
    public static LayUIPage  getLayUIPageByData(List data){
        LayUIPage layUIPage=new LayUIPage();
        layUIPage.setCount(data.size());
        layUIPage.setData(data);
        layUIPage.setCode(0);
        return layUIPage;
    }
}
