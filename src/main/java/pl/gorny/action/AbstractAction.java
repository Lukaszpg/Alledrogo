package pl.gorny.action;

import org.slf4j.Logger;
import pl.gorny.dto.ResponseDto;

public abstract class AbstractAction {

    protected ResponseDto responseDto = new ResponseDto();

    protected String json;

    protected Logger logger;

    public abstract void execute();

    public void setJson(String json) {
        this.json = json;
    }

    public ResponseDto getResponseDto() {
        return this.responseDto;
    }
}
