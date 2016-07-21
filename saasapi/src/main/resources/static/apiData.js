var jsondata = [


    {
        "apiname": "注册(http)",
        "apiurl": "/registerNoLogo",
        "apitype": "post",
        "info": {
            "username":"test1",
            "nickname":"nickname",
            "password":"123456",
            "mobile": 18013955701,
            "plateNo":"苏A 199BS2",
            "verificationCode":"noCode"
        }
    }    ,
    
    {
        "apiname": "判断手机号是否重复(http)",
        "apiurl": "/existsMobile",
        "apitype": "post",
        "info": {
            "mobile": "18013955701"
        }
    },
   
    {
        "apiname": "发送短信验证码(http)",
        "apiurl": "/getVerificationCode",
        "apitype": "post",
        "info": {
            "mobile": 18013955701
        }
    },
    {
        "apiname": "gps心跳包",
        "apiurl": "gps",
        "apitype": "ws",
        "info": {
            "lat": 15.22,
            "lon": 88.24
        }
    },
    {
        "apiname": "根据昵称查询用户",
        "apiurl": "findUsersLikeName",
        "apitype": "ws",
        "info": {
            "keyword":"e"
        }
    },
    {
        "apiname": "更新用户信息",
        "apiurl": "modifyUserInfo",
        "apitype": "ws",
        "info": {
            "nickname":"changed",
            "plateNo":"苏A 22222",
            "mobile":"11113343222",
            "logo":"test"

        }
    },
    {
        "apiname": "查询所有用户",
        "apiurl": "listAllUsers",
        "apitype": "ws",
        "info": {
        }
    },
    {
        "apiname": "查询我的好友",
        "apiurl": "listMyFriends",
        "apitype": "ws",
        "info": {
        }
    },
    {
        "apiname": "添加好友",
        "apiurl": "addFriend",
        "apitype": "ws",
        "info": {
            "friendId": 1
        }
    },
    {
        "apiname": "删除好友",
        "apiurl": "deleteFriend",
        "apitype": "ws",
        "info": {
            "friendId": 1
        }
    },
    {
        "apiname": "查询群组标签",
        "apiurl": "listGroupTags",
        "apitype": "ws",
        "info": {
        }
    },
    {
        "apiname": "添加群组",
        "apiurl": "addGroup",
        "apitype": "ws",
        "info": {
            "name":"testGroup",
            "descipt":"for test",
            "tagId":"1"
        }
    },
    {
        "apiname": "我的群组",
        "apiurl": "listMyGroup",
        "apitype": "ws",
        "info": {
        }
    },
    {
        "apiname": "群组信息",
        "apiurl": "groupDetail",
        "apitype": "ws",
        "info": {
            "groupId": 1
        }
    },
    {
        "apiname": "删除群组",
        "apiurl": "deleteGroup",
        "apitype": "ws",
        "info": {
            "groupId": 1
        }
    },
    {
        "apiname": "添加群用户",
        "apiurl": "addUserToGroup",
        "apitype": "ws",
        "info": {
            "groupId": 1,
            "userId": 2
        }
    },

    {
        "apiname": "下载文件",
        "apiurl": "downloadFile",
        "apitype": "ws",
        "info": {
            "filePath": "/tmp/saasapi/1.txt"
        }
    },



    










    {
        "apiname": "重置密码",
        "apiurl": "resetUserPassword",
        "apitype": "ws",
        "info": {
            "mobile":"18013955700"
        }
    }
]