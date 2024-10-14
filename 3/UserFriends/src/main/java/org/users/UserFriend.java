package org.users;

import io.reactivex.Observable;

public class UserFriend {
    public int userId;
    public int friendId;

    public UserFriend(int userId, int friendsId) {
        this.userId = userId;
        this.friendId = friendsId;
    }

    public int getUserId() { return userId; }

    public Integer getFriendId() { return friendId; }

    public static Observable<UserFriend> getFriends(int userId) {
        return Observable.fromArray(
                new UserFriend(userId, (int) (Math.random() * 100)),
                new UserFriend(userId, (int) (Math.random() * 100)),
                new UserFriend(userId, (int) (Math.random() * 100))
        );
    }
    
    public static void main(String[] args) {
        Integer[] userIds = {1, 2, 3, 4, 5};
        Observable<Integer> userIdStream = Observable.fromArray(userIds);

        Observable<UserFriend> userFriendsStream = userIdStream.flatMap(UserFriend::getFriends);

        userFriendsStream.subscribe(userFriend -> {
            System.out.println("User: " + userFriend.getUserId() + ", Friends: " + userFriend.getFriendId());
        });
    }
}