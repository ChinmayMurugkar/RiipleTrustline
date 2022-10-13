package com.io.RippleTrustline.Controller;

import com.io.RippleTrustline.Exception.RippleCustomException;
import io.tej.SwaggerCodgen.api.UserApi;
import io.tej.SwaggerCodgen.model.*;
import com.io.RippleTrustline.Exception.ResourceNotFoundException;
import com.io.RippleTrustline.Exception.UnauthorizedException;
import com.io.RippleTrustline.Utility.State;
import com.io.RippleTrustline.Utility.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UtilityClass utility;


    @Value("${spring.profiles.active}")
    private String currentuser;

    @Value("${server.port}")
    private int currentServerPort;

    @Override
    public ResponseEntity<GetBalanceResponse> getBalance(String userName){
        if(utility.getBalanceRecords()==null || !utility.getBalanceRecords().containsKey(userName))
            throw new RippleCustomException("Records not present");
        String balance = utility.getBalanceRecords().get(userName);
        if(balance.equals(""))
            balance = String.valueOf(0);
        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setBalance(balance);
        getBalanceResponse.setUserName(userName);
        return ResponseEntity.ok(getBalanceResponse);
    }

    @Override
    public ResponseEntity<ReceivingUnitsResponse> receivingUnits(ReceivingUnitsRequest receivingUnitsRequest){
        logger.info("amount received is :" +receivingUnitsRequest.getAmount());
        String transactionId = receivingUnitsRequest.getTransactionId();
        if(!utility.getTrasnactionIdStateMap().containsKey(transactionId)){
            utility.getTrasnactionIdStateMap().put(transactionId, State.EXECUTING);
        }

        if(utility.getTrasnactionIdStateMap().get(transactionId).equals(State.COMPLETE)){
            throw new RippleCustomException("Duplicate request, cannot be completed");
        }
        if(utility.getTrasnactionIdStateMap().get(transactionId).equals(State.DEAD)){
            throw new RippleCustomException("Dead request, cannot be completed");
        }

        if(utility.updateReceived(receivingUnitsRequest.getAmount(), currentuser)){
                utility.getTrasnactionIdStateMap().put(transactionId, State.COMPLETE);
        }else{
            if(utility.getTrasnactionRetryCountmap().get(transactionId)<5){
                utility.getTrasnactionIdStateMap().put(transactionId, State.RETRY);
                utility.getTrasnactionRetryCountmap().put(transactionId, utility.getTrasnactionRetryCountmap().get(transactionId)+1);
                receivingUnits(receivingUnitsRequest);
            }else{
                utility.getTrasnactionIdStateMap().put(transactionId, State.DEAD);
            }
        }

        ReceivingUnitsResponse response = new ReceivingUnitsResponse();
        response.setUnitsReceived(receivingUnitsRequest.getAmount());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SendUnitResponse> sendUnits(SendUnitRequest sendUnitRequest){

        String amount = sendUnitRequest.getAmount();
        String receivingUser = sendUnitRequest.getUserName();
        logger.info("receiving User is:"+receivingUser);
        logger.info("current User is:"+ currentuser);
        utility.fillPortMap();
        utility.printMap(utility.getUserNamePortMap());

        if(!utility.isAmountValid(amount)){
            throw new RippleCustomException("Amount is not valid");
        }

        if(!utility.isUserValid(receivingUser, currentuser)){
            throw new RippleCustomException("User is not valid");
        }

        if(!utility.getUserNamePortMap().containsKey(receivingUser)){
            throw new ResourceNotFoundException("Specified User is unknown");
        }

        String receivingPort = utility.getUserNamePortMap().get(receivingUser);

        if(receivingPort.equals( String.valueOf(currentServerPort))){
            throw new UnauthorizedException("user specified is accessing from same service instance which is not allowed");
        }

        String receivingUrl = "http://127.0.0.1:"+receivingPort+"/user/receivingUnits";
        String transactionId = utility.generateTxnId();
        utility.getTrasnactionIdStateMap().put(transactionId, State.EXECUTING);
        ReceivingUnitsResponse response =utility.postUnits(currentuser, receivingUrl, amount, receivingUser, utility.getRestTemplate(), transactionId);

        if(response!=null){
            SendUnitResponse sendUnitResponse = utility.mapSendUnitResponse(response, receivingUser);
            ResponseEntity.ok(sendUnitResponse);
            return ResponseEntity.ok(sendUnitResponse);
        }else{
            throw new RippleCustomException("Something went wrong with the request, please try again");
        }

    }
}
