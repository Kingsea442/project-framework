主要总结个人项目中常用开发组件及一些好用的技巧
## api-mvc
封装一下组件：

### ApiResp 统一接口返回数据结构
```json
{
    "status":{
        "code":0,
        "name":"OK",
        "msg":"succeed"
    },
    "data":{
        "user":{
        },
        "token":""
    }
}
```
status结构存储接口状态相关信息
data结构存储接口返回的数据
