package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.saasapi.pojo.usergroup.UserGroupEntity;
import com.gensoft.saasapi.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alan on 16-5-24.
 */
@RequestMapping("/group")
@RestController
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping(value = "/listMine", method = RequestMethod.GET)
    ApiResult listMine(@Login UserInfo userInfo) {
        //userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/listTags", method = RequestMethod.GET)
    ApiResult listTags() {
        //userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ApiResult add(@Login UserInfo userInfo, @RequestBody UserGroupEntity req) {
        //userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ApiResult delete(@Login UserInfo userInfo, @RequestParam String groupId) {
        //userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    ApiResult<UserGroupEntity> detail(@Login UserInfo userInfo, @RequestParam String groupId) {
        //userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    ApiResult modifyInfo(@RequestBody UserGroupEntity req) {
        //TODO
        return ApiResult.successInstance();
    }
}
