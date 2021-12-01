package hr.trio.realestatetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {

    private Object data;
    private String error;

    public ApiResponse(Object data) {
        this.data = data;
    }

    public ApiResponse(String error) {
        this.error = error;
    }

}
