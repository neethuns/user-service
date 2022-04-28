package com.maveric.demo.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class FeignClientRetryer implements Retryer {

    private int retryMaxAttempt;

    private long retryInterval;

    private int attempt = 1;


    public FeignClientRetryer(int retryMaxAttempt, Long retryInterval) {
        this.retryMaxAttempt = retryMaxAttempt;
        this.retryInterval = retryInterval;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());

        if(attempt++ == retryMaxAttempt){
            throw e;
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public Retryer clone() {
        return new FeignClientRetryer(6, 60000L);
    }
}

