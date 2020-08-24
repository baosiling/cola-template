package com.baosiling.cola.extension;

/**
 * BizScenario(业务场景) = bizId + useCase + scenario, which can uniquely identify a user scenario.
 *
 */
public class BizScenario {

    public final static String DEFAULT_BIZ_ID = "defaultBizId";
    public final static String DEFAULT_USE_CASE = "defaultUseCase";
    public final static String DEFAULT_SCENARIO = "defaultScenario";
    private final static String DOT_SEPARATOR = ",";

    /**
     * bizId is used to identify(确定) a business, such as "tmall", it's nullable if there is only one biz.
     */
    private String bizId = DEFAULT_BIZ_ID;

    /**
     * useCase is used to identify a use case, such as "placeOrder"(下单), can not be null.
     */
    private String useCase = DEFAULT_USE_CASE;

    /**
     * scenario is used to identify a use case, such as "88vip","normal", can not be null
     */
    private String scenario = DEFAULT_SCENARIO;

    /**
     * For above case, the BizScenario will be "tmall.placeOrder.88vip",
     * with this code, we can provide extension processing other than "tmall.placeOrder.normal"
     * @return
     */
    public String getUniqueIdentity(){
        return bizId +DOT_SEPARATOR + useCase + DOT_SEPARATOR + scenario;
    }

    public static BizScenario valueOf(String bizId, String useCase, String scenario){
        BizScenario bizScenario = new BizScenario();
        bizScenario.bizId = bizId;
        bizScenario.useCase = useCase;
        bizScenario.scenario = scenario;
        return bizScenario;
    }

    public static BizScenario valueOf(String bizId){
        return valueOf(bizId, DEFAULT_USE_CASE, DEFAULT_SCENARIO);
    }

    public static BizScenario valueOf(String useCase, String scenario){
        return valueOf(DEFAULT_BIZ_ID, useCase, scenario);
    }

    public static BizScenario newDefault(){
        return valueOf(DEFAULT_BIZ_ID, DEFAULT_USE_CASE, DEFAULT_SCENARIO);
    }

}
