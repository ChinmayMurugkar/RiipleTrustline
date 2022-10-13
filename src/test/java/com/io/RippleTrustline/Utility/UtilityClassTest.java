package com.io.RippleTrustline.Utility;

import com.io.RippleTrustline.Controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UtilityClassTest {
    @InjectMocks
    UtilityClass utilityClass;


    @Mock
    UserController userController;

    @Test
    void postUnitHappyPath() {
        //TODO
    }

    @Test
    void postUnitRetryState() {
        //TODO
    }

    @Test
    void postUnitRetryMoreThan5Times() {
        //TODO
    }

    @Test
    void postUnitRetryLessThan5Times() {
        //TODO
    }

    @Test
    void postUnitRetry5Times() {
        //TODO
    }

    @Test
    void mapSendUnitResponseHappyPath() {
        //TODO
    }
}
