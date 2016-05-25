package com.gensoft.core.pojo;

/**
 * Created by alan on 16-5-25.
 */
public class UserInfo {
    private Long id;
    private Long deviceId;

    public UserInfo(Long id, Long deviceId) {
        this.id = id;
        this.deviceId = deviceId;
    }

    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
