package pl.gorny.action;

import pl.gorny.dto.ResponseDto;

public abstract class AbstractAction {

    protected ResponseDto responseDto = new ResponseDto();

    protected String json;

    public abstract void execute();

    public void setJson(String json) {
        this.json = json;
    }

    public ResponseDto getResponseDto() {
        return this.responseDto;
    }
}
