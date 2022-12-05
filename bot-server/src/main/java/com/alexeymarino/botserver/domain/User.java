package com.alexeymarino.botserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    private String userName;
    private final Long userId;

    private ScrollableListWrapper scrollableListWrapper;

}
