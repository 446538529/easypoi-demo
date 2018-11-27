package com.yuan.easypoidemo.controller;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yuan.easypoidemo.dto.PersonExcelDto;
import com.yuan.easypoidemo.service.OssService;
import com.yuan.easypoidemo.util.PoiUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chen.yuanpeng
 * @date 2018/11/26 10:36
 */
@RestController("/yuan")
public class Controller {

    @Autowired
    OssService ossService;

    @RequestMapping("/export")
    public void export(HttpServletResponse response){

        //模拟从数据库获取需要导出的数据
        List<PersonExcelDto> personList = new ArrayList<PersonExcelDto>();
        PersonExcelDto person1 = new PersonExcelDto("路飞","1",new Date());
        PersonExcelDto person2 = new PersonExcelDto("娜美","2", DateUtils.addDays(new Date(),3));
        PersonExcelDto person3 = new PersonExcelDto("索隆","1", DateUtils.addDays(new Date(),10));
        PersonExcelDto person4 = new PersonExcelDto("小狸猫","1", DateUtils.addDays(new Date(),-10));
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        //导出操作
        PoiUtil.exportExcel(personList,"花名册","草帽一伙", PersonExcelDto.class,"海贼王.xls",response);
    }

    @RequestMapping("importExcel")
    public void importExcel(){
        String filePath = "F:\\海贼王.xls";
        //解析excel，
        List<PersonExcelDto> personList = PoiUtil.importExcel(filePath,1,1, PersonExcelDto.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");

        //TODO 保存数据库
    }

    /**
     * 导出Excel返回文件链接
     */
    @RequestMapping("/exportUrl")
    public String export(){

        //模拟从数据库获取需要导出的数据
        List<PersonExcelDto> personList = new ArrayList<PersonExcelDto>();
        PersonExcelDto person1 = new PersonExcelDto("路飞","1",new Date());
        PersonExcelDto person2 = new PersonExcelDto("娜美","2", DateUtils.addDays(new Date(),3));
        PersonExcelDto person3 = new PersonExcelDto("索隆","1", DateUtils.addDays(new Date(),10));
        PersonExcelDto person4 = new PersonExcelDto("小狸猫","1", DateUtils.addDays(new Date(),-10));
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        //生成Excel
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("花名册", "草帽一伙"),PersonExcelDto.class,personList);
        //生成链接
        String url = ossService.exportExcel("导出Excel返回文件链接.xls",workbook);

        return url;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile uploadFile){
        String url = "上传失败";

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadFile.getBytes());
            url = ossService.uploadFile("测试文件上传",byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;


    }



}
