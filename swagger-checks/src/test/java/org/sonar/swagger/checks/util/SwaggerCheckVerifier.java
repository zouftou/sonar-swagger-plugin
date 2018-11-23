package org.sonar.swagger.checks.util;

import com.google.common.base.Charsets;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sonar.swagger.parser.SwaggerParserBuilder;
import org.sonar.swagger.visitors.CharsetAwareVisitor;
import org.sonar.swagger.visitors.SwaggerVisitorContext;
import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.visitors.issue.*;

/**
 * To unit test checks.
 */
public class SwaggerCheckVerifier {

	private SwaggerCheckVerifier() {
	}

	/**
	 * Check verify
	 * 
	 * @param check
	 *            Check to test
	 * @param file
	 *            File to test
	 *
	 *            Example:
	 * 
	 *            <pre>
	 * SwaggerCheckVerifier.verify(new MyCheck(), myFile, Charsets.UTF_8))
	 *    .next().startAtLine(2).withMessage("This is message for line 2.")
	 *    .next().startAtLine(3).withMessage("This is message for line 3.").withCost(2.)
	 *    .next().startAtLine(8).startAtColumn(4)
	 *    .noMore();
	 *            </pre>
	 */
	public static CheckVerifier verify(SwaggerCheck check, File file) {
		return verify(check, file, Charsets.UTF_8);
	}

	public static CheckVerifier verify(SwaggerCheck check, File file, Charset charset) {
		if (check instanceof CharsetAwareVisitor) {
			((CharsetAwareVisitor) check).setCharset(charset);
		}
		return CheckVerifier.verify(getTestIssues(file.getAbsolutePath(), check, charset));
	}

	private static Collection<TestIssue> getTestIssues(String relativePath, SwaggerCheck check, Charset charset) {
		File file = new File(relativePath);

		SwaggerTree swaggerTree = (SwaggerTree) SwaggerParserBuilder.createParser(charset).parse(file);
		SwaggerVisitorContext context = new SwaggerVisitorContext(swaggerTree, file);
		List<Issue> issues = check.scanFile(context);

		List<TestIssue> testIssues = new ArrayList<>();
		for (Issue issue : issues) {
			TestIssue testIssue;

			if (issue instanceof FileIssue) {
				FileIssue fileIssue = (FileIssue) issue;
				testIssue = new TestIssue(fileIssue.message());
				for (IssueLocation issueLocation : fileIssue.secondaryLocations()) {
					testIssue.addSecondaryLine(issueLocation.startLine());
				}

			} else if (issue instanceof LineIssue) {
				LineIssue lineIssue = (LineIssue) issue;
				testIssue = new TestIssue(lineIssue.message()).starLine(lineIssue.line());

			} else {
				PreciseIssue preciseIssue = (PreciseIssue) issue;
				testIssue = new TestIssue(preciseIssue.primaryLocation().message())
						.starLine(preciseIssue.primaryLocation().startLine())
						.startColumn(preciseIssue.primaryLocation().startLineOffset() + 1)
						.endLine(preciseIssue.primaryLocation().endLine())
						.endColumn(preciseIssue.primaryLocation().endLineOffset() + 1);
				for (IssueLocation issueLocation : preciseIssue.secondaryLocations()) {
					testIssue.addSecondaryLine(issueLocation.startLine());
				}
			}

			if (issue.cost() != null) {
				testIssue.cost(issue.cost());
			}

			testIssues.add(testIssue);
		}

		return testIssues;
	}

}
