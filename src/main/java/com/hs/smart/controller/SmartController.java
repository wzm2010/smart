package com.hs.smart.controller;

import com.hs.smart.Const;
import org.apache.catalina.Session;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Controller
public class SmartController {
    private final ConcurrentMap map = new ConcurrentHashMap();

    @RequestMapping(value={"/"} , method = RequestMethod.GET)
    public String welcome(){
        return "join";
    }

    @RequestMapping(value={"/join"} , method = RequestMethod.POST)
    public String join(String email, HttpSession session, Model model){
        map.put(email,session);
        map.forEach((key,value)->System.out.println(key+"_"+((HttpSession)value).getId()));
        model.addAttribute("list",map);
        return "center";
    }

    @RequestMapping("/getMap")
    @ResponseBody
    public ConcurrentMap getMap(){
        return map;
    }

    private Map<String,Object> rs = new HashMap<String, Object>();
    @MessageMapping("/chats/join")
    @SendTo("/message/chats")
    public Map<String,Object> addChats(){
        rs.put(Const.STATE,"ok");
        rs.put(Const.MESSAGE,"welcome join chat");
        return  rs;
    }

}
