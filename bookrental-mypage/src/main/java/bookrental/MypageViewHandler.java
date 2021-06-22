package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenAsked_then_CREATE_1 (@Payload Asked asked) {
        try {
            if (asked.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setAskId(asked.getId());
                mypage.setBookId(asked.getBookId());
                mypage.setStatus(asked.getStatus());
                mypage.setBookPrice(asked.getBookPrice());
                mypage.setAskDate(asked.getAskDate());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaid_then_CREATE_2 (@Payload Paid paid) {
        try {
            if (paid.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setAskId(paid.getId());
                mypage.setBookId(paid.getBookId());
                mypage.setStatus(paid.getStatus());
                //mypage.setPayDate(paid.getPayDate());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentalRegistered_then_CREATE_3 (@Payload RentalRegistered rentalRegistered) {
        try {
            if (rentalRegistered.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setBookId(rentalRegistered.getId());
                mypage.setStatus(rentalRegistered.getStatus());
                mypage.setBookPrice(rentalRegistered.getBookPrice());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookRented_then_UPDATE_1(@Payload BookRented bookRented) {
        try {
            if (bookRented.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByBookId(bookRented.getId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setBookId(bookRented.getId());
                    mypage.setStatus(bookRented.getStatus());
                    mypage.setBookId(bookRented.getBookId());

                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookRentCanceled_then_UPDATE_2(@Payload BookRentCanceled bookRentCanceled) {
        try {
            if (bookRentCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByBookId(bookRentCanceled.getId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setBookId(bookRentCanceled.getId());
                    mypage.setStatus(bookRentCanceled.getStatus());
                    mypage.setBookId(bookRentCanceled.getBookId());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}