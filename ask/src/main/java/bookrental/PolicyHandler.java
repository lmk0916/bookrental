package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    AskRepository askRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookRented_UpdateStatus(@Payload BookRented bookRented){

        if(bookRented.isMe()){
            System.out.println("##### wheneverBookRented_UpdateStatus : " + bookRented.toJson());

            Optional<Ask> optional = askRepository.findById(bookRented.getAskID());
            Ask ask = optional.get();
            ask.setStatus(bookRented.getStatus());

            askRepository.save(ask);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookRentCanceled_UpdateStatus(@Payload BookRentCanceled bookRentCanceled){

        if(bookRentCanceled.isMe()){
            System.out.println("##### wheneverBookRentCanceled_UpdateStatus : " + bookRentCanceled.toJson());

            Optional<Ask> optional = askRepository.findById(bookRentCanceled.getAskId());
            Ask ask = optional.get();
            ask.setStatus(bookRentCanceled.getStatus());

            askRepository.save(ask);

        }
    }

}
