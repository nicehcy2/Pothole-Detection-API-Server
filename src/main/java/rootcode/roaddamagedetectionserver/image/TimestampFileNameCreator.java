package rootcode.roaddamagedetectionserver.image;

import org.springframework.stereotype.Component;

@Component
public class TimestampFileNameCreator implements FileNameCreator {
    @Override
    public String createFileName() {
        return String.valueOf(System.currentTimeMillis());
    }
}
