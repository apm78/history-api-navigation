# HistoryApiNavigationStateManager Add-on for Vaadin 8

HistoryApiNavigationStateManager is a utility add-on for Vaadin 8 that enables the Navigator to work with the HTML5 History API.

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/history-api-navigation

## Building and running demo

    git clone git clone https://github.com/apm78/history-api-navigation.git
    mvn clean install
    cd history-api-navigation-demo
    mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

### Version 0.1.2
- Fixed problems with different context paths
- Updated Vaadin dependency to 8.0.6

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.

HistoryApiNavigationStateManager is written by Axel P. Meier, akquinet engineering GmbH
