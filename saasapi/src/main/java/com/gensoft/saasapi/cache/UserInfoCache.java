package com.gensoft.saasapi.cache;

import com.gensoft.core.pojo.UserInfo;
import io.netty.channel.Channel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan on 16-7-12.
 */
@Repository
public class UserInfoCache {

    private Map<Long, UserInfo> userInfoMap = new HashMap<>();

    private Map<Long, Channel> useridChannelMap = new HashMap<>();

    private Map<Channel, Long> channelUseridMap = new HashMap<>();

    public void removeChannel(Channel channel){
        Long userId = channelUseridMap.get(channel);
        if(userId != null){
            userInfoMap.remove(userId);
            useridChannelMap.remove(userId);
        }
    }

    public void addChannel(Channel channel, UserInfo userInfo){
        Long userId = userInfo.getId();
        channelUseridMap.put(channel, userId);
        userInfoMap.put(userId, userInfo);
        useridChannelMap.put(userId, channel);
    }

    public UserInfo getUserByChannel(Channel channel){
        return userInfoMap.get(channelUseridMap.get(channel));
    }

    public Channel getChannelByUserId(Long userId){
        return useridChannelMap.get(userId);
    }

    public UserInfo getUserInfoById(Long userId){
        return userInfoMap.get(userId);
    }
}
