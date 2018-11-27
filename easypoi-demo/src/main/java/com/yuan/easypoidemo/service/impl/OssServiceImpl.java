package com.yuan.easypoidemo.service.impl;

import com.aliyun.oss.OSSClient;
import com.yuan.easypoidemo.service.OssService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author chen.yuanpeng
 * @date 2018/11/26 17:51
 */
@Service
public class OssServiceImpl implements OssService {

    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "###";
    private static String accessKeySecret = "###";
    private static String bucketName = "###";

    private OSSClient ossClient = null;

    public OSSClient getOSSClient() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    @Override
    public String exportExcel(String fileName, Workbook workbook) {

        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            workbook.write(byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            byteArrayInputStream.close();
            byteArrayOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //上传
        getOSSClient();
        ossClient.putObject(bucketName,fileName,byteArrayInputStream);
        //获取链接
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        //关闭
        ossClient.shutdown();
        //返回链接
        return url;
    }

    @Override
    public String uploadFile(String fileName, ByteArrayInputStream byteArrayInputStream) {
        //上传
        getOSSClient();
        ossClient.putObject(bucketName,fileName,byteArrayInputStream);
        //获取链接
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        //关闭
        ossClient.shutdown();
        //返回链接
        return url;
    }


}
