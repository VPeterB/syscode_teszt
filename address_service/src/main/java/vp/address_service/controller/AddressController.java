package vp.address_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vp.address_service.model.Address;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private static final Logger logger = Logger.getLogger(AddressController.class.getName());

    @GetMapping
    public Address getAddress() {
        logger.info("get address called");
        return new Address("teszt address");
    }
}
