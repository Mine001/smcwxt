package com.hulqframe.base.common.upload;


import com.alibaba.fastjson.JSON;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.common.upload.bean.UploadBean;
import com.hulqframe.exceptions.ApiException;
import com.hulqframe.system.model.Attachment;
import com.hulqframe.system.service.AttachmentService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UploadController {

    @Autowired
    private UploadComponent uploadComponent;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile,
                         HttpServletRequest request){
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        Result result1=Result.SUCCESS;
        result1.setData(result);
        return result1;
    }


    @RequestMapping("/upload/wangEditorUpload")
    @ResponseBody
    public String WangEditorUpload(@RequestParam("file") MultipartFile multipartFile,
                         HttpServletRequest request) {
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        return result.getFileUrl();

    }
    /**
     * CKEditor黏贴图片
     * */
    @RequestMapping("/upload/CKEditorPasteUpload")
    @ResponseBody
    public String CKEditorPasteUpload(@RequestParam("upload") MultipartFile multipartFile,
                                 HttpServletRequest request,HttpServletResponse response) throws IOException {
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        Map<String, String> reMap = new HashMap<>();

        //必须返回这样格式的json字符串
        reMap.put("uploaded", "1");
        reMap.put("url", result.getFileUrl());
        return JSON.toJSONString(reMap);

    }
    /**
     * CKEditor图片上传
     * */
    @RequestMapping("/upload/CKEditorUpload")
    @ResponseBody
    public String CKEditorUpload(@RequestParam("upload") MultipartFile multipartFile,
                                 @RequestParam("CKEditorFuncNum") String CKEditorFuncNum,
                                 HttpServletRequest request,HttpServletResponse response) throws IOException {
//        StringBuffer sb=new StringBuffer();
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        PrintWriter out = response.getWriter();
//        sb.append("<script type=\"text/javascript\">");
//        sb.append("window.parent.CKEDITOR.tools.callFunction("+ request.getParameter("CKEditorFuncNum") + ",'" +result.getFileUrl()+"','')");
//        sb.append("</script>");
//        return sb.toString();
        // 返回"图像"选项卡和图像在服务器的地址并显示图片
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" +result.getFileUrl()+"','')");
        out.println("</script>");
        out.close();
        return null;

    }

    @RequestMapping(value = "/res/{key}.{resType}")
    public void showAttr(@PathVariable(value = "key",required = false) String key,
                         @PathVariable(value = "resType",required = false) String resType,
                         HttpServletRequest request,HttpServletResponse response) {
        Attachment attachment = attachmentService.findByUnique("fileKey",key);
        if(attachment==null||!attachment.getFileName().contains(resType))
            throw new ApiException("文件不存在！");
        try {
            response.reset();
            /* 判断浏览器类型，设置文件下载名 */
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")||userAgent.contains("trident")||userAgent.contains("edge")) {
                attachment.setOriginalFilename(URLEncoder.encode(attachment.getOriginalFilename(), "UTF-8"));
            } else {
                attachment.setOriginalFilename(new String(attachment.getOriginalFilename().getBytes("utf-8"), "ISO8859-1"));
            }
            response.setHeader("Content-disposition", "attachment;filename="+attachment.getOriginalFilename());
            response.setContentType(attachment.getFileExtname());
            FileCopyUtils.copy(new FileInputStream(uploadComponent.getUploadPath()+attachment.getFilePath()), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
        }
    }

}
