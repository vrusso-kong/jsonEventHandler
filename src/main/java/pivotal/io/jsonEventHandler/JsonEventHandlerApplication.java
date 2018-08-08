package pivotal.io.jsonEventHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class JsonEventHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonEventHandlerApplication.class, args);
	}

	@PostMapping(value = "/watson", consumes = "application/json")
    public void handleEvent(@RequestBody String body){
	    System.out.println(body);
	    // PUSH to MS SQL
    }

}
