# AOP(Aspect-Oriented Programming) 관점(Aspect)지향 프로그래밍

스프링은 크게 IOC AOP PSA의 개념을 제공해주는  Sprign triangle라고 부르는 개념들이 있다.

AOP 구현 방법
    - 컴파일
        A.java --(AOP)--> A.class (AspectJ가 제공)
    - 바이트코드 조작
        A.java ----> A.class --(AOP)--> 메모리 (AspectJ가 제공)
    - 프록시 패턴(Spring AOP)
        기존의 코드를 건드리지 않고 그 객체를 다른 객체로 바꾸는 방법
        클라이언트 코드에 영향을 적게 주면서.
        
대표적인 예로 트렌젝션 경계 설정이 있다.

```java
class insert {
    setAutoCommit(false);
    userRepository.save(user);
    setAutoCommit(true);
}
```

AOP 적용
```java
class insert {
    userRepository.save(user);
}
```
(@transactional)
insert에서 에러나 Exception이 발생하면 commit을 하지않거나 rollback을 해야한다.
그래서 AOP, 앞 뒤에 일어날 부분을 구현하여 적용한다.

또한 AOP의 구현체를 바꾸게 된다면 적용된 모든 class에서 하나하나 고쳐주어야 하는데,
AOP를 적용하면 구현한 필드만 변경하면 되니, 편리하다.


성능 검사 예시
```java
@GetMapping("owners/new")
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