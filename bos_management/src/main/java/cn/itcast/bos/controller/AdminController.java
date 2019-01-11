package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Admin;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired(required=true)
    private AdminService adminService;

    @RequestMapping("login")
    private ResponseResult login(HttpServletRequest req, HttpServletResponse response, Admin loginAdmin, String code){
        String checkcode = (String) req.getSession().getAttribute("checkcode");
        req.getSession().removeAttribute("checkcode");
        if (!code.equalsIgnoreCase(checkcode)){
            //清空验证码
            return new ResponseResult(false,"验证码不正确");
        }
        System.out.println(adminService);

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
    public Map getMessage(HttpServletRequest req){
        Map<String,String> map = new HashMap<String,String>();
        Admin user = (Admin) req.getSession().getAttribute("user");
        map.put("uil",getIpAddress(req));
        if (user!=null){
            map.put("name",user.getName());
        }

        return map;
    }

    /**
     * 获取用户真实的IP地址
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
