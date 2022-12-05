package com.alexeymarino.botserver.cache;

import com.alexeymarino.botserver.botapi.BotState;
import com.alexeymarino.botserver.domain.User;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);


    User getUserProfileData(long userId);
}
