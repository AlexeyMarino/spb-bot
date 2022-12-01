package com.alexeymarino.botserver.cache;

import com.alexeymarino.botserver.botapi.BotState;
import java.util.HashMap;
import java.util.Map;

public class UserDataCache implements DataCache{
    private final Map<Long, BotState> usersBotStates = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        return usersBotStates.get(userId);
    }
}
