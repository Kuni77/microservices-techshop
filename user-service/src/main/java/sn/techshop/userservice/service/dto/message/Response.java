package sn.techshop.userservice.service.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

/*
Reponse de notre api toutes les reponses de nos services doivent respecter ce format de données

*/
public class Response<T> {
    private HttpStatus status;
    private T donnees;
    private String message;
    private Object errors;
    private Object metaData;

    public  static <T> Response<T> ok(){
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        return  response;
    }

    public  static <T> Response<T> create(){
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.CREATED);
        return  response;
    }
    public static <T> Response<T> accessDenied(){
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.FORBIDDEN);
        return  response;
    }

    public static <T> Response<T> exception(){
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.UPGRADE_REQUIRED);
        return  response;
    }

    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        return  response;
    }

    public static <T> Response<T> wrongCredentials() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.LOCKED);
        return  response;
    }
}
