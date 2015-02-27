package pl.ioki.releasemonitor.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Data not ready yet, request has been queued")
public class DataNotReadyYetException extends RuntimeException {
}
