package ca.csl.gifthub.web.api.modules.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ErrorDTO {
    private String error = "error";

    public ErrorDTO(String msg) {
        this.error = msg;
    }
}
