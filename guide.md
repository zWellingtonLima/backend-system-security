# Explicação das tecnologias, métodos e afins usados

---

## Anotações Usadas
### @Transactional
> Abre uma transação no BD.

Foi usado em alguns serviços onde o foco era a **escrita de dados**, logo, em métodos que usavam de save(), update() ou delete, ou quando múltiplas operações no banco eram realizadas dentro do mesmo método.

Ele garante se algo der errado, um rollback vai ser lançado, garantindo consistências dos dados da DB.

Em métodos de leitura não é necessário mas podemos anotar com @Transactional(readOnly = true)

---

## Users user = (Users) request.getAttribute("usuarioAutenticado"); 
> Usado no Consumos Service.

## Wrapper Integer vs primitivo int como parâmetro de Controllers ou Services
O Spring já garante que o _{id}_ que chega no método é o tipo de valor esperado, caso não, lança um **MethodArgumentTypeMismatchException**.

Em momentos do código quando estamos trabalhando com as entidades JPA é mais seguro usar Wrappers porque esses possibilitam trabalhar com a ausência de valor.

> O Wrapper Integer foi escolhido porque podemos trabalhar com a possibilidade de um valor Null.

---

## Optional\<T\> 
- Um dos erros mais comuns em java é o **NullPointerException**, causado quando a gente tenta acessar uma propriedade ou um método de um valor que é Null.

```java
User user = userRepository.findById(id); // E se o id não existir? 
System.out.println(user.getName); // Ai ele explode aqui. NullPointerException
```

O Optional serve para esses casos. Ele pode ou não conter algo. Ele fornece métodos pra gente trabalhar mesmo na ausência de um valor.

### Como usar:
```java
// O repositório retorna Optional
Optional<User> optionalUser = userRepository.findById(id);

// Forma 1: verificar se existe antes de usar
if (optionalUser.isPresent()) {
    User user = optionalUser.get();
}

// Forma 2 (mais roxeda): orElseThrow — lança exceção se vazio
User user = userRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

// Forma 3: orElse — retorna um valor padrão se vazio
User user = userRepository.findById(id)
    .orElse(new User());

// Forma 4: ifPresent — executa uma ação se existir
optionalUser.ifPresent(u -> System.out.println(u.getName()));

// Forma 5: map — transforma o valor se existir
String name = optionalUser
    .map(User::getName)
    .orElse("Desconhecido");
```

---

## Method Reference
> O Method Reference é uma forma mais concisa de se escrever uma expressão Lambda.
```java
 .map(User::getName) // É a mesma coisa de .map(user -> user.getName());

```

A utilização disso é comum em algumas API mais recentes que implementar interfaces funcionais, ou as Collections ou as Streams.

---

## Streams

> Uma Stream é uma sequência de elementos que permite operações funcionais tais como o map, filter, reduce. Isso é usado para evitar iterar manualmente usando um for, por exemplo.

```java
    public List<ConsumosResponseDTO> listConsumos() {
    return consumosRepo.findAll()
            .stream() 
            .map(ConsumosResponseDTO::from)
            .toList();
}
```
O **.map()** transforma cada elemento da stream, ou seja, para cada objeto retornado do banco, ele aplica o método **from**

> Entra um Consumos -> sai o ConsumosResponseDTO através do método from() existente dentro do ConsumosResponseDTO. Por fim, o toList() coleta todos os elementos do stream e transforma novamente em uma List, finalizando a stream.

---

## ConsumosResponseDTO.from()
> O método from é basicamente um método de fábrica, ou seja, serve para construir um objeto, recebendo certas características e transformando-o em outro formato.

Usando esse mesmo exemplo, o ConsumosResponseDTO.from recebe um Objeto do tipo Consumos, pega em todas as propriedades que ele possui e internamente converte-o para um tipo definido pelo ConsumosResponseDTO.

---

## @JsonIgnore
Não tentar aceder a lista de chaves dentro da Entidade Classe por causa do Lazy Loading. 
JSON ignore usado para evitar que o JPA fosse buscar essa lista.
Posso fazer a busca através do Repositório de Chaves invés de buscar usando o getters de chaves dentro de sala.

---

## Exceptions
### RuntimeException
> Essa é a classe base das Exceções não verificadas (unchecked)

- Usando ela não somos obrigados a declarar no método ou classe o _throws_
- Geralmente elas indicam um erro de programação ou quando uma regra de negócio é violada
- Usamos isso quando não faz sentido tratar o erro.

### IllegalArgumentException
> Usar quando um argumento passado para um método é inválido

A usabilidade aqui é clara: quando o problema está na entrada do método
```java
if (idade < 0) {
    throw new IllegalArgumentException("Idade nao pode ser negativa");
        }
```

### IllegalStateException
> Quando o estado atual do sistema não permite a operação

No nosso caso, verificar se um funcionário ou visitante já existe com base no ID dele.
```java
if (funcionarioRepo.existsByNumeroFuncionario(...)) {
    throw new IllegalStateException("Ja existe um funcionario com esse numero")
        }
```

> Diferenças com o IllegalArgumentException:
> - O input pode ser válido.
> - Mas o sistema já está num estado que impede essa ação.

### EntityNotFoundException
> Esse é comum no JPA. Usado quando buscamos pela existência de uma entidade que, por algum motivo, não existe.

---

## private <T extends Enum<T>> List<Map<String, String>> buildList(T[] values)
> Usado no controller de listagens

```java
private <T extends Enum<T>> List<Map<String, String>> buildList(T[] values) {
        return Arrays.stream(values)
                .map(v -> {
                    Map<String, String> item = new LinkedHashMap<>();
                    item.put("valor", v.name());
                    item.put("label", ((LabeledEnum) v).getLabel());
                    return item;
                })
                .collect(Collectors.toList());
    }
```

Possui como assinatura o <T extends Enum<T>>, ou seja, ele aceita qualquer lista de Enums.
Aceita como parâmetro (T[] values) -> que significa um array de enums.

O fluxo  interno desse código é:
1. Arrays.stream(values) -> converte para Stream
2. .map() -> Pra cada enum (v)
3. Map<String, String> item = new LinkedHashMap<> -> Cria um Map e o LinkedHashMap mantém a ordem em que eles foram inseridos.
4. item.pu("valor", v.name()) e item.put("label", ((LabeledEnum) v).getLabel()) -> Cria as propriedades valor e label e, no label, faz o Cast pra essa LabeledEnum usando esse método getLabel criado lá.
5. retorna o map
6. Junta tudo numa lista com o collect(Collectors.toList())

> OBS: No ponto 6 foi usado o collect(Collectors.toList()) invés de apenas toList() porque queremos que essa lista seja mutável, ou seja, posteriormente será possível adicionar ou remover algum item dessa lista.

---

## @Autowired vs private final + Construtor
[Link Artigo Baeldung sobre problemas em usar Field Injection](https://www.baeldung.com/java-spring-field-injection-cons)

### Resumindo:
- Em situações onde fossemos testar o código criado (não é o caso ainda) seria preciso iniciar todo o contexto do Spring. Usando a injeção por Construtor, podemos só instanciar a classe.
- Imutabilidade: quando a gente usa _private final_ o campo nunca pode ser reatribuído após a construção. Fica mais seguro e previsível.
- Dependências ocultas: O @Autowired em campo esconde as dependências da classe. O Construtor obriga que elas sejam explícitas. Por exemplo:

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
}
```

Se por algum motivo a gente quisesse instanciar essa classe manualmente (sem o Spring), teríamos de fazer

```java
Userservice service = new UserService();
```

Só que dessa forma faltam dependências obrigatórias para o funcionamento dessa classe (os repositórios) e a gente só saberia disso quando desse um **erro em Runtime** NullPointerException.

> Usando a outra forma é obrigatório a gente passar como parâmetro pro Construtor ou ele nem compila.

---

## ResponseEntity\<T>
O ResponseEntity representa toda a resposta HTTP: corpo, cabeçalhos, código de Status.
Com ele a gente tem controle total sobre o que nossos endpoints retornam.

```java
// Sem ResponseEntity
@GetMapping("/user/{id}")
public User getUser(@PathVariable Long id) {
    return userRepository.findById(id).orElse(null); // Status sempre 200
}

// Com ResponseEntity
@GetMapping("/user/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return userRepository.findById(id)
        .map(user -> ResponseEntity.ok(user))          // 200 OK com o user
        .orElse(ResponseEntity.notFound().build());    // 404 Not Found
}
```

### Principais usos
```java
// 200 OK com corpo
ResponseEntity.ok(objeto);

// 200 OK sem corpo
ResponseEntity.ok().build();

// 201 Created (criação de recurso)
ResponseEntity.status(HttpStatus.CREATED).body(novoObjeto);
// ou atalho:
ResponseEntity.created(URI.create("/users/" + id)).body(novoObjeto);

// 204 No Content (ex: delete bem-sucedido)
ResponseEntity.noContent().build();

// 400 Bad Request
ResponseEntity.badRequest().body("Dados inválidos");

// 404 Not Found
ResponseEntity.notFound().build();

// 500 Internal Server Error
ResponseEntity.internalServerError().body("Erro interno");

// Com cabeçalhos personalizados
ResponseEntity.ok()
    .header("X-Custom-Header", "valor")
    .body(objeto);
```

## DTO (Data Transfer Object)
> Fazer o mapeamento para DTO evita expor dados que o frontend não precisa saber (e alguns que nem deve).

### O que são e para quê servem
Os DTO são objetos que carregam dados entre camadas ou processos numa aplicação. Normalmente são usados para encapsular dados e transportá-los, tipicamente entre o servidor e o cliente.

### Características de um DTO
- São simples objetos sem lógica de negócio.
- São compostos apenas de campos, getters e setters e as vezes construtores

### Por que usar isso no Spring?
- São usados para esconder dados, permitindo a gente expor para os _clientes_ apenas os dados necessários.
- Permitem fazer uma distinção clara entre o modelo de dados usado internamente da representação externa desses dados. É Basicamente o primeiro ponto dessa lista. 
- Permitem isolar a lógica de validação dos dados inseridos. 


---

## Funcionalidades Java
```java
    public List<ConsumosResponseDTO> listConsumos() {
        return consumosRepo.findAll()
                .stream()
                .map(ConsumosResponseDTO::from)
                .toList();
    }
```

```java
public ConsumosResponseDTO searchById(Integer id) {
        return consumosRepo.findById(id)
                .map(ConsumosResponseDTO::from)
                .orElseThrow(() -> new RuntimeException("Consumo não encontrado: " + id));
    }
```

---

## Nome de métodos em repository JPA
- [QueryDerivation Baeldung](https://www.baeldung.com/spring-data-derived-queries)
- [JPA Query Methods - Spring](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)

> findTopByTipoConsumoAndAtivoTrueOrderByDataRegistoDesc

Isso é chamado de **query derivation** do Spring Data JPA. Quebrando isso em partes fica:
- findTopBy -> pega 1 resultado
- TipoConsumo -> é o campo
- AndAtivoTrue -> ativo = true
- OrderByDataRegistoDesc -> ordena de forma decrescente

O equivalente em SQL seria algo como:
```sql
SELECT *
FROM consumos
WHERE tipo_consumo = ?
  AND ativo = true
ORDER BY data_registo DESC
LIMIT 1;
```

### Regras Principais pra fazer a Query Derivation
- Prefixos
  - findBy
  - findTopBy
  - findFirstBy
  - existsBy
  - countBy
- Operadores
  - And
  - Or
- Condições
  - True, False
  - IsNull, IsNotNull
  - LessThan, GreaterThan
- Ordenação
  - OrderByNomedoCampAsc ou Desc
- Limitação
  - Top
  - First


# Problemas no desenvolvimento e da atual usabilidade do sistema
Quando começamos a desenvolver os Serviços de Consumos a gente encontrou um problema: da maneira que estávamos fazendo era preciso sempre passar o id da sessão ou o id do usuário.
Então em todos os serviços seria preciso saber quem é o utilizador que deverá estar autenticado com uma sessão ativa e válida. 

Uma forma que encontramos de fazer a verificação antes mesmo dos dados chegarem ao Controller foi a de usar um **Interceptor**. 
Dessa maneira a gente consegue tanto proteger já as rotas que precisam de autenticação quanto inserir os dados necessários como idSessao e idUsuario, tornando reutilizável em quaisquer outros serviços porventura criados.

## Usar um Inteiro como ID da Sessão
> É suscetível a **IDOR** (Insecure Direct Object Reference)

Um utilizador mal-intencionado ao descobrir que o id da sessão que é retornado do _login_ é apenas um inteiro, pode simplesmente enviar diversas solicitações com o id crescente até que ache uma válida.

> A mesma coisa acontece com o id de qualquer outra das entidades.

### Para resolver
- Alterar o tipo de dado para um UUID.
- Implementar um campo **token** na tabela de sessão.

## Tabela Visitantes
- É uma tabela desnecessária caso não haja um histórico por visitante, ou seja, o Jessé da empresa Jessezinho entrou 4 vezes.
- Não existe um controle de acesso prévio por visitante, tal como, "O Jessé da empresa Jessezinho está agendado e permitido de entrar hoje para realizar tal serviço."
- Ou se esses dados não sirvam para o guarda reutilizar numa próxima visita da mesma pessoa.

> A ideia da tabela Visitantes é justamente tornar mais simples o procedimento do guarda registrar visitas que ocorrem com uma frequência regular como um serviço de café que vem a cada semana, uma limpeza que tambbém pode ocorrer semanalmente etc.

## Listagens de problemas já identificados
- Cadastro Funcionario Permite enviar numero como parâmetro de setor e o sistema o salva como string.
- Criação de Movimentações não verifica o tipo de documento. Aceita qualquer valor.
- Sessão não tem tempo de expiração no Backend (no front vai ser SessionStorage).


# Para fazer: 
- Incluir todo o fluxo de chaves e molhos: criação, histórico, controle.
- Verificar os createUser e createDate em todos os endpoints
- Fazer algo com o Tipo Chave (Principal, Reserva)
- Analisar adição de @Transactional em algumas operações no ConsumosService 
- Enviar campo "Criado em" na Lista Funcionarios

# Links de Apoio
> Interceptors
- [Como interceptors funcionam e como criar](https://medium.com/@AlexanderObregon/how-spring-boot-configures-and-executes-interceptors-52238f6081d1)
- [Spring Boot Interceptors](https://www.geeksforgeeks.org/advance-java/spring-boot-interceptor/)

> DTO's
- [Entendendo DTO's](https://medium.com/@roshanfarakate/understanding-dtos-in-spring-boot-a-comprehensive-guide-20e2b8101ee6)