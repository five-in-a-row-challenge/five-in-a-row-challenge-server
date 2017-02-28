package com.gomoku.repository.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * Player immutable class.
 *
 * @author zeldan
 *
 */
public class Player {

    @Id
    private String userName;

    private String networkAddress;

    public Player() {
    }

    public Player(final String userName, final String networkAddress) {
        this.userName = userName;
        this.networkAddress = networkAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return Objects.equals(userName, other.getUserName());
    }

}
