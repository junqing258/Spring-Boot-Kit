package org.inlighting.service.impl;

import org.inlighting.entity.RepMsgBean;
import org.inlighting.service.WsService;
import org.inlighting.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("wsService")
public class WsServiceImpl implements WsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WsServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void sendToUser(String router, String message) {
        RepMsgBean rep = new RepMsgBean("0000", "服务器返回收到的信息", message);
        messagingTemplate.convertAndSend("/user/topic"+router, JsonUtil.getJsonString(rep));
    }

    @Override
    public void sendToAll(String router, String message) {
        RepMsgBean rep = new RepMsgBean("0000", "服务器返回收到的信息", message);
        messagingTemplate.convertAndSend("/topic"+router, JsonUtil.getJsonString(rep));
    }
}
