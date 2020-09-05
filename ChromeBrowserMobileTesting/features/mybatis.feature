@regression
@Replace(firstName=Maulidi)
@Replace(customerId=43,customerId2=53,customerId3=63)
Feature: Mybatis Test
	Mybatis Test
	
	Background:
		Given User initialize environment
		When I open my "ENDPOINT" website
		
	#@SqlBefore(common/delete.sql,common/insert.sql)
	#@SqlBefore(common/insert.sql)
	#@SqlBefore(common/update.sql)
	#@SqlBefore(common/delete.sql)
	Scenario Outline: Validate Mybatis implementation
		Given I test the mybatis to get customer by id <id>
		When I get the list of all customers
		Then I count the total number of customers
		
		Examples:
		|id   |
		|44    |