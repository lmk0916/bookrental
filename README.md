# bookrental
## 서비스 시나리오


#### 기능적 요구사항

1. 관리자는 도서를 등록할 수 있다.
2. 고객이 도서를 선택해 예약하면 결제가 진행된다.
3. 예약이 결제되면 도서의 예약 가능 여부가 변경된다.
4. 도서가 예약 불가 상태로 변경되면 예약이 확정(대여가능)된다.
5. 고객은 예약을 취소할 수 있다.
6. 예약이 취소되면 결제가 취소되고 도서의 예약 가능 여부가 변경된다.
7. 고객은 도서의 예약가능여부를 확인할 수 있다.


#### 비기능적 요구사항

##### 트랜잭션

1. 도서 예약은 결제가 취소된 경우 확정이 불가능 해야한다.(Sync 호출)
2. 결제가 완료 되지 않은 예약 건은 예약이 성립되지 않는다.(Sync 호출)
3. 예약과 결제는 동시에 진행된다.(Sync 호출)
4. 예약 취소와 결제 취소는 동시에 진행된다.(Sync 호출)

##### 장애격리

1. 도서 시스템이 수행되지 않더라도 예약 / 결제는365일 24시간 받을 수 있어야 한다.(Async 호출-event-driven)
2. 도서 시스템이 과중 되면 예약 / 결제를 받지 않고 결제 취소를 잠시 후에 하도록 유도한다.(Circuit breaker, fallback)
3. 결제가 취소되면 도서의 예약 취소가 확정되고, 도서의 예약 가능 여부가 변경된다.(Circuit breaker, fallback)
 
##### 성능
1. 고객은 도서 상태를 확인할 수 있다.(CQRS)
2. 예약/결제 취소 정보가 변경 될 때마다 도서 예약 가능 여부가 변경 될 수 있어야 한다.(Event Driven)

## 분석/설계

### Event Storming 결과

- MSAEz 로 모델링한 이벤트스토밍 결과:
 http://www.msaez.io/#/storming/EdLoXS5GivQ3D5rdMVF8LW8AwHR2/mine/0cb8e25511a4990af45763023274e8a6

#### 이벤트 도출
![image](https://user-images.githubusercontent.com/84304021/122895802-698da880-d383-11eb-9271-2e098abd3591.png)

#### Actor, Command, Aggregate 추가
![image](https://user-images.githubusercontent.com/84304021/122902412-5e3d7b80-d389-11eb-8724-412489ac13a5.png)

#### Bounded Context 로 묶기
![image](https://user-images.githubusercontent.com/84304021/122902870-d0ae5b80-d389-11eb-8218-11a20fc005e8.png)

#### Policy 부착 (괄호는 수행주체)
![image](https://user-images.githubusercontent.com/84304021/122903668-97c2b680-d38a-11eb-8029-e0b112f1b01a.png)

#### Policy의 이동과 Context Mapping (점선은 Pub/Sub, 실선은 Req/Resp)
![image](https://user-images.githubusercontent.com/84304021/122904378-3ea75280-d38b-11eb-8659-b6e936f3f83f.png)

#### 완성된 1차 모형
![image](https://user-images.githubusercontent.com/84304021/122904724-8e861980-d38b-11eb-95c1-eb2c795a9a33.png)

#### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증
![image](https://user-images.githubusercontent.com/84304021/122905820-84b0e600-d38c-11eb-9aa6-5e867dcfdb56.png)
```
Red
- 고객이 도서를 선택해 예약을 진행한다. (OK)
- 예약 시 자동으로 결제가 진행된다. (OK)
- 결제가 성공하면 도서가 예약불가 상태가 된다. (OK)
- 도서 상태 변경 시 예약이 확정(대여가능)상태가 된다. (OK) 
Blue
- 고객이 예약/결제를 취소한다. (OK)
- 예약/결제 취소 시 자동 예약/결제 취소된다. (OK)
- 결제가 취소되면 도서가 예약가능 상태가 된다. (OK)
```
#### 모델수정
![image](https://user-images.githubusercontent.com/84304021/122906493-2c2e1880-d38d-11eb-8d10-ed4ea0ca792e.png)
```
- View Model 추가(CQRS 적용)
- 수정된 모델은 모든 요구사항을 커버함
```
#### 비기능 요구사항에 대한 검증
![image](https://user-images.githubusercontent.com/84304021/122906872-91820980-d38d-11eb-9d51-d7e8780d7666.png)
```
- 도서 등록 서비스를 예약/결제 서비스와 격리하여 도서 등록 서비스 장애 시에도 예약이 가능
- 도서가 예약 불가 상태일 경우 예약 확정이 불가함
- 먼저 결제가 이루어진 도서에 대해서는 예약을 불가 하도록 함
```
### 헥사고날 아키텍처 다이어그램 도출
![image](https://user-images.githubusercontent.com/84304021/122907341-fccbdb80-d38d-11eb-8e20-314328a6a57b.png)
```
- Chris Richardson, MSA Patterns 참고하여 Inbound adaptor와 Outbound adaptor를 구분함
- 호출관계에서 PubSub 과 Req/Resp 를 구분함
- 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐
```
## 구현
