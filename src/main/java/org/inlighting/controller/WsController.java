package org.inlighting.controller;

import org.inlighting.entity.RepMsgBean;
import org.inlighting.entity.RequestMessage;
import org.inlighting.entity.ResponseMessage;
import org.inlighting.service.WsService;
import org.inlighting.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private WsService wsService;

    @MessageMapping("/welcome")
    @SendTo("/topic/welcome")
    public ResponseMessage welcome(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome:" + message.getName() + " !");
    }

    @MessageMapping("/initialize")
    @SendToUser(value = "/topic/initialize", broadcast = false)
    public String initialize(SimpMessageHeaderAccessor headerAccessor, String payload) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        logger.info("/topic/initialize:attr:" + sessionAttributes.get("WEBSOCKET_USERNAME"));
        logger.info("/topic/initialize:payload" + payload);
        Map<String, Object> body = JsonUtil.json2Obj(payload, HashMap.class);
        Map<String, Object> data = new HashMap<>();
        data.put("message", body);
        RepMsgBean rep = new RepMsgBean("0000", "服务器返回收到的信息", data);
        return JsonUtil.getJsonString(rep);
    }

    /**
     * 定时推送消息
     */
    @Scheduled(fixedRate = 10000)
    public void loop() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        wsService.sendToAll("/callback", "定时推送消息时间: " + df.format(new Date()));
    }
}
