package org.sonar.swagger.checks.util;

import com.google.common.base.Objects;
import com.google.common.collect.Ordering;
import org.hamcrest.Matcher;

import java.util.*;

import static org.junit.Assert.assertThat;

public class CheckVerifier {

	private final Iterator<TestIssue> iterator;
	private TestIssue current;

	private static final Comparator<TestIssue> ORDERING = (left, right) -> {
		if (Objects.equal(left.starLine(), right.starLine())) {
			return left.startColumn().compareTo(right.startColumn());
		} else if (left.starLine() == null) {
			return -1;
		} else if (right.starLine() == null) {
			return 1;
		} else {
			return left.starLine().compareTo(right.starLine());
		}
	};

	private CheckVerifier(Collection<TestIssue> testIssues) {
		iterator = Ordering.from(ORDERING).sortedCopy(testIssues).iterator();
	}

	public static CheckVerifier verify(Collection<TestIssue> testIssues) {
		return new CheckVerifier(testIssues);
	}

	public CheckVerifier next() {
		if (!iterator.hasNext()) {
			throw new AssertionError("\nExpected issue");
		}
		current = iterator.next();
		return this;
	}

	public void noMore() {
		if (iterator.hasNext()) {
			TestIssue next = iterator.next();
			throw new AssertionError("\nNo more issues expected\ngot: at line " + next.starLine());
		}
	}

	private void checkStateOfCurrent() {
		if (current == null) {
			throw new IllegalStateException("Prior to this method you should call next()");
		}
	}

	public CheckVerifier startAtLine(Integer expectedLine) {
		checkStateOfCurrent();
		if (!Objects.equal(expectedLine, current.starLine())) {
			throw assertionError(expectedLine, current.starLine());
		}
		return this;
	}

	public CheckVerifier startAtColumn(Integer expectedColumn) {
		checkStateOfCurrent();
		if (!Objects.equal(expectedColumn, current.startColumn())) {
			throw assertionError(expectedColumn, current.startColumn());
		}
		return this;
	}

	public CheckVerifier endAtLine(Integer expectedLine) {
		checkStateOfCurrent();
		if (!Objects.equal(expectedLine, current.endLine())) {
			throw assertionError(expectedLine, current.endLine());
		}
		return this;
	}

	public CheckVerifier endAtColumn(Integer expectedColumn) {
		checkStateOfCurrent();
		if (!Objects.equal(expectedColumn, current.endColumn())) {
			throw assertionError(expectedColumn, current.endColumn());
		}
		return this;
	}

	public CheckVerifier withMessage(String expectedMessage) {
		checkStateOfCurrent();
		String actual = current.message();
		if (!actual.equals(expectedMessage)) {
			throw assertionError("\"" + expectedMessage + "\"", "\"" + actual + "\"");
		}
		return this;
	}

	public CheckVerifier withMessageThat(Matcher<String> matcher) {
		checkStateOfCurrent();
		String actual = current.message();
		assertThat(actual, matcher);
		return this;
	}

	public CheckVerifier withCost(Double expectedCost) {
		checkStateOfCurrent();
		if (!Objects.equal(expectedCost, current.cost())) {
			throw assertionError(expectedCost, current.cost());
		}
		return this;
	}

	public CheckVerifier withSecondaryLines(int... expectedLines) {
		checkStateOfCurrent();

		Collections.sort(current.secondaryLines());
		Arrays.sort(expectedLines);

		int i;
		for (i = 0; i < expectedLines.length; i++) {
			if (current.secondaryLines().size() <= i) {
				throw new AssertionError("\nMissing secondary location at line " + expectedLines[i]);
			} else if (expectedLines[i] != current.secondaryLines().get(i)) {
				throw assertionError(expectedLines[i], current.secondaryLines().get(i));
			}
		}

		if (i < current.secondaryLines().size()) {
			throw new AssertionError(
					"\nNo more secondary location expected\ngot: at line " + current.secondaryLines().get(i));
		}

		return this;
	}

	private static AssertionError assertionError(Object expected, Object actual) {
		return new AssertionError("\nExpected: " + expected + "\ngot: " + actual);
	}
	
}
