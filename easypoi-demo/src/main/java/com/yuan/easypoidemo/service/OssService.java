package com.yuan.easypoidemo.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;

/**
 * @author chen.yuanpeng
 * @date 2018/11/26 17:50
 */
public interface OssService {

    /**
     * 上传Excel返回URL
     * @param fileName
     * @param workbook
     * @return
     */
    String exportExcel(String fileName, Workbook workbook);

    /**
     * 上传文件
     * @param fileName
     * @param byteArrayInputStream
     * @return
     */
    String uploadFile(String fileName, ByteArrayInputStream byteArrayInputStream);
}
