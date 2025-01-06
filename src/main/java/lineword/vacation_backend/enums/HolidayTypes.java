package lineword.vacation_backend.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum HolidayTypes {
    ANNUAL_LEAVE("연가"),
    OFFICIAL_LEAVE("공가");

    private final String type;

    public boolean isEqual(String givenType){
        return Objects.equals(this.getType(), givenType);
    }
}
