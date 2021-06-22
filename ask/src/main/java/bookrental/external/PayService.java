
package bookrental.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="pay", url="http://pay:8080")
public interface PayService {

    @RequestMapping(method= RequestMethod.POST, path="/pays")
    public void pay(@RequestBody Pay pay);

    @RequestMapping(method= RequestMethod.POST, path="/pays/{askId}")
    public void payCancel(@RequestBody Pay pay, @PathVariable("bookId") Long bookId);

}