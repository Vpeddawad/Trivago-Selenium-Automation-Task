Feature:  trivago.com search result.

Scenario Outline: Goto trivago.com and serach hotel or place with 

Given user is already on trivago Page
When title of login page is "<homePage>"
Then enter "<place>" to search  
Then it will list new serach page click to top 2 result
Then click on Our lowest price
Then goto the Our lowest price with free canceleation and  click
Then it will land to the vender page at different tab and validate the hotel and price.

Then Close browser

Examples:
	| place | homePage|  
	| Berlin  | trivago.com | 

	