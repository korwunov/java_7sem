package org.users;

import io.reactivex.Observable;

import java.util.*;

public class UserFriend {
    public int userId;
    public int friendId;

    public UserFriend(int userId, int friendsIds) {
        this.userId = userId;
        this.friendId = friendsIds;
    }

    public int getUserId() { return userId; }

    public Integer getFriendId() { return friendId; }

//    public void addFriend(int userId) {
//        this.friendsIds.add(userId);
//    }
//
//    public static Map<Integer, UserFriend> generateUsersAndFriends(Integer[] userIds) {
//        Map<Integer, UserFriend> map = new HashMap<>();
//        for (Integer id : userIds) {
//            List<Integer> friends = new ArrayList<>();
//            for (int i = 0; i < new Random().nextInt(1, 5); i++) { friends.add((int) (Math.random() * 100)); }
//            UserFriend user = new UserFriend(id, friends);
//            map.put(id, user);
//        }
//        return map;
//    }

    public static Observable<UserFriend> getFriends(int userId) {
        return Observable.fromArray(
                new UserFriend(userId, (int) (Math.random() * 100)),
                new UserFriend(userId, (int) (Math.random() * 100)),
                new UserFriend(userId, (int) (Math.random() * 100))
        );
    }
    
    public static void main(String[] args) {
        Integer[] userIds = {1, 2, 3, 4, 5};
//        Map<Integer, UserFriend> map = generateUsersAndFriends(userIds);
        Observable<Integer> userIdStream = Observable.fromArray(userIds);

        Observable<UserFriend> userFriendsStream = userIdStream.flatMap(UserFriend::getFriends);

        userFriendsStream.subscribe(userFriend -> {
            System.out.println("User: " + userFriend.getUserId() + ", Friends: " + userFriend.getFriendId());
        });
    }
}