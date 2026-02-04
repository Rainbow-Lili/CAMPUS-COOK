package tg.univlome.epl.cookplusserver.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import tg.univlome.epl.cookplusserver.dao.NotificationDAO;
import tg.univlome.epl.cookplusserver.entities.Notification;
import tg.univlome.epl.cookplusserver.exceptions.EntityNotFoundException;

@Stateless
public class NotificationService {

    @Inject
    private NotificationDAO notificationDAO;

    public List<Notification> findAll() {
        return notificationDAO.findAll();
    }

    public Notification findById(Long id) {
        return notificationDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification", id));
    }

    public List<Notification> findByUtilisateurId(Long utilisateurId) {
        return notificationDAO.findByUtilisateurId(utilisateurId);
    }

    public List<Notification> findUnreadByUtilisateurId(Long utilisateurId) {
        return notificationDAO.findUnreadByUtilisateurId(utilisateurId);
    }

    public Notification save(Notification notification) {
        return notificationDAO.save(notification);
    }

    public void deleteById(Long id) {
        findById(id);
        notificationDAO.deleteById(id);
    }

    public void markAsRead(Long id) {
        Notification notification = findById(id);
        notification.setLu(Boolean.TRUE);
        notificationDAO.save(notification);
    }
}
