//package org.sonar.swagger.checks;
//
//import java.io.File;
//
//public class GenerateRuleDescriptionsBatch {
//
//	private static final String TEMPLATE_DIRECTORY = "swagger-checks/src/main/resources/org/sonar/l10n/swagger/rules/swagger/template/";
//	private static final String TARGET_DIRECTORY = "swagger-checks/target/classes/org/sonar/l10n/swagger/rules/swagger/";
//
//	private GenerateRuleDescriptionsBatch() {
//	}
//
//	public static void main(String... args) throws Exception {
//		RuleDescriptionsGenerator ruleDescriptionsGenerator = new RuleDescriptionsGenerator();
//		File[] files = new File(TEMPLATE_DIRECTORY).listFiles();
//		for (File file : files) {
//			ruleDescriptionsGenerator.generateHtmlRuleDescription(file.getPath(), TARGET_DIRECTORY + file.getName());
//		}
//	}
//
//}
