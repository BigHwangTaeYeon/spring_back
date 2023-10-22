# Bean
스프링 IoC 컨테이너가 관리하는 객체

등록방법
1) Component Scanning
    @Component
        - @Repository
        - @Service
        - @Controller
        - @Component
        - @Configuration
        - @.. 직접 정의도 가능
2) 직접 일일히 xml이나 Java 설정 파일에 등록
    Java 
        @Configuration
            @Bean
    xml

사용법
1) @Autowired, @Inject
2) ApplicationContext class에서 getBean(OwnerRepository.class)처럼 사용

특징
- 오로지 "빈"들만 의존성 주입이 가능하다.

Main class
```java
@Autowired
ApplicationContext applicationContext;

@Test
public void getBean(){
    OwnerController ownerController = new OwnerController();    // 직접 객체를 만들었지만, 이건 Bean이 아니다 !
    OwnerController bean = applicationContext.getBean(OwnerController.class);   // 이건 Bean이 맞다. applicationContext에서 직접 가져왔기에.
    assertThat(bean).isNotNull();   // spring에서 말하는 Bean은 applicationContext가 알고 있는 객체, 만들어서 담고있는 객체가 Bean이다
}
```

OwnerController
```java
@Controller
class OwnerController {
    private final OwnerRepository owners;
    private final ApplicationContext applicationContext;

    // 아래와 같이, 오직 Bean만 의존성 주입이 된다.
    public OwnerController(OwnerRepository clinicService, ApplicationContext applicationContext) {
        this.owners = clinicService;
        this.applicationContext = applicationContext;
    }
}
```
Component Scan
@Controller 어노테이션이 사실상 Component라는 메타 어노테이션을 사용하는 어노테이션이다.
spring IoC 컨테이너가 사용하는 여러가지 interface를 라이프 사이클 콜백이라 부른다.
라이프 사이클 콜백은 @Component라는 annotation을 사용하는 모든 Class를 찾아서 instance를 만들어서 bean으로 등록하는 복잡한 일을 하는 annotation 프로세서, annotation 처리기가 등록되어있다. 
spring boot에는 SpringBootApplication annotation을 타고 들어가면 SpringBootApplication class안에 @ComponentScan annotation이 있다.
@ComponentScan이 어디부터 Component를 찾아보라고 알려준다.
@SpringBootApplication에서부터 하위 패키지의 Class를 모두 찾아본다.

Repository는 Spring Data JPA가 제공해주는 기능에 의해 Bean으로 등록된다.
특정한 annotation이 없더라도, 특정한 interface를 상속받아 사용된다.



Bean으로 직접 등록하는 방법
xml, Java설정 - 추세는 Java 설정으로 되어있음

```java
// SampleController를 ComponentScan을 활용하여 Bean으로 등록
@Controller 
public class SampleController{

}
```

```java
@Configuration
public class SampleConfig {
    @Bean   // Java로 Bean을 직접 정의.
    public SampleController sampleController() {
        return new SampleController();
    }
}
// SampleConfig에서 java로 Bean을 등록했기에 @Controller를 사용할 필요가 없다.
//@Controller 
public class SampleController{

}
```

```java
//ApplicationContext에 Bean으로 등록되어있는지 확인하는 Test Code 작성
@Runwith(SpringRunner.class)
@SpringBootTest
public class SampleControllerTest {
    @Autowired
    ApplicationContext applicationContext;
    @Test
    public void testDI() {
        SampleController bean = applicationContext.getBean(SampleController.class);
        assertTha(bean).isNotNull();
    }
}
```

사용 방법

```java
@Controller
class OwnerController {

    //private final OwnerRepository owners;
    //private final ApplicationContext applicationContext;

    @Autowired  // 이런식으로 Autowired를 사용하면, 생성자를 사용하지 않고 ApplicationContext로 직접 꺼내지 않고 
    private OwnerRepository owners; // DI하여 사용할 수 있다.

    // 아래와 같이, 오직 Bean만 의존성 주입이 된다.
    // public OwnerController(OwnerRepository clinicService, ApplicationContext applicationContext) {
    //     this.owners = clinicService;
    //     this.applicationContext = applicationContext;
    // }

    @GetMapping("/owners/new")
    public ...
}
```
