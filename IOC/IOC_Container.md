# IOC(Inversion Of Control) Container

Bean을 만들고 의존성을 엮어주며 Container가 가지고 있는 Bean들을 제공해주는 일을 한다.

Controller Repository 등 Bean이며
@Controller, interface , @Configuration class 안에서 @Bean사용하여 method구현 등으로 만들어준다

의존성 주입은 Bean끼리만 가능하다.
즉, Spring IOC 컨테이너 안에 들어 있는 객체들끼리만 의존성 주입이 가능하다.

```java
@Controller
class OwnerController {
    private final OwnerRepository owners;
    private final ApplicationContext applicationContext;
    
    public OwnerController(OwnerRepository clinicService, ApplicationContext applicationContext){
        this.owners = clinicService;
        this.applicationContext = applicationContext;
    }

    @Test
    public void getBean(){
        applicationContext.getBeanDefinitionNames();    // 이 안에 들어있는 모든 Bean들의 이름을 가져올 수 있다.
        applicationContext.getBean("BeanName"); // getBeanDefinitionNames()에서 찾은 BeanName을 넣어주면 가져올 수 있다.
        applicationContext.getBean(OwnerController.class); // 이렇게 Bean객체를 직접 가져올 수도 있다.
    }

    @GetMapping("/bean")
    @ResponseBody
    public String bean() {  // 같은 hash값이 찍힌다.
        return "bean : " + applicationContext.getBean(OwnerRepository.class) + "\n" // ApplicationContext applicationContext
            + "owners : " + this.owners;    // OwnerRepository owners
    }
}
```
이러한 객체를 singletone scope의 객체라 부른다.
객체의 scope, 유효범위.
객체 하나를 application 전반에서 계속해서 재사용한다.

IOC Container에서 singletone scope의 객체를 손쉽게 사용할 수 있다.