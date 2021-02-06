package com.hulqframe.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 接口返回结果
 * */
@Data
public class ApiResult {

    @ApiModelProperty(example="200表示成功",name="返回状态码",dataType = "int")
    private int statusCode;//返回状态码 200成功 其他失败
    @ApiModelProperty(example="操作成功",name="提示信息",dataType = "String")
    private  String message;//提示信息
    @ApiModelProperty(example="page",name="接口返回值",dataType = "Object",notes = "请求数据结果通过data的形式返回")
    private Object data;//数据值
    public static ApiResult toSuccess(Object data){
        ApiResult apiResult=new ApiResult();
        apiResult.setData(data);
        apiResult.setStatusCode(200);
        apiResult.setMessage("操作成功");
        return apiResult;
    }
    public static ApiResult toFaild(Object data){
        ApiResult apiResult=new ApiResult();
        apiResult.setData(data);
        apiResult.setStatusCode(200);
        apiResult.setMessage("操作失败");
        return apiResult;
    }
    public static ApiResult toFaild(Object data,String mesage){
        ApiResult apiResult=new ApiResult();
        apiResult.setData(data);
        apiResult.setStatusCode(200);
        apiResult.setMessage(mesage);
        return apiResult;
    }
}
