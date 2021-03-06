//@@author ifalluphill

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.api.services.calendar.model.Event;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DailyScheduleShownChangedEvent;
import seedu.address.commons.events.ui.ResetDirectionsEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;
import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;


/**
* Shows the user the events for the current day
*/
public class ShowScheduleCommand extends Command {

    public static final String COMMAND_WORD = "show-schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all events for a specified day. \n"
            + "Parameters: "
            + PREFIX_SCHEDULE_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "Tomorrow ";

    public static final String MESSAGE_NO_EVENTS = "No events found.";
    public static final String MESSAGE_ERROR = "Unable to retrieve events. Please try again later.";
    private final LocalDate localDate;


    public ShowScheduleCommand(LocalDate localDate) {
        this.localDate = localDate;
    };


    @Override
    public CommandResult execute() {
        requireNonNull(model);

        User user = model.getLoggedInUser();

        try {
            List<String> dailyEvents = OAuthManager.getDailyEventsAsStringList(user, localDate);
            String dailyEventsAsString = String.join("\n", dailyEvents);

            if (dailyEventsAsString.length() == 0) {
                dailyEventsAsString = MESSAGE_NO_EVENTS;
            }

            //@@author jaronchan
            List<Event> dailyEventsList = OAuthManager.getDailyEvents(user, localDate);
            EventsCenter.getInstance().post(new DailyScheduleShownChangedEvent(dailyEventsList));
            EventsCenter.getInstance().post(new ResetDirectionsEvent());
            EventsCenter.getInstance().post(new UpdateNumberOfButtonsEvent(dailyEvents.size() - 1));

            //@@author ifalluphill
            return new CommandResult(dailyEventsAsString);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }

    }

}

//@@author
