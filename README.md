# Peoplo Management

Esta aplicação foi feita em Java Spring Boot. 

É uma API que permite gerenciar pessoas.

##Funcionalidades
- Criar pessoas;
- Editar uma pessoa;
- Consultar uma pessoa;
- Criar endereços para a pessoa;
- Listar endereços da pessoa;
- Poder informar qual o endereço favorito da pessa.

##Características das pessoas
A pessoa deve tem os seguintes campos:
- Nome;
- Data de nascimento;
- Endereço:
  - Logradouro
  - CEP;
  - Número;
  - Cidade;

#Infos do projeto
- Todas as respostas da API são em JSON;
- O banco de dados utilizado é o H2;
- Foram aplicados testes unitários nos métodos das services;
- Foram adotodas boas práticas do Clean Code como:
  - Simples e direto: seguindo o princípio de KISS (Keep It Simple Stupid), o código bem escrito deve ter o mínimo de complexidade possível, para que possa ser facilmente depurado e compreendido.
  - Seco: o código DRY “Don’t Repeat It” – conceito trazido por Andy Hunt, um dos autores do Manifesto Ágil -, é aquele sem ambiguidade, ou seja, se você já o inseriu em algum lugar no código-fonte, ele não deve ser implementado novamente.
  - Eficiente: um código é programado para atender a algum objetivo específico, então procure se certificar que ele funcionará da forma que foi proposto.
  - Elegante: Bjarne Stroustrup, inventor do C++, diz que gosta dos seus códigos elegantes e que, quando você o lê, deve se sentir feliz. A ideia da elegância é tornar o código diferente dos demais.
  - Atenção aos detalhes: o programador deve sempre criar um código de forma cuidadosa, já que um código mal escrito desde o início impactará em sua manutenção, trazendo grandes prejuízos e lentidão para entendê-lo.
  - Atenção aos comentários: comente o mínimo possível em seu código. O código já deve ser claro o suficiente para não necessitar de comentários. Caso necessite, tente comentar o mínimo possível.

##Documentação
http://localhost:8080/swagger-ui/index.html



