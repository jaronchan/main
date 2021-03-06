package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.DuplicateUserException;

//@@author kaisertanqr
/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User DEFAULT_USER = new User(new Username("username"), new Password("pass"));
    public static final User SLAP = new User(new Username("slap"), new Password("pass"));
    public static final User LONG = new User(new Username("long"), new Password("pass"));
    public static final User KARA = new User(new Username("kara"), new Password("pass"));
    public static final User DANY = new User(new Username("dany"), new Password("pass"));
    public static final User RACH = new User(new Username("rach"), new Password("pass"));
    public static final User RICK = new User(new Username("rick"), new Password("pass"));

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UserDatabase} with all the typical users.
     */
    public static UserDatabase getTypicalUserDatabase() {
        UserDatabase ud = new UserDatabase();
        for (User user: getTypicalUsers()) {
            try {
                ud.addUser(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        return ud;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(DEFAULT_USER, SLAP, LONG, KARA, DANY));
    }

}
