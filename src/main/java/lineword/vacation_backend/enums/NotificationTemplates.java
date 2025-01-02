package lineword.vacation_backend.enums;

import lineword.vacation_backend.domain.HolidayRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;


@AllArgsConstructor
public enum NotificationTemplates {

    HOLIDAY_APPROVAL("${sender}님이 휴가에 대한 승인을 요청하였습니다.");

    private final String template;

    public String getTemplate(HolidayRequest holidayRequest) {
        Map<String, String> values = Map.of(
                "sender", holidayRequest.getMember().getAlias()
        );

        return applyTemplate(template, values);
    }

    private String applyTemplate(String template, Map<String, String> values){
        return StringSubstitutor.replace(template, values);
    }
}
