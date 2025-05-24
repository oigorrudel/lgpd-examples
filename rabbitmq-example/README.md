# rabbitmq-example

Utilizando Spring Boot e RabbitMQ existem algumas possibilidades de tratamento utilizando dados sensíveis.
<br>As possibilidades:
- criptografia de ponta a ponta (ex: RSA ou AES)
- transporte seguro (ex: TLS ou mTLS)

```
curl -X POST --location "http://localhost:8080/v1/persons"
```