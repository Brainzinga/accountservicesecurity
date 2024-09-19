package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.persistence.entity.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RequestMapping("/customers")
//@RestController
public class CustomerController {

    @GetMapping
    public List<Customer> listaCliente() {
        List<Customer> listaClientes = List.of(new Customer(1L, "Pedro", "Pedro@gmail.com"),
                new Customer(1L, "Ricardo", "Ricardo@gmail.com"),
                new Customer(1L, "Saul", "Saul@gmail.com"),
                new Customer(1L, "javier", "javier@gmail.com"));
        return listaClientes;
    }

    @GetMapping("/saludo")
    public String saludoCliente() {
        return "¡Saludos!";
    }

}
