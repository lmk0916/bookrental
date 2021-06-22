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
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_Rent(@Payload Paid paid){

        if(paid.isMe()){
            System.out.println("##### listener Rent : " + paid.toJson());

            Optional<Book> optional = bookRepository.findById(paid.getBookId());
            Book book = optional.get();
            book.setAskId(paid.getAskId());
            book.setStatus("RENTED");

            bookRepository.save(book);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_RentCancel(@Payload PayCanceled payCanceled){

        if(payCanceled.isMe()){
            System.out.println("##### listener RentCancel : " + payCanceled.toJson());
            Optional<Book> optional = bookRepository.findById(payCanceled.getBookId());
            Book book = optional.get();
            book.setAskId(null);
            book.setStatus("WAITING");

            bookRepository.save(book);
        }


    }

}
