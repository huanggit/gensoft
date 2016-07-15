package com.gensoft.saasapi.cache;

import com.gensoft.core.pojo.UserInfo;
import com.gensoft.dao.user.User;
import com.gensoft.saasapi.service.UserService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alan on 16-7-12.
 */
@Repository
public class UserInfoCache implements InitializingBean {

    @Autowired
    UserService userService;

    private Map<Long, UserInfo> userInfoMap = new HashMap<>();

    private Map<Long, Channel> useridChannelMap = new HashMap<>();

    private Map<Channel, Long> channelUseridMap = new HashMap<>();

    public void removeChannel(Channel channel){
        Long userId = channelUseridMap.get(channel);
        channelUseridMap.remove(channel);
        if(userId != null){
            useridChannelMap.remove(userId);
        }
    }

    public void addChannel(Channel channel, UserInfo userInfo){
        Long userId = userInfo.getId();
        channelUseridMap.put(channel, userId);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshUserMap();
    }

    public void refreshUserMap(){
        List<User> allUsers = userService.getUserfindAll();
        Map<Long, UserInfo> tempUserMap = new HashMap<>();
        for(User user: allUsers){
            tempUserMap.put(user.getId(), new UserInfo(user));
        }
        userInfoMap = tempUserMap;
    }


}
