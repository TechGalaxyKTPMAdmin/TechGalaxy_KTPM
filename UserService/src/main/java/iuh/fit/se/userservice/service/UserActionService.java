package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.entities.UserAction;
import iuh.fit.se.userservice.entities.enumeration.ActionType;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

public interface UserActionService {
    UserAction saveAction(UserAction userAction);

    List<UserAction> getActionsByUserId(String userId);

    UserAction getActionsByUserIdAndType(String userId, ActionType actionType);
}
