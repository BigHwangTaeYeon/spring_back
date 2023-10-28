# PSA(Portable Service Abstraction)
보통 Service Abstraction이라 부름

아래는 PSA 예시

[Spring MVC](#spring-mvc)

[Spring MVC](#spring-transaction)

[Spring Cache](#spring-cache)

Controller를 보다시피,
Servlet Application을 만들지만 Servlet을 사용하지 않는다.

```java 
// GET POST 등 요청하여 요청대로 실행해주는 것이 Servlet. ex) GetMapping("/owner/create")
public class OwnewCreateServlet extends HttpServlet {
    // 이 안에서 Get요청이나 Post요청을 처리하고,
    // 이러한 Servlet을 Web.xml이라는 파일에 매핑하여
    // 모든 요청(GET /owner/create)이 들어오면 doGet() 실행
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws {
        super.doGet(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws {
        super.doPost(req, resp);
    }
}
```

### Spring MVC

나의 코드
@Controller | @RequestMapping
servlet | Reactive
톰캣, 제티, 네티, 언더토우


Spring이 제공해주는 추상화 계층, (boot에서 tomcat이 자동으로 잡히는 것도 있음)
그 중 Spring Web MVC가 제공하는 추상화 계층을 설명

사용하던 코드를 그대로 다른 프로그래밍이 가능

spring 5 webFlux
spring mvc와 비슷한 방법으로 코딩하지만 기존 방식과 다름
기존(Servlet)처럼 하나의 요청에 하나의 쓰레드를 사용하는 구조가 아닌,
CPU개수 만큼 Thread를 유지하며 뒷단에서 쓰레드풀을 이용하지만 앞단에 최소한의 쓰레드로 가용성을 높이는 프로그래밍

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.1.0</version>
</dependency>
대신 
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-webflux -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
    <version>3.1.0</version>
</dependency>
사용

starter-web이 빠지면 Spring mvc가 빠진다.
mvc가 빠지면 더이상 ModelAndView를 사용할 수 없다.
```java
// import org.springframework.web.servlet.ModelAndView;
// @GetMappint("/Hello/word")
// public ModelAndView hello(){
//     ModelAndView mv = new ModelAndView("Hello/word");
//     return mv;
// }
```

이렇게 실행하면, tomcat에서 netty기반으로 실행이 된다.
Netty started on port(s) : 8080
Spring이 제공해주는 추상화 계층을 사용하여 Netty를 사용한다.


### Spring Transaction
나의 코드
@transactional
Jpa TransactionManager | Datasource TransactionManager | Hibernate TransactionManager
쇼핑몰에서 물건을 살 때, 물건이 없으면 결제한 돈도 돌려줘야한다.
```java
try {
    dbConnection = getDBConnection();
    dbConnection.setAutoCommit(false);

    ...(insert)

    dbConnection.commit();
} catch(SQLException) {
    dbConnection.rollback();
}
```
위와 같은 코딩을 Spring에서 @transactional로 제공한다.


이 모든게 Portable Service Abstraction이다.

### Spring Cache
@Cacheable | @CacheEvict | ...

CacheManager
JCacheManager | ConcurrentMapCacheManager | EhCacheCacheManager | ...

```java
public interface VetRepository extends Repository<Vet, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("vets")  // 이런 Annotation을 사용하면 구현체는 신경쓰지 않아도, 필요에 따라 바꿔 사용해도 작성한 코드는 바꾸지 않아도 된다.
    Collection<Vet> findAll() throws DataAccessException;
}
```
<!-- https://mvnrepository.com/artifact/org.ehcache/ehcache -->
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.10.0</version>
</dependency>

@Cacheable("vets")  // 이런 Annotation을 사용하면 구현체는 신경쓰지 않아도, 필요에 따라 바꿔 사용해도 작성한 코드는 바꾸지 않아도 된다.

이렇게 Spring에서 제공하는 다양한 편의성, Service Abstraction를 사용하면 더 견고해지고, 기술이 바뀌더라도 코드는 바뀌지 않는다. 