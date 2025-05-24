# log-example

O mascaramento de dados sensíveis em logs em aplicações Spring Boot,
é possível com apenas uma classe Java e um arquivo XML.

O **Logback** é o framework de logs padrão de aplicações Spring Boot.
Adicionando o arquivo ```logback-spring.xml``` em ```resources``` é possível configurá-lo como desejar.

Algumas aplicações possíveis:
- aplicar filtros em logs
- manipular das informações logadas (como esse exemplo de mascaramento de valores de campos no toString)
- padronizar os logs (como por default o Spring já faz, ex: org/springframework/boot/logging/logback/base.xml)
- configurar diferentes logs para diferentes ambientes