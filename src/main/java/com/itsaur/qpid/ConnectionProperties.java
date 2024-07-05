package com.itsaur.qpid;

import java.util.Objects;

public record ConnectionProperties(String url, String username, String password) {
    public ConnectionProperties {
        Objects.requireNonNull(url);
    }
}
