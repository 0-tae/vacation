package lineword.vacation_backend.model.response_standard;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter @ToString
public class BackendApiResponseDto<T> {

    @Builder.Default
    int resultCode = 200;

    @Builder.Default
    String resultMessage ="ok";

    T item;

    public static <T> BackendApiResponseDto body(T item){
        return BackendApiResponseDto.builder()
                .item(item)
                .build();
    }
}
