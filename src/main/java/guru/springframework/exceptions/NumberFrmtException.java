package guru.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumberFrmtException extends NumberFormatException {

    public NumberFrmtException() {
        super();
    }


    public NumberFrmtException(String message) {
        super(message);
    }

}
