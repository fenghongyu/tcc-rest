package com.tcc.tccrestparticipantapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 2019-09-04
 *
 * @author fenghongyu
 */

public abstract class TccParticipantController<T> {
    public static final String TCC_MEDIA_TYPE = "application/tcc";
    public static final String TRANSACTION_ID = "txId";
    protected static final Logger logger = LoggerFactory.getLogger(TccParticipantController.class);

    @PostMapping(value = "/tcc/{txId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tryOperation(@PathVariable(TRANSACTION_ID) String txId, @RequestBody T body) {
        logger.info("{} begin to try transaction {}", getParticipantName(), txId);
        ResponseEntity result;
        try {
            result = executeTry(txId, body);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = ResponseEntity.notFound().build();
        }
        logger.info("{} finish try transaction {} ,result {}", getParticipantName(), txId, result.getStatusCode());
        return result;
    }

    @DeleteMapping(value = "/tcc/{txId}", consumes = TCC_MEDIA_TYPE, produces = TCC_MEDIA_TYPE)
    public ResponseEntity cancel(@PathVariable(TRANSACTION_ID) String txId) {
        logger.info("{} begin to try cancel {}", getParticipantName(), txId);
        ResponseEntity result;
        try {
            result = executeCancel(txId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = ResponseEntity.notFound().build();
        }
        return result;
    }

    @PutMapping(value = "/tcc/{txId}", consumes = TCC_MEDIA_TYPE, produces = TCC_MEDIA_TYPE)
    public ResponseEntity confirm(@PathVariable(TRANSACTION_ID) String txId) {
        logger.info("{} begin to try confirm {}", getParticipantName(), txId);
        ResponseEntity result;
        try {
            result = executeConfirm(txId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = ResponseEntity.notFound().build();
        }
        return result;
    }

    public abstract Object getParticipantName();

    public abstract ResponseEntity executeTry(String txId, T body);

    public abstract ResponseEntity executeConfirm(String txId);

    public abstract ResponseEntity executeCancel(String txId);


}
