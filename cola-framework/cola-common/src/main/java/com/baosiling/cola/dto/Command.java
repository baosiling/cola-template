package com.baosiling.cola.dto;

import com.baosiling.cola.extension.BizScenario;

/**
 * Command stands for a request from Client.
 * According CommandExecutor will be help to handle the business logic. This is a classic Command Pattern
 */
public abstract class Command extends DTO {

    private BizScenario bizScenario;

    public BizScenario getBizScenario() {
        return bizScenario;
    }

    public void setBizScenario(BizScenario bizScenario) {
        this.bizScenario = bizScenario;
    }

}
