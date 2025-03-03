package csu.yuhang.provider;

import csu.yuhang.common.service.UserService;
import csu.yuhang.rpc.RpcApplication;
import csu.yuhang.rpc.config.RegistryConfig;
import csu.yuhang.rpc.config.RpcConfig;
import csu.yuhang.rpc.model.ServiceMetaInfo;
import csu.yuhang.rpc.registry.LocalRegistry;
import csu.yuhang.rpc.registry.Registry;
import csu.yuhang.rpc.registry.RegistryFactory;
import csu.yuhang.rpc.server.HttpServer;
import csu.yuhang.rpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
