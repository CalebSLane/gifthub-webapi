package ca.csl.gifthub.web.api.modules.error;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/errors/test" }, produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class TestError {

    @GetMapping(value = "/null")
    public void testNull() {
        throw new NullPointerException();
    }

}
