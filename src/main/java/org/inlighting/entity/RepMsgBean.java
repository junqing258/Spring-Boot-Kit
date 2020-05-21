package org.inlighting.entity;

public class RepMsgBean {

    private String rspCode;

    // 返回信息
    private String message;

    // 返回的数据
    private Object data;

    private String router;

    private long rspTime;

    public RepMsgBean(String rspCode, String message, Object data) {
        this.router = router;
        this.rspCode = rspCode;
        this.message = message;
        this.data = data;
        this.rspTime = System.currentTimeMillis();
    }


}
