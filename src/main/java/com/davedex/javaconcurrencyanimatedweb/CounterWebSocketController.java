package com.davedex.javaconcurrencyanimatedweb;

import com.davedex.javaconcurrencyanimatedweb.simulations.AtomicIntegerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import java.util.List;

@Controller
public class CounterWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private AtomicIntegerService atomicIntegerService;


    @MessageMapping("/incrementCounters")
    @SendTo("/topic/atomicIntegers")
    public List<Integer> incrementCounter() {
        return atomicIntegerService.incrementAll();
    }

    @MessageMapping("/resetCounters")
    @SendTo("/topic/atomicIntegers")
    public List<Integer> resetAtomicIntegers() {
        return atomicIntegerService.resetIntegers();
    }

}
