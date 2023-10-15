package com.trevorism.controller

import com.trevorism.gcloud.webapi.controller.RootController
import org.junit.jupiter.api.Test

/**
 * @author tbrooks
 */
class RootControllerTest {

    @Test
    void testRootControllerEndpoints(){
        RootController rootController = new RootController()
        assert rootController.index().getBody().get().contains("/help")
    }

    @Test
    void testRootControllerPing(){
        RootController rootController = new RootController()
        assert rootController.ping() == "pong"
    }
}
