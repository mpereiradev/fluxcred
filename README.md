# FluxCred Core API

## Sobre a Aplicação

A FluxCred Core API é uma aplicação responsável por gerenciar os principais recursos do sistema FluxCred, incluindo empréstimos, parcelas, autenticação de usuários e muito mais.

## Arquitetura Hexagonal

Utilizamos a arquitetura hexagonal para garantir a separação clara das responsabilidades e facilitar a manutenção e evolução da aplicação. Esta arquitetura divide a aplicação em camadas externas (portas) e camadas internas (adaptadores), permitindo que a lógica de negócios permaneça independente das implementações técnicas.

## Spring Security

O Spring Security é utilizado para garantir a segurança da API, controlando o acesso aos recursos por meio de autenticação e autorização baseadas em tokens JWT.

## RabbitMQ

Integramos o RabbitMQ para implementar a comunicação assíncrona entre os microsserviços que compoem a solução. Isso nos permite garantir a escalabilidade e a confiabilidade do sistema, além de desacoplar processos que não precisam ser executados de forma síncrona.

## Design Patterns

Na construção da aplicação, aplicamos diversos padrões de projeto para promover a reutilização de código, simplificar a manutenção e melhorar a escalabilidade. Alguns dos padrões utilizados incluem:

- **Factory Method:** Para a criação de objetos complexos, como as parcelas de empréstimos.
- **Strategy:** Utilizado em casos como o cálculo do valor das parcelas, onde diferentes estratégias podem ser aplicadas com base nas características do empréstimo.
- **DTO (Data Transfer Object):** Para transferir dados entre as camadas da aplicação e garantir a separação entre a lógica de negócios e a camada de apresentação.

## PostgreSQL

O PostgreSQL é o banco de dados escolhido para armazenar os dados da aplicação. 
Isso não estava explicito no arquivo de documentação do projeto, mas pelo desenho da modelagem dos campos entende-se que deve ser usado um banco relacional. 
Escolhi ele por que oferece recursos avançados de segurança, desempenho e escalabilidade, atendendo às necessidades do FluxCred Core API.

## Testes Unitários

A FluxCred Core API possui uma cobertura abrangente de testes unitários para garantir a qualidade e estabilidade do código. Os testes unitários são escritos usando o framework de teste JUnit e Mockito para simular o comportamento de classes e componentes externos.

Devido a restrições de tempo, os testes integrados e o uso de tecnologias como testContainers não puderam ser implementados neste momento. No entanto, os testes unitários fornecem uma camada sólida de garantia de qualidade para as funcionalidades da API, permitindo a identificação precoce de regressões e problemas de código.

## Configuração da Rede e Arquivos Docker Compose

As APIs FluxCred Core e FluxCred Payment compartilham a mesma rede para facilitar a comunicação entre elas. Antes de executar os arquivos de docker-compose, é necessário criar a rede com o seguinte comando:

Certifique-se de criar a rede FluxCred Network antes de executar os arquivos docker-compose para garantir a conectividade entre as APIs.

Aqui estão o comando:
```
docker network create fluxcred_network
```
## Rodando a Aplicação com Docker e Docker-Compose

Para executar a aplicação localmente, siga estas etapas:

1. Certifique-se de ter o Docker e o Docker Compose instalados em seu sistema.
2. Clone o repositório do FluxCred Core API.
3. Navegue até o diretório raiz do projeto.
4. Navegue até o diretório docker do projeto.
4. Execute o comando `docker-compose -p fluxcred up --build -d` para construir e iniciar os contêineres da aplicação.
5. Após a inicialização, a API estará disponível em `http://localhost:8080`.

## Conclusão

A FluxCred é uma solução simples mas completa, construída com tecnologias modernas e boas práticas de desenvolvimento. 
Busquei desenvolver algo que me destaque dos demais candidatos e ao mesmo tempo mostre uma parte do meu conhecimento técnico e atenda o curto prazo para o teste de 3 dias.

