OKCoin OKEX V3 Open Api使用说明
--------------
### 1.使用技术：okhttp3 + retrofit2
### 2.依赖maven，内部使用 (用户可上传nexus使用)
```
        <dependency>
            <groupId>com.okcoin.commons</groupId>
            <artifactId>okex-java-sdk-api</artifactId>
            <version>1.0.0</version>
        </dependency>
```
### 3.简单使用方式:
```
 public static void main(String[] args) {

        APIConfiguration config = new APIConfiguration();
        config.setEndpoint("http://192.168.80.14:8118");
        config.setApiKey("75c60758-be16-4acc-8f2d-66403e53072c");
        config.setSecretKey("8DF095FE9C662F787A60F3133A06414C");
        config.setPassphrase("19205A%9980");
        config.setPrint(false);

        GeneralAPIService marketAPIService = new GeneralAPIServiceImpl(config);
        ServerTime time = marketAPIService.getServerTime();
        // eg: {"epoch":"1520848286.633","iso":"2018-03-12T09:51:26.633Z"}
        System.out.println(JSON.toJSONString(time));

        FuturesTradeAPIService tradeAPIService = new FuturesTradeAPIServiceImpl(config);

        Order order = new Order();
        order.setClient_oid("TYPb040b3daa40f793e69f86344a1839");
        order.setType(FuturesTransactionTypeEnum.OPEN_SHORT.code());
        order.setProduct_id("BTC-USD-0331");
        order.setPrice(99900.00);
        order.setAmount(123);
        order.setMatch_price(0);
        order.setLever_rate(10.00);
        OrderResult orderResult = tradeAPIService.newOrder(order);
        // eg : {"client_oid":"TYPb040b3daa40f793e69f86344a1839","order_id":"400574928061440","result":true}
        System.out.println(JSON.toJSONString(orderResult));
 }
```
### 4.Spring 或 Spring Boot使用方式:
```
@RestController
public class TestOKEXOpenApiV3 {

    @Autowired
    private GeneralAPIService generalAPIService;

    @GetMapping("/server-time")
    public ServerTime getServerTime() {
        return generalAPIService.getServerTime();
    }
    
    @Bean
    public APIConfiguration okexApiConfig() {
        APIConfiguration config = new APIConfiguration();
        config.setEndpoint("http://192.168.80.14:8118");
        config.setApiKey("75c60758-be16-4acc-8f2d-66403e53072c");
        config.setSecretKey("8DF095FE9C662F787A60F3133A06414C");
        config.setPassphrase("19205A%9980");
        config.setPrint(false);
        return config;
    }

    @Bean
    public GeneralAPIService generalAPIService(APIConfiguration config) {
        return new GeneralAPIServiceImpl(config);
    }
}
```
#### 4.1 可使用web测试: http://192.168.150.197:8080/server-time
```
{
"iso": "2018-03-12T10:12:01.468Z",
"epoch": "1520849521.468"
}
```
