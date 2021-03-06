package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Admin;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:48
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    private ResponseResult login(HttpServletRequest req, HttpServletResponse response, Admin loginAdmin, String code){
        String checkcode = (String) req.getSession().getAttribute("checkcode");
        req.getSession().removeAttribute("checkcode");
        if (!code.equalsIgnoreCase(checkcode)){
            //清空验证码
            return new ResponseResult(false,"验证码不正确");
        }

        List<Admin> admin = adminService.login(loginAdmin);
        System.out.println(admin);
        if (admin==null||admin.size()==0){
            return new ResponseResult(false,"账号或者密码不正确");
        }
        if (admin.size()>1){
            throw new RuntimeException("结果集不唯一");
        }
        req.getSession().setAttribute("user",admin.get(0));
        return new ResponseResult(true,"登录成功");
    }
    @RequestMapping("getMessage")
    private Map getMessage(HttpServletRequest request){
        Map map= new HashMap();
        Admin user = (Admin) request.getSession().getAttribute("user");
        if (user!=null){
            map.put("name",((Admin) request.getSession().getAttribute("user")).getName());
        }
        map.put("uil",request.getLocalAddr());
        return map;
    }
}
