package com.tcc.tcccoordinatorcustomer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.atomikos.tcc.rest.ParticipantLink;
import com.atomikos.tcc.rest.Transaction;
import com.tcc.tcccoordinatorcustomer.dto.FrozeRequest;
import com.tcc.tcccoordinatorcustomer.dto.UserOrder;

@Component
public class TccOrderService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TccOrderService.class);
    @Value("${tcc.participant.orderService}")
    String orderServiceUrlTemplate;
    @Value("${tcc.participant.inventoryService}")
    String inventoryServiceUrlTemplate;
    @Autowired
    TccCoordinatorClient tccCoordinatorClient;
    @Value("${tcc.transaction.timeoutInMs}")
    private long transactionTtimeoutInMs;
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        String orderServiceUrlTemplate="http://localhost:8080/order/tcc/%s";
        String txId = UUID.randomUUID().toString();
        System.out.println( String.format(orderServiceUrlTemplate, txId));
    }

    public void newOrderWithTcc(UserOrder orderRequest, String txId) {
        long expireTime = System.currentTimeMillis() + transactionTtimeoutInMs;

        List<ParticipantLink> participantLinks = new ArrayList<>(2);
        String orderServiceUrl = String.format(orderServiceUrlTemplate, txId);
        participantLinks.add(new ParticipantLink(orderServiceUrl, expireTime));

        String inventoryServiceUrl = String.format(inventoryServiceUrlTemplate, txId);
        participantLinks.add(new ParticipantLink(inventoryServiceUrl, expireTime));

        Transaction transaction = new Transaction(participantLinks);
        try {

            //1. try participant order-service
            restTemplate.postForEntity(orderServiceUrl, orderRequest, String.class);

            //2. try participant inventory-service
            FrozeRequest frozeRequest = FrozeRequest.builder()
                    .productCode(orderRequest.getProductCode())
                    .frozenNum(orderRequest.getQuantity())
                    .version(1L)
                    .build();
            restTemplate.postForEntity(inventoryServiceUrl, frozeRequest, String.class);

            //3. call coordinator to confirm
            tccCoordinatorClient.confirm(transaction);
        } catch (Exception e) {
            //4. call coordinator to cancel
            tccCoordinatorClient.cancel(transaction);
            LOGGER.error(e.getMessage(),e);
            String msg = e instanceof HttpStatusCodeException ? ((HttpStatusCodeException) e).getResponseBodyAsString() : e.getMessage();
            throw new RuntimeException(msg, e);
        }
    }
}
