package cheque.handover.services.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllUserDetailList {
    CommonResponse commonResponse = new CommonResponse();
    List<UserDetailResponse> userDetailResponse = new ArrayList<>();
}
