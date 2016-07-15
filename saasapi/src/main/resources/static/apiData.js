var jsondata = [


    {
        "apiname": "注册(http)",
        "apiurl": "/register",
        "apitype": "post",
        "info": {
            "username":"test1",
            "nickname":"nickname",
            "password":"123456",
            "repeatPassword":"123456",
            "logo":"aa.jpg",
            "mobile":"18013955700",
            "plateNo":"苏A 199BS2",
            "verificationCode":"code"
        }
    }    ,
    
    {
        "apiname": "判断手机号是否重复(http)",
        "apiurl": "/existsMobile",
        "apitype": "post",
        "info": {
            "mobile": 18013955701
        }
    },
   
    {
        "apiname": "发送短信验证码(http)",
        "apiurl": "/getVerificationCode",
        "apitype": "post",
        "info": {
            "mobile": 18013955700
        }
    },
    {
        "apiname": "重置密码",
        "apiurl": "/resetPassword",
        "apitype": "post",
        "info": {
            "mobile":"18013955700"
        }
        
    },
    {
        "apiname": "更新用户信息",
        "apiurl": "modifyInfo",
        "apitype": "ws",
        "info": {
        	"nickname":"test1",
            "plateNo":"111",
            "mobile":"1111",
            "logo":"test"
            
        }
    },
    
    {
        "apiname": "根据昵称查询用户",
        "apiurl": "findLikeName",
        "apitype": "ws",
        "info": {
            "keyword":"te"
        }
    },
    {
        "apiname": "查询所有用户",
        "apiurl": "/user/listAll",
        "apitype": "post",
        
    },
    {
        "apiname": "查询当前好友",
        "apiurl": "/friends/listMine",
        "apitype": "get"
        
    },
    {
        "apiname": "添加好友",
        "apiurl": "/friends/add",
        "apitype": "post",
        "info": {
            "friendId":"1"
        }
    },
    {
        "apiname": "删除好友",
        "apiurl": "/friends/delete",
        "apitype": "post",
        "info": {
            "friendId":"1"
        }
    },
    {
        "apiname": "我的群组",
        "apiurl": "/group/listMine",
        "apitype": "get"
    },
    {
        "apiname": "添加群组",
        "apiurl": "/group/add",
        "apitype": "post",
        "info": {
                 "name":"aaa",
                 "descipt":"aa",
                 "tagId":"1"
             }
    },
    {
        "apiname": "删除群组",
        "apiurl": "/group/delete",
        "apitype": "post",
        "info": {
            "groupId":"1"
            
        }
    },
    {
        "apiname": "添加群用户",
        "apiurl": "/group/addUserToGroup",
        "apitype": "post",
        "info": {
            "groupId":"1",
            "userIds":"1,2,3"
            
        }
    }
]