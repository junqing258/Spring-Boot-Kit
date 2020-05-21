package org.inlighting.service;

public interface WsService {

    void sendToUser(String router, String message);

    void sendToAll(String router, String message);
}
