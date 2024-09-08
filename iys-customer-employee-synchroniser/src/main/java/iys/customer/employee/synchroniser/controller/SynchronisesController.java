package iys.customer.employee.synchroniser.controller;

import iys.customer.employee.synchroniser.model.CustomerSpace;
import iys.customer.employee.synchroniser.service.SynchronisesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SynchronisesController {

    private SynchronisesService service;

    @PostMapping(value = "/synchronises/create-customer-space")
    public ResponseEntity<Void> createNewCustomerSpace(@RequestBody CustomerSpace customerSpace){
        return ResponseEntity.accepted().build();
    }


}
