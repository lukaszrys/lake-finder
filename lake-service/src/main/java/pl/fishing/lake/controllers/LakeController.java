package pl.fishing.lake.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LakeController {

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public void getCurrentAccount() {
        System.out.println("CURRENT");
    }
}
