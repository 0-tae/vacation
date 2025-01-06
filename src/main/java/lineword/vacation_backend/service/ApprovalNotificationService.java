package lineword.vacation_backend.service;

import lineword.vacation_backend.domain.Event;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.domain.Notification;
import lineword.vacation_backend.repository.ApprovalNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApprovalNotificationService {
    private final ApprovalNotificationRepository approvalNotificationRepository;

    public Notification createNotice(Event event, String message, Member receiver, HolidayRequest relatedRequest){
        return Notification.builder()
                .event(event)
                .message(message)
                .receiver(receiver)
                .sender(relatedRequest.getMember())
                .build();
    }

    public Notification saveNotification(Notification notification){
        return approvalNotificationRepository.save(notification);
    }

    public void saveNotificationAll(Iterable<Notification> notices){
        approvalNotificationRepository.saveAll(notices);
    }

    public List<Notification> findAllBySenderId(int senderId){
        return this.approvalNotificationRepository.findAllBySenderId(senderId);
    }

    public List<Notification> findAllByReceiverId(int receiverId){
        return this.approvalNotificationRepository.findAllByReceiverId(receiverId);
    }
}
