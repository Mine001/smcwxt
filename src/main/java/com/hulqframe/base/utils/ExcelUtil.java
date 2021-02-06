package com.hulqframe.base.utils;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * 路径：com.example.demo.utils
 * 类名：
 * 功能：导入导出
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 11:21
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
public class ExcelUtil {

    /**
     * 方法名：exportExcel
     * 功能：导出Excel
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:00
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static void exportExcel(HttpServletResponse response, List data, String[] headers,String[] cols,
                                   String fileName) {
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            HSSFSheet sheet = workbook.createSheet("sheet");
            //设置表头
            setTitle(workbook, sheet, headers);
            //设置单元格并赋值
            setData(sheet, data,cols);
            //设置浏览器下载
            setBrowser(response, workbook,fileName);
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setTitle
     * 功能：设置表头
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 10:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
            //创建表头名称
            HSSFCell cell;
            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setData
     * 功能：表格赋值
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:11
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setData(HSSFSheet sheet, List data,String[] cols) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                for (int j = 0; j < cols.length; j++) {
                    String attrName = cols[j];
                    Class<?> attrType = data.get(i).getClass().getDeclaredField(attrName).getType();
                    Method method = getGetMethod(data.get(i).getClass(), attrName);
                    Object value= method.invoke(data.get(i), new Object[0]);
                    String newValue="";
                    if(attrType.isInstance(Integer.class)||attrType.isInstance(int.class)){
                        newValue=String.valueOf(value);
                    }else if(attrType.isInstance(Double.class)){
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(newValue);
                    }else if(attrType.isInstance(Float.class)){
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(newValue);
                    } else if(attrType.isInstance(BigDecimal.class)){
                        BigDecimal b = new BigDecimal(newValue);
                        Double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(d);
                    }else if(attrType.isInstance(Date.class)||attrType.isInstance(Timestamp.class)){
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
                        newValue=format.format(value);
                    }else if(attrType.isInstance(Boolean.class)){
                        newValue=String.valueOf(value);
                    }else{
                        newValue= (String) value;
                    }
                    row.createCell(j).setCellValue(newValue);
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    private static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 方法名：setBrowser
     * 功能：使用浏览器下载
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" +java.net.URLEncoder.encode(fileName, "UTF-8"));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setContentType("octets/stream;charset=utf-8");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }

    }


    /**
     * 方法名：importExcel
     * 功能：导入
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 11:45
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static List<Object[]> importExcel(String fileName) {
        log.info("导入解析开始，fileName:{}",fileName);
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (cell.getCellType().equals(NUMERIC)) {
                        objects[index] = (int) cell.getNumericCellValue();
                    }
                    if (cell.getCellType().equals(STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (cell.getCellType().equals(BOOLEAN)) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (cell.getCellType().equals(ERROR)) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        }catch (Exception e){
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法名：exportExcel
     * 功能：导出Excel
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:00
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static void exportExcelX(HttpServletResponse response, List data, String[] headers,String[] cols,
                                   String fileName) {
        try {
            //实例化HSSFWorkbook
            SXSSFWorkbook workbook = new SXSSFWorkbook ();
            workbook.setCompressTempFiles(true);
            //创建一个Excel表单，参数为sheet的名字
            SXSSFSheet sheet = workbook.createSheet("sheet");
            //设置表头
            setTitleX(workbook, sheet, headers);
            //设置单元格并赋值
            setDataX(sheet, data,cols);
            //设置浏览器下载
            setBrowserX(response, workbook,fileName);
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!");
            e.printStackTrace();
        }
    }
    /**
     * 方法名：setTitle
     * 功能：设置表头
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 10:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setTitleX(SXSSFWorkbook workbook, SXSSFSheet sheet, String[] str) {
        try {
            SXSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            CellStyle  style = workbook.createCellStyle();
            Font  font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
            //创建表头名称
            SXSSFCell cell;
            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }

    /**
     * 方法名：setData
     * 功能：表格赋值
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:11
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setDataX(SXSSFSheet sheet, List data,String[] cols) {
        try{
            int rowNum = 1;
            for (int i = 0; i < data.size(); i++) {
                SXSSFRow  row = sheet.createRow(rowNum);
                for (int j = 0; j < cols.length; j++) {
                    String attrName = cols[j];
                    Class<?> attrType = data.get(i).getClass().getDeclaredField(attrName).getType();
                    Method method = getGetMethod(data.get(i).getClass(), attrName);
                    Object value= method.invoke(data.get(i), new Object[0]);
                    String newValue="";
                    if(value instanceof Integer){
                        newValue=String.valueOf(value);
                    }else if(value instanceof Double){
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(newValue);
                    }else if(value instanceof Float){
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(newValue);
                    } else if(value instanceof BigDecimal){
                        BigDecimal b = new BigDecimal(newValue);
                        Double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        DecimalFormat df = new DecimalFormat("0.00");
                        newValue=df.format(d);
                    }else if(value instanceof Date||value instanceof Timestamp){
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
                        newValue=format.format(value);
                    }else if(value instanceof Boolean){
                        newValue=String.valueOf(value);
                    }else{
                        newValue= String.valueOf(value);
                    }
                    row.createCell(j).setCellValue(newValue);
                }
                rowNum++;
            }
            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }
    /**
     * 方法名：setBrowser
     * 功能：使用浏览器下载
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    private static void setBrowserX(HttpServletResponse response, SXSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" +java.net.URLEncoder.encode(fileName, "UTF-8"));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }

    }
}
