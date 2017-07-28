# HistoryApiNavigationStateManager Add-on for Vaadin 8

HistoryApiNavigationStateManager is a utility add-on for Vaadin 8 that enables the Navigator to work with the HTML5 History API.

The Vaadin Navigator manages views with fragments in the URI. So a typical URI looks like this:

    http://localhost:8080/demo#!viewname/parameter
    
With this add-on you can have URLs without fragments. It enables Vaadin web applications to change the location of the browser and this way support proper deep linking, back button and search engines, without the hashbang ("#!" in the URL).
 
To make use of the add-on's functionality you use the HistoryApiNavigationStateManager instead of Vaadin's default UriFragmentManager together with the Navigator class. The HistoryApiNavigatorFactory has a convenient API to create a Navigator that automatically uses the HTML5 History API. You URL of the example above than looks like this:

    http://localhost:8080/demo/viewname/parameter
    
There is one little issue you have to have to mind. When you use empty context roots and an empty view name you cannot have parameters for that view. `http://localhost:8080//parameter` cannot be pushed because of the double slashes (try `window.history.pushState({}, "", "//param");` in your browser console to see what I mean) and `http://localhost:8080/parameter` is handled as if `parameter` is a view name.    

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/history-api-navigation

## Building and running demo

    git clone git clone https://github.com/apm78/history-api-navigation.git
    mvn clean install
    cd history-api-navigation-demo
    mvn jetty:run

To see the demo, navigate to http://localhost:8080/demo

## Release notes

### Version 0.2.1
- Improved state to path conversion
- Updated Vaadin dependency to 8.1.0

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

This add-on is written by Axel P. Meier.
