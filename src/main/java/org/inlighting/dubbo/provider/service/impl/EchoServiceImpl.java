package org.inlighting.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.inlighting.dubbo.provider.service.IEchoService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Service(interfaceClass = IEchoService.class, version = "1.0.0")
@Component
public class EchoServiceImpl implements IEchoService {
    @Override
    public String echo(String content) {
        return "hello:" + Objects.toString(content,"null");
    }
}
