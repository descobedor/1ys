# iys project version 1.0.0
---
Microservices project iys

![Infraestructure plan](http://.../)

## iys-security
### Descripcion

Generate token with Basic authentication and storing public key management.


## iys-customer-employee-interaction
---
### Description

This service...
### Interaction Services
Contains API related to creating and retrieving all user information. In this service, we are also demonstrating how we use user's privilege in accessing the API. For example, the access to `GET ` `/interactions` endpoint will only be allowed for user whose `READ_BASIC_INFORMATION` privilege, but the access
 to other endpoints don't require any special privilege as long as it has correct scope. Please refer to spring security docs [here](http://projects.spring.io/spring-security-oauth/docs/oauth2.html) for more details.

### Secuencia completa empleado-cliente
```mermaid
sequenceDiagram
    Empleado->>Empleado: Escanea el codigo de la mesa (opcional)
    Empleado->>Servicio interacción: Solicita crear un espacio, y de forma opcional (a partir de una mesa)
    Servicio interacción-->>Servicio externo: SI existe una mesa asociada, envía notificación de mesa ocupada.
    Servicio interacción-->>Empleado: Ruta a escanear por el cliente en imagen QR
    Cliente->>Empleado: Escanea el código QR
    Cliente->>Servicio interacción: Se incorpora al espacio creado
    Servicio interacción->>Cliente: Confirma que OK
    Cliente->>Servicio interacción: Solicita servicio empleado
    Servicio interacción->>Empleado: Envia notificación al empleado (mesa)
    Cliente-->>Servicio interacción: No puede volver a solicitar el servicio
    Empleado->>Empleado: Empleado escanea el codigo de la mesa del cliente
    Empleado->>Servicio interacción: Borra la notificación del cliente a partir del codigo de la mesa 
    Cliente->>Servicio interacción: Solicita servicio empleado
    Cliente->>Servicio interacción: Cancela la solicitud del servicio empleado
    Empleado->>Empleado: Agrega una mesa
    Empleado->>Servicio interacción: Solicita la incorporación de una mesa para un sitio (2 mesas en total)
    Servicio interacción-->>Servicio externo: Envía notificación de mesa ocupada con la(s) otra(s) mesa(s) ocupada(s)
    Cliente->>Servicio interacción: Solicita servicio empleado
    Servicio interacción->>Empleado: Envia notificación al empleado (las DOS mesas asociadas)
    Empleado->>Empleado: Empleado escanea el codigo de cualquiera de las dos mesas asociadas
    Empleado->>Servicio interacción: Borra la notificación del cliente a partir del codigo de cualquier codigo de la mesa 
    Cliente->>Servicio interacción: Pide la cuenta
    Cliente-->>Servicio interacción: No puede volver a solicitar el servicio ni otra vez la cuenta
    Empleado->>Servicio interacción: Empleado finaliza el espacio
```
---



```mermaid
sequenceDiagram
    Employee->>Synchroniser Service: POST /synchronises
    Synchroniser Service-->>Employee: 201 route for customer [QR code format]
    Customer->>Synchroniser Service: POST /synchronises/take-ups
    Synchroniser Service-->>Customer: 200 OK
```

---



| Method | Path                                     | Description                                                | Scope |  Privilege   | 
|--------|------------------------------------------|------------------------------------------------------------|-------|--------------|
| POST   | /interactions                            | Create customer space                                      | ui    | WRITE_ACCESS |
| GET    | /interactions/{code}                     | Get customer space with detail                             | ui    | ALL_ACCESS   |
| POST   | /interactions/{code}/orders              | Create customer service                                    | ui    | ALL_ACCESS   |
| DELETE | /interactions/{code}/orders              | Delete customer service by customer                        | ui    | ALL_ACCESS   |
| DELETE | /interactions/{code}/orders/{reference}  | Delete customer service by employee scanning any reference | ui    | ALL_ACCESS   |
| PUT    | /interactions/{code}                     | Add new reference into space                               | ui    | ALL_ACCESS   | 
| POST   | /interactions/{code}/bills               | Can i have the bill?                                       | ui    | ALL_ACCESS   |
| DELETE | /interactions/{code}                     | Delete customer space                                      | ui    | WRITE_ACCESS |
| GET    | /interactions                            | Get all customer spaces with details                       | ui    | WRITE_ACCESS |




# iys project version 2.0.0 - [TBD]
---
Microservices project iys

![Infraestructure plan](http://.../)

## iys-accounting
---
### Description
[TBD]


## iys-stock-control
---
### Description

[TBD]
https://www.camarero10.com/controlar-stock-de-cocina-en-un-restaurante/