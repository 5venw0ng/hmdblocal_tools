// 
// Decompiled by Procyon v0.5.36
// 

package com.ruoyi.common.utils.poi;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ExportExcelFile
{
    public static void exportExcelMap(final List<Map> content, final String[] fileTitel, final String mapKeys, String fileName) {
        final XSSFWorkbook wb = new XSSFWorkbook();
        final XSSFSheet sheet = wb.createSheet(fileName);
        final XSSFRow row = sheet.createRow(0);
        final String[] keys = mapKeys.split(",");
        for (int i = 0; i < fileTitel.length; ++i) {
            row.createCell(i).setCellValue(fileTitel[i]);
        }
        int i = 1;
        for (final Map<String, Object> map : content) {
            final XSSFRow nRow = sheet.createRow(i);
            for (int j = 0; j < keys.length; ++j) {
                if (map.containsKey(keys[j])) {
                    final Object obj = map.get(keys[j]);
                    final String str = "";
                    if (obj == null) {
                        nRow.createCell(j).setCellValue("");
                    }
                    else if (obj.getClass().getSimpleName().equals("Integer")) {
                        nRow.createCell(j).setCellValue((double)(int)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("Double")) {
                        nRow.createCell(j).setCellValue((double)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("BigDecimal")) {
                        nRow.createCell(j).setCellValue(((BigDecimal)obj).doubleValue());
                    }
                    else {
                        nRow.createCell(j).setCellValue(obj.toString());
                    }
                }
            }
            ++i;
        }
        fileName += ".xlsx";
        try {
            final String projectHome = System.getProperty("user.dir");
            final String filePath = projectHome + "/target/runtime/" + fileName;
            final FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write((OutputStream)fileOut);
            fileOut.flush();
            fileOut.close();
            wb.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void saveWb(String filePath, final XSSFWorkbook wb) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write((OutputStream)fileOut);
            fileOut.flush();
            fileOut.close();
            wb.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static XSSFWorkbook buildExcelByMap(final List<Map<String, Object>> content, final String[] fileTitel, final String mapKeys,String sheetName) {
        final XSSFWorkbook wb = new XSSFWorkbook();
        final XSSFSheet sheet = wb.createSheet(sheetName);
        final XSSFRow row = sheet.createRow(0);
        final String[] keys = mapKeys.split(",");
        for (int i = 0; i < fileTitel.length; ++i) {
            row.createCell(i).setCellValue(fileTitel[i]);
        }
        int i = 1;
        for (final Map<String, Object> map : content) {
            final XSSFRow nRow = sheet.createRow(i);
            for (int j = 0; j < keys.length; ++j) {
                if (map.containsKey(keys[j])) {
                    final Object obj = map.get(keys[j]);
                    final String str = "";
                    if (obj == null) {
                        nRow.createCell(j).setCellValue("");
                    }
                    else if (obj.getClass().getSimpleName().equals("Integer")) {
                        nRow.createCell(j).setCellValue((double)(int)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("Double")) {
                        nRow.createCell(j).setCellValue((double)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("BigDecimal")) {
                        nRow.createCell(j).setCellValue(((BigDecimal)obj).doubleValue());
                    }
                    else {
                        nRow.createCell(j).setCellValue(obj.toString());
                    }
                }
            }
            ++i;
        }
        return wb;
    }


    /**
     * @Description: 创建一个新的sheet
     * @Author: 老王
     * @Date: 2022/4/20 1:42 下午
     */
    public static XSSFWorkbook addSheetByMap(final List<Map<String, Object>> content, final String[] fileTitel, final String mapKeys,XSSFWorkbook wb,String sheetName) {

        final XSSFSheet sheet = wb.createSheet(sheetName);
        final XSSFRow row = sheet.createRow(0);
        final String[] keys = mapKeys.split(",");
        for (int i = 0; i < fileTitel.length; ++i) {
            row.createCell(i).setCellValue(fileTitel[i]);
        }
        int i = 1;
        for (final Map<String, Object> map : content) {
            final XSSFRow nRow = sheet.createRow(i);
            for (int j = 0; j < keys.length; ++j) {
                if (map.containsKey(keys[j])) {
                    final Object obj = map.get(keys[j]);
                    final String str = "";
                    if (obj == null) {
                        nRow.createCell(j).setCellValue("");
                    }
                    else if (obj.getClass().getSimpleName().equals("Integer")) {
                        nRow.createCell(j).setCellValue((double)(int)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("Double")) {
                        nRow.createCell(j).setCellValue((double)obj);
                    }
                    else if (obj.getClass().getSimpleName().equals("BigDecimal")) {
                        nRow.createCell(j).setCellValue(((BigDecimal)obj).doubleValue());
                    }
                    else {
                        nRow.createCell(j).setCellValue(obj.toString());
                    }
                }
            }
            ++i;
        }
        return wb;
    }
}
