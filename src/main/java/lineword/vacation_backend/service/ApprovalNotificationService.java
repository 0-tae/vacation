package lineword.vacation_backend.service;

import lineword.vacation_backend.domain.Event;
import lineword.vacation_backend.domain.HolidayRequest;
import lineword.vacation_backend.domain.Member;
import lineword.vacation_backend.domain.ApprovalNotification;
import lineword.vacation_backend.repository.ApprovalNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApprovalNotificationService {
    private final ApprovalNotificationRepository approvalNotificationRepository;

    public ApprovalNotification createNotice(Event event, String message, Member receiver, HolidayRequest relatedRequest){
        return ApprovalNotification.builder()
                .event(event)
                .message(message)
                .receiver(receiver)
                .sender(relatedRequest.getMember())
                .relatedRequest(relatedRequest)
                .build();
    }

    public ApprovalNotification saveNotification(ApprovalNotification approvalNotification){
        return approvalNotificationRepository.save(approvalNotification);
    }

    public void saveNotificationAll(Iterable<ApprovalNotification> notices){
        approvalNotificationRepository.saveAll(notices);
    }

    public List<ApprovalNotification> findAllBySenderId(int senderId){
        return this.approvalNotificationRepository.findAllBySenderId(senderId);
    }

    public List<ApprovalNotification> findAllByReceiverId(int receiverId){
        return this.approvalNotificationRepository.findAllByReceiverId(receiverId);
    }
}
