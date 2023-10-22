# DI (Dependency Injectioni)
의존성 주입 받는 방법
@Autowired / @Inject
    - 생성자
    - 필드
    - Setter

생성자로 의존성 주입 받는 법
```java
@Controller
class OwnerController {

    private final OwnerRepository owners;

    @Autowired  
    public OwnerController(OwnerRepository clinicService) { 
        this.owners = clinicService;
    }
    // 생성자에도 @Autowired annotation을 사용하여 DI를 할 수 있다.
    // 그리고 spring 4.3 부터 
    // 어떠한 class에 생성자가 하나뿐이고, 그 생성자로 주입받는 Reference 변수들이 Bean으로 등록되어있다면,
    // 그 Bean을 자동으로 주입해주도록 Spring Framework에 기능이 추가가 되었다.
    // 그래서 @Autowired를 생략할 수 있다.

    @GetMapping("/owners/new")
    public ...

}
```

필드로 바로 의존성 주입 받는 법
```java
@Controller
class OwnerController {
    // 필드로 의존성 주입
    @Autowired
    private OwnerRepository owners;

    // 생성자로 의존성 주입
    // private private OwnerRepository owners;
    // @Autowired  
    // public OwnerController(OwnerRepository clinicService) { 
    //     this.owners = clinicService;
    // }

    @GetMapping("/owners/new")
    public ...

}
```

Setter로 바로 의존성 주입 받는 법
```java
@Controller
class OwnerController {

    private OwnerRepository owners;

    @Autowired  
    public setOwners(OwnerRepository owners) { 
        this.owners = owners;
    }

    @GetMapping("/owners/new")
    public ...

}
```

sampleRepository를 Bean으로 등록하지 않은 상태
```java
@Controller
class OwnerController {

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private SampleRepository sampleRepository;  
    // sampleRepository가 빨간줄로 오류가 난다. 
    // No qualifying bean of type SampleRepository 오류 메시지.
    // OwnerController에 필요한 의존성을 넣어줄 수 없기 때문이다.

    @GetMapping("/owners/new")
    public ...

}
```

Spring Framework Reference에서 권장하는 방법은 생성자이다.
```java
@Controller
class OwnerController {

    private final OwnerRepository owners;

    @Autowired  
    public OwnerController(OwnerRepository clinicService) { 
        this.owners = clinicService;
    }
    // 생성자를 사용하는 방법이 좋은 이유는,
    // 필수적으로 사용해야 하는 reference(OwnerRepository clinicService) 없이는 
    // OwnerController instance를 만들지 못하도록 강제할 수 있다.
    // field Injection 이나 setter Injection은 그냥 만들 수 있다.

    @GetMapping("/owners/new")
    public ...

}
```

하지만 순환참조가 발생할 수 있다.
A가 B를 참조하고 B가 A를 참조하고.
둘다 생성자 Injection을 사용한다면 둘다 못만든다.
그런 경우는 setter나 field Injection을 사용하는 것이 좋다.

setter, field Injection 시, final을 사용하면 안된당.
