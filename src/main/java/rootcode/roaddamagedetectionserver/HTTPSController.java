package rootcode.roaddamagedetectionserver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTTPSController {

    @GetMapping("/")
    public ResponseEntity<Void> home() {
        return new ResponseEntity<>(HttpStatus.OK);  // 200 OK 상태만 반환
    }

    @GetMapping("/cicd/test")
    public ResponseEntity<Void> cicd() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}