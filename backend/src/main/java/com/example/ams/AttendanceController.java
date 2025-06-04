package com.example.ams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceSessionRepository sessionRepo;
    @Autowired
    private AttendanceLogRepository logRepo;
    @Autowired
    private SubscriberRepository subscriberRepo;
    @Autowired
    private OrganizationRepository organizationRepo;

    @PostMapping("/entities/{entityId}/sessions")
    public AttendanceSession createSession(@PathVariable Long entityId, @RequestBody AttendanceSession session) {
        Organization org = organizationRepo.findById(entityId).orElseThrow();
        session.setOrganization(org);
        return sessionRepo.save(session);
    }

    @PostMapping("/attendance/log")
    public AttendanceLog logAttendance(@RequestParam Long sessionId, @RequestParam String cardUid) {
        AttendanceSession session = sessionRepo.findById(sessionId).orElseThrow();
        Subscriber sub = subscriberRepo.findByCardUid(cardUid).orElseThrow();
        AttendanceLog log = new AttendanceLog();
        log.setSession(session);
        log.setSubscriber(sub);
        return logRepo.save(log);
    }

    @GetMapping("/entities/{entityId}/sessions/{sessionId}/absentees")
    public List<Subscriber> getAbsentees(@PathVariable Long entityId, @PathVariable Long sessionId) {
        Organization org = organizationRepo.findById(entityId).orElseThrow();
        List<Subscriber> all = subscriberRepo.findAll().stream().filter(s -> s.getOrganization().getId().equals(entityId)).toList();
        List<Subscriber> present = logRepo.findAll().stream()
                .filter(l -> l.getSession().getId().equals(sessionId))
                .map(AttendanceLog::getSubscriber)
                .collect(Collectors.toList());
        return all.stream().filter(s -> !present.contains(s)).collect(Collectors.toList());
    }
}
