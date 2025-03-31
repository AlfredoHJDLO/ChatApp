package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.BlockedUsers;

public interface BlockedUserDAO {
    public boolean addBlockedUser(BlockedUsers blockedUser);
    public boolean isBlocked(String mac_address);
    public boolean removeBlockedUser(String mac_address);
}
