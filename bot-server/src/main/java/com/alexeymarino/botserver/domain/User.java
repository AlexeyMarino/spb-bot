package com.alexeymarino.botserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@RequiredArgsConstructor
public class User {
    private final Long userId;
    private final String userName;

    private ScrollableListWrapper scrollableListWrapper;

}
