package deeroot.deeroot_shop.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponseDto {

    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;

    public String toString(){
        return "Session Id: " + sessionId + "\n Session Url: " + sessionUrl + "\n Status: " + status + "\n Message: " + message;
    }
}
