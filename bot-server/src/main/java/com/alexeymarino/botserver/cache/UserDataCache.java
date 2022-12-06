package com.alexeymarino.botserver.cache;

import com.alexeymarino.botserver.botapi.BotState;
import com.alexeymarino.botserver.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserDataCache implements DataCache {
    private final Map<Long, BotState> usersBotStates = new HashMap<>();
    private final Map<Long, User> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        return usersBotStates.get(userId);
    }

    @Override
    public User getUserProfileData(long userId) {
        return usersProfileData.get(userId);
    }

    @Override
    public Boolean isUserContain(long userId) {
        return usersProfileData.containsKey(userId);
    }

    @Override
    public void add(User user) {
        usersProfileData.put(user.getUserId(), user);
    }
}
