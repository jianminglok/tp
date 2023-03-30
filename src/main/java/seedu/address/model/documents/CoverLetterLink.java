package seedu.address.model.documents;

import seedu.address.model.person.InternshipApplicationAttribute;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Represents a link to a cover letter in the internship application.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoverLetterLink(String)}
 */
public class CoverLetterLink extends InternshipApplicationAttribute {

    public static final String MESSAGE_CONSTRAINTS = "Link to cover letter should be in the form http://domain/path "
            + "or https://domain/path";
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" + DOMAIN_LAST_PART_REGEX;

    public final String value;

    /**
     * Constructs an {@code CoverLetter}.
     *
     * @param coverLetterLink A valid link to a cover letter.
     */
    public CoverLetterLink(String coverLetterLink) {
        requireNonNull(coverLetterLink);
        checkArgument(isValidCoverLetterLink(coverLetterLink), MESSAGE_CONSTRAINTS);
        value = coverLetterLink;
    }

    /**
     * Returns if a given string is a valid link to a cover letter.
     */
    public static boolean isValidCoverLetterLink(String test) {
        requireNonNull(test);
        try {
            URI uri = new URL(test).toURI();
            String domain = uri.getHost();
            if (isNull(domain)) {
                return false;
            }
            return test.matches("^(https?)://.*$") && domain.matches(DOMAIN_REGEX);
        } catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CoverLetterLink // instanceof handles nulls
                && value.equals(((CoverLetterLink) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
