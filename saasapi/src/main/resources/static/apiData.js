var jsondata = [
    {
        "apiname": "登录",
        "apiurl": "/user/login",
        "apitype": "post",
        "info": {
            "username": "test",
            "password": "test"
        }
    },
    {
        "apiname": "判断手机号是否重复",
        "apiurl": "/user/existsMobile",
        "apitype": "post",
        "info": {
            "mobile":"18013955700"
        }
    },
    {
        "apiname": "根据昵称查询用户",
        "apiurl": "/user/findLikeName",
        "apitype": "post",
        "info": {
            "namekeyword":"te"
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
    },
    {
        "apiname": "注册",
        "apiurl": "/user/register", 
        "apitype": "post",
        "info": {
            "username":"aaa",
            "nickname":"aaa",
            "password":"123456",
            "logo":"aa.jpg",
            "mobile":"18013955700"
        }
    }
]