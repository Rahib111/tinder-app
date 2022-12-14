package finding.concretes;

import finding.abstracts.NonLikedUserFind;
import model.Like;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

import static constants.findingConstants.FindingLayerConstants.serviceLike;
import static constants.findingConstants.FindingLayerConstants.serviceUser;

public class NonLikedUserFindManager implements NonLikedUserFind {
    private static final NonLikedUserFindManager nonLikedUserFindManager = new NonLikedUserFindManager();

    @Override
    public List<Integer> getUser(int id) {
        return serviceUser
                .getAllUser()
                .stream()
                .filter(user -> serviceUser.findUserIdByEmail(user.getEmail()) != id)
                .map(user -> serviceUser.findUserIdByEmail(user.getEmail()))
                .filter(idX -> !serviceLike.getAllSigned(id).stream()
                        .map(Like::getUserTo)
                        .collect(Collectors.toSet())
                        .contains(id))
                .collect(Collectors.toList());
    }

    public static List<Integer> getNonLikedUser(int id) {
        return nonLikedUserFindManager.getUser(id);
    }
}
