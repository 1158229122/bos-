package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.util.PinYin4jUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.pipe.AAShapePipe;

import java.io.IOException;
import java.util.*;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 11 19:46
 */
@RestController
@RequestMapping("area")
public class AreaController {
    @Autowired
    private AreaService areaService;
    @RequestMapping("upload")
    public ResponseResult upload( MultipartFile file){
        //获取文件的名称
        try {
            List<Area> list = new LinkedList<Area>();
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".")+1, filename.length());
            if("xls".equals(suffix)){
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum()==0){
                        continue;
                    }
                    if (row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                        continue;//过滤空行
                    }
                    //执行数据的封装
                    Area area = new Area();
                    area.setId(row.getCell(0).getStringCellValue());
                    area.setProvince(row.getCell(1).getStringCellValue());
                    area.setCity(row.getCell(2).getStringCellValue());
                    area.setDistrict(row.getCell(3).getStringCellValue());
                    area.setPostcode(row.getCell(4).getStringCellValue());

                    //citycode 城市简码 beijing
                    String city = row.getCell(2).getStringCellValue();
                    String cityStr = city.substring(0,city.length() - 1);
                    if (cityStr.contains("自治")){
                        cityStr=cityStr.substring(0,cityStr.length() - 2);
                    }
                    if (cityStr.contains("区")){
                        cityStr=cityStr.substring(0,cityStr.length() - 1);
                    }
                    String cityCode = PinYin4jUtils.hanziToPinyin(cityStr,"");

                    area.setCitycode(cityCode);

                    //shortcode; // 简码 BJHD
                    String province = row.getCell(1).getStringCellValue();
                    String district = row.getCell(3).getStringCellValue();
                    //字符串拼接去掉省市州
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(province.substring(0,province.length()-1)).
                            append(city.substring(0,city.length()-1)).
                            append(district.substring(0,district.length()-1));

                    String shortCodeStr = stringBuffer.toString();

                    String[] headString = PinYin4jUtils.getHeadByString(shortCodeStr);
                    String shortCode = StringUtils.join(headString);
                    area.setShortcode(shortCode);
                    area.setIsDelete("1");
                    //调用service保存
                    //System.out.println(area);
                    areaService.saveArea(area);
                }
            }
            return ResponseResult.SUCCESS();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.FAIL();
    }

    //分页条件查询
    @RequestMapping("searchFindAll")
    public Map searchFindAll(int page, int rows, Area area){
        Pageable pageable = PageRequest.of(page-1,rows);
        Page<Area> areas = areaService.searchFindAll(pageable, area);
        Map map = new HashMap();
        map.put("total",areas.getTotalElements());
        map.put("rows",areas.getContent());
        return map;
    }

    @RequestMapping("deleteByIds")
    public ResponseResult deleteById(String[] ids){
        try {
            areaService.deleteByIds(ids);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }

    @RequestMapping("updateStatus")
    public ResponseResult updateStatus(String[] ids){
        try {
            areaService.updateStatus(ids);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.FAIL();
        }
    }

    @RequestMapping("save")
    public ResponseResult save(Area area){
        try {
            areaService.saveArea(area);
            return  ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

}
