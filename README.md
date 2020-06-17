主要总结个人项目中常用开发组件及一些好用的技巧
## api
封装了接口层的相关组件：

### ApiResp 统一接口返回数据结构
ApiResp是定义的接口返回的数据结构，ResponseBodyRewriter负责重写返回的数据
```json
{
    "status":{
        "code":0,
        "name":"OK",
        "msg":"succeed"
    },
    "data":object
}
```
status结构存储接口状态相关信息
data结构存储接口返回的数据
