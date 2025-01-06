package lineword.vacation_backend.repository;

import lineword.vacation_backend.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalNotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("SELECT n FROM Notification n WHERE n.sender.id = :senderId")
    List<Notification> findAllBySenderId(@Param("senderId") int senderId);


    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId")
    List<Notification> findAllByReceiverId(@Param("receiverId") int receiverId);
}
