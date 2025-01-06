package lineword.vacation_backend.service;

import lineword.vacation_backend.domain.Event;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.domain.Notification;
import lineword.vacation_backend.enums.NotificationTemplates;
import lineword.vacation_backend.exception.EventNotFoundException;
import lineword.vacation_backend.model.ApprovalEventCheckRequestDto;
import lineword.vacation_backend.model.ApprovalEventCheckResponseDto;
import lineword.vacation_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ApprovalNotificationService approvalNotificationService;

    public void publishApprovalNotification(String eventType, HolidayRequest request) {
        Event event = findByEventType(eventType);
        String message = NotificationTemplates.HOLIDAY_APPROVAL.getTemplate(request);

        List<Notification> createdNotification = request.getApprovers().stream()
                .map(receiver -> approvalNotificationService.createNotice(event, message, receiver, request)).toList();

        approvalNotificationService.saveNotificationAll(createdNotification);
    }

    public List<ApprovalEventCheckResponseDto> checkApprovalNotification(ApprovalEventCheckRequestDto requestDto) {
        // 지속적으로 DB에서 receiver client의 id와 일치하는 approvalNotification 확인
        // 영속성 컨텍스트 캐시 이용 목적
        return approvalNotificationService.findAllByReceiverId(requestDto.getUserId())
                .stream().map(notification-> ApprovalEventCheckResponseDto.builder()
                        .createdAt(notification.getCreatedAt())
                        .message(notification.getMessage())
                        .notificationId(notification.getId())
                        .build()).toList();
    }

    public Event findByEventType(String eventType) {
        return eventRepository.findByEventType(eventType).orElseThrow(() -> new EventNotFoundException(eventType));
    }
}
