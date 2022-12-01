package com.alexeymarino.botserver.cache;

import com.alexeymarino.botserver.botapi.BotState;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);
}
