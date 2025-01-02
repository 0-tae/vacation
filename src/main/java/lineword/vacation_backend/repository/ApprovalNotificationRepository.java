package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.ApprovalNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalNotificationRepository extends JpaRepository<ApprovalNotification,Integer> {
    @Query("SELECT n FROM ApprovalNotification n WHERE n.sender.id = :senderId")
    List<ApprovalNotification> findAllBySenderId(@Param("senderId") int senderId);


    @Query("SELECT n FROM ApprovalNotification n WHERE n.receiver.id = :receiverId")
    List<ApprovalNotification> findAllByReceiverId(@Param("receiverId") int receiverId);
}
