package pl.gorny.action;

import org.slf4j.Logger;
import pl.gorny.dto.ResponseDto;

public abstract class AbstractAction<T> {

    protected ResponseDto responseDto;

    protected String json;

    protected Logger logger;

    protected T dto;

    public abstract void execute();

    public void setJson(String json) {
        this.json = json;
    }

    public ResponseDto getResponseDto() {
        return this.responseDto;
    }
}
