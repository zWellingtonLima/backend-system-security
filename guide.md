# Explicação das tecnologias, métodos e afins usados

---

## Users user = (Users) request.getAttribute("usuarioAutenticado"); 
> Usado no Consumos Service.

## Wrapper Integer vs primitivo int como parâmetro de Controllers ou Services
O Spring já garante que o _{id}_ que chega no método é o tipo de valor esperado, caso não, lança um **MethodArgumentTypeMismatchException**.

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

## @JsonIgnore
Não tentar aceder a lista de chaves dentro da Entidade Classe por causa do Lazy Loading. 
JSON ignore usado para evitar que o JPA fosse buscar essa lista.
Posso fazer a busca através do Repositório de Chaves invés de buscar usando o getters de chaves dentro de sala.

---

## RuntimeException

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












# Problemas no desenvolvimento e da atual usabilidade do sistema
Quando começamos a desenvolver os Serviços de Consumos a gente encontrou um problema: da maneira que estávamos fazendo era preciso sempre passar o id da sessão ou o id do usuário.
Então em todos os serviços seria preciso saber quem é o utilizador que deverá estar autenticado com uma sessão ativa e válida. 

Uma forma que encontramos de fazer a verificação antes mesmo dos dados chegarem ao Controller foi a de usar um **Interceptor**. 
Dessa maneira a gente consegue tanto proteger já as rotas que precisam de autenticação quanto inserir os dados necessários como idSessao e idUsuario, tornando reutilizável em quaisquer outros serviços porventura criados.

## Usar um Inteiro como Sessão
> É suscetível a **IDOR** (Insecure Direct Object Reference)

Um utilizador mal-intencionado ao descobrir que o id da sessão que é retornado do _login_ é apenas um inteiro, pode simplesmente enviar diversas solicitações com o id crescente até que ache uma válida.

### Para resolver
- Alterar o tipo de dado para um UUID.
- Implementar um campo **token** na tabela de sessão.

## Links de Apoio
> Interceptors
- [Como interceptors funcionam e como criar](https://medium.com/@AlexanderObregon/how-spring-boot-configures-and-executes-interceptors-52238f6081d1)
- [Spring Boot Interceptors](https://www.geeksforgeeks.org/advance-java/spring-boot-interceptor/)

> DTO's
- [Entendendo DTO's](https://medium.com/@roshanfarakate/understanding-dtos-in-spring-boot-a-comprehensive-guide-20e2b8101ee6)