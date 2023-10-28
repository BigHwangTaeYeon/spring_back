# @AOP(Aspect-Oriented Programming) 관점(Aspect)지향 프로그래밍

성능 검사 예시
```java
@GetMapping("owners/new")
@LogExecutionTime   // Create Annotation    ctn + 1 - quick fix
public String initCreationForm(Map<String,Object> model) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    ...

    stopWatch.stop();
    sysout(stopWatch.prettyPrint());

    ...

    stopWatch.stop();
    sysout(stopWatch.prettyPrint());

    return "/owners/"+owner.getId();
}
```

```java
// 간단한 Marker형 Annotation
@Target(ElementType.METHOD) // Annotation 사용 위치
@Retention(TetentionPolicy.RUNTIME) // 언제까지 유지할 것인가
public @interface LogExecutionTime {
}
```

```java
@Component // Bean 등록
@Aspect // Aspect 선언
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Around("@annotation(LogExecutionTime)")    // JoinPoint를 받을 수 있다. (targer method)
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();
        //joinPoint.proceed() == initCreationForm()

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());

        return proceed;
    }
}
```

initCreationForm Class는 저번 Proxy에서 Cash class와 동일한 Target이다.
logExecutionTime Class는 저번 Proxy에서 CashPerf class와 동일하다.
Payment를 상속받고 객체 생성시 Cash 대신 CashPerf instance를 넣어주는 것은 
Spring AOP에서 자동으로 만들어주는 것이다.

@Around 말고 @After @Before 어떤 Exception이 발생했을 때,
("@annotation(LogExecutionTime)") @annotation 말고 @Bean 등,
@LogExecutionTime 없이도 적용하는 방법 등
Aspect사용 법은 여러가지 방법이 많다.