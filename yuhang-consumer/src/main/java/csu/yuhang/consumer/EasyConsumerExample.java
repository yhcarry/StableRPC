package csu.yuhang.consumer;


import csu.yuhang.common.model.User;
import csu.yuhang.common.service.UserService;
import csu.yuhang.rpc.config.RpcConfig;
import csu.yuhang.rpc.proxy.ServiceProxyFactory;
import csu.yuhang.rpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yuhang");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
