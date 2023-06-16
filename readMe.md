# **자바 ORM 표준 JPA 프로그래밍 - 기본편**

## 1. JPA 소개

### SQL 중심적인 개발의 문제점

- SQL 중심 개발의 문제점
    - 객체 설계 시간보다 SQL 설계에 많은 시간이 소요된다.
    - 객체와 관계형 디비의 불일치가 발생한다.
- 객체와 관계형 데이터베이스의 차이
    - 크게 4가지이다. 상속, 연관관계, 데이터 타입, 데이터 식별 방법이다.
    - 객체는 상속관계를 사용하고 DB는 상속관계 안쓴다.
    - 객체는 참조를 사용하고, 테이블은 외래 키를 사용한다.
    - 객체는 자유롭게 객체 그래프를 탐색할 수 있어야 한다. 하지만 SQL은 처음 실행하는 것에 다라 탐색범위가 결정된다.

### JPA소개

- ORM이란?
    - Object-relational-mapping의 약자로 객체관계매핑을 뜻한다.
    - 객체는 객체대로 설계할 수 있게해준다.
    - 관계형 데이터베이스는 관계형 데이터베이스대로 설계해준다.
    - ORM 프레임워크가 중간에서 매핑한다.
    - 요즘은 대중언어에는 ORM이 대부분 존재한다.
- JPA 동작원리

  ![Screen Shot 2023-06-10 at 19.25.44 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3f200f64-6f18-421a-9c11-492fa1d6b22a/Screen_Shot_2023-06-10_at_19.25.44_PM.png)

    - JPA는 애플리케이션과 JDBC사이에서 동작한다.
    - 객체를 저장 요청하면 JPA가 객체를 분석후 인서트 SQL까지 만들어준다.
    - 조회 시에도 JPA에 ID만 넘겨주면 셀렉트쿼리를 만들어서 날려준 후 엔티티 오브젝트까지 반환해준다.
- JPA 사용 이유는?
    - SQL중심 개발에서 객체 중심 개발을 할 수 있다.
    - 생산성이 올라간다.
        - 기존 쿼리들에서 JPA함수로 한줄에 해결 가능하다.
    - 유지보수에 유리하다.
        - 객체의 필드 변경 시 SQL은 모두 변경해 줘야한다.
        - JPA는 필드만 추가하면된다.
    - 패러다임의 불일치를 해결한다.
    - 성능이 좋다.
- JPA가 패러다임의 불일치를 해결해 준다는것은?
    - JPA 사용 시 JPA가 상속처리를 해준다.
    - JPA 사용 시 연관관계저장과 탐색이 가능하다.
- JPA가 성능이 좋은 이유는?
    - 1차 캐시와 동일성 보장해준다.
        - 즉, 같은 멤버 조회 시 캐시 작업을 해준다.
    - 트랜잭션을 지원하는 쓰기 지연해준다.
        - 즉, 트랜잭션을 커밋할 때까지 INSERT SQL을 모았다가 한번에 SQL전송한다.
    - 지연 로딩과 즉시 로딩을 제공한다.
        - 지연로딩은 객체가 실제 사용될 때 로딩하는 것이고,
        - 즉시 로딩은 JOIN SQL로 한번에 연관된 객체까지 미리 조회하는 것이다.

## 2. JPA 시작

### Hello JPA - 프로젝트 생성

- 메이븐이란?
    - 자바 라이브러리와 빌드를 관리한다.
    - 라이브러리 자동 다운로드 및 의존성 관리한다.
    - 최근에는 Gradle를 많이 사용한다.
- persistence.xml 파일은?
    - JPA 설정파일이다.
    - 데이터베이스 방언을 설정할 수 있다.

### Hello JPA - 애플리케이션 개발

- JPA 구동 방식

  ![Screen Shot 2023-06-10 at 21.14.27 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/19b16dc1-184d-4f96-aec6-acb2f709d6bd/Screen_Shot_2023-06-10_at_21.14.27_PM.png)

    - JPA는 Persistence클래스가 있다.
    - Persistence클래스는 설정정보를 받아서 EntityManagerFactory 클래스를 만든다.
    - 그 후 EntityManagerFactory에서 EntityManager를 만들어 낸다.
- 객체와 테이블을 생성하고 매핑하는법
    - @Entity, @Id 두개의 어노테이션을 이용한다.
    - @Entity는 JPA가 관리하는 객체를 뜻하며
    - @Id는 데이터베이스의 PK와 매핑된다.

    ```java
    @Entity
    public class Member {
    
    @Id
    private Long id;
    }
    ```

- JPA 기본 함수
    - Persistence.createEntityManagerFactory() → EntityManagerFactory를 생성한다.
    - EntityManagerFactory.createEntityManager() → EntityManager를 생성한다.
    - EntityManager.getTransaction() → 트랜잭션을 생성한다.
        - JPA 실행시 트랜잭션 실행, 종료가 필요하다.
    - EntityManager.persist(member) → 객체를 JPA를 통해 저장해서 테이블을 생성한다.

## 3. 영속성 관리 - 내부 동작 방식

### 영속성 컨텍스트

- 엔티티 매니저 팩토리와 엔티티 매니저의 이해

  ![Screen Shot 2023-06-10 at 22.22.56 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/95ff7349-ba52-4f4a-81e5-4650314fa9a1/Screen_Shot_2023-06-10_at_22.22.56_PM.png)

    - 사용자의 요청이 올때마다 엔티티 매니저 팩토리는 엔티티 매니저를 생성하고
    - 엔티티 매니저는 내부적으로 데이터베이스 커넥션을 사용해서 디비를 사용한다.
- 영속성 컨텍스트란?
    - 엔티티를 영구 저장하는 환경이란 뜻
    - EntityManager.persist(entity)  → 엔티티를 영속성 컨텍스트에 저장한다는 것
    - 논리적인 개념이고, 엔티티 매니저를 통해서 접근가능하다.
- 엔티티의 생명주기
    - 비영속 → 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
    - 영속 → 영속성 컨텍스트에 관리되는 상태
        - 코드로는 EntityManager.persist(entity)로 이루어진다.
    - 준영속 → 영속성 컨텍스트에 저장되었다가 분리된 상태
        - 코드로는 EntityManager.detach(entity), EntityManager.remove(entity)로 이루어진다.
    - 삭제 → 삭제된 상태
- 영속성 컨텍스트 이점
    - 1차 캐시
    - 동일성 보장
    - 트랜잭션을 지원하는 쓰기 지연
    - 변경 감지
    - 지연 로딩
- 영속성 컨텍스트의 1차캐시란?
    - 영속성 컨텍스트에는 키(ID) - 밸류(엔티티)로 값을 저장하는 1차 캐시가 있다.
    - 엔티티 조회 시 캐시에 값이 있으면 DB를 거치지않고 바로 값을 반환한다.
    - 엔티티 조회 시 캐시에 값이 없으면 DB의 값을 캐시에 저장 후 값을 반환한다.
- 영속 엔티티의 동일성 보장이란?
    - 동일한 ID의 엔티티 조회시 ==비교의 동일성을 비교해준다.

    ```java
    Member findMember1 = em.find(Member.class, 101L);
    Member findMember2 = em.find(Member.class, 101L);
    
    System.out.println(findMember1==findMember2) // true
    ```

- 엔티티 등록 시 트랜잭션을 지원하는 쓰지 지연이란?

  ![Screen Shot 2023-06-10 at 23.29.16 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8d6a8206-f086-4026-a578-566148d4caea/Screen_Shot_2023-06-10_at_23.29.16_PM.png)

    - persist로 등록 시 바로 쿼리를 날리는 것이아닌
    - 해당 엔티티를 영속성 컨텍스트의 쓰기 지연 SQL저장소와 1차 캐시에 저장한다.
    - 그후 commit()을 날리면 flush되면 DB에 쓰기지연 SQL저장소에 있는 값을 실행한다.
    - 그후 커밋한다.
- 엔티티 수정 시 변경감지란?
    - 엔티티의 값을 변경 시 특정 함수를 사용하지 않아도 객체의 값만 변경해줘도 JPA에서 자동으로 값을 수정해준다.
- 변경감지(Dirty Checking) 동작 원리는?

  ![Screen Shot 2023-06-10 at 23.57.31 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/31824c5e-ca55-41b1-a7a4-95f0752641f2/Screen_Shot_2023-06-10_at_23.57.31_PM.png)

    - 커밋을하면 영속성 컨텍스트에서 flush()를 호출한다.
    - 그 후 1차캐시에 있는  스냅샷과 엔티티를 비교한다.
    - 수정 사항이 있으면 업데이트 쿼리를 쓰기 지연 SQL 저장소에 반영 한다.
    - 그후 해당 쿼리를 통해 DB에 날린다.

### 플러시

- 플러시란?
    - 영속성 컨텍스트의 변경내용을 데이터에비스에 반영하는 것이다.
- 플러시 발생 시 일어나는 것들
    - 변경 감지(Dirty Checking)
    - 수정된 엔티티를 쓰기 지연 SQL저장소에 등록
    - 쓰기 지연 SQL저장소의 쿼리를 데이터베이스에 전송
- 영속성 컨텍스트를 플러시하는 방법
    - em.flush() → 직접 호출
    - 트랜잭션 커밋(tx.commit) → 플러시 자동 호출
    - JPQL 쿼리 실행 → 플러시 자동 호출
- 플러시 옵션으로 호출 타이밍을 정할 수 있다.
- 플러시 특징
    - 영속성 컨텍스트를 비우지 않는다.
    - 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 한다.
    - 트랜잭션이라는 작업 단위가 중요하다.

### 준영속 상태

- 준영속 상태란?
    - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리된것
    - 변경감지(Dirty Checking)과 같은 기능을 사용 못함
- 준영속 상태를 만드는 방법
    - em.detach(entity) → 특정 엔티티만 준영속 상태롤 전환한다.
    - em.clear() → 영속성 컨텍스트를 완전히 초기화한다.
    - em.close() → 영속성 컨텍스트를 종료한다.

## 4. 엔티티 매핑

### 객체와 테이블 매핑

- 엔티티 매핑의 어노테이션 종류
    - 객체와 테이블 매핑 → @Entity, @Table
    - 필드와 컬럼 매핑 → @Column
    - 기본 키 매핑 → @Id
    - 연관관계 매핑 → @ManyToOne, @JoinColumn
- @Entity무엇이고 특징은?
    - @Entity가 붙은 클래스는 JPA가 관리하고, 엔티티라 한다.
    - JPA를 사용해서 테이블과 매핑할 클래스는 @Entity는 필수다.
    - 단 제약조건으로
        - 기본 생성자는 필수이다.
        - final, enum, interface, inner클래스에는 사용불가하다.
        - 저장할 필드에 final 사용 불가하다.
    - name 속성을 통해서 엔티티 이름 지정가능하나 보통 기본값인 클래스명을 사용한다.
- @Table의 속성은
    - name → 매핑할 테이블 이름
    - catalog → 데이터베이스 catalog 매핑
    - schema → 데이터베이스 schema 매핑
    - uniqueConstraints → DDL 생성 시에 유니크 제약 조건 생성

### 데이터베이스 스키마 자동 생성

- JPA는 애플리케이션 생성시점에 DDL을 생성을 지원해준다.
- 운영서버에서 사용하기보단, 로컬에서 주로 사용된다.
- 데이터 스키마 자동생성에 여러 옵션이 있다.
    - persistence.xml의 hibernate.hbm2ddl.auto로 옵션 선택가능하다.
    - 옵션 create → 기존테이블 삭제 후 다시 생성한다.(drop + create)
    - 옵션 create-drop → create와 같으나 종료시점에 테이블 drop한다.
    - 옵션 update → 변경분만 반영한다.(운영에 사용하면 안됨)
    - 옵션 validate → 엔티티와 테이블이 정상 매핑되었는지만 확인한다.
    - 옵션 none → 사용하지 않는다.
- 자동 생성 옵션 사용 단계
    - 운영은 create, create-drop, upate를 사용하면 안된다.
    - 개발 초기에는 create, update
    - 테스트 서버에서는 update, validate
    - 운영 서버는 validate, none
- DDL 생성시 여러 옵션이 가능하다.
    - @Column(nullable = false, length=10)
    - @Table(uniqueConstraints = {@UniqueConstraint( name = "NAME_AGE_UNIQUE",
      columnNames = {"NAME", "AGE"} )})

### 필드와 컬럼 매핑

- 매핑 어노테이션 종류
    - @Column → 컬럼 매핑
    - @Temproal → 날짜 타입 매핑
    - @Enumerated → enum 타입 매핑
    - @Lob → BLOB, CLOB 매핑
    - @Transient → 특정 필드를 컴럼에 매핑하지 않음
- @Column의 여러 옵션
    - name
    - insertable, updateble
    - nullable
    - unique
    - columnDefinition
    - length
    - precision, scale
- @Enumerated 옵션
    - value → db 순서

### 기본 키 매핑

- 기본 키 직접 할당 시 @Id를 사용하면 된다.
- 자동 생성 시 @GeneratedValue를 사용한다.
    - 옵션 strategy =
        - GenerationType.IDENTITY → 기본키 생성을 데이터베이스에 위임한다.
        - GenerationType.SEQUENCE → 데이터베이스 시퀀스 오브젝트 사용, @SequenceGenerator  필요하다.
        - GenerationType.TABLE → 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략이다. @TableGenerator 필요하다.
- 권장하는 식별자 전략
    - Long형 + 대체키 + 키 생성전략 사용
- @GeneratedValue의 strategy = GenerationType.IDENTITY 의 특징
    - 커밋하는 시점이 아닌 persist시점에 인서트 쿼리를 날린다.

## 5. 연관관계 매핑 기초

### 단방향 연관관계

- 객체를 테이블에 맞춰서 모델링 하면
  - 

    ```java
    @Entity
    public class Member {
    
    @Id @GeneratedValue
    private Long id;
    
    @Column(name = "TEAM_ID")
    private Long teamId;
    }
    ```

    - 위의 코드처럼 Id 값으로 매핑 시 여러 단점이 있다.
    - 우선 객체지향적 구조가 아니다.
    - 객체는 참조를 사용해서 연관된 객체를 찾는다.
- 객체지향 모델링

    ```java
    @Entity
    public class Member {
    
    @Id @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    @Column(name = "TEAM_ID")
    private Long teamId;
    }
    ```

    - @ManyToOne을 통해서 하나의 팀의 다수의 멤버에 매핑이된걸 표시해준다.
    - @JoinColumn을 통해서 조인시 매핑할 컬럼을 명시해준다.

### 양방향 연관관계와 연관관계의 주인

- @OneToMany, @ManyToOne으로 양방향 연관관계를 맺어준다.

    ```java
    @Entity
    public class Team{
    @Id @GeneratedValue
     private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<Member>();
    }
    ```

- 객체의 양방향 관계는 서로 다른 단방향 관계 2개다.
- 테이블은 외래키 하나로 두테이블의 연관관계를 관리할 수 있다.
- 객체의 양방향 시 테이블의 외래키를 변경시킬 주인이 필요하다.

  ![Screen Shot 2023-06-12 at 02.44.46 AM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d4175015-0f22-435f-9d78-4c2629513bca/Screen_Shot_2023-06-12_at_02.44.46_AM.png)

- 연관관계의 주인 - 양방향 매핑 규칙은?
    - 객체의 두 관계중 하나를 연관관계의 주인으로 지정해야한다.
    - 연관관계의 주인만이 외래 키를 관리한다.(등록, 수정)
    - 주인이 아닌쪽은 읽기만 가능하다.
    - 주인은 mappedBy속성을 사용하지 못한다.
    - 주인이 아니면 mappedBy 속성으로 주인 지정가능하다.
- 주인이 되는 기준인 → 외래키가 있는 곳을 주인으로 정한다.

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6d44ed68-666c-4a76-b7bf-75710da42e99/Untitled.png)

- 보통 1 대 다  중에서 다쪽으로 정한다.
- 양방향 연관관계 시 주의점
    - 순수 객체상태를 고려해서 양쪽에 값을 설정하자
    - 연관관계 편의 메소드를 만들면 편하다.

    ```java
    @Entity
    public class Member {
    ...
    
    public void changeTeam(Team team){
     this.team = team;
     team.getMembers().add(this);
    }
    ```

- 양방향 매핑시 toString(), lombok, JSON 생성 라이브러리의 무한 루프를 조심해야한다.

## 6. 다양한 연관관계 매핑

### 다대일 N:1

- 다중성
    - 다대일 : @ManyToOne
    - 일대다 : @OneToMany
    - 일대일 : @OneToOne
    - 다대다 : @ManyToMany → 거의 안쓴다.
- 다대일 양방향 매핑
    - 다대일 매핑이 가장 많이 사용하는 연관관계이다.
    - 외래 키가 있는 쪽이 연관간계 주인이다.
    - 양쪽을 서로 참조하도록 개발한다.

### 일대다 1:N

- 일대다 단방향 매핑의 단점
    - 엔티티가 관리하는 외래 키가 다른 테이블에 있다.
    - 연관관계 관리를 위해 추가로 UPDATE SQL실행해야한다.
- 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하는것을 권장한다.

### 일대일 1:1

- 일대일 관계 특징
    - 일대일 관계는 그 반대도 일대일이다.
    - 주 테이블이나 대상 테이블 중에 외래 키 선택 가능하다.
        - 주 테이블에 외래 키
        - 대상 테이블에 외래 키
    - 외래 키에 데이터베이스 유니크 제약조건 추가 가능하다.
- 일대일 - 주 테이블에 외래 키 단방향

  ![Screen Shot 2023-06-13 at 22.59.26 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dc2878ad-ceca-47ee-a405-53579df52dd7/Screen_Shot_2023-06-13_at_22.59.26_PM.png)

    ```java
    @Entity
    public class Member{
    
     @OneToOne
     @JoinColumn(name="LOCKER_ID")
     private Locker locker;
    
    }
    ```

    ```java
    @Entity
    public class Locker{
     @Id @GeneratedValue
     private Long id;
     private String name;
    }
    ```

- 1대1 양방향 시
    - @OneToOne(mappedBy=”locker”) 을 하면된다.

    ```java
    @Entity
    public class Locker{
     @Id @GeneratedValue
     private Long id;
     private String name;
     
     @OneToOne(mappedBy="locker")
     private Member member;
    }
    ```

- 일대일 - 대상 테이블에 외래 키 양방향

  ![Screen Shot 2023-06-13 at 23.44.04 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d24595d2-c23d-4771-9d0b-6f17581b892b/Screen_Shot_2023-06-13_at_23.44.04_PM.png)

- **주 테이블에 외래 키 VS 대상 테이블에 외래 키**
    - **주 테이블에 외래 키**
        - 주 객체가  대상 객체의 참조를 가지는 것처럼 주 테이블에 외래 키를 두고 대상 테이블을 찾음
        - 객체지향 개발자 선호한다.
        - JPA 매핑이 편리하다.
        - 장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능하다.
        - 단점 : 값이 없으면 외래 키에 null 허용한다.
    - **대상 테이블에 외래 키**
        - 대상 테이블에 외래 키가 존재한다.
        - 전통적인 데이터베이스 개발자가 선호한다.
        - 장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지한다.
        - 단점 : 프록시 기능의 한계로 지연로딩으로 설정해도 항상 즉시 로딩된다.

### 다대다

- 다대다는 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야한다.
- @ManyToMany는 사용하지 않는것이 좋다.

## 7. 고급 매핑

### 상속관계 매핑

- 슈퍼타입 서브타입의 논리모델을 실제 물리모텔(테이블)로 구현하는 방법은 크게 세가지이다.
    - 각각 테이블로 변환 → 조인 전략

      ![Screen Shot 2023-06-14 at 21.50.21 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cd96c9c8-b928-454f-98f1-eba47542e3dc/Screen_Shot_2023-06-14_at_21.50.21_PM.png)

    - 통합 테이블로 변환 → 단일 테이블 전략

      ![Screen Shot 2023-06-14 at 21.50.24 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/944fb4eb-e50f-4ba1-a05f-03b48fb47c75/Screen_Shot_2023-06-14_at_21.50.24_PM.png)

    - 서브타입 테이블로 변환 → 구현 클래스마다 테이블 전략

      ![Screen Shot 2023-06-14 at 21.50.28 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a1cdd320-1769-4ff5-a8b9-abdf75e6fa7e/Screen_Shot_2023-06-14_at_21.50.28_PM.png)

- 조인 전략 특징과 구현법
    - 슈퍼 클래스에 @Inheritance(strategy = InheritanceType.JOINED)로 상속관계를 만들 수 있다.
    - 슈퍼클래스에 @DiscriminatorColumn으로 DTYPE을 생성해준다.(디폴트는 엔티티 명)
    - 자식클래스의 @DiscriminatorValue()을 통해서 DTYPE에 들어갈 값을 변경할 수 있다.

    ```java
    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)
    @DiscriminatorColumn
    public class Item{
    ...
    }
    ```

    - 조회 시에는 조인해서 갖고온다.
- 단일 테이블 전략 특징과 구현법
    - 슈퍼 클래스에 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)로 단일테이블로 만들 수 있다.
    - 슈퍼클래스에 @DiscriminatorColumn으로 DTYPE을 생성해준다. 없어도 DTYPE가 생성된다.

    ```java
    @Entity
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn
    public class Item{
    ...
    }
    ```

- 구현 클래스마다 테이블 전략 특징과 구현법
    - 슈퍼 클래스에 @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)로 단일테이블로 만들 수 있다.
    - @DiscriminatorColumn을 사용할 필요가 없다.
- 조인전략의 장,단점
    - 장점 :
        - 테이블이 정규화가 되어있고,
        - 제약조건을 아이템에 맞추어서 할수 있다.
        - 저장공간 효율화한다.
        - 슈퍼클래스로 하나로 여러 처리가 가능해진다.
        - 조인 전략이 정석이다.
    - 단점 :
        - 조회 시 조인을 많이 사용하고, 성능이 저하된다.
        - 조회 쿼리가 복잡하다.
        - 테이블이 많을게 좀 복잡하다.
- 단일 테이블 전략의 장,단점
    - 장점 :
        - 조인이 없어서 조회가 빠르다.
        - 조회 쿼리가 단순하다.
    - 단점 :
        - 자식 엔티티가 매핑한 칼럼은 모두 Null을 허용해야한다.
        - 하나의 테이블이 너무 커질 수 있다.
- 구현 클래스마다 테이블 전략의 장,단점
    - 해당 기능은 추천을 하지 않음
    - 단점 :
        - 각각의 서브테이블에 묶이는게 없다.
        - 자식테이블을 통합해서 쿼리하기 어렵다.
        - 여러 자식테이블을 함께 조회 시 성능이 느리다.

### Mapped Superclass - 매핑 정보 상속

- 부모클래스를 상속받는 자식 클래스에 매핑 정보만 제공할때
    - **@MappedSuperclass를 사용한다.**
    - 직접 생성해서 사용할 일이 없으므로 추상클래스를 권장한다.
    - 주로 등록일, 수정일, 등록자, 수정자 등에 사용된다.

## 프록시와 연관관계 관리

### 프록시

- 프록시 사용법
    - em.getReference() → 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회한다.
- 프록시의 특징

  ![Screen Shot 2023-06-15 at 23.42.42 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/02c6f666-0949-47a6-bcbf-86eef98c18eb/Screen_Shot_2023-06-15_at_23.42.42_PM.png)

    - 실제 클래스를 상속받아서 만들어진다.
    - 실제 클래스와 겉 모양이 같다.
    - 이론상 사용하는 입장에선 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면된다.
    - 프록시 객체는 실제 객체의 참조(target)를 보관한다.
    - 프록시 객체 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.
    -
- 프록시 객체 초기화

  ![Screen Shot 2023-06-15 at 23.46.34 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fa4866af-ad3b-46c4-8689-1655a8dc91cf/Screen_Shot_2023-06-15_at_23.46.34_PM.png)

    - em.getReference(Member.class,”id1”)로 프록시 객체를 갖고온다.
    - getName()을 호출 하는 시점에  프록시객체는 영속성 컨텍스트에 멤버 엔티티를 요청하고
    - 실제 멤버엔티티를 디비에서 갖고오면
    - 프록시의 타겟을 실제 멤버엔티티에 매핑해준다.
    - 해당 과정을 프록시 객체의 초기화라고 한다.
- 프록시의 주요 특징
    - 프록시 객체는 처음 사용할 때 한 번만 초기화 한다.
    - 프록시 객체를 초기화 할때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.
    - 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근가능하다.
    - 프록시 개게는 원본 엔티티를 상속는다. ==비교실패한다.
    - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환한다.
    - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제발생한다. 예외발생한다.
- 프록시 확인 함수들
    - PersistenceUnitUtil.isLoaded(Object entity) → 프록시 인스턴스의 초기화 여부 확인
    - entity.getClass().getName() → 프록시 클랙스 확인
    - org.hibernate.Hibernate.initialize(entity) → 프록시 강제 초기화

### 즉시 로딩과 지연 로딩

- @ManyToOne(fetch = FetchType.LAZY) → 통해서 지연로딩을 설정할 수 있다.
- 지연로딩 시 해당 테이블만 갖고오고 나중에 getTeam()등을 통해 갖고올 시 프록시로 갖고온다.
- 그후 프록시의 특정 함수를 사용할 때 해당 프록시가 초기화 된다.
- Member와 Team을 같이 자주 사용하면 (fetch = FetchType.Eager)을 통해 즉시로딩이 가능하다.
- 즉시 로딩 시 조인해서 한번에 다 갖고온다.
- 실무에선 가급적 지연로딩만 사용하는것이 좋다.
    - 즉시로딩 시 예상하지 못한 SQL이 발생한다.
    - 즉시로딩 시 JPQL에서 N+1 문제를 일으킨다.
    - @ManyToOne, @OneToOne은 기본이 즉시 로딩이므로 → LAZY로 변경해줘야한다.
    - @OneToMany, @ManyToMany는 기본이 지연로딩이다.

### 영속성 전이 : CASCADE와 고아 객체

- 영속성 전이란 →

  ![Screen Shot 2023-06-16 at 01.27.28 AM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c0e63201-2b57-4528-873d-d29361f721e1/Screen_Shot_2023-06-16_at_01.27.28_AM.png)

    - 특정 엔티티를 영속상태로 만들 때 연관된 엔티티도 함꼐 영속 상태로 만들고 싶을때 사용한다.
- @OneToMany(mappedBy=”parent”, cascade=CascadeType.ALL)과 같이 쓴다.
- CASCADE의 종류
    - ALL → 모두 적용
    - PERSIST → 영속
    - REMOVE → 삭제
- 고아 객체 제거란 →
    - 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제한다.
- orphanRemoval=true로 사용이 가능하다.
- 고아객체는 참조하는 곳이 하나일 때 사용해야 한다.

## 9. 값 타입

### 기본값 타입

- JPA의 데이터 타입 분류
    - 엔티티 타입
        - @Entity로 정의하는 객체이다.
        - 데이터가 변해도 식별자로 지속해서 추적 가능하다.
    - 값 타입
        - int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체이다.
        - 식별자가 없고 값만 있으므로 변경 시 추적불가하다.
- 값 타입 분류
    - 기본값 타입
        - 자바 기본 타입 → int, double
        - 래퍼 클래스 → Integer, Long
        - String
    - 임베디드 타입
    - 컬렉션 값 타입
- 기본값 타입 특징
    - String name, int age
    - 생명주기를 엔티티에 의존한다.
    - 값 타입은 공유하면 안된다.

### 임베디드 타입

- 임베디드 타입은 새로운 값 타입을 직접 정의할 수 있다.
- @Embeddable → 값 타입을 정의하는 곳에 표시
- @Embedded → 값 타입을 사용하는 곳에 표시
- 임베디드 타입의 장점
    - 재사용이 높다
    - 응집도가 높다
    - 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있다.
    - 임베디드 타입을 포함한 모든 값 타입은 엔티티에 생명주기를 의존한다.
- 임베디드 타입과 테이블 매핑은 아래의 사진처럼 이루어진다.

![Screen Shot 2023-06-16 at 17.50.35 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3f263b00-78c6-45ec-9ccc-a7f05d19ca70/Screen_Shot_2023-06-16_at_17.50.35_PM.png)

```java
@Entity
public class Member{
...
@Embedded
private Period wokrPeriod;

@Embedded
private Address homeAddress;

...
}

```

```java

@Embeddable
public class Period{

private LocalDateTime startDate;
private LocalDateTime endDate;

...

}
```

```java
@Embeddable
public class Address{

private String city;
private String street;
private String zipcode;

...

}
```

- 임베디드 타입과 테이블 매핑 시
    - 임베디드 타입은 엔티티의 값일 뿐이다.
    - 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
    - 객체와 테이블을 아주 세밀하게 매핑하는것이 가능해진다.
- 한 엔티티에서 같은 값타입을 사용하면 컬럼명이 중복된다.
    - → @AttributeOverides,  @AttributeOveride를 사용해서 컬럼명 속성의 재정의 가능하다.
-