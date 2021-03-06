## Raising a Bug
While raising a bug, please provide:
* Version of the SonarQube platform
* Version of the Swagger plugin
* Log file of the analysis
* Source code to reproduce the error

## Contributing
Any contribution is more than welcome!
 
You feel like:
* Adding a new check? Just [open an issue](https://github.com/zouftou/sonar-swagger-plugin/issues/new) to discuss the value of your check. Once validated, code, don't forget to add a lot of unit tests and open a PR.
* Fixing some bugs or improving existing checks? Just open a PR.

## Building / Testing / Releasing
* Building and running unit tests: `mvn clean install`
* Building and running unit tests and running integration tests: `mvn clean install -Pits -Dsonar.runtimeVersion=$VERSION` ($VERSION = 'LTS' or 'LATEST_RELEASE'). Behind a proxy, add `-Dhttps.proxyHost=localhost -Dhttps.proxyPort=3128 -Dhttp.proxyHost=localhost -Dhttp.proxyPort=3128`.
* Releasing on Maven Central: `mvn clean deploy -Possrh`
