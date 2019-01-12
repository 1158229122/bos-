package cn.itcast.bos.aop;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.util.CookieUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 10 15:22
 */
@Component
@Aspect//标注aop切面
public class CookieHandle {
    @Autowired
    private StandardDao standardDao;
    //访问的类
    private Class clazz;
    //访问的方法
    private Method method;

    @Before("execution(* cn.itcast.bos.service.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //获取cookie的值
        String cookieValue = CookieUtil.getCookieValue(request, "repeal", "UTF-8");
        List<Map> maps = null;
        if (cookieValue!=null||"".equals(cookieValue)){
             maps = JSON.parseArray(cookieValue, Map.class);
        } else {
             maps = new ArrayList<Map>();
        }

        clazz = jp.getTarget().getClass(); //具体要访问的类
        String methodName = jp.getSignature().getName(); //获取访问的方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数
        if (args==null||args.length==0){
            return;
        }
        //如果方法名为save
        if ("save".equals(methodName)){
            String responseValue = null;
           Standard standard = (Standard) args[0];
            System.out.println(standard.getId());
           if (standard.getId()!=null){
               //执行了修改的操作
               Map map = new HashMap();
               Optional<Standard> optionalStandard = standardDao.findById(standard.getId());
               Standard updateStandard = optionalStandard.get();
               map.put("update-standerd",updateStandard);
               maps.add(map);
               responseValue = JSON.toJSONString(maps);
               //响应给浏览器
               CookieUtil.setCookie(request,response,"repeal",responseValue,60*60*24,"UTF-8");
           }
        }
        if ("deleteById".equals(methodName)){
            //执行了删除方法
            Map map = new HashMap();
            Integer[] ids = (Integer[]) args[0];
            System.out.println(Arrays.toString(ids));
            map.put("delete-standerd",ids);
            maps.add(map);
            String responseValue = JSON.toJSONString(maps);
            //响应给浏览器
            System.out.println(responseValue+"执行了方法");
            CookieUtil.setCookie(request,response,"repeal",responseValue,60*60*24,"UTF-8");
        }
    }
}
