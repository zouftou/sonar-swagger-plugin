[![Release](https://img.shields.io/github/release/zouftou/sonar-swagger-plugin.svg)](https://github.com/zouftou/sonar-swagger-plugin/releases/latest)
[![Build Status](https://api.travis-ci.org/zouftou/sonar-swagger-plugin.svg?branch=master)](https://travis-ci.org/zouftou/sonar-swagger-plugin)
[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/imfckm45thk6vvh4/branch/master?svg=true)](https://ci.appveyor.com/project/zouftou/sonar-swagger-plugin/branch/master)

[![Quality Gate status](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.swagger%3Aswagger&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.swagger%3Aswagger)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.swagger%3Aswagger&metric=ncloc)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.swagger%3Aswagger)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.codehaus.sonar-plugins.swagger%3Aswagger&metric=coverage)](https://sonarcloud.io/dashboard?id=org.codehaus.sonar-plugins.swagger%3Aswagger)

# SonarQube Swagger Analyzer

## Description
This [SonarQube](http://www.sonarqube.org) plugin analyzes [Swagger 2.0](https://swagger.io/) files and:

 * Computes metrics: lines of code, statements, etc.
 * Checks various guidelines to find out potential bugs and code smells through more than [4 checks](#available-rules)
 * Provides the ability to write your own checks
 
 
## Usage
 
 
 
## Custom Checks
 
 
 
## Troubleshooting
 This syntax is not supported :
 ```
 description: 200 response
          examples:
            application/json: |-
              {
                  "versions": [
                      {
                          "status": "CURRENT",
                          "updated": "2012-01-01T00:00:00Z",
                          "id": "v1.0",
                          "links": [
                              {
                                  "href": "https://openstack.example.com/v1.0/",
                                  "rel": "self"
                              }
                          ]
                      }
                  ]
              }
 ```
 
## Available Rules

### Generic
* BOM should not be used for UTF-8 files
* File names should comply with a naming convention
* Parsing Error Swagger files.
* Swagger version should folow the [Semantic Versioning] (https://semver.org/spec/v2.0.0.html)
