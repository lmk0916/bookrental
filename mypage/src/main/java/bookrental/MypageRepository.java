package bookrental;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypageRepository extends CrudRepository<Mypage, Long> {

    List<Mypage> findByBookId(Long bookId);


        void deleteByAskId(Long askId);
}