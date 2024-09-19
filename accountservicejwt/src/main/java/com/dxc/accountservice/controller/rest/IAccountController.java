package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@Tag(name = "API de cuentas", description = "Esta es una api para crear, actualizar y consumir cuentas")
public interface IAccountController {

    @Operation(summary = "Obtiene la lista de cuentas de un cliente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo bien"),
            @ApiResponse(responseCode = "400", description = "Peticion de datos erronea"),
            @ApiResponse(responseCode = "404", description = "El producto no existe"),
            @ApiResponse(responseCode = "415", description = "Se debe indicar en header el campo content-type y valor application/json")
    })
    @GetMapping(path = "/customer/{customerId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<AccountDtoResponse>> getAccountByCustomer(
            @Parameter(name = "ID Cliente",description = "Identificador del cliente",allowEmptyValue = false,required = true,example="1")
            @PathVariable Long customerId
    );


    @Operation(summary = "Obtiene la cuenta por Id de la cuenta y el  Id cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo OK"),
            @ApiResponse(responseCode = "400", description = "Peticion de datos erronea"),
            @ApiResponse(responseCode = "404", description = "El producto no existe"),
            @ApiResponse(responseCode = "405", description = "Metodo no soportado para este endpoint"),
            @ApiResponse(responseCode = "415", description = "Se debe indicar en header el campo content-type y valor application/json")
    })
    @GetMapping(path = "/{accountId}/{customerId}" ,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE} )
    ResponseEntity<AccountDtoResponse> obtenerCuentaPorId(@Positive @PathVariable Long accountId, @Positive @PathVariable Long customerId);

    @Operation(summary = "Crea una nueva cuenta para un cliente")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada"),
            @ApiResponse(responseCode = "400", description = "Peticion de datos erronea"),
            @ApiResponse(responseCode = "405", description = "Metodo no soportado para este endpoint")
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<AccountDtoResponse> crearCuenta(@Valid @RequestBody  AccountDtoRequest AccountDtoRequest);

    @Operation(summary = "Actualiza una cuenta por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada"),
            @ApiResponse(responseCode = "400", description = "Peticion de datos erronea"),
            @ApiResponse(responseCode = "405", description = "Metodo no soportado para este endpoint")
    })
    @PutMapping("/{accountId}")
    ResponseEntity<AccountDtoResponse> actualizarCuenta(@PathVariable Long accountId,
                                                               @Valid @RequestBody AccountDtoRequest account);

    @Operation(summary = "Elimina todas las  cuentas de un cliente por Id de un cliente"  )
    @DeleteMapping("/{customerId}")
    ResponseEntity<String> eliminarCuentas(@PathVariable Long customerId);

    @Operation(summary = "Agrega dinero a la cuenta de un cliente" )
    @PostMapping("/add-money")
    ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody AddAmountBalanceDto addAmountBalanceDto);

    @Operation(summary = "Retirar dinero de la cuenta de un cliente. allAccounts en true eso para retirar el monto de todas las cuentas"  )
    @PostMapping("/rest-money")
    ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody RestMoneyBalanceDto restMoneyBalanceDto,
                                                     @RequestParam(required = false) boolean allAccounts);

    @Operation(summary = "Comprobar si un cliente tiene un préstamo" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo OK"),
            @ApiResponse(responseCode = "400", description = "Peticion de datos erronea"),
            @ApiResponse(responseCode = "404", description = "El producto no existe"),
            @ApiResponse(responseCode = "405", description = "Metodo no soportado para este endpoint"),
            @ApiResponse(responseCode = "415", description = "Se debe indicar en header el campo content-type y valor application/json")
    })
    @GetMapping(path = "/comprobar-prestamo/{customerId}/{monto}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<String> comprobarPrestamo(
            @Parameter(name = "Id Customer",description = "Identificador único del cliente",required = true,example = "1")
            @Positive @PathVariable Long customerId,
            @Parameter(name = "monto",description = "monto del prestamo",required = true,example = "1")
            @Positive @PathVariable Integer monto);
}
