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

#### 이벤트 도출
![image](https://user-images.githubusercontent.com/84304021/122895802-698da880-d383-11eb-9271-2e098abd3591.png)

#### Actor, Command, Aggregate 추가
![image](https://user-images.githubusercontent.com/84304021/122902412-5e3d7b80-d389-11eb-8724-412489ac13a5.png)

#### Bounded Context 로 묶기
![image](https://user-images.githubusercontent.com/84304021/122902870-d0ae5b80-d389-11eb-8218-11a20fc005e8.png)

#### Policy 부착 (괄호는 수행주체)


#### Policy의 이동과 Context Mapping (점선은 Pub/Sub, 실선은 Req/Resp)




```
*****
