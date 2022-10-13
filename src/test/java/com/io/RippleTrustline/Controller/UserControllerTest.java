package com.io.RippleTrustline.Controller;

import com.io.RippleTrustline.Exception.RippleCustomException;
import com.io.RippleTrustline.Utility.UtilityClass;
import io.tej.SwaggerCodgen.model.ReceivingUnitsResponse;
import io.tej.SwaggerCodgen.model.SendUnitRequest;
import io.tej.SwaggerCodgen.model.SendUnitResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UtilityClass utility;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(utility.isUserValid(anyString(), anyString())).thenReturn(true);
    }

    @Test
    void sendUnitsSuccess(){
        SendUnitRequest sendUnitRequest = new SendUnitRequest();
        sendUnitRequest.setUserName("John");
        sendUnitRequest.setAmount("10");
        when(utility.isAmountValid(anyString())).thenReturn(true);
        when(utility.isUserValid(anyString(), anyString())).thenReturn(true);
        RestTemplate rtMock = mock(RestTemplate.class);
        HashMap<String, String> stubbedMap = new HashMap<>();
        stubbedMap.put("userA", "8080");
        stubbedMap.put("userB", "8080");
        stubbedMap.put("John", "8080");
        when(utility.getUserNamePortMap()).thenReturn(stubbedMap);
        ReceivingUnitsResponse response = new ReceivingUnitsResponse();
        response.setUnitsReceived("10");
        when(utility.getRestTemplate()).thenReturn(rtMock);
        when(utility.postUnits("userA", "http://127.0.0.1:8080/user/receivingUnits", "10", "John", rtMock,null))
                .thenReturn(response);

        ReflectionTestUtils.setField(userController, "currentuser", "userA");

        SendUnitResponse sendUnitResponse = new SendUnitResponse();
        sendUnitResponse.setUnitsSent("10");
        sendUnitResponse.setUserName("John");

        lenient().when(utility.mapSendUnitResponse(any(ReceivingUnitsResponse.class), anyString())).thenReturn(sendUnitResponse);
        ResponseEntity<SendUnitResponse> actual = userController.sendUnits(sendUnitRequest);
        assertEquals(ResponseEntity.ok(sendUnitResponse), actual);

    }


    @Test
    void sendUnitsSuccessNotAValidUser(){
        SendUnitRequest sendUnitRequest = new SendUnitRequest();
        sendUnitRequest.setUserName("John");
        sendUnitRequest.setAmount("10");
        when(utility.isAmountValid(anyString())).thenReturn(true);
        when(utility.isUserValid(anyString(), anyString())).thenThrow(new RippleCustomException("Not a valid user"));
        RestTemplate rtMock = mock(RestTemplate.class);
        HashMap<String, String> stubbedMap = new HashMap<>();
        stubbedMap.put("userA", "8080");
        stubbedMap.put("userB", "8080");
        stubbedMap.put("John", "8080");
        when(utility.getUserNamePortMap()).thenReturn(stubbedMap);
        ReceivingUnitsResponse response = new ReceivingUnitsResponse();
        response.setUnitsReceived("10");

        ReflectionTestUtils.setField(userController, "currentuser", "userA");

        SendUnitResponse sendUnitResponse = new SendUnitResponse();
        sendUnitResponse.setUnitsSent("10");
        sendUnitResponse.setUserName("John");

        lenient().when(utility.mapSendUnitResponse(any(ReceivingUnitsResponse.class), anyString())).thenReturn(sendUnitResponse);
        assertThrows(RippleCustomException.class,
                () ->userController.sendUnits(sendUnitRequest));
    }

    @Test
    void sendUnitsSuccessNotAValidAmount(){
        SendUnitRequest sendUnitRequest = new SendUnitRequest();
        sendUnitRequest.setUserName("John");
        sendUnitRequest.setAmount("-10");
        when(utility.isAmountValid(anyString())).thenThrow(new RippleCustomException("Not a valid amount"));

        HashMap<String, String> stubbedMap = new HashMap<>();
        stubbedMap.put("userA", "8080");
        stubbedMap.put("userB", "8080");
        stubbedMap.put("John", "8080");
        when(utility.getUserNamePortMap()).thenReturn(stubbedMap);
        ReceivingUnitsResponse response = new ReceivingUnitsResponse();
        response.setUnitsReceived("10");

        ReflectionTestUtils.setField(userController, "currentuser", "userA");

        SendUnitResponse sendUnitResponse = new SendUnitResponse();
        sendUnitResponse.setUnitsSent("10");
        sendUnitResponse.setUserName("John");

        lenient().when(utility.mapSendUnitResponse(any(ReceivingUnitsResponse.class), anyString())).thenReturn(sendUnitResponse);
        assertThrows(RippleCustomException.class,
                () ->userController.sendUnits(sendUnitRequest));
    }

    @Test
    void sendUnitNoPortMap(){
        //TODO
    }

    @Test
    void sendUnitUserNotValid(){
        //TODO
    }

    @Test
    void sendUnitUnautharizedUser(){
        //TODO
    }

    @Test
    void sendUnitreceivingUrlWrong(){
        //TODO
    }

    @Test
    void sendUnitNotransactionId(){
        //TODO
    }

    @Test
    void sendUnitRetryState(){
        //TODO
    }

    @Test
    void sendUnitDeadState(){
        //TODO
    }

    @Test
    void sendUnitWrongState(){
        //TODO
    }

    @Test
    void sendUnitCyclicalState(){
        //TODO
    }

    @Test
    void sendUnitResponseNull(){
        //TODO
    }

    @Test
    void receivingUnitsResponseNull(){
        //TODO
    }

    @Test
    void receivingUnitsHappyPath(){
        //TODO
    }

    @Test
    void receivingUnitsExecutingState(){
        //TODO
    }

    @Test
    void receivingUnitsDeadState(){
        //TODO
    }

    @Test
    void receivingUnitsCompleteState(){
        //TODO
    }

}
