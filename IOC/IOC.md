# Inversion Of Control
제어의 역전

일반적 경우
```java
@Controller
class OwnerConroller {
    private OwnerRepository repository = new OwnerRepository();
}
```
의존성(new OwnerRepository())을 만들어서 자기(OwnerConroller)가 사용했다.

IOC
```java
class OwnerController {
    private OnewRepository repo;                        // OnewRepository를 사용하지만 OnewRepository객체를 만들지는 않음

    public OwnerController(OwnerRepository repo){       // Dependency Injection
        this.repo = repo;                               // 생성자를 통해 받아온다.
    }
}                                   // OwnerController 밖에서 누군가가 줄 수 있게 끔. 생성자를 통해 받아온다.
                                    // 의존성을 만드는, 관리하는 일은 OwnerController가 아닌 밖에서 한다.
                                    // 즉 제어권이 역전되었다.
class OwnerConrollerTest{           // 이렇게 의존성을 주입해주는 일을 DI(Dependency Injection)라 한다.
    @Test                           // 그래서 DI도 일종의 IOC라 볼 수 있다.
    public void create(){
        OnewRepository repo = new OnewRepository();
        OwnerController controller = new OwnerController(repo);
    }
}
```
그래서 생명주기를 밖에서 관리하게 된다.
생성도 new 연산자를 사용하지않고 생성자를 통해 받아온다면,
생성자에 주입해준 class안에서 생성을 하고 넣어준 것이기 때문에 해당 class에서 생성을 한 것이고,
소멸도 주입받아 사용했기에 해당 class에서 소멸이 가능하다.