package com.hello.springboot.web;

import com.hello.springboot.dao.TestMapper;
import com.hello.springboot.entity.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Resource
    private TestMapper testMapper;

    @RequestMapping("/user")
    public String first(HttpSession httpSession){
        List<Test> list = testMapper.selectAll();
        StringBuffer sb = new StringBuffer();
        for(Test t : list){
            sb.append(t.toString()+"\n");
        }
        String message = sb.toString();
        httpSession.setAttribute("message", message);
        return "index";
    }

}
