//package org.sonar.swagger.checks;
//
//import com.google.common.base.Charsets;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.Lists;
//
//import java.io.*;
//import java.util.List;
//import java.util.Map;
//
//import org.sonar.api.internal.apachecommons.io.FileUtils;
//import org.sonar.swagger.checks.puppet.PuppetLicenseCheck;
//
//public class RuleDescriptionsGenerator {
//
//	private final Map<String, String> tags = ImmutableMap.<String, String>builder()
//			.put("[[allLicenses]]", generateHtmlTable(PuppetLicenseCheck.AVAILABLE_LICENSES)).build();
//
//	public void generateHtmlRuleDescription(String templatePath, String outputPath) throws IOException {
//		try (OutputStream fileOutputStream = new FileOutputStream(outputPath)) {
//			Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charsets.UTF_8));
//			writer.write(replaceTags(FileUtils.readFileToString(new File(templatePath), Charsets.UTF_8)));
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			throw new IllegalStateException("Could not generate the HTML description.", e);
//		}
//	}
//
//	private String generateHtmlTable(List<String> elements) {
//		StringBuilder html = new StringBuilder("<table style=\"border: 0;\">");
//		List<List<String>> subLists = Lists.partition(elements, 3);
//		for (List<String> subList : subLists) {
//			html.append("<tr>");
//			for (String element : subList) {
//				html.append("<td style=\"border: 0;\">").append(element).append("</td>");
//			}
//			html.append("</tr>");
//		}
//		html.append("</table>");
//		return html.toString();
//	}
//
//	private String replaceTags(String rawDescription) {
//		String description = rawDescription;
//		for (Map.Entry<String, String> tag : tags.entrySet()) {
//			description = description.replace(tag.getKey(), tag.getValue());
//		}
//		return description;
//	}
//
//}
