package lineword.vacation_backend.event;


import lineword.vacation_backend.domain.Event;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HolidayEventListener {
    @EventListener
    public void handleHolidayEvent(Event event){
        String eventType = event.getEventType();


    }
}
