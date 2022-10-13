package com.io.RippleTrustline.Utility;

import com.io.RippleTrustline.Exception.RippleCustomException;
import io.tej.SwaggerCodgen.model.ReceivingUnitsRequest;
import io.tej.SwaggerCodgen.model.ReceivingUnitsResponse;
import io.tej.SwaggerCodgen.model.SendUnitResponse;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

@Component
@Getter
@Setter
public class UtilityClass {

    Logger logger = LoggerFactory.getLogger(UtilityClass.class);

    HashMap<String, String> balanceRecords;
    HashMap<String, State> trasnactionIdStateMap;
    HashMap<String, String> userNamePortMap;
    HashMap<String, Integer> trasnactionRetryCountmap;



    @Autowired
    public UtilityClass() {
        this.balanceRecords = new HashMap<>();
        this.trasnactionIdStateMap = new HashMap<>();
        this.userNamePortMap =new HashMap<>();
        this.trasnactionRetryCountmap = new HashMap<>();
    }

    /*
    * ------------_RULES FOR AMOUNT-----------
    * Minimum value = 0
    * No negative integers allowed
    * Can contain '.' decimal
    * Specified as a string
    * May contain E or e to indicate being raised to a power of 10
    * It is a base of 10
     * */
    public boolean isAmountValid(String amount) {
        if(amount==null || amount.isEmpty() || amount.charAt(0)=='-' || amount.charAt(0)==' ' || amount.length()>19){
            if(amount.contains("[a-zA-Z]+")){
                return amount.contains("E") || amount.contains("e");
            }
        }
        return true;
    }
    /*
    * ------------_RULES FOR USER-----------
    * User is String
    * atleast has one character
    * No empty stirngs allowed
    * No more than 10 characters
    * */
    public boolean isUserValid(String user, String currentActiveuser) {
        if(user==null || user.isEmpty() || user.charAt(0)==' ' || user.length()>10)
            return false;
        return !currentActiveuser.equals(user);
    }

    public ReceivingUnitsResponse postUnits(String currentuser, String receivingUrl, String amount, String receivingUser, RestTemplate restTemplate, String transactionId) {

        if(trasnactionIdStateMap.get(transactionId).equals(State.DEAD)){
            throw new RippleCustomException("Dead request, cannot be completed");
       }

        logger.info("sending the units of the address: "+receivingUrl+ " units of: "+ amount+" to the user: "+ receivingUser);
        System.out.println("-----------------transactionId--------------     "+ transactionId);
        trasnactionRetryCountmap.put(transactionId, 0);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ReceivingUnitsRequest receivingUnitsRequest = new ReceivingUnitsRequest();
        receivingUnitsRequest.setTransactionId(transactionId);
        receivingUnitsRequest.setAmount(amount);
        receivingUnitsRequest.setSederName(currentuser);
        ReceivingUnitsResponse response = restTemplate.postForObject(receivingUrl, receivingUnitsRequest, ReceivingUnitsResponse.class);
        if(response!=null) {
            logger.info("units sent: " + response.getUnitsReceived());
            trasnactionIdStateMap.put(transactionId, State.COMPLETE);
            if( balanceRecords.containsKey(currentuser))
                balanceRecords.put(currentuser, String.valueOf(Integer.parseInt(balanceRecords.get(currentuser))-Integer.parseInt(amount)));
            else
                balanceRecords.put(currentuser, amount);
            return response;
        }
        //CONSIDER QUEUE
        //CONSIDER Putting the threshold in properties file
        else{
            if(trasnactionRetryCountmap.get(transactionId)<5){
                trasnactionIdStateMap.put(transactionId, State.RETRY);
                trasnactionRetryCountmap.put(transactionId, trasnactionRetryCountmap.get(transactionId)+1);
                postUnits(currentuser, receivingUrl, amount, receivingUser, restTemplate, transactionId);
            }else{
                trasnactionIdStateMap.put(transactionId, State.DEAD);
            }
        }
        return response;
    }

    public SendUnitResponse mapSendUnitResponse(ReceivingUnitsResponse response, String receivingUser) {
        SendUnitResponse sendUnitResponse = new SendUnitResponse();
        sendUnitResponse.setUserName(receivingUser);
        sendUnitResponse.setUnitsSent(response.getUnitsReceived());
        return sendUnitResponse;
    }

    public void fillPortMap() {
        this.userNamePortMap.put("userA", "8080");
        this.userNamePortMap.put("userB", "8090");
    }

    public void printMap(HashMap<String, String> map) {
        for (String key: map.keySet()){
            logger.info(key+ " = " + map.get(key));
        }
    }

    public boolean updateReceived(String amount, String currentuser) {
        if(balanceRecords==null || !balanceRecords.containsKey(currentuser)){
            assert balanceRecords != null;
            balanceRecords.put(currentuser, String.valueOf(0));
        }
        balanceRecords.put(currentuser, String.valueOf(Integer.parseInt(balanceRecords.get(currentuser))+Integer.parseInt(amount)));
        return true;
    }

    public String generateTxnId(){
        return UUID.randomUUID().toString();
    }

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
