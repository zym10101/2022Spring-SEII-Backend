package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.vo.customer.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/findByType")
    public Response findByType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getCustomersByType(type));
    }

    @PostMapping("/register")
    public Response customerRegister(@RequestBody CustomerVO customerVO) {
        customerService.registerCustomer(customerVO);
        return Response.buildSuccess();
    }

    @GetMapping("/drop")
    public Response customerDrop(@RequestParam Integer id) {
        customerService.dropCustomer(id);
        return Response.buildSuccess();
    }
}
